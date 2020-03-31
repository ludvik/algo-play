package home.ludvik.solutions.dp;

public class SolutionDP {

    /**
     * Semantics of the subproblem, M[i]:
     * M[i] represents the fibo result for input i;
     *
     * Induction rule:
     * M[k] := M[K - 1] + M[K - 2]
     *
     * Time Complexity:
     * O(K)
     *
     * Space Complexity:
     * O(K) (could be optimized down to O(1))
     */
    public long fibonacci(int K) {
        // Write your solution here
        long[] M = new long[K];
        M[0] = 0;
        M[1] = 1;
        M[2] = 1;

        for(int i = 3 ; i <= K; i++){
            M[i] = M[i - 2] + M[i - 1];
        }

        return M[K];
    }

    /**
     * Title: Find the longest ascending sub-array length
     *
     * High level idea:
     * Go through the whole array, for each position i, memorize the longest sub-array lenth M[i].
     *
     * Induction Rule:
     * 1. if array[i + 1] > array[i] -> M[i + 1] = M[i] + 1;
     * 2. if array[i + 1] <= array[i] -> M[i + 1] = M[i];
     *
     */
    public int longestAscending(int[] array) {

        if(array == null || array.length == 0){
            return 0;
        }

        if(array.length == 1){
            return 1;
        }

        int[] M = new int[array.length];
        M[0] = 1;
        int curAscCnt = 1;

        for(int i = 1; i < array.length; i++){
            if(array[i] > array[i - 1]){
                curAscCnt ++;
            }else{ // <=
                curAscCnt = 1;
            }
            M[i] = curAscCnt > M[i - 1] ? curAscCnt : M[i - 1];
        }

        return M[array.length - 1];
    }

    /**
     * Given a rope with positive integer-length n, how to cut the rope into m integer-length parts with length p[0],
     * p[1], ...,p[m-1], in order to get the maximal product of p[0]*p[1]* ... *p[m-1]? m is determined by you and must
     * be greater than 0 (at least one cut must be made). Return the max product you can have.
     *
     * Assumptions
     * n >= 2
     *
     * Examples
     *
     * n = 12, the max product is 3 * 3 * 3 * 3 = 81(cut the rope into 4 pieces with length of each is 3).
     *
     * High Level Idea:
     *
     *
     *
     *
     */
    public int cutRope(int length) {
        if(length < 2){
            return 0;
        }

        if(length == 2){
            return 1;
        }

        int[] M = new int[length + 1];
        M[0] = 0;
        M[1] = 0;

        for(int i = 2; i <= length; i++){
            int curMax = 0;
            for(int j = 1; j < i; j++){
                curMax = Math.max(curMax, Math.max(j, M[j]) * (i - j));
            }
            M[i] = curMax;
        }

        return M[length];
    }

    /**
     * Array Hopper
     *
     * Given an array A of non-negative integers, you are initially positioned at index 0 of the array. A[i] means the
     * maximum jump distance from that position (you can only jump towards the end of the array). Determine if you are
     * able to reach the last index.
     *
     * Assumptions
     *
     * The given array is not null and has length of at least 1.
     *
     * Examples
     *
     * {1, 3, 2, 0, 3}, we are able to reach the end of array(jump to index 1 then reach the end of the array)
     * {2, 1, 1, 0, 2}, we are not able to reach the end of array
     *
     * /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     * High level idea:
     * Linear scan the array, starting from the end back to index 0. Standing in the end index,
     *
     * Semantics of the boolean DP array:
     * M[i] means if the end index is reachable from position i, true or false;
     *
     * Base case:
     * M[length - 1] = true.
     *
     * For each step(induction rule):
     * for every node array[i], scan forward for array[i] of indexes, if there is exists a true in M[...], then M[i] = true,
     * otherwise false.
     *
     * Problem Solving:
     * return M[0]
     *
     */
    public boolean canJump(int[] array) {

        if(array == null || array.length ==0){
            return false;
        }

        if(array.length == 1){
            return true;
        }

        // array.length >= 2 below
        boolean[] M = new boolean[array.length];
        M[array.length - 1] = true;

        for(int i = array.length - 2; i >= 0; i--){
            M[i] = false;
            for(int j = i; j <= i + array[i] && i < array.length; j++){
                if(M[j] == true){
                    M[i] = true;
                    break;
                }
                M[i] = M[j];
            }
        }

        return M[0];
    }

    /**
     * Array Hopper II
     *
     * Given an array A of non-negative integers, you are initially positioned at index 0 of the array. A[i] means the
     * maximum jump distance from index i (you can only jump towards the end of the array). Determine the minimum number
     * of jumps you need to reach the end of array. If you can not reach the end of the array, return -1.
     *
     * Assumptions
     *
     * The given array is not null and has length of at least 1.
     *
     * Examples
     *
     * {3, 3, 1, 0, 4}, the minimum jumps needed is 2 (jump to index 1 then to the end of array)
     * {2, 1, 1, 0, 2}, you are not able to reach the end of array, return -1 in this case.
     *
     * High level idea:
     * Traverse from end to start, to calculate the shortest hops to end for each index.
     *
     * Base case:
     * M[array.length - 1] = 0;
     *
     * Semantics of the DP table:
     * M[i] means starting from index i ,the shortest hops needed of jumping to the end; -1 means unreachable;
     *
     * Solving the problem:
     * return M[0]
     *
     *
     */

    public int minJump(int[] array) {

        if(array.length == 1){
            return 0;
        }

        int[] M = new int[array.length];

        M[array.length - 1] = 0; // base case
        for (int i = array.length - 2; i >= 0; i--){
            int curMin = Integer.MAX_VALUE;
            for(int j = i + 1; j <= i + array[i] && j < array.length; j++){
                if(M[j] > -1) {
                    curMin = Math.min(curMin, M[j] + 1);
                }
            }
            M[i] = (curMin == Integer.MAX_VALUE) ? -1 : curMin;
        }

        return M[0];
    }

}
