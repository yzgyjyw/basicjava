package sort;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, -1, 4655, 4765, 76, 87686, 987};

        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > current) {
                j--;
            }

            for (int k = i; k > j + 1; k--) {
                array[k] = array[k - 1];
            }

            array[j + 1] = current;
        }


        Arrays.stream(array).forEach(System.out::println);
    }

}
