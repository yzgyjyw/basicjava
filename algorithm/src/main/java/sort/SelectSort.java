package sort;

import java.util.Arrays;

public class SelectSort {

    public static void main(String[] args) {

        int[] array = new int[]{1, 324, 453, 5, 4655, 4765, 76, 87686, 987};

        for (int i = 0; i < array.length; i++) {
            int min = i;
            for(int j=i+1;j<array.length;j++){
                if(array[min]> array[j]){
                    min = j;
                }
            }
            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;

        }

        Arrays.stream(array).forEach(System.out::println);
    }

}
