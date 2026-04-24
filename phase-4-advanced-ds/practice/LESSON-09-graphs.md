# Lesson 9: Graphs — BFS & DFS on Grids and Adjacency Lists

> Graph problems boil down to: "explore all reachable nodes." BFS = shortest path in unweighted graphs. DFS = explore as deep as possible (connected components, cycle detection).

---

## Two Representations

```java
// 1. Adjacency List (most common)
List<List<Integer>> graph = new ArrayList<>();
for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
graph.get(u).add(v); // directed edge u → v

// 2. Grid (implicit graph)
// Each cell is a node, 4-directional neighbors are edges
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
```

## BFS Template (Shortest Path)

```java
Queue<int[]> queue = new ArrayDeque<>();
queue.offer(new int[]{startR, startC});
boolean[][] visited = new boolean[m][n];
visited[startR][startC] = true;
int steps = 0;

while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        int[] cell = queue.poll();
        // process cell
        for (int[] d : dirs) {
            int nr = cell[0] + d[0], nc = cell[1] + d[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc]) {
                visited[nr][nc] = true;
                queue.offer(new int[]{nr, nc});
            }
        }
    }
    steps++;
}
```

## DFS Template (Connected Components)

```java
void dfs(int[][] grid, int i, int j, boolean[][] visited) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return;
    if (visited[i][j] || grid[i][j] == 0) return;
    visited[i][j] = true;
    for (int[] d : dirs) dfs(grid, i + d[0], j + d[1], visited);
}
```

## Topological Sort (DAG ordering)

```java
// Kahn's Algorithm (BFS-based)
int[] indegree = new int[n];
for (int[] edge : edges) indegree[edge[1]]++;
Queue<Integer> q = new ArrayDeque<>();
for (int i = 0; i < n; i++) if (indegree[i] == 0) q.offer(i);
List<Integer> order = new ArrayList<>();
while (!q.isEmpty()) {
    int node = q.poll();
    order.add(node);
    for (int nei : graph.get(node)) {
        if (--indegree[nei] == 0) q.offer(nei);
    }
}
// If order.size() < n → cycle exists
```

## Problems

**1. Number of Islands (LC 200)** — MEDIUM ⭐⭐ (Most asked graph question)  
**2. Rotting Oranges (LC 994)** — MEDIUM ⭐⭐ (Multi-source BFS)  
**3. Course Schedule (LC 207)** — MEDIUM ⭐⭐ (Topological Sort)  
**4. Clone Graph (LC 133)** — MEDIUM ⭐  

See full theory in `phase-4-advanced-ds/02-graphs.md`.
