package home.ludvik.solutions.linkedlist;

import home.ludvik.util.DebugNodeAllocator;
import home.ludvik.util.ListNode;

public class SolutionLinkedList {

    DebugNodeAllocator allocator;

    public void setAllocator(DebugNodeAllocator allocator) {
        this.allocator = allocator;
    }

    public ListNode reverse(ListNode head) {

        // validation
        if(head == null) {
            return null;
        }

        ListNode prev = null;
        ListNode cur = head;

        while(cur != null){
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        return prev;
    }

    public ListNode reverseRecursive(ListNode head){

        System.out.println("======================reverse(" + head.value + ") start =========================");
        allocator.printAllListNodes();

        //base case
        if(head == null || head.next == null){
            System.out.println("======================reverse(" + head.value + ") end =========================");
            return head;
        }

        ListNode newHead = reverseRecursive(head.next);

        head.next.next = head;
        head.next = null;

        System.out.println("reverse("+ head.value +") postprocess finished. List detail: ");
        allocator.printAllListNodes();
        System.out.println("======================reverse(" + head.value + ") end =========================");

        return newHead;
    }

    public ListNode middleNode(ListNode head){
        // validation and basecase 1
        if(head == null || head.next == null){
            return head;
        }

        // base case 2
        if(head.next.next == null){
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;

        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public boolean hasCycle(ListNode head) {
        // validation
        if(head == null){
            return false;
        }

        // corner case
        if(head.next == null || head.next.next == null){
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while(fast.next != null && fast.next.next != null){

            if(fast.next == slow || fast.next.next == slow){
                return true;
            }

            slow = slow.next;
            fast = fast.next.next;
        }

        return false;
    }

    public ListNode insert(ListNode head, int value) {

        // sample code is much more elegant

        ListNode targetNode = newNode(value);
        ListNode dummy = newNode(Integer.MIN_VALUE);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode cur = dummy.next;

        if(cur == null){
            return targetNode;
        }

        while(cur != null){

            if(value <= cur.value){
                // insert target node before cur
                targetNode.next = cur;
                prev.next = targetNode;
                return dummy.next;
            }
            prev = cur;
            cur = cur.next;
        }

        // cur == null, target is the biggest insert to the tail
        prev.next = targetNode;
        return dummy.next;
    }

    public ListNode merge(ListNode one, ListNode two) {
        //validation
        if(one == null && two == null){
            return null;
        }

        ListNode dummy = newNode(Integer.MIN_VALUE);
        ListNode mergeTail = dummy;
        ListNode prev1 = one;
        ListNode prev2 = two;

        // stops on either end of list
        while(prev1 != null && prev2 != null){
            if(prev1.value < prev2.value){
                mergeTail.next = prev1;
                prev1 = prev1.next;
            }else{
                mergeTail.next = prev2;
                prev2 = prev2.next;
            }

            mergeTail.next.next = null;
            mergeTail = mergeTail.next;
        }

        // append the rest to tail
        if(prev1 != null){
            mergeTail.next = prev1;
        }

        if(prev2 != null){
            mergeTail.next = prev2;
        }

        return dummy.next;
    }

    public ListNode reorder(ListNode head) {
        // validation & base case
        if(head == null || head.next == null || head.next.next == null){
            return head;
        }

        // cut the list into two halves
        ListNode mid = middleNode(head);
        ListNode head1 = head;
        ListNode head2 = mid.next;
        mid.next = null;

        // revert the last half
        head2 = reverse(head2);

        // merge one by one
        ListNode dummy = newNode(Integer.MIN_VALUE);
        ListNode cur = dummy;
        int flag = 0;
        while(head1 != null && head2 != null){
            if(flag == 0){
                cur.next = head1;
                head1 = head1.next;

            }else{
                cur.next = head2;
                head2 = head2.next;
            }
            cur = cur.next;
            cur.next = null;
            flag = (flag + 1) % 2;
        }

        cur.next = (head1 == null) ? head2 : head1;
        return dummy.next;
    }

    public ListNode partition(ListNode head, int target) {

        //validation & base case
        if(head == null || head.next == null){
            return head;
        }

        // use two dummy heads and pointers to transverse.
        ListNode dummy1 = newNode(Integer.MIN_VALUE);
        ListNode c1 = dummy1;
        ListNode dummy2 = newNode(Integer.MIN_VALUE);
        ListNode c2 = dummy2;

        while(head != null){
            if(head.value < target){
                c1.next = head;
                head = head.next;
                c1 = c1.next;
                c1.next = null;
            }else{
                c2.next = head;
                head = head.next;
                c2 = c2.next;
                c2.next = null;
            }
        }

        // concat
        c1.next = dummy2.next;

        return dummy1.next;
    }

    private ListNode newNode(int value){
        ListNode node = allocator.newListNode(value);
        node.next = null;
        return node;
    }
}
