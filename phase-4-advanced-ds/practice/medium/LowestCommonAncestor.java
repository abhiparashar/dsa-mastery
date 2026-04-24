/**
 * LeetCode 236 - Lowest Common Ancestor of Binary Tree ⭐⭐
 * META'S #1 MOST ASKED QUESTION.
 *
 * Given a binary tree and two nodes p and q, find their LCA.
 * LCA = deepest node that has both p and q as descendants.
 *
 * APPROACH (bottom-up):
 * 1. If current node is null, p, or q → return it
 * 2. Recurse left and right
 * 3. If both sides return non-null → this node is the LCA
 * 4. If only one side returns non-null → that side has the answer
 *
 * WHY IT WORKS:
 * - If p is in left subtree and q is in right subtree, root is LCA
 * - If both are in left subtree, left recursion finds the LCA
 * - If one of p,q IS the root, root is the LCA (handles the ancestor case)
 */
public class LowestCommonAncestor {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public TreeNode lowestCommonAncestor_yours(TreeNode root, TreeNode p, TreeNode q) {
        // TODO
        return null;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root; // p and q on different sides
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        //       3
        //      / \
        //     5   1
        //    / \ / \
        //   6  2 0  8
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);

        LowestCommonAncestor sol = new LowestCommonAncestor();

        TreeNode lca1 = sol.lowestCommonAncestor(root, root.left, root.right);
        assert lca1.val == 3;
        System.out.println("LCA(5, 1) = 3 ✓");

        TreeNode lca2 = sol.lowestCommonAncestor(root, root.left, root.left.right);
        assert lca2.val == 5;
        System.out.println("LCA(5, 2) = 5 ✓ (5 is ancestor of 2)");

        TreeNode lca3 = sol.lowestCommonAncestor(root, root.left.left, root.left.right);
        assert lca3.val == 5;
        System.out.println("LCA(6, 2) = 5 ✓");

        System.out.println("\n✓ All tests passed!");
    }
}
