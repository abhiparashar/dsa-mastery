import java.util.*;

/**
 * LeetCode 133 - Clone Graph ⭐
 *
 * Deep copy a connected undirected graph. Each node has val and list of neighbors.
 *
 * APPROACH: BFS/DFS with HashMap to track old→new node mapping.
 * 1. Create clone of current node, store in map
 * 2. For each neighbor: if not cloned yet, clone and recurse
 * 3. Add cloned neighbor to current clone's neighbor list
 *
 * KEY: The HashMap serves dual purpose — clone storage AND visited set.
 */
public class CloneGraph {

    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node(int val) {
            this.val = val;
            this.neighbors = new ArrayList<>();
        }
    }

    Map<Node, Node> cloneMap = new HashMap<>();

    public Node cloneGraph_yours(Node node) {
        // TODO
        return null;
    }

    public Node cloneGraph(Node node) {
        if (node == null) return null;
        cloneMap = new HashMap<>();
        return dfs(node);
    }

    Node dfs(Node node) {
        if (cloneMap.containsKey(node)) return cloneMap.get(node);

        Node clone = new Node(node.val);
        cloneMap.put(node, clone);

        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(dfs(neighbor));
        }
        return clone;
    }

    public static void main(String[] args) {
        CloneGraph sol = new CloneGraph();

        // Build: 1 -- 2
        //        |    |
        //        4 -- 3
        Node n1 = new Node(1), n2 = new Node(2), n3 = new Node(3), n4 = new Node(4);
        n1.neighbors.addAll(List.of(n2, n4));
        n2.neighbors.addAll(List.of(n1, n3));
        n3.neighbors.addAll(List.of(n2, n4));
        n4.neighbors.addAll(List.of(n1, n3));

        Node clone = sol.cloneGraph(n1);

        assert clone != n1 : "Must be a new object";
        assert clone.val == 1;
        assert clone.neighbors.size() == 2;
        assert clone.neighbors.get(0).val == 2;
        assert clone.neighbors.get(1).val == 4;
        assert clone.neighbors.get(0) != n2 : "Neighbors must be clones";
        System.out.println("Test 1 passed: 4-node cycle cloned correctly");

        assert sol.cloneGraph(null) == null;
        System.out.println("Test 2 passed: null → null");

        Node single = new Node(1);
        Node sClone = sol.cloneGraph(single);
        assert sClone != single && sClone.val == 1 && sClone.neighbors.isEmpty();
        System.out.println("Test 3 passed: single node cloned");

        System.out.println("\n✓ All tests passed!");
    }
}
