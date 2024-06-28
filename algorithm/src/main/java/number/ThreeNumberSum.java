package number;

import java.util.Arrays;

public class ThreeNumberSum {

    public static void main(String[] args) {
        int[] array = new int[]{-1, 0, 1, 2, -1, -4};

        int targetNum = 0;

        Arrays.sort(array);

        for (int i = 1; i < array.length; i++) {
            int left = i - 1;
            int right = array.length - 1;

            while (left < right && left < i && right > i) {
                int currentNum = array[left] + array[right] + array[i];
                if (currentNum == targetNum) {
                    System.out.println(array[left] + "," + array[i] + "," + array[right]);
                    left++;
                    right--;
                } else if (currentNum < targetNum && right > i) {
                    left++;
                } else if (left < i) {
                    right--;
                }else{
                    break;
                }
            }
        }
    }

}
