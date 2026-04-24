# Phase 2.1: LinkedList — Pointer Manipulation

> **First Principle**: A linked list trades O(1) random access for O(1) insert/delete (when you have the node). It exists because arrays are expensive to resize and insert into. Understanding linked lists is really about understanding *pointer manipulation* — the skill transfers to trees, graphs, and any node-based structure.

---

## Why Linked Lists Matter

Linked list problems are **pointer puzzles**. Interviewers don't care if you use linked lists in production (you probably shouldn't). They care if you can:
1. Manipulate pointers without losing references
2. Handle edge cases (null, single node, cycles)
3. Think about space — can you do this without extra storage?

---

## Mental Model

```
Array:  [10][20][30][40] — contiguous memory, index = direct access

LinkedList: [10|→] → [20|→] → [30|→] → [40|null]
            Each node stores: value + pointer to next
            No index. To reach node 3, you must walk from head.
```

### Java Implementation (Internalize This)

```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
```

---

## The Dummy Head Technique (Use This Always)

**The Problem**: Operations on the head of a linked list are special cases. The head has no "previous" node.

**The Solution**: Create a fake node before the head. This eliminates all special cases.

```java
// Without dummy: messy head handling
ListNode deleteVal(ListNode head, int val) {
    while (head != null && head.val == val) head = head.next; // special case
    ListNode curr = head;
    while (curr != null && curr.next != null) {
        if (curr.next.val == val) curr.next = curr.next.next;
        else curr = curr.next;
    }
    return head;
}

// With dummy: clean and uniform
ListNode deleteVal(ListNode head, int val) {
    ListNode dummy = new ListNode(0, head);
    ListNode prev = dummy;
    while (prev.next != null) {
        if (prev.next.val == val) prev.next = prev.next.next;
        else prev = prev.next;
    }
    return dummy.next;
}
```

---

## Core Patterns

### Pattern 1: Fast and Slow Pointers (Floyd's Tortoise & Hare)

**First Principle**: If slow moves 1 step and fast moves 2 steps, fast reaches the end when slow is at the middle. If there's a cycle, they MUST meet.

**Why they must meet in a cycle**: Think of it as fast chasing slow on a circular track. Fast gains 1 step per iteration. The gap closes by 1 each time. They must collide.

```java
// Find middle of linked list (LC 876)
ListNode middleNode(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow; // slow is at the middle
}

// Detect cycle (LC 141)
boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}

// Find cycle start (LC 142) — the math is beautiful
// When slow and fast meet: slow traveled d + k, fast traveled d + k + C (cycle length)
// Since fast = 2 * slow: d + k + C = 2(d + k) → C = d + k → d = C - k
// So if you start one pointer at head and one at meeting point, they meet at cycle start!
ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) {
            ListNode finder = head;
            while (finder != slow) {
                finder = finder.next;
                slow = slow.next;
            }
            return finder;
        }
    }
    return null;
}
```

### Pattern 2: Reverse a Linked List

**This is the most fundamental linked list operation.** If you can't do this in your sleep, practice until you can.

```java
// Iterative reverse (LC 206) — the three-pointer dance
ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next; // save next
        curr.next = prev;          // reverse pointer
        prev = curr;               // advance prev
        curr = next;               // advance curr
    }
    return prev;
}

// Recursive reverse — elegant but uses O(n) stack space
ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newHead = reverseList(head.next);
    head.next.next = head; // the magic line: make my next node point back to me
    head.next = null;      // I'm now the tail
    return newHead;
}

// Reverse between positions left and right (LC 92)
ListNode reverseBetween(ListNode head, int left, int right) {
    ListNode dummy = new ListNode(0, head);
    ListNode prev = dummy;
    
    for (int i = 1; i < left; i++) prev = prev.next;
    
    ListNode curr = prev.next;
    for (int i = 0; i < right - left; i++) {
        ListNode next = curr.next;
        curr.next = next.next;
        next.next = prev.next;
        prev.next = next;
    }
    return dummy.next;
}
```

### Pattern 3: Merge Two Sorted Lists

```java
// LC 21 — asked at literally every company
ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            tail.next = l1;
            l1 = l1.next;
        } else {
            tail.next = l2;
            l2 = l2.next;
        }
        tail = tail.next;
    }
    tail.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

### Pattern 4: Kth from End

**Trick**: Two pointers, `n` nodes apart. When the leader hits null, the follower is at kth from end.

```java
// Remove Nth Node From End (LC 19) ⭐
ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    ListNode fast = dummy, slow = dummy;
    
    for (int i = 0; i <= n; i++) fast = fast.next;
    
    while (fast != null) {
        fast = fast.next;
        slow = slow.next;
    }
    slow.next = slow.next.next;
    return dummy.next;
}
```

---

## Edge Cases Checklist

| Case | What Can Go Wrong |
|------|-------------------|
| Empty list (head == null) | NullPointerException |
| Single node | head.next == null, reversal is no-op |
| Two nodes | Verify reverse and merge work |
| Cycle exists | Infinite loop if not detected |
| Operations on tail | Need to track previous node |
| Even vs odd length | Middle node position differs |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Reverse Linked List (LC 206) ⭐⭐ | Three-pointer reverse |
| 2 | Merge Two Sorted Lists (LC 21) ⭐⭐ | Dummy head + merge |
| 3 | Linked List Cycle (LC 141) ⭐ | Fast/slow |
| 4 | Middle of Linked List (LC 876) | Fast/slow |
| 5 | Remove Duplicates from Sorted List (LC 83) | Skip duplicates |
| 6 | Intersection of Two Linked Lists (LC 160) ⭐ | Length equalization or two-pass |
| 7 | Palindrome Linked List (LC 234) ⭐ | Reverse second half |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Remove Nth Node From End (LC 19) ⭐⭐ | Two pointers, n apart |
| 2 | Add Two Numbers (LC 2) ⭐⭐ | Carry simulation |
| 3 | Linked List Cycle II (LC 142) ⭐ | Floyd's cycle start |
| 4 | Reorder List (LC 143) ⭐ | Find mid + reverse + merge |
| 5 | Sort List (LC 148) ⭐ | Merge sort on linked list |
| 6 | Copy List with Random Pointer (LC 138) ⭐ | HashMap or interleaving |
| 7 | Swap Nodes in Pairs (LC 24) | Pointer reassignment |
| 8 | Rotate List (LC 61) | Make circular, then cut |
| 9 | Odd Even Linked List (LC 328) | Separate odd/even, reconnect |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Merge K Sorted Lists (LC 23) ⭐⭐ | Min-heap or divide & conquer |
| 2 | Reverse Nodes in K-Group (LC 25) ⭐ | Group reverse + recursion |
| 3 | LRU Cache (LC 146) ⭐⭐ | HashMap + Doubly Linked List |

---

## The LRU Cache — Why It's the GOAT LinkedList Problem

This combines HashMap (O(1) lookup) with Doubly Linked List (O(1) remove/insert). It's the perfect interview problem because it tests data structure *design*.

```java
class LRUCache {
    class Node {
        int key, val;
        Node prev, next;
        Node(int k, int v) { key = k; val = v; }
    }
    
    int capacity;
    Map<Integer, Node> map = new HashMap<>();
    Node head = new Node(0, 0), tail = new Node(0, 0);
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);
        insertAtHead(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) remove(map.get(key));
        Node node = new Node(key, value);
        insertAtHead(node);
        map.put(key, node);
        if (map.size() > capacity) {
            Node lru = tail.prev;
            remove(lru);
            map.remove(lru.key);
        }
    }
    
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    private void insertAtHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

---

*Next: [Stack & Queue — LIFO/FIFO Thinking](02-stack-queue.md)*
