package top100;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 *
 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
 *
 *
 *
 * 示例 1:
 *
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 *  示例 2:
 *
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 */
public class FindAllAnagramsInAString {
    public static void main(String[] args) {
        List<Integer> anagrams = findAnagrams("aaaaaaaaaa", "aaaaaaaaaaaaa");
        System.out.println(Joiner.on(",").join(anagrams.iterator()));
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();

        if(s.length() < p.length()){
            return result;
        }

        int[] target = new int[26];

        int[] current = new int[26];
        for(int i=0;i<p.length();i++){
            target[p.charAt(i)-'a']++;
            current[s.charAt(i)-'a']++;
        }


        for(int i=p.length();i<s.length();i++){
            if(Arrays.equals(current,target)){
                result.add(i - p.length());
            }

            current[s.charAt(i-p.length())-'a']--;

            current[s.charAt(i)-'a']++;
        }

        if(Arrays.equals(current,target)){
            result.add(s.length() - p.length());
        }
        return result;
    }
}
