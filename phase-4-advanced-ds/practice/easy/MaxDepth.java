/**
 * LeetCode 104 - Maximum Depth of Binary Tree ⭐
 *
 * Return the maximum depth (number of nodes on longest root-to-leaf path).
 *
 * APPROACH: Bottom-up. Depth = 1 + max(left depth, right depth).
 * Base case: null → 0.
 */
public class MaxDepth {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public int maxDepth_yours(TreeNode root) {
        // TODO
        return 0;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static void main(String[] args) {
        MaxDepth sol = new MaxDepth();

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
        assert sol.maxDepth(t1) == 3;
        System.out.println("Test 1 passed: depth = 3");

        assert sol.maxDepth(null) == 0;
        System.out.println("Test 2 passed: null → 0");

        TreeNode t3 = new TreeNode(1);
        t3.left = new TreeNode(2);
        assert sol.maxDepth(t3) == 2;
        System.out.println("Test 3 passed: skewed → 2");

        System.out.println("\n✓ All tests passed!");
    }
}
