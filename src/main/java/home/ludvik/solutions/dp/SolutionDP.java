package home.ludvik.solutions.dp;

public class SolutionDP {

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


}
