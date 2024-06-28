package top100;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的每个数字在每个组合中只能使用 一次 。
 *
 * 注意：解集不能包含重复的组合。
 *
 *
 *
 * 示例 1:
 *
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 输出:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 * 示例 2:
 *
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 输出:
 * [
 * [1,2,2],
 * [5]
 * ]
 */
public class CombinationSumII {
    public static void main(String[] args) {
        combinationSum2(new int[]{2,5,2,1,2},5);
    }
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[candidates.length];
        process(candidates,target,0,new ArrayList<>(),result,0,visited);
        result.stream().forEach(integers -> System.out.println(Joiner.on(",").join(integers)));
        return result;
    }

    public static void process(int[] candidates, int target, int current, List<Integer> currentList, List<List<Integer>> result, int idx, boolean[] visited) {
        if(current>target){
            return;
        }

        if(current==target){
            result.add(new ArrayList<>(currentList));
        }

        for(int i=idx;i<candidates.length;i++){
            if(i>0 && !visited[i-1] && candidates[i]==candidates[i-1]) continue;
            visited[i] = true;
            currentList.add(candidates[i]);

            process(candidates,target,current+candidates[i],currentList,result,i+1,visited);

            currentList.remove(currentList.size()-1);
            visited[i] = false;
        }
    }
}
