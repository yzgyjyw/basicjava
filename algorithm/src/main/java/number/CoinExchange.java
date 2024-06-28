package number;

import org.checkerframework.checker.units.qual.min;

import java.util.Arrays;

/**
 * 共有3种硬币,1,2,5 给定指定的数额,求最少需要多少枚硬币才能组成指定的数额
 */
public class CoinExchange {
    public static void main(String[] args) {
        calTotalCount();
//        calcMinCoins();
    }

    public static void calcMinCoins() {
        int[] coins = new int[]{1, 2, 5};

        int targetAmount = 11;

        int[] total = new int[12];

        total[0] = 1;

        for (int i = 1; i < total.length; i++) {
            int current = 0;
            for (int j = 0; j < coins.length; j++) {
                if (i == coins[j]) {
                    current += 1;
                } else if (i - coins[j] >= 0) {
                    current = total[i-coins[j]]+1;
                }
            }
            total[i] = current;
        }


        Arrays.stream(total).forEach(System.out::println);










/*
        int[] dp = new int[12];

        dp[0] = 0;


        for (int i = 1; i <= targetAmount; i++) {
            int currentMin = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i == coins[j]) {
                    currentMin = 1;
                    break;
                } else if (i - coins[j] > 0) {
                    currentMin = Math.min(currentMin, dp[i - coins[j]] + 1);
                }
            }
            dp[i] = currentMin;
        }

        Arrays.stream(dp).forEach(System.out::println);*/
    }

    public static void calTotalCount() {
        int[] coins = new int[]{1, 2, 5};
        int targetNum = 11;
        int[] dp = new int[targetNum + 1];
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = 1; j <= targetNum; j++) {
                if (j >= coins[i]) {
                    dp[j] = dp[j] + dp[j - coins[i]];
                }
            }
        }

        System.out.println(dp[targetNum]);
    }
}
