/**
 * LeetCode 53 - Maximum Subarray ⭐⭐ (Kadane's Algorithm)
 *
 * Find the contiguous subarray with the largest sum.
 *
 * Example 1: [-2,1,-3,4,-1,2,1,-5,4] → 6 (subarray [4,-1,2,1])
 * Example 2: [1]                       → 1
 * Example 3: [5,4,-1,7,8]             → 23 (entire array)
 *
 * BEFORE YOU CODE — the decision framework:
 *
 * At each position i, you extend the previous subarray OR restart.
 *
 * WHY?
 * If the best subarray ending at i-1 has a negative sum, carrying it forward
 * can only hurt. Starting fresh with nums[i] alone is better.
 *
 * If the best subarray ending at i-1 has a non-negative sum, it can only help
 * (or at worst, not hurt). So extend it.
 *
 * RECURRENCE:
 *   dp[i] = max(nums[i], dp[i-1] + nums[i])
 *                 ↑              ↑
 *            restart here    extend previous
 *
 *   answer = max(dp[0], dp[1], ..., dp[n-1])
 *
 * Since dp[i] only depends on dp[i-1], we replace the array with one variable.
 *
 * EDGE CASE TRAP:
 * What if ALL numbers are negative? e.g., [-5, -3, -1, -8]
 * Kadane's correctly returns -1 (the single element [-1]).
 * The subarray must be non-empty, so we can't return 0.
 * This is why we initialize currentMax = nums[0], NOT 0.
 */
public class MaximumSubarray {

    // ==========================================
    // YOUR SOLUTION — write here first
    // ==========================================
    public int maxSubArray_yours(int[] nums) {
        // TODO: implement Kadane's algorithm yourself
        // Step 1: Initialize currentMax and globalMax to nums[0]
        // Step 2: Loop from index 1
        // Step 3: At each step, decide: extend or restart?
        // Step 4: Update globalMax

        return 0;
    }

    // ==========================================
    // BRUTE FORCE — O(n²) time, O(1) space
    // ==========================================
    public int maxSubArray_brute(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    // ==========================================
    // KADANE'S ALGORITHM — O(n) time, O(1) space
    // ==========================================
    public int maxSubArray(int[] nums) {
        int currentMax = nums[0]; // best subarray sum ENDING at current position
        int globalMax = nums[0];  // best subarray sum ANYWHERE

        for (int i = 1; i < nums.length; i++) {
            // THE key decision: extend the previous subarray, or start fresh?
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            globalMax = Math.max(globalMax, currentMax);
        }

        return globalMax;
    }

    // ==========================================
    // VARIATION: Return the actual subarray (not just the sum)
    // ==========================================
    public int[] maxSubArrayIndices(int[] nums) {
        int currentMax = nums[0];
        int globalMax = nums[0];
        int start = 0, end = 0, tempStart = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > currentMax + nums[i]) {
                currentMax = nums[i];
                tempStart = i; // restart: new subarray starts here
            } else {
                currentMax = currentMax + nums[i]; // extend
            }

            if (currentMax > globalMax) {
                globalMax = currentMax;
                start = tempStart;
                end = i;
            }
        }

        return new int[]{start, end, globalMax};
    }

    // ==========================================
    // TEST
    // ==========================================
    public static void main(String[] args) {
        MaximumSubarray sol = new MaximumSubarray();

        // Test 1: Mixed positive and negative
        assert sol.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}) == 6 : "Test 1";
        System.out.println("Test 1 passed: [-2,1,-3,4,-1,2,1,-5,4] → 6");

        // Test 2: Single element
        assert sol.maxSubArray(new int[]{1}) == 1 : "Test 2";
        System.out.println("Test 2 passed: [1] → 1");

        // Test 3: All positive (entire array)
        assert sol.maxSubArray(new int[]{5, 4, -1, 7, 8}) == 23 : "Test 3";
        System.out.println("Test 3 passed: [5,4,-1,7,8] → 23");

        // Test 4: All negative (pick least negative)
        assert sol.maxSubArray(new int[]{-5, -3, -1, -8}) == -1 : "Test 4";
        System.out.println("Test 4 passed: [-5,-3,-1,-8] → -1");

        // Test 5: Single negative
        assert sol.maxSubArray(new int[]{-1}) == -1 : "Test 5";
        System.out.println("Test 5 passed: [-1] → -1");

        // Test 6: Zero in array
        assert sol.maxSubArray(new int[]{-2, 0, -1}) == 0 : "Test 6";
        System.out.println("Test 6 passed: [-2,0,-1] → 0");

        // Test 7: Subarray indices variation
        int[] result = sol.maxSubArrayIndices(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        System.out.println("Test 7: subarray indices [" + result[0] + "," + result[1]
                + "], sum=" + result[2]); // [3, 6], sum=6

        System.out.println("\n✓ All tests passed!");
    }
}
