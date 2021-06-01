package sort;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987,-1};

        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(i, array.length - 1, array);
        }

        for (int i = 0; i < array.length; i++) {
            int temp = array[0];
            array[0] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;

            adjustHeap(0, array.length - 1 - i - 1, array);
        }


        Arrays.stream(array).forEach(System.out::println);

    }

    public static void adjustHeap(int start, int end, int[] array) {

        int temp = array[start];

        for (int i = start * 2 + 1; i <= end; i = i * 2 + 1) {

            if (i + 1 <= end && array[i + 1] < array[i]) {
                i++;
            }

            if (array[i] > temp) {
                break;
            }

            array[start] = array[i];
            start = i;

        }

        array[start] = temp;

    }

}
