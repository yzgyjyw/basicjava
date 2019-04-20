package com.jyw.basicjava.cache.store;

import com.jyw.basicjava.cache.store.bean.User;
import com.jyw.basicjava.cache.store.impl.WeakRefDataStore;
import org.junit.Test;

public class MyCacheTest {

    @Test
    public void testMyCache(){
        MyCache<String, User> userCahce = new MyCache<>(new WeakRefDataStore<>());

        User user1 = new User("jyw",25);
        User user2 = new User("lq",26);

        userCahce.put(user1.getName(),user1);
        userCahce.put(user2.getName(),user2);

        System.out.println("get from user cache:");
        System.out.println(userCahce.get("jyw"));
        System.out.println(userCahce.get("lq"));

        user1=null;
        System.gc();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after user1=null get from user cache:");
        System.out.println(userCahce.get("jyw"));
        System.out.println(userCahce.get("lq"));

        userCahce.remove("lq");
        System.out.println("after user1=null&&remove user2 get from user cache:");
        System.out.println(userCahce.get("jyw"));
        System.out.println(userCahce.get("lq"));
    }

}
