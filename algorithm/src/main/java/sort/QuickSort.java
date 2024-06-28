package sort;

import java.util.Arrays;

public class QuickSort {


    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987};

        quickSort(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }


    public static void quickSort(int array[], int start, int end) {
        if (start >= end) {
            return;
        }

        int low = start;
        int high = end;

        while (low < high) {
            while (array[high] < array[start] && high > low) {
                high--;
            }

            while (array[low] > array[start] && high > low) {
                low++;
            }

            if (high > low) {
                int temp = array[high];
                array[high] = array[low];
                array[low] = temp;
            }
        }

        int temp = array[start];

        array[start] = array[low];

        array[low] = temp;

        quickSort(array, start, low - 1);
        quickSort(array, low + 1, end);
    }
}
