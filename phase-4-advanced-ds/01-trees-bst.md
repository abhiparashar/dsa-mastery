# Phase 4.1: Binary Trees & BST

> **First Principle**: A tree is a connected acyclic graph. A binary tree restricts each node to at most 2 children. A BST adds the invariant: left < node < right. Trees are the first data structure where recursion isn't optional — it's *natural*. Every tree problem reduces to: "What can the root do if its left and right subtrees already have the answer?"

---

## Mental Model

```
        1           Binary Tree: no ordering guarantee
       / \
      2   3         BST: left subtree < node < right subtree
     / \   \
    4   5   6       Complete: every level filled except possibly last
                    Balanced: height = O(log n)
```

```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}
```

---

## The Recursive Framework for Tree Problems

Every tree problem follows one of these patterns:

### Pattern 1: Top-Down (Preorder Thinking)
Pass information DOWN from parent to children.

```java
// Max Depth of Binary Tree (LC 104) — top-down
void maxDepth(TreeNode node, int depth, int[] max) {
    if (node == null) return;
    max[0] = Math.max(max[0], depth);
    maxDepth(node.left, depth + 1, max);
    maxDepth(node.right, depth + 1, max);
}
```

### Pattern 2: Bottom-Up (Postorder Thinking) — THE MOST COMMON
Get information UP from children to parent. Most tree problems use this.

```java
// Max Depth — bottom-up (cleaner)
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}

// Diameter of Binary Tree (LC 543) ⭐⭐
int diameter = 0;
public int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return diameter;
}
int height(TreeNode node) {
    if (node == null) return 0;
    int left = height(node.left);
    int right = height(node.right);
    diameter = Math.max(diameter, left + right); // path through this node
    return 1 + Math.max(left, right);
}
```

### Pattern 3: Level-Order (BFS)

```java
// Binary Tree Level Order Traversal (LC 102) ⭐
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
```

---

## Tree Traversals (The Foundation)

```java
// Inorder: Left → Root → Right (gives sorted order for BST!)
void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    inorder(node.left, result);
    result.add(node.val);
    inorder(node.right, result);
}

// Preorder: Root → Left → Right (serialize/reconstruct trees)
// Postorder: Left → Right → Root (compute size/height bottom-up)

// Iterative Inorder — important for interview
public List<Integer> inorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        result.add(curr.val);
        curr = curr.right;
    }
    return result;
}
```

---

## Classic Tree Problems

```java
// Invert Binary Tree (LC 226) ⭐
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);
    root.left = right;
    root.right = left;
    return root;
}

// Symmetric Tree (LC 101) ⭐
public boolean isSymmetric(TreeNode root) {
    return isMirror(root.left, root.right);
}
boolean isMirror(TreeNode a, TreeNode b) {
    if (a == null && b == null) return true;
    if (a == null || b == null) return false;
    return a.val == b.val && isMirror(a.left, b.right) && isMirror(a.right, b.left);
}

// Lowest Common Ancestor (LC 236) ⭐⭐ — Meta #1 most asked
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root; // p and q on different sides
    return left != null ? left : right;
}

// Path Sum III (LC 437) ⭐ — prefix sum on trees!
public int pathSum(TreeNode root, int targetSum) {
    Map<Long, Integer> prefixSums = new HashMap<>();
    prefixSums.put(0L, 1);
    return dfs(root, 0, targetSum, prefixSums);
}
int dfs(TreeNode node, long currentSum, int target, Map<Long, Integer> prefixSums) {
    if (node == null) return 0;
    currentSum += node.val;
    int count = prefixSums.getOrDefault(currentSum - target, 0);
    prefixSums.merge(currentSum, 1, Integer::sum);
    count += dfs(node.left, currentSum, target, prefixSums);
    count += dfs(node.right, currentSum, target, prefixSums);
    prefixSums.merge(currentSum, -1, Integer::sum); // backtrack!
    return count;
}

// Serialize and Deserialize Binary Tree (LC 297) ⭐⭐
public class Codec {
    public String serialize(TreeNode root) {
        if (root == null) return "null";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }
    
    public TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        return build(queue);
    }
    
    TreeNode build(Queue<String> queue) {
        String val = queue.poll();
        if ("null".equals(val)) return null;
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = build(queue);
        node.right = build(queue);
        return node;
    }
}
```

---

## BST-Specific Patterns

**Key insight**: Inorder traversal of a BST gives sorted order. Many BST problems exploit this.

```java
// Validate BST (LC 98) ⭐⭐
public boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}
boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return validate(node.left, min, node.val) && validate(node.right, node.val, max);
}

// Kth Smallest Element in BST (LC 230) ⭐
public int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (true) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        curr = stack.pop();
        if (--k == 0) return curr.val;
        curr = curr.right;
    }
}
```

---

## Edge Cases

| Case | Trap |
|------|------|
| null root | Return 0, null, false, or empty list |
| Single node | Left and right are both null |
| Skewed tree (linked list) | Height = n, not log n. Stack overflow risk |
| BST with Integer.MIN_VALUE / MAX_VALUE | Use long for range checks |
| Duplicate values in BST | Decide: left ≤ or strictly left < |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Maximum Depth (LC 104) ⭐ | Bottom-up |
| 2 | Invert Binary Tree (LC 226) ⭐ | Bottom-up swap |
| 3 | Same Tree (LC 100) | Two-tree recursion |
| 4 | Symmetric Tree (LC 101) ⭐ | Mirror check |
| 5 | Path Sum (LC 112) | Top-down target |
| 6 | Subtree of Another Tree (LC 572) ⭐ | Same tree + search |
| 7 | Balanced Binary Tree (LC 110) | Bottom-up height |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Lowest Common Ancestor (LC 236) ⭐⭐ | Bottom-up search |
| 2 | Binary Tree Level Order (LC 102) ⭐ | BFS |
| 3 | Validate BST (LC 98) ⭐⭐ | Range propagation |
| 4 | Kth Smallest in BST (LC 230) ⭐ | Iterative inorder |
| 5 | Diameter of Binary Tree (LC 543) ⭐⭐ | Height with side effect |
| 6 | Construct from Preorder & Inorder (LC 105) ⭐ | Divide & conquer |
| 7 | Right Side View (LC 199) ⭐ | BFS last of each level |
| 8 | Path Sum III (LC 437) ⭐ | Prefix sum on tree |
| 9 | Binary Tree Zigzag Level Order (LC 103) | BFS + alternating |
| 10 | Flatten BT to Linked List (LC 114) ⭐ | Postorder or Morris |
| 11 | Count Good Nodes (LC 1448) | Top-down max tracking |
| 12 | Binary Tree Max Path Sum (LC 124) ⭐⭐ | Bottom-up with global max |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Serialize/Deserialize BT (LC 297) ⭐⭐ | Preorder + queue |
| 2 | Binary Tree Max Path Sum (LC 124) ⭐⭐ | Bottom-up, consider negatives |
| 3 | Vertical Order Traversal (LC 987) | BFS + coordinate sorting |
| 4 | Binary Tree Cameras (LC 968) | Greedy postorder |

---

*Next: [Graphs — BFS, DFS, and Beyond](02-graphs.md)*
