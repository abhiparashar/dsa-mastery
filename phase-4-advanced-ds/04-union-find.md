# Phase 4.4: Union-Find / Disjoint Set Union (DSU)

> **First Principle**: Union-Find answers one question efficiently: "Are these two elements in the same group?" It supports two operations — union (merge groups) and find (which group?) — both in nearly O(1) amortized time with path compression and union by rank.

---

## When to Think Union-Find

| Signal | Why Union-Find |
|--------|---------------|
| "Connected components" | Each component = one set |
| "Merge groups" | Union operation |
| "Are X and Y connected?" | Find operation |
| "Earliest time when all connected" | Process edges in order, union |
| "Redundant connection" | Edge that creates a cycle |

---

## Implementation

```java
class UnionFind {
    int[] parent, rank;
    int components;
    
    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;
        for (int i = 0; i < n; i++) parent[i] = i;
    }
    
    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]); // path compression
        return parent[x];
    }
    
    boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false; // already connected
        if (rank[px] < rank[py]) { int temp = px; px = py; py = temp; }
        parent[py] = px;
        if (rank[px] == rank[py]) rank[px]++;
        components--;
        return true;
    }
    
    boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}

// Number of Connected Components (LC 323) ⭐
// Redundant Connection (LC 684) ⭐ — find the edge that creates a cycle
public int[] findRedundantConnection(int[][] edges) {
    UnionFind uf = new UnionFind(edges.length + 1);
    for (int[] edge : edges) {
        if (!uf.union(edge[0], edge[1])) return edge;
    }
    return new int[0];
}

// Accounts Merge (LC 721) ⭐ — union by shared emails
```

---

## Problem Set

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Number of Connected Components (LC 323) | Basic union-find |
| 2 | Redundant Connection (LC 684) ⭐ | Cycle detection |
| 3 | Accounts Merge (LC 721) ⭐ | Union by shared attribute |
| 4 | Number of Provinces (LC 547) | Connected components |
| 5 | Satisfiability of Equality Equations (LC 990) | Union equal, check unequal |
| 6 | Longest Consecutive Sequence (LC 128) | Alternative UF approach |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Number of Islands II (LC 305) | Dynamic union-find |
| 2 | Swim in Rising Water (LC 778) | Binary search + UF |
| 3 | Making a Large Island (LC 827) | UF + try flipping each 0 |

---

*Next: Phase 5 — [DP from First Principles](../phase-5-dp/01-dp-fundamentals.md)*
