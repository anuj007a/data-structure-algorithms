package com.anuj.Sorting;

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
        int[] arr = {-4, 1, 3, 3, 9, 13, 14, 15, 16, 20};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            for (int j = i + 1; j < arr.length - i - 1; j++) {
                if (arr[i] > arr[j]) {
                    arr[i] = arr[j] + arr[j] - (arr[j] = arr[i]);
//                    int temp = arr[j];
//                    arr[j]=arr[i];
//                    arr[i]=temp;
                    swapped = true;
                }
                if (!swapped) {
                    break;
                }
            }
        }

    }
}
