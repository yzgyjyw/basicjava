package number;

import java.util.Arrays;

/**
 * 奇数偶数分开
 */
public class ArraySequenceAdjust {
    public static void main(String[] args) {
        int[] array = new int[]{2,1, 2, 3, 4, 5, 65, 645, 5675, 765,8,10};

        int low = 0;
        int high = array.length - 1;

        while (low < high) {
            if (low < high && array[high] % 2 == 0) {
                high--;
            }

            if (low < high && array[low] % 2 != 0) {
                low++;
            }

            if (low < high) {
                int temp = array[low];
                array[low] = array[high];
                array[high] = temp;
            }
        }

        Arrays.stream(array).forEach(System.out::println);

    }
}
