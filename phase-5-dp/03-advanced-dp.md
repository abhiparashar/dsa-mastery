# Phase 5.3: Advanced DP — Bitmask, Interval, DP on Trees

> These patterns appear in hard interview problems and competitive programming. Master them after the fundamentals are solid.

---

## Bitmask DP

**When to use**: Small n (≤ 20), need to track which elements are "used."

**Key idea**: Represent a subset of n elements as an n-bit integer.

```java
// Partition to K Equal Sum Subsets (LC 698)
// State: bitmask of which numbers are used
public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = Arrays.stream(nums).sum();
    if (sum % k != 0) return false;
    int target = sum / k;
    int n = nums.length;
    boolean[] dp = new boolean[1 << n];
    int[] subsetSum = new int[1 << n];
    dp[0] = true;
    
    Arrays.sort(nums);
    for (int mask = 0; mask < (1 << n); mask++) {
        if (!dp[mask]) continue;
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) continue; // already used
            int newMask = mask | (1 << i);
            if (subsetSum[mask] % target + nums[i] <= target) {
                dp[newMask] = true;
                subsetSum[newMask] = subsetSum[mask] + nums[i];
            }
        }
    }
    return dp[(1 << n) - 1];
}
```

### Bit Operations Cheat Sheet
```java
// Check if bit i is set:        (mask >> i) & 1  or  (mask & (1 << i)) != 0
// Set bit i:                     mask | (1 << i)
// Clear bit i:                   mask & ~(1 << i)
// Toggle bit i:                  mask ^ (1 << i)
// Count set bits:                Integer.bitCount(mask)
// All bits set (n elements):     (1 << n) - 1
// Iterate over subsets of mask:  for (int sub = mask; sub > 0; sub = (sub - 1) & mask)
```

---

## DP on Trees

**Pattern**: DFS + compute DP values bottom-up.

```java
// Binary Tree Maximum Path Sum (LC 124) ⭐⭐
int maxSum = Integer.MIN_VALUE;
public int maxPathSum(TreeNode root) {
    dfs(root);
    return maxSum;
}
int dfs(TreeNode node) {
    if (node == null) return 0;
    int left = Math.max(0, dfs(node.left));   // take 0 if negative
    int right = Math.max(0, dfs(node.right));
    maxSum = Math.max(maxSum, left + right + node.val); // path through node
    return Math.max(left, right) + node.val;            // best single-branch
}

// House Robber III (LC 337) — rob/not-rob on tree
public int rob(TreeNode root) {
    int[] result = dfs(root); // [not_rob, rob]
    return Math.max(result[0], result[1]);
}
int[] dfs(TreeNode node) {
    if (node == null) return new int[]{0, 0};
    int[] left = dfs(node.left);
    int[] right = dfs(node.right);
    int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    int rob = node.val + left[0] + right[0];
    return new int[]{notRob, rob};
}
```

---

## Matrix Chain / Interval DP

**Pattern**: dp[i][j] depends on dp[i][k] and dp[k+1][j] for all k in [i, j].

```java
// Minimum Cost to Merge Stones
// Burst Balloons (LC 312) — covered in patterns
// Iterate by interval length, from small to large
for (int len = 2; len <= n; len++) {
    for (int i = 0; i + len - 1 < n; i++) {
        int j = i + len - 1;
        for (int k = i; k < j; k++) {
            dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k+1][j] + cost(i, j));
        }
    }
}
```

---

## Problem Set

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Binary Tree Max Path Sum (LC 124) ⭐⭐ | DP on tree |
| 2 | House Robber III (LC 337) | DP on tree |
| 3 | Partition to K Equal Sum Subsets (LC 698) | Bitmask DP |
| 4 | Shortest Hamilton Path | Bitmask DP — TSP variant |
| 5 | Burst Balloons (LC 312) ⭐ | Interval DP |
| 6 | Strange Printer (LC 664) | Interval DP |
| 7 | Super Egg Drop (LC 887) | Binary search + DP |
| 8 | Minimum Cost to Merge Stones (LC 1000) | Interval DP |
| 9 | Number of Ways to Wear Hats (LC 1434) | Bitmask DP |
| 10 | Minimum Cost to Cut a Stick (LC 1547) | Interval DP |

---

*Next: Phase 6 — [Monotonic Stack & Queue](../phase-6-advanced-algos/01-monotonic.md)*
