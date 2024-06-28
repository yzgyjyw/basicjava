package top100;

import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 *
 * 返回 滑动窗口中的最大值 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 * 示例 2：
 *
 * 输入：nums = [1], k = 1
 * 输出：[1]
 *
 * [1,-1]  1
 *
 * [7,2,4] 2
 */
public class SlidingWindowMaximum {
    public static void main(String[] args) {
        int[] ints = maxSlidingWindow(new int[]{7,2,4}, 2);
        System.out.println(Joiner.on(",").join(Arrays.stream(ints).iterator()));
    }
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];

        LinkedList<Integer> queue = new LinkedList<>();

        for(int i=0;i<nums.length;i++){
            while (!queue.isEmpty() && nums[queue.getLast()] <= nums[i]){
                queue.removeLast();
            }

            queue.addLast(i);

            while(i - queue.getFirst() + 1 > k){
                queue.removeFirst();
            }

            if(i>=k-1){
                result[i-(k-1)] = nums[queue.getFirst()];
            }
        }

        return result;
    }
}
