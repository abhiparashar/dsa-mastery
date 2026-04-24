import java.util.*;

/**
 * LeetCode 102 - Binary Tree Level Order Traversal ⭐
 *
 * Return values level by level (left to right).
 *
 * APPROACH: BFS with queue. Process one level at a time using queue size.
 *
 * TEMPLATE:
 * Queue<TreeNode> q = new ArrayDeque<>();
 * q.offer(root);
 * while (!q.isEmpty()) {
 *     int size = q.size();          // snapshot level size
 *     for (int i = 0; i < size; i++) {
 *         TreeNode node = q.poll();
 *         // process node
 *         if (node.left != null) q.offer(node.left);
 *         if (node.right != null) q.offer(node.right);
 *     }
 * }
 */
public class LevelOrderTraversal {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public List<List<Integer>> levelOrder_yours(TreeNode root) {
        // TODO
        return new ArrayList<>();
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }

    public static void main(String[] args) {
        LevelOrderTraversal sol = new LevelOrderTraversal();

        //     3
        //    / \
        //   9  20
        //     /  \
        //    15   7
        TreeNode t1 = new TreeNode(3);
        t1.left = new TreeNode(9);
        t1.right = new TreeNode(20);
        t1.right.left = new TreeNode(15);
        t1.right.right = new TreeNode(7);

        List<List<Integer>> r1 = sol.levelOrder(t1);
        assert r1.size() == 3;
        assert r1.get(0).equals(List.of(3));
        assert r1.get(1).equals(List.of(9, 20));
        assert r1.get(2).equals(List.of(15, 7));
        System.out.println("Test 1 passed: " + r1);

        assert sol.levelOrder(null).isEmpty();
        System.out.println("Test 2 passed: null → []");

        TreeNode t3 = new TreeNode(1);
        List<List<Integer>> r3 = sol.levelOrder(t3);
        assert r3.equals(List.of(List.of(1)));
        System.out.println("Test 3 passed: single node → [[1]]");

        System.out.println("\n✓ All tests passed!");
    }
}
