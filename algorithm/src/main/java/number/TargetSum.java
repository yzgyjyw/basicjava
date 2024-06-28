package number;

public class TargetSum {

    public static void main(String[] args) {
        int[] array = new int[]{1, 5, 1, 11};

        int targetNum = 17;

        boolean[] dp = new boolean[targetNum + 1];

        for (int i = 0; i < array.length; i++) {
            for (int j = targetNum; j >= array[i]; j--) {
                if (j == array[i]) {
                    dp[j] = true;
                } else {
                    dp[j] = dp[j - array[i]] || dp[j];
                }
            }
        }

        System.out.println(dp[targetNum]);

    }

}
