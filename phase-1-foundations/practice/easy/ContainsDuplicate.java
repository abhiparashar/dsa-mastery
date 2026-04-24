import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 217 - Contains Duplicate
 *
 * Given an integer array nums, return true if any value appears
 * at least twice in the array, and return false if every element is distinct.
 *
 * Example 1: nums = [1,2,3,1]       → true
 * Example 2: nums = [1,2,3,4]       → false
 * Example 3: nums = [1,1,1,3,3,4,3] → true
 *
 * Constraints: 1 <= nums.length <= 10^5
 *
 * THINK BEFORE CODING:
 * - What's the brute force? (check every pair — O(n²))
 * - What data structure gives O(1) existence checks? (HashSet)
 * - Could sorting help? (Yes: O(n log n), check adjacent elements)
 * - Which approach is better for an interview?
 */
public class ContainsDuplicate {

    // YOUR SOLUTION
    public boolean containsDuplicate_yours(int[] nums) {
        // TODO: solve it yourself
        return false;
    }

    // BRUTE FORCE — O(n²) time, O(1) space
    public boolean containsDuplicate_brute(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) return true;
            }
        }
        return false;
    }

    // OPTIMAL — O(n) time, O(n) space
    public boolean containsDuplicate_optimal(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) return true; // add() returns false if already present
        }
        return false;
    }

    // SORTING APPROACH — O(n log n) time, O(1) space (if in-place sort allowed)
    public boolean containsDuplicate_sort(int[] nums) {
        java.util.Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ContainsDuplicate sol = new ContainsDuplicate();

        System.out.println(sol.containsDuplicate_optimal(new int[]{1, 2, 3, 1}));       // true
        System.out.println(sol.containsDuplicate_optimal(new int[]{1, 2, 3, 4}));       // false
        System.out.println(sol.containsDuplicate_optimal(new int[]{1, 1, 1, 3, 3, 4})); // true
        System.out.println(sol.containsDuplicate_optimal(new int[]{1}));                 // false (single element)
        System.out.println(sol.containsDuplicate_optimal(new int[]{}));                  // false (empty — edge case)

        System.out.println("\n✓ Manual verification complete");
    }
}
