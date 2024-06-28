package number;

public class SumEquals {

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int targetNum = 12;

        int low = 0;
        int high = array.length-1;

        while (low < high) {

            if (array[low] + array[high] == targetNum) {
                System.out.println(array[low] + "," + array[high]);
                break;
            }

            if (array[low] + array[high] < targetNum) {
                low++;
            }

            if (array[low] + array[high] > targetNum) {
                high--;
            }
        }



    }

}
