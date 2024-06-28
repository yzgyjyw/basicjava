package number;

import java.util.Arrays;

public class TopK {

    public static void main(String[] args) {
        int[] array = new int[]{234, 32, 432, 423, 4, 23, 543, 534, 5, 34};

        int k = 2;

        int start = 0;
        int end = array.length - 1;

        int partition = partition(array, start, end);

        while (partition != k) {

            if (partition < k) {
                start = partition + 1;
            }

            if (partition > k) {
                end = partition - 1;
            }

            partition = partition(array, start, end);
        }

        Arrays.stream(array).forEach(System.out::println);
    }

    public static int partition(int[] array, int start, int end) {

        if (start >= end) return -1;

        int i = start;
        int j = end;

        while (i < j) {
            while (j > i && array[j] < array[start]) {
                j--;
            }

            while (j > i && array[i] > array[start]) {
                i++;
            }

            if (j > i) {
                int temp = array[j];
                array[j] = array[i];
                array[i] = temp;
            }
        }

        int temp = array[0];
        array[0] = array[i];
        array[i] = temp;

        return i;
    }

}
