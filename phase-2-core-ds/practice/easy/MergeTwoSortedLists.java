/**
 * LeetCode 21 - Merge Two Sorted Lists ⭐⭐
 *
 * Merge two sorted linked lists into one sorted list.
 * Example: 1→2→4, 1→3→4 → 1→1→2→3→4→4
 *
 * PATTERN: Dummy head + compare + stitch
 * This is the building block for Merge K Sorted Lists (LC 23).
 */
public class MergeTwoSortedLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeTwoLists_yours(ListNode list1, ListNode list2) {
        // TODO: dummy head, compare, advance the smaller one
        return null;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        tail.next = (list1 != null) ? list1 : list2;
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
        MergeTwoSortedLists sol = new MergeTwoSortedLists();

        System.out.println(toString(sol.mergeTwoLists(build(1, 2, 4), build(1, 3, 4))));
        // 1→1→2→3→4→4

        System.out.println(toString(sol.mergeTwoLists(null, build(0))));
        // 0

        System.out.println(toString(sol.mergeTwoLists(null, null)));
        // (empty)

        System.out.println(toString(sol.mergeTwoLists(build(5), build(1, 2, 3))));
        // 1→2→3→5

        System.out.println("\n✓ Manual verification complete");
    }
}
