package number;

import java.util.Arrays;

public class TopKWithHeap {

    public static void main(String[] args) {
        int[] array = new int[]{234, 32, 432, 423, 4, 23, 543, 534, 5, 34};

        int k = 3;

        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length - 1);
        }

        for (int i = 0; i < k; i++) {
            int temp = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[0];
            array[0] = temp;
            adjustHeap(array, 0, array.length - 1 - i - 1);
        }

        Arrays.stream(array).forEach(System.out::println);

    }

    private static void adjustHeap(int[] array, int start, int end) {

        int temp = array[start];

        for(int i= start*2+1;i<=end;i++){

            if(i+1<=end && array[i+1]>array[i]){
                i=i+1;
            }

            if(array[i] <= temp){
                break;
            }

            array[start] = array[i];
            start = i;
        }

        array[start] = temp;

    }


}