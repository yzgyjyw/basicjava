package number;

public class OccurOnece {

    public static void main(String[] args) {
        othersThree();
    }

    public static void othersTwo() {
        int[] array = new int[]{1, 2, 3, 2, 1};

        int sum = array[0];

        for (int i = 1; i < array.length; i++) {
            sum = sum ^ array[i];
        }

        System.out.println(sum);
    }

    public static void othersThree() {
        int[] array = new int[]{1, 2, -3, 2, 1, 1, 2};

        int[] bitsNumber = new int[32];

        System.out.println(2 & 2);

        for (int i = 0; i < array.length; i++) {
            int bit = 1;

            for (int j = 0; j < 32; j++) {
                if ((array[i] & bit) == bit) {
                    bitsNumber[j]++;
                }
                bit = bit << 1;
            }

        }

        int result = 0;

        for (int j = 31; j >= 0; j--) {
            result = result << 1;
            result |= bitsNumber[j] % 3;
        }

        System.out.println(result);
    }
}
