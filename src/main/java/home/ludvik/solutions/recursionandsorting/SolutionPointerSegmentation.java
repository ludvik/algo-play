package home.ludvik.solutions.recursionandsorting;

import home.ludvik.util.ArrayHelpers;

public class SolutionPointerSegmentation {

    public int[] moveZero(int[] array){

        // validation
        if(array == null){
            return null;
        }

        if(array.length == 0){
            return array; // nothing to do.
        }

        // Assumptions:
        // 1. the rest of the array is not guaranteed to be in the same order
        //
        // Top Idea:
        // Use two pointers, say, i and j. We separate the array into tree parts:
        // nnnnnnuuuuuu0000
        //       i    j
        // [0, i): elements that are not zero;
        // [i, j]: unchecked elements;
        // (j, array.length): zeroes;
        //
        // Initial position:
        // i = 0: [0, 0) elements that are not zero, ok
        // j = array.length - 1: (end, end) zeros, which is empty for now;
        // [i, j]: unknown area
        //
        //  uuuuuuuuuuuuuuuuu
        //  i               j
        //
        //  nnnn0n0000
        //      ij
        //  nnnnn00000
        //      ij
        //      ji
        //  nn
        //  ij
        //   ji
        //
        //  00
        //  ij
        // ji
        //
        // 2, 3, 8, 9, 8, 7, 7, 6, 0, 0, 0
        //                      ij
        //                   j        i

        int i = 0;
        int j = array.length - 1;

        while(i <= j){
            System.out.println("i = " + i + ", j = " + j );
            if(array[j] == 0){
                j--;
            } else if(array[i] != 0){
                i++;
            } else if(array[i] == 0 && array[j] != 0){
                swap(array, i++, j--);
            }

            System.out.println("i = " + i + ", j = " + j );
            ArrayHelpers.printArray(array);
            System.out.println("----");
        }

        return array;
    }

    public int[] rainbowSort(int[] array){
        // input of array consists of -1,0,1
        // sort them in a -1, 0, 1 order
        //
        // Assumptions:
        // no other elements here
        //
        // Big picture of idea:
        // tranverse the array once, put elements into right position accordingly.
        // use three pointers to separate the array into four segments:
        //
        // RrrrrGgggUuuuuBbbbb
        //      i   j   k
        //
        // [0, i) : Red nodes
        // [i, j) : Green nodes
        // [j, k] : unkonwn nodes to explore
        //      j is the current index;
        // (k, length) : Blue nodes
        //
        // Initial position of i, j, k:
        // i = j = 0
        // k = length - 1;
        //
        // cases:
        // [j] == r : swap (i, j); i++; j++
        // [j] == g : j++
        // [j] == b : swap (j, k); k--;
        //
        // corner cases:
        // 1. empty or one element -> do nothing
        // 2. only two elements [r,r]/[b,b]/[g,b]/[g,r]
        // 3. three elements [b,r,r,]/[b,b,r]/[g,g,b]/[g,g,r]
        // normal case:
        // [b,r,g,g,r,b,b,r,g]

        // validation
        if(array == null){
            return new int[0];
        }
        // corner cases
        if(array.length <= 1){
            return array;// nothing to do
        }

        // initialize pointers
        int i = 0;
        int j = 0;
        int k = array.length - 1;

        // main loop
        while(j <= k){
            if(array[j] == -1){
                swap(array, i++, j++); // array[i] equals either to  r or g; there is only one initial case, [0] = b
            }else if(array[j] == 0){
                j++;
            }else if(array[j] == 1){
                swap(array, j, k--);
            }
        }// outside: j > k

        return array;
    }

    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
