package sort;

import java.util.Arrays;

public class QuickSortWithoutDigui {

    public static void main(String[] args) {
        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987};

        int low = 0;
        int high = array.length - 1;

        Arrays.stream(array).forEach(System.out::println);
    }

    public static int quickSort(int start, int end, int[] array) {

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

        return low;
    }
}
