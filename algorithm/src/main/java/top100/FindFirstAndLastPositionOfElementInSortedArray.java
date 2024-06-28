package top100;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.stream.Stream;

public class FindFirstAndLastPositionOfElementInSortedArray {
    public static void main(String[] args) {
        int[] ints = searchRange(new int[]{1, 1, 1, 2, 2, 2, 2, 2, 3, 4, 5, 5, 5, 5, 6, 7, 8, 9}, 2);

        System.out.println(ints[0]+", "+ints[1]);
    }

    public static int[] searchRange(int[] nums, int target) {
        return new int[]{getFirstPosition(nums,target),getLastPosition(nums,target)};
    }

    public static int getFirstPosition(int[] nums,int target){
        int start = 0;
        int end = nums.length - 1;

        while(start <= end){
            int middle = start + ((end-start) >> 1);
            if(nums[middle] < target){
                start = middle + 1;
            }else if(nums[middle] > target){
                end = middle - 1;
            }else {
                end = middle - 1;
            }
        }

        return start;
    }

    public static int getLastPosition(int[] nums,int target){
        int start = 0;
        int end = nums.length - 1;

        while(start <= end){
            int middle = start + ((end-start) >> 1);
            if(nums[middle] < target){
                start = middle + 1;
            }else if(nums[middle] > target){
                end = middle - 1;
            }else {
                start = middle + 1;
            }
        }

        return end;
    }
}
