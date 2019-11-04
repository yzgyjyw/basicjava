两种方式
1. subscribe(动态分区)
2. assign(静态分区,手动指定,并且没有rebalance)

提交偏移量
new consumer 
有自动提交偏移量的后台任务.有同步和异步两种模式


每一个消费组有一个协调者Coordinator


ConsumerCoordinator 消费者的协调者对象(类似于rpc的client)
GroupCoordinator(服务端消费者,类似于rpc的server)


消费者轮寻的前提准备
1. ensureCoordiantorKnown   确保客户端连接上协调者
2. ensurePartitionAssignment 确保消费者收到协调者分配给他的分区

属于同一个消费组的消费者需要连接的协调者节点都是同一个
