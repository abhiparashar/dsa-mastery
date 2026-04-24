/**
 * LeetCode 543 - Diameter of Binary Tree ⭐⭐
 *
 * Diameter = longest path between ANY two nodes (measured in edges).
 * The path may or may not pass through the root.
 *
 * APPROACH: Bottom-up. At each node, the path through it = leftHeight + rightHeight.
 * Track the global max while returning height to parent.
 *
 * KEY INSIGHT: Height function returns height, but SIDE EFFECT updates diameter.
 * This "piggyback" pattern appears in many tree problems.
 */
public class DiameterOfBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    int maxDiameter;

    public int diameterOfBinaryTree_yours(TreeNode root) {
        // TODO
        return 0;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        height(root);
        return maxDiameter;
    }

    int height(TreeNode node) {
        if (node == null) return 0;
        int left = height(node.left);
        int right = height(node.right);
        maxDiameter = Math.max(maxDiameter, left + right);
        return 1 + Math.max(left, right);
    }

    public static void main(String[] args) {
        DiameterOfBinaryTree sol = new DiameterOfBinaryTree();

        //     1
        //    / \
        //   2   3
        //  / \
        // 4   5
        TreeNode t1 = new TreeNode(1);
        t1.left = new TreeNode(2);
        t1.right = new TreeNode(3);
        t1.left.left = new TreeNode(4);
        t1.left.right = new TreeNode(5);
        assert sol.diameterOfBinaryTree(t1) == 3;
        System.out.println("Test 1 passed: diameter = 3 (4→2→1→3)");

        TreeNode t2 = new TreeNode(1);
        t2.left = new TreeNode(2);
        assert sol.diameterOfBinaryTree(t2) == 1;
        System.out.println("Test 2 passed: diameter = 1");

        assert sol.diameterOfBinaryTree(new TreeNode(1)) == 0;
        System.out.println("Test 3 passed: single node → 0");

        // Diameter not through root
        //       1
        //      /
        //     2
        //    / \
        //   3   4
        //  /     \
        // 5       6
        TreeNode t4 = new TreeNode(1);
        t4.left = new TreeNode(2);
        t4.left.left = new TreeNode(3);
        t4.left.right = new TreeNode(4);
        t4.left.left.left = new TreeNode(5);
        t4.left.right.right = new TreeNode(6);
        assert sol.diameterOfBinaryTree(t4) == 4;
        System.out.println("Test 4 passed: diameter = 4 (not through root)");

        System.out.println("\n✓ All tests passed!");
    }
}
