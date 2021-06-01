package number;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class huadongchuangkouzuidazhi {

    public static void main(String[] args) {

        int[] array = new int[]{2, 3, 4, 2, 6, 2, 5, 1};

        int size = 3;

        int[] result = new int[array.length - 3 + 1];

        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < array.length; i++) {
            if (deque.isEmpty()) {
                deque.offer(i);
            }

            while (!deque.isEmpty()) {
                if (array[deque.peekLast()] < array[i]) {
                    deque.removeLast();
                }else{
                    break;
                }
            }

            deque.offer(i);

            if (i >= size - 1) {
                Integer integer = deque.peekFirst();
                result[i - size + 1] = integer;

                if(i-integer+1>=3){
                    deque.removeFirst();
                }
            }
        }

        Arrays.stream(result).forEach(i->System.out.println(array[i]));
    }

}
