package top100;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingCharacters {
    // abcabcbb 3
    public static void main(String[] args) {
        int i = lengthOfLongestSubstring("abcabcbb");
        System.out.println(i);
    }

    /**
     * 注意: 这样写必须要保证所有的字符都是小写字母
     */
    public static int lengthOfLongestSubstring(String s) {
        if(s==null || s.length() == 0){
            return 0;
        }

        int result = 1;
        int[] array = new int[s.length()];
        int[] character2Post = new int[26];
        Arrays.fill(character2Post,-1);

        array[0] = 1;
        character2Post[s.charAt(0)-'a'] = 0;

        for(int i=1; i < s.length(); i++){
            array[i] = Math.min(array[i-1]+1,i - character2Post[s.charAt(i)-'a']);
            result = Math.max(result,array[i]);
            character2Post[s.charAt(i)-'a'] = i;
        }

        return result;
    }

    /**
     * 注意: 这样写必须要保证所有的字符都是小写字母
     */
    public static int lengthOfLongestSubstringV2(String s) {
        if(s==null || s.length() == 0){
            return 0;
        }

        Map<Character,Integer> character2Position = new HashMap<>();

        int max = 1;
        int[] array = new int[s.length()];
        character2Position.put(s.charAt(0),0);
        array[0] = 1;

        for(int i=1;i<s.length();i++){
            int current = Math.min(array[i-1]+1, i-character2Position.getOrDefault(s.charAt(i),-1));
            max = Math.max(max,current);
            array[i] = current;
            character2Position.put(s.charAt(i),i);
        }

        return max;
    }
}
