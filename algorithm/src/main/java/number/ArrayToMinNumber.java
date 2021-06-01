package number;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayToMinNumber {
    public static void main(String[] args) {
        int[] array = new int[]{3, 32, 321};

        /*System.out.println("321".compareTo("32"));*/

        List<String> collect = Arrays.stream(array).boxed().map(i -> i + "").collect(Collectors.toList());

        Collections.sort(collect, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                int length = Math.min(str1.length(), str2.length());

                for (int i = 0; i < length; i++) {
                    if (str1.charAt(i) > str2.charAt(i)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (str1.length() > str2.length()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        collect.stream().forEach(System.out::println);

    }

}
