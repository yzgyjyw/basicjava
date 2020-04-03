import redis.clients.jedis.Jedis;

public class String2Hash {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");

        

        jedis.close();
    }

}
