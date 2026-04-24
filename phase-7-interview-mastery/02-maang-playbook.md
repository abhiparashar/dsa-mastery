# Phase 7.2: MAANG Interview Playbook

> From someone who interviews at Meta. Here's exactly what you need to know.

---

## Company-Specific Patterns

### Meta (Facebook)
**Style**: 2 coding rounds, 45 min each. Often 1 medium + 1 medium/hard per round.
**Favorites**: Arrays, strings, trees, graphs, DP
**Most asked**:
| # | Problem | Frequency |
|---|---------|-----------|
| 1 | Lowest Common Ancestor (LC 236) | Very High |
| 2 | Binary Tree Vertical Order (LC 314) | Very High |
| 3 | Random Pick with Weight (LC 528) | High |
| 4 | Subarray Sum Equals K (LC 560) | High |
| 5 | Valid Palindrome II (LC 680) | High |
| 6 | Merge Intervals (LC 56) | High |
| 7 | Minimum Remove to Make Valid Parentheses (LC 1249) | High |
| 8 | Product of Array Except Self (LC 238) | High |
| 9 | Nested List Weight Sum (LC 339/364) | High |
| 10 | Word Break (LC 139) | High |
| 11 | 3Sum (LC 15) | High |
| 12 | LRU Cache (LC 146) | High |
| 13 | Alien Dictionary (LC 269) | Medium |
| 14 | Clone Graph (LC 133) | Medium |
| 15 | Serialize/Deserialize BT (LC 297) | Medium |

### Google
**Style**: 45 min, usually 1 problem explored deeply with follow-ups.
**Favorites**: Binary search on answer, DP, graphs, design
**Most asked**:
| # | Problem | Notes |
|---|---------|-------|
| 1 | Median of Two Sorted Arrays (LC 4) | Classic Google |
| 2 | Longest Increasing Subsequence (LC 300) | With follow-ups |
| 3 | Word Ladder (LC 127) | BFS mastery |
| 4 | Split Array Largest Sum (LC 410) | BS on answer |
| 5 | Trapping Rain Water (LC 42) | Multiple approaches |
| 6 | Maximum Profit in Job Scheduling (LC 1235) | Sort + DP + BS |
| 7 | Design Search Autocomplete (LC 642) | Trie |
| 8 | Minimum Window Substring (LC 76) | Sliding window |
| 9 | Snapshot Array (LC 1146) | Binary search design |
| 10 | Longest Consecutive Sequence (LC 128) | HashSet |

### Amazon
**Style**: 2-3 coding rounds. Medium difficulty. Heavy on OOP and Leadership Principles.
**Favorites**: BFS/DFS, strings, design
**Most asked**:
| # | Problem | Notes |
|---|---------|-------|
| 1 | Number of Islands (LC 200) | BFS/DFS |
| 2 | Two Sum (LC 1) | Warm-up |
| 3 | LRU Cache (LC 146) | Design |
| 4 | Merge K Sorted Lists (LC 23) | Heap |
| 5 | Word Search II (LC 212) | Trie + backtrack |
| 6 | Reorder Data in Log Files (LC 937) | Custom sort |
| 7 | K Closest Points to Origin (LC 973) | Heap |
| 8 | Copy List with Random Pointer (LC 138) | HashMap |
| 9 | Course Schedule II (LC 210) | Topological sort |
| 10 | Sliding Window Maximum (LC 239) | Monotonic deque |

### Apple
**Style**: Mix of coding and system design. Values clean code.
**Most asked**: Trees, arrays, DP basics

### Microsoft  
**Style**: 3-4 rounds. Mix of medium problems.
**Most asked**: Trees, linked lists, string manipulation, design patterns

---

## The Top 75 "Blind 75" List — Minimum Viable Preparation

These are the absolute minimum you must solve before any MAANG interview:

### Arrays & Hashing
1. Two Sum ⭐
2. Best Time to Buy and Sell Stock ⭐
3. Contains Duplicate
4. Product of Array Except Self ⭐
5. Maximum Subarray ⭐
6. Maximum Product Subarray
7. Find Minimum in Rotated Sorted Array
8. Search in Rotated Sorted Array
9. 3Sum ⭐
10. Container With Most Water ⭐

### Two Pointers
11. Valid Palindrome
12. 3Sum (duplicate from above)

### Sliding Window
13. Longest Substring Without Repeating Characters ⭐
14. Longest Repeating Character Replacement
15. Minimum Window Substring ⭐

### Stack
16. Valid Parentheses ⭐

### Binary Search
17. Find Minimum in Rotated Sorted Array ⭐
18. Search in Rotated Sorted Array ⭐

### Linked List
19. Reverse Linked List ⭐
20. Merge Two Sorted Lists ⭐
21. Remove Nth Node From End ⭐
22. Linked List Cycle ⭐
23. Merge K Sorted Lists ⭐

### Trees
24. Invert Binary Tree ⭐
25. Maximum Depth of Binary Tree ⭐
26. Same Tree
27. Binary Tree Level Order Traversal ⭐
28. Serialize and Deserialize BT ⭐
29. Subtree of Another Tree
30. Construct Binary Tree from Preorder and Inorder ⭐
31. Validate BST ⭐
32. Kth Smallest Element in BST
33. Lowest Common Ancestor ⭐
34. Binary Tree Max Path Sum ⭐

### Heap
35. Merge K Sorted Lists (duplicate)
36. Top K Frequent Elements ⭐
37. Find Median from Data Stream ⭐

### Backtracking
38. Combination Sum ⭐
39. Word Search ⭐

### Graphs
40. Number of Islands ⭐
41. Clone Graph
42. Pacific Atlantic Water Flow
43. Course Schedule ⭐
44. Number of Connected Components
45. Graph Valid Tree

### Dynamic Programming
46. Climbing Stairs ⭐
47. Coin Change ⭐
48. Longest Increasing Subsequence ⭐
49. Longest Common Subsequence ⭐
50. Word Break ⭐
51. Combination Sum IV
52. House Robber ⭐
53. House Robber II
54. Decode Ways
55. Unique Paths ⭐
56. Jump Game

### Greedy
57. Maximum Subarray ⭐
58. Jump Game
59. Jump Game II

### Intervals
60. Insert Interval
61. Merge Intervals ⭐
62. Non-overlapping Intervals
63. Meeting Rooms II ⭐

### Math & Bit
64. Rotate Image ⭐
65. Spiral Matrix
66. Set Matrix Zeroes
67. Number of 1 Bits
68. Counting Bits
69. Reverse Bits
70. Missing Number
71. Sum of Two Integers

### Design
72. LRU Cache ⭐
73. Implement Trie ⭐
74. Design Add and Search Words
75. Word Search II ⭐

---

## Interview Day Checklist

### Before
- [ ] Solve 2-3 problems to warm up (easy/medium)
- [ ] Review your weak topic (one page of notes)
- [ ] Test your environment (IDE, whiteboard, video)

### During
- [ ] Clarify the problem before coding (2 min)
- [ ] State brute force and its complexity
- [ ] State optimized approach and get confirmation
- [ ] Code cleanly with good variable names
- [ ] Walk through a test case manually
- [ ] Discuss edge cases
- [ ] State time and space complexity

### Communication Templates
```
"Let me make sure I understand the problem..."
"The brute force would be O(n²) by checking all pairs. Can we do better?"
"I think we can use [pattern] because [reason]."
"The time complexity is O(n log n) because of the sort, and space is O(n) for the hashmap."
"Let me trace through this example to verify..."
"Edge cases I'm thinking about: empty input, single element, all duplicates..."
```

---

*Next: [Codeforces Transition Guide](03-codeforces-guide.md)*
