package number;

import java.util.Arrays;
import java.util.Stack;

public class FirstBiggerI {

    public static void main(String[] args) {

        int[] array = new int[]{5, 9, 3, 0, 4, 5, 6, 9};

        int[] result = new int[array.length];

        Stack<Integer> stack = new Stack<>();

        for(int i=0;i<array.length;i++){
            if(stack.isEmpty()){
                stack.push(i);
                continue;
            }

            while (!stack.isEmpty()){
                int lastIndex = stack.peek();
                if(array[i]>array[lastIndex]){
                    result[lastIndex] = i;
                    stack.pop();
                }else{
                    break;
                }
            }

            stack.push(i);
        }

        while (!stack.isEmpty()){
            Integer integer = stack.pop();
            result[integer] = -1;
        }


        Arrays.stream(result).forEach(System.out::println);
    }

}
