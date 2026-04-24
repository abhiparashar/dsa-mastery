# Phase 3.4: Recursion & Backtracking

> **First Principle**: Recursion solves a problem by solving smaller versions of the same problem. Backtracking adds a twist: we explore a decision tree, and when a path fails, we *undo* the last decision and try the next option. If recursion is "divide and conquer on a single branch," backtracking is "explore all branches and prune."

---

## How to Think Recursively

**The 3-step framework:**
1. **Base case**: What's the simplest version of this problem? (Usually empty input, single element, or goal reached.)
2. **Recursive step**: Assume the function works for a smaller input. How do you use that to solve the current input?
3. **Combine**: How do you combine the sub-results?

```
Factorial: 5! = 5 * 4!
                    4! = 4 * 3!
                              3! = 3 * 2!
                                        2! = 2 * 1!
                                                  1! = 1 (base case)
```

---

## Backtracking Template (THE Template)

```java
void backtrack(List<List<Integer>> result, List<Integer> path, /* state */) {
    // 1. Base case: if we've built a complete solution
    if (isComplete(path)) {
        result.add(new ArrayList<>(path)); // COPY the path!
        return;
    }
    
    // 2. Try each choice
    for (int choice : getChoices(/* state */)) {
        // 3. Check if this choice is valid (PRUNE)
        if (!isValid(choice)) continue;
        
        // 4. Make the choice
        path.add(choice);
        // update state
        
        // 5. Recurse
        backtrack(result, path, /* updated state */);
        
        // 6. UNDO the choice (backtrack)
        path.remove(path.size() - 1);
        // restore state
    }
}
```

**Why copy the path?** Java passes object references. If you do `result.add(path)`, all entries in result point to the SAME list, which will be empty when backtracking finishes.

---

## The Big Three: Subsets, Permutations, Combinations

These are the foundations. Every backtracking problem is a variant of one of these.

### Subsets (LC 78) ⭐⭐

**Decision at each step**: Include this element or skip it.

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, int start) {
    result.add(new ArrayList<>(path)); // every path is a valid subset
    
    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        backtrack(result, path, nums, i + 1); // i + 1: don't reuse
        path.remove(path.size() - 1);
    }
}
// Generates: [], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]
```

### Subsets II — With Duplicates (LC 90) ⭐

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums); // MUST sort to group duplicates
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, int start) {
    result.add(new ArrayList<>(path));
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue; // skip duplicate at same level
        path.add(nums[i]);
        backtrack(result, path, nums, i + 1);
        path.remove(path.size() - 1);
    }
}
```

### Permutations (LC 46) ⭐⭐

**Decision at each step**: Which remaining element goes in this position?

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, new boolean[nums.length]);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, boolean[] used) {
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
```

### Permutations II — With Duplicates (LC 47) ⭐

```java
void backtrack(List<List<Integer>> result, List<Integer> path, int[] nums, boolean[] used) {
    if (path.size() == nums.length) {
        result.add(new ArrayList<>(path));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        // Skip duplicate: same value as previous, and previous was not used (backtracked)
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
        used[i] = true;
        path.add(nums[i]);
        backtrack(result, path, nums, used);
        path.remove(path.size() - 1);
        used[i] = false;
    }
}
```

### Combinations (LC 77)

**Decision at each step**: Choose K elements from 1 to n.

```java
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), n, k, 1);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> path, int n, int k, int start) {
    if (path.size() == k) {
        result.add(new ArrayList<>(path));
        return;
    }
    // Pruning: need (k - path.size()) more elements, so don't start past n - remaining + 1
    for (int i = start; i <= n - (k - path.size()) + 1; i++) {
        path.add(i);
        backtrack(result, path, n, k, i + 1);
        path.remove(path.size() - 1);
    }
}
```

---

## Decision Tree Visualization

For `subsets([1, 2, 3])`:
```
                    []
              /      |      \
           [1]      [2]     [3]
          /    \      |
       [1,2]  [1,3] [2,3]
         |
      [1,2,3]
```

For `permutations([1, 2, 3])`:
```
                      []
              /        |        \
           [1]        [2]       [3]
          /   \      /   \     /   \
       [1,2] [1,3] [2,1] [2,3] [3,1] [3,2]
         |     |     |     |     |     |
     [1,2,3] ...   ...   ...   ...  [3,2,1]
```

---

## Classic Backtracking Problems

```java
// N-Queens (LC 51) ⭐ — THE classic backtracking problem
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    boolean[] cols = new boolean[n];
    boolean[] diag1 = new boolean[2 * n]; // row - col + n
    boolean[] diag2 = new boolean[2 * n]; // row + col
    
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row, '.');
    
    solve(result, board, 0, cols, diag1, diag2, n);
    return result;
}

void solve(List<List<String>> result, char[][] board, int row,
           boolean[] cols, boolean[] diag1, boolean[] diag2, int n) {
    if (row == n) {
        List<String> solution = new ArrayList<>();
        for (char[] r : board) solution.add(new String(r));
        result.add(solution);
        return;
    }
    for (int col = 0; col < n; col++) {
        if (cols[col] || diag1[row - col + n] || diag2[row + col]) continue;
        
        board[row][col] = 'Q';
        cols[col] = diag1[row - col + n] = diag2[row + col] = true;
        
        solve(result, board, row + 1, cols, diag1, diag2, n);
        
        board[row][col] = '.';
        cols[col] = diag1[row - col + n] = diag2[row + col] = false;
    }
}

// Word Search (LC 79) ⭐⭐ — Meta, Amazon top question
public boolean exist(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (dfs(board, word, i, j, 0)) return true;
        }
    }
    return false;
}

boolean dfs(char[][] board, String word, int i, int j, int k) {
    if (k == word.length()) return true;
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;
    if (board[i][j] != word.charAt(k)) return false;
    
    char temp = board[i][j];
    board[i][j] = '#'; // mark visited (in-place!)
    
    boolean found = dfs(board, word, i+1, j, k+1) || dfs(board, word, i-1, j, k+1) ||
                    dfs(board, word, i, j+1, k+1) || dfs(board, word, i, j-1, k+1);
    
    board[i][j] = temp; // restore
    return found;
}

// Generate Parentheses (LC 22) ⭐⭐
public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    generate(result, new StringBuilder(), 0, 0, n);
    return result;
}

void generate(List<String> result, StringBuilder sb, int open, int close, int n) {
    if (sb.length() == 2 * n) {
        result.add(sb.toString());
        return;
    }
    if (open < n) {
        sb.append('(');
        generate(result, sb, open + 1, close, n);
        sb.deleteCharAt(sb.length() - 1);
    }
    if (close < open) {
        sb.append(')');
        generate(result, sb, open, close + 1, n);
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

---

## Recursion vs Backtracking vs DP

| | Recursion | Backtracking | DP |
|---|-----------|-------------|-----|
| **Goal** | Solve by decomposition | Find all/any valid configs | Optimize (min/max/count) |
| **Explores** | One path | All paths (with pruning) | All subproblems (with memo) |
| **Undo step?** | No | Yes (backtrack) | No |
| **Overlapping?** | Maybe | Usually not | Yes — that's the point |
| **Example** | Merge sort | N-Queens | Fibonacci |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Power of Three (LC 326) | Simple recursion |
| 2 | Fibonacci Number (LC 509) | Recursion → memoization intro |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Subsets (LC 78) ⭐⭐ | Include/exclude |
| 2 | Subsets II (LC 90) ⭐ | Dedup at same level |
| 3 | Permutations (LC 46) ⭐⭐ | Used array |
| 4 | Permutations II (LC 47) ⭐ | Used array + dedup |
| 5 | Combination Sum (LC 39) ⭐⭐ | Unlimited reuse |
| 6 | Combination Sum II (LC 40) ⭐ | Each once + dedup |
| 7 | Generate Parentheses (LC 22) ⭐⭐ | Constraint backtracking |
| 8 | Word Search (LC 79) ⭐⭐ | Grid DFS + backtrack |
| 9 | Letter Combinations of Phone (LC 17) ⭐ | Multi-branch recursion |
| 10 | Palindrome Partitioning (LC 131) ⭐ | Partition + validate |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | N-Queens (LC 51) ⭐ | Constraint-heavy backtracking |
| 2 | Sudoku Solver (LC 37) | Cell-by-cell backtracking |
| 3 | Word Search II (LC 212) ⭐ | Trie + backtracking |
| 4 | Expression Add Operators (LC 282) | Backtrack with math state |
| 5 | Stickers to Spell Word (LC 691) | Bitmask + backtracking |

---

*Next: Phase 4 — [Binary Trees & BST](../phase-4-advanced-ds/01-trees-bst.md)*
