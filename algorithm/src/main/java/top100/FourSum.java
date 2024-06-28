package top100;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
 *
 * 0 <= a, b, c, d < n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * 示例 2：
 *
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 *
 */
public class FourSum {
    public static void main(String[] args) {
        List<List<Integer>> lists = fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0);
        lists.stream().forEach(integers -> System.out.println(Joiner.on(",").join(integers)));
    }
    public static List<List<Integer>> fourSum(int[] nums, int target) {
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

        if(current==0 && currentList.size()==4){
            result.add(new ArrayList<>(currentList));
            return;
        }

        if(currentList.size() >= 4){
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
