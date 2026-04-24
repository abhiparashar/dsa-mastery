# Phase 2.4: Heap / Priority Queue — Top-K Thinking

> **First Principle**: A heap is a complete binary tree stored in an array where every parent is smaller (min-heap) or larger (max-heap) than its children. This gives O(1) access to the min/max and O(log n) insert/remove. When you need "the smallest/largest K elements" or "the next item to process," think heap.

---

## Why Heaps Exist

**The Problem**: Sorting gives you the top-K elements but takes O(n log n). What if you only need the top K out of millions? Maintaining a heap of size K gives O(n log K) — far better when K << n.

**The other problem**: You need to repeatedly get the minimum/maximum from a changing collection. Sorted array gives O(1) min but O(n) insert. Heap gives O(log n) for both.

---

## Mental Model

```
Min-Heap as array: [1, 3, 2, 7, 5, 4, 6]

As tree:        1
              /   \
            3       2
          /   \   /   \
         7     5 4     6

Parent of i:     (i - 1) / 2
Left child of i:  2 * i + 1
Right child of i: 2 * i + 2

Insert: add at end, bubble UP — O(log n)
Remove min: swap root with last, bubble DOWN — O(log n)
Peek min: return root — O(1)
```

---

## Java PriorityQueue

```java
// Min-heap (default in Java)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// Custom comparator — sort by frequency, then alphabetically
PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
    if (freq.get(a) != freq.get(b)) return freq.get(b) - freq.get(a);
    return a.compareTo(b);
});

// Operations
pq.offer(element);     // O(log n) — add
pq.poll();             // O(log n) — remove and return min/max
pq.peek();             // O(1) — look at min/max
pq.size();             // O(1)
pq.isEmpty();          // O(1)
pq.remove(element);    // O(n) — THIS IS SLOW, avoid if possible
pq.contains(element);  // O(n) — also slow
```

**Critical Java gotcha**: `PriorityQueue.remove(Object)` is O(n), not O(log n). If you need efficient removal, consider a TreeMap or mark-as-deleted pattern.

---

## Core Patterns

### Pattern 1: Top-K Elements

**The Idea**: Maintain a heap of size K. For top-K largest, use a MIN-heap (counterintuitive!). Why? You keep the K largest, and the min-heap tells you the *threshold* — anything smaller than the top of the heap gets rejected.

```java
// Kth Largest Element in an Array (LC 215) ⭐⭐
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) minHeap.poll();
    }
    return minHeap.peek();
}
// Time: O(n log k), Space: O(k)
// For k << n, this beats sorting O(n log n)

// Top K Frequent Elements (LC 347) ⭐
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);
    
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(
        (a, b) -> freq.get(a) - freq.get(b)
    );
    
    for (int num : freq.keySet()) {
        minHeap.offer(num);
        if (minHeap.size() > k) minHeap.poll();
    }
    
    int[] result = new int[k];
    for (int i = k - 1; i >= 0; i--) result[i] = minHeap.poll();
    return result;
}
```

### Pattern 2: Merge K Sorted Streams

**The Idea**: Keep one element from each stream in the heap. Poll the smallest, then add the next element from that stream.

```java
// Merge K Sorted Lists (LC 23) ⭐⭐
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> pq = new PriorityQueue<>(
        (a, b) -> a.val - b.val
    );
    
    for (ListNode list : lists) {
        if (list != null) pq.offer(list);
    }
    
    ListNode dummy = new ListNode(0), tail = dummy;
    while (!pq.isEmpty()) {
        ListNode node = pq.poll();
        tail.next = node;
        tail = tail.next;
        if (node.next != null) pq.offer(node.next);
    }
    return dummy.next;
}
// Time: O(N log K) where N = total nodes, K = number of lists
```

### Pattern 3: Two Heaps (Median Stream)

**The Idea**: Split elements into two halves — a max-heap for the lower half and a min-heap for the upper half. The median is at the top of one (or both).

```java
// Find Median from Data Stream (LC 295) ⭐⭐ — Amazon, Google favorite
class MedianFinder {
    PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder()); // max-heap: lower half
    PriorityQueue<Integer> hi = new PriorityQueue<>(); // min-heap: upper half
    
    public void addNum(int num) {
        lo.offer(num);
        hi.offer(lo.poll()); // balance: largest of lower goes to upper
        if (hi.size() > lo.size()) {
            lo.offer(hi.poll()); // keep lo same size or 1 larger
        }
    }
    
    public double findMedian() {
        if (lo.size() > hi.size()) return lo.peek();
        return (lo.peek() + hi.peek()) / 2.0;
    }
}
// Every operation is O(log n). Median is O(1).
```

### Pattern 4: Greedy + Heap (Task Scheduling)

```java
// Task Scheduler (LC 621) ⭐
// Greedy: always process the most frequent task first
public int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char t : tasks) freq[t - 'A']++;
    
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    for (int f : freq) if (f > 0) maxHeap.offer(f);
    
    int time = 0;
    Queue<int[]> cooldown = new LinkedList<>(); // [remaining_count, available_at]
    
    while (!maxHeap.isEmpty() || !cooldown.isEmpty()) {
        time++;
        if (!maxHeap.isEmpty()) {
            int cnt = maxHeap.poll() - 1;
            if (cnt > 0) cooldown.offer(new int[]{cnt, time + n});
        }
        if (!cooldown.isEmpty() && cooldown.peek()[1] == time) {
            maxHeap.offer(cooldown.poll()[0]);
        }
    }
    return time;
}
```

### Pattern 5: Heap for Interval Problems

```java
// Meeting Rooms II (LC 253) ⭐ — how many rooms needed?
public int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // sort by start
    PriorityQueue<Integer> endTimes = new PriorityQueue<>(); // min-heap of end times
    
    for (int[] interval : intervals) {
        if (!endTimes.isEmpty() && endTimes.peek() <= interval[0]) {
            endTimes.poll(); // reuse room
        }
        endTimes.offer(interval[1]);
    }
    return endTimes.size();
}
```

---

## Heap vs Sorting vs QuickSelect

| Need | Best Approach | Time |
|------|--------------|------|
| Kth largest (one-time) | QuickSelect | O(n) avg, O(n²) worst |
| Kth largest (stream) | Heap of size K | O(n log K) |
| All elements sorted | Sort | O(n log n) |
| Top-K from stream | Min-heap of size K | O(n log K) |
| Median from stream | Two heaps | O(log n) per add |

---

## Edge Cases

| Case | Trap |
|------|------|
| k = 0 or k > n | Handle before heap operations |
| All same elements | Heap works but result may surprise |
| Negative numbers | Comparator must handle correctly |
| Integer overflow in comparator | `(a, b) -> a - b` overflows! Use `Integer.compare(a, b)` |
| Empty heap | `poll()` returns null, `peek()` returns null |

**Comparator overflow trap**:
```java
// WRONG — overflows when a and b have different signs
PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> a - b);

// CORRECT — handles all cases
PriorityQueue<Integer> pq = new PriorityQueue<>(Integer::compare);
// or
PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(a, b));
```

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Last Stone Weight (LC 1046) | Max-heap simulation |
| 2 | Kth Largest Element in Stream (LC 703) | Min-heap of size K |
| 3 | Relative Ranks (LC 506) | Max-heap or sort |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Kth Largest Element (LC 215) ⭐⭐ | Min-heap of size K or QuickSelect |
| 2 | Top K Frequent Elements (LC 347) ⭐ | Freq map + min-heap |
| 3 | K Closest Points to Origin (LC 973) ⭐ | Max-heap of size K |
| 4 | Task Scheduler (LC 621) ⭐ | Greedy + max-heap |
| 5 | Reorganize String (LC 767) ⭐ | Max-heap: place most frequent |
| 6 | Sort Characters By Frequency (LC 451) | Max-heap or bucket sort |
| 7 | Meeting Rooms II (LC 253) ⭐ | Sort + min-heap |
| 8 | Kth Smallest Element in Sorted Matrix (LC 378) | Min-heap merge K streams |
| 9 | Find K Pairs with Smallest Sums (LC 373) | Min-heap + expansion |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Merge K Sorted Lists (LC 23) ⭐⭐ | Min-heap merge |
| 2 | Find Median from Data Stream (LC 295) ⭐⭐ | Two heaps |
| 3 | Sliding Window Median (LC 480) | Two heaps + lazy deletion |
| 4 | IPO (LC 502) | Two heaps: greedy scheduling |
| 5 | Smallest Range Covering Elements from K Lists (LC 632) | Min-heap + track max |

---

*Next: Phase 3 — [Two Pointers & Sliding Window](../phase-3-paradigms/01-two-pointers-sliding-window.md)*
