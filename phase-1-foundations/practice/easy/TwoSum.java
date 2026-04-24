import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 1 - Two Sum
 *
 * TRY TO SOLVE THIS YOURSELF FIRST.
 * Spend at least 15 minutes before looking at the solutions below.
 *
 * Steps:
 * 1. Understand the problem (what are the inputs? outputs? constraints?)
 * 2. Write brute force
 * 3. Identify the bottleneck (what's the inner loop doing?)
 * 4. Optimize with the right data structure
 * 5. Handle edge cases
 */
public class TwoSum {

    // ==========================================
    // YOUR SOLUTION — write here first, then compare
    // ==========================================
    public int[] twoSum_yours(int[] nums, int target) {
        // TODO: Solve it yourself before scrolling down

        return new int[]{};
    }

    // ==========================================
    // BRUTE FORCE — O(n²) time, O(1) space
    // ==========================================
    public int[] twoSum_brute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    // ==========================================
    // OPTIMAL — O(n) time, O(n) space
    // ==========================================
    public int[] twoSum_optimal(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }

            seen.put(nums[i], i);
        }

        return new int[]{};
    }

    // ==========================================
    // TEST — run this to verify your solution
    // ==========================================
    public static void main(String[] args) {
        TwoSum solution = new TwoSum();

        // Test 1: Basic case
        int[] result1 = solution.twoSum_optimal(new int[]{2, 7, 11, 15}, 9);
        assert result1[0] == 0 && result1[1] == 1 : "Test 1 failed";
        System.out.println("Test 1 passed: [2,7,11,15] target=9 → [" + result1[0] + "," + result1[1] + "]");

        // Test 2: Answer at the end
        int[] result2 = solution.twoSum_optimal(new int[]{1, 2, 3, 4}, 7);
        assert result2[0] == 2 && result2[1] == 3 : "Test 2 failed";
        System.out.println("Test 2 passed: [1,2,3,4] target=7 → [" + result2[0] + "," + result2[1] + "]");

        // Test 3: Negative numbers
        int[] result3 = solution.twoSum_optimal(new int[]{-3, 4, 3, 90}, 0);
        assert result3[0] == 0 && result3[1] == 2 : "Test 3 failed";
        System.out.println("Test 3 passed: [-3,4,3,90] target=0 → [" + result3[0] + "," + result3[1] + "]");

        // Test 4: Duplicates
        int[] result4 = solution.twoSum_optimal(new int[]{3, 3}, 6);
        assert result4[0] == 0 && result4[1] == 1 : "Test 4 failed";
        System.out.println("Test 4 passed: [3,3] target=6 → [" + result4[0] + "," + result4[1] + "]");

        // Test 5: Large negative target
        int[] result5 = solution.twoSum_optimal(new int[]{-10, -20, -30, 5}, -50);
        assert result5[0] == 1 && result5[1] == 2 : "Test 5 failed";
        System.out.println("Test 5 passed: [-10,-20,-30,5] target=-50 → [" + result5[0] + "," + result5[1] + "]");

        System.out.println("\n✓ All tests passed!");
    }
}
