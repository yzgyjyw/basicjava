package volatil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileDemo {

    private  static Map<String, String> map = new ConcurrentHashMap<>();


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.submit(() -> {
                Map<String, String> newMap = new ConcurrentHashMap<>();
                for(int k=0;k<=finalI;k++){
                    newMap.put(String.valueOf(k), String.valueOf(System.currentTimeMillis()));
                }
                map = newMap;
                System.out.println(finalI + "\tnewMap 成功");
            });
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(i * i);
        }


        for (int i = 0; i < 10; i++) {
            System.out.println(i + "\t" + map.get(String.valueOf(i)));
        }


    }
}
