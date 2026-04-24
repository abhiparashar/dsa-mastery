/**
 * LeetCode 167 - Two Sum II (Input Array is Sorted)
 *
 * Given a 1-INDEXED sorted array, find two numbers that add up to target.
 * Return the indices (1-indexed).
 *
 * Example: numbers = [2,7,11,15], target = 9 → [1,2]
 *
 * WHY THIS PROBLEM MATTERS:
 * This teaches you the Two Pointer technique — the most space-efficient
 * approach when input is sorted. You CANNOT use this on unsorted arrays.
 *
 * FIRST PRINCIPLES THINKING:
 * - Array is sorted. What does sorting give us?
 * - If sum is too small → we need a bigger number → move left pointer right
 * - If sum is too big → we need a smaller number → move right pointer left
 * - This works because sorted order guarantees monotonic behavior
 *
 * CAN WE USE THE HASHMAP APPROACH FROM TWO SUM I?
 * Yes! It still works in O(n) time, O(n) space.
 * But sorted input gives us a BETTER approach: O(n) time, O(1) space.
 * Always exploit the structure you're given.
 */
public class TwoSumII {

    // YOUR SOLUTION
    public int[] twoSum_yours(int[] numbers, int target) {
        // TODO
        return new int[]{};
    }

    // TWO POINTERS — O(n) time, O(1) space — THE BEST APPROACH
    public int[] twoSum(int[] numbers, int target) {
        int lo = 0, hi = numbers.length - 1;

        while (lo < hi) {
            int sum = numbers[lo] + numbers[hi];

            if (sum == target) {
                return new int[]{lo + 1, hi + 1}; // 1-indexed
            } else if (sum < target) {
                lo++; // need bigger sum → move left pointer right
            } else {
                hi--; // need smaller sum → move right pointer left
            }
        }

        return new int[]{}; // never reached per problem guarantee
    }

    // PROOF OF CORRECTNESS (understand this, don't just memorize):
    //
    // Claim: If answer is (i, j) where i < j, we will find it.
    //
    // Initially lo=0, hi=n-1. The answer (i, j) is somewhere in [lo, hi].
    //
    // Case 1: sum < target → nums[lo] + nums[hi] < target
    //   Can (lo, anything) be the answer? No, because nums[hi] is the largest,
    //   and even nums[lo] + nums[hi] < target. So lo can't be part of the answer.
    //   Safe to advance lo.
    //
    // Case 2: sum > target → similar argument, safe to decrease hi.
    //
    // We never skip the answer. QED.

    public static void main(String[] args) {
        TwoSumII sol = new TwoSumII();

        int[] r1 = sol.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println("[" + r1[0] + "," + r1[1] + "]"); // [1,2]

        int[] r2 = sol.twoSum(new int[]{2, 3, 4}, 6);
        System.out.println("[" + r2[0] + "," + r2[1] + "]"); // [1,3]

        int[] r3 = sol.twoSum(new int[]{-1, 0}, -1);
        System.out.println("[" + r3[0] + "," + r3[1] + "]"); // [1,2]

        System.out.println("\n✓ Manual verification complete");
    }
}
