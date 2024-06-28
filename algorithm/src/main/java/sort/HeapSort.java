package sort;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987};

        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjstHeap(array, i, array.length - 1);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            adjstHeap(array, 0, i - 1);
        }

        Arrays.stream(array).forEach(System.out::println);
    }


    public static void adjstHeap(int[] array, int start, int end) {

        int temp = array[start];

        for (int i = start * 2 + 1; i < end; i = i * 2 + 1) {

            if (i + 1 <= end && array[i + 1] < array[i]) {
                i = i + 1;
            }

            if (array[i] < temp) {
                array[start] = array[i];
                start = i;
            }

        }

        array[start] = temp;
    }


}
