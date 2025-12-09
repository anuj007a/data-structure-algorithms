package com.anuj.sorting;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args){
//        int[] arr =  {10, 80, 30, 90, 40, 50, 70};
        int[] arr =  {90, 80, 60, 6, 0, 30, 8, 10, 40, 13, 50, 100};
        quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
    public static void quickSort( int[] arr, int low, int high){
        if(low<high){
            int pivot = partition( arr, low, high);
            quickSort(arr, low, pivot);
            quickSort(arr, pivot+1, high);
        }
    }

    public static int partition(int[] arr, int low, int high){
        int pivot = arr[low];
        int j = high+1;
        for( int i = high; i>=low;i--){
            if(arr[i]>pivot){
                j--;
                arr[i]= arr[i]+arr[j]-(arr[j]=arr[i]);
                System.out.println("After swapping : "+Arrays.toString(arr));
            }
        }
        arr[j-1]=arr[j-1]+arr[low]-(arr[low]=arr[j-1]);
        return j-1;
    }

    static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
