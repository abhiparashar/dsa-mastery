/**
 * LeetCode 226 - Invert Binary Tree ⭐
 *
 * Mirror a binary tree (swap left and right at every node).
 *
 * APPROACH: Bottom-up. Invert children, then swap them.
 * Can also think top-down: swap first, then recurse.
 */
public class InvertBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public TreeNode invertTree_yours(TreeNode root) {
        // TODO
        return null;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    public static void main(String[] args) {
        InvertBinaryTree sol = new InvertBinaryTree();

        //   4           4
        //  / \   →     / \
        // 2   7       7   2
        TreeNode t1 = new TreeNode(4);
        t1.left = new TreeNode(2);
        t1.right = new TreeNode(7);
        TreeNode r1 = sol.invertTree(t1);
        assert r1.left.val == 7 && r1.right.val == 2;
        System.out.println("Test 1 passed: [4,2,7] → [4,7,2]");

        assert sol.invertTree(null) == null;
        System.out.println("Test 2 passed: null → null");

        TreeNode t3 = new TreeNode(1);
        TreeNode r3 = sol.invertTree(t3);
        assert r3.val == 1 && r3.left == null && r3.right == null;
        System.out.println("Test 3 passed: single node unchanged");

        System.out.println("\n✓ All tests passed!");
    }
}
