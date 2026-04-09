package com.wraith.searching;

/*
Time Complexity
    Best O(1) // When found at mid
    Worst O(log n) // When found at last iteration
Auxiliary Space
    O(1) // Because not using extra space
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 5, 7, 10, 12, 15, 17, 20, 25, 26};
        int target = 170;
        int start = 0;
        int end = arr.length-1;
        int position = binarySearch(arr, target, start, end);
        if(position == -1){
            System.out.println("Number is not found");
        }else{
            System.out.println("Number is found at position "+position);
        }
    }

    public static int binarySearch( int[] arr, int target, int start, int end){

        while(start<=end){
            int mid = start+(end-start)/2;
            if(arr[mid]<target){
                start = mid+1;
            }else if(arr[mid]>target){
                end = mid;
            }else{
                return mid;
            }
        }
        return -1;
    }
}
