package number;

public class NumberOfOne {

    public static void main(String[] args) {
        int number = 15;

       int count =0;

       while (number!=0){
           count++;
           number = number & (number-1);
       }

        System.out.println(count);
    }

}
