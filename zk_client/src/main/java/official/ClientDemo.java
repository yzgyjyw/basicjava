package official;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Cleaner;

import java.io.IOException;
import java.util.List;

public class ClientDemo implements Watcher {

    private static Logger LOGGER = LoggerFactory.getLogger(ClientDemo.class);

    private ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
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
                    getChildNode(watchedEvent.getPath());
                } else if (Event.EventType.NodeCreated.equals(watchedEvent.getType())) {
                    LOGGER.info("NodeCreated,{}", watchedEvent);
                } else if (Event.EventType.NodeDataChanged.equals(watchedEvent.getType())) {
                    LOGGER.info("NodeDataChanged,{}", watchedEvent);
                    getData(watchedEvent.getPath());
                } else if (Event.EventType.NodeDeleted.equals(watchedEvent.getType())) {
                    LOGGER.info("NodeDeleted,{}", watchedEvent);
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
    }


    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {

        ClientDemo clientDemo = new ClientDemo();

        clientDemo.connect();
        clientDemo.existAndDelete("/test01");
        clientDemo.createPersistentNode("/test01");
        clientDemo.getChildNode("/test01");
        clientDemo.getData("/test01");

        Thread.sleep(1000000000);
    }

    public void connect() throws IOException {
        String connectString = "bigdata03:2181,bigdata04:2181,bigdata05:2181/ctest";
        int sessionTimeout = 10000000;
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, this);
    }

    public void getData(String path) throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData(path, this, stat);
        LOGGER.info("get data stat,stat:{},data:{}", stat, new String(data));
    }

    public void existAndDelete(String path) throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/test01", true);
        Object trace = "trace";
        if (exists != null) {
            zooKeeper.delete(path, -1);
        }
    }

    public void createPersistentNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.create(path, "I'm test01".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void getChildNode(String path) throws KeeperException, InterruptedException {

        Object ctx = "trace";

        Stat stat = new Stat();
        zooKeeper.getChildren(path, this, new AsyncCallback.ChildrenCallback() {

            @Override
            public void processResult(int i, String s, Object o, List<String> list) {
                LOGGER.info("i={},s={},o={}", i, s, o);
                list.forEach(cpath -> LOGGER.info(cpath));
            }
        }, ctx);

    }


}