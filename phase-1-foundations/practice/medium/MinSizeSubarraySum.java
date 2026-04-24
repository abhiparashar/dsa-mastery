/**
 * LeetCode 209 - Minimum Size Subarray Sum ⭐
 *
 * Find the minimum length subarray with sum >= target.
 * All numbers are POSITIVE. Return 0 if no such subarray.
 *
 * Example 1: target=7, nums=[2,3,1,2,4,3] → 2 (subarray [4,3])
 * Example 2: target=4, nums=[1,4,4]        → 1 (subarray [4])
 * Example 3: target=11, nums=[1,1,1,1,1]   → 0 (impossible)
 *
 * WHY SLIDING WINDOW WORKS:
 * All numbers are positive → adding to window ALWAYS increases sum,
 * removing ALWAYS decreases sum. This monotonicity means:
 * - Expand right until sum ≥ target (window becomes valid)
 * - Shrink left to find minimum length while still valid
 * - No element is skipped, no element is revisited more than twice
 *   (once when right passes it, once when left passes it)
 *
 * Time: O(n) — each element enters and leaves window at most once
 */
public class MinSizeSubarraySum {

    // YOUR SOLUTION
    public int minSubArrayLen_yours(int target, int[] nums) {
        // TODO: variable-size sliding window
        // Expand right, shrink left while sum >= target
        return 0;
    }

    // BRUTE FORCE — O(n²)
    public int minSubArrayLen_brute(int target, int[] nums) {
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum >= target) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // no point extending further
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // OPTIMAL — O(n) sliding window
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0, sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right]; // expand

            while (sum >= target) { // valid! try to shrink
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        MinSizeSubarraySum sol = new MinSizeSubarraySum();

        assert sol.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}) == 2;
        System.out.println("Test 1 passed: target=7, [2,3,1,2,4,3] → 2");

        assert sol.minSubArrayLen(4, new int[]{1, 4, 4}) == 1;
        System.out.println("Test 2 passed: target=4, [1,4,4] → 1");

        assert sol.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}) == 0;
        System.out.println("Test 3 passed: target=11, [1,1,1,...] → 0");

        assert sol.minSubArrayLen(15, new int[]{5, 1, 3, 5, 10, 7, 4, 9, 2, 8}) == 2;
        System.out.println("Test 4 passed: target=15, mixed → 2");

        assert sol.minSubArrayLen(3, new int[]{3}) == 1;
        System.out.println("Test 5 passed: single element equals target → 1");

        assert sol.minSubArrayLen(100, new int[]{1, 2, 3}) == 0;
        System.out.println("Test 6 passed: impossible → 0");

        System.out.println("\n✓ All tests passed!");
    }
}
