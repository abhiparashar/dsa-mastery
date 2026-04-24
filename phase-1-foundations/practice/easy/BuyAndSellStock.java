/**
 * LeetCode 121 - Best Time to Buy and Sell Stock ⭐⭐
 *
 * Given prices[i] = price of stock on day i.
 * Find the maximum profit from ONE buy + ONE sell (buy before sell).
 * If no profit possible, return 0.
 *
 * Example 1: [7,1,5,3,6,4] → 5 (buy at 1, sell at 6)
 * Example 2: [7,6,4,3,1]   → 0 (prices only fall)
 *
 * BEFORE YOU CODE — ask yourself:
 * 1. What's the brute force? Check all (buy, sell) pairs → O(n²)
 * 2. What's the bottleneck? For each sell day, we search for the best buy day.
 * 3. What information do I need from the past? Just the minimum price seen so far.
 * 4. Can I track that with a single variable? YES.
 *
 * STATE TO TRACK:
 *   minPrice — the cheapest price we could have bought at (scanning left to right)
 *   maxProfit — the best profit achievable so far
 */
public class BuyAndSellStock {

    // ==========================================
    // YOUR SOLUTION — write here first
    // ==========================================
    public int maxProfit_yours(int[] prices) {
        // TODO: solve it yourself
        // Hint: track minPrice and maxProfit as you scan

        return 0;
    }

    // ==========================================
    // BRUTE FORCE — O(n²) time, O(1) space
    // ==========================================
    public int maxProfit_brute(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
            }
        }
        return maxProfit;
    }

    // ==========================================
    // OPTIMAL — O(n) time, O(1) space
    // ==========================================
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }

        return maxProfit;
    }

    // WHY IT WORKS:
    // For each day (potential sell day), the best buy day is the cheapest
    // price in all previous days. We track that with minPrice.
    // We compute the profit (price - minPrice) and track the global best.
    //
    // We never "sell before we buy" because minPrice is only updated
    // with prices we've already passed. And we allow buy and sell on
    // the same day (profit = 0), which is handled correctly.

    // ==========================================
    // TEST
    // ==========================================
    public static void main(String[] args) {
        BuyAndSellStock sol = new BuyAndSellStock();

        // Test 1: Normal case
        assert sol.maxProfit(new int[]{7, 1, 5, 3, 6, 4}) == 5 : "Test 1 failed";
        System.out.println("Test 1 passed: [7,1,5,3,6,4] → 5");

        // Test 2: Decreasing prices
        assert sol.maxProfit(new int[]{7, 6, 4, 3, 1}) == 0 : "Test 2 failed";
        System.out.println("Test 2 passed: [7,6,4,3,1] → 0");

        // Test 3: Buy and sell same day
        assert sol.maxProfit(new int[]{5}) == 0 : "Test 3 failed";
        System.out.println("Test 3 passed: [5] → 0");

        // Test 4: Two elements, profit possible
        assert sol.maxProfit(new int[]{1, 2}) == 1 : "Test 4 failed";
        System.out.println("Test 4 passed: [1,2] → 1");

        // Test 5: Two elements, no profit
        assert sol.maxProfit(new int[]{2, 1}) == 0 : "Test 5 failed";
        System.out.println("Test 5 passed: [2,1] → 0");

        // Test 6: Minimum at the very end
        assert sol.maxProfit(new int[]{3, 8, 1}) == 5 : "Test 6 failed";
        System.out.println("Test 6 passed: [3,8,1] → 5");

        // Test 7: Large jump at the end
        assert sol.maxProfit(new int[]{2, 1, 4}) == 3 : "Test 7 failed";
        System.out.println("Test 7 passed: [2,1,4] → 3");

        System.out.println("\n✓ All tests passed!");
    }
}
