package number;

public class OccurTimesInSortedArray {

    public static void main(String[] args) {
        int[] array = new int[]{1, 1, 1, 5, 5, 5, 7, 8, 8, 8, 8, 9, 10};

        int targetNum = 8;

        int first = getFirst(array, targetNum);
        int last = getLast(array, targetNum);

        System.out.println(last - first + 1);


    }

    public static int getFirst(int[] array, int targetNum) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int middle = (low + high) / 2;

            if (array[middle] > targetNum) {
                high = middle - 1;
            } else if (array[middle] < targetNum) {
                low = middle + 1;
            } else if (middle - 1 >= 0 && array[middle - 1] == targetNum) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public static int getLast(int[] array, int targetNum) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int middle = (low + high) / 2;

            if (array[middle] > targetNum) {
                high = middle - 1;
            } else if (array[middle] < targetNum) {
                low = middle + 1;
            } else if (middle + 1 <= array.length - 1 && array[middle + 1] == targetNum) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }


}
