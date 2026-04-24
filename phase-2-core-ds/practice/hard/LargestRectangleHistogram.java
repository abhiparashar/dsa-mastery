import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 84 - Largest Rectangle in Histogram ⭐⭐
 * THE classic monotonic stack problem. If you understand this, you understand the pattern.
 *
 * Given heights of bars in a histogram, find the largest rectangle.
 *
 * Example: [2,1,5,6,2,3] → 10 (rectangle of height 5, width 2 covering bars [5,6])
 *
 * APPROACH:
 * Maintain a stack of indices in INCREASING height order.
 * When a bar is shorter than the stack top:
 *   - The popped bar's rectangle is bounded by the current bar (right) and
 *     the new stack top (left).
 *   - width = i - stack.peek() - 1 (or i if stack is empty)
 *   - height = heights[popped]
 *
 * Add a sentinel height 0 at the end to flush remaining bars from the stack.
 */
public class LargestRectangleHistogram {

    public int largestRectangleArea_yours(int[] heights) {
        // TODO: monotonic increasing stack
        return 0;
    }

    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            int h = (i == heights.length) ? 0 : heights[i]; // sentinel

            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        LargestRectangleHistogram sol = new LargestRectangleHistogram();

        assert sol.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}) == 10;
        System.out.println("Test 1 passed: [2,1,5,6,2,3] → 10");

        assert sol.largestRectangleArea(new int[]{2, 4}) == 4;
        System.out.println("Test 2 passed: [2,4] → 4");

        assert sol.largestRectangleArea(new int[]{1}) == 1;
        System.out.println("Test 3 passed: [1] → 1");

        assert sol.largestRectangleArea(new int[]{2, 2, 2, 2}) == 8;
        System.out.println("Test 4 passed: [2,2,2,2] → 8");

        assert sol.largestRectangleArea(new int[]{6, 2, 5, 4, 5, 1, 6}) == 12;
        System.out.println("Test 5 passed: [6,2,5,4,5,1,6] → 12");

        System.out.println("\n✓ All tests passed!");
    }
}
