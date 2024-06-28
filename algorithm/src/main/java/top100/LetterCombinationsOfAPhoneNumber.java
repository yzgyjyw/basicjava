package top100;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * <p>
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 示例 2：
 * <p>
 * 输入：digits = ""
 * 输出：[]
 * 示例 3：
 * <p>
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 */
public class LetterCombinationsOfAPhoneNumber {
    public static void main(String[] args) {
        List<String> strings = letterCombinations("23");

        System.out.println(Joiner.on(",").join(strings));
    }

    public static List<String> letterCombinations(String digits) {
        Map<Character, String> map = new HashMap<>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");

        StringBuffer sb = new StringBuffer();
        List<String> result = new ArrayList<>();

        if(digits.length()==0 || digits==null){
            return result;
        }

        process(digits, sb, result, 0, map);

        return result;
    }

    public static void process(String digits,StringBuffer sb,List<String> result,int idx,Map<Character,String> map){
        if(idx == digits.length()){
            result.add(sb.toString());
            return;
        }

        String currentString = map.get(digits.charAt(idx));

        for(int i = 0;i<currentString.length();i++){
            sb.append(currentString.charAt(i));
            process(digits,sb,result,idx+1,map);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}
