import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class TestJedusReturnCode {
    public static void main(String[] args) {
        /*hdelReturnCode();
        hexpireCode();
        scan();
        del();*/
//        setNx();
//        testLuaScript();
//        testPublish();
//        time();
        hget();
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
//        System.out.println("scan：返回用于下次遍历的游标"+scan.getStringCursor());
        System.out.println("scan：返回结果"+scan.getResult());

        jedis.close();
    }

    public static void del(){
        Jedis jedis = new Jedis("127.0.0.1");
        Long a = jedis.del("a");
        System.out.println(a);
        jedis.close();
    }

    public static void setNx(){
        Jedis jedis = new Jedis("127.0.0.1");
//        String s = jedis.set("f", "1", "NX", "PX", 10000000);
        Long f = jedis.pexpire("f", 1000000);
        Long decrBy = jedis.decrBy("a", 1);
        Long ttl = jedis.pttl("a");
        String a = jedis.get("a");
        Long bttl = jedis.pttl("b");
//        System.out.println(s);
        System.out.println(f);
        System.out.println(decrBy);
        System.out.println(ttl);
        System.out.println(a);
        System.out.println(bttl);
        jedis.close();
    }

    public static void testLuaScript(){
        Jedis jedis = new Jedis("127.0.0.1");
        String lua = "return 'hello world'";
        List<String> keyList = new ArrayList<>();
        List<String> argList = new ArrayList<>();
        Object o = jedis.evalsha(jedis.scriptLoad(lua), keyList, argList);
        System.out.println(o);
        jedis.close();
    }

    public static void testPublish(){
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println(jedis.publish("abc","abc"));
        jedis.close();
    }

    public static void time(){
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println(jedis.hincrBy("1","1",10L));
        jedis.close();
    }

    public static void hget(){
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println(jedis.hget("abc","ghy"));
        jedis.close();
        AtomicInteger atomicInteger = new AtomicInteger(10);
        atomicInteger.getAndUpdate(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                return 0;
            }
        });
    }
}