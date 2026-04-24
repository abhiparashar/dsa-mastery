/**
 * LeetCode 152 - Maximum Product Subarray ⭐
 *
 * Find the contiguous subarray with the largest product.
 *
 * Example 1: [2,3,-2,4]   → 6  (subarray [2,3])
 * Example 2: [-2,0,-1]    → 0  (subarray [0])
 * Example 3: [-2,3,-4]    → 24 (entire array: -2 × 3 × -4)
 *
 * WHY THIS IS HARDER THAN KADANE'S:
 *
 * In Kadane's (sum), a negative running total is always bad → restart.
 * In products, a negative running product might become AMAZING when
 * multiplied by another negative number.
 *
 * Example: [-3, 2, -4]
 *   At index 1: maxProd = 2, minProd = -6
 *   At index 2: -6 × -4 = 24 ← the MIN became the MAX!
 *
 * SOLUTION: Track BOTH maxProd and minProd at each position.
 *
 * When we see a negative number:
 *   - maxProd × negative → small (or very negative)
 *   - minProd × negative → potentially huge!
 * So we swap max and min before multiplying.
 *
 * SPECIAL CASE — ZERO:
 * Zero kills any running product. Both max and min reset to 0.
 * But that's handled naturally: max(0, 0*anything) = 0 → restart.
 */
public class MaxProductSubarray {

    // ==========================================
    // YOUR SOLUTION
    // ==========================================
    public int maxProduct_yours(int[] nums) {
        // TODO: Track maxProd and minProd
        // Remember: when nums[i] is negative, swap them before computing

        return 0;
    }

    // ==========================================
    // BRUTE FORCE — O(n²)
    // ==========================================
    public int maxProduct_brute(int[] nums) {
        int result = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int product = 1;
            for (int j = i; j < nums.length; j++) {
                product *= nums[j];
                result = Math.max(result, product);
            }
        }
        return result;
    }

    // ==========================================
    // OPTIMAL — O(n) time, O(1) space
    // ==========================================
    public int maxProduct(int[] nums) {
        int maxProd = nums[0]; // max product of subarray ending here
        int minProd = nums[0]; // min product of subarray ending here (might flip!)
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Negative number flips max and min
            if (nums[i] < 0) {
                int temp = maxProd;
                maxProd = minProd;
                minProd = temp;
            }

            // Same Kadane logic: extend or restart
            maxProd = Math.max(nums[i], maxProd * nums[i]);
            minProd = Math.min(nums[i], minProd * nums[i]);

            result = Math.max(result, maxProd);
        }

        return result;
    }

    // ALTERNATIVE APPROACH (some people find this clearer):
    // Track max and min using all three candidates explicitly
    public int maxProduct_v2(int[] nums) {
        int result = nums[0];
        int currMax = nums[0], currMin = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // All three candidates for new max and min:
            int a = nums[i];
            int b = currMax * nums[i];
            int c = currMin * nums[i];

            currMax = Math.max(a, Math.max(b, c));
            currMin = Math.min(a, Math.min(b, c));

            result = Math.max(result, currMax);
        }

        return result;
    }

    // ==========================================
    // TEST
    // ==========================================
    public static void main(String[] args) {
        MaxProductSubarray sol = new MaxProductSubarray();

        assert sol.maxProduct(new int[]{2, 3, -2, 4}) == 6 : "Test 1";
        System.out.println("Test 1 passed: [2,3,-2,4] → 6");

        assert sol.maxProduct(new int[]{-2, 0, -1}) == 0 : "Test 2";
        System.out.println("Test 2 passed: [-2,0,-1] → 0");

        assert sol.maxProduct(new int[]{-2, 3, -4}) == 24 : "Test 3";
        System.out.println("Test 3 passed: [-2,3,-4] → 24 (negative × negative!)");

        assert sol.maxProduct(new int[]{-2}) == -2 : "Test 4";
        System.out.println("Test 4 passed: [-2] → -2 (single negative)");

        assert sol.maxProduct(new int[]{0, 2}) == 2 : "Test 5";
        System.out.println("Test 5 passed: [0,2] → 2 (zero resets)");

        assert sol.maxProduct(new int[]{-4, -3, -2}) == 12 : "Test 6";
        System.out.println("Test 6 passed: [-4,-3,-2] → 12 (two negatives)");

        assert sol.maxProduct(new int[]{2, -5, -2, -4, 3}) == 24 : "Test 7";
        System.out.println("Test 7 passed: [2,-5,-2,-4,3] → 24");

        // Verify both approaches give same results
        int[] testArr = {-1, -2, -9, -6};
        assert sol.maxProduct(testArr) == sol.maxProduct_v2(testArr) : "Approaches differ!";
        System.out.println("Test 8 passed: both approaches agree on [-1,-2,-9,-6] → "
                + sol.maxProduct(testArr));

        System.out.println("\n✓ All tests passed!");
    }
}
