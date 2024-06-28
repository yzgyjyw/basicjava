package number;

/**
 * 找到旋转字符串的最小数
 */
public class RotateArray {
    public static void main(String[] args) {
        int[] array = {3, 4, 5, 6, 7, 1, 2};

        int l = 0;
        int r = array.length - 1;

        int result = 0;

        while (array[l] >= array[r]) {

            if (r - l == 1) {
                result = r;
                break;
            }


            result = (r + l) / 2;

            if (array[l] == array[result] && array[result] == array[r]) {
                int min = l;
                for (int i = 0; i <= r; i++) {
                    if (array[min] <= array[i]) {
                        min = i;
                    }
                }
                result = min;
                break;
            }

            if (array[result] >= array[l]) {
                l = result;
            } else if (array[result] <= array[r]) {
                r = result;
            }

        }

        System.out.println(array[result]);
    }
}
