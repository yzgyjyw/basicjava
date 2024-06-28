package number;

import java.util.Arrays;

public class LongestUpperSequence {

    public static void main(String[] args) {
        int[] array = new int[]{4, 6, 5, 7, 3};


        int[] steps = new int[array.length];

        steps[0] = 1;

        for (int i = 1; i < array.length; i++) {
            int max = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] < array[i]) {
                    max = Math.max(max, steps[j]+1);
                }
            }
            steps[i] = max;
        }



        Arrays.stream(steps).forEach(System.out::println);








































        /*int[] steps = new int[array.length];

        steps[0] = 1;

        for (int i = 1; i < array.length; i++) {
            int beforeMax = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] < array[i]) {
                    beforeMax = Math.max(beforeMax, steps[j]);
                }
            }
            steps[i] = beforeMax + 1;
        }

        Arrays.stream(steps).forEach(System.out::println);*/
    }

}
