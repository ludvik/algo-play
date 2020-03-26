package home.ludvik.util;

import java.util.*;

public class Heap {// MaxHeap

    private final List<Integer> buff = new ArrayList<Integer>(0);
    private Comparator comparator;
    Queue q = new PriorityQueue();

    public Heap(Comparator comparator){
        this.comparator = comparator;
    }

    public int pop(){
        Integer head = buff.get(0);
        swap(0, buff.size() - 1);
        buff.remove(buff.size() - 1);
        heapifyRecursive(0);

        return head;
    }

    public int add(Integer num){
        buff.add(num);
        int curIdx = buff.size() - 1;
        int parentIdx = (curIdx - 1) / 2;

        int cur = buff.get(curIdx);
        int parent = buff.get(parentIdx);

        while(curIdx > 0 && comparator.compare(parent , cur) == false){
            swap(curIdx, (curIdx - 1) / 2);

            curIdx = (curIdx - 1) / 2;
            parentIdx = (curIdx - 1) / 2;

            cur = buff.get(curIdx);
            parent = buff.get(parentIdx);
        }

        return buff.get(0);
    }

    public int addAll(List<Integer> input){

        // load all into inner buf
        buff.addAll(input);

        // heapify the whole buf
        heapifyRecursive(0);

        return buff.get(0);
    }

    public int[] toArray(){
        int[] out = new int[buff.size()];
        for(int i = 0; i < buff.size(); i++){
            out[i] = buff.get(i);
        }
        Arrays.sort(out);
        return out;
    }

    private void heapifyRecursive(int rootIndex){

        // base case 1
        if(rootIndex >= buff.size()){
            return;
        }

        int leftIdx = rootIndex * 2 + 1;
        int rightIdx = rootIndex * 2 + 2;

        // base case 2
        if(leftIdx >= buff.size() && rightIdx >= buff.size()){
            return;
        }

        // heapify left and right subtree
        heapifyRecursive(leftIdx);
        heapifyRecursive(rightIdx);

        // heapify current level
        if(leftIdx < buff.size()){
            Integer root = buff.get(rootIndex);
            Integer left = buff.get(leftIdx);
            if(comparator.compare(root, left) == false){
                swap(rootIndex, leftIdx);
            }
        }

        if(rightIdx < buff.size()){
            Integer root = buff.get(rootIndex);
            Integer right = buff.get(rightIdx);
            if(comparator.compare(root, right) == false){
                swap(rootIndex, rightIdx);
            }
        }

    }

    private void swap(int idx1, int idx2){
        Integer tmp = buff.get(idx1);
        buff.set(idx1, buff.get(idx2));
        buff.set(idx2, tmp);
    }

    // Comparator definition
    public interface Comparator{
        // this is a heap that every compare(root, child) == true;
        public boolean compare(Integer a, Integer b);
    }

    public static class LargerThan implements Comparator{
        @Override
        public boolean compare(Integer a, Integer b) {
            return a > b;
        }
    }

    public static class SmallerThan implements Comparator{
        @Override
        public boolean compare(Integer a, Integer b) {
            return a < b;
        }
    }
}
