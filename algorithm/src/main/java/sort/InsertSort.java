package sort;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987};

        for (int i = 1; i < array.length; i++) {

            int temp = array[i];
            int j = i;

            while (j > 0 && temp > array[j - 1]) {
                j--;
            }

            for (int k = i; k > j; k--) {
                array[k] = array[k - 1];
            }

            array[j] = temp;
        }

        Arrays.stream(array).forEach(System.out::println);
    }

}
