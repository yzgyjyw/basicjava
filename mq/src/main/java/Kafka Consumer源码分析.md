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


  //初始化consumer的名称,这边是hello-group_mi-OptiPlex-7050-1570624931963-6158be7a,这个值与partition的分配相关
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

    //StaticTopicCount对象,{consumerIdString='hello-group_mi-OptiPlex-7050-1570626069724-c647fe32',topicCountMap{(test02,3),(test01,2)}}
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
###### 在zk上注册当前consumer：在/consumers/group_name/ids下增加一个临时节点
```
private def registerConsumerInZK(dirs: ZKGroupDirs, consumerIdString: String, topicCount: TopicCount) {
    info("begin registering consumer " + consumerIdString + " in ZK")
    val timestamp = SystemTime.milliseconds.toString
    val consumerRegistrationInfo = Json.encode(Map("version" -> 1, "subscription" -> topicCount.getTopicCountMap, "pattern" -> topicCount.pattern,
                                                  "timestamp" -> timestamp))
    val zkWatchedEphemeral = new ZKCheckedEphemeral(dirs.
                                                    consumerRegistryDir + "/" + consumerIdString,
                                                    consumerRegistrationInfo,
                                                    zkUtils.zkConnection.getZookeeper,
                                                    false)
    /*
    * path: /consumers/hello-group/ids/hello-group_mi-OptiPlex-7050-1570701002210-f5d11e4b(consumerIdString)
    * date: {"version":1,"subscription":{"test02":3,"test01":2},"pattern":"static","timestamp":"1570701004383"}
    */
    zkWatchedEphemeral.create()
  }
```
##### 初始化consumer
```
private def reinitializeConsumer[K,V](
      topicCount: TopicCount,
      queuesAndStreams: List[(LinkedBlockingQueue[FetchedDataChunk],KafkaStream[K,V])]) {

    val dirs = new ZKGroupDirs(config.groupId)

    // listener to consumer and partition changes
    if (loadBalancerListener == null) {
      val topicStreamsMap = new mutable.HashMap[String,List[KafkaStream[K,V]]]
      loadBalancerListener = new ZKRebalancerListener(
        config.groupId, consumerIdString, topicStreamsMap.asInstanceOf[scala.collection.mutable.Map[String, List[KafkaStream[_,_]]]])
    }

    // create listener for session expired event if not exist yet
    if (sessionExpirationListener == null)
      sessionExpirationListener = new ZKSessionExpireListener(
        dirs, consumerIdString, topicCount, loadBalancerListener)

    // create listener for topic partition change event if not exist yet
    if (topicPartitionChangeListener == null)
      topicPartitionChangeListener = new ZKTopicPartitionChangeListener(loadBalancerListener)

    val topicStreamsMap = loadBalancerListener.kafkaMessageAndMetadataStreams

    // map of {topic -> Set(thread-1, thread-2, ...)}
    val consumerThreadIdsPerTopic: Map[String, Set[ConsumerThreadId]] =
      topicCount.getConsumerThreadIdsPerTopic

    val allQueuesAndStreams = topicCount match {
      case wildTopicCount: WildcardTopicCount =>
        /*
         * Wild-card consumption streams share the same queues, so we need to
         * duplicate the list for the subsequent zip operation.
         */
        (1 to consumerThreadIdsPerTopic.keySet.size).flatMap(_ => queuesAndStreams).toList
      case statTopicCount: StaticTopicCount =>
        queuesAndStreams
    }

    val topicThreadIds = consumerThreadIdsPerTopic.map {
      case(topic, threadIds) =>
        threadIds.map((topic, _))
    }.flatten

    threadQueueStreamPairs.foreach(e => {
      val topicThreadId = e._1
      val q = e._2._1
      topicThreadIdAndQueues.put(topicThreadId, q)
      debug("Adding topicThreadId %s and queue %s to topicThreadIdAndQueues data structure".format(topicThreadId, q.toString))
      newGauge(
        "FetchQueueSize",
        new Gauge[Int] {
          def value = q.size
        },
        Map("clientId" -> config.clientId,
          "topic" -> topicThreadId._1,
          "threadId" -> topicThreadId._2.threadId.toString)
      )
    })

    val groupedByTopic = threadQueueStreamPairs.groupBy(_._1._1)
    groupedByTopic.foreach(e => {
      val topic = e._1
      val streams = e._2.map(_._2._2).toList
      topicStreamsMap += (topic -> streams)
      debug("adding topic %s and %d streams to map.".format(topic, streams.size))
    })

    // listener to consumer and partition changes
    zkUtils.zkClient.subscribeStateChanges(sessionExpirationListener)

    zkUtils.zkClient.subscribeChildChanges(dirs.consumerRegistryDir, loadBalancerListener)

    topicStreamsMap.foreach { topicAndStreams =>
      // register on broker partition path changes
      val topicPath = BrokerTopicsPath + "/" + topicAndStreams._1
      zkUtils.zkClient.subscribeDataChanges(topicPath, topicPartitionChangeListener)
    }

    //consumer刚启动时直接出发rebalance
    loadBalancerListener.syncedRebalance()
  }
```

##### reebalance
```
 private def rebalance(cluster: Cluster): Boolean = {
      val myTopicThreadIdsMap = TopicCount.constructTopicCount(
        group, consumerIdString, zkUtils, config.excludeInternalTopics).getConsumerThreadIdsPerTopic
      val brokers = zkUtils.getAllBrokersInCluster()
      if (brokers.size == 0) {
        // This can happen in a rare case when there are no brokers available in the cluster when the consumer is started.
        // We log an warning and register for child changes on brokers/id so that rebalance can be triggered when the brokers
        // are up.
        warn("no brokers found when trying to rebalance.")
        zkUtils.zkClient.subscribeChildChanges(BrokerIdsPath, loadBalancerListener)
        true
      }
      else {
        /**
         * fetchers must be stopped to avoid data duplication, since if the current
         * rebalancing attempt fails, the partitions that are released could be owned by another consumer.
         * But if we don't stop the fetchers first, this consumer would continue returning data for released
         * partitions in parallel. So, not stopping the fetchers leads to duplicate data.
         */
         //关闭消息拉取器
        closeFetchers(cluster, kafkaMessageAndMetadataStreams, myTopicThreadIdsMap)
        if (consumerRebalanceListener != null) {
          info("Invoking rebalance listener before relasing partition ownerships.")
          consumerRebalanceListener.beforeReleasingPartitions(
            if (topicRegistry.size == 0)
              new java.util.HashMap[String, java.util.Set[java.lang.Integer]]
            else
              mapAsJavaMap(topicRegistry.map(topics =>
                topics._1 -> topics._2.keys
              ).toMap).asInstanceOf[java.util.Map[String, java.util.Set[java.lang.Integer]]]
          )
        }
        //释放之前的消费者分配方案
        releasePartitionOwnership(topicRegistry)
        //获取每一个topic对应的partition数目，consumer数目
        val assignmentContext = new AssignmentContext(group, consumerIdString, config.excludeInternalTopics, zkUtils)
        // 全局的分配结果,针对当前consumerConnector中订阅的topic的全局分配结果
        val globalPartitionAssignment = partitionAssignor.assign(assignmentContext)
        // 获取当前consumerConnector分配的分区
        // 0 = {Tuple2@2856} "([test02,5],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-2)"
        // 1 = {Tuple2@2857} "([test02,2],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-1)"
        // 2 = {Tuple2@2858} "([test01,1],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-0)"
        // 3 = {Tuple2@2859} "([test02,1],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-0)"
        // 4 = {Tuple2@2860} "([test02,4],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-2)"
        // 5 = {Tuple2@2861} "([test02,3],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-1)"
        // 6 = {Tuple2@2862} "([test01,0],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-0)"
        // 7 = {Tuple2@2863} "([test02,0],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-0)"
        // 8 = {Tuple2@2864} "([test01,2],hello-group_mi-OptiPlex-7050-1570846172781-6f97fcc1-1)"
        val partitionAssignment = globalPartitionAssignment.get(assignmentContext.consumerId)
        
        val currentTopicRegistry = new Pool[String, Pool[Int, PartitionTopicInfo]](
          valueFactory = Some((topic: String) => new Pool[Int, PartitionTopicInfo]))

        
        val topicPartitions = partitionAssignment.keySet.toSeq
        
        //拉取每一个partition的offset
        val offsetFetchResponseOpt = fetchOffsets(topicPartitions)

        if (isShuttingDown.get || !offsetFetchResponseOpt.isDefined)
          false
        else {
          val offsetFetchResponse = offsetFetchResponseOpt.get
          topicPartitions.foreach(topicAndPartition => {
            val (topic, partition) = topicAndPartition.asTuple
            val offset = offsetFetchResponse.requestInfo(topicAndPartition).offset
            val threadId = partitionAssignment(topicAndPartition)
            addPartitionTopicInfo(currentTopicRegistry, partition, topic, offset, threadId)
          })

          /**
           * move the partition ownership here, since that can be used to indicate a truly successful re-balancing attempt
           * A rebalancing attempt is completed successfully only after the fetchers have been started correctly
           */
          if(reflectPartitionOwnershipDecision(partitionAssignment)) {
            allTopicsOwnedPartitionsCount = partitionAssignment.size

            partitionAssignment.view.groupBy { case(topicPartition, consumerThreadId) => topicPartition.topic }
                                      .foreach { case (topic, partitionThreadPairs) =>
              newGauge("OwnedPartitionsCount",
                new Gauge[Int] {
                  def value() = partitionThreadPairs.size
                },
                ownedPartitionsCountMetricTags(topic))
            }
            //consumer进行消费之前需要的信息都在这个对象中，包含每个partition的offset
            topicRegistry = currentTopicRegistry
            // Invoke beforeStartingFetchers callback if the consumerRebalanceListener is set.
            if (consumerRebalanceListener != null) {
              info("Invoking rebalance listener before starting fetchers.")

              // Partition assignor returns the global partition assignment organized as a map of [TopicPartition, ThreadId]
              // per consumer, and we need to re-organize it to a map of [Partition, ThreadId] per topic before passing
              // to the rebalance callback.
              val partitionAssginmentGroupByTopic = globalPartitionAssignment.values.flatten.groupBy[String] {
                case (topicPartition, _) => topicPartition.topic
              }
              val partitionAssigmentMapForCallback = partitionAssginmentGroupByTopic.map({
                case (topic, partitionOwnerShips) =>
                  val partitionOwnershipForTopicScalaMap = partitionOwnerShips.map({
                    case (topicAndPartition, consumerThreadId) =>
                      topicAndPartition.partition -> consumerThreadId
                  })
                  topic -> mapAsJavaMap(collection.mutable.Map(partitionOwnershipForTopicScalaMap.toSeq:_*))
                    .asInstanceOf[java.util.Map[java.lang.Integer, ConsumerThreadId]]
              })
              consumerRebalanceListener.beforeStartingFetchers(
                consumerIdString,
                mapAsJavaMap(collection.mutable.Map(partitionAssigmentMapForCallback.toSeq:_*))
              )
            }
            updateFetcher(cluster)
            true
          } else {
            false
          }
        }
      }
    }
```
```scala
class AssignmentContext(group: String, val consumerId: String, excludeInternalTopics: Boolean, zkUtils: ZkUtils) {
  //当前consumerConnector的consumer线程集合
  //(test01,List(hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-0, hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-1))
  //,(test02,List(hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-0, hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-1,
  val myTopicThreadIds: collection.Map[String, collection.Set[ConsumerThreadId]] = {
    val myTopicCount = TopicCount.constructTopicCount(group, consumerId, zkUtils, excludeInternalTopics)
    myTopicCount.getConsumerThreadIdsPerTopic
  }
  // 获取topic的partition
  // zk路径 /brokers/topics/test01
  // (test01,ArrayBuffer(0, 1, 2)),(test02,ArrayBuffer(0, 1, 2, 3, 4, 5))
  val partitionsForTopic: collection.Map[String, Seq[Int]] =
    zkUtils.getPartitionsForTopics(myTopicThreadIds.keySet.toSeq)

  //获取topic的consumer数量(注意不是consumerConnetc的数量,而是consumer线程数)
  //先获取/consumers/group/ids下的consumerConnect的节点数目,再分别将节点data取出来,计算出每一个consumerConnector的consumer线程数
  //data:{"version":1,"subscription":{"test02":3,"test01":2},"pattern":"static","timestamp":"1570843716967"}
  //(test01,List(hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-0, hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-1))
  //,(test02,List(hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-0, hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-1, hello-group_mi-OptiPlex-7050-1570843882022-3a773d86-2))
  val consumersForTopic: collection.Map[String, List[ConsumerThreadId]] =
    zkUtils.getConsumersPerTopic(group, excludeInternalTopics)

  //按照consumerConnector的名称排序,即consumerIdString,consumerIdString的生成
  val consumers: Seq[String] = zkUtils.getConsumersInGroup(group).sorted
}
```
updateFetcher(localTopicRegistry)
    LeaderFinder()
        addFetcherForPartition([TopicAndPartition,BrokerAndOffset])
            createFetcherThread
                为新建的或者之前建立的FectherThread添加topicPartition
                
拉取线程ConsumerFetcherThread的数目与topic partition的主副本数目和numFetchers有关,默认numFetchers为1,即只与
当前consumer消费的所有的topic的所有partition的主副本有关,一般情况下,我们会将每一个topic的partition设置为broker的
数量,这样其实thread就与broker的数目有关


```scala
class RangeAssignor() extends PartitionAssignor with Logging {

  def assign(ctx: AssignmentContext) = {
    val valueFactory = (topic: String) => new mutable.HashMap[TopicAndPartition, ConsumerThreadId]
    val partitionAssignment =
      new Pool[String, mutable.Map[TopicAndPartition, ConsumerThreadId]](Some(valueFactory))
    //为当前consumerConnector订阅的每一个主题进行partition分配
    for (topic <- ctx.myTopicThreadIds.keySet) {
      val curConsumers = ctx.consumersForTopic(topic)
      val curPartitions: Seq[Int] = ctx.partitionsForTopic(topic)

      val nPartsPerConsumer = curPartitions.size / curConsumers.size
      //不能整除时,剩下的需要多分配一个partition的consumerThread的数量
      val nConsumersWithExtraPart = curPartitions.size % curConsumers.size

      info("Consumer " + ctx.consumerId + " rebalancing the following partitions: " + curPartitions +
        " for topic " + topic + " with consumers: " + curConsumers)

      for (consumerThreadId <- curConsumers) {
        val myConsumerPosition = curConsumers.indexOf(consumerThreadId)
        assert(myConsumerPosition >= 0)
        //当前consumerThread消费的partition的起始位置
        val startPart = nPartsPerConsumer * myConsumerPosition + myConsumerPosition.min(nConsumersWithExtraPart)
        //当前consumerThreadId消费的partition的总数
        val nParts = nPartsPerConsumer + (if (myConsumerPosition + 1 > nConsumersWithExtraPart) 0 else 1)

        /**
         *   Range-partition the sorted partitions to consumers for better locality.
         *  The first few consumers pick up an extra partition, if any.
         */
        if (nParts <= 0)
          warn("No broker partitions consumed by consumer thread " + consumerThreadId + " for topic " + topic)
        else {
          for (i <- startPart until startPart + nParts) {
            val partition = curPartitions(i)
            info(consumerThreadId + " attempting to claim partition " + partition)
            // record the partition ownership decision
            val assignmentForConsumer = partitionAssignment.getAndMaybePut(consumerThreadId.consumer)
            assignmentForConsumer += (TopicAndPartition(topic, partition) -> consumerThreadId)
          }
        }
      }
    }

    // assign Map.empty for the consumers which are not associated with topic partitions
    ctx.consumers.foreach(consumerId => partitionAssignment.getAndMaybePut(consumerId))
    partitionAssignment
  }
}
```