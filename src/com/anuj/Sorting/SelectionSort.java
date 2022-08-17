package com.anuj.Sorting;

import java.util.Arrays;

/*
Time Complexity
    Best O(n^2) // When found at mid
    Worst O(n^2) // When found at last iteration
Auxiliary Space
    O(1) // Because not using extra space
Method
    Selection
Sorting in place
    No
Stable
    No
*/

public class SelectionSort {

    public static void main(String[] args){
        int[] arr = {-5, -6, 0, 5, 3, -4, 13, 7, 23, 17, 8};
        selectionSortUsingMax(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void selectionSortUsingMax(int[] arr) {
        for( int i =0; i<arr.length; i++){
            int lastIndex = arr.length-i-1;
           int maxIndex =  findMaxIndex(arr, lastIndex);
           arr[maxIndex] = (arr[lastIndex]+arr[maxIndex])-(arr[lastIndex]=arr[maxIndex]);
        }
    }

    static int findMaxIndex(int[] arr, int len){
        int max = 0;
        for( int i = 1 ; i<= len; i++){
            if(arr[i]>arr[max]){
                max=i;
            }
        }
        return max;
    }

    public static void selectionSortUsingMin(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[min_idx] > nums[j]) {
                    min_idx = j;
                }
                int temp = nums[min_idx];
                nums[min_idx] = nums[i];
                nums[i] = temp;
            }
        }
    }
}
