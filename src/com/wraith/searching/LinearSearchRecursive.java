package com.wraith.searching;

/*
Time Complexity
    Best O(1) // When found at first or last place
    Worst O(n) // When found at middle place
Auxiliary Space
    O(1) // Because not using extra space
 */

public class LinearSearchRecursive {

    public static void main(String[] args) {
        int[] arr = {1, 5, 9, 3, 8, 11, 7, 4};
        int target = 7;
        int position = linearSearch(arr, arr.length - 1, target);
        if (position == -1) {
            System.out.println("Number is not found");
        } else {
            System.out.println("Number is found at position " + position);
        }
    }

    public static int linearSearch(int[] arr, int size, int target) {
        if (size == 0) {
            return -1;
        }
        if (arr[size] == target) {
            return size;
        } else {
            int position = linearSearch(arr, size - 1, target);
            return position;
        }
    }
}
