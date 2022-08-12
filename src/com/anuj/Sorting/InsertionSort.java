package com.anuj.Sorting;

import java.util.Arrays;

/*
Time Complexity
    Best O(n^2) // When found at mid
    Worst O(n^2) // When found at last iteration
Auxiliary Space
    O(1) // Because not using extra space
Sorting in place
    Yes
Stable
    Yes

1. No of swapped reduced a compare to bubble sort
2. Stable
3. Use for smaller value for n, works good => When array are partially sorted
4. It's take part of hybrid sorting algorithm
 */
public class InsertionSort {

    public static void main( String[] args){
        int[] arr = {2, 5, 1, 9, 0, 23, 8, 6};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertionSort( int[] arr){
        for( int i = 0; i < arr.length-1; i++){
            for( int j = i+1; j>0; j--){
                if(arr[j]<arr[j-1]){
                    arr[j-1]= (arr[j-1]+arr[j])-(arr[j]=arr[j-1]);
                }else{
                    break;
                }
            }
        }
    }
}
