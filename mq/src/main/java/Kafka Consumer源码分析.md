## 背景:
```
Properties props = new Properties();
            props.put("zookeeper.connect", zkAddress);
            props.put("group.id", "hello-group"+j);
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "300000000000");
            Map<String, Integer> topicCountMap = new HashMap<>();
            topicCountMap.put("test01", 2);
            topicCountMap.put("test02", 3);
            //第一步:创建消费者连接器
            ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
            //第二步:创建消息流
            Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumerConnector.createMessageStreams(topicCountMap);
```

## 分析消费者连接器的创建
### 第一步 创建ZookeeperConsumerConnector
```
  private val isShuttingDown = new AtomicBoolean(false)
  private val rebalanceLock = new Object
  private var fetcher: Option[ConsumerFetcherManager] = None
  private var zkUtils: ZkUtils = null
  private var topicRegistry = new Pool[String, Pool[Int, PartitionTopicInfo]]
  private val checkpointedZkOffsets = new Pool[TopicAndPartition, Long]
  private val topicThreadIdAndQueues = new Pool[(String, ConsumerThreadId), BlockingQueue[FetchedDataChunk]]
  private val scheduler = new KafkaScheduler(threads = 1, threadNamePrefix = "kafka-consumer-scheduler-")
  private val messageStreamCreated = new AtomicBoolean(false)

  private var sessionExpirationListener: ZKSessionExpireListener = null
  private var topicPartitionChangeListener: ZKTopicPartitionChangeListener = null
  private var loadBalancerListener: ZKRebalancerListener = null

  private var offsetsChannel: BlockingChannel = null
  private val offsetsChannelLock = new Object

  private var wildcardTopicWatcher: ZookeeperTopicEventWatcher = null
  private var consumerRebalanceListener: ConsumerRebalanceListener = null

  // useful for tracking migration of consumers to store offsets in kafka
  private val kafkaCommitMeter = newMeter("KafkaCommitsPerSec", "commits", TimeUnit.SECONDS, Map("clientId" -> config.clientId))
  private val zkCommitMeter = newMeter("ZooKeeperCommitsPerSec", "commits", TimeUnit.SECONDS, Map("clientId" -> config.clientId))
  private val rebalanceTimer = new KafkaTimer(newTimer("RebalanceRateAndTime", TimeUnit.MILLISECONDS, TimeUnit.SECONDS, Map("clientId" -> config.clientId)))


  //初始化consumer的名称,这边是hello-group_mi-OptiPlex-7050-1570624931963-6158be7a
  val consumerIdString = {
    var consumerUuid : String = null
    config.consumerId match {
      case Some(consumerId) // for testing only
      => consumerUuid = consumerId
      case None // generate unique consumerId automatically
      => val uuid = UUID.randomUUID()
      consumerUuid = "%s-%d-%s".format(
        InetAddress.getLocalHost.getHostName, System.currentTimeMillis,
        uuid.getMostSignificantBits().toHexString.substring(0,8))
    }
    config.groupId + "_" + consumerUuid
  }
  
  //初始化zkUtils工具类
  connectZk()
  //创建初始化拉取管理器  new ConsumerFetcherManager
  createFetcher()
  //当使用kafka内部主题管理offset才会执行,使用zk管理offset可以忽略该方法
  ensureOffsetManagerConnected()
  
  //如果是自动提交consumerOffset,则这边开启一个定时任务进行提交
  if (config.autoCommitEnable) {
    scheduler.startup
    info("starting auto committer every " + config.autoCommitIntervalMs + " ms")
    scheduler.schedule("kafka-consumer-autocommit",
                       autoCommit,
                       delay = config.autoCommitIntervalMs, //1000
                       period = config.autoCommitIntervalMs,//1000
                       unit = TimeUnit.MILLISECONDS)
  }
```
### 第二步 createMessageStreams

#### 调用ZookeeperConsumerConnector的consumer方法返回Map[String,List[KafkaStream[K,V]]]
```
def consume[K, V](topicCountMap: scala.collection.Map[String,Int], keyDecoder: Decoder[K], valueDecoder: Decoder[V])
      : Map[String,List[KafkaStream[K,V]]] = {
    debug("entering consume ")
    if (topicCountMap == null)
      throw new RuntimeException("topicCountMap is null")

    //StaticTopicCount对象,{consumerIdStrin='hello-group_mi-OptiPlex-7050-1570626069724-c647fe32',topicCountMap{(test02,3),(test01,2)}}
    val topicCount = TopicCount.constructTopicCount(consumerIdString, topicCountMap)

    //(test01,Set(hello-group_mi-OptiPlex-7050-1570626069724-c647fe32-1, hello-group_mi-OptiPlex-7050-1570626069724-c647fe32-0))
    //(test02,Set(hello-group_mi-OptiPlex-7050-1570626069724-c647fe32-2, hello-group_mi-OptiPlex-7050-1570626069724-c647fe32-1, hello-group_mi-OptiPlex-7050-1570626069724-c647fe32-0))
    val topicThreadIds = topicCount.getConsumerThreadIdsPerTopic

    //size:5    ([],kafkaStream)
    val queuesAndStreams = topicThreadIds.values.map(threadIdSet =>
      threadIdSet.map(_ => {
        val queue =  new LinkedBlockingQueue[FetchedDataChunk](config.queuedMaxMessages)
        val stream = new KafkaStream[K,V](
          queue, config.consumerTimeoutMs, keyDecoder, valueDecoder, config.clientId)
        (queue, stream)
      })
    ).flatten.toList

    // hello-group
    val dirs = new ZKGroupDirs(config.groupId)
    
    //
    registerConsumerInZK(dirs, consumerIdString, topicCount)
    //
    reinitializeConsumer(topicCount, queuesAndStreams)

    //
    loadBalancerListener.kafkaMessageAndMetadataStreams.asInstanceOf[Map[String, List[KafkaStream[K,V]]]]
  }
```