package top100;

public class SearchInRotatedSortedArray {
    // nums = [4,5,6,7,0,1,2], target = 0
    // nums = [4,5,6,7,0,1,2], target = 3
    // nums = [3,1], target = 1
    public static void main(String[] args) {
        int search = search(new int[]{3, 1}, 1);
        System.out.println(search);
    }

    public static int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while(start<=end){
            int middle = start + ((end-start) >> 1);
            if(nums[middle]==target){
                return middle;
            }

            // middle在左侧上升区间
            if(nums[start] <= nums[middle]){
               if(target >= nums[start] && target < nums[middle]){
                    end = middle - 1;
                }else {
                    start = middle + 1;
                }
            }else {
                // middle在右侧上升区间
                if (target <= nums[end] && target > nums[middle]) {
                    start = middle + 1;
                } else {
                    end = middle - 1;
                }
            }
        }

        return -1;
    }
}
