package home.ludvik.solutions.recursionandsorting;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SolutionMergeSort {
    public int[] mergeSort(int[] array) {
        // validation
        if(array == null || array.length == 0){
            return new int[0];
        }

        // corner case
        if(array.length == 1){
            return array;
        }

        // new array for sorted
        int[] sorted = new int[array.length];
        mergeSortRecursive(sorted, array, 0, array.length - 1);

        return sorted;
    }

    private void mergeSortRecursive(int[] sorted, int[] array, int start, int end){
        if(start >= end){
            return;
        }
        System.out.println("mergesortRecursive[start = " + start + ", " + end + "] enter.");

        int mid = start + ( end  - start ) / 2;

        mergeSortRecursive(sorted, array, start, mid);
        mergeSortRecursive(sorted, array,  mid + 1, end);
        merge(sorted, array,  start, mid, mid + 1, end);
        System.out.println("mergesortRecursive[start = " + start + ", " + end + "] leave.");
    }

    private void merge(int[] sorted, int[] array , int start1, int end1, int start2, int end2){
        int sortIdx = start1;
        int p1 = start1;
        int p2 = start2;

        while(p1 <= end1 && p2 <= end2){
            sorted[sortIdx++] = array[p1] <= array[p2] ? array[p1++] : array[p2++];
        }

        while(p2 <= end2){
            sorted[sortIdx++] = array[p2++];
        }

        while(p1 <= end1){
            sorted[sortIdx++] = array[p1++];
        }

        // copy the merged sub array back to original array which is used in larger range merge process;
        for(int i = start1; i <= end2; i++){
            array[i] = sorted[i];
        }
    }
}
