import home.ludvik.util.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class UtilTest {
    private DebugNodeAllocator allocator = new DebugNodeAllocator();

    @Test
    public void testDebugListNodeAllocator(){
        ListNode node0 = allocator.newListNode(1);
        ListNode node1 = allocator.newListNode(2);
        ListNode node2 = allocator.newListNode(3);
        ListNode node3 = allocator.newListNode(4);

        ListNode node = new ListNode(1);

        node2.next = node1;
        node1.next = node0;
        node3.next = node;

        allocator.printAllListNodes();
    }

    @Test
    public void testTreePopulate(){
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
        allocator.printTree(head);
    }

    @Test
    public void testHeap(){
        List<Integer> input = new ArrayList<Integer>(){{
            add(10);
            add(3);
            add(9);
            add(15);
            add(2);
            add(7);
            add(12);
        }};

        Heap maxHeap = new Heap(new Heap.LargerThan());
        Heap minHeap = new Heap(new Heap.SmallerThan());

        int min = minHeap.addAll(input);
        int max = maxHeap.addAll(input);

        Assert.assertEquals(2, min);
        Assert.assertEquals(15, max);

        min = minHeap.pop();
        Assert.assertEquals(2, min);

        min = minHeap.pop();
        Assert.assertEquals(3, min);

        min = minHeap.add(2);
        Assert.assertEquals(2, min);

        min = minHeap.pop();
        Assert.assertEquals(2, min);

        min = minHeap.pop();
        Assert.assertEquals(7, min);

        min = minHeap.pop();
        Assert.assertEquals(9, min);

        min = minHeap.pop();
        Assert.assertEquals(10, min);

        min = minHeap.pop();
        Assert.assertEquals(12, min);

        min = minHeap.pop();
        Assert.assertEquals(15, min);

        // max heap pop test
        max = maxHeap.pop();
        Assert.assertEquals(15, max);

        max = maxHeap.pop();
        Assert.assertEquals(12, max);

        max = maxHeap.pop();
        Assert.assertEquals(10, max);

        max = maxHeap.add(100);
        Assert.assertEquals(100, max);

        max = maxHeap.pop();
        Assert.assertEquals(100, max);

    }
}
