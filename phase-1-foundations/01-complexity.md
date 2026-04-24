# Phase 1.1: Complexity Analysis & Mathematical Thinking

> **First Principle**: Every algorithm is a trade-off between time and space. Complexity analysis is the language we use to quantify that trade-off. Without it, you're guessing.

---

## Why Complexity Matters — The Real Reason

Imagine you're at a Meta interview. You solve the problem in O(n²). The interviewer says "Can you do better?" If you don't understand complexity, you don't even know what "better" means, let alone how to get there.

**Complexity is not math class. It's a tool for making decisions.**

---

## Building Intuition from Scratch

### What is Time Complexity?

It answers one question: **As my input grows, how much slower does my code get?**

```
n = 10      → your code runs in 0.001s
n = 100     → your code runs in 0.1s
n = 1000    → your code runs in 10s      ← too slow for interviews
n = 10000   → your code runs in 1000s    ← way too slow
```

That's O(n²) growth. Now compare:

```
n = 10      → 0.001s
n = 100     → 0.01s
n = 1000    → 0.1s
n = 10000   → 1s
```

That's O(n) growth. **Same problem, but 1000x faster at scale.**

### The Hierarchy You Must Internalize

```
O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2^n) < O(n!)

Constant  Logarithmic  Linear  Linearithmic  Quadratic  Exponential  Factorial
```

**Practical limits in interviews (n is input size, ~1 second time limit):**

| Complexity | Max n (approx) | Example |
|-----------|----------------|---------|
| O(n!) | ~10-11 | Permutations |
| O(2^n) | ~20-25 | Subsets, bitmask DP |
| O(n³) | ~300-500 | 3D DP, Floyd-Warshall |
| O(n²) | ~3,000-5,000 | Nested loops, 2D DP |
| O(n log n) | ~10^6 | Sorting, divide & conquer |
| O(n) | ~10^7-10^8 | Single pass, two pointers |
| O(log n) | ~10^18 | Binary search |
| O(1) | Any | Math formula, hash lookup |

**This table is your compass.** When you see n ≤ 10^5 in a problem, you know you need O(n) or O(n log n). When n ≤ 20, brute force with bitmask might work.

---

## How to Analyze Complexity — The Mental Framework

### Rule 1: Count the Loops

```java
// O(n) — one loop
for (int i = 0; i < n; i++) { ... }

// O(n²) — nested loops over same range
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) { ... }
}

// O(n * m) — nested loops over different ranges
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) { ... }
}
```

### Rule 2: Halving = Logarithmic

Every time you halve the search space, it's O(log n).

```java
// O(log n) — binary search pattern
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (condition) hi = mid;
    else lo = mid + 1;
}
```

**Why?** If n = 1024, you halve 10 times to reach 1. log₂(1024) = 10.

### Rule 3: Sorting Dominates

If you sort an array and then do a linear scan:
- Sort: O(n log n)
- Scan: O(n)
- Total: O(n log n) — the larger term wins

### Rule 4: Recursion → Draw the Tree

```java
// What's the complexity?
int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

Draw the recursion tree:
```
         fib(5)
        /      \
    fib(4)    fib(3)
    /    \     /    \
 fib(3) fib(2) fib(2) fib(1)
  ...
```

Each level roughly doubles → O(2^n). This is why memoization matters (converts to O(n)).

### Rule 5: Amortized Analysis — Don't Be Fooled

```java
// This looks O(n²) but is actually O(n)
ArrayList<Integer> list = new ArrayList<>();
for (int i = 0; i < n; i++) {
    list.add(i); // occasionally resizes (copies everything)
}
```

Most `add()` calls are O(1). Occasionally it's O(n) for resize. But total across all n operations is still O(n). Average per operation = O(1). This is **amortized O(1)**.

---

## Space Complexity — The Forgotten Dimension

### What Counts as Space?

1. **Extra data structures** you create (arrays, maps, sets)
2. **Recursion call stack** depth
3. **NOT** the input itself (that's given to you)

```java
// O(1) space — modifying in-place
void reverse(int[] arr) {
    int l = 0, r = arr.length - 1;
    while (l < r) {
        int tmp = arr[l]; arr[l] = arr[r]; arr[r] = tmp;
        l++; r--;
    }
}

// O(n) space — creating new array
int[] copy = new int[n];

// O(n) space — recursion depth n
void recurse(int n) {
    if (n == 0) return;
    recurse(n - 1); // call stack grows to depth n
}

// O(log n) space — balanced recursion
void mergeSort(int[] arr, int l, int r) {
    if (l >= r) return;
    int mid = (l + r) / 2;
    mergeSort(arr, l, mid);     // stack depth = log n
    mergeSort(arr, mid + 1, r);
}
```

---

## Common Complexity Traps in Interviews

### Trap 1: String Concatenation in Java

```java
// O(n²) — EACH concatenation creates a new String
String s = "";
for (int i = 0; i < n; i++) {
    s += "a"; // copies entire string each time
}

// O(n) — StringBuilder is amortized O(1) per append
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append("a");
}
```

### Trap 2: HashMap Operations Aren't Always O(1)

```java
// Average: O(1) lookup
// Worst case: O(n) with hash collisions
// In interviews: assume O(1) unless interviewer asks about worst case
HashMap<String, Integer> map = new HashMap<>();
```

### Trap 3: Substring in Java

```java
// Java String.substring() is O(k) where k is substring length
// It creates a NEW string (since Java 7u6)
String sub = s.substring(0, k); // O(k), not O(1)
```

### Trap 4: Collections.sort() on a List

```java
// O(n log n) for primitives and objects
// But if you sort strings of average length k: O(n * k * log n)
// because each comparison is O(k)
```

---

## The Complexity Cheat Sheet for Java

| Operation | ArrayList | LinkedList | HashMap | TreeMap | HashSet | PriorityQueue |
|-----------|-----------|------------|---------|---------|---------|---------------|
| Access by index | O(1) | O(n) | - | - | - | - |
| Search | O(n) | O(n) | O(1) avg | O(log n) | O(1) avg | O(n) |
| Insert at end | O(1)* | O(1) | O(1) avg | O(log n) | O(1) avg | O(log n) |
| Insert at start | O(n) | O(1) | - | - | - | - |
| Delete | O(n) | O(1)** | O(1) avg | O(log n) | O(1) avg | O(n)† |
| Min/Max | O(n) | O(n) | O(n) | O(log n) | O(n) | O(1)/O(log n) |

*amortized, **if you have the node reference, †O(log n) for poll()

---

## Practice Problems

### Easy (Warm-up)
| # | Problem | Key Insight |
|---|---------|-------------|
| 1 | Running Sum of 1D Array (LC 1480) | Understand O(n) — single pass |
| 2 | Find Numbers with Even Digits (LC 1295) | O(n * d) where d = digits |
| 3 | Richest Customer Wealth (LC 1672) | O(m * n) — nested iteration |

### Medium (Core)
| # | Problem | Key Insight |
|---|---------|-------------|
| 1 | Product of Array Except Self (LC 238) | Analyze space: can you do O(1) extra? |
| 2 | Find the Duplicate Number (LC 287) | Time-space tradeoff thinking |
| 3 | Sort Colors (LC 75) | Can you beat O(n log n)? |

### Hard
| # | Problem | Key Insight |
|---|---------|-------------|
| 1 | First Missing Positive (LC 41) | O(n) time, O(1) space — the holy grail |
| 2 | Median of Two Sorted Arrays (LC 4) | O(log(min(m,n))) — constraint drives solution |

### Star (Most Asked in MAANG)
| # | Problem | Where |
|---|---------|-------|
| ⭐ | Product of Array Except Self (LC 238) | Meta, Amazon, Google |
| ⭐ | First Missing Positive (LC 41) | Google, Microsoft |

---

## Key Takeaways

1. **Always state complexity before coding.** "My approach is O(n log n) time, O(n) space." This shows the interviewer you're thinking, not guessing.
2. **Constraints reveal the solution.** n ≤ 10^5? Think O(n log n). n ≤ 20? Think 2^n bitmask.
3. **When the interviewer says "optimize"**, they're asking you to reduce complexity class, not just remove a constant factor.
4. **Space optimization is often the follow-up.** "Can you do it in O(1) space?" is the second question 70% of the time.

---

*Next: [Arrays & Strings — The Building Blocks](02-arrays-strings.md)*
