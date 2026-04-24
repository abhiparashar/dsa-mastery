# Lesson 5: LinkedList — Pointer Puzzles

> Linked list problems are pointer manipulation puzzles. Master three patterns: reverse, fast/slow, and dummy head. Every linked list problem is a combination of these three.

---

## The Three Patterns

### 1. Dummy Head — Eliminates Head Edge Cases
```java
ListNode dummy = new ListNode(0, head);
// ... work with dummy.next as the "real" head
return dummy.next;
```

### 2. Fast/Slow — Find Middle, Detect Cycles
```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
// slow is at the middle
```

### 3. Reverse — The Most Fundamental Operation
```java
ListNode prev = null, curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev;
```

---

## Problems

**1. Reverse Linked List (LC 206)** — EASY ⭐⭐: Three-pointer dance  
**2. Merge Two Sorted Lists (LC 21)** — EASY ⭐⭐: Dummy head + merge  
**3. Linked List Cycle (LC 141)** — EASY ⭐: Fast/slow  
**4. Remove Nth Node From End (LC 19)** — MEDIUM ⭐⭐: Two pointers, n apart  
**5. Reorder List (LC 143)** — MEDIUM ⭐: Find mid + reverse + merge  
**6. LRU Cache (LC 146)** — HARD ⭐⭐: HashMap + Doubly Linked List  

See Java files and full theory in `phase-2-core-ds/01-linkedlist.md`.
