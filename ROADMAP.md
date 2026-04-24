# DSA Mastery Roadmap — From First Principles to Grandmaster Thinking

> **Philosophy**: We don't memorize solutions. We build **mental models** that let us *derive* solutions. Every data structure exists because some operation was too slow. Every algorithm exists because brute force was too expensive. Understand the *why*, and the *how* follows.

---

## Who This Is For

You're a Java developer who wants to:
1. **Crack any MAANG interview** (Meta, Apple, Amazon, Netflix, Google + tier-1 companies)
2. **Solve any LeetCode problem** by recognizing patterns, not memorizing
3. **Build a foundation for Codeforces** (competitive programming → HFT companies)

---

## The 7-Phase Plan

| Phase | Topic | Duration | Goal |
|-------|-------|----------|------|
| **1** | Foundations & Complexity | Week 1-2 | Think in time/space. Master arrays, strings, math |
| **2** | Core Data Structures | Week 3-5 | LinkedList, Stack, Queue, HashMap, HashSet, Heap |
| **3** | Algorithmic Paradigms | Week 6-9 | Two Pointers, Sliding Window, Binary Search, Sorting, Recursion, Backtracking |
| **4** | Trees & Graphs | Week 10-14 | BFS, DFS, BST, Tries, Union-Find, Shortest Path, Topological Sort |
| **5** | Dynamic Programming | Week 15-18 | 1D DP, 2D DP, Knapsack, Interval, Bitmask DP, DP on Trees |
| **6** | Advanced Algorithms | Week 19-21 | Segment Trees, Monotonic Stack/Queue, Math/Number Theory, Bit Manipulation |
| **7** | Interview Mastery & CF Prep | Week 22-24 | System of problem recognition, mock interviews, CF rated problems |

---

## Daily Practice Structure (2-3 hours/day)

```
[30 min] Study the concept from first principles (read the phase notes)
[60 min] Solve 2-3 problems (1 easy, 1 medium, attempt 1 hard)
[30 min] Review editorial + optimize your solution
[15 min] Write down the pattern you learned in your own words
[15 min] Revisit 1 problem from a previous day (spaced repetition)
```

---

## How to Read Each Phase File

Every phase file follows this structure:
1. **First Principles** — WHY does this exist? What problem does it solve?
2. **Mental Model** — How to THINK about it (not just code it)
3. **Pattern Recognition** — How to SPOT when to use it
4. **Java Template** — Clean, reusable code you understand line by line
5. **Problem Set** — Easy / Medium / Hard + Star (most asked in MAANG)
6. **Edge Cases** — The traps interviewers set
7. **Interviewer's Perspective** — What I look for when I interview at Meta

---

## File Index

### Phase 1: Foundations
- [Complexity Analysis & Mathematical Thinking](phase-1-foundations/01-complexity.md)
- [Arrays & Strings — The Building Blocks](phase-1-foundations/02-arrays-strings.md)
- [Math & Number Theory Basics](phase-1-foundations/03-math-basics.md)

### Phase 2: Core Data Structures
- [LinkedList — Pointer Manipulation](phase-2-core-ds/01-linkedlist.md)
- [Stack & Queue — LIFO/FIFO Thinking](phase-2-core-ds/02-stack-queue.md)
- [HashMap & HashSet — O(1) Lookup Power](phase-2-core-ds/03-hashing.md)
- [Heap / Priority Queue — Top-K Thinking](phase-2-core-ds/04-heap.md)

### Phase 3: Algorithmic Paradigms
- [Two Pointers & Sliding Window](phase-3-paradigms/01-two-pointers-sliding-window.md)
- [Binary Search — Beyond Sorted Arrays](phase-3-paradigms/02-binary-search.md)
- [Sorting — Not Just Arrays](phase-3-paradigms/03-sorting.md)
- [Recursion & Backtracking](phase-3-paradigms/04-recursion-backtracking.md)

### Phase 4: Trees & Graphs
- [Binary Trees & BST](phase-4-advanced-ds/01-trees-bst.md)
- [Graphs — BFS, DFS, and Beyond](phase-4-advanced-ds/02-graphs.md)
- [Trie — Prefix-Based Thinking](phase-4-advanced-ds/03-trie.md)
- [Union-Find / Disjoint Set](phase-4-advanced-ds/04-union-find.md)

### Phase 5: Dynamic Programming
- [DP from First Principles](phase-5-dp/01-dp-fundamentals.md)
- [Classic DP Patterns](phase-5-dp/02-classic-patterns.md)
- [Advanced DP — Bitmask, Interval, DP on Trees](phase-5-dp/03-advanced-dp.md)

### Phase 6: Advanced Algorithms
- [Monotonic Stack & Queue](phase-6-advanced-algos/01-monotonic.md)
- [Segment Tree & BIT](phase-6-advanced-algos/02-segment-tree.md)
- [Bit Manipulation](phase-6-advanced-algos/03-bit-manipulation.md)
- [Advanced Math & Number Theory](phase-6-advanced-algos/04-advanced-math.md)

### Phase 7: Interview Mastery
- [Problem Recognition System](phase-7-interview-mastery/01-recognition-system.md)
- [MAANG Interview Playbook](phase-7-interview-mastery/02-maang-playbook.md)
- [Codeforces Transition Guide](phase-7-interview-mastery/03-codeforces-guide.md)

### Templates
- [All Java Templates](templates/all-templates.md)

---

## The Meta Interview Reality (From Someone Who Conducts Them)

Here's what most candidates don't understand:

### What Gets You Hired
1. **Problem decomposition** — Can you break a hard problem into subproblems?
2. **Communication** — Can you explain your thinking WHILE coding?
3. **Edge case awareness** — Do you think about empty inputs, overflow, duplicates BEFORE I ask?
4. **Optimization instinct** — After brute force, can you identify the bottleneck?
5. **Clean code** — Variable names matter. Structure matters. This is a proxy for how you'd code at work.

### What Gets You Rejected
1. Jumping to code without thinking
2. Memorized solutions that crumble when I twist the problem
3. Can't analyze your own time/space complexity
4. Silent coding (we can't evaluate what we can't see)
5. Giving up instead of working through partial solutions

### The Scoring (at Meta specifically)
- **Coding**: Did you write correct, clean, efficient code?
- **Problem Solving**: Did you approach it methodically? Handle hints well?
- **Communication**: Could I follow your thought process?
- **Verification**: Did you test your code? Find your own bugs?

---

## Progress Tracking

Use this checklist. Mark each topic when you can:
- [ ] Explain the concept to a 5-year-old (Feynman technique)
- [ ] Write the template from scratch without looking
- [ ] Solve a medium problem in < 25 minutes
- [ ] Identify the pattern within 3 minutes of reading a new problem
- [ ] Handle all edge cases without being prompted

---

## Golden Rules

1. **Never look at the solution before spending 30 minutes**. The struggle IS the learning.
2. **After solving, always read the top editorial**. There's always a cleaner way.
3. **If you can't solve it in 45 min, read the APPROACH only** (not the code). Then code it yourself.
4. **Revisit problems you couldn't solve after 3 days**. If you still can't, the concept hasn't clicked.
5. **Every problem you solve should teach you a TRANSFERABLE pattern**, not a specific trick.

---

*Let's begin. Start with Phase 1.*
