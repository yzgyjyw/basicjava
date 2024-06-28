package top100;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.List;

/**
 *数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *  *
 *
 *
 * 示例 1：
 *
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 *
 * 输入：n = 1
 * 输出：["()"]
 */
public class GenerateParentheses {
    public static void main(String[] args) {
        List<String> strings = generateParenthesis(3);

        System.out.println(Joiner.on(",").join(strings));
    }

    // 设左括号为-1,右括号为1,不能出现大于0的情况
    // -1 -1 -1 1 1 1
    public static List<String> generateParenthesis(int n) {
        List<Integer> nums = new ArrayList<>();
        for(int i=0;i<n;i++){
            nums.add(-1);
        }

        for(int i=0;i<n;i++){
            nums.add(1);
        }

        List<Integer> currentList = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        boolean[] visited = new boolean[n*2];

        process(nums,currentList,result,visited);

        List<String> realResult = new ArrayList<>();
        for(List<Integer> list : result){
            StringBuffer sb = new StringBuffer();

            for(int i : list){
                sb.append(i<0 ? "(" : ")");
            }

            realResult.add(sb.toString());
        }

        return realResult;
    }

    public static void process(List<Integer> nums, List<Integer> currentList, List<List<Integer>> result, boolean[] visited){
        if(getSum(currentList) > 0 ){
            return;
        }

        if(currentList.size()==nums.size()){
            result.add(new ArrayList<>(currentList));
            return;
        }

        for(int i = 0;i<nums.size();i++){
            if(visited[i]) continue;
            if (i != 0 &&  i!= nums.size() / 2 && !visited[i - 1]) {
                continue;
            }

            visited[i] = true;
            currentList.add(nums.get(i));

            process(nums,currentList,result,visited);

            visited[i] = false;
            currentList.remove(currentList.size()-1);
        }
    }

    public static int getSum(List<Integer> list){
        int sum = 0;
        for(int i : list){
            sum+=i;
        }
        return sum;
    }
}
