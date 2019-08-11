package official;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Cleaner;

import java.io.IOException;

public class ClientDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(ClientDemo.class);

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        String connectString = "bigdata03:2181/test,bigdata04:2181/test,bigdata05:2181/test";
        int sessionTimeout = 1000;
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (Event.KeeperState.AuthFailed.equals(watchedEvent.getState())) {
                    LOGGER.warn("zk session authFailed,{}", watchedEvent);
                } else if (Event.KeeperState.Disconnected.equals(watchedEvent.getState())) {
                    LOGGER.warn("zk session disConnected,{}", watchedEvent);
                } else if (Event.KeeperState.Expired.equals(watchedEvent.getState())) {
                    LOGGER.warn("zk session expired,{}", watchedEvent);
                } else if (Event.KeeperState.Expired.equals(watchedEvent.getState())) {
                    LOGGER.warn("zk session expired,{}", watchedEvent);
                } else if (Event.KeeperState.SyncConnected.equals(watchedEvent.getState())) {
                    if (Event.EventType.None.equals(watchedEvent.getType())) {
                        LOGGER.info("zk session connected,{}", watchedEvent);
                    } else if (Event.EventType.NodeChildrenChanged.equals(watchedEvent.getType())) {
                        LOGGER.info("NodeChildrenChanged,{}", watchedEvent);
                    } else if (Event.EventType.NodeCreated.equals(watchedEvent.getType())) {
                        LOGGER.info("NodeCreated,{}", watchedEvent);
                    } else if (Event.EventType.NodeDataChanged.equals(watchedEvent.getType())) {
                        LOGGER.info("NodeDataChanged,{}", watchedEvent);
                    } else if (Event.EventType.NodeDeleted.equals(watchedEvent.getType())) {
                        LOGGER.info("NodeDeleted,{}", watchedEvent);
                    }
                }
            }
        });

        Stat exists = zooKeeper.exists("/test01", true);

        System.out.println("1");

    }
}
