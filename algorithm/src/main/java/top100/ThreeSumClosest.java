package top100;

import java.util.Arrays;

/**
 * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
 * 返回这三个数的和。
 * 假定每组输入只存在恰好一个解。
 *
 *
 * 示例 1：
 *
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 * 示例 2：
 *
 * 输入：nums = [0,0,0], target = 1
 * 输出：0
 *
 */
public class ThreeSumClosest {
    public static void main(String[] args) {
        int sumClosest = threeSumClosest(new int[]{0,0,0}, 1);
        System.out.println(sumClosest);
    }

    public static int threeSumClosest(int[] nums, int target) {
        int result = 0;
        int minDiff = Integer.MAX_VALUE;

        Arrays.sort(nums);

        for(int i = 0;i < nums.length;i++){
            int currentTarget = target - nums[i];
            int left = i+1;
            int right = nums.length - 1;

            while (left < right){
                if(nums[left] + nums[right] == currentTarget){
                    return target;
                }

                if(minDiff > Math.abs(nums[left] + nums[right] - currentTarget)){
                    minDiff =  Math.abs(nums[left] + nums[right] - currentTarget);
                    result = nums[left] + nums[right] + nums[i];
                }

                if(nums[left] + nums[right] < currentTarget){
                    left++;
                }else{
                    right--;
                }
            }
        }

        return result;
    }
}
