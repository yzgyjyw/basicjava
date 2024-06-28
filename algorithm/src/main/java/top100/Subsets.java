package top100;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 *
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 * 示例 2：
 *
 * 输入：nums = [0]
 * 输出：[[],[0]]
 */
public class Subsets {
    public static void main(String[] args) {
        List<List<Integer>> subsets = subsets(new int[]{1, 2, 3});

        System.out.println(1);
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<Integer> currentList = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        process(nums,currentList,result,0);
        return result;
    }

    public static void process(int[] nums, List<Integer> currentList, List<List<Integer>> result, int idx){
        result.add(new ArrayList<>(currentList));

        for(int i = idx;i<nums.length;i++){
            currentList.add(nums[i]);
            process(nums,currentList,result,i+1);
            currentList.remove(currentList.size()-1);
        }
    }
}
