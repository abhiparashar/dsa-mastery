import java.util.*;

/**
 * LeetCode 78 - Subsets ⭐⭐
 *
 * Given a set of DISTINCT integers, return all possible subsets.
 *
 * Example: [1,2,3] → [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]
 *
 * DECISION AT EACH STEP: for each remaining element, include or skip it.
 * The start parameter ensures we only look forward (avoid duplicates).
 * Every path is a valid subset — no base case needed to collect.
 */
public class Subsets {

    public List<List<Integer>> subsets_yours(int[] nums) {
        // TODO: backtrack with start index
        return new ArrayList<>();
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, int start) {
        result.add(new ArrayList<>(path)); // every path is a valid subset

        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtrack(result, path, nums, i + 1); // i+1: don't reuse
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        Subsets sol = new Subsets();

        List<List<Integer>> result = sol.subsets(new int[]{1, 2, 3});
        System.out.println("Subsets of [1,2,3]: " + result);
        assert result.size() == 8 : "Expected 8 subsets, got " + result.size();

        List<List<Integer>> result2 = sol.subsets(new int[]{0});
        System.out.println("Subsets of [0]: " + result2);
        assert result2.size() == 2;

        System.out.println("\n✓ All tests passed!");
    }
}
