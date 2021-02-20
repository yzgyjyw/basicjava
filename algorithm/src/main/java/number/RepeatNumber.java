package number;

/**
 * 找到重复的数字
 */
public class RepeatNumber {

    public static void main(String[] args) {
        int array[] = new int[]{6, 3, 1, 0, 2, 5, 3};

        for (int i = 0; i < array.length; ) {
            if(array[i]==i){
                i++;
            }else{
                if(array[i]==array[array[i]]){
                    System.out.println(array[i]);
                    break;
                }else{
                    int temp = array[i];
                    array[i] = array[temp];
                    array[temp] = temp;
                }
            }
        }

        System.out.println(-1);
    }
}
