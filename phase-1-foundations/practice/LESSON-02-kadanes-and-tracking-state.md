# Lesson 2: Kadane's Algorithm & The Art of Tracking State

> **Why this is Lesson 2**: In Lesson 1, you learned to trade space for time with a HashMap. Now you'll learn something even more powerful — sometimes you don't need extra space at all. You just need to carry the right information forward as you scan. This is **tracking state**, and it's the foundation of greedy algorithms, DP, and real-time stream processing.

---

## The Core Idea: What Information Do I Need to Carry Forward?

Every time you loop through an array, ask yourself:

**"At position i, what do I need to know about positions 0..i-1 to make the best decision at i?"**

If the answer is "everything" — you need DP or brute force.
If the answer is "just a few things" (min so far, max so far, running sum) — you have an O(n) time, O(1) space solution.

This lesson teaches you to identify those "few things."

---

## Problem 1: Best Time to Buy and Sell Stock (LeetCode 121) ⭐⭐

```
You are given an array prices where prices[i] is the price of a stock on day i.

You want to maximize your profit by choosing a single day to buy
and a single day to sell (buy must come BEFORE sell).

Return the maximum profit. If no profit is possible, return 0.

Example 1: prices = [7, 1, 5, 3, 6, 4] → 5
  Buy on day 1 (price=1), sell on day 4 (price=6), profit = 6-1 = 5

Example 2: prices = [7, 6, 4, 3, 1] → 0
  Prices only decrease. No profit possible.
```

### Step 1: Understand What We're Actually Looking For

We need to find two indices `i < j` that maximize `prices[j] - prices[i]`.

Brute force: check all pairs. O(n²).

```java
// BRUTE FORCE — O(n²)
public int maxProfit(int[] prices) {
    int maxProfit = 0;
    for (int i = 0; i < prices.length; i++) {
        for (int j = i + 1; j < prices.length; j++) {
            maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
        }
    }
    return maxProfit;
}
```

Can we do better? Let's think about what the inner loop is doing.

For each day `j`, we're finding the minimum price in `prices[0..j-1]` and computing `prices[j] - min`. The inner loop just finds the minimum of everything before `j`.

**We don't need a loop for that. We can TRACK the minimum as we go.**

### Step 2: The Key Insight — Track Minimum So Far

```
Day:      0    1    2    3    4    5
Price:    7    1    5    3    6    4
Min:      7    1    1    1    1    1     ← running minimum
Profit:   0    0    4    2    5    3     ← price - minSoFar
                                   ^
                              max = 5
```

At each day, we know two things:
1. The cheapest price we could have bought at (minSoFar)
2. The profit if we sell today (price - minSoFar)

We just track the maximum of those profits.

### Step 3: The Solution

```java
// OPTIMAL — O(n) time, O(1) space
public int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;

    for (int price : prices) {
        minPrice = Math.min(minPrice, price);             // best buy price so far
        maxProfit = Math.max(maxProfit, price - minPrice); // best profit if we sell today
    }

    return maxProfit;
}
```

**That's it. 5 lines.** But each line carries deep meaning.

### Step 4: Why This Works (Proof)

**Claim**: The optimal sell day `j` must use the minimum price from `prices[0..j-1]` as the buy day.

**Proof**: Suppose the optimal answer buys at day `i` and sells at day `j`. If there exists some day `i' < j` where `prices[i'] < prices[i]`, then buying at `i'` instead gives a strictly higher profit. Contradiction. So the buy day must be the minimum in `prices[0..j-1]`.

Since we consider every possible sell day (the loop) and for each we use the best possible buy day (minSoFar), we can't miss the optimal answer.

### Step 5: What State Are We Tracking?

```
State 1: minPrice — the best buy opportunity seen so far
State 2: maxProfit — the best profit achievable so far

At each position: update both, using only the previous values.
No array needed. No HashMap needed. Just two variables.
```

**This is the essence of "tracking state" — carry forward only what you need.**

---

## Problem 2: Maximum Subarray (LeetCode 53) ⭐⭐ — Kadane's Algorithm

```
Given an integer array nums, find the subarray with the largest sum,
and return its sum.

A subarray is a CONTIGUOUS non-empty part of the array.

Example 1: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4] → 6
  Subarray [4, -1, 2, 1] has the largest sum = 6

Example 2: nums = [1] → 1

Example 3: nums = [-1] → -1  (must pick at least one element!)

Example 4: nums = [5, 4, -1, 7, 8] → 23  (entire array)
```

### Step 1: Brute Force (Understand the Problem)

```java
// BRUTE FORCE — O(n²)
public int maxSubArray(int[] nums) {
    int maxSum = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
        int sum = 0;
        for (int j = i; j < nums.length; j++) {
            sum += nums[j];
            maxSum = Math.max(maxSum, sum);
        }
    }
    return maxSum;
}
```

We try every starting point `i`, extend the subarray to every ending point `j`, tracking the sum. O(n²).

### Step 2: The Kadane Insight — Extend or Restart?

This is the most important insight in array algorithms. At each position, you have exactly two choices:

1. **Extend**: Add the current element to the subarray ending at the previous position
2. **Restart**: Start a new subarray beginning at the current element

When should you restart? **When the previous subarray sum is negative.** A negative prefix can only hurt you — starting fresh is always better.

```
Think of it like carrying a backpack on a hike:
- If your backpack has positive weight (good stuff), keep it
- If your backpack has negative weight (debt), drop it and start fresh
```

### Step 3: Walk Through the Example

```
nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]

i=0: num=-2
     currentMax = max(-2, 0 + (-2)) = -2  (must take it, first element)
     globalMax = -2

i=1: num=1
     currentMax = max(1, -2 + 1) = max(1, -1) = 1  ← RESTART! (-2 was hurting us)
     globalMax = 1

i=2: num=-3
     currentMax = max(-3, 1 + (-3)) = max(-3, -2) = -2  ← extend (1 still helps)
     globalMax = 1

i=3: num=4
     currentMax = max(4, -2 + 4) = max(4, 2) = 4  ← RESTART! (-2 was hurting us)
     globalMax = 4

i=4: num=-1
     currentMax = max(-1, 4 + (-1)) = max(-1, 3) = 3  ← extend
     globalMax = 4

i=5: num=2
     currentMax = max(2, 3 + 2) = max(2, 5) = 5  ← extend
     globalMax = 5

i=6: num=1
     currentMax = max(1, 5 + 1) = max(1, 6) = 6  ← extend
     globalMax = 6

i=7: num=-5
     currentMax = max(-5, 6 + (-5)) = max(-5, 1) = 1  ← extend (6 still helps)
     globalMax = 6

i=8: num=4
     currentMax = max(4, 1 + 4) = max(4, 5) = 5  ← extend
     globalMax = 6

Answer: 6 ✓ (subarray [4, -1, 2, 1])
```

### Step 4: The Code

```java
// KADANE'S ALGORITHM — O(n) time, O(1) space
public int maxSubArray(int[] nums) {
    int currentMax = nums[0]; // best subarray sum ENDING at current position
    int globalMax = nums[0];  // best subarray sum seen ANYWHERE

    for (int i = 1; i < nums.length; i++) {
        // Key decision: extend the previous subarray, or start fresh?
        currentMax = Math.max(nums[i], currentMax + nums[i]);

        // Track the overall best
        globalMax = Math.max(globalMax, currentMax);
    }

    return globalMax;
}
```

### Step 5: The DP Perspective (Same Algorithm, Different Lens)

Kadane's IS dynamic programming. The recurrence:

```
dp[i] = maximum subarray sum ending at index i

dp[i] = max(nums[i], dp[i-1] + nums[i])
                ↑              ↑
          start fresh    extend previous

Answer = max(dp[0], dp[1], ..., dp[n-1])
```

Since `dp[i]` only depends on `dp[i-1]`, we don't need the array — just one variable (`currentMax`).

**This is your first real DP optimization: when dp[i] depends only on dp[i-1], replace the array with a variable.**

### Step 6: What State Are We Tracking?

```
State 1: currentMax — best subarray sum ending at the current position
State 2: globalMax — best subarray sum ending at any position

The "extend or restart" decision is encoded in:
    currentMax = max(nums[i], currentMax + nums[i])

If currentMax was negative, nums[i] alone is better → restart
If currentMax was non-negative, extending is better → extend
```

---

## Problem 3: Maximum Product Subarray (LeetCode 152) ⭐

```
Given an integer array nums, find the subarray with the largest product.

Example 1: nums = [2, 3, -2, 4] → 6  (subarray [2, 3])
Example 2: nums = [-2, 0, -1]   → 0  (subarray [0])
Example 3: nums = [-2, 3, -4]   → 24 (entire array! negative × negative = positive)
```

### Why This Problem Is Here

This looks like Kadane's, but there's a twist: **a negative number can flip the minimum to the maximum.** -10 × -3 = 30.

So tracking only the maximum isn't enough. We need to track both the maximum AND minimum at each position.

### The Insight

```
At each position, the maximum product subarray ending here is one of:
1. nums[i] alone (restart)
2. maxSoFar × nums[i] (extend with positive result)
3. minSoFar × nums[i] (a negative × negative might be the biggest!)

We must track BOTH max and min because:
- If nums[i] is positive: max extends max, min extends min (normal)
- If nums[i] is negative: max extends MIN (flip!), min extends MAX (flip!)
- If nums[i] is 0: both reset to 0
```

### The Code

```java
// O(n) time, O(1) space
public int maxProduct(int[] nums) {
    int maxProd = nums[0];
    int minProd = nums[0];
    int result = nums[0];

    for (int i = 1; i < nums.length; i++) {
        // If nums[i] is negative, max becomes min and vice versa
        // So we swap before computing
        if (nums[i] < 0) {
            int temp = maxProd;
            maxProd = minProd;
            minProd = temp;
        }

        maxProd = Math.max(nums[i], maxProd * nums[i]);
        minProd = Math.min(nums[i], minProd * nums[i]);

        result = Math.max(result, maxProd);
    }

    return result;
}
```

### Walk Through

```
nums = [-2, 3, -4]

i=0: maxProd=-2, minProd=-2, result=-2

i=1: nums[1]=3 (positive, no swap)
     maxProd = max(3, -2*3) = max(3, -6) = 3
     minProd = min(3, -2*3) = min(3, -6) = -6
     result = max(-2, 3) = 3

i=2: nums[2]=-4 (negative! SWAP max and min)
     After swap: maxProd=-6, minProd=3
     maxProd = max(-4, -6*-4) = max(-4, 24) = 24  ← the magic! min became max!
     minProd = min(-4, 3*-4) = min(-4, -12) = -12
     result = max(3, 24) = 24

Answer: 24 ✓ (entire array: -2 × 3 × -4 = 24)
```

### What Extra State Did We Need?

Kadane's classic tracks 1 value: `currentMax`.
Product variant tracks 2 values: `maxProd` AND `minProd`.

**The meta-lesson: when the operation isn't purely additive (products, abs values, conditional logic), ask: "What ELSE do I need to track?"**

---

## Problem 4: Best Time to Buy and Sell Stock II (LeetCode 122) ⭐

```
You can buy and sell the stock MULTIPLE TIMES.
You can only hold ONE share at a time.
Find the maximum profit.

Example: prices = [7, 1, 5, 3, 6, 4] → 7
  Buy day 1 (1), sell day 2 (5), profit=4
  Buy day 3 (3), sell day 4 (6), profit=3
  Total = 7
```

### The Greedy Insight

You should capture every upward movement. If tomorrow's price is higher than today's, buy today, sell tomorrow.

```java
// O(n) time, O(1) space
public int maxProfit(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            profit += prices[i] - prices[i - 1];
        }
    }
    return profit;
}
```

### Why This Works

Any profitable transaction (buy at i, sell at j) can be decomposed into consecutive daily profits:

```
profit(i, j) = prices[j] - prices[i]
             = (prices[j] - prices[j-1]) + (prices[j-1] - prices[j-2]) + ... + (prices[i+1] - prices[i])
```

So capturing every positive daily change is equivalent to making all optimal transactions.

### State Tracked: Just a Running Total

No need to track when you bought. Just accumulate every positive daily change. This is **greedy** — the locally optimal choice (capture every gain) is globally optimal.

---

## The Master Concept: What Kind of State Do I Need?

Here's a framework for all "scan the array once" problems:

| Problem Pattern | State to Track | Example |
|---|---|---|
| "Best pair (i < j)" | Min/max so far | Buy/Sell Stock I |
| "Best contiguous sum" | Current sum (extend/restart) | Kadane's |
| "Best contiguous product" | Current max AND min | Product subarray |
| "Capture all gains" | Running total of positive diffs | Buy/Sell Stock II |
| "Running frequency" | HashMap of counts | Two Sum, Subarray Sum |
| "Longest streak" | Current streak length | Consecutive ones |
| "At most K violations" | Counter of violations | Longest Substring |

**Before writing code, identify your state variables. If you can list them on one hand, you have an O(n) solution.**

---

## Edge Cases Across All Problems

| Edge Case | Stock I | Kadane's | Product | Stock II |
|-----------|---------|----------|---------|----------|
| Single element | return 0 | return nums[0] | return nums[0] | return 0 |
| All negative | return 0 | return least negative | depends on count | return 0 |
| All same | return 0 | return nums[0]*n? no, just nums[0] | return nums[0]^n | return 0 |
| Contains 0 | no effect | might restart | RESETS product to 0 | no effect |
| Overflow | prices ≤ 10^4, safe | sums can be large, use long if n>10^5 | products OVERFLOW fast, may need long | safe |
| Empty array | handle before loop | handle before loop | handle before loop | return 0 |

### The Zero Trap in Product Subarray

Zero resets the product to 0. After a zero, you're effectively starting a new subarray. The code handles this naturally because:
- `max(0, maxProd * 0) = 0` → restart
- `min(0, minProd * 0) = 0` → restart

But the RESULT might still be 0 if that's the best available (e.g., `[-5, 0, -3]` → answer is 0).

### The Overflow Trap in Product Subarray

For LeetCode 152, values are bounded: -10 ≤ nums[i] ≤ 10, n ≤ 2×10^4. So products can be at most 10^(2×10^4) — way beyond long. But LeetCode guarantees the answer fits in int. So we're fine with int in this specific problem. In an interview, ASK about overflow.

---

## The Progression You Just Learned

```
Problem 1: Track 1 thing (minPrice)                → Buy/Sell Stock I
Problem 2: Track 1 thing (currentMax) + decision   → Kadane's
Problem 3: Track 2 things (max AND min) + decision → Product Subarray
Problem 4: Greedy — no complex state needed         → Buy/Sell Stock II
```

Each problem builds on the previous. The state gets richer, but the framework is identical:

```java
for (each element) {
    update state variables;
    update global answer;
}
```

---

## Now Solve These (In Order)

### Immediate Practice:

**1. Best Time to Buy and Sell Stock (LC 121)** — EASY ⭐⭐
Track minPrice, compute profit at each step.
File: `easy/BuyAndSellStock.java`

**2. Maximum Subarray (LC 53)** — MEDIUM ⭐⭐
Kadane's: extend or restart.
File: `medium/MaximumSubarray.java`

**3. Maximum Product Subarray (LC 152)** — MEDIUM ⭐
Track both max and min product.
File: `medium/MaxProductSubarray.java`

**4. Best Time to Buy and Sell Stock II (LC 122)** — MEDIUM
Greedy: capture every upslope.
File: `medium/BuyAndSellStockII.java`

### Stretch Problems (This Week):

**5. Minimum Size Subarray Sum (LC 209)** — MEDIUM ⭐
```
Find the shortest subarray with sum ≥ target.
```
This is a SLIDING WINDOW problem, but the state-tracking intuition is the same: carry forward a running sum, shrink when the condition is met.

**6. Best Time to Buy and Sell Stock with Cooldown (LC 309)** — MEDIUM ⭐
```
After selling, you must wait one day before buying again.
```
This extends the state machine: instead of 1-2 variables, you track 3 states (hold, sold, rest). It's the bridge from greedy to DP.

**7. Longest Consecutive Sequence (LC 128)** — MEDIUM ⭐
```
Find the length of the longest consecutive sequence.
```
Doesn't use Kadane's, but uses the same "what state do I track" thinking with a HashSet.

---

## Self-Check: Do You Really Understand?

1. In Kadane's, why do we initialize `currentMax = nums[0]` instead of `currentMax = 0`?
2. Why can't we use Kadane's for Maximum Product Subarray directly?
3. In Buy/Sell Stock I, what happens if we track maxPrice instead of minPrice and scan backward? Does it work?
4. Why is Buy/Sell Stock II's greedy approach correct? Could there be a case where NOT selling on an upslope gives better total profit?
5. For an array of all negative numbers, what does Kadane's return?

<details>
<summary>Answers</summary>

1. Because the subarray must be non-empty. If we start at 0 and all numbers are negative, we'd return 0 (empty subarray), which violates the constraint. Starting at nums[0] forces us to include at least one element.

2. Because multiplication by a negative number FLIPS max to min. In addition, `currentMax + nums[i]` is monotonic in `currentMax` — if `currentMax` is larger, the sum is larger. But `currentMax * nums[i]` is NOT monotonic — if `nums[i]` is negative, a larger `currentMax` gives a SMALLER product. So we need to track min too.

3. Yes! Scanning backward, tracking maxPrice so far, computing `maxPrice - prices[i]`. This is the mirror approach and gives the same answer. Both are correct.

4. The proof is in the decomposition: `profit(i,j) = sum of daily gains from i to j`. Capturing every positive daily gain is equivalent to making all optimal multi-day transactions. Skipping a positive daily gain can only reduce total profit.

5. It returns the least negative number (the "largest" among negatives). For [-5, -3, -1, -4], Kadane's returns -1. The best subarray is [-1] — a single element. This is correct per the problem's requirement that the subarray must be non-empty.

</details>

---

## What's Next

**Lesson 3**: Prefix Sum Deep Dive + Sliding Window — the two techniques that turn O(n²) subarray problems into O(n).

You already saw prefix sum in Lesson 1 (SubarraySumEqualsK). Lesson 3 goes deeper: when to use prefix sum vs sliding window, why sliding window fails with negative numbers, and the variable-size window template.

---

*Go solve the 4 problems now. Type every line. For Kadane's (LC 53), first write the brute force, then derive the optimization yourself. If you can't derive it in 20 minutes, re-read the "Extend or Restart" section above, then try again. Don't look at the code.*
