package number;

public class 从1累加到n不允许使用乘除法以及条件判断语句 {

    public static void main(String[] args) {

        int n = 20;

        System.out.println(get(n));
    }

    public static int get(int n) {
        if (n == 1) {
            return 1;
        }

        return n + get(n - 1);
    }

}
