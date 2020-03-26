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
     * 2. if array[i + 1] <= array[i] -> M[i + 1]  = M[i];
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


}
