import java.util.*;

/**
 * LeetCode 300 - Longest Increasing Subsequence ⭐⭐
 *
 * Find the length of the longest strictly increasing subsequence.
 *
 * APPROACH 1 — O(n²) DP:
 * dp[i] = length of LIS ending at index i
 * For each j < i, if nums[j] < nums[i], dp[i] = max(dp[i], dp[j] + 1)
 *
 * APPROACH 2 — O(n log n) Patience Sorting:
 * Maintain a "tails" array where tails[i] = smallest tail element for LIS of length i+1.
 * For each num, binary search for its position in tails.
 * - If num > all tails: extend LIS (append)
 * - Else: replace the first tail ≥ num (keep smallest possible tail)
 *
 * WHY PATIENCE SORTING WORKS:
 * tails is always sorted. We maintain the POTENTIAL for longest subsequences
 * by keeping the smallest possible values at each length.
 */
public class LongestIncreasingSubsequence {

    public int lengthOfLIS_yours(int[] nums) {
        // TODO
        return 0;
    }

    // O(n²) DP — intuitive
    public int lengthOfLIS_dp(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    // O(n log n) Patience Sorting — optimal
    public int lengthOfLIS(int[] nums) {
        List<Integer> tails = new ArrayList<>();
        for (int num : nums) {
            int pos = Collections.binarySearch(tails, num);
            if (pos < 0) pos = -(pos + 1);
            if (pos == tails.size()) tails.add(num);
            else tails.set(pos, num);
        }
        return tails.size();
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequence sol = new LongestIncreasingSubsequence();

        assert sol.lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}) == 4;
        System.out.println("Test 1: [10,9,2,5,3,7,101,18] → 4 (2,3,7,101)");

        assert sol.lengthOfLIS(new int[]{0,1,0,3,2,3}) == 4;
        System.out.println("Test 2: [0,1,0,3,2,3] → 4");

        assert sol.lengthOfLIS(new int[]{7,7,7,7,7}) == 1;
        System.out.println("Test 3: all same → 1 (strictly increasing)");

        assert sol.lengthOfLIS(new int[]{1,2,3,4,5}) == 5;
        System.out.println("Test 4: already sorted → 5");

        assert sol.lengthOfLIS(new int[]{5,4,3,2,1}) == 1;
        System.out.println("Test 5: decreasing → 1");

        System.out.println("\n✓ All tests passed!");
    }
}
