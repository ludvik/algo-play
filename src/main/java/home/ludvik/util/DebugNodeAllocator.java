package home.ludvik.util;

import java.util.*;

public class DebugNodeAllocator {

    // for Linked List
    private final List<ListNode> nodesList = new ArrayList<>();
    private final Map<ListNode, Integer> node2NodeName = new HashMap<>();

    public ListNode newListNode(int value){
        ListNode node = new ListNode(value);
        nodesList.add(node);
        node2NodeName.put(node, nodesList.size() - 1);
        return node;
    }

    public void printAllListNodes(){

        System.out.println(">>>>>>>>>>>>>>>>>>>>>");
        for(int i = 0; i < nodesList.size(); i++){
            ListNode node = nodesList.get(i);
            Integer nextNodeName = node2NodeName.get(node.next);

            System.out.println("Node" + i + "(" + node.value + ") --> "
                    + (nextNodeName != null ? ("Node" + nextNodeName + "(" + node.next.value + ")" )  : "NULL"));
        }
    }

    public void printLL(ListNode head){
        if(head == null){
            System.out.println("NULL head!");
        }

        System.out.println("-------------");
        while(head != null){
            System.out.print(head.value + " -> ");
            head = head.next;
        }
        System.out.println();
    }

    // for Binary Tree
    public TreeNode populateTree(List<Integer> input){

        assert(input != null);
        assert(input.size() > 0);

        TreeNode head = new TreeNode(input.get(0));
        fillNodeRecursively(head, input, 0);

        return head;
    }

    private void fillNodeRecursively(TreeNode cur, List<Integer> input, int index){
        if(index >= input.size()){//base case
            return;
        }

        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;

        if( leftChild < input.size() && input.get(leftChild) != Integer.MIN_VALUE ){
            TreeNode node = new TreeNode(input.get(leftChild));
            cur.left = node;
            fillNodeRecursively(node, input, leftChild);
        }

        if( rightChild < input.size() && input.get(rightChild) != Integer.MIN_VALUE){
            TreeNode node = new TreeNode(input.get(rightChild));
            cur.right = node;
            fillNodeRecursively(node, input, rightChild);
        }
    }

    private static final int COUNT = 10;
    private void print2DUtil(TreeNode root, int space)
    {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.key + "\n");

        // Process left child
        print2DUtil(root.left, space);
    }

    // Wrapper over print2DUtil()
    public void printTree(TreeNode root)
    {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }
}
