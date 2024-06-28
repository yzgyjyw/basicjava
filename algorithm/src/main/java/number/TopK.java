package number;

import java.util.Arrays;

public class TopK {

    public static void main(String[] args) {
        int[] array = new int[]{234, 32, 432, 423, 4, 23, 543, 534, 5, 34};

        int partition = partition(array, 0, array.length - 1);

        while (partition != 3) {

            if (partition < 3) {
                partition = partition(array, partition+1, array.length - 1);
            }

            if (partition > 3) {
                partition = partition(array, 0, partition-1);
            }

        }

        Arrays.stream(array).forEach(System.out::println);

    }

    public static int partition(int[] array, int start, int end) {
        if (start >= end) {
            return -1;
        }

        int low = start;
        int high = end;

        while (low < high) {

            while (high > low && array[high] < array[start]) {
                high--;
            }

            while (high > low && array[low] >= array[start]) {
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
