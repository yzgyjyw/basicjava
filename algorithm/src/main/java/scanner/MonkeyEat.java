package scanner;

public class MonkeyEat {
    public static void main(String[] args) {
        int total = 1;
        for (int i = 0; i < 9; i++) {
            total = (total + 1) * 2 ;
        }
        System.out.println(total);
    }
}