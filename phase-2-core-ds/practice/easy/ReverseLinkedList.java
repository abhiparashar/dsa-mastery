/**
 * LeetCode 206 - Reverse Linked List ⭐⭐
 *
 * Reverse a singly linked list.
 * Example: 1→2→3→4→5 → 5→4→3→2→1
 *
 * IF YOU CAN'T DO THIS IN YOUR SLEEP, PRACTICE UNTIL YOU CAN.
 * This is the most fundamental linked list operation.
 *
 * ITERATIVE: Three-pointer dance (prev, curr, next)
 * RECURSIVE: "My next node should point back to me"
 */
public class ReverseLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // YOUR SOLUTION
    public ListNode reverseList_yours(ListNode head) {
        // TODO: three pointers: prev, curr, next
        return null;
    }

    // ITERATIVE — O(n) time, O(1) space
    public ListNode reverseList(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next; // save next
            curr.next = prev;          // reverse pointer
            prev = curr;               // advance prev
            curr = next;               // advance curr
        }
        return prev; // prev is the new head
    }

    // RECURSIVE — O(n) time, O(n) space (call stack)
    public ListNode reverseList_recursive(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList_recursive(head.next);
        head.next.next = head; // my next node points back to me
        head.next = null;      // I'm now the tail
        return newHead;
    }

    // HELPERS
    static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int v : vals) { curr.next = new ListNode(v); curr = curr.next; }
        return dummy.next;
    }

    static String toString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append("→");
            head = head.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ReverseLinkedList sol = new ReverseLinkedList();

        System.out.println(toString(sol.reverseList(build(1, 2, 3, 4, 5))));
        // 5→4→3→2→1

        System.out.println(toString(sol.reverseList(build(1, 2))));
        // 2→1

        System.out.println(toString(sol.reverseList(build(1))));
        // 1

        System.out.println(toString(sol.reverseList(null)));
        // (empty)

        System.out.println("\n✓ Manual verification complete");
    }
}
