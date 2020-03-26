package home.ludvik.util;

public class ListNode {
    public int value;
    public ListNode next;
    public ListNode(int value) {
        this.value = value;
        next = null;
    }

    @Override
    public String toString() {
        return Integer.toHexString(hashCode());
    }
}
