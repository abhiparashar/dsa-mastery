# Lesson 10: Dynamic Programming — The 5-Step Process

> DP = recursion with memory. If you can write a recursive solution, you can convert it to DP. The hard part is defining the subproblem.

---

## The 5-Step DP Process

1. **Define subproblem**: What does `dp[i]` represent? (English first, code later)
2. **Find recurrence**: `dp[i]` in terms of smaller subproblems
3. **Identify base cases**: What do you know for free?
4. **Determine order**: Fill dp array so dependencies are ready
5. **Extract answer**: Usually `dp[n]` or `dp[n-1]` or `max(dp[])`

## Pattern 1: Linear DP

```java
// dp[i] = best answer considering items 0..i
dp[0] = base;
for (int i = 1; i <= n; i++) {
    dp[i] = recurrence(dp[i-1], dp[i-2], ...);
}
return dp[n];
```

**Problems**: Climbing Stairs, House Robber, Coin Change

## Pattern 2: 2D DP (Two sequences)

```java
// dp[i][j] = answer for first i chars of s1 and first j chars of s2
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (s1[i-1] == s2[j-1]) dp[i][j] = dp[i-1][j-1] + 1;
        else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
    }
}
```

**Problems**: Edit Distance, LCS, Longest Common Subsequence

## Pattern 3: Decision DP (Take or Skip)

```java
// At each item: take it or skip it
dp[i] = Math.max(
    dp[i-1],           // skip item i
    dp[i-2] + val[i]   // take item i (can't take i-1)
);
```

**Problems**: House Robber, 0/1 Knapsack

## Pattern 4: LIS (Longest Increasing Subsequence)

```java
// dp[i] = length of LIS ending at index i
for (int i = 0; i < n; i++) {
    dp[i] = 1;
    for (int j = 0; j < i; j++) {
        if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
    }
}
// O(n²) — can be optimized to O(n log n) with patience sorting
```

## Problems

**1. Climbing Stairs (LC 70)** — EASY ⭐ (The "hello world" of DP)  
**2. House Robber (LC 198)** — MEDIUM ⭐⭐ (Take/skip pattern)  
**3. Coin Change (LC 322)** — MEDIUM ⭐⭐ (Unbounded knapsack)  
**4. Longest Increasing Subsequence (LC 300)** — MEDIUM ⭐⭐  
**5. Longest Common Subsequence (LC 1143)** — MEDIUM ⭐⭐  
**6. Edit Distance (LC 72)** — HARD ⭐⭐ (The ultimate 2D DP)  

See full theory in `phase-5-dp/01-dp-fundamentals.md` and `02-classic-patterns.md`.
