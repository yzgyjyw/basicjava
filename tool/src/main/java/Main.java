import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*int[] time = new int[]{0,1,1,3,3 };
        int[] direction = new int[]{0,1,0,0,1};

        int[] times = getTimes(time, direction);

        System.out.println(Arrays.toString(times));*/

        System.out.println((int)Math.round(1.47));

    }

    private static int[] getTimes(int[] time, int[] direction){
        int[] result = new int[time.length];
        // direction[i]==0 进入  direction[i]==1 出去
        // 1. 切分time与direction
        List<Integer> inQueue = new ArrayList<>();
        List<Integer> outQueue = new ArrayList<>();

        // 2. 采用数组记录index对应关系
        int[] inQueueIndex = new int[time.length];
        int[] outQueueIndex = new int[time.length];

        int inStart = 0;
        int outStart = 0;
        for(int i=0;i<direction.length;i++){
            if(direction[i]==0){
                inQueue.add(time[i]);
                inQueueIndex[inStart++] = i;
            }else if(direction[i]==1){
                outQueue.add(time[i]);
                outQueueIndex[outStart++] = i;
            }
        }

        // 前一秒的状态,0 进入  1 出去  null 无状态
        Integer lastStatus = null;

        // inQueue指针
        int i = 0;
        // outQueue指针
        int j = 0;

        int currentTime = 0;

        while(i<inQueue.size() && j<outQueue.size()){
            Integer inTime = inQueue.get(i);
            Integer outTime = outQueue.get(j);

            // 两个人都需要走
            if(inTime<=currentTime && outTime <= currentTime){
                if(lastStatus==null){
                    result[outQueueIndex[j]] = currentTime;
                    // 当前人花费的时间
                    lastStatus=1;
                    currentTime++;
                    j++;
                }else if(lastStatus==0){
                    result[inQueueIndex[i]] = currentTime;
                    // 当前人花费的时间
                    lastStatus=0;
                    currentTime++;
                    i++;
                } else if(lastStatus==1){
                    result[outQueueIndex[j]] = currentTime;
                    // 当前人花费的时间
                    lastStatus=1;
                    currentTime++;
                    j++;
                }
            }else if(inTime<=currentTime){
                result[inQueueIndex[i]] = currentTime;
                // 当前人花费的时间
                lastStatus=0;
                currentTime++;
                i++;
            }else if(outTime<=currentTime){
                result[outQueueIndex[j]] = currentTime;
                // 当前人花费的时间
                lastStatus=1;
                currentTime++;
                j++;
            }else {
                currentTime++;
                lastStatus=null;
            }
        }

        while(i<inQueue.size()){
            Integer inTime = inQueue.get(i);
            if(inTime<=currentTime){
                result[inQueueIndex[i]]=currentTime;
                i++;
            }
            currentTime++;
        }

        while(j<outQueue.size()){
            Integer outTime = outQueue.get(j);
            if(outTime<=currentTime){
                result[outQueueIndex[j]]=currentTime;
                j++;
            }
            currentTime++;
        }

        return result;
    }
}
