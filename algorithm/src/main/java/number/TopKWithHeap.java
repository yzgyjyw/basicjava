package number;

import java.util.Arrays;

public class TopKWithHeap {

    public static void main(String[] args) {
        int[] array = new int[]{234, 32, 432, 423, 4, 23, 543, 534, 5, 34};

        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length);
        }


        for (int i = 0; i < 3; i++) {
            int temp = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[0];
            array[0] = temp;
            adjustHeap(array, 0, array.length - 1 - i);
        }

        Arrays.stream(array).forEach(System.out::println);
    }


    public static void adjustHeap(int[] array, int start, int length) {

        int temp = array[start];

        for (int i = start * 2 + 1; i < length; i = i * 2 + 1) {
            if (i + 1 < length && array[i + 1] > array[i]) {
                i = i + 1;
            }

            if (array[i] > temp) {
                array[start] = array[i];
                start = i;
            }
        }

        array[start] = temp;
    }
}