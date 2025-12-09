package com.anuj.sorting;

import java.util.Arrays;

/*
Time Complexity
    Best O(n) // When found at mid
    Worst O(n*n) // When found at last iteration
Auxiliary Space
    O(1) // Because not using extra space
Sorting in place
    Yes
Stable
    Yes
*/

public class BubbleSorting {

    public static void main(String[] args) {
//        int[] arr = {3, 1, -4, 3, 9, 13, 14, 20, 16, 15};
        int[] arr = {2,5,1,32,8,3,-3,6,9,13};

        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j+1]) {
//                    arr[i] = arr[j] + arr[j] - (arr[j] = arr[i]);
                    int temp = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                    swapped = true;
                }
                }
            if (!swapped) {
                break;
            }
        }
    }
}
