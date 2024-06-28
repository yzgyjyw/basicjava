package top100;

/**
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 子数组 是数组中的一个连续部分。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * 示例 2：
 *
 * 输入：nums = [1]
 * 输出：1
 * 示例 3：
 *
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 */
public class MaximumSubarray {
    public static void main(String[] args) {
        int maxSubArray = maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});

        System.out.println(maxSubArray);
    }

    public static int maxSubArray(int[] nums) {
        int result = nums[0];

        int[] array = new int[nums.length];
        array[0] = nums[0];

        for(int i=1;i<nums.length;i++){
            array[i] = array[i-1] < 0 ? nums[i] : array[i-1]+nums[i];

            result = Math.max(array[i],result);
        }

        return result;
    }
}
