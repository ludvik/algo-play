package home.ludvik.solutions.tree;

import home.ludvik.util.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * public class TreeNode {
 *   public int key;
 *   public TreeNode left;
 *   public TreeNode right;
 *   public TreeNode(int key) {
 *     this.key = key;
 *   }
 * }
 */
public class SolutionTree {
    public List<Integer> getRange(TreeNode root, int min, int max) {
        if(root == null){
            return new ArrayList<Integer>(0);
        }

        List<Integer> list = new ArrayList<>(0);
        getRangeRecursive(root, list, min, max);
        return list;
    }

    private void getRangeRecursive(TreeNode root, List<Integer> list, int min, int max){
        // base case
        if (root == null){
            return;
        }

        // in order tranverse
        if(min <= root.key){
            getRangeRecursive(root.left, list, min, root.key < max ? root.key : max);
        }

        if(min <= root.key && root.key <= max){
            list.add(root.key);
        }

        if(max >= root.key){
            getRangeRecursive(root.right, list, root.key > min ? root.key : min, max);
        }
    }
}

