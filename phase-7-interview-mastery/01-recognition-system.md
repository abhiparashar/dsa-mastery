# Phase 7.1: Problem Recognition System

> **The most valuable skill in interviews isn't solving problems — it's recognizing what TYPE of problem you're looking at within the first 2-3 minutes.** This chapter is the cheat code.

---

## The Decision Tree (Print This and Pin It)

```
Read the problem. Ask yourself these questions IN ORDER:

1. Is it asking about a CONTIGUOUS subarray/substring?
   ├── Fixed size? → SLIDING WINDOW (fixed)
   ├── Variable size with condition? → SLIDING WINDOW (variable)
   └── Sum equals K? → PREFIX SUM + HASHMAP

2. Is the input SORTED (or can be sorted)?
   ├── Find a target? → BINARY SEARCH
   ├── Find pairs? → TWO POINTERS
   └── Merge intervals? → SORT + GREEDY

3. Is it about TREES?
   ├── Level by level? → BFS
   ├── Path/subtree property? → DFS (bottom-up)
   └── BST property? → INORDER TRAVERSAL

4. Is it about GRAPHS?
   ├── Shortest path (unweighted)? → BFS
   ├── Shortest path (weighted)? → DIJKSTRA
   ├── Connected components? → BFS/DFS/UNION-FIND
   ├── Ordering with dependencies? → TOPOLOGICAL SORT
   └── Detect cycle? → DFS (3-color)

5. Is it about COUNTING/OPTIMIZATION?
   ├── "How many ways?" → DP (counting)
   ├── "Minimum/maximum cost?" → DP (optimization) or GREEDY
   ├── "Is it possible?" → DP (decision) or BFS/BACKTRACKING
   └── Overlapping subproblems? → DP with MEMOIZATION

6. Is it about generating ALL solutions?
   ├── Permutations? → BACKTRACKING
   ├── Subsets/combinations? → BACKTRACKING
   └── Valid configurations? → BACKTRACKING with PRUNING

7. Is it about FREQUENCY/EXISTENCE?
   ├── "Does X exist?" → HASHSET
   ├── "How many of X?" → HASHMAP
   └── "Top K frequent?" → HEAP or BUCKET SORT

8. Is it about maintaining ORDER?
   ├── Most recent first? → STACK
   ├── First in first out? → QUEUE
   ├── Next greater/smaller? → MONOTONIC STACK
   └── Prefix-based lookup? → TRIE

9. Is n ≤ 20?
   └── BITMASK DP or BRUTE FORCE

10. Does it involve a MATRIX/GRID?
    ├── Search/traversal? → BFS/DFS
    ├── DP on grid? → 2D DP
    └── Binary search on rows? → BINARY SEARCH
```

---

## Signal → Pattern Quick Reference

| You See In Problem | Think This Pattern |
|---|---|
| "sorted array" + "find target" | Binary search |
| "subarray" + "sum" | Prefix sum + hashmap |
| "substring" + "longest/shortest" | Sliding window |
| "all permutations/subsets/combinations" | Backtracking |
| "minimum cost" + "choices at each step" | DP |
| "connected" + "components" | Union-Find or BFS/DFS |
| "level order" or "shortest path (unweighted)" | BFS |
| "dependency order" | Topological sort |
| "next greater" | Monotonic stack |
| "k-th largest/smallest" | Heap (or QuickSelect) |
| "duplicate" or "seen before" | HashSet |
| "anagram" or "frequency" | HashMap + frequency count |
| "palindrome" | Two pointers or expand from center |
| "interval" + "overlap" | Sort + sweep or merge |
| "binary tree" + "depth/height" | DFS (bottom-up) |
| "valid parentheses" or "matching" | Stack |
| "design" + "O(1) operations" | HashMap + LinkedList (LRU), or creative combination |
| n ≤ 15-20 | Bitmask DP |
| "prefix" or "autocomplete" | Trie |
| "minimum/maximum of range" | Segment tree or monotonic deque |
| "number of islands" or "flood fill" | BFS/DFS on grid |

---

## Constraint → Complexity Mapping

| Constraint | Target Complexity | Likely Approach |
|---|---|---|
| n ≤ 10 | O(n!) or O(2^n * n) | Brute force, backtracking |
| n ≤ 20 | O(2^n) | Bitmask DP, backtracking |
| n ≤ 500 | O(n³) | Floyd-Warshall, interval DP |
| n ≤ 5,000 | O(n²) | Standard DP, nested loops |
| n ≤ 10^5 | O(n log n) | Sorting, binary search, heap, segment tree |
| n ≤ 10^6 | O(n) | Two pointers, sliding window, hashmap, prefix sum |
| n ≤ 10^9 | O(log n) or O(√n) | Binary search, math |
| n ≤ 10^18 | O(log n) | Binary search, math, matrix exponentiation |

---

## The 5-Minute Framework (Use This in Every Interview)

```
Minute 0-1: READ & CLARIFY
  - Read problem twice
  - Ask: input range? edge cases? return type?
  - Confirm understanding with an example

Minute 1-2: CLASSIFY
  - What pattern does this match? (Use decision tree above)
  - What data structure is needed?

Minute 2-3: BRUTE FORCE
  - State the brute force approach and its complexity
  - "The naive approach would be O(n²) by checking all pairs..."

Minute 3-4: OPTIMIZE
  - Can I sort? Use a hash map? Binary search? DP?
  - State the optimal approach and complexity

Minute 4-5: PLAN BEFORE CODE
  - Outline your solution in 3-4 bullet points
  - Identify edge cases you'll handle
  - Get the interviewer's approval: "Does this approach sound good?"
```

---

## Common Pitfalls by Pattern

| Pattern | Common Mistake | Fix |
|---|---|---|
| Sliding Window | Using it with negative numbers for sum | Only works when add/remove is monotonic |
| Binary Search | Off-by-one in lo/hi | Use templates consistently |
| DP | Wrong base case | Think carefully about dp[0] meaning |
| Backtracking | Not making a copy of path | `new ArrayList<>(path)` |
| Graph BFS | Not marking visited before adding to queue | Mark when adding, not when polling |
| Two Pointers | Using on unsorted input | Must sort first (or use hashmap instead) |
| Heap | Comparator overflow | Use `Integer.compare()` not `a - b` |
| Trie | Not handling end-of-word flag | `isEnd = true` only for complete words |

---

*Next: [MAANG Interview Playbook](02-maang-playbook.md)*
