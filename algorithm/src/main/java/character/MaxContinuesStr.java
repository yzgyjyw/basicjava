package character;

import java.util.ArrayList;
import java.util.List;

public class MaxContinuesStr {

    public static void main(String[] args) {
        String strs = "ABCABCDBEBCDEF";
        int i = 0;
        int j = 0;


        int maxLen = 0;
        String result = "";
        String current = "";

        for (; j < strs.length(); j++) {

            int index = current.indexOf(strs.charAt(j));

            if (index == -1) {
                current += strs.charAt(j);
                if(j==strs.length()-1){
                    if(current.length()>result.length()){
                        result = current;
                        maxLen = result.length();
                    }
                }
            } else {
                // 说明找到了重复的
                if (j - i > maxLen) {
                    maxLen = j - i;
                    result = current;
                    current = strs.substring(i+1, j+1);
                    i = strs.indexOf(strs.charAt(j), i) + 1;
                }else{
                    current = strs.substring(i+1, j+1);
                    i = strs.indexOf(strs.charAt(j), i) + 1;
                }
            }
        }

        System.out.println(maxLen);
        System.out.println(result);
    }

}
