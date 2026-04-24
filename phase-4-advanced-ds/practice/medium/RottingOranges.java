import java.util.*;

/**
 * LeetCode 994 - Rotting Oranges ⭐⭐ (Multi-source BFS classic)
 *
 * Grid: 0=empty, 1=fresh, 2=rotten. Each minute, rotten oranges rot adjacent
 * fresh ones. Return minutes until no fresh oranges, or -1 if impossible.
 *
 * APPROACH: Multi-source BFS.
 * 1. Add ALL rotten oranges to queue at once (they all start at time 0)
 * 2. BFS level by level (each level = 1 minute)
 * 3. Check if any fresh oranges remain
 *
 * KEY INSIGHT: This is NOT "start BFS from one rotten orange."
 * ALL rotten oranges expand simultaneously — that's multi-source BFS.
 */
public class RottingOranges {

    public int orangesRotting_yours(int[][] grid) {
        // TODO
        return -1;
    }

    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int fresh = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                else if (grid[i][j] == 1) fresh++;
            }
        }

        if (fresh == 0) return 0;

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        int minutes = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        fresh--;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
            minutes++;
        }

        return fresh == 0 ? minutes - 1 : -1;
    }

    public static void main(String[] args) {
        RottingOranges sol = new RottingOranges();

        int[][] g1 = {{2,1,1},{1,1,0},{0,1,1}};
        assert sol.orangesRotting(g1) == 4;
        System.out.println("Test 1 passed: 4 minutes");

        int[][] g2 = {{2,1,1},{0,1,1},{1,0,1}};
        assert sol.orangesRotting(g2) == -1;
        System.out.println("Test 2 passed: -1 (unreachable)");

        int[][] g3 = {{0,2}};
        assert sol.orangesRotting(g3) == 0;
        System.out.println("Test 3 passed: 0 (no fresh)");

        int[][] g4 = {{1}};
        assert sol.orangesRotting(g4) == -1;
        System.out.println("Test 4 passed: -1 (no rotten to start)");

        System.out.println("\n✓ All tests passed!");
    }
}
