package sort;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987};

        quickSort(0, array.length - 1, array);

        Arrays.stream(array).forEach(System.out::println);
    }

    public static void quickSort(int start, int end, int[] array) {

        if (start > end) {
            return;
        }

        int low = start;
        int high = end;

        while (low < high) {

            if (low < high && array[high] > array[start]) {
                high--;
            }

            if (low < high && array[low] < array[start]) {
                low++;
            }

            if (low < high) {
                int temp = array[low];
                array[low] = array[high];
                array[high] = temp;
            }

        }

        int temp = array[start];
        array[start] = array[low];
        array[low] = temp;

        quickSort(start, low - 1, array);
        quickSort(low + 1, end, array);
    }
}
