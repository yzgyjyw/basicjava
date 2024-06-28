package top100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
 *
 * 你返回所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 *
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 * 示例 2：
 *
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0 。
 * 示例 3：
 *
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0 。
 */
public class ThreeSum {
    public static void main(String[] args) {
        List<List<Integer>> lists = threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        System.out.println(1);
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<Integer> currentList = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        Arrays.sort(nums);
        process(nums,0,0,currentList,visited,result);
        return result;
    }

    public static void process(int[] nums, int current, int idx, List<Integer> currentList, boolean[] visited,  List<List<Integer>> result){
        if(current>0){
            return;
        }

        if(current==0 && currentList.size()==3){
            result.add(new ArrayList<>(currentList));
            return;
        }

        if(currentList.size() >= 3){
            return;
        }

        for(int i= idx;i<nums.length;i++){
            if(i>0 && !visited[i-1] && nums[i-1]==nums[i]) continue;
            visited[i] = true;
            currentList.add(nums[i]);
            process(nums,current+nums[i],i+1,currentList,visited,result);
            currentList.remove(currentList.size()-1);
            visited[i] = false;
        }
    }
}
