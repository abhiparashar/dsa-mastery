/**
 * LeetCode 684 - Redundant Connection ⭐⭐ (Union-Find classic)
 *
 * Given an undirected graph that started as a tree with one extra edge added,
 * find and return that extra edge (last one in input if multiple answers).
 *
 * APPROACH: Union-Find. Process edges one by one.
 * If both endpoints are already in the same set → this edge creates a cycle.
 *
 * UNION-FIND TEMPLATE:
 * - find(x): return root of x's set (with path compression)
 * - union(x,y): merge sets (with rank optimization)
 * - If find(x) == find(y) before union → cycle!
 */
public class RedundantConnection {

    int[] parent, rank;

    public int[] findRedundantConnection_yours(int[][] edges) {
        // TODO
        return new int[]{};
    }

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 1; i <= n; i++) parent[i] = i;

        for (int[] edge : edges) {
            if (!union(edge[0], edge[1])) {
                return edge; // already connected → redundant
            }
        }
        return new int[]{};
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false; // already same set
        if (rank[px] < rank[py]) { int tmp = px; px = py; py = tmp; }
        parent[py] = px;
        if (rank[px] == rank[py]) rank[px]++;
        return true;
    }

    public static void main(String[] args) {
        RedundantConnection sol = new RedundantConnection();

        int[] r1 = sol.findRedundantConnection(new int[][]{{1,2},{1,3},{2,3}});
        assert r1[0] == 2 && r1[1] == 3;
        System.out.println("Test 1 passed: [2,3] is redundant");

        int[] r2 = sol.findRedundantConnection(new int[][]{{1,2},{2,3},{3,4},{1,4},{1,5}});
        assert r2[0] == 1 && r2[1] == 4;
        System.out.println("Test 2 passed: [1,4] is redundant");

        int[] r3 = sol.findRedundantConnection(new int[][]{{1,2},{1,3},{1,4},{3,4}});
        assert r3[0] == 3 && r3[1] == 4;
        System.out.println("Test 3 passed: [3,4] is redundant");

        System.out.println("\n✓ All tests passed!");
    }
}
