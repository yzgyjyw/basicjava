package number;

public class MaxSumContinusChildArray {

    public static void main(String[] args) {
        int array[] = new int[]{1, 2, -1, -1, 2, 4, 6};

        int currentSum = array[0];
        int maxSum = array[0];
        for (int i = 1; i < array.length; i++) {
            if (currentSum  > 0) {
                currentSum += array[i];
            }else{
                currentSum = array[i];
            }

            maxSum = Math.max(currentSum, maxSum);
        }

        System.out.println(maxSum);
    }
}
