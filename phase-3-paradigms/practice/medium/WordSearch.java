/**
 * LeetCode 79 - Word Search ⭐⭐ (Meta, Amazon top question)
 *
 * Given m×n grid of characters, find if word exists by adjacent cells
 * (horizontal/vertical). Same cell can't be used twice.
 *
 * Example:
 * board = [["A","B","C","E"],
 *          ["S","F","C","S"],
 *          ["A","D","E","E"]]
 * word = "ABCCED" → true
 *
 * APPROACH: DFS/backtracking from each cell.
 * Mark visited by temporarily changing the cell to '#'.
 * Restore after recursion (backtrack).
 */
public class WordSearch {

    public boolean exist_yours(char[][] board, String word) {
        // TODO
        return false;
    }

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
        board[i][j] = '#'; // mark visited

        boolean found = dfs(board, word, i + 1, j, k + 1)
                      || dfs(board, word, i - 1, j, k + 1)
                      || dfs(board, word, i, j + 1, k + 1)
                      || dfs(board, word, i, j - 1, k + 1);

        board[i][j] = temp; // restore (backtrack)
        return found;
    }

    public static void main(String[] args) {
        WordSearch sol = new WordSearch();

        char[][] board = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };

        assert sol.exist(board, "ABCCED") == true;
        System.out.println("Test 1 passed: ABCCED → true");

        assert sol.exist(board, "SEE") == true;
        System.out.println("Test 2 passed: SEE → true");

        assert sol.exist(board, "ABCB") == false;
        System.out.println("Test 3 passed: ABCB → false (can't reuse B)");

        assert sol.exist(new char[][]{{'A'}}, "A") == true;
        System.out.println("Test 4 passed: single cell match → true");

        System.out.println("\n✓ All tests passed!");
    }
}
