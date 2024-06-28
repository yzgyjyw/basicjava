package top100;

import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.Stack;

/**
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 *
 *
 *
 * 示例 1:
 *
 * 输入: temperatures = [73,74,75,71,69,72,76,73]
 * 输出: [1,1,4,2,1,1,0,0]
 * 示例 2:
 *
 * 输入: temperatures = [30,40,50,60]
 * 输出: [1,1,1,0]
 * 示例 3:
 *
 * 输入: temperatures = [30,60,90]
 * 输出: [1,1,0]
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        int[] result = dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});

        System.out.println(Joiner.on(",").join(Arrays.stream(result).iterator()));
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];

        Stack<Integer> stack = new Stack<>();

        for(int i = 0;i<temperatures.length;i++){
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]){
                result[stack.peek()] = i - stack.peek();
                stack.pop();
            }

            stack.push(i);
        }

        while(!stack.isEmpty()){
            result[stack.pop()] = 0;
        }

        return result;
    }

}
