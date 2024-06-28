package number;

public class ExceedHalfNum {

    public static void main(String[] args) {

        int array[] = {2, 1, 1, 3, 1, 1, 2, 2, 2, 2, 2, 2, 26};

        int times = 1;

        int targetNum = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] == targetNum) {
                times++;
            } else {
                times--;
            }

            if (times == 0) {
                targetNum = array[i];
                times = 1;
            }
        }

        System.out.println(targetNum);

    }

}
