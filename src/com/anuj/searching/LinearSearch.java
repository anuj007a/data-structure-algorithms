package com.wraith.searching;

/*
Time Complexity
    Best O(1) // When found at first or last place
    Worst O(n) // When found at middle place
Auxiliary Space
    O(1) // Because not using extra space
 */

public class LinearSearch {
    public static void main(String[] args) {
        int[] arr = {3, 5, 7, 10, 9, 45, 88, -2, 4, 2};
        int target = 9;
        int position = linearSearch(arr, target);
        if (position == -1) {
            System.out.println("Number is not found at");
        } else {
            System.out.println("Number is found at position " + position);
        }
    }

    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
