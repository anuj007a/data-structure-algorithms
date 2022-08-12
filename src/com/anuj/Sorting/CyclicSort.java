package com.anuj.Sorting;

import java.util.Arrays;
/*
Time Complexity
    O(n)
Auxiliary Space
    O(1) // Because not using extra space
 */
/*
If given no from 1 to N. Apply cyclic sort
 */
public class CyclicSort {
    public static void main( String[] args){
        int[] arr = { 3, 5, 1, 4, 2, 8, 7, 6, 9};
        cyclicSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void cyclicSort( int[] arr){
        int i =0;
        while(i<arr.length-1){
            if( arr[i] == i+1){
                i++;
            }else {
                int temp = arr[arr[i]-1];
                arr[arr[i]-1]=arr[i];
                arr[i]= temp;
            }
        }
    }
}