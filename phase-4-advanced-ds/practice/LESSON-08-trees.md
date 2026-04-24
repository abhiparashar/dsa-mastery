# Lesson 8: Binary Trees — Think Bottom-Up

> Every tree problem: "What can the root do if its left and right subtrees already have the answer?" This is bottom-up (postorder) thinking. Master it and trees become mechanical.

---

## The Three Traversal Patterns

```java
// Bottom-up (MOST COMMON): solve children first, combine at root
int solve(TreeNode node) {
    if (node == null) return baseCase;
    int left = solve(node.left);
    int right = solve(node.right);
    return combine(left, right, node.val);
}

// Top-down: pass info from root to children
void solve(TreeNode node, int infoFromParent) { ... }

// Level-order (BFS): process level by level with a queue
```

## Problems

**1. Maximum Depth (LC 104)** — EASY ⭐  
**2. Invert Binary Tree (LC 226)** — EASY ⭐  
**3. Lowest Common Ancestor (LC 236)** — MEDIUM ⭐⭐ (Meta #1)  
**4. Validate BST (LC 98)** — MEDIUM ⭐⭐  
**5. Binary Tree Level Order (LC 102)** — MEDIUM ⭐  
**6. Diameter of Binary Tree (LC 543)** — MEDIUM ⭐⭐  
**7. Serialize/Deserialize BT (LC 297)** — HARD ⭐⭐  

See Java files and full theory in `phase-4-advanced-ds/01-trees-bst.md`.
