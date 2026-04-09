package com.wraith.searching;

/*
Time Complexity
    Best O(1) // When found at first or last place
    Worst O(n) // When found at middle place
Auxiliary Space
    O(1) // Because not using extra space
 */

public class LinearSearchApproach2 {

    public static void main(String[] args) {
        int[] arr = {1, 5, 9, 3, 8, 11, 7, 4};
        int target = 7;
        int position = linearSearch(arr, target);
        if (position == -1) {
            System.out.println("Number is not found");
        } else {
            System.out.println("Number is found at position " + position);
        }
    }

    public static int linearSearch(int[] arr, int target) {
        int start = 0;
        int last = arr.length - 1;

        for (; start <= last; ) {
            if (arr[start] == target) {
                return start;
            } else if (arr[last] == target) {
                return last;
            }
            start++;
            last--;
        }
        return -1;
    }
}
