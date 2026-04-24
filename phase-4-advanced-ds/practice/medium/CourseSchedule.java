import java.util.*;

/**
 * LeetCode 207 - Course Schedule ⭐⭐ (Topological Sort classic)
 *
 * n courses with prerequisites. Can you finish all courses?
 * Equivalent to: does the directed graph have a cycle?
 *
 * APPROACH: Kahn's Algorithm (BFS Topological Sort)
 * 1. Build adjacency list and in-degree array
 * 2. Start BFS from all nodes with in-degree 0
 * 3. For each processed node, reduce neighbors' in-degree
 * 4. If we process all n nodes → no cycle → can finish
 *
 * ALTERNATIVE: DFS with 3-color marking (white/gray/black).
 */
public class CourseSchedule {

    public boolean canFinish_yours(int numCourses, int[][] prerequisites) {
        // TODO
        return false;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);
            indegree[pre[0]]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }

        int processed = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            processed++;
            for (int next : graph.get(course)) {
                if (--indegree[next] == 0) queue.offer(next);
            }
        }

        return processed == numCourses;
    }

    public static void main(String[] args) {
        CourseSchedule sol = new CourseSchedule();

        assert sol.canFinish(2, new int[][]{{1,0}}) == true;
        System.out.println("Test 1 passed: [1→0] can finish");

        assert sol.canFinish(2, new int[][]{{1,0},{0,1}}) == false;
        System.out.println("Test 2 passed: cycle → false");

        assert sol.canFinish(3, new int[][]{{1,0},{2,1}}) == true;
        System.out.println("Test 3 passed: chain 0→1→2 can finish");

        assert sol.canFinish(1, new int[][]{}) == true;
        System.out.println("Test 4 passed: single course, no prereqs");

        assert sol.canFinish(4, new int[][]{{1,0},{2,1},{3,2},{1,3}}) == false;
        System.out.println("Test 5 passed: larger cycle → false");

        System.out.println("\n✓ All tests passed!");
    }
}
