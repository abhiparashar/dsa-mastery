/**
 * LeetCode 98 - Validate Binary Search Tree ⭐⭐
 *
 * Check if a binary tree is a valid BST.
 * BST: left < node < right (for ALL descendants, not just immediate children).
 *
 * APPROACH: Pass valid range [min, max] down. Each node must be within range.
 * Use long to handle Integer.MIN_VALUE/MAX_VALUE edge cases.
 *
 * ALTERNATIVE: Inorder traversal should be strictly increasing.
 */
public class ValidateBST {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public boolean isValidBST_yours(TreeNode root) {
        // TODO
        return false;
    }

    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val)
            && validate(node.right, node.val, max);
    }

    public static void main(String[] args) {
        ValidateBST sol = new ValidateBST();

        //   2
        //  / \
        // 1   3
        TreeNode t1 = new TreeNode(2);
        t1.left = new TreeNode(1);
        t1.right = new TreeNode(3);
        assert sol.isValidBST(t1) == true;
        System.out.println("Test 1 passed: [2,1,3] → true");

        //   5
        //  / \
        // 1   4
        //    / \
        //   3   6
        TreeNode t2 = new TreeNode(5);
        t2.left = new TreeNode(1);
        t2.right = new TreeNode(4);
        t2.right.left = new TreeNode(3);
        t2.right.right = new TreeNode(6);
        assert sol.isValidBST(t2) == false;
        System.out.println("Test 2 passed: [5,1,4,null,null,3,6] → false (4 < 5)");

        // Edge: Integer.MIN_VALUE node
        TreeNode t3 = new TreeNode(Integer.MIN_VALUE);
        assert sol.isValidBST(t3) == true;
        System.out.println("Test 3 passed: [MIN_VALUE] → true");

        System.out.println("\n✓ All tests passed!");
    }
}
