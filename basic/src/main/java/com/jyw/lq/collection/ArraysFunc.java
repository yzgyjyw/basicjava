package com.jyw.lq.collection;
// 10 30 60
public class ArraysFunc {
    public static void main(String[] args) {
        System.out.println(Math.sqrt(4.0));
        long firstLoginTs = System.currentTimeMillis() - 60*1000*61;
        long fetchRequestTs = System.currentTimeMillis() - 150 * 60 * 1000;
//        //ping中第N次应该发送拉取离线消息的时间.每一次的间隔时间以i*10000递增
//        int i = 1;
//        //ping中第N次应该发送拉取离线消息的时间.每一次的间隔时间以i*10000递增
//        long timeToSendFetchN = firstLoginTs + i * 10 * 60 * 1000;
//        while (timeToSendFetchN < System.currentTimeMillis()) {
//            i++;
//            timeToSendFetchN += i * 10 * 60 * 1000;
//        }
//
//        // 第i-1次真实发送的时间小于理应发送的时间，补发第i-1次的拉取
//        System.out.println(fetchRequestTs <= System.currentTimeMillis() - i * 10 * 60 * 1000);
//
//        System.out.println(i);
//
//        System.out.println(Boolean.TRUE.toString().equals(null));

        int index = 1;
//        int index = getIndex(firstLoginTs);
        int index2 = getIndex2(firstLoginTs);
        System.out.println(index + "\t" + index2);
    }


    public static int getIndex(long firstLoginTs) {
        int i = 1;
        long timeToSendFetchN = firstLoginTs + i * 10 * 60 * 1000;
        while (timeToSendFetchN < System.currentTimeMillis()) {
            i++;
            timeToSendFetchN += i * 10 * 60 * 1000;
        }
        return i;
    }

    public static int getIndex2(long firstLoginTs) {
        System.out.println(Math.sqrt((System.currentTimeMillis() - firstLoginTs) * 2.0 / (10 * 60 * 1000) + 0.25) - 0.5);
        int index = (int) Math.ceil(Math.sqrt((System.currentTimeMillis() - firstLoginTs) * 2.0 / (10 * 60 * 1000) + 0.25) - 0.5);
        return index;
    }


}
