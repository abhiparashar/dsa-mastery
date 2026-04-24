/**
 * LeetCode 322 - Coin Change ⭐⭐ (Unbounded knapsack)
 *
 * Given coins of different denominations, find MINIMUM coins to make amount.
 * Return -1 if impossible. Unlimited supply of each coin.
 *
 * SUBPROBLEM: dp[a] = minimum coins to make amount a
 * RECURRENCE: dp[a] = min(dp[a - coin] + 1) for each coin
 * BASE: dp[0] = 0 (zero coins for amount 0)
 *
 * WHY "UNBOUNDED" KNAPSACK:
 * Unlike 0/1 knapsack (use each item once), here each coin can be used
 * unlimited times. The inner loop goes over coins, not items.
 */
public class CoinChange {

    public int coinChange_yours(int[] coins, int amount) {
        // TODO
        return -1;
    }

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        java.util.Arrays.fill(dp, amount + 1); // "infinity"
        dp[0] = 0;

        for (int a = 1; a <= amount; a++) {
            for (int coin : coins) {
                if (coin <= a) {
                    dp[a] = Math.min(dp[a], dp[a - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        CoinChange sol = new CoinChange();

        assert sol.coinChange(new int[]{1,5,10,25}, 30) == 2;
        System.out.println("Test 1: coins=[1,5,10,25], amount=30 → 2 (25+5)");

        assert sol.coinChange(new int[]{2}, 3) == -1;
        System.out.println("Test 2: coins=[2], amount=3 → -1 (impossible)");

        assert sol.coinChange(new int[]{1}, 0) == 0;
        System.out.println("Test 3: amount=0 → 0 coins");

        assert sol.coinChange(new int[]{1,2,5}, 11) == 3;
        System.out.println("Test 4: coins=[1,2,5], amount=11 → 3 (5+5+1)");

        assert sol.coinChange(new int[]{186,419,83,408}, 6249) == 20;
        System.out.println("Test 5: large case → 20");

        System.out.println("\n✓ All tests passed!");
    }
}
