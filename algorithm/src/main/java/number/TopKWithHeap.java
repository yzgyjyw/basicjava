package number;

import java.util.ArrayList;
import java.util.Arrays;

public class TopKWithHeap {

    public static void main(String[] args) {
       FirstNotRepeatingChar("NXWtnzyoHoBhUJaPauJaAitLWNMlkKwDYbbigdMMaYfkVPhGZcrEwp");

    }


    public static int FirstNotRepeatingChar(String str) {
        int[] hashArray = new int['z'-'A'];

        for(int i=0;i<str.length();i++){
            hashArray[str.charAt(i)-'A']++;
        }

        for(int i=0;i<str.length();i++){
            if(hashArray[str.charAt(i)-'A']==1){
                return i;
            }
        }

        return -1;
    }
}