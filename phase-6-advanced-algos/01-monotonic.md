# Phase 6.1: Monotonic Stack & Queue — Advanced Patterns

> Already introduced in Phase 2. This covers advanced applications.

---

## Advanced Monotonic Stack

### Largest Rectangle in Histogram (LC 84) ⭐⭐ — The Gold Standard

```java
public int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int maxArea = 0;
    
    for (int i = 0; i <= heights.length; i++) {
        int h = (i == heights.length) ? 0 : heights[i];
        while (!stack.isEmpty() && h < heights[stack.peek()]) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        stack.push(i);
    }
    return maxArea;
}

// Maximal Rectangle (LC 85) ⭐ — build histogram row by row, apply LC 84
public int maximalRectangle(char[][] matrix) {
    if (matrix.length == 0) return 0;
    int[] heights = new int[matrix[0].length];
    int maxArea = 0;
    
    for (char[] row : matrix) {
        for (int j = 0; j < row.length; j++) {
            heights[j] = row[j] == '1' ? heights[j] + 1 : 0;
        }
        maxArea = Math.max(maxArea, largestRectangleArea(heights));
    }
    return maxArea;
}

// Sum of Subarray Minimums (LC 907) — monotonic stack + contribution technique
public int sumSubarrayMins(int[] arr) {
    int MOD = 1_000_000_007;
    int n = arr.length;
    long result = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (int i = 0; i <= n; i++) {
        while (!stack.isEmpty() && (i == n || arr[stack.peek()] >= arr[i])) {
            int mid = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            int right = i;
            result = (result + (long) arr[mid] * (mid - left) * (right - mid)) % MOD;
        }
        stack.push(i);
    }
    return (int) result;
}
```

---

## Problem Set

| # | Problem | Pattern |
|---|---------|---------|
| 1 | Largest Rectangle in Histogram (LC 84) ⭐⭐ | Monotonic stack |
| 2 | Maximal Rectangle (LC 85) ⭐ | Histogram per row |
| 3 | Trapping Rain Water (LC 42) ⭐⭐ | Stack or two-pointer |
| 4 | Sum of Subarray Minimums (LC 907) | Contribution counting |
| 5 | Sum of Subarray Ranges (LC 2104) | Min + max contribution |
| 6 | Remove K Digits (LC 402) ⭐ | Monotonic increasing |
| 7 | 132 Pattern (LC 456) | Monotonic stack from right |
| 8 | Sliding Window Maximum (LC 239) ⭐⭐ | Monotonic deque |

---

*Next: [Segment Tree & BIT](02-segment-tree.md)*
