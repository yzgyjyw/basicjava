package top100;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 *
 *
 *
 * 示例 1:
 *
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 示例 2:
 *
 * 输入: nums = [0]
 * 输出: [0]
 */
public class MoveZeroes {
    public static void main(String[] args) {
        int[] input = {0, 1, 0, 3, 12};
        moveZeroes(input);
        System.out.println(Joiner.on(",").join(Arrays.stream(input).iterator()));
    }
    public static void moveZeroes(int[] nums) {
        int nonZeroStartPos = -1;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=0){
                swap(nums,i,++nonZeroStartPos);
            }
        }
    }

    public static void swap(int[] nums, int i,int j){
        int temp = nums[i];
        nums[i]= nums[j];
        nums[j]=temp;
    }
}
