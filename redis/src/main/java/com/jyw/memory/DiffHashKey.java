package com.jyw.memory;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class DiffHashKey {

    public static void main(String[] args) {
//        for(int i=0;i<20;i++){
//            insertData1();
//        }

//          query();
//        insertData1();
//        for(int i=0;i<10;i++){
//            insertData3();
//        }

        query2();

    }


    public static void deleteAll(){
        Jedis jedis = new Jedis("127.0.0.1");
        Set<String> keys = jedis.keys("*");
        if(keys.size()>0){
            String [] strKeys = new String[keys.size()];
            keys.toArray(strKeys);
            jedis.del(strKeys);
        }
        jedis.close();
    }


    //存储格式为  hashmapname regid , <formid,timestamp>或者regid～formid_timestamp
    //不拆分子key
    //10000条数据 17555688
    public static void insertData1(){
        Jedis jedis = new Jedis("127.0.0.1");
        long start = System.currentTimeMillis();
        for(int i = 0;i<100000;i++){
            String regId = UUID.randomUUID().toString();
            String innerkey = String.valueOf(System.currentTimeMillis());
            String formid = UUID.randomUUID().toString();
            jedis.hset(regId,innerkey,formid);
        }
        long stop = System.currentTimeMillis();
        System.out.println(stop-start);
        jedis.close();
    }


    //拆分子key  outerkey:regid/2 regid/2 --> formid_timestamp
    //19160584
    public static void insertData2(){
        Jedis jedis = new Jedis("127.0.0.1");
        long start = System.currentTimeMillis();
        for(int i = 0;i<100000;i++){
            String regId = UUID.randomUUID().toString();
            String outerKey = regId.substring(0,regId.length()/2);
            String innerKey = regId.substring(regId.length()/2);

            String formid = UUID.randomUUID().toString();
            String value= formid+"~"+String.valueOf(System.currentTimeMillis());
            jedis.hset(outerKey,innerKey,value);
        }
        long stop = System.currentTimeMillis();

        System.out.println(stop-start);

        jedis.close();
    }

    //在insertData2的基础上在增加10000条数据，不过其outerkey都是之前存在的
    //25560744
    public static void insertData3(){
        Jedis jedis = new Jedis("127.0.0.1");
        Set<String> keys = jedis.keys("*");
        keys.stream().forEach(key->{
            String regId = UUID.randomUUID().toString();
            String innerKey = regId.substring(regId.length()/2);
            String formid = UUID.randomUUID().toString();
            String value= formid+"~"+String.valueOf(System.currentTimeMillis());
            jedis.hset(key,innerKey,value);
        });

        long start = System.currentTimeMillis();
        long stop = System.currentTimeMillis();

        System.out.println(stop-start);

        jedis.close();
    }

    //1w    19160584              17564232
    //2w    25560744              34612968
    //3w    35161224              52710120
    //4w    41561544              68710120
    //5w    47961704              84710120
    //6w    54361864              104904424
    //7w    73562024              120904424
    //10w   87966424              168904880
    //随着

    public static void query(){
        Jedis jedis = new Jedis("127.0.0.1");
        Set<String> stringSet = jedis.keys("*");
        String[] keys = new String[stringSet.size()];
        stringSet.toArray(keys);
        for(int i=0;i<20;i++){
            long start = System.currentTimeMillis();
            //jedis.hgetAll("949ae4cf-062c-4c34-8022-6fd6de11989f");
            String regId = keys[Math.abs(new Random().nextInt())%1000];
            String outerKey = regId.substring(0,regId.length()/2);
            String innerKey = regId.substring(regId.length()/2);
            jedis.hget(innerKey,outerKey);
            long stop = System.currentTimeMillis();
            System.out.println(stop-start);
        }
        jedis.close();
    }

    public static void query2(){
        Jedis jedis = new Jedis("127.0.0.1");
        Set<String> stringSet = jedis.keys("*");
        String[] keys = new String[stringSet.size()];
        stringSet.toArray(keys);
        for(int i=0;i<20;i++){
            long start = System.currentTimeMillis();
            //jedis.hgetAll("949ae4cf-062c-4c34-8022-6fd6de11989f");
            String regId = keys[Math.abs(new Random().nextInt())%1000];
            jedis.hgetAll(regId);
            long stop = System.currentTimeMillis();
            System.out.println(stop-start);
        }
        jedis.close();
    }
}
