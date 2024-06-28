package top100;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 *
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 *
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 *
 *
 *
 * 示例 1：
 *
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 * 解释：
 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
 * 7 也是一个候选， 7 = 7 。
 * 仅有这两种组合。
 * 示例 2：
 *
 * 输入: candidates = [2,3,5], target = 8
 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
 * 示例 3：
 *
 * 输入: candidates = [2], target = 1
 * 输出: []
 */
public class CombinationSum {
    private static List<List<Integer>> result = new ArrayList<>();

    public static void main(String[] args) {
        List<List<Integer>> lists = combinationSum(new int[]{2, 3, 6, 7}, 7);

        lists.stream().forEach(integers -> System.out.println(Joiner.on(",").join(integers)));
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        process(candidates,target,0,0,new ArrayList<>());
        return result;
    }

    /**
     *
     * @param candidates 元素集合
     * @param target     目标值
     * @param current    当前值
     * @param idx        当前索引
     * @param currentList 当前收集到的元素集合
     */
    public static void process(int[] candidates, int target, int current, int idx, List<Integer> currentList){
        if(current > target){
            return;
        }

        if(current == target){
            result.add(new ArrayList<>(currentList));
        }

        for(int i = idx;i<candidates.length;i++){
            currentList.add(candidates[i]);

            process(candidates,target,current+candidates[i],i,currentList);

            currentList.remove(currentList.size()-1);
        }
    }
}
