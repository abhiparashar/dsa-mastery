import java.util.*;

/**
 * LeetCode 39 - Combination Sum ⭐⭐
 *
 * Find all unique combinations where candidates sum to target.
 * Same number CAN be used unlimited times. All numbers are positive.
 *
 * Example: candidates=[2,3,6,7], target=7 → [[2,2,3],[7]]
 *
 * KEY DIFFERENCE FROM SUBSETS:
 * - Can reuse elements → recurse with i (not i+1)
 * - Stop when sum exceeds target (pruning)
 */
public class CombinationSum {

    public List<List<Integer>> combinationSum_yours(int[] candidates, int target) {
        // TODO: backtrack with start=i (reuse allowed), track remaining sum
        return new ArrayList<>();
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // sort for early termination
        backtrack(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    void backtrack(List<List<Integer>> result, List<Integer> path,
                   int[] candidates, int remaining, int start) {
        if (remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) break; // prune (since sorted)
            path.add(candidates[i]);
            backtrack(result, path, candidates, remaining - candidates[i], i); // i, not i+1!
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSum sol = new CombinationSum();

        System.out.println(sol.combinationSum(new int[]{2, 3, 6, 7}, 7));
        // [[2,2,3], [7]]

        System.out.println(sol.combinationSum(new int[]{2, 3, 5}, 8));
        // [[2,2,2,2], [2,3,3], [3,5]]

        System.out.println(sol.combinationSum(new int[]{2}, 1));
        // [] (impossible)

        System.out.println("\n✓ Manual verification complete");
    }
}
