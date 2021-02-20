package number;

public class MaxSumContinusChildArray {

    public static void main(String[] args) {
        int array[] = new int[]{1, 2, -1, -1, 2, 4, 6};

        int sum = array[0];
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            sum = Math.max(array[i]+sum,array[i]);
            max = Math.max(sum,max);
        }

        System.out.println(max);
    }


}
