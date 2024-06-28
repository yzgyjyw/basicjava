package com.jerry.flink.streaming;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AckStatisticsCacheByDevice {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AckStatisticsCacheByDevice.class);

    private static final String REDIS_KEY_SPLITTER = ":";

    private static final long KEY_MASK = 100000;

    private static int ONE_DAY_MS = 1000 * 3600 * 24;

    private static final Jedis jedis = new Jedis("127.0.0.1");

    private static String getTime() {
        return String.valueOf(System.currentTimeMillis() / (ONE_DAY_MS));
    }

    public static String getRecordByUserIdKey(Long userId) {
        return getTime() + REDIS_KEY_SPLITTER + userId % KEY_MASK;
    }

    public static String getRecordByUserIdSubKey(Long appId, Long userId) {
        return appId + REDIS_KEY_SPLITTER + userId / KEY_MASK;
    }

    private static String getAckedNumSubKey(String env) {
        return env + REDIS_KEY_SPLITTER + getTime();
    }

    /**
     * 记录该设备当天收到该app的消息
     *
     * @param appId
     * @param userId
     */
    public static void recordByUserId(Long appId, Long userId) {
        jedis.hset(getRecordByUserIdKey(userId), getRecordByUserIdSubKey(appId, userId), "1");
    }

    /**
     * 记录该设备当天收到该app的消息
     *
     * @param appId
     * @param userId
     */
    public static void recordByUserId(String key, Map<String,String> params) {
        jedis.hmset(key,params);
    }

    /**
     * 判断该设备当日是否收到过消息
     *
     * @param appId
     * @param userId
     * @return
     */
    public static boolean isUserIdAcked(long appId, Long userId) {
        return "1".equals(jedis.hget(getRecordByUserIdKey(userId), getRecordByUserIdSubKey(appId, userId)));
    }


    /**
     * 获取app当天在海外所有机房下发的消息的设备总数
     */
    public static int getAckedDeviceNumByEnv(Long appId, String env) {
        return NumberUtils.strToInt(jedis.hget(String.valueOf(appId), getAckedNumSubKey(env)));
    }

    /**
     * 获取app在各个机房下发消息的设备数
     */
    public static Map<String, Integer> getAckedDeviceNumByEnvs(Long appId, List<String> envs) {
        Map<String, Integer> result = new HashMap<>();

        String[] subkeys = new String[envs.size()];
        envs.stream().map(AckStatisticsCacheByDevice::getAckedNumSubKey).collect(Collectors.toList()).toArray(subkeys);
        List<String> list = jedis.hmget(String.valueOf(appId), subkeys);
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }

        for (int i = 0; i < envs.size(); i++) {
            result.put(envs.get(i), NumberUtils.strToInt(list.get(i)));
        }

        return result;
    }


    /**
     * 获取app当天在海外机房下发的消息的设备总数
     */
    public static void recordAckedDeviceNum(Long appId, int value, String env) {
        jedis.hset(String.valueOf(appId), getAckedNumSubKey(env), String.valueOf(value));
    }

    /**
     * 记录本机房当天收到ack的量
     */
    public static void incrAckedDeviceNum(Long appId, int value, String env) {
        jedis.hincrBy(String.valueOf(appId), getAckedNumSubKey(env), value);
    }

}
