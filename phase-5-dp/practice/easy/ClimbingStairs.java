/**
 * LeetCode 70 - Climbing Stairs ⭐ (THE "hello world" of DP)
 *
 * You can climb 1 or 2 steps. How many distinct ways to reach step n?
 *
 * SUBPROBLEM: dp[i] = number of ways to reach step i
 * RECURRENCE: dp[i] = dp[i-1] + dp[i-2]  (come from step i-1 or i-2)
 * BASE: dp[0] = 1, dp[1] = 1
 *
 * This is literally Fibonacci! Space-optimize to O(1).
 */
public class ClimbingStairs {

    public int climbStairs_yours(int n) {
        // TODO
        return 0;
    }

    // Full DP array (for clarity)
    public int climbStairs_dp(int n) {
        if (n <= 1) return 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // Space-optimized O(1)
    public int climbStairs(int n) {
        if (n <= 1) return 1;
        int prev2 = 1, prev1 = 1;
        for (int i = 2; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        ClimbingStairs sol = new ClimbingStairs();

        assert sol.climbStairs(2) == 2;
        System.out.println("Test 1: n=2 → 2 ways (1+1, 2)");

        assert sol.climbStairs(3) == 3;
        System.out.println("Test 2: n=3 → 3 ways");

        assert sol.climbStairs(5) == 8;
        System.out.println("Test 3: n=5 → 8 ways");

        assert sol.climbStairs(1) == 1;
        System.out.println("Test 4: n=1 → 1 way");

        assert sol.climbStairs(10) == 89;
        System.out.println("Test 5: n=10 → 89 ways");

        System.out.println("\n✓ All tests passed!");
    }
}
