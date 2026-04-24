# Lesson 3: Prefix Sum & Sliding Window — Two Ways to Conquer Subarrays

> **Why this is Lesson 3**: You've seen prefix sum briefly in Lesson 1 (SubarraySumEqualsK) and Kadane's in Lesson 2. Now we formalize the two main weapons for subarray problems: prefix sum (for any subarray query) and sliding window (for contiguous window optimization). Knowing WHEN to use which is the real skill.

---

## The Decision: Prefix Sum vs Sliding Window

```
Subarray problem?
├── Need sum of arbitrary range [i, j]?          → PREFIX SUM
├── Need count of subarrays with property?        → PREFIX SUM + HASHMAP
├── Contiguous window, ALL elements positive?
│   ├── Find longest/shortest with condition?     → SLIDING WINDOW
│   └── Fixed size window?                        → SLIDING WINDOW (fixed)
└── Contains negative numbers?
    ├── Sum-related query?                        → PREFIX SUM + HASHMAP
    └── NOT sliding window (monotonicity breaks)
```

**The critical rule**: Sliding window works when adding an element can only increase (or only decrease) some property. Negative numbers break this for sums.

---

## Part A: Prefix Sum Deep Dive

### The Idea

```
nums:   [3,  1,  2,  5,  1]
prefix: [0,  3,  4,  6, 11, 12]
         ^                      ^
     empty prefix          sum of all

sum(i, j) = prefix[j+1] - prefix[i]

sum(1, 3) = prefix[4] - prefix[1] = 11 - 3 = 8  (nums[1]+nums[2]+nums[3] = 1+2+5 = 8) ✓
```

Build in O(n), query any range in O(1).

### Template

```java
// Build prefix sum
int[] prefix = new int[nums.length + 1];
for (int i = 0; i < nums.length; i++) {
    prefix[i + 1] = prefix[i] + nums[i];
}
// Range sum [i, j] inclusive
int rangeSum = prefix[j + 1] - prefix[i];
```

### Problem: Range Sum Query - Immutable (LC 303)

```java
class NumArray {
    int[] prefix;

    public NumArray(int[] nums) {
        prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
```

### Problem: Product of Array Except Self (LC 238) ⭐⭐

This is prefix PRODUCT — but with a twist: you can't use division (what if there's a zero?).

**Key insight**: `result[i] = product of everything LEFT of i × product of everything RIGHT of i`

```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];

    // Pass 1: left products
    result[0] = 1;
    for (int i = 1; i < n; i++) {
        result[i] = result[i - 1] * nums[i - 1];
    }

    // Pass 2: multiply by right products
    int rightProduct = 1;
    for (int i = n - 1; i >= 0; i--) {
        result[i] *= rightProduct;
        rightProduct *= nums[i];
    }

    return result;
}
// O(n) time, O(1) extra space (result array doesn't count)
```

**Walk through**: nums = [1, 2, 3, 4]
```
After pass 1 (left products):  result = [1, 1, 2, 6]
Pass 2 (right products from right to left):
  i=3: result[3] = 6 * 1 = 6,   rightProduct = 4
  i=2: result[2] = 2 * 4 = 8,   rightProduct = 12
  i=1: result[1] = 1 * 12 = 12, rightProduct = 24
  i=0: result[0] = 1 * 24 = 24, rightProduct = 24
Result: [24, 12, 8, 6] ✓
```

---

## Part B: Sliding Window

### Fixed-Size Window Template

```java
// Compute something over every window of size k
int windowSum = 0;
for (int i = 0; i < k; i++) windowSum += nums[i]; // init first window

int best = windowSum;
for (int i = k; i < nums.length; i++) {
    windowSum += nums[i] - nums[i - k]; // slide: add right, remove left
    best = Math.max(best, windowSum);    // or min, or whatever
}
```

### Variable-Size Window Template (THE TEMPLATE)

```java
int left = 0;
for (int right = 0; right < n; right++) {
    // 1. EXPAND: add nums[right] to window state

    // 2. SHRINK: while window is invalid
    while (windowInvalid()) {
        // remove nums[left] from window state
        left++;
    }

    // 3. UPDATE: window [left, right] is now valid
    answer = Math.max(answer, right - left + 1); // for longest
    // answer = Math.min(answer, right - left + 1); // for shortest
}
```

### Problem: Minimum Size Subarray Sum (LC 209) ⭐

```
Find the minimal length subarray with sum ≥ target.
All numbers are POSITIVE.

Example: target = 7, nums = [2,3,1,2,4,3] → 2 (subarray [4,3])
```

```java
public int minSubArrayLen(int target, int[] nums) {
    int left = 0, sum = 0;
    int minLen = Integer.MAX_VALUE;

    for (int right = 0; right < nums.length; right++) {
        sum += nums[right]; // expand

        while (sum >= target) { // window is "valid" (sum ≥ target)
            minLen = Math.min(minLen, right - left + 1);
            sum -= nums[left]; // shrink to find shorter
            left++;
        }
    }

    return minLen == Integer.MAX_VALUE ? 0 : minLen;
}
```

**Why sliding window works here**: All numbers are positive. So expanding (adding) always increases sum, shrinking (removing) always decreases sum. This monotonicity lets us safely shrink.

**Why it WOULDN'T work with negatives**: If nums = [2, -1, 3], target = 4. Removing -1 would INCREASE the sum. The shrink logic breaks.

### Problem: Longest Substring Without Repeating Characters (LC 3) ⭐⭐

```
Find the length of the longest substring without repeating characters.

Example: "abcabcbb" → 3 ("abc")
```

```java
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> lastSeen = new HashMap<>();
    int maxLen = 0, left = 0;

    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);

        // If we've seen this char and it's within our current window
        if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
            left = lastSeen.get(c) + 1; // jump left past the duplicate
        }

        lastSeen.put(c, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }

    return maxLen;
}
```

**Why jump instead of shrink one-by-one?** We know exactly where the duplicate is (from the HashMap). Shrinking one-by-one would be correct but wasteful.

### Problem: Permutation in String (LC 567) ⭐

```
Given strings s1 and s2, return true if s2 contains a permutation of s1.

Example: s1 = "ab", s2 = "eidbaooo" → true ("ba" is permutation of "ab")
```

```java
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;

    int[] need = new int[26], have = new int[26];
    for (char c : s1.toCharArray()) need[c - 'a']++;

    int k = s1.length();

    // Initialize first window
    for (int i = 0; i < k; i++) have[s2.charAt(i) - 'a']++;
    if (Arrays.equals(need, have)) return true;

    // Slide window
    for (int i = k; i < s2.length(); i++) {
        have[s2.charAt(i) - 'a']++;        // add right
        have[s2.charAt(i - k) - 'a']--;    // remove left
        if (Arrays.equals(need, have)) return true;
    }

    return false;
}
```

**Optimization**: Instead of comparing 26-element arrays each time (O(26) per step), track a `matches` counter. Only O(1) per step. But for interviews, the simple version is fine.

---

## 2D Prefix Sum (Bonus — appears in Google interviews)

```java
// For a matrix, prefix[i][j] = sum of all elements in rectangle (0,0) to (i-1, j-1)
int[][] prefix = new int[m + 1][n + 1];
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        prefix[i][j] = matrix[i-1][j-1]
                      + prefix[i-1][j]
                      + prefix[i][j-1]
                      - prefix[i-1][j-1]; // subtract double-counted
    }
}

// Sum of rectangle (r1,c1) to (r2,c2):
int sum = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1];
```

---

## Edge Cases

| Scenario | Prefix Sum | Sliding Window |
|----------|-----------|----------------|
| Empty array | prefix = [0] | return 0 |
| All zeros | Works fine | Window may never satisfy condition |
| Single element | prefix = [0, nums[0]] | Window of size 1 |
| Negative numbers | Works always | DOES NOT WORK for sum windows |
| Very large sums | Use long | Use long |
| k > array length | No window possible | Return 0 or handle |
| All elements satisfy | Entire array is the answer | left stays at 0 |

---

## Now Solve These (In Order)

### Immediate:

**1. Product of Array Except Self (LC 238)** — MEDIUM ⭐⭐
Left prefix product × right prefix product. No division allowed.
File: `medium/ProductExceptSelf.java`

**2. Minimum Size Subarray Sum (LC 209)** — MEDIUM ⭐
Sliding window — shrink when sum ≥ target.
File: `medium/MinSizeSubarraySum.java`

**3. Longest Substring Without Repeating Characters (LC 3)** — MEDIUM ⭐⭐
Sliding window + HashMap for last seen index.
File: `medium/LongestSubstringNoRepeat.java`

**4. Permutation in String (LC 567)** — MEDIUM ⭐
Fixed-size sliding window + frequency match.
File: `medium/PermutationInString.java`

### Stretch:

**5. Find All Anagrams in a String (LC 438)** — MEDIUM
Same as Permutation in String but collect ALL positions.

**6. Minimum Window Substring (LC 76)** — HARD ⭐⭐
Variable window + frequency counting. The hardest standard sliding window problem.
File: `hard/MinWindowSubstring.java`

---

## Self-Check

1. Why can't you use sliding window for "subarray sum equals K" when negatives exist?
2. What's the time complexity of the 2-pass Product of Array Except Self?
3. In the variable window template, why do we use `while` for shrinking, not `if`?
4. For LC 3 (longest substring), what happens if we use a `Set` instead of a `Map`? Does it still work?
5. What's the relationship between prefix sum and Kadane's algorithm?

<details>
<summary>Answers</summary>

1. Because removing an element from the left can increase the sum (if it's negative), so the window sum isn't monotonic. You can't safely shrink.

2. O(n) — two passes, each O(n). Space is O(1) extra (the result array is required output).

3. Because removing one element might not be enough to restore validity. We may need to shrink multiple times. Example: window [1,1,1,1,1], max sum = 3. Adding 1 makes sum = 6. Need to remove 3 elements, not 1.

4. With a Set, when you find a duplicate, you'd need to shrink left one-by-one until the duplicate is removed. It works but is slower in practice. The Map lets you jump directly.

5. Kadane's is essentially "prefix sum where we reset the prefix to 0 whenever it goes negative." The running sum in Kadane's IS a prefix sum — we just restart it when it becomes a liability.

</details>

---

*Next: Lesson 4 — Binary Search: Beyond Sorted Arrays*
