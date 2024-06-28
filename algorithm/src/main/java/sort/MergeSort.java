package sort;

import com.google.common.base.Joiner;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686,312};

        int[] result = new int[9];

        mergeSort(array, 0, array.length - 1,result);

        Arrays.stream(result).forEach(System.out::println);
    }


    public static void mergeSort(int[] array, int start, int end, int[] result) {
        if (start >= end) {
            return;
        }


        int start1 = start;
        int end1 = (start+end)/2;

        int start2 = end1 + 1;
        int end2 = end;

        mergeSort(array, start1, end1, result);
        mergeSort(array, start2, end2, result);

        int k = start;

        while (start1 <= end1 && start2 <= end2) {
            if (array[start1] <= array[start2]) {
                result[k] = array[start1];
                start1++;
                k++;
            } else {
                result[k] = array[start2];
                start2++;
                k++;
            }
        }

        while (start1 <= end1) {
            result[k] = array[start1];
            k++;
            start1++;
        }

        while (start2 <= end2) {
            result[k] = array[start2];
            k++;
            start2++;
        }

        for (k = start; k <= end; k++){
            array[k] = result[k];
        }
    }

}
