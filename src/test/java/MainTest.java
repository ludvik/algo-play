import home.ludvik.solutions.dp.SolutionDP;
import home.ludvik.solutions.graph.SolutionGraph;
import home.ludvik.solutions.linkedlist.SolutionLinkedList;
import home.ludvik.solutions.recursionandsorting.SolutionMergeSort;
import home.ludvik.solutions.recursionandsorting.SolutionPointerSegmentation;
import home.ludvik.solutions.recursionandsorting.SolutionQuickSort;
import home.ludvik.solutions.string.SolutionString;
import home.ludvik.solutions.tree.SolutionTree;
import home.ludvik.util.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainTest {

    private SolutionBinarySearch slnBinarySearch;
    private SolutionMergeSort slnMergeSort;
    private SolutionQuickSort slnQuickSort;
    private SolutionPointerSegmentation slnPtrSeg;
    private SolutionLinkedList slnLinkedList;
    private SolutionTree slnTree;
    private SolutionGraph slnGraph;
    private SolutionString slnString;
    private SolutionDP slnDp;

    private DebugNodeAllocator allocator = new DebugNodeAllocator();

    @Before
    public void setup(){
        slnBinarySearch = new SolutionBinarySearch();
        slnMergeSort = new SolutionMergeSort();
        slnQuickSort = new SolutionQuickSort();
        slnPtrSeg = new SolutionPointerSegmentation();
        slnLinkedList = new SolutionLinkedList();
        slnTree = new SolutionTree();
        slnGraph = new SolutionGraph();
        slnString = new SolutionString();
        slnDp = new SolutionDP();
    }

    @Test
    public void testSearch(){
        Dictionary dict = new SimpleDictionary(new Integer[]{1, 3, 4, 4, 6, 10, 11, 12, 15, 15});

        int indice = slnBinarySearch.search(dict, 6);

        Assert.assertEquals(4, indice);
    }

    @Test
    public void testMergeSort_Normal(){
        int[] ary = new int[]{2,5,6,3,4,5,8,9};

        int[] sorted = slnMergeSort.mergeSort(ary);

        System.out.print("final : ");
        IntStream.of(sorted).forEach(System.out::print);
        System.out.println(" ");
        Assert.assertArrayEquals(new int[]{2,3,4,5,5,6,8,9}, sorted);
    }

    @Test
    public void testMergeSort_OneElement(){
        int[] ary = new int[]{2};

        int[] sorted = slnMergeSort.mergeSort(ary);

        System.out.print("final : ");
        IntStream.of(sorted).forEach(System.out::print);
        System.out.println(" ");
        Assert.assertArrayEquals(new int[]{2}, sorted);
    }

    @Test
    public void testMove0s_Normal(){
        int[] ary = new int[]{2,5,6,8,4,5,3,9};

        int[] sorted = slnQuickSort.quickSort(ary);

        System.out.print("final : ");
        IntStream.of(sorted).forEach(num -> System.out.print(num + ", "));
        System.out.println();
        Assert.assertArrayEquals(new int[]{2,3,4,5,5,6,8,9}, sorted);
    }

    @Test
    public void testMoveZero(){
        int[] ary1 = new int[]{0,2};

        int[] move = slnPtrSeg.moveZero(ary1);

        System.out.print("final1 : ");
        ArrayHelpers.printArray(move);
        System.out.println();
        Assert.assertArrayEquals(new int[]{2,0}, move);

        ary1 = new int[]{2,3,0,9,0,7,7,6,8,0,8};

        move = slnPtrSeg.moveZero(ary1);

        System.out.print("final2 : ");
        ArrayHelpers.printArray(move);
        System.out.println();
        //Assert.assertArrayEquals(new int[]{2,0}, move);

    }

    // corner cases:
    // 1. empty or one element -> do nothing
    // 2. only two elements [r,r]/[b,b]/[g,b]/[g,r]
    // 3. three elements [b,r,r,]/[b,b,r]/[g,g,b]/[g,g,r]
    // normal case:
    // [b,r,g,g,r,b,b,r,g]
    @Test
    public void testRainbowSort(){
        int[] ary1 = new int[]{-1,-1};
        int[] rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[-1, -1]", rtn);
        Assert.assertArrayEquals(new int[]{-1,-1}, rtn);

        ary1 = new int[]{1,-1};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[1, -1]", rtn);
        Assert.assertArrayEquals(new int[]{-1, 1}, rtn);

        ary1 = new int[]{1,0};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[1, 0]", rtn);
        Assert.assertArrayEquals(new int[]{0,1}, rtn);

        ary1 = new int[]{0,0};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[0, 0]", rtn);
        Assert.assertArrayEquals(new int[]{0,0}, rtn);

        ary1 = new int[]{1,1};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[1, 1]", rtn);
        Assert.assertArrayEquals(new int[]{1,1}, rtn);

        ary1 = new int[]{1,-1, -1};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[1, -1, -1]", rtn);
        Assert.assertArrayEquals(new int[]{-1, -1, 1}, rtn);

        ary1 = new int[]{1, 0, -1};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[1, 0, -1]", rtn);
        Assert.assertArrayEquals(new int[]{-1, 0, 1}, rtn);

        ary1 = new int[]{1, 1, 0};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[1, 1, 0]", rtn);
        Assert.assertArrayEquals(new int[]{0, 1, 1}, rtn);

        ary1 = new int[]{1, -1, 0, 1, 1, -1 ,0};
        rtn = slnPtrSeg.rainbowSort(ary1);
        ArrayHelpers.printArray("Case[1, -1, 0, 1, 1, -1 ,0]", rtn);
        Assert.assertArrayEquals(new int[]{-1, -1, 0, 0, 1, 1, 1}, rtn);
    }

    @Test
    public void testReverseLinedList(){
        DebugNodeAllocator allocator = new DebugNodeAllocator();

        // 4 nodes
        ListNode node0 = allocator.newListNode(1);
        ListNode node1 = allocator.newListNode(2);
        ListNode node2 = allocator.newListNode(3);
        ListNode node3 = allocator.newListNode(4);

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = null;

        System.out.println("before:");
        allocator.printAllListNodes();

        ListNode head = slnLinkedList.reverse(node0);
        Assert.assertEquals(node3, head);

        System.out.println("after:");
        allocator.printAllListNodes();

        // 1 node
        node3.next = null;
        head = slnLinkedList.reverse(node3);
        Assert.assertEquals(node3, head);

        System.out.println("one node after:");
        allocator.printAllListNodes();

    }

    @Test
    public void testReverseLinedListRecursive() {

        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 4 nodes
        ListNode node0 = allocator.newListNode(0);
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = null;

        ListNode head = slnLinkedList.reverseRecursive(node0);
        Assert.assertEquals(node3, head);
    }

    @Test
    public void testFindMiddleNode() {

        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 4 nodes
        ListNode node0 = allocator.newListNode(0);
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = null;

        ListNode middle = slnLinkedList.middleNode(node0);
        Assert.assertEquals(node1, middle);
    }

    @Test
    public void testHasCircle() {

        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 4 nodes
        ListNode node0 = allocator.newListNode(0);
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);
        ListNode node4 = allocator.newListNode(4);
        ListNode node5 = allocator.newListNode(5);

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node2;

        boolean hasCircle = slnLinkedList.hasCycle(node0);
        Assert.assertEquals(true, hasCircle);

        // 2 nodes
        node1.next = node0;
        hasCircle = slnLinkedList.hasCycle(node0);
        Assert.assertEquals(true, hasCircle);

        // 1 node
        node0.next = node0;
        hasCircle = slnLinkedList.hasCycle(node0);
        Assert.assertEquals(true, hasCircle);

        // no circle
        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        hasCircle = slnLinkedList.hasCycle(node0);
        Assert.assertEquals(false, hasCircle);
    }

    @Test
    public void testInsertToSortedLinkedList(){
        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 4 nodes
        ListNode node0 = allocator.newListNode(0);
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);
        ListNode node4 = allocator.newListNode(4);
        ListNode node5 = allocator.newListNode(5);

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        ListNode head = slnLinkedList.insert(node0, -1);
        Assert.assertNotEquals(node0, head);
        allocator.printAllListNodes();

        head = slnLinkedList.insert(head, 7);
        allocator.printAllListNodes();

        head = slnLinkedList.insert(head, 6);
        allocator.printAllListNodes();
    }

    @Test
    public void testMergeTwoSortedLL(){
        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 4 nodes
        ListNode node0 = allocator.newListNode(0);
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);
        ListNode node4 = allocator.newListNode(4);
        ListNode node5 = allocator.newListNode(5);

        // normal case
        node0.next = node1;
        node1.next = node2;
        node2.next = null;

        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        System.out.println("normal case");
        allocator.printAllListNodes();
        ListNode head = slnLinkedList.merge(node0, node3);
        allocator.printAllListNodes();
        allocator.printLL(head);

        // one null
        node0.next = node1;
        node1.next = node2;
        node2.next = null;

        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        System.out.println("one null");
        head = slnLinkedList.merge(node0, null);
        allocator.printLL(head);

        System.out.println("both null");
        head = slnLinkedList.merge(null, null);
        Assert.assertEquals(null, head);
    }

    @Test
    public void testReorderLL(){
        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 6 nodes
        ListNode node0 = allocator.newListNode(0);
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);
        ListNode node4 = allocator.newListNode(4);
        ListNode node5 = allocator.newListNode(5);

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        System.out.println("6 nodes");
        slnLinkedList.reorder(node0);
        allocator.printLL(node0);

        System.out.println("3 nodes");
        node0.next = node1;
        node1.next = node2;
        node2.next = null;
        slnLinkedList.reorder(node0);
        allocator.printLL(node0);

        System.out.println("2 nodes");
        node0.next = node1;
        node1.next = null;
        slnLinkedList.reorder(node0);
        allocator.printLL(node0);
    }

    @Test
    public void testReorder(){

        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 6 nodes
        ListNode node0 = allocator.newListNode(0);
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);
        ListNode node4 = allocator.newListNode(4);
        ListNode node5 = allocator.newListNode(5);

        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;

        System.out.println("6 nodes");
        ListNode head = slnLinkedList.reorder(node0);
        allocator.printLL(node0);

        // 3 nodes
        node0.next = node1;
        node1.next = node2;
        node2.next = null;

        System.out.println("3 nodes");
        head = slnLinkedList.reorder(node0);
        allocator.printLL(node0);

        // 2 nodes
        node0.next = node1;
        node1.next = null;

        System.out.println("2 nodes");
        head = slnLinkedList.reorder(node0);
        allocator.printLL(node0);
    }

    @Test
    public void testPartition() {

        DebugNodeAllocator allocator = new DebugNodeAllocator();
        slnLinkedList.setAllocator(allocator);

        // 6 nodes
        ListNode node1 = allocator.newListNode(1);
        ListNode node2 = allocator.newListNode(2);
        ListNode node3 = allocator.newListNode(3);
        ListNode node4 = allocator.newListNode(4);
        ListNode node5 = allocator.newListNode(5);
        ListNode node6 = allocator.newListNode(6);

        node2.next = node4;
        node4.next = node3;
        node3.next = node5;
        node5.next = node1;
        node1.next = null;

        ListNode head = slnLinkedList.partition(node2, 3);
        allocator.printLL(head);
    }

    @Test
    public void testIsBTComplete(){
        List<Integer> input = new ArrayList<Integer>(){{
            add(0);
            add(1);
            add(2);
            add(Integer.MIN_VALUE);
            add(3);
            add(4);
            add(5);
        }};
        TreeNode head = allocator.populateTree(input);

        boolean isComplete = slnGraph.isBTCompleted(head);
        Assert.assertEquals(false, isComplete);

        List<Integer> input1 = new ArrayList<Integer>(){{
            add(0);
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};
        TreeNode head1 = allocator.populateTree(input1);
        Assert.assertEquals(true, slnGraph.isBTCompleted(head1));
    }

    @Test
    public void testKSmallest(){
        int[] input = new int[]{10,23,12,3,4,3,2};
        int[] out = slnGraph.kSmallest(input, 5);

        System.out.println(Arrays.toString(out));
    }

    @Test
    public void testLayerByLayer(){
        List<Integer> input = new ArrayList<Integer>(){{
            add(0);
            add(1);
            add(2);
            add(Integer.MIN_VALUE);
            add(3);
            add(Integer.MIN_VALUE);
            add(4);
            add(Integer.MIN_VALUE);
            add(Integer.MIN_VALUE);
            add(5);
        }};
        TreeNode head = allocator.populateTree(input);

        allocator.printTree(head);

        List<List<Integer>> res = slnGraph.layerByLayer(head);
        int i = 1;
        for(List<Integer> l : res){
            System.out.print("Level " + i + " : ");
            for(Integer e : l){
                System.out.print(e + ", ");
            }
            System.out.println();
            i++;
        }
    }

    @Test
    public void testBipartile(){
        GraphNode node1 = new GraphNode(1);
        GraphNode node2 = new GraphNode(2);
        GraphNode node3 = new GraphNode(3);
        GraphNode node4 = new GraphNode(4);
        GraphNode node5 = new GraphNode(5);
        GraphNode node6 = new GraphNode(6);
        GraphNode node7 = new GraphNode(7);
        List<GraphNode> graph = new ArrayList<>();

        node1.neighbors.add(node2);
        node1.neighbors.add(node3);
        node2.neighbors.add(node1);
        node2.neighbors.add(node5);
        node3.neighbors.add(node1);
        node3.neighbors.add(node4);
        node4.neighbors.add(node3);
        node4.neighbors.add(node6);
        node5.neighbors.add(node2);
        node6.neighbors.add(node4);
        node7.neighbors.add(node4);

        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        graph.add(node6);
        graph.add(node7);

        boolean res = slnGraph.isBipartite(graph);
        Assert.assertEquals(true, res);

        node3.neighbors.add(node6);
        res = slnGraph.isBipartite(graph);
        Assert.assertEquals(false, res);

        // two sub graph
        node1 = new GraphNode(1);
        node2 = new GraphNode(2);
        node3 = new GraphNode(3);
        node4 = new GraphNode(4);
        node5 = new GraphNode(5);
        node6 = new GraphNode(6);
        node7 = new GraphNode(7);

        graph = new ArrayList<>();
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        graph.add(node6);
        graph.add(node7);

        node1.neighbors.add(node2);
        node2.neighbors.add(node3);
        node3.neighbors.add(node4);

        node5.neighbors.add(node6);
        node5.neighbors.add(node7);
        node6.neighbors.add(node5);
        node6.neighbors.add(node7);
        node7.neighbors.add(node5);
        node7.neighbors.add(node6);

        res = slnGraph.isBipartite(graph);
        Assert.assertEquals(false, res);

        /**
         *                         7
         *                       /   \
         *  1 -> 2 -> 3 -> 4 -> 5 -  6
         *
         */
        node1 = new GraphNode(1);
        node2 = new GraphNode(2);
        node3 = new GraphNode(3);
        node4 = new GraphNode(4);
        node5 = new GraphNode(5);
        node6 = new GraphNode(6);
        node7 = new GraphNode(7);

        graph = new ArrayList<>();
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        graph.add(node6);
        graph.add(node7);

        node1.neighbors.add(node2);
        node2.neighbors.add(node3);
        node3.neighbors.add(node4);
        node4.neighbors.add(node5);

        node5.neighbors.add(node6);
        node5.neighbors.add(node7);
        node6.neighbors.add(node5);
        node6.neighbors.add(node7);
        node7.neighbors.add(node5);
        node7.neighbors.add(node6);

        res = slnGraph.isBipartite(graph);
        Assert.assertEquals(false, res);
    }

    @Test
    public void testFindSubset(){
        List<String> output = slnGraph.subSets(null);
        output.stream().forEach(s -> System.out.println(s));
    }

    @Test
    public void testValidParentheses(){
        List<String> output = slnGraph.validParentheses(3);
        output.stream().forEach(s -> System.out.println(s));
    }

    @Test
    public void testCombinations(){
        int[] coins = new int[]{469,441,365,301,269,169,104,79,23};
        int target = 169;

        List<List<Integer> > output = slnGraph.combinations(target, coins);

        output.stream().forEach(solution -> {
            System.out.print("[");

            int total = 0;
            int index = 0;
            for(Integer n : solution){
                System.out.print(n + ", ");
                total += coins[index++] * n;
            }
            Assert.assertEquals(target, total);
            System.out.println("]");
        });
    }

    @Test
    public void testPermutations(){
        List<String> res = slnGraph.permutations("abc");
        res.stream().forEach(System.out::println);
    }

    @Test
    public void testTopKFrequent(){
        String[] words = new String[]{"d","a","c","b","d","a","b","b","a","d","d","a","d"};
        String[] topK = slnString.topKFrequent(words, 5);

        for (String word : topK) {
            System.out.println(word);
        }
    }

    @Test
    public void testMissing(){
        int[] input = new int[]{2,3,4,5};
        int missing = slnString.missing(input);

        Assert.assertEquals(1, missing);
    }

    @Test
    public void testCommon(){
        int[] a = new int[]{1,1,2,3};
        int[] b = new int[]{1,1,1,1,1,1,1,1,1,2,3};

        List<Integer> res = slnString.common(a, b);
        res.stream().forEach(i -> System.out.println(i));
    }

    @Test
    public void testRemoveStr(){
        String input = new String("aabbaabaa");
        String ex = new String("bbb");

        String res = slnString.remove(input, ex);
        Assert.assertEquals("aaaaaa", res);

        String ex1 = new String("ab");
        res = slnString.remove(input, ex1);
        Assert.assertEquals("", res);
    }

    @Test
    public void testRemoveSpace(){
        String input = new String(" I   love    you!!!     ");
        String res = slnString.removeSpaces(input);

        Assert.assertEquals("I love you!!!", res);
    }

    @Test
    public void testDeDupeString(){
        String input = new String( "aaaabbbc");
        String res = slnString.deDup(input);

        Assert.assertEquals("abc", res);
    }

    @Test
    public void testKthSmallest(){

        int[][] matrix = new int[][]{
                {1,2,3,5},
                {2,3,4,8},
                {3,5,7,9},
                {5,7,9,11}
        };

        int res = slnGraph.kthSmallest(matrix, 16);

        Assert.assertEquals(11, res);


        matrix = new int[][]{
                {1,2}
        };

        res = slnGraph.kthSmallest(matrix, 2);

        Assert.assertEquals(2, res);

    }

    @Test
    public void testReverseString(){
        String test = new String("123456");
        String res = slnString.reverse(test);

        Assert.assertEquals("654321", res);
    }

    @Test
    public void testReverseWords(){
        String test = new String("an apple");
        String res = slnString.reverseWords(test);

        Assert.assertEquals("apple an", res);
    }

    @Test
    public void testDpFibonacci(){
        Assert.assertEquals(2, slnDp.fibonacci(3));
    }

    @Test
    public void testLongestAscendingArrayLength(){
        int[] ary = new int[]{1, 2, 3, 5, 3, 2, 8, 7};
        Assert.assertEquals(4, slnDp.longestAscending(ary));

        int[] ary1 = new int[]{1, 2, 3, 5, 3, 2, 8, 7, 9, 10};
        Assert.assertEquals(4, slnDp.longestAscending(ary1));

        int[] ary2 = new int[]{1, 2, 3, 5, 3, 2, 8, 7, 9};
        Assert.assertEquals(4, slnDp.longestAscending(ary2));

        int[] ary3 = new int[]{1, 2, 4, 5, 3, 2, 8, 9, 10, 11, 3};
        Assert.assertEquals(5, slnDp.longestAscending(ary3));

        int[] ary4 = new int[0];
        Assert.assertEquals(0, slnDp.longestAscending(ary4));

        Assert.assertEquals(0, slnDp.longestAscending(null));
    }

    @Test
    public void testCutRope(){
        System.out.println("0 -> " + slnDp.cutRope(0));
        System.out.println("1 -> " + slnDp.cutRope(1));
        System.out.println("2 -> " + slnDp.cutRope(2));
        System.out.println("3 -> " + slnDp.cutRope(3));
        System.out.println("4 -> " + slnDp.cutRope(4));
        System.out.println("5 -> " + slnDp.cutRope(5));
        System.out.println("6 -> " + slnDp.cutRope(6));
    }

    @Test
    public void testArrayHopper(){
        int[] ary = new int[0];
        Assert.assertEquals(false, slnDp.canJump(ary));

        int[] ary1 = new int[]{3};
        Assert.assertEquals(true, slnDp.canJump(ary1));

        int[] ary2 = new int[]{1, 3};
        Assert.assertEquals(true, slnDp.canJump(ary2));

        int[] ary3 = new int[]{3, 3};
        Assert.assertEquals(true, slnDp.canJump(ary3));

        int[] ary4 = new int[]{1, 2, 0, 3};
        Assert.assertEquals(true, slnDp.canJump(ary4));

        int[] ary5 = new int[]{1, 2, 0, 0, 3};
        Assert.assertEquals(false, slnDp.canJump(ary5));

    }

    @Test
    public void testArrayHopperII(){

        int[] ary1 = new int[]{3};
        Assert.assertEquals(0, slnDp.minJump(ary1));

        int[] ary2 = new int[]{1, 3};
        Assert.assertEquals(1, slnDp.minJump(ary2));

        int[] ary3 = new int[]{3, 3};
        Assert.assertEquals(1, slnDp.minJump(ary3));

        int[] ary4 = new int[]{1, 2, 0, 3};
        Assert.assertEquals(2, slnDp.minJump(ary4));

        int[] ary5 = new int[]{1, 2, 0, 0, 3};
        Assert.assertEquals(-1, slnDp.minJump(ary5));
    }
}
