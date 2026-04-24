import java.util.Arrays;

/**
 * LeetCode 238 - Product of Array Except Self ⭐⭐ (Meta, Amazon, Google)
 *
 * Given integer array nums, return an array where result[i] = product
 * of all elements EXCEPT nums[i]. You CANNOT use division.
 *
 * Example: [1,2,3,4] → [24,12,8,6]
 * Example: [-1,1,0,-3,3] → [0,0,9,0,0]
 *
 * THINK BEFORE CODING:
 * 1. With division: total product / nums[i]. But division by zero!
 * 2. Without division: result[i] = leftProduct[i] × rightProduct[i]
 *    - leftProduct[i]  = nums[0] × ... × nums[i-1]
 *    - rightProduct[i] = nums[i+1] × ... × nums[n-1]
 * 3. Can we do it in O(1) extra space? YES. Use the result array
 *    for left products, then multiply right products in a second pass.
 *
 * FOLLOW-UP: What if there are zeros?
 * - Zero zeros: normal
 * - One zero: only that position has non-zero product
 * - Two+ zeros: everything is zero
 * Our approach handles all cases naturally!
 */
public class ProductExceptSelf {

    // YOUR SOLUTION
    public int[] productExceptSelf_yours(int[] nums) {
        // TODO: two-pass approach
        // Pass 1: result[i] = product of everything to the LEFT
        // Pass 2: multiply each result[i] by product of everything to the RIGHT
        return new int[nums.length];
    }

    // OPTIMAL — O(n) time, O(1) extra space
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // Pass 1: left products
        // result[i] = product of nums[0..i-1]
        result[0] = 1; // nothing to the left of index 0
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // Pass 2: multiply by right products
        // rightProduct tracks product of nums[i+1..n-1] as we scan right to left
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i];
        }

        return result;
    }

    public static void main(String[] args) {
        ProductExceptSelf sol = new ProductExceptSelf();

        assert Arrays.equals(sol.productExceptSelf(new int[]{1, 2, 3, 4}), new int[]{24, 12, 8, 6});
        System.out.println("Test 1 passed: [1,2,3,4] → [24,12,8,6]");

        assert Arrays.equals(sol.productExceptSelf(new int[]{-1, 1, 0, -3, 3}), new int[]{0, 0, 9, 0, 0});
        System.out.println("Test 2 passed: [-1,1,0,-3,3] → [0,0,9,0,0]");

        assert Arrays.equals(sol.productExceptSelf(new int[]{2, 3}), new int[]{3, 2});
        System.out.println("Test 3 passed: [2,3] → [3,2]");

        assert Arrays.equals(sol.productExceptSelf(new int[]{0, 0}), new int[]{0, 0});
        System.out.println("Test 4 passed: [0,0] → [0,0] (two zeros)");

        assert Arrays.equals(sol.productExceptSelf(new int[]{1, 0}), new int[]{0, 1});
        System.out.println("Test 5 passed: [1,0] → [0,1] (one zero)");

        System.out.println("\n✓ All tests passed!");
    }
}
