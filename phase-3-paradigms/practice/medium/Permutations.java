import java.util.*;

/**
 * LeetCode 46 - Permutations ⭐⭐
 *
 * Given DISTINCT integers, return all permutations.
 *
 * Example: [1,2,3] → [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * DIFFERENCE FROM SUBSETS:
 * - Subsets: order doesn't matter, use start index
 * - Permutations: order matters, use boolean[] used array
 *
 * Base case: path.size() == nums.length (used all elements)
 */
public class Permutations {

    public List<List<Integer>> permute_yours(int[] nums) {
        // TODO: used array, loop from 0 each time
        return new ArrayList<>();
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, new boolean[nums.length]);
        return result;
    }

    void backtrack(List<List<Integer>> result, List<Integer> path,
                   int[] nums, boolean[] used) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            path.add(nums[i]);
            backtrack(result, path, nums, used);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        Permutations sol = new Permutations();

        List<List<Integer>> result = sol.permute(new int[]{1, 2, 3});
        System.out.println("Permutations of [1,2,3]: " + result);
        assert result.size() == 6 : "Expected 6, got " + result.size();

        List<List<Integer>> result2 = sol.permute(new int[]{1});
        assert result2.size() == 1;

        System.out.println("\n✓ All tests passed!");
    }
}
