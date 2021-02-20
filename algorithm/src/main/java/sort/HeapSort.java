package sort;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987};


        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(i, array.length - 1, array);
        }

        for (int i = array.length - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            adjustHeap(0, i - 1, array);
        }


        Arrays.stream(array).forEach(System.out::println);
    }

    public static void adjustHeap(int position, int endIndex, int[] array) {

        int temp = array[position];

        for (int i = position * 2 + 1; i <= endIndex; i = i * 2 + 1) {
            if (i + 1 <= endIndex && array[i + 1] < array[i]) {
                i = i + 1;
            }

            if (temp <= array[i]) {
                break;
            }

            array[position] = array[i];
            position = i;
        }

        array[position] = temp;
    }


}
