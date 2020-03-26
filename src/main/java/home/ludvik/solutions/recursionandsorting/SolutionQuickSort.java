package home.ludvik.solutions.recursionandsorting;

import home.ludvik.util.ArrayHelpers;

public class SolutionQuickSort {
    public int[] quickSort(int[] array){
        // validation
        if(array == null || array.length == 0){
            return new int[0];
        }

        if(array.length == 1){
            return array;
        }

        quickSortRecursive(array, 0, array.length - 1);

        return array;
    }

    private void quickSortRecursive(int[] array, int start, int end){

        System.out.println("------------------------");
        System.out.print("input array -> ");
        ArrayHelpers.printArray(array, start, end);
        // base case 1
        if(start >= end){
            return;
        }

        // base case 2
        if(start == end - 1){
            if(array[start] > array[end]){
                swap(array, start, end);
            }
            ArrayHelpers.printArray(array, start, end);
            return;
        }

        // sementics of pointer i and j:
        //
        // sssssssssuuuuuuuuuuuubbbbbbbbbbbP
        //          i          j
        //   small     unknown   bigger    pivot
        //
        // i: all elements on the left of i is smaller or equal to pivot
        // j: all elements on the right of j is larger than pivot
        // pivot: at each beginning of iteration, always choose the first element as the pivot
        //
        // when one pass finished, we swap pivot back and return;
        int i = start;
        int j = end - 1; // the last element is used as the pivot;
        int pivot = array[end];

        // 6, 8, 4, 5, 5
        // i        j  p
        // 5, 8, 4, 6, 5
        // i        j  p
        //    i  j
        // 5, 4, 8, 6, 5
        //    i  j     p
        //    j  i
        // 5, 4, 5, 6, 8
        //    j  i

        // corner case
        // 6, 8, 4, 5, 9
        // i        j  p
        //    i     j  p
        //       i  j  p
        //          ij p
        //          j  ip
        // 6, 8, 4, 5, 9  // after swap(i, p)

        // 6, 8, 4, 5, 1
        // i        j  p
        // i     j     p
        // i  j        p
        //ij           p
        // j=-1        p
        // 1, 8, 4, 5, 6  // after swap(i, p)

        // 6, 8, 4, 5, 5
        // i        j  p
        // 5, 8, 4, 6, 5
        // i        j  p
        //    i     j  p
        //    i  j     p
        // 5, 4, 8, 6, 5
        //    i  j     p
        //    j  i     p
        //

        // use pivot to run through the array
        while(i <= j){
            if(array[i] <= pivot){
                i++;
            }else if(array[j] > pivot){
                j--;
            }else{
                swap(array, i, j);
                i++;
                j--;
            }
        }
        //  1. sssssuubbbbbP
        //          ij
        //  2. ssssssubbbbbP
        //           i
        //           j
        //  3. sssssssbbbbbP
        //           ji     // i is finally pointing to the left most element bigger than P
        System.out.print("befor swap ->");
        ArrayHelpers.printArray(array, start, end);
        System.out.println("swappivot(["+ i +"] = "+ array[i] +", ["+ end +"] = "+ array[end] +" )");
        swap(array, i, end);

        ArrayHelpers.printArray(array, start, end);


        // go recursively to sub arrays
        quickSortRecursive(array, start, j); // left part
        quickSortRecursive(array, i + 1, end);

    }

    private void swap(int[] array, int from, int to){
        int tmp = array[to];
        array[to] = array[from];
        array[from] = tmp;
    }
}
