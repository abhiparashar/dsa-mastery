import java.util.*;

/**
 * LeetCode 297 - Serialize and Deserialize Binary Tree ⭐⭐ (HARD)
 *
 * Convert a binary tree to a string and back.
 *
 * APPROACH: Preorder traversal with "null" markers.
 * Serialize: preorder, write val or "null" for null nodes.
 * Deserialize: read tokens in order, recursively build tree.
 *
 * WHY PREORDER + NULL MARKERS WORKS:
 * Preorder alone is ambiguous (multiple trees give same preorder).
 * But with null markers, the structure is uniquely determined.
 */
public class SerializeDeserializeBT {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // --- YOUR SOLUTION ---
    public String serialize_yours(TreeNode root) {
        // TODO
        return "";
    }

    public TreeNode deserialize_yours(String data) {
        // TODO
        return null;
    }

    // --- OPTIMAL SOLUTION ---
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    public TreeNode deserialize(String data) {
        Queue<String> tokens = new ArrayDeque<>(Arrays.asList(data.split(",")));
        return deserializeHelper(tokens);
    }

    TreeNode deserializeHelper(Queue<String> tokens) {
        String val = tokens.poll();
        if (val.equals("null")) return null;
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(tokens);
        node.right = deserializeHelper(tokens);
        return node;
    }

    public static void main(String[] args) {
        SerializeDeserializeBT sol = new SerializeDeserializeBT();

        //     1
        //    / \
        //   2   3
        //      / \
        //     4   5
        TreeNode t1 = new TreeNode(1);
        t1.left = new TreeNode(2);
        t1.right = new TreeNode(3);
        t1.right.left = new TreeNode(4);
        t1.right.right = new TreeNode(5);

        String s1 = sol.serialize(t1);
        System.out.println("Serialized: " + s1);
        TreeNode d1 = sol.deserialize(s1);
        assert d1.val == 1;
        assert d1.left.val == 2;
        assert d1.right.val == 3;
        assert d1.right.left.val == 4;
        assert d1.right.right.val == 5;
        System.out.println("Test 1 passed: round-trip [1,2,3,null,null,4,5]");

        String s2 = sol.serialize(null);
        TreeNode d2 = sol.deserialize(s2);
        assert d2 == null;
        System.out.println("Test 2 passed: null round-trip");

        TreeNode t3 = new TreeNode(42);
        String s3 = sol.serialize(t3);
        TreeNode d3 = sol.deserialize(s3);
        assert d3.val == 42 && d3.left == null && d3.right == null;
        System.out.println("Test 3 passed: single node round-trip");

        // Negative values
        TreeNode t4 = new TreeNode(-1);
        t4.left = new TreeNode(-2);
        String s4 = sol.serialize(t4);
        TreeNode d4 = sol.deserialize(s4);
        assert d4.val == -1 && d4.left.val == -2;
        System.out.println("Test 4 passed: negative values");

        System.out.println("\n✓ All tests passed!");
    }
}
