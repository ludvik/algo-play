package home.ludvik.solutions.graph;
import home.ludvik.util.GraphNode;
import home.ludvik.util.Heap;
import home.ludvik.util.TreeNode;

import java.util.*;

public class SolutionGraph {

    public boolean isBTCompleted(TreeNode root){

        // top level idea:
        // using BFS-1(Breadth First Search)
        // 1. Data structure: using a FIFO queue to store the unexpanded node.
        // 2. Initial state: the root node
        // 3. Node generation / expansion rule:
        //    3.1 generation rule:
        //    3.2 expansion rule:
        // 4. Termination condition:
        //    4.1 when scanning the generated node stream, once there is a null node, there should be no node generated
        //        any more, otherwise return false;
        //    4.2 when the queue is empty(the tree is fully tranversed), return true

        // validation
        if(root == null){
            return true;
        }

        Queue<TreeNode> toBeExpanded = new ArrayDeque<>(10);
        toBeExpanded.offer(root);
        boolean alreadySeenNull = false;

        while(toBeExpanded.size() != 0){

            TreeNode cur = toBeExpanded.poll();

            if(cur.left != null){
                if(alreadySeenNull == true){
                    return false;
                }
                toBeExpanded.offer(cur.left);
            }else{ // left == null
                alreadySeenNull = true;
            }

            if(cur.right != null){
                if(alreadySeenNull == true){
                    return false;
                }
                toBeExpanded.offer(cur.right);
            }else{
                alreadySeenNull = true;
            }
        }

        return true;
    }

    /*
    High level idea:

        Initialize a k sized Max Heap to store the smallest numbers with the first k numbers in the array;
        tranverse the rest of the input array, keep doing two actions:
        2.1 add it to the heap
        2.2 pop the heap (eleminate the biggest in the k smallest elements)
        return the content of the heap;

    Time Complexity
        Assumption: we use a Binary Tree max heap

        when initializing the k max heap: 1 + log_2(2) + ... + log_2(k) ~ klog_2(k)
        when tranverse the rest of the input array: (n - k)(log_2(k) + k)
        To sum up, the Time Complexity is
        klogk + nlogk - nk - klogk - k^2 ~ O((n+k)logk)

    Space Complexity

        we keep a K element heap ~ k
        we do add function when tranversing each input number, we use iteration ~ 1;
        we do pop to each element, there is a recursion in it: log_2(k)
        overall, K + log_2(k) + 1 ~ O(K+log_2(k))

     */

    public int[] kSmallest(int[] array, int k) {
        // validation
        if(array == null || array.length == 0 || k == 0){
            return new int[0];
        }

        // base case
        if(array.length <= k){
            return array;
        }

        // initialize max heap for k smallest values
        Heap kSmallest = new Heap(new Heap.LargerThan());
        for(int i = 0; i < array.length; i++){
            if(i < k) {
                kSmallest.add(array[i]);
                continue;
            }

            kSmallest.add(array[i]);
            kSmallest.pop();
        }

        return kSmallest.toArray();

    }

    /**
     * High level idea:
     * Treat the problem as a Breadth First traverse of a graph
     *
     * Data Structure:
     *      use a queue to store generated nodes and expand in a FIFO manner.
     * Initial position:
     *      the root node.
     * Node generation/expansion rule:
     *      generate a whole next level and then expand the current level
     * Deduplication rule:
     *      no deduplication needed coz this is a tree
     *
     * Time complexity:
     *      we travers all the tree nodes once, ~ O(n) time complexity.
     *
     * Space complexity:
     *      we use a queue to store a whole level of nodes.
     *      worst case: for a n nodes complete BT, with full bottom layer, which has n/2 nodes.
     *      ~ O(n) space complexity.
     */
    public List<List<Integer>> layerByLayer(TreeNode root) {

        List result = new ArrayList(0);
        Queue<TreeNode> q = new ArrayDeque<>(10);

        q.offer(root); // initial position

        while(q.size() != 0){
            List<Integer> level = new ArrayList<>(0);
            int levelSize = q.size();
            for(int i = 0; i < levelSize; i++ ){
                // expand current level and generate next level
                TreeNode cur = q.poll();
                level.add(cur.key);

                // generate for next level
                if(cur.left != null){
                    q.offer(cur.left);
                }

                if(cur.right != null){
                    q.offer(cur.right);
                }
            }

            result.add(level);
        }

        return result;
    }


    /**
     * High level idea:
     *      based on breadth first traverse method, we can use generate and mark strategy. Every generated node is marked with
     *      a sign which opposites to the origin node. If a node is assigned with both signs from different direction,
     *      this graph is not a bipartile. If we expanded the whole graph w/ any contradiction, this is a bipartile.
     *
     * Initial Position:
     *      Any node of the graph;
     *      if a subgraph is traversed and justified as bipartile, we need continue check if there are more sub-graphs.
     *
     * Data Structure:
     *      use a queue to store all generated nodes to be explored.
     *      use a Hashmap<GraphNode, Integer> to store assigned color.
     *      use a Hashset<GraphNode> to store traversed node.
     *
     * Generating and Expanding rules:
     *      mark the generated nodes with a sign which opposites to it's origin node and check if there exists contradiction.
     *
     * Deduplication rule
     *      no deduplication since we need to traverse very single edge.
     *
     * Time Complexity
     *      for each node in graph:
     *      1. check if it is in traversed set: log2(V)
     *      2. for every Edge, check goto-node color in node color set: Elog2(V)
     *
     *      O(Vlog2(V) + Elog2(V))
     *
     *      for Sparse Graph, V >> E ~ O(Vlog2(V))
     *      for Dense Graph, E >> V ~ O((V + E)log2(V))
     *
     * Space Complexity
     *      exploration queue: O(V)
     *      traversed set: O(V)
     *      color map: O(V)
     *
     *      ~ O(V)
     */
    public boolean isBipartite(List<GraphNode> graph) {

        Set<GraphNode> traversed = new HashSet<>();
        for(GraphNode node : graph){
            if(traversed.contains(node)){
                continue;
            }

            if(false == checkSubGraph(node, traversed)){
                return false;
            }
        }
        return true;
    }

    private boolean checkSubGraph(GraphNode orig, Set<GraphNode> traversed){

        // nodes to be explored
        Queue<GraphNode> queue = new ArrayDeque<>(0);
        // node color map
        Map<GraphNode, Integer> node2Color = new HashMap<>();

        // generate first node
        queue.offer(orig);
        node2Color.put(orig, 1); // set initial color for the first node

        while(queue.size() != 0){
            // expand
            GraphNode node = queue.poll();
            Integer origColor = node2Color.get(node); // generated node must have a color assigned.
            traversed.add(node);

            // set color to its neighbours
            for(GraphNode neighbour : node.neighbors){
                Integer color = node2Color.getOrDefault(neighbour, -1);
                if(color == -1){
                    // set color
                    node2Color.put(neighbour, (origColor + 1) % 2 );
                    // generate
                    queue.offer(neighbour);
                }else if(origColor == color){ // this node is already assigned a color, check
                    return false; // contradiction found, this not a bipartile
                }
                // node has already been assigned with a color and no contradiction
            }
        }

        return true;
    }

    /**
     * How many levels? What is the semantics for each level?
     * Each level represents a decision that whether we choose input[index] into the final solution.
     * Hence we need input.length levels.
     *
     * How many different states should we try to put on each level?
     * Two branches, either select or not select input[index]
     *
     * Time Complexity( n: length of the input array)
     *
     * 1 + 2 + 4 + 2^3 + .. + 2^n = 2 * 2^n - 1 = 2^(n+1) - 1
     *
     * Extra Storage
     *
     * O(n) : the height of the DFS tree.
     *
     */

    public List<String> subSets(String input){

        if(input == null){
            return new ArrayList<String>();
        }

        List<String> output = new ArrayList<>();
        findSubset(input.toCharArray(), 0, new StringBuilder(), output);

        return output;
    }

    private void findSubset(char[] input, int index, StringBuilder solution, List<String> output){
        if(index == input.length){
            output.add(solution.toString());
            return;
        }

        findSubset(input, index + 1, solution, output);

        solution.append(input[index]);
        findSubset(input, index + 1, solution, output);
        solution.deleteCharAt(solution.length() - 1);
    }

    /**
     * Semantics of each layer：
     * one layer represents a decision to fill a slot with either '(' or ')'
     * there are total 2 x n layers.
     *
     * # of states per layer?
     * Two, to fill '(' or ')'
     *
     * Base case:
     * nLeft == nRight == n
     *
     * Rule:
     * At any layer, these rules stand:
     * 1. nLeft <= n
     * 2. nRight <= nLeft
     *
     *
     *
     *                                  {}  // n = 2
     *                  /                                  \
     *                 {(}                                 {)}
     *           /            \                      /              \
     *         {((}          {()}
     *     /               /       \
     *   {(()}           ()(
     */
    public List<String> validParentheses(int n) {
        List<String> output = new ArrayList<String>(0);
        findValidParenthesesRecursive(n, 0, 0, new StringBuilder(), output);
        return output;
    }

    private void findValidParenthesesRecursive(int nParentheses, int left, int right, StringBuilder solution, List<String> output){
        // base case
        if(left == nParentheses && right == nParentheses){
            output.add(solution.toString());
            System.out.println("--> " + solution.toString());
            //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            return;
        }

        if(left < nParentheses){ // trim: left cannot be larger than n parentheses
            solution.append('(');
            //System.out.println(solution.toString());
            findValidParenthesesRecursive(nParentheses, left + 1, right, solution, output);
            solution.deleteCharAt(solution.length() - 1);
            //System.out.println(solution.toString());
        }

        if(right < left){ // trim: right cannot be larget than left
            solution.append(')');
            //System.out.println(solution.toString());
            findValidParenthesesRecursive(nParentheses, left, right + 1, solution, output);
            solution.deleteCharAt(solution.length() - 1);
            //System.out.println(solution.toString());
        }
    }

    /**
     * Semantics of each layer:
     * one coin type per layer, it represents a decision that how many coins are chosen in the solution.
     *
     * How many states that for each layer:
     * remains / coins[index] branches.
     *
     * Base case (termination rule):
     * (index == coins.length) || (remains == 0);
     *
     * Time Complexity
     * quarter coin level: 5 branches;
     * dime coin level: 11 branches;
     * 5 cent level: 21 branches;
     * 1 cent level: 100 branches;
     *
     * DFS will traverse this whole calling tree once, so the TC ~ 100 x 21 x 11 x 5
     *
     * Space Complexity
     *
     * on Stack:
     * O(height == 4)
     *
     * on heap:
     * O(Constant)
     *
     * ~ O(1) complexity
     *
     */
    public List<List<Integer>> combinations(int target, int[] coins) {

        if(coins == null){
            return null;
        }

        int[] coinNum = new int[coins.length];
        List output = new ArrayList();

        combinationsRecursive(coins, coinNum, target, 0, output);

        return output;
    }

    private void combinationsRecursive(int[] coins, int[] solution, int remains, int index, List<List<Integer> > output){
        // base case 1, solution found
        if(remains == 0) {
            output.add(new ArrayList<Integer>() {{
                for (int i : solution) {
                    add(i);
                }
            }});
            return;
        }

        // base case 2, solution not found
        if(index >= coins.length){
            return;
        }

        for(int nCoins = 0; coins[index] * nCoins <= remains; nCoins++){
            solution[index] = nCoins;
            combinationsRecursive(coins, solution, remains - coins[index] * nCoins, index + 1, output);
            solution[index] = 0;
        }
    }

    /**
     * Problem:
     * Given a string with no duplicate characters, return a list with all permutations of the characters.
     * Assume that input string is not null.
     *
     * Examples
     *
     * Set = “abc”, all permutations are [“abc”, “acb”, “bac”, “bca”, “cab”, “cba”]
     * Set = "", all permutations are [""]
     *
     * High level idea:
     * using DFS.
     *
     * Semantic of each level:
     * each level represents a decision to put a character to a paticular slot. #level == #slot
     *
     * # of states of each level:
     * Despite the chars that is already chosen, the # of states equals to the # of the rest of the characters.
     *
     * Semantic of the swap cur pointer:
     * [0, cur) -> slots that are already set.
     * cur -> slots that is under decision process.
     * (cur, input.length) -> options of chars for cur position
     */
    public List<String> permutations(String input) {

        List<String> res = new ArrayList<>();
        permutationRecursive(input.toCharArray(), 0, res);

        return res;
    }

    private void permutationRecursive(char[] solution, int cur, List<String> output){
        // base case
        if(cur >= solution.length - 1){
            output.add(new String(solution));
            return;
        }

        for(int i = cur; i < solution.length; i++){
            swap(solution, cur, i);
            permutationRecursive(solution, cur + 1, output);
            swap(solution, cur, i);
        }
    }

    /**
     * Given a matrix of size N x M. For each row the elements are sorted in ascending order, and for each column the
     * elements are also sorted in ascending order. Find the Kth smallest number in it.
     *
     * Assumptions
     *
     * the matrix is not null, N > 0 and M > 0
     * K > 0 and K <= N * M
     *
     * Examples
     *
     * { {1,  3,   5,  7},
     *
     *   {2,  4,   8,   9},
     *
     *   {3,  5, 11, 15},
     *
     *   {6,  8, 13, 18} }
     *
     * the 5th smallest number is 4
     * the 8th smallest number is 6
     *
     * High level idea:
     *
     * use BFS method.
     *
     * Data Structure:
     *
     * use a queue to store all the generated node, and visit them in a FIFO manner.
     *
     * Initial Position: left upper conner;
     *
     * generation rule:
     *
     * generate lower and right cells;
     *
     * Deduplication:
     *
     * dedup at generation phase. use a "n x n" sized boolean array.
     *
     * Terminal condition:
     *
     * queue is empty
     *
     *
     *
     */
    public class Cell implements Comparable<Cell>{
        public Integer row;
        public Integer col;
        public Integer value;

        public Cell(int row, int col, int v){
            this.row = row;
            this.col = col;
            this.value = v;
        }

        @Override
        public int compareTo(Cell o) {
           return this.value.compareTo(o.value); // natural ordering of value: small means high priority
        }
    }

    public int kthSmallest(int[][] matrix, int k) {

        Queue<Cell> q = new PriorityQueue<>(); // natural ordering of Cell::value, smaller value has higher priority.
        int colBound = matrix[0].length;
        int rowBound = matrix.length;
        boolean[][] explored = new boolean[rowBound][colBound]; // don't use x,y. use row / col, that's much better.

        // initial position, left-upper corner.
        q.offer(new Cell(0, 0, matrix[0][0]));

        while(q.size() != 0){
            Cell cur = q.poll();
            System.out.println(cur.value);
            if(--k < 1){ // count to kth element
                return cur.value;
            }

            // dedup and generate:
            if(cur.col + 1 < colBound && explored[cur.row][cur.col + 1] == false) { // try to explore lower cell
                Cell lower = new Cell(cur.row, cur.col + 1, matrix[cur.row][cur.col + 1]);
                q.offer(lower);
                explored[cur.row][cur.col + 1] = true;
            }

            if(cur.row + 1 < rowBound && explored[cur.row + 1][cur.col] == false) { // try to explore right cell
                Cell right = new Cell(cur.row + 1, cur.col, matrix[cur.row + 1][cur.col]);
                q.offer(right);
                explored[cur.row + 1][cur.col] = true;
            }
        }

        // k > total # of elements in matrix.
        return Integer.MIN_VALUE;
    }

    private void swap(char[] str, int a, int b){
        char t = str[a];
        str[a] = str[b];
        str[b] = t;
    }

    /**
     * Given two nodes in a binary tree, find their lowest common ancestor.
     *
     * Assumptions
     *
     * There is no parent pointer for the nodes in the binary tree
     *
     * The given two nodes are guaranteed to be in the binary tree
     *
     * Examples
     *
     *         5
     *
     *       /   \
     *
     *      9     12
     *
     *    /  \      \
     *
     *   2    3      14
     *
     * The lowest common ancestor of 2 and 14 is 5
     *
     * The lowest common ancestor of 2 and 9 is 9
     *
     * public class TreeNode {
     *   public int key;
     *   public TreeNode left;
     *   public TreeNode right;
     *   public TreeNode(int key) {
     *     this.key = key;
     *   }
     * }
     */

    //   public TreeNode lowestCommonAncestor(TreeNode root,
    //      TreeNode one, TreeNode two) {
    //    return root;
    //  }

    /**
     * Find the height of binary tree.
     *
     * Examples:
     *
     *         5
     *
     *       /    \
     *
     *     3        8
     *
     *   /   \        \
     *
     * 1      4        11
     *
     *
     * For Example:
     *
     * The sequence [1, 2, 3, #, #, 4] represents the following binary tree:
     *
     *     1
     *
     *   /   \
     *
     *  2     3
     *
     *       /
     *
     *     4
     *
     *  public class TreeNode {
     *    public int key;
     *    public TreeNode left;
     *    public TreeNode right;
     *    public TreeNode(int key) {
     *      this.key = key;
     *    }
     *  }
     */
    //public int findHeight(TreeNode root) {
    //}

    /**
     * Implement a recursive, pre-order traversal of a given binary tree, return the list of keys of each node in the tree as it is pre-order traversed.
     *
     * Examples
     *
     *         5
     *
     *       /    \
     *
     *     3        8
     *
     *   /   \        \
     *
     * 1      4        11
     *
     * Pre-order traversal is [5, 3, 1, 4, 8, 11]
     *
     * For Example:
     *
     * The sequence [1, 2, 3, #, #, 4] represents the following binary tree:
     *
     *     1
     *
     *   /   \
     *
     *  2     3
     *
     *       /
     *
     *     4
     *  public class TreeNode {
     *    public int key;
     *    public TreeNode left;
     *    public TreeNode right;
     *    public TreeNode(int key) {
     *      this.key = key;
     *    }
     *  }
     */
    // public List<Integer> preOrder(TreeNode root) {
    // }

    /**
     * Check if a given binary tree is balanced. A balanced binary tree is one in which the depths of every node’s left and right subtree differ by at most 1.
     *
     * Examples
     *
     *         5
     *
     *       /    \
     *
     *     3        8
     *
     *   /   \        \
     *
     * 1      4        11
     *
     * is balanced binary tree,
     *
     *         5
     *
     *       /
     *
     *     3
     *
     *   /   \
     *
     * 1      4
     *
     * is not balanced binary tree.
     *
     * Corner Cases
     *
     * What if the binary tree is null? Return true in this case.
     *
     */


    /**
     *     Find the K smallest numbers in an unsorted integer array A. The returned numbers should be in ascending order.
     *     Assumptions
     *     * A is not null
     *     * K is >= 0 and smaller than or equal to size of A
     *
     *     Return
     *     * an array with size K containing the K smallest numbers in ascending order
     *
     *     Examples
     *     * A = {3, 4, 1, 2, 5}, K = 3, the 3 smallest numbers are {1, 2, 3} 
     *
     */
    //public int[] kSmallest(int[] array, int k) {
    //}

    /**
     *     Given a set of characters represented by a String, return a list containing all subsets of the characters.
     *     Assumptions
     *     * There are no duplicate characters in the original set.
     *
     *     Examples
     *     * Set = "abc", all the subsets are [“”, “a”, “ab”, “abc”, “ac”, “b”, “bc”, “c”]
     *     * Set = "", all the subsets are [""]
     *     * Set = null, all the subsets are []
     *
     */
//    public List<String> subSets(String input){
//    }

    /**
     *     Given N pairs of parentheses “()”, return a list with all the valid permutations.
     *
     *     Assumptions
     *     * N > 0
     *
     *     Examples
     *     * N = 1, all valid permutations are ["()"]
     *     * N = 3, all valid permutations are ["((()))", "(()())", "(())()", "()(())", "()()()"]
     */
//    public List<String> validParentheses(int n) {
//    }


    /**
     *     Check if a given linked list has a cycle. Return true if it does, otherwise return false.
     *
     *     Assumption:
     *
     *     You can assume there is no duplicate value appear in the linked list.
     *
     * class ListNode {
     *   public int value;
     *   public ListNode next;
     *   public ListNode(int value) {
     *     this.value = value;
     *     next = null;
     *   }
     * }
     */

//    public boolean hasCycle(ListNode head) {
//
//    }

    /**
     * Given a string, remove all leading/trailing/duplicated empty spaces.
     *
     * Attention:
     * please do it in place.
     *
     * Assumptions:
     *
     * The given string is not null.
     * Examples:
     *
     * “  a” --> “a”
     * “   I     love MTV ” --> “I love MTV”
     */

    // public char[] removeSpaces(char[] input) {
    //}


    /**
     * Get all valid ways of putting N Queens on an N * N chessboard so that no two Queens threaten
     * each other.
     *
     * Assumptions
     *
     * N > 0
     * Return
     *
     * A list of ways of putting the N Queens
     * Each way is represented by a list of the Queen's y index for x indices of 0 to (N - 1)
     * Example
     *
     * N = 4, there are two ways of putting 4 queens:
     *
     * [1, 3, 0, 2] --> the Queen on the first row is at y index 1, the Queen on the second row is
     * at y index 3, the Queen on the third row is at y index 0 and the Queen on the fourth row is
     * at y index 2.
     *
     * [2, 0, 3, 1] --> the Queen on the first row is at y index 2, the Queen on the second row is
     * at y index 0, the Queen on the third row is at y index 3 and the Queen on the fourth row is
     * at y index 1.
     */

    //  public List<List<Integer>> nqueens(int n) {
    //    // Write your solution here
    //  }


    /**
     * ----------------------Hard-----------------------
     */

    /**
     * Given a word and a dictionary, determine if it can be composed by concatenating words from the given dictionary.
     *
     * Assumptions
     *
     * The given word is not null and is not empty
     * The given dictionary is not null and is not empty and all the words in the dictionary are not null or empty
     * Examples
     *
     * Dictionary: {“bob”, “cat”, “rob”}
     *
     * Word: “robob” return false
     *
     * Word: “robcatbob” return true since it can be composed by "rob", "cat", "bob"
     */

    //  public boolean canBreak(String input, String[] dict) {
    //    // Write your solution here
    //  }


}

