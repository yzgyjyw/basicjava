package number;

public class ExceedHalfNum {

    public static void main(String[] args) {

        int array[] = {1, 1, 1, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 26, 3};

        int targetNum = array[0];
        int times = 1;

        for (int i = 1; i < array.length; i++) {
            if (times == 0) {
                targetNum = array[i];
            }
            if (array[i] == targetNum) {
                times++;
            } else {
                times--;
            }

        }

        System.out.println(targetNum);

    }

}
