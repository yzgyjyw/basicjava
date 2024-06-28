package top100;

import com.sun.imageio.plugins.common.I18N;

import javax.jnlp.IntegrationService;

/**
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 *
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 */
public class ReverseInteger {
    public static void main(String[] args) {
        // 2147483647
        System.out.println(Integer.MAX_VALUE);
        // -2147483648
        System.out.println(Integer.MIN_VALUE);

        int reverse = reverse(1463847413);

        System.out.println(reverse);
    }

    public static int reverse(int x) {
        int result = 0;

        int abs = x;

        while(abs!=0){
            int current = abs % 10;

            abs = abs / 10;

            // 讲道理这边应该是 result>214748364 && current <=7 || result<-214748364 && current <= 8;
            // 但是由于传入的x是个int类型的数字,最后一位一定是1和2中的一位
            if(result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10){
                return 0;
            }

            result = result * 10 + current;
        }

        return result;
    }
}
