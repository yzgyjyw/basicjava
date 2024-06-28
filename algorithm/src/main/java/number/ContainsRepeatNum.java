package number;

/**
 * 排好序的数组找到第一个
 */
public class ContainsRepeatNum {

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 5, 5, 5, 5, 5, 7, 8, 9};

        int targetNum = 5;

        int low = 0;
        int high = array.length - 1;

        while (low < high) {

            int middle = (low+high)/2;

            if(array[middle]>targetNum){
                high = middle-1;
            }else if(array[middle]<targetNum){
                low = middle+1;
            }else if(middle-1>=0 && array[middle-1]==targetNum){
                high = middle-1;
            }else{
                System.out.println(middle);
                break;
            }
        }

    }

}
