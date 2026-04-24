# Lesson 1: Two Sum and the Power of Hashing

> **Why we start here**: Two Sum is not an "easy" problem — it's a *foundational* problem. It teaches you the single most important optimization in all of DSA: **trading space for time using a HashMap**. Every MAANG interview starts with a variant of this idea.

---

## Problem: Two Sum (LeetCode 1)

```
Given an array of integers nums and an integer target,
return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution,
and you may not use the same element twice.

Example:
Input:  nums = [2, 7, 11, 15], target = 9
Output: [0, 1]
Explanation: nums[0] + nums[1] = 2 + 7 = 9
```

---

## Step 1: Before You Code — THINK

Read the problem again. Ask yourself these questions (in an interview, ask the interviewer):

1. **Can there be negative numbers?** — Yes (not stated otherwise)
2. **Can there be duplicates?** — Yes
3. **Is the array sorted?** — No (not stated)
4. **Is there always exactly one answer?** — Yes (stated in problem)
5. **Do I return indices or values?** — Indices

These 5 questions take 30 seconds and save 10 minutes of bugs.

---

## Step 2: Brute Force — Always Start Here

**Don't be ashamed of brute force.** It shows you understand the problem.

**Thinking**: Check every possible pair. For each element, try pairing it with every element after it.

```java
// BRUTE FORCE — O(n²) time, O(1) space
public int[] twoSum(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[]{i, j};
            }
        }
    }
    return new int[]{}; // never reached per problem guarantee
}
```

**Complexity**:
- Time: O(n²) — two nested loops, each up to n
- Space: O(1) — no extra data structures

**In an interview, say**: "The brute force is O(n²) — we check all pairs. Can we do better?"

The interviewer will say yes. Now you need to think.

---

## Step 3: The Key Insight — Think About What You're SEARCHING For

In the brute force, what is the inner loop actually doing?

```java
for (int j = i + 1; j < nums.length; j++) {
    if (nums[i] + nums[j] == target) { ... }
}
```

It's searching for `target - nums[i]`. That's it. For each element, we search for its **complement**.

**Searching in an unsorted array is O(n). But what if we could search in O(1)?**

That's exactly what a HashMap does.

---

## Step 4: The Optimal Solution

**The Idea**: As we scan the array, we build a HashMap of {value → index}. For each new element, we check: "Is my complement already in the map?"

```java
// OPTIMAL — O(n) time, O(n) space
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        
        if (seen.containsKey(complement)) {
            return new int[]{seen.get(complement), i};
        }
        
        seen.put(nums[i], i);
    }
    
    return new int[]{};
}
```

**Walk through the example**: nums = [2, 7, 11, 15], target = 9

```
i=0: nums[0]=2, complement=7, seen={}, 7 not in seen → add {2:0}
     seen = {2:0}

i=1: nums[1]=7, complement=2, seen={2:0}, 2 IS in seen! → return [0, 1] ✓
```

Done in 2 steps instead of checking all pairs.

---

## Step 5: Why This Works — First Principles

1. **We need two numbers a + b = target**, which means **b = target - a**.
2. For each number `a`, we need to know if `b` exists AND where it is (its index).
3. A HashMap maps value → index in O(1).
4. We build the map as we go — we only look backward, never using the same element twice.

**The space-time tradeoff**: We spent O(n) extra space (the HashMap) to reduce time from O(n²) to O(n). This tradeoff appears in 70%+ of interview optimizations.

---

## Step 6: Edge Cases You Must Consider

| Edge Case | Does Our Code Handle It? |
|-----------|--------------------------|
| Array of size 2 | Yes — checks pair [0,1] |
| Negative numbers | Yes — HashMap works with any integer |
| Target = 0, nums = [-1, 1] | Yes — complement of -1 is 1 |
| Duplicate values: [3,3], target=6 | Yes — we check BEFORE adding, so index 0 maps to 3, index 1 finds complement 3 at index 0 |
| Same element used twice: [3,5], target=6 | No issue — we only find complements from EARLIER indices |
| Very large array (10^5) | Yes — O(n) handles it fine |

**The duplicate case is the sneaky one.** Walk through it:
```
nums = [3, 3], target = 6

i=0: complement = 3, seen = {}, 3 not in seen → add {3:0}
i=1: complement = 3, seen = {3:0}, 3 IS in seen → return [0, 1] ✓
```

It works because we check BEFORE inserting.

---

## Step 7: What You Just Learned (The Transferable Patterns)

### Pattern: Complement Lookup
**Whenever you need to find a pair satisfying some equation, think HashMap + complement.**

This pattern appears in:
- 3Sum (fix one, two-sum the rest)
- 4Sum II (split into two halves)
- Subarray Sum Equals K (prefix sum complement)
- Two Sum BST (complement in tree)

### Concept: Space-Time Tradeoff
**When brute force is too slow, ask: "What can I precompute and store?"**

Usually the answer is a HashMap or HashSet.

### Skill: Single-Pass vs Two-Pass
Our solution uses a single pass — build and query the map simultaneously. A two-pass solution (build map first, then query) also works but is slightly less elegant:

```java
// Two-pass version — also O(n), but 2 passes
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        map.put(nums[i], i); // pass 1: build map (later index overwrites earlier)
    }
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement) && map.get(complement) != i) { // pass 2: query
            return new int[]{i, map.get(complement)};
        }
    }
    return new int[]{};
}
```

The single-pass is better because:
1. Only one loop (cleaner)
2. Naturally avoids using the same element twice
3. Doesn't need the `!= i` check

---

## Step 8: Variations You'll See in Interviews

### Variation A: "What if the array is SORTED?"

Now you don't need a HashMap. Two pointers from both ends is better — O(1) space.

```java
// Sorted array → Two Pointers — O(n) time, O(1) space
public int[] twoSum(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo < hi) {
        int sum = nums[lo] + nums[hi];
        if (sum == target) return new int[]{lo, hi};
        else if (sum < target) lo++;  // need bigger sum
        else hi--;                     // need smaller sum
    }
    return new int[]{};
}
```

**Why this works**: If sum is too small, moving `lo` right can only increase it (sorted!). If too big, moving `hi` left can only decrease it.

### Variation B: "What if we need to return ALL pairs?"

```java
// Return all pairs (not just one)
public List<int[]> twoSumAll(int[] nums, int target) {
    Map<Integer, List<Integer>> map = new HashMap<>();
    List<int[]> result = new ArrayList<>();
    
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            for (int j : map.get(complement)) {
                result.add(new int[]{j, i});
            }
        }
        map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
    }
    return result;
}
```

### Variation C: "What if we want to count pairs instead of returning them?"

```java
// Count pairs that sum to target
public int twoSumCount(int[] nums, int target) {
    Map<Integer, Integer> freq = new HashMap<>();
    int count = 0;
    for (int num : nums) {
        count += freq.getOrDefault(target - num, 0);
        freq.merge(num, 1, Integer::sum);
    }
    return count;
}
```

---

## Now Solve These (In Order)

### Do Immediately (reinforce the pattern):

**1. Contains Duplicate (LC 217)** — EASY
```
Given array, return true if any value appears at least twice.
```
Hint: HashSet. One line of insight. If `set.add(num)` returns false, you have a duplicate.

**2. Valid Anagram (LC 242)** — EASY
```
Given strings s and t, return true if t is an anagram of s.
```
Hint: Frequency counting. Either sort both (O(n log n)) or use int[26] (O(n)).

**3. Two Sum II - Sorted Array (LC 167)** — MEDIUM
```
Same as Two Sum, but array is sorted. Return 1-indexed pair.
```
Hint: Two pointers. You just learned this in Variation A.

### Do This Week (extends the pattern):

**4. Group Anagrams (LC 49)** — MEDIUM ⭐
```
Group strings that are anagrams of each other.
```
Hint: HashMap where key = sorted string. What's the time complexity?

**5. Subarray Sum Equals K (LC 560)** — MEDIUM ⭐⭐
```
Count subarrays that sum to k.
```
Hint: This is Two Sum in disguise. Replace "two numbers" with "two prefix sums." If prefix[j] - prefix[i] = k, then the subarray (i, j] sums to k. Use the exact same complement lookup pattern.

This one is HARD to see. Spend 30 minutes before looking at the hint below.

<details>
<summary>Click for detailed hint (only after 30 min of trying)</summary>

Think of it this way:
- Running sum at position j = prefix[j]
- You need: prefix[j] - prefix[i] = k, so prefix[i] = prefix[j] - k
- For each j, count how many earlier prefix sums equal (prefix[j] - k)
- This is EXACTLY Two Sum, but with prefix sums as the "numbers" and k as the "target"

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1); // empty prefix (subarray from index 0)
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        count += prefixCount.getOrDefault(sum - k, 0);
        prefixCount.merge(sum, 1, Integer::sum);
    }
    return count;
}
```
</details>

---

## Self-Check: Do You Really Understand?

Answer these without looking back:

1. Why can't we use two pointers on an unsorted array for Two Sum?
2. What's the time complexity of HashMap.containsKey()? Average? Worst case?
3. Why do we check the map BEFORE inserting in the single-pass solution?
4. If we sorted the array first, we'd lose the original indices. How would you handle that?
5. In what scenario would brute force O(n²) actually be BETTER than HashMap O(n)?

<details>
<summary>Answers</summary>

1. Two pointers needs sorted order to decide which pointer to move. Without sorting, we don't know if moving left will increase or decrease the sum.

2. Average O(1), worst case O(n) with hash collisions (Java 8+ degrades to O(log n) using tree bins).

3. To avoid using the same element twice. If nums = [3, 5], target = 6, inserting first would let index 0 find itself as the complement.

4. Store (value, original_index) pairs, sort by value, then two-pointer. Or use the HashMap approach which naturally preserves indices.

5. When n is very small (< ~20), the overhead of creating a HashMap (object allocation, hashing) might make it slower. Also when memory is extremely constrained. In practice: always use HashMap for interviews.
</details>

---

## What's Next

**Lesson 2**: Best Time to Buy and Sell Stock + Maximum Subarray → introduces **Kadane's Algorithm** and the concept of "tracking the best seen so far."

These problems teach you to think about **state**: what information do I need to carry forward as I scan?

---

*Go solve Two Sum on LeetCode right now. Type every line yourself. Don't copy-paste. Then do problems 1-3 above. Come back when done.*
