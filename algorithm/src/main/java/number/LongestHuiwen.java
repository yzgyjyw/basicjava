package number;

public class LongestHuiwen {

    public static void main(String[] args) {
        String str = "abcdedcbr";

        int[][] dp = new int[str.length()][str.length()];

        /**
         * i ---> 8 dp[8][8]-->1
         * i---> 7  dp[7][7]--->1
         * i----> 6 dp[6][6] ---> 1
         * i----> 5
         *
         *
         */


        for (int i = str.length() - 1; i >= 0; i--) {
            dp[i][i] = 1;

            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }

        }

        System.out.println(dp[0][str.length() - 1]);

//        for (int i = str.length() - 1; i >= 0; i--) {
//            dp[i][i] = 1;
//            for (int j = i + 1; j < str.length(); j++) {
//                if (str.charAt(i) == str.charAt(j)) {
//                    dp[i][j] = dp[i + 1][j - 1] + 2;
//                } else {
//                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
//                }
//            }
//        }
//
//        System.out.println(dp[0][str.length() - 1]);

    }

}
