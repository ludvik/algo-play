package home.ludvik.solutions.string;

import java.util.*;

public class SolutionString {

    /**
     * High level idea:
     * 1) go through the string array, record the frequency of each word into a hashmap.
     * 2) maintain a min heap of frequencies, sized k. go through the hashmap and keep the top K min heap updated.
     *
     * Trick:
     * How to define a min heap?
     * --> in priorityQueue, if compreTo return -1, means o1 has higher priority
     * --> o1 < o2 -> -1, looks like o1 - o2, means min heap.
     * --> o1 > o2 -> -1, looks like o2 - o1, means max heap.
     */

    public String[] topKFrequent(String[] combo, int k) {

        Map<String, Integer> word2Freq = new HashMap<>();
        for(String str : combo){
            Integer freq = word2Freq.getOrDefault(str, -1);
            if(freq == -1){
                word2Freq.put(str, 1);
                continue;
            }
            word2Freq.put(str, ++freq);
        }

        // min heap for top k freq words
        PriorityQueue<Map.Entry<String, Integer> > minHeap = new PriorityQueue<>(k, new Comparator<Map.Entry<String, Integer> >(){
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e1.getValue().compareTo(e2.getValue());
            }
        });

        for(Map.Entry<String, Integer> entry : word2Freq.entrySet()){
            if(minHeap.size() < k){
                minHeap.offer(entry);
                continue;
            }

            Map.Entry<String, Integer> kthFreq = minHeap.peek();
            if(kthFreq.getValue() < entry.getValue()){
                minHeap.offer(entry);
                minHeap.poll();
            }
        }

        String[] kFreqWords = new String[minHeap.size()];
        Integer size = minHeap.size();
        for(int i = 0; i < size; i++){
            Map.Entry<String, Integer> e = minHeap.poll();
            kFreqWords[size - i - 1] = e.getKey();
        }

        return kFreqWords;
    }

    public int missing(int[] array) {
       int theMissingOne = 0;

       for(int i = 1; i <= array.length + 1; i++){
           theMissingOne ^= i;
       }

       for(int num : array){
           theMissingOne ^= num;
       }

       return theMissingOne;
    }

    /**
     * High level idea:
     * Use two pointers, one array each, for each loop step, compare the value of the pointee, move the one smaller forward.
     * If they are equals, put that value into the result list.
     *
     * Termination rule:
     * Either pointer reaches the end of its pointing array.
     *
     */
    public List<Integer> common(int[] A, int[] B) {
        if(A == null || B == null){
            return new ArrayList<>();
        }

        List<Integer> res = new ArrayList<>();
        int p1 = 0;
        int p2 = 0;
        while(p1 < A.length && p2 < B.length){
            if(A[p1] == B[p2]){
                res.add(A[p1]);
                p1++;
                p2++;
            }else if(A[p1] < B[p2]){
                p1++;
            }else{
                p2++;
            }
        }

        return res;
    }

    /**
     * Remove given characters in input string, the relative order of other characters should be remained. Return the
     * new string after deletion.
     *
     * Assumptions
     *
     * The given input string is not null.
     * The characters to be removed is given by another string, it is guaranteed to be not null.
     * Examples
     *
     * input = "abcd", t = "ab", delete all instances of 'a' and 'b', the result is "cd".
     *
     * High level idea(in place):
     * using to pointers, namely, i and j, the semantics are:
     * left of i(not including i) --> chars that are going to keep;
     * between i(include i) and j(not include j) --> chars going to delete;
     * j --> char being processed.
     * right of j --> chars yet to be processed
     *
     * For each step:
     * 1. the char j is pointing to, is included in the target string --> j++
     * 2. the char is not ..., --> swap(i,j), i++, j++
     *
     * Termination rule:
     * j >= input.length()
     *
     * Time Complexity:
     * Copy to StringBuilder --> n
     * Check if charAt[j] is included by t --> mn
     * Swap(i, j) --> n
     * ~ O(2n + mn) ~ O(mn)
     *
     * Extra Space:
     * O(n)
     */
    public String remove(String input, String t) {
        if(input == null || t == null || input.length() == 0){
            return new String();
        }

        StringBuilder builder = new StringBuilder(input);

        int i = 0;
        int j = 0;
        while(j < input.length()){
            if(includedBy(t, input.charAt(j))){
                j++;
            }else{
                swap(builder, i, j);
                i++;
                j++;
            }
        }

        return (i == 0) ? new String() : builder.substring(0, i);
    }

    private void swap(StringBuilder input, int i, int j){
        char tmp = input.charAt(i);
        input.setCharAt(i, input.charAt(j));
        input.setCharAt(j, tmp);
    }

    private boolean includedBy(String t, char target){
        for(int i = 0; i < t.length(); i++){
            if(target == t.charAt(i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Semantics of i and j:
     * [0, i) : chars to keep
     * [i, j) : chars to erase
     * j : char being processed
     * (j, length) : to be explored
     *
     * Branches for each processing char:
     * 1. j == ' '
     *   1.1 i == 0 -> j++;
     *   1.2 i != 0 and (i - 1) != ' ' -> swap(i, j); i++; j++
     *   1.3 i != 0 and (i - 1) == ' ' -> j++;
     * 2. j != ' ' -> swap(i,j); i++; j++
     *
     * Example:
     * "I    love    you!!!    "
     *   i
     *    j
     *
     */
    public String removeSpaces(String input) {

        StringBuilder builder = new StringBuilder(input);
        int i = 0;
        int j = 0;
        while(j < builder.length()){
            char p = builder.charAt(j);
            if( p == ' '){
                if(i == 0){
                    j++; // head space, remove
                }else if(builder.charAt(i - 1) != ' '){// i != 0
                    swap(builder, i++, j++); // first ' ' in the middle of phrase
                }else{ // i != 0 and i-1 == ' '
                    j++;
                }
            }else{ // p != ' '
                swap(builder, i++, j++);
            }
        }

        return (i == 0) ? new String() :
                (builder.charAt(i - 1) == ' ') ? builder.substring(0, i - 1) : builder.substring(0, i) ;
    }

    /**
     * Remove adjacent, repeated characters in a given string, leaving only one character for each group of such characters.
     *
     * Assumptions
     *
     * Try to do it in place.
     *
     * Examples
     *
     * “aaaabbbc” is transferred to “abc”
     *
     * Corner Cases
     *
     * If the given string is null, returning null or an empty string are both valid.
     *
     * High level idea:
     *
     * we use two pointers, i and j. Semantics of i and j:
     * [0, i) -> chars to keep.
     * [i, j) -> chars to remove.
     *   j    -> char being processed.
     * (j, length) -> chars yet to be processed.
     *
     * And a sequance register, flag, its semantics:
     * stores the char last processed. it will be updated when current char finished processing.
     *
     *
     * Return
     *
     *  -> [0, i)
     *
     * Termination condition:
     *
     * j >= length
     *
     * Logic branches for each loop:
     * [j] == flag -> j++;
     * [j] != flag -> swap(i, j);  i++; j++; flag = [j];
     *
     */
    public String deDup(String input) {
        if(input == null || input.length() == 0){
            return new String();
        }

        int i = 0;
        int j = 0;
        char flag = 'a' - 1;
        StringBuilder builder = new StringBuilder(input);

        while(j < builder.length()){
            char c = builder.charAt(j);
            if(c != flag){
                swap(builder, i++, j++);
                flag = c;
            }else{
                j++;
            }
        }

        return builder.substring(0, i);
    }

    /**
     * high level idea:
     * use two pointers of index, say, i and j, starting from both beginning and ending, moving towards each other with the same pace,
     * one character per step. for each step, we swap i and j;
     *
     * termination condition:
     * when i > j;
     *
     * Time Complexity:
     * swap n/2 times. ~ O(n)
     *
     * Space Complexity:
     * in place, O(1)
     */
    public String reverse(String input) {

        StringBuilder reverseSb = new StringBuilder(input);
        reverse(reverseSb, 0, input.length() - 1);

        return reverseSb.toString();
    }

    private void reverse(StringBuilder input, int startIdx, int endIdx){
        while(startIdx <= endIdx){
            swap(input, startIdx++, endIdx--);
        }
    }

    /**
     *
     * Reverse the words in a sentence.
     *
     * Assumptions
     *
     * Words are separated by single space
     *
     * There are no heading or tailing white spaces
     *
     * Examples
     *
     * “I love Google” → “Google love I”
     *
     * Corner Cases
     *
     * If the given string is null, we do not need to do anything.
     *
     * Time Complexity:
     * First char to char reverse: O(n)
     * Second run to reverse each word: O(2n)
     *
     * ~ O(n)
     */
    public String reverseWords(String input) {
        if(input == null || input.length() == 0){
            return input;
        }

        StringBuilder sb = new StringBuilder(input);

        // reverse the whole sentence char by char.
        reverse(sb, 0, input.length() - 1);

        // split the sentence by space, reverse each word
        int slow = 0;
        int fast = 0;

        while(fast < sb.length()){

            if(sb.charAt(fast) != ' '){
                if(fast == sb.length() - 1){ // last word
                    reverse(sb, slow, fast);
                }
                fast++;
            }else if(sb.charAt(fast) == ' '){ // fast == ' '
                reverse(sb, slow, fast - 1);
                fast++;
                slow = fast;
            }
        }

        return sb.toString();
    }

}
