# Phase 1.2: Arrays & Strings — The Building Blocks

> **First Principle**: An array is a contiguous block of memory. This single fact explains why access is O(1), insertion is O(n), and cache performance is excellent. Every other data structure is either built on arrays or compensates for their weaknesses.

---

## Why Arrays Are the Foundation

80% of interview problems involve arrays or strings. Not because they're "easy" — because they're the canvas on which every technique is painted. Two pointers, sliding window, binary search, DP — they all operate on arrays.

**If you can't manipulate arrays fluently, nothing else matters.**

---

## Mental Model: Arrays in Memory

```
Index:    0    1    2    3    4
Value:  [10] [20] [30] [40] [50]
Address: 100  104  108  112  116  (each int = 4 bytes)
```

- **Access arr[i]**: base_address + i * size = O(1). That's it. No traversal.
- **Insert at position k**: shift everything from k onwards right → O(n)
- **Delete at position k**: shift everything from k onwards left → O(n)
- **This is why** we use ArrayList (dynamic array) in Java — it handles resizing.

### Strings in Java — The Gotcha

```java
String s = "hello";
// Strings are IMMUTABLE in Java
// Every modification creates a NEW String object
// s.charAt(i) → O(1)
// s.substring(i, j) → O(j - i) — creates new string
// s + "world" → O(n) — copies entire string
// s.equals(t) → O(min(n, m))
```

**Rule**: If you're modifying a string repeatedly, use `StringBuilder` or `char[]`.

---

## Core Array Techniques (From First Principles)

### Technique 1: In-place Manipulation

**The Idea**: Instead of creating a new array, modify the existing one. This saves O(n) space.

**When to recognize it**: Problem says "do it in-place" or "O(1) extra space."

```java
// Remove Duplicates from Sorted Array (LC 26)
// Key insight: use a "write pointer" — slow advances only when we find a new element
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    int slow = 0;
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    return slow + 1;
}
```

**Why does this work?** The `slow` pointer marks the end of the "valid" portion. Everything before `slow` is the answer. Everything after hasn't been processed or is overwritten.

### Technique 2: Prefix Sum

**The Idea**: Pre-compute cumulative sums so any subarray sum becomes O(1).

**When to recognize it**: "Sum of subarray from i to j" appears multiple times.

```java
// Build prefix sum
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + nums[i];
}

// Sum from index i to j (inclusive) = prefix[j + 1] - prefix[i]
// Why? prefix[j+1] = nums[0] + ... + nums[j]
//       prefix[i]   = nums[0] + ... + nums[i-1]
//       Difference   = nums[i] + ... + nums[j]  ✓
```

**Template**: Subarray Sum Equals K (LC 560) — the STAR pattern

```java
// This is one of the most asked patterns at MAANG
public int subarraySum(int[] nums, int k) {
    // Key insight: if prefix[j] - prefix[i] = k, then subarray (i, j] sums to k
    // So we need: prefix[j] - k = prefix[i] for some earlier i
    // Use a HashMap to count how many prefix sums we've seen
    
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1); // empty prefix — handles subarrays starting at index 0
    
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        count += prefixCount.getOrDefault(sum - k, 0);
        prefixCount.merge(sum, 1, Integer::sum);
    }
    return count;
}
```

### Technique 3: Kadane's Algorithm (Maximum Subarray)

**The Idea**: At each position, decide: extend the current subarray, or start fresh?

**When to recognize it**: "Maximum/minimum subarray sum" or any contiguous subsequence optimization.

```java
// Maximum Subarray (LC 53) — asked at every MAANG company
public int maxSubArray(int[] nums) {
    // First principles: at position i, the best subarray ending here is either:
    //   1. nums[i] alone (start fresh)
    //   2. nums[i] + best subarray ending at i-1 (extend)
    // We pick whichever is larger.
    
    int currentMax = nums[0];
    int globalMax = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        currentMax = Math.max(nums[i], currentMax + nums[i]);
        globalMax = Math.max(globalMax, currentMax);
    }
    return globalMax;
}
// Time: O(n), Space: O(1)
```

**Why it works**: If `currentMax` becomes negative, it can only hurt future subarrays. Starting fresh is always better than extending a negative sum.

### Technique 4: Dutch National Flag (Three-Way Partition)

**The Idea**: Partition array into three sections using three pointers.

```java
// Sort Colors (LC 75) — classic Meta interview question
public void sortColors(int[] nums) {
    // Three regions: [0...lo-1] are 0s, [lo...mid-1] are 1s, [hi+1...n-1] are 2s
    // [mid...hi] is unexplored
    int lo = 0, mid = 0, hi = nums.length - 1;
    
    while (mid <= hi) {
        if (nums[mid] == 0) {
            swap(nums, lo, mid);
            lo++; mid++;
        } else if (nums[mid] == 1) {
            mid++;
        } else { // nums[mid] == 2
            swap(nums, mid, hi);
            hi--;
            // don't advance mid — swapped element needs inspection
        }
    }
}
```

---

## Core String Techniques

### Technique 1: Character Frequency Array

**The Idea**: For lowercase English letters, use `int[26]` instead of HashMap — faster and cleaner.

```java
// Valid Anagram (LC 242)
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }
    for (int c : count) {
        if (c != 0) return false;
    }
    return true;
}
```

### Technique 2: Two-Pointer for Palindromes

```java
// Valid Palindrome (LC 125)
public boolean isPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
        while (l < r && !Character.isLetterOrDigit(s.charAt(l))) l++;
        while (l < r && !Character.isLetterOrDigit(s.charAt(r))) r--;
        if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
            return false;
        }
        l++; r--;
    }
    return true;
}
```

### Technique 3: StringBuilder for Construction

```java
// When you need to build a string character by character
StringBuilder sb = new StringBuilder();
for (char c : chars) {
    sb.append(c);
}
String result = sb.toString();

// Reverse a string
String reversed = new StringBuilder(s).reverse().toString();
```

---

## Edge Cases — The Interview Traps

| Scenario | Why It Matters |
|----------|---------------|
| Empty array/string | `nums.length == 0` — immediate return |
| Single element | `n == 1` — often the answer itself |
| All same elements | Duplicate handling logic |
| All negative numbers | Kadane's still works (picks least negative) |
| Integer overflow | `int` max is 2^31-1 ≈ 2.1 billion. Use `long` for sums |
| n = 0 vs n = 1 | Off-by-one errors |
| Sorted vs unsorted | Some techniques require sorting first |
| Negative numbers in array | Prefix sum with negatives — can't use sliding window |

### The Overflow Trap in Java

```java
// WRONG — can overflow
int mid = (lo + hi) / 2;

// CORRECT — prevents overflow
int mid = lo + (hi - lo) / 2;

// WRONG — product can overflow
int product = a * b;

// CORRECT — use long
long product = (long) a * b;
```

---

## Problem Set

### Easy
| # | Problem | Pattern | Key Insight |
|---|---------|---------|-------------|
| 1 | Two Sum (LC 1) ⭐ | HashMap | Complement lookup |
| 2 | Best Time to Buy and Sell Stock (LC 121) ⭐ | Track min so far | Kadane's variant |
| 3 | Contains Duplicate (LC 217) | HashSet | O(n) vs O(n log n) |
| 4 | Valid Anagram (LC 242) | Frequency array | int[26] trick |
| 5 | Merge Sorted Array (LC 88) ⭐ | Three pointers | Fill from end |
| 6 | Move Zeroes (LC 283) | Two pointers in-place | Write pointer |
| 7 | Majority Element (LC 169) ⭐ | Boyer-Moore voting | O(1) space genius |
| 8 | Roman to Integer (LC 13) | Pattern matching | Subtraction rule |
| 9 | Longest Common Prefix (LC 14) | Vertical scan | Compare column by column |
| 10 | Plus One (LC 66) | Carry simulation | Edge: all 9s |

### Medium
| # | Problem | Pattern | Key Insight |
|---|---------|---------|-------------|
| 1 | Product of Array Except Self (LC 238) ⭐⭐ | Prefix + Suffix | Left pass then right pass |
| 2 | Maximum Subarray (LC 53) ⭐⭐ | Kadane's | Extend vs restart |
| 3 | Subarray Sum Equals K (LC 560) ⭐⭐ | Prefix sum + HashMap | Count prefix matches |
| 4 | Sort Colors (LC 75) ⭐ | Dutch National Flag | Three-way partition |
| 5 | Rotate Array (LC 189) | Reverse trick | Three reverses |
| 6 | Container With Most Water (LC 11) ⭐ | Two pointers | Move shorter side |
| 7 | Group Anagrams (LC 49) ⭐ | HashMap + sort key | Sorted string as key |
| 8 | Longest Substring Without Repeating (LC 3) ⭐⭐ | Sliding window | HashMap for last seen |
| 9 | Next Permutation (LC 31) ⭐ | Pattern recognition | Find, swap, reverse |
| 10 | Spiral Matrix (LC 54) ⭐ | Boundary tracking | Shrink boundaries |

### Hard
| # | Problem | Pattern | Key Insight |
|---|---------|---------|-------------|
| 1 | First Missing Positive (LC 41) ⭐ | Index as hash | Place nums[i] at index nums[i]-1 |
| 2 | Trapping Rain Water (LC 42) ⭐⭐ | Two pointers or stack | Water = min(maxL, maxR) - height |
| 3 | Minimum Window Substring (LC 76) ⭐⭐ | Sliding window | Expand right, shrink left |
| 4 | Longest Consecutive Sequence (LC 128) ⭐ | HashSet | Only start counting from sequence start |

---

## Interviewer's Perspective (What I Look For at Meta)

When I give "Two Sum" as a warm-up:
- **Weak candidate**: Writes nested loops O(n²), done.
- **Average candidate**: Uses HashMap, gets O(n). Done.
- **Strong candidate**: Uses HashMap, discusses trade-offs (space vs time), handles edge cases (duplicate values, empty array, single element), mentions that for sorted input we could use two pointers for O(1) space.
- **Exceptional candidate**: All of the above + asks clarifying questions ("Can there be negative numbers? Multiple solutions? Should I return indices or values?")

**The problem isn't a test of whether you know HashMap. It's a test of how you think.**

---

*Next: [Math & Number Theory Basics](03-math-basics.md)*
