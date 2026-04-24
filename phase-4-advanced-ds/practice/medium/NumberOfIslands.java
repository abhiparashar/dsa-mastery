/**
 * LeetCode 200 - Number of Islands ⭐⭐ (THE most asked graph question)
 *
 * Given m×n grid of '1' (land) and '0' (water), count islands.
 * An island is surrounded by water and formed by connecting adjacent lands.
 *
 * APPROACH: For each unvisited '1', run DFS to mark the entire island.
 * Each DFS call = one island found. Count the number of DFS calls.
 *
 * TRICK: Instead of a visited array, sink the island by setting '1' → '0'.
 */
public class NumberOfIslands {

    public int numIslands_yours(char[][] grid) {
        // TODO
        return 0;
    }

    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }
        return count;
    }

    void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return;
        if (grid[i][j] != '1') return;
        grid[i][j] = '0'; // sink
        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }

    public static void main(String[] args) {
        NumberOfIslands sol = new NumberOfIslands();

        char[][] g1 = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        assert sol.numIslands(g1) == 1;
        System.out.println("Test 1 passed: 1 island");

        char[][] g2 = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        assert sol.numIslands(g2) == 3;
        System.out.println("Test 2 passed: 3 islands");

        char[][] g3 = {{'0'}};
        assert sol.numIslands(g3) == 0;
        System.out.println("Test 3 passed: all water → 0");

        char[][] g4 = {{'1'}};
        assert sol.numIslands(g4) == 1;
        System.out.println("Test 4 passed: single land → 1");

        System.out.println("\n✓ All tests passed!");
    }
}
