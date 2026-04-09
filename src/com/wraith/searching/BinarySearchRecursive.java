package com.wraith.searching;

/*
Time Complexity
    Best O(1) // When found at mid
    Worst O(log n) // When found at last iteration
Auxiliary Space
    O(log n)
 */
public class BinarySearchRecursive {

    public static void main( String[] args){
        int[] arr = { 1, 4, 7, 11, 23, 34, 35, 43, 51};
        int target = 293;
        int start = 0;
        int end = arr.length-1;
        int position = binarySearch( arr, target, start, end);
        if( position == -1){
            System.out.println("Number is not found");
        }else{
            System.out.println("Number is found at position "+ position);
        }
    }

    public static int binarySearch(int[] arr, int target, int start, int end){
        if(start<=end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                return binarySearch(arr, target, mid + 1, end);
            } else if (arr[mid] > target) {
                return binarySearch(arr, target, start, mid);
            }
        }
        return -1;
    }
}
