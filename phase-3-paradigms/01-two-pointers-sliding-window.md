# Phase 3.1: Two Pointers & Sliding Window

> **First Principle**: Two pointers reduce O(n²) brute force to O(n) by exploiting structure — either sortedness or a monotonic property. Sliding window does the same for contiguous subarray/substring problems. Both work by avoiding redundant computation: instead of re-examining every pair or subarray from scratch, you incrementally adjust.

---

## Two Pointers — When to Recognize

**Signal**: The problem involves pairs, and the input is sorted (or can be sorted without losing information).

| If you see... | Think... |
|---------------|----------|
| "Find a pair that sums to target" in sorted array | Two pointers from both ends |
| "Remove duplicates in-place" | Fast/slow pointer |
| "Is this a palindrome?" | Left/right moving inward |
| "Merge two sorted arrays" | Two pointers, one per array |
| "Partition around a pivot" | Two/three pointers |

### Type 1: Opposite Direction (Converging)

```java
// Two Sum II — Input Array is Sorted (LC 167) ⭐
public int[] twoSum(int[] numbers, int target) {
    int lo = 0, hi = numbers.length - 1;
    while (lo < hi) {
        int sum = numbers[lo] + numbers[hi];
        if (sum == target) return new int[]{lo + 1, hi + 1};
        else if (sum < target) lo++;
        else hi--;
    }
    return new int[]{};
}
// WHY it works: if sum is too small, the only way to increase it is move lo right
// (moving hi left would make sum even smaller). This eliminates half the search space.

// 3Sum (LC 15) ⭐⭐ — Meta, Google, Amazon top question
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    
    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue; // skip duplicates
        
        int lo = i + 1, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[i] + nums[lo] + nums[hi];
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                while (lo < hi && nums[lo] == nums[lo + 1]) lo++; // skip dups
                while (lo < hi && nums[hi] == nums[hi - 1]) hi--; // skip dups
                lo++; hi--;
            } else if (sum < 0) lo++;
            else hi--;
        }
    }
    return result;
}

// Container With Most Water (LC 11) ⭐
public int maxArea(int[] height) {
    int lo = 0, hi = height.length - 1, max = 0;
    while (lo < hi) {
        int area = Math.min(height[lo], height[hi]) * (hi - lo);
        max = Math.max(max, area);
        if (height[lo] < height[hi]) lo++;
        else hi--;
    }
    return max;
}
// WHY move the shorter side? The area is limited by the shorter side.
// Moving the taller side can only decrease width (never increase height beyond the limit).
// Moving the shorter side MIGHT find a taller one.
```

### Type 2: Same Direction (Fast/Slow)

```java
// Remove Duplicates from Sorted Array (LC 26) ⭐
public int removeDuplicates(int[] nums) {
    int slow = 0;
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            nums[++slow] = nums[fast];
        }
    }
    return slow + 1;
}

// Move Zeroes (LC 283) ⭐
public void moveZeroes(int[] nums) {
    int write = 0;
    for (int read = 0; read < nums.length; read++) {
        if (nums[read] != 0) {
            nums[write++] = nums[read];
        }
    }
    while (write < nums.length) nums[write++] = 0;
}
```

---

## Sliding Window — When to Recognize

**Signal**: The problem asks about a **contiguous** subarray/substring that satisfies some condition, and you need to find the optimal (longest, shortest, or count).

**Key question: Can the window property be maintained incrementally?**
- Adding an element → update state in O(1)
- Removing an element → update state in O(1)
- If yes → sliding window works

### The Two Templates

#### Template 1: Fixed Window Size

```java
// Maximum Average Subarray I (LC 643)
public double findMaxAverage(int[] nums, int k) {
    int sum = 0;
    for (int i = 0; i < k; i++) sum += nums[i];
    
    int maxSum = sum;
    for (int i = k; i < nums.length; i++) {
        sum += nums[i] - nums[i - k]; // slide: add right, remove left
        maxSum = Math.max(maxSum, sum);
    }
    return (double) maxSum / k;
}
```

#### Template 2: Variable Window Size (THE Template)

```java
// This is the template you'll use for 80% of sliding window problems
int left = 0;
for (int right = 0; right < n; right++) {
    // 1. Expand: add nums[right] to window state
    
    // 2. Shrink: while window is invalid, remove nums[left] and advance left
    while (windowIsInvalid()) {
        // remove nums[left] from window state
        left++;
    }
    
    // 3. Update answer (window [left, right] is now valid)
    // For longest: answer = max(answer, right - left + 1)
    // For shortest: answer = min(answer, right - left + 1)
}
```

### Classic Sliding Window Problems

```java
// Longest Substring Without Repeating Characters (LC 3) ⭐⭐
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> lastSeen = new HashMap<>();
    int maxLen = 0, left = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
            left = lastSeen.get(c) + 1; // jump left past the duplicate
        }
        lastSeen.put(c, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}

// Minimum Window Substring (LC 76) ⭐⭐ — THE hardest standard sliding window
public String minWindow(String s, String t) {
    int[] need = new int[128], have = new int[128];
    int required = 0;
    for (char c : t.toCharArray()) {
        if (need[c]++ == 0) required++;
    }
    
    int formed = 0, left = 0;
    int minLen = Integer.MAX_VALUE, minStart = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        have[c]++;
        if (have[c] == need[c]) formed++;
        
        while (formed == required) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minStart = left;
            }
            char lc = s.charAt(left);
            if (have[lc] == need[lc]) formed--;
            have[lc]--;
            left++;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
}

// Longest Repeating Character Replacement (LC 424) ⭐
public int characterReplacement(String s, int k) {
    int[] count = new int[26];
    int maxFreq = 0, left = 0, maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        count[s.charAt(right) - 'A']++;
        maxFreq = Math.max(maxFreq, count[s.charAt(right) - 'A']);
        
        // Window length - max frequency = number of changes needed
        // If > k, shrink window
        if ((right - left + 1) - maxFreq > k) {
            count[s.charAt(left) - 'A']--;
            left++;
        }
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

---

## Decision Framework

```
Is the input sorted?
├── Yes → TWO POINTERS (converging or same-direction)
└── No → Can you sort it without losing needed information?
    ├── Yes → Sort + TWO POINTERS
    └── No → Is it about a contiguous subarray/substring?
        ├── Yes → SLIDING WINDOW
        │   ├── Fixed size? → Template 1
        │   └── Variable size? → Template 2
        └── No → Different technique (hash map, DP, etc.)
```

---

## Edge Cases

| Case | Trap |
|------|------|
| Array of size 0 or 1 | Window doesn't form |
| All elements same | Two pointers may skip everything |
| k = 0 in sliding window | Window can't grow |
| Negative numbers | Can't use sliding window for "subarray sum ≥ target" with negatives (window monotonicity breaks) |
| Empty string for substring problems | Return "" immediately |
| Window larger than array | Return entire array or handle gracefully |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Two Sum II (LC 167) | Converging two pointers |
| 2 | Valid Palindrome (LC 125) | Converging + skip |
| 3 | Merge Sorted Array (LC 88) ⭐ | Two pointers from end |
| 4 | Remove Duplicates (LC 26) | Fast/slow |
| 5 | Move Zeroes (LC 283) | Fast/slow write pointer |
| 6 | Squares of Sorted Array (LC 977) | Converging, fill from ends |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | 3Sum (LC 15) ⭐⭐ | Sort + converging |
| 2 | Container With Most Water (LC 11) ⭐ | Converging, greedy choice |
| 3 | Longest Substring Without Repeating (LC 3) ⭐⭐ | Variable window + map |
| 4 | Longest Repeating Character Replacement (LC 424) ⭐ | Variable window + freq |
| 5 | Permutation in String (LC 567) ⭐ | Fixed window + freq |
| 6 | Minimum Size Subarray Sum (LC 209) ⭐ | Variable window (shrink) |
| 7 | Fruit Into Baskets (LC 904) | Variable window, at most K distinct |
| 8 | 3Sum Closest (LC 16) | Sort + converging |
| 9 | Sort Colors (LC 75) ⭐ | Three pointers (Dutch flag) |
| 10 | Subarray Product Less Than K (LC 713) | Variable window (count) |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Minimum Window Substring (LC 76) ⭐⭐ | Variable window (shrink) |
| 2 | Sliding Window Maximum (LC 239) ⭐⭐ | Monotonic deque |
| 3 | Trapping Rain Water (LC 42) ⭐⭐ | Two pointers or stack |
| 4 | Substring with Concatenation of All Words (LC 30) | Fixed window + freq |
| 5 | Subarrays with K Different Integers (LC 992) | At most K - at most (K-1) |

---

*Next: [Binary Search — Beyond Sorted Arrays](02-binary-search.md)*
