package character;

import java.util.HashMap;

public class MaxContinuesStr2 {

    public static void main(String[] args) {

        String s = "ABCABCDBCDEF";

        int curLength = 0; // 记录以当前字符的上一个字符为结尾的最长不重复子字符串
        int maxLength = 0; // 记录最长不重复子串
        HashMap<Character, Integer> stringIndex = new HashMap<>(); // 字符出现的下标

        for (int i = 0; i < s.length(); i++) {
            if (stringIndex.containsKey(s.charAt(i))) {
                Integer integer = i - stringIndex.get(s.charAt(i));
                if (integer > curLength) {
                    curLength++;
                } else {
                    curLength = integer;
                }
            } else {
                curLength++;
            }

            stringIndex.put(s.charAt(i), i);
            maxLength = Math.max(curLength, maxLength);
        }

        System.out.println(maxLength);
    }
}

/*    String strs = "ABCABCDBCDEF";
    int i = 0;
    int j = 0;


    int maxLen = 0;
    String result = "";
    String current = "";

    HashMap<Character, Integer> map = new HashMap<>();

        for (; j < strs.length(); j++) {
        Integer index = map.get(strs.charAt(j));

        if (index == null) {
        current += strs.charAt(j);
        if (j == strs.length() - 1 && current.length() > strs.length()) {
        result = current;
        }
        } else {

        int diff = j - index;

        if (diff > current.length()) {
        current += strs.charAt(j);
        } else {
        current += strs.charAt(j);
        //DABA
        current = current.substring(current.length() - diff);
        }
        }

        if(result.length()<current.length()){
        result = current;
        }

        map.put(strs.charAt(j), j);
        }

        System.out.println(result);*/
