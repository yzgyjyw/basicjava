import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.Set;

public class TestJedusReturnCode {
    public static void main(String[] args) {
//        hdelReturnCode();
//        hexpireCode();
//        scan();
        del();
    }

    public static void hdelReturnCode() {
        Jedis jedis = new Jedis("127.0.0.1");
        Long hset = jedis.hset("map1", "username", "jyw");
        System.out.println("hset return code:\t" + hset);

        Long hdel = jedis.hdel("map1", "username");
        System.out.println("hdel return code:\t" + hdel);
        jedis.close();
    }

    public static void hexpireCode() {
        Jedis jedis = new Jedis("127.0.0.1");
        Long hset = jedis.hset("map2", "username", "jyw");
        System.out.println("hset return code:\t" + hset);

        Long ttl = jedis.ttl("map2");
        System.out.println("ttl:\t" + ttl);

        Long map2TTL = jedis.expire("map2", 100);
        System.out.println("map2TTL:\t" + map2TTL);

        jedis.close();
    }

    public static void scan(){
        Jedis jedis = new Jedis("127.0.0.1");

        ScanParams scanParams = new ScanParams();
        scanParams.match("map*");
        scanParams.count(5);
        String select = jedis.select(1);
        System.out.println(select);
        ScanResult<String> scan = jedis.scan("1", scanParams);
        System.out.println("scan：返回用于下次遍历的游标"+scan.getStringCursor());
        System.out.println("scan：返回结果"+scan.getResult());

        jedis.close();
    }

    public static void del(){
        Jedis jedis = new Jedis("127.0.0.1");

        Long a = jedis.del("a");
        System.out.println(a);

        jedis.close();
    }
}
