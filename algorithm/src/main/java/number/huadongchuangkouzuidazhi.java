package number;

import java.util.Deque;
import java.util.LinkedList;

public class huadongchuangkouzuidazhi {

    public static void main(String[] args) {

        int[] array = new int[]{2, 3, 4, 2, 6, 2, 5, 1};

        int size = 3;

        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < array.length; i++) {
            if(!deque.isEmpty() && i-deque.peekFirst()+1>size){
                deque.removeFirst();
            }
            while (!deque.isEmpty() && array[i]>array[deque.peekLast()]){
                deque.removeLast();
            }

            deque.offer(i);

            if(i>=size-1){
                System.out.println(array[deque.peekFirst()]);
            }
        }

    }

}
