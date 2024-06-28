import java.util.HashMap;
import java.util.Map;

public class MaxUnRepeatbleStr {

    public static void main(String[] args) {
        System.out.println(getMaxUnRepeatbleStr("abcadefatayuio"));
    }


    public static String getMaxUnRepeatbleStr(String str) {
        int maxUnRepeat = 1;
        String result = str.substring(0,1);
        Map<Character,Integer> c2Position = new HashMap<Character,Integer>();
        c2Position.put(str.charAt(0),0);

        int[] array = new int[str.length()];
        array[0] = 1;

        for(int i=1;i<str.length();i++){
            array[i] = Math.min(array[i-1]+1, i- c2Position.getOrDefault(str.charAt(i),-1));

            c2Position.put(str.charAt(i),i);

            if(array[i] > maxUnRepeat){
                maxUnRepeat = array[i];
                result = str.substring(i+1-maxUnRepeat,i+1);
            }
        }

        return result;
    }
}
