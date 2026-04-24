/**
 * LeetCode 122 - Best Time to Buy and Sell Stock II ⭐
 *
 * You can buy and sell MULTIPLE TIMES (but hold at most 1 share).
 * Find the maximum total profit.
 *
 * Example 1: [7,1,5,3,6,4] → 7 (buy@1 sell@5=4, buy@3 sell@6=3 → total 7)
 * Example 2: [1,2,3,4,5]   → 4 (buy@1 sell@5, or buy@1 sell@2 buy@2 sell@3... = same)
 * Example 3: [7,6,4,3,1]   → 0 (prices only fall, no profit)
 *
 * KEY INSIGHT (this is a greedy problem):
 *
 * Any multi-day profit can be decomposed into consecutive daily profits:
 *   profit(day1 → day5) = (day2-day1) + (day3-day2) + (day4-day3) + (day5-day4)
 *
 * So capturing EVERY positive daily change gives the same result as
 * making all optimal transactions.
 *
 * PROOF:
 * Suppose we buy at day i and sell at day j (i < j).
 * Profit = prices[j] - prices[i]
 *        = (prices[j] - prices[j-1]) + (prices[j-1] - prices[j-2]) + ... + (prices[i+1] - prices[i])
 *        = sum of daily changes from i to j
 *
 * If we capture every positive daily change, we get at least as much
 * as any set of transactions. And we can't do better, because every
 * positive daily change IS part of some profitable transaction.
 *
 * CONTRAST WITH STOCK I:
 * Stock I: one transaction → track minPrice, compute best single sell
 * Stock II: unlimited transactions → capture every upslope (greedy)
 */
public class BuyAndSellStockII {

    // ==========================================
    // YOUR SOLUTION
    // ==========================================
    public int maxProfit_yours(int[] prices) {
        // TODO: Capture every positive daily change

        return 0;
    }

    // ==========================================
    // GREEDY — O(n) time, O(1) space
    // ==========================================
    public int maxProfit(int[] prices) {
        int totalProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            // If price went up today, capture the gain
            if (prices[i] > prices[i - 1]) {
                totalProfit += prices[i] - prices[i - 1];
            }
        }

        return totalProfit;
    }

    // ==========================================
    // DP APPROACH — for comparison (leads to Stock III and IV)
    // ==========================================
    // States: at each day, you're either HOLDING or NOT HOLDING stock
    // hold[i] = max profit on day i if you're holding stock
    // notHold[i] = max profit on day i if you're not holding
    public int maxProfit_dp(int[] prices) {
        int hold = -prices[0]; // bought on day 0
        int notHold = 0;       // haven't bought

        for (int i = 1; i < prices.length; i++) {
            int newHold = Math.max(hold, notHold - prices[i]);   // keep holding OR buy today
            int newNotHold = Math.max(notHold, hold + prices[i]); // keep not holding OR sell today
            hold = newHold;
            notHold = newNotHold;
        }

        return notHold; // at the end, not holding is always >= holding
    }

    // WHY SHOW THE DP APPROACH?
    // Because when you add constraints (cooldown, fee, K transactions),
    // greedy stops working but the DP approach generalizes:
    // - Stock with cooldown (LC 309): add a "just sold" state
    // - Stock with fee (LC 714): subtract fee on sell
    // - Stock with K transactions (LC 188): add transaction count dimension

    // ==========================================
    // TEST
    // ==========================================
    public static void main(String[] args) {
        BuyAndSellStockII sol = new BuyAndSellStockII();

        assert sol.maxProfit(new int[]{7, 1, 5, 3, 6, 4}) == 7 : "Test 1";
        System.out.println("Test 1 passed: [7,1,5,3,6,4] → 7");

        assert sol.maxProfit(new int[]{1, 2, 3, 4, 5}) == 4 : "Test 2";
        System.out.println("Test 2 passed: [1,2,3,4,5] → 4");

        assert sol.maxProfit(new int[]{7, 6, 4, 3, 1}) == 0 : "Test 3";
        System.out.println("Test 3 passed: [7,6,4,3,1] → 0");

        assert sol.maxProfit(new int[]{1}) == 0 : "Test 4";
        System.out.println("Test 4 passed: [1] → 0");

        assert sol.maxProfit(new int[]{1, 2}) == 1 : "Test 5";
        System.out.println("Test 5 passed: [1,2] → 1");

        // Verify greedy and DP give same results
        int[] test = {7, 1, 5, 3, 6, 4};
        assert sol.maxProfit(test) == sol.maxProfit_dp(test) : "Approaches differ!";
        System.out.println("Test 6 passed: greedy and DP agree");

        int[] test2 = {3, 3, 3, 3};
        assert sol.maxProfit(test2) == 0 : "Test 7";
        System.out.println("Test 7 passed: [3,3,3,3] → 0 (flat prices)");

        System.out.println("\n✓ All tests passed!");
    }
}
