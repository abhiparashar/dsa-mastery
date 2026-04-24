import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 560 - Subarray Sum Equals K ⭐⭐ (Meta, Google, Amazon top question)
 *
 * Given an array of integers and an integer k,
 * return the TOTAL NUMBER of subarrays whose sum equals k.
 *
 * Example 1: nums = [1,1,1], k = 2      → 2  (subarrays [1,1] at positions 0-1 and 1-2)
 * Example 2: nums = [1,2,3], k = 3      → 2  (subarrays [1,2] and [3])
 *
 * Constraints: -1000 <= nums[i] <= 1000 (NEGATIVE NUMBERS EXIST!)
 *
 * =====================================================================
 * THIS IS THE MOST IMPORTANT PROBLEM IN THIS LESSON.
 * It combines Two Sum + Prefix Sum. If you understand this,
 * you understand the deepest pattern in array problems.
 * =====================================================================
 *
 * THINK BEFORE CODING (spend 30 minutes. seriously.):
 *
 * ATTEMPT 1 — Brute Force:
 * Check all subarrays. For each start i, for each end j, compute sum.
 * O(n³) — too slow. Can optimize to O(n²) with running sum.
 *
 * ATTEMPT 2 — Can we use sliding window?
 * NO! Sliding window only works when all numbers are positive.
 * Why? Because with negatives, expanding the window can decrease the sum,
 * so we can't decide when to shrink.
 * Example: nums = [1, -1, 1], k = 1
 * Sliding window would miss subarrays like [1, -1, 1].
 *
 * ATTEMPT 3 — Prefix Sum + HashMap (THE INSIGHT):
 * prefix[j] = nums[0] + nums[1] + ... + nums[j-1]
 * Sum of subarray (i, j] = prefix[j] - prefix[i]
 *
 * We want: prefix[j] - prefix[i] = k
 * Rearrange: prefix[i] = prefix[j] - k
 *
 * For each j, we need to count how many earlier prefix sums equal (prefix[j] - k).
 * THIS IS EXACTLY TWO SUM with prefix sums and counting instead of returning indices!
 */
public class SubarraySumEqualsK {

    // YOUR SOLUTION
    public int subarraySum_yours(int[] nums, int k) {
        // TODO: try it yourself for 30 minutes
        return 0;
    }

    // BRUTE FORCE — O(n²) time, O(1) space
    public int subarraySum_brute(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) count++;
            }
        }
        return count;
    }

    // OPTIMAL — O(n) time, O(n) space
    public int subarraySum(int[] nums, int k) {
        // This map stores: prefix_sum → how many times we've seen this prefix sum
        Map<Integer, Integer> prefixCount = new HashMap<>();

        // CRITICAL: Initialize with {0: 1}
        // Why? A prefix sum of 0 means "the empty subarray before index 0"
        // If prefix[j] itself equals k, then prefix[j] - k = 0, and we need
        // to count that as 1 valid subarray (from index 0 to j).
        prefixCount.put(0, 1);

        int sum = 0;
        int count = 0;

        for (int num : nums) {
            sum += num; // running prefix sum up to current index

            // How many earlier prefix sums equal (sum - k)?
            // Each one represents a subarray ending here that sums to k.
            count += prefixCount.getOrDefault(sum - k, 0);

            // Record this prefix sum
            prefixCount.merge(sum, 1, Integer::sum);
        }

        return count;
    }

    // DETAILED WALKTHROUGH:
    // nums = [1, 2, 3], k = 3
    //
    // prefixCount = {0: 1}
    //
    // i=0: sum = 1, need sum-k = 1-3 = -2, found 0 times → count=0
    //      prefixCount = {0:1, 1:1}
    //
    // i=1: sum = 3, need sum-k = 3-3 = 0, found 1 time → count=1
    //      (subarray [1,2] sums to 3 ✓)
    //      prefixCount = {0:1, 1:1, 3:1}
    //
    // i=2: sum = 6, need sum-k = 6-3 = 3, found 1 time → count=2
    //      (subarray [3] sums to 3 ✓ — from prefix 3 to prefix 6)
    //      prefixCount = {0:1, 1:1, 3:1, 6:1}
    //
    // Result: 2 ✓

    // WHY prefixCount.put(0, 1) IS CRITICAL:
    // Without it, we'd miss subarrays that start at index 0.
    //
    // Example: nums = [3], k = 3
    // sum = 3, need sum-k = 0
    // If {0:1} wasn't in the map, we'd find 0 instead of 1.
    // The subarray [3] itself sums to k, and it starts at index 0.

    public static void main(String[] args) {
        SubarraySumEqualsK sol = new SubarraySumEqualsK();

        System.out.println(sol.subarraySum(new int[]{1, 1, 1}, 2));     // 2
        System.out.println(sol.subarraySum(new int[]{1, 2, 3}, 3));     // 2
        System.out.println(sol.subarraySum(new int[]{1}, 0));            // 0
        System.out.println(sol.subarraySum(new int[]{0, 0, 0}, 0));     // 6 (all subarrays!)
        System.out.println(sol.subarraySum(new int[]{-1, -1, 1}, 0));   // 1 (only [-1,1] or [-1,-1,1]... actually: [-1,1] at 1-2 and... let me trace)

        // Trace for [-1, -1, 1], k = 0:
        // prefix = {0:1}
        // i=0: sum=-1, need -1-0=-1 → 0, count=0, prefix={0:1, -1:1}
        // i=1: sum=-2, need -2-0=-2 → 0, count=0, prefix={0:1, -1:1, -2:1}
        // i=2: sum=-1, need -1-0=-1 → 1 (seen -1 at i=0), count=1, prefix={0:1, -1:2, -2:1}
        // So answer = 1. The subarray [-1, 1] (indices 1-2) sums to 0. ✓

        System.out.println(sol.subarraySum(new int[]{3}, 3));            // 1

        System.out.println("\n✓ Manual verification complete");
    }
}
