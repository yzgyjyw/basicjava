package top100;

public class LongestPalindromicSubstring {
    // babad  bab aba
    public static void main(String[] args) {
        String result = longestPalindrome("babad");

        System.out.println(result);
    }

    public static String longestPalindrome(String s){
        String result = null;
        int max = Integer.MIN_VALUE;
        for(int i=0;i<s.length();i++){
            String oddString = process(s,i,i);
            String evenString = process(s,i,i+1);

            String currentString  = oddString.length() > evenString.length() ? oddString : evenString;

            if(currentString.length() > max){
                result = currentString;
                max = currentString.length();
            }
        }
        return result;
    }


    // 考虑两种情况：
    // 1. l和r相等
    // 2. l+1=r
    public static String process(String s,int l,int r){
        int i = l;
        int j = r;

        while(i>=0 && j<=s.length()-1 && s.charAt(i)==s.charAt(j)){
            i--;
            j++;
        }

        // 包含i+1,但是不包含j
        return s.substring(i+1,j);
    }
}
