package number;

public class NumerPow {
    public static void main(String[] args) {
        System.out.println(power(2, 10));
    }

    public static int power(int base, int expon) {
        int result = 0;

        if (expon == 1) {
            return base;
        }

        if (expon == 0) {
            return 1;
        }

        result = power(base, expon / 2) * power(base, expon / 2);

        if (expon % 2 != 0) {
            result = result * base;
        }

        return result;
    }
}
