package com.anuj.Sorting;

import java.util.Arrays;

public class Sorting {

    public static void main(String[] args) {
//        int[] arr = {3, 1, -4, 3, 9, 13, 14, 20, 16, 15};
        int[] arr = {3, 5, 1, 4, 2, 8, 7, 6, 9};

        quick(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quick(int[] arr, int low, int high){
        if(low<high) {
            int p = pivot(arr, low, high);
            quick(arr, low, p);
            quick(arr, p+1, high);
        }

    }

    public static int pivot(int[] arr, int low, int high){
        int pivot = arr[low];
        int j= high+1;
        for( int i =high; i>=low; i--){
            if(arr[i]>pivot){
                j--;
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i]=temp;
            }

        }
        int temp = arr[low];
        arr[low] = arr[j-1];
        arr[j-1]=temp;
        return j-1;

    }


    public static void cyclic(int[] arr) {
        int i = 0;
        while (i < arr.length) {
            if (arr[i] == i+1) {
                i++;
            } else {
                int temp = arr[arr[i]-1];
                arr[arr[i]-1]=arr[i];
                arr[i] =temp;
            }
        }
    }

    public static void insertion(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }


    static void selection(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }

    }

}
