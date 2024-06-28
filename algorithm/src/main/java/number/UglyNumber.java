package number;

import java.util.ArrayList;
import java.util.List;

public class UglyNumber {
    public static void main(String[] args) {

        int n = 1400;


        List<Integer> ugluLists = new ArrayList<>();
        ugluLists.add(1);

        int chenTwo = 0;
        int chenThree = 0;
        int chenFive = 0;

        while (ugluLists.size() < n) {
            Integer currMaxUglyNumber = ugluLists.get(ugluLists.size() - 1);

            for (int i = 0; i < ugluLists.size(); i++) {
                if (currMaxUglyNumber < (chenTwo = ugluLists.get(i) * 2)) {
                    break;
                }
            }

            for (int i = 0; i < ugluLists.size(); i++) {
                if (currMaxUglyNumber < (chenThree = ugluLists.get(i) * 3)) {
                    break;
                }
            }


            for (int i = 0; i < ugluLists.size(); i++) {
                if (currMaxUglyNumber < (chenFive = ugluLists.get(i) * 5)) {
                    break;
                }
            }

            ugluLists.add(Math.min(chenTwo, Math.min(chenThree, chenFive)));
        }


        System.out.println(ugluLists.get(n-1));

    }


}
