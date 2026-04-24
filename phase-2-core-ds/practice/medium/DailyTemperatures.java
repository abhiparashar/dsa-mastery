import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 739 - Daily Temperatures ⭐⭐ (Monotonic Stack)
 *
 * Given daily temperatures, for each day find how many days you have to wait
 * for a warmer temperature. Return 0 if no warmer day exists.
 *
 * Example: [73,74,75,71,69,72,76,73] → [1,1,4,2,1,1,0,0]
 *
 * WHY MONOTONIC STACK:
 * For each temperature, we want the NEXT GREATER element.
 * Brute force: for each element, scan right → O(n²).
 * Monotonic stack: maintain decreasing stack of indices.
 * When a new temp is higher than stack top, that stack element found its answer.
 *
 * O(n) because each element is pushed and popped at most once.
 */
public class DailyTemperatures {

    public int[] dailyTemperatures_yours(int[] temperatures) {
        // TODO: monotonic decreasing stack (stores indices)
        return new int[temperatures.length];
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // stores INDICES

        for (int i = 0; i < n; i++) {
            // Pop all elements smaller than current temperature
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prev = stack.pop();
                answer[prev] = i - prev; // days to wait
            }
            stack.push(i);
        }
        // Elements remaining in stack have no warmer day → answer stays 0
        return answer;
    }

    public static void main(String[] args) {
        DailyTemperatures sol = new DailyTemperatures();

        assert Arrays.equals(
            sol.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73}),
            new int[]{1, 1, 4, 2, 1, 1, 0, 0}
        );
        System.out.println("Test 1 passed: [73,74,75,71,69,72,76,73] → [1,1,4,2,1,1,0,0]");

        assert Arrays.equals(
            sol.dailyTemperatures(new int[]{30, 40, 50, 60}),
            new int[]{1, 1, 1, 0}
        );
        System.out.println("Test 2 passed: increasing → [1,1,1,0]");

        assert Arrays.equals(
            sol.dailyTemperatures(new int[]{60, 50, 40, 30}),
            new int[]{0, 0, 0, 0}
        );
        System.out.println("Test 3 passed: decreasing → [0,0,0,0]");

        assert Arrays.equals(
            sol.dailyTemperatures(new int[]{50}),
            new int[]{0}
        );
        System.out.println("Test 4 passed: single element → [0]");

        System.out.println("\n✓ All tests passed!");
    }
}
