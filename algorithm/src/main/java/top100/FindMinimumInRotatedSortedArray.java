package top100;

/**
 * 已知一个长度为n的数组,预先按照升序排列,经由1到n次旋转后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到： 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2] 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7] 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 *
 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 *
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [3,4,5,1,2] 输出：1 解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。 示例 2：
 *
 * 输入：nums = [4,5,6,7,0,1,2] 输出：0 解释：原数组为 [0,1,2,4,5,6,7] ，旋转 3 次得到输入数组。 示例 3：
 *
 * 输入：nums = [11,13,15,17] 输出：11 解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
 */
public class FindMinimumInRotatedSortedArray {

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3, 4, 5, 1, 2}));
    }

    public static int findMin(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int low, mid, high;
        low = 0;
        high = nums.length - 1;
        while (low < high) {
            mid = (low + high) / 2;
            if (nums[low] < nums[high]) {
                return nums[low];
            }
            if (nums[mid] > nums[low]) {
                low = mid;
            } else if (nums[mid] < nums[low]) {
                high = mid;
            } else {
                return nums[high];
            }
        }

        return -1;
    }
}
