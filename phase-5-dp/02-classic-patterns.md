# Phase 5.2: Classic DP Patterns

> Every DP problem fits into a finite number of patterns. Learn the pattern, and you can solve any variation.

---

## Pattern 1: 0/1 Knapsack

**Problem**: Given items with weights and values, maximize value within weight capacity. Each item used at most once.

```java
// dp[i][w] = max value using first i items with capacity w
// Transition: dp[i][w] = max(dp[i-1][w], dp[i-1][w-weight[i]] + value[i])

// Partition Equal Subset Sum (LC 416) ⭐ — "Can we split into two equal subsets?"
// Reduces to: "Is there a subset with sum = totalSum/2?" → 0/1 knapsack
public boolean canPartition(int[] nums) {
    int sum = 0;
    for (int n : nums) sum += n;
    if (sum % 2 != 0) return false;
    int target = sum / 2;
    
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    
    for (int num : nums) {
        for (int j = target; j >= num; j--) { // iterate BACKWARD for 0/1
            dp[j] = dp[j] || dp[j - num];
        }
    }
    return dp[target];
}

// Target Sum (LC 494) ⭐ — count subsets with sum = (total + target) / 2
```

## Pattern 2: Unbounded Knapsack

**Each item can be used unlimited times.** Iterate FORWARD.

```java
// Coin Change (LC 322) ⭐⭐
// dp[amount] = minimum coins to make this amount
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}

// Coin Change II (LC 518) — count combinations (not permutations!)
// Outer loop = coins, inner loop = amounts → combinations
// Outer loop = amounts, inner loop = coins → permutations
public int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;
    for (int coin : coins) {          // coins outer → each combination counted once
        for (int j = coin; j <= amount; j++) {
            dp[j] += dp[j - coin];
        }
    }
    return dp[amount];
}
```

## Pattern 3: Longest Increasing Subsequence (LIS)

```java
// LC 300 ⭐⭐ — O(n²) DP
public int lengthOfLIS(int[] nums) {
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);
    int maxLen = 1;
    for (int i = 1; i < nums.length; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        maxLen = Math.max(maxLen, dp[i]);
    }
    return maxLen;
}

// O(n log n) — Patience Sorting (binary search approach)
public int lengthOfLIS_optimal(int[] nums) {
    List<Integer> tails = new ArrayList<>();
    for (int num : nums) {
        int pos = Collections.binarySearch(tails, num);
        if (pos < 0) pos = -(pos + 1);
        if (pos == tails.size()) tails.add(num);
        else tails.set(pos, num);
    }
    return tails.size();
}
```

## Pattern 4: Palindrome DP

```java
// Longest Palindromic Substring (LC 5) ⭐⭐
// Expand from center — O(n²) time, O(1) space
public String longestPalindrome(String s) {
    int start = 0, maxLen = 0;
    for (int i = 0; i < s.length(); i++) {
        int len1 = expand(s, i, i);     // odd length
        int len2 = expand(s, i, i + 1); // even length
        int len = Math.max(len1, len2);
        if (len > maxLen) {
            maxLen = len;
            start = i - (len - 1) / 2;
        }
    }
    return s.substring(start, start + maxLen);
}

int expand(String s, int l, int r) {
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
        l--; r++;
    }
    return r - l - 1;
}

// Palindromic Substrings (LC 647) — count all, same expand technique
// Longest Palindromic Subsequence (LC 516) — 2D DP
```

## Pattern 5: Interval DP

```java
// Burst Balloons (LC 312) ⭐
// dp[i][j] = max coins from bursting all balloons between i and j
// Key: think about which balloon to burst LAST in the interval
public int maxCoins(int[] nums) {
    int n = nums.length;
    int[] arr = new int[n + 2];
    arr[0] = arr[n + 1] = 1;
    for (int i = 0; i < n; i++) arr[i + 1] = nums[i];
    
    int[][] dp = new int[n + 2][n + 2];
    for (int len = 1; len <= n; len++) {
        for (int i = 1; i + len - 1 <= n; i++) {
            int j = i + len - 1;
            for (int k = i; k <= j; k++) {
                dp[i][j] = Math.max(dp[i][j],
                    dp[i][k-1] + arr[i-1] * arr[k] * arr[j+1] + dp[k+1][j]);
            }
        }
    }
    return dp[1][n];
}
```

## Pattern 6: State Machine DP

```java
// Best Time to Buy and Sell Stock with Transaction Limit (LC 188) ⭐
// State: day, transactions used, holding or not
public int maxProfit(int k, int[] prices) {
    int n = prices.length;
    if (k >= n / 2) { // unlimited transactions
        int profit = 0;
        for (int i = 1; i < n; i++) profit += Math.max(0, prices[i] - prices[i-1]);
        return profit;
    }
    
    int[][] dp = new int[k + 1][2]; // [transactions][0=not holding, 1=holding]
    for (int j = 0; j <= k; j++) dp[j][1] = Integer.MIN_VALUE;
    
    for (int price : prices) {
        for (int j = k; j >= 1; j--) {
            dp[j][0] = Math.max(dp[j][0], dp[j][1] + price);   // sell
            dp[j][1] = Math.max(dp[j][1], dp[j-1][0] - price); // buy
        }
    }
    return dp[k][0];
}
```

---

## Problem Set

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Coin Change (LC 322) ⭐⭐ | Unbounded knapsack |
| 2 | Coin Change II (LC 518) ⭐ | Unbounded — count combinations |
| 3 | Partition Equal Subset Sum (LC 416) ⭐ | 0/1 knapsack |
| 4 | Target Sum (LC 494) ⭐ | 0/1 knapsack count |
| 5 | Longest Increasing Subsequence (LC 300) ⭐⭐ | LIS |
| 6 | Longest Palindromic Substring (LC 5) ⭐⭐ | Expand or 2D DP |
| 7 | Palindromic Substrings (LC 647) | Expand from center |
| 8 | Maximum Length of Repeated Subarray (LC 718) | 2D DP (LCS variant) |
| 9 | Best Time Buy Sell Stock with Cooldown (LC 309) ⭐ | State machine |
| 10 | Interleaving String (LC 97) | Two-string DP |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Edit Distance (LC 72) ⭐⭐ | Two-string |
| 2 | Burst Balloons (LC 312) ⭐ | Interval DP |
| 3 | Best Time Buy Sell IV (LC 188) ⭐ | State machine |
| 4 | Palindrome Partitioning II (LC 132) | 1D + palindrome check |
| 5 | Distinct Subsequences (LC 115) | Two-string counting |
| 6 | Regular Expression Matching (LC 10) ⭐ | Two-string with '*' |
| 7 | Longest Valid Parentheses (LC 32) ⭐ | 1D DP or stack |

---

*Next: [Advanced DP](03-advanced-dp.md)*
