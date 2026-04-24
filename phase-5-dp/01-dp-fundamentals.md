# Phase 5.1: Dynamic Programming from First Principles

> **First Principle**: DP is NOT memorization. DP is recognizing that a problem has **overlapping subproblems** and **optimal substructure**. It's recursion made efficient. If you can write a brute-force recursive solution, you can convert it to DP. Period.

---

## The DP Mindset Shift

Most people fail at DP because they try to "see" the DP table from the start. Wrong approach.

**The correct process:**
1. **Write the recursive brute force first.** Don't think about optimization.
2. **Identify overlapping subproblems.** Is the same function called with the same arguments multiple times?
3. **Add memoization.** Cache results. Done — you have top-down DP.
4. **Convert to bottom-up if needed.** Fill a table iteratively.
5. **Optimize space if possible.** Often only the previous row/few values matter.

---

## Step-by-Step Example: Climbing Stairs (LC 70)

### Step 1: Brute Force Recursion
```java
// How many ways to reach step n (1 or 2 steps at a time)?
int climbStairs(int n) {
    if (n <= 1) return 1;
    return climbStairs(n - 1) + climbStairs(n - 2);
}
// Time: O(2^n) — each call branches into two. Terrible.
```

### Step 2: Notice Overlapping Subproblems
```
climbStairs(5)
├── climbStairs(4)
│   ├── climbStairs(3)    ← computed here
│   └── climbStairs(2)
└── climbStairs(3)        ← AND here (same work!)
    ├── climbStairs(2)
    └── climbStairs(1)
```

### Step 3: Top-Down DP (Memoization)
```java
int climbStairs(int n) {
    return dp(n, new int[n + 1]);
}
int dp(int n, int[] memo) {
    if (n <= 1) return 1;
    if (memo[n] != 0) return memo[n];
    memo[n] = dp(n - 1, memo) + dp(n - 2, memo);
    return memo[n];
}
// Time: O(n), Space: O(n)
```

### Step 4: Bottom-Up DP
```java
int climbStairs(int n) {
    int[] dp = new int[n + 1];
    dp[0] = dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n];
}
// Time: O(n), Space: O(n)
```

### Step 5: Space Optimization
```java
int climbStairs(int n) {
    int prev2 = 1, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
// Time: O(n), Space: O(1) — only need last two values
```

---

## How to Write the Recurrence

**The most important skill in DP.** Ask yourself:

1. **What is the state?** What variables define a subproblem uniquely?
2. **What is the base case?** The smallest subproblem you can solve directly.
3. **What is the transition?** How do I build the answer from smaller subproblems?
4. **What is the answer?** Which state gives the final answer?

### Framework

```
Define: dp[state] = answer to the subproblem defined by this state

Base case: dp[smallest_state] = known_value

Transition: dp[state] = f(dp[smaller_states])

Answer: dp[final_state] or max/min over all states
```

---

## 1D DP Patterns

### Pattern 1: Linear Sequence

```java
// House Robber (LC 198) ⭐⭐
// dp[i] = max money robbing houses 0..i
// Either rob house i (skip i-1) or don't rob house i (take best of 0..i-1)
public int rob(int[] nums) {
    int n = nums.length;
    if (n == 1) return nums[0];
    int prev2 = nums[0], prev1 = Math.max(nums[0], nums[1]);
    for (int i = 2; i < n; i++) {
        int curr = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}

// Decode Ways (LC 91) ⭐
// dp[i] = number of ways to decode s[0..i-1]
public int numDecodings(String s) {
    int n = s.length();
    int prev2 = 1, prev1 = s.charAt(0) == '0' ? 0 : 1;
    for (int i = 2; i <= n; i++) {
        int curr = 0;
        int oneDigit = s.charAt(i - 1) - '0';
        int twoDigit = Integer.parseInt(s.substring(i - 2, i));
        if (oneDigit >= 1) curr += prev1;
        if (twoDigit >= 10 && twoDigit <= 26) curr += prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
```

### Pattern 2: Decision at Each Step

```java
// Best Time to Buy and Sell Stock with Cooldown (LC 309) ⭐
// States: hold, sold, rest
public int maxProfit(int[] prices) {
    int hold = -prices[0], sold = 0, rest = 0;
    for (int i = 1; i < prices.length; i++) {
        int newHold = Math.max(hold, rest - prices[i]);
        int newSold = hold + prices[i];
        int newRest = Math.max(rest, sold);
        hold = newHold; sold = newSold; rest = newRest;
    }
    return Math.max(sold, rest);
}
```

---

## 2D DP Patterns

### Pattern 1: Grid DP

```java
// Unique Paths (LC 62) ⭐
public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    Arrays.fill(dp[0], 1);
    for (int i = 0; i < m; i++) dp[i][0] = 1;
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = dp[i-1][j] + dp[i][j-1];
        }
    }
    return dp[m-1][n-1];
}

// Minimum Path Sum (LC 64)
// dp[i][j] = min cost to reach (i,j)
```

### Pattern 2: Two Strings DP

```java
// Longest Common Subsequence (LC 1143) ⭐⭐
// dp[i][j] = LCS of text1[0..i-1] and text2[0..j-1]
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i-1) == text2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
    }
    return dp[m][n];
}

// Edit Distance (LC 72) ⭐⭐ — Meta, Google classic
// dp[i][j] = min operations to convert word1[0..i-1] to word2[0..j-1]
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i-1) == word2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1];
            } else {
                dp[i][j] = 1 + Math.min(dp[i-1][j-1],   // replace
                                Math.min(dp[i-1][j],       // delete
                                         dp[i][j-1]));     // insert
            }
        }
    }
    return dp[m][n];
}
```

---

## How to Recognize DP Problems

| Signal | Why DP |
|--------|--------|
| "Count the number of ways" | Counting problems → DP |
| "Minimum/maximum cost/value" | Optimization → DP |
| "Can you reach / is it possible" | Decision → DP (or greedy) |
| "Longest/shortest subsequence" | Subsequence + optimization → DP |
| "Given choices at each step" | Decision at each step → DP |
| Constraints: n ≤ 1000 (2D) or n ≤ 10^5 (1D) | Feasible for DP |

**DP vs Greedy**: If local optimal choices don't guarantee global optimum, you need DP. If they do, greedy suffices.

---

## Edge Cases

| Case | Trap |
|------|------|
| Empty input | dp array size 0 |
| Single element | Base case handling |
| All same values | Still need to process correctly |
| Negative values | Min/max initial values must account for negatives |
| Integer overflow | Use long for counting problems |
| Base case off-by-one | dp[0] vs dp[1] — think carefully about what index 0 means |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Climbing Stairs (LC 70) ⭐ | Linear 1D |
| 2 | Min Cost Climbing Stairs (LC 746) | Linear 1D |
| 3 | Fibonacci Number (LC 509) | Linear 1D |
| 4 | Pascal's Triangle (LC 118) | 2D building |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | House Robber (LC 198) ⭐⭐ | Skip or take |
| 2 | House Robber II (LC 213) ⭐ | Circular: two passes |
| 3 | Longest Increasing Subsequence (LC 300) ⭐⭐ | O(n²) DP or O(n log n) |
| 4 | Coin Change (LC 322) ⭐⭐ | Unbounded knapsack |
| 5 | Unique Paths (LC 62) ⭐ | Grid DP |
| 6 | Decode Ways (LC 91) ⭐ | String 1D |
| 7 | Longest Common Subsequence (LC 1143) ⭐⭐ | Two-string DP |
| 8 | Maximum Product Subarray (LC 152) ⭐ | Track min AND max |
| 9 | Word Break (LC 139) ⭐⭐ | String + set lookup |
| 10 | Jump Game (LC 55) ⭐ | DP or greedy |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Edit Distance (LC 72) ⭐⭐ | Two-string DP |
| 2 | Longest Valid Parentheses (LC 32) ⭐ | Stack or DP |
| 3 | Regular Expression Matching (LC 10) ⭐ | Two-string with wildcards |
| 4 | Wildcard Matching (LC 44) | Two-string with '*' |

---

*Next: [Classic DP Patterns](02-classic-patterns.md)*
