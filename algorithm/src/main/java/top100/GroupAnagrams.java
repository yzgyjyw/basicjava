package top100;

import com.google.common.base.Joiner;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 *
 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
 *
 *
 *
 * 示例 1:
 *
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 示例 2:
 *
 * 输入: strs = [""]
 * 输出: [[""]]
 * 示例 3:
 *
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 */
public class GroupAnagrams {
    public static void main(String[] args) {
        List<List<String>> lists = groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});

        lists.stream().forEach(strs-> System.out.println(Joiner.on(",").join(strs)));
    }
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();

        for(String str : strs){
            char[] arrays = str.toCharArray();
            Arrays.sort(arrays);
            String key = new String(arrays);
            map.putIfAbsent(key,new ArrayList<>());

            map.get(key).add(str);
        }

        return map.entrySet().stream().map(Entry::getValue).collect(Collectors.toList());
    }
}
