/**
 * LeetCode 19 - Remove Nth Node From End of List ⭐⭐
 *
 * Remove the nth node from the end and return the head.
 * Example: 1→2→3→4→5, n=2 → 1→2→3→5 (removed 4)
 *
 * TRICK: Two pointers, n nodes apart.
 * When the leader hits null, the follower is at the node BEFORE the target.
 * Use dummy head to handle removing the head node.
 */
public class RemoveNthFromEnd {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode removeNthFromEnd_yours(ListNode head, int n) {
        // TODO: dummy head + two pointers n+1 apart
        return null;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy, slow = dummy;

        // Advance fast n+1 steps so there's a gap of n between fast and slow
        for (int i = 0; i <= n; i++) fast = fast.next;

        // Move both until fast hits null
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // slow.next is the node to remove
        slow.next = slow.next.next;
        return dummy.next;
    }

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
        RemoveNthFromEnd sol = new RemoveNthFromEnd();

        System.out.println(toString(sol.removeNthFromEnd(build(1, 2, 3, 4, 5), 2)));
        // 1→2→3→5

        System.out.println(toString(sol.removeNthFromEnd(build(1), 1)));
        // (empty — removed only node)

        System.out.println(toString(sol.removeNthFromEnd(build(1, 2), 1)));
        // 1

        System.out.println(toString(sol.removeNthFromEnd(build(1, 2), 2)));
        // 2 (removed head)

        System.out.println("\n✓ Manual verification complete");
    }
}
