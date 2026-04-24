# Phase 3.2: Binary Search — Beyond Sorted Arrays

> **First Principle**: Binary search works whenever you can define a predicate that's FALSE for some prefix and TRUE for some suffix (or vice versa). The sorted array is just one instance. The real power is **binary search on the answer space** — when you can't search the input directly, but you can check "is this answer feasible?" in O(n) or O(n log n).

---

## The Insight Most People Miss

Binary search isn't "search in a sorted array." It's:

**Given a monotonic predicate `f(x)`, find the boundary where `f` changes from false to true.**

```
x:    1  2  3  4  5  6  7  8  9  10
f(x): F  F  F  F  T  T  T  T  T  T
                  ^
                  This is what you're looking for
```

---

## The Two Templates (Memorize Both)

### Template 1: Find Leftmost True (Lower Bound)

```java
// Find the smallest index where condition is true
int lo = LEFT_BOUND, hi = RIGHT_BOUND;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (condition(mid)) {
        hi = mid;        // mid might be the answer, search left
    } else {
        lo = mid + 1;    // mid is definitely not the answer
    }
}
// lo == hi == answer (or LEFT_BOUND if no element satisfies condition)
```

### Template 2: Find Rightmost True (Upper Bound)

```java
// Find the largest index where condition is true
int lo = LEFT_BOUND, hi = RIGHT_BOUND;
while (lo < hi) {
    int mid = lo + (hi - lo + 1) / 2; // NOTE: ceiling division to avoid infinite loop
    if (condition(mid)) {
        lo = mid;        // mid might be the answer, search right
    } else {
        hi = mid - 1;    // mid is definitely not the answer
    }
}
// lo == hi == answer
```

**Why ceiling division in Template 2?** If `lo = 3, hi = 4`, floor division gives `mid = 3`. If `condition(3)` is true, `lo = mid = 3` — infinite loop! Ceiling gives `mid = 4`, which always makes progress.

---

## Category 1: Classic Binary Search on Sorted Array

```java
// Basic binary search
int binarySearch(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return -1; // not found
}

// Find first occurrence (lower bound)
int lowerBound(int[] nums, int target) {
    int lo = 0, hi = nums.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] >= target) hi = mid;
        else lo = mid + 1;
    }
    return lo; // first index where nums[i] >= target
}

// Find last occurrence
int upperBound(int[] nums, int target) {
    int lo = 0, hi = nums.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] > target) hi = mid;
        else lo = mid + 1;
    }
    return lo - 1; // last index where nums[i] <= target
}

// Search in Rotated Sorted Array (LC 33) ⭐⭐
public int search(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        
        if (nums[lo] <= nums[mid]) { // left half is sorted
            if (nums[lo] <= target && target < nums[mid]) hi = mid - 1;
            else lo = mid + 1;
        } else { // right half is sorted
            if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
            else hi = mid - 1;
        }
    }
    return -1;
}

// Find Minimum in Rotated Sorted Array (LC 153) ⭐
public int findMin(int[] nums) {
    int lo = 0, hi = nums.length - 1;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] > nums[hi]) lo = mid + 1; // min is in right half
        else hi = mid;                           // min is in left half (including mid)
    }
    return nums[lo];
}
```

---

## Category 2: Binary Search on Answer Space

**This is the advanced pattern that separates good from great.**

**The Idea**: Instead of searching the input, binary search on the possible answer values. For each candidate answer, check if it's achievable (the "feasibility check").

```
Problem: "What is the minimum/maximum value X such that some condition holds?"

Step 1: Define the search space [lo, hi] for the answer
Step 2: Write a function canAchieve(x) that returns true/false
Step 3: Binary search for the boundary
```

```java
// Koko Eating Bananas (LC 875) ⭐
// Find minimum eating speed to finish all bananas in h hours
public int minEatingSpeed(int[] piles, int h) {
    int lo = 1, hi = Arrays.stream(piles).max().getAsInt();
    
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (canFinish(piles, h, mid)) {
            hi = mid;        // this speed works, try slower
        } else {
            lo = mid + 1;    // too slow, need faster
        }
    }
    return lo;
}

boolean canFinish(int[] piles, int h, int speed) {
    int hours = 0;
    for (int pile : piles) {
        hours += (pile + speed - 1) / speed; // ceiling division
    }
    return hours <= h;
}

// Split Array Largest Sum (LC 410) ⭐ — Google favorite
// Minimize the maximum subarray sum when splitting array into m parts
public int splitArray(int[] nums, int m) {
    int lo = Arrays.stream(nums).max().getAsInt();   // min possible answer
    int hi = Arrays.stream(nums).sum();               // max possible answer
    
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (canSplit(nums, m, mid)) {
            hi = mid;        // can achieve this max sum, try smaller
        } else {
            lo = mid + 1;    // can't split with this max sum
        }
    }
    return lo;
}

boolean canSplit(int[] nums, int m, int maxSum) {
    int parts = 1, currentSum = 0;
    for (int num : nums) {
        if (currentSum + num > maxSum) {
            parts++;
            currentSum = num;
            if (parts > m) return false;
        } else {
            currentSum += num;
        }
    }
    return true;
}
```

---

## Category 3: Binary Search on Matrix

```java
// Search a 2D Matrix (LC 74) — treat as flattened sorted array
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int lo = 0, hi = m * n - 1;
    
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        int val = matrix[mid / n][mid % n]; // convert 1D index to 2D
        if (val == target) return true;
        else if (val < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return false;
}
```

---

## How to Recognize "Binary Search on Answer"

| Signal | Example |
|--------|---------|
| "Minimize the maximum ..." | Split Array Largest Sum |
| "Maximize the minimum ..." | Aggressive Cows (classic CF) |
| "Minimum speed/capacity such that ..." | Koko Eating Bananas |
| "Can you achieve X within Y?" | Ship packages within D days |
| Answer space is bounded and monotonic | If X works, X+1 also works |

---

## Edge Cases

| Case | Trap |
|------|------|
| Empty array | Return -1 or handle |
| Single element | lo == hi from start |
| All duplicates | Lower/upper bound differ from basic search |
| Overflow in mid calculation | Always use `lo + (hi - lo) / 2` |
| Search space = [0, Integer.MAX_VALUE] | Use long for mid calculations |
| Off-by-one: lo < hi vs lo <= hi | Template 1 & 2 use `lo < hi`. Basic search uses `lo <= hi` |
| Rotated array with duplicates | Worst case O(n) — can't binary search |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Binary Search (LC 704) | Basic template |
| 2 | Search Insert Position (LC 35) | Lower bound |
| 3 | First Bad Version (LC 278) | Lower bound on predicate |
| 4 | Sqrt(x) (LC 69) | Binary search on answer |
| 5 | Guess Number Higher or Lower (LC 374) | Basic with API |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Search in Rotated Sorted Array (LC 33) ⭐⭐ | Identify sorted half |
| 2 | Find Minimum in Rotated Array (LC 153) ⭐ | Compare mid with hi |
| 3 | Find First and Last Position (LC 34) ⭐ | Lower + upper bound |
| 4 | Search a 2D Matrix (LC 74) ⭐ | Flatten to 1D |
| 5 | Koko Eating Bananas (LC 875) ⭐ | BS on answer |
| 6 | Capacity to Ship Packages (LC 1011) ⭐ | BS on answer |
| 7 | Find Peak Element (LC 162) ⭐ | BS on non-sorted |
| 8 | Search in Rotated Array II (LC 81) | Handle duplicates |
| 9 | Time Based Key-Value Store (LC 981) | BS on timestamp |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Median of Two Sorted Arrays (LC 4) ⭐⭐ | BS on partition |
| 2 | Split Array Largest Sum (LC 410) ⭐ | BS on answer |
| 3 | Find in Mountain Array (LC 1095) | Find peak + BS both sides |
| 4 | Smallest Good Base (LC 483) | BS + math |
| 5 | Aggressive Cows (SPOJ/CF classic) | BS on answer (maximize min) |

---

*Next: [Sorting — Not Just Arrays](03-sorting.md)*
