# Phase 6.2: Segment Tree & Binary Indexed Tree (BIT/Fenwick)

> **First Principle**: When you need to answer range queries (sum, min, max) AND update elements efficiently, prefix sums aren't enough (O(n) per update). Segment tree gives O(log n) for both query and update.

---

## When to Use

| Need | Data Structure |
|------|---------------|
| Range sum, no updates | Prefix sum O(1) query |
| Range sum + point updates | BIT or Segment Tree O(log n) |
| Range sum + range updates | Segment Tree with lazy propagation |
| Range min/max + updates | Segment Tree |

---

## Binary Indexed Tree (Fenwick Tree) — Simpler, When Sufficient

```java
class BIT {
    int[] tree;
    
    BIT(int n) { tree = new int[n + 1]; }
    
    void update(int i, int delta) {
        for (i++; i < tree.length; i += i & (-i))
            tree[i] += delta;
    }
    
    int query(int i) { // prefix sum [0, i]
        int sum = 0;
        for (i++; i > 0; i -= i & (-i))
            sum += tree[i];
        return sum;
    }
    
    int query(int l, int r) { // range sum [l, r]
        return query(r) - (l > 0 ? query(l - 1) : 0);
    }
}
```

## Segment Tree — Full Power

```java
class SegmentTree {
    int[] tree;
    int n;
    
    SegmentTree(int[] nums) {
        n = nums.length;
        tree = new int[4 * n];
        build(nums, 1, 0, n - 1);
    }
    
    void build(int[] nums, int node, int start, int end) {
        if (start == end) { tree[node] = nums[start]; return; }
        int mid = (start + end) / 2;
        build(nums, 2 * node, start, mid);
        build(nums, 2 * node + 1, mid + 1, end);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }
    
    void update(int node, int start, int end, int idx, int val) {
        if (start == end) { tree[node] = val; return; }
        int mid = (start + end) / 2;
        if (idx <= mid) update(2 * node, start, mid, idx, val);
        else update(2 * node + 1, mid + 1, end, idx, val);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }
    
    int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;
        if (l <= start && end <= r) return tree[node];
        int mid = (start + end) / 2;
        return query(2 * node, start, mid, l, r) + 
               query(2 * node + 1, mid + 1, end, l, r);
    }
}
```

---

## Problem Set

| # | Problem | Pattern |
|---|---------|---------|
| 1 | Range Sum Query - Mutable (LC 307) ⭐ | BIT or Segment Tree |
| 2 | Count of Smaller Numbers After Self (LC 315) ⭐ | BIT / merge sort |
| 3 | Reverse Pairs (LC 493) | BIT / merge sort |
| 4 | Count of Range Sum (LC 327) | Segment tree / merge sort |
| 5 | My Calendar I/II/III (LC 729/731/732) | Segment tree variants |

---

*Next: [Bit Manipulation](03-bit-manipulation.md)*
