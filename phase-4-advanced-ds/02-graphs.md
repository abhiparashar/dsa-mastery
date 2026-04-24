# Phase 4.2: Graphs — BFS, DFS, and Beyond

> **First Principle**: A graph is a set of nodes connected by edges. Trees are special graphs (connected, acyclic). Graphs are the most general data structure for representing relationships. Every graph problem reduces to: traversal (BFS/DFS), shortest path, connectivity, or cycle detection.

---

## Graph Representation

```java
// Adjacency List (most common in interviews)
List<List<Integer>> graph = new ArrayList<>();
for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
graph.get(u).add(v); // edge u → v

// From edge list
int[][] edges = {{0,1}, {1,2}, {2,0}};
for (int[] edge : edges) {
    graph.get(edge[0]).add(edge[1]);
    graph.get(edge[1]).add(edge[0]); // if undirected
}

// Adjacency Matrix (when n is small or dense graph)
boolean[][] adj = new boolean[n][n];
adj[u][v] = true;

// Weighted graph
List<int[]>[] graph = new List[n]; // int[] = {neighbor, weight}

// Implicit graph (grid)
int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
```

---

## BFS — Breadth-First Search

**Use when**: Shortest path in unweighted graph, level-order processing, minimum steps.

```java
// BFS Template
int bfs(int start, int target, List<List<Integer>> graph) {
    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();
    queue.offer(start);
    visited.add(start);
    int level = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int node = queue.poll();
            if (node == target) return level;
            for (int neighbor : graph.get(node)) {
                if (visited.add(neighbor)) {
                    queue.offer(neighbor);
                }
            }
        }
        level++;
    }
    return -1; // unreachable
}

// BFS on Grid — Number of Islands (LC 200) ⭐⭐
public int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                count++;
                bfs(grid, i, j);
            }
        }
    }
    return count;
}

void bfs(char[][] grid, int r, int c) {
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    Queue<int[]> queue = new ArrayDeque<>();
    queue.offer(new int[]{r, c});
    grid[r][c] = '0'; // mark visited by modifying input
    
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : dirs) {
            int nr = cell[0] + d[0], nc = cell[1] + d[1];
            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length 
                && grid[nr][nc] == '1') {
                grid[nr][nc] = '0';
                queue.offer(new int[]{nr, nc});
            }
        }
    }
}
```

---

## DFS — Depth-First Search

**Use when**: Explore all paths, detect cycles, topological sort, connected components.

```java
// DFS Template (Recursive)
void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, graph, visited);
        }
    }
}

// DFS Template (Iterative)
void dfs(int start, List<List<Integer>> graph) {
    Deque<Integer> stack = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();
    stack.push(start);
    
    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (!visited.add(node)) continue;
        for (int neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                stack.push(neighbor);
            }
        }
    }
}
```

---

## Topological Sort

**Use when**: Dependencies, course scheduling, build order. Only works on DAGs.

```java
// Topological Sort — BFS (Kahn's Algorithm) — THE interview approach
// Course Schedule II (LC 210) ⭐⭐
public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] inDegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);
        inDegree[pre[0]]++;
    }
    
    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    int[] order = new int[numCourses];
    int idx = 0;
    while (!queue.isEmpty()) {
        int course = queue.poll();
        order[idx++] = course;
        for (int next : graph.get(course)) {
            if (--inDegree[next] == 0) queue.offer(next);
        }
    }
    return idx == numCourses ? order : new int[0]; // cycle if idx < numCourses
}
```

---

## Shortest Path Algorithms

### Dijkstra's — Weighted Graph, No Negative Edges

```java
// O((V + E) log V) with PriorityQueue
int[] dijkstra(int src, List<int[]>[] graph, int n) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // {node, dist}
    pq.offer(new int[]{src, 0});
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int u = curr[0], d = curr[1];
        if (d > dist[u]) continue; // already found shorter path
        
        for (int[] edge : graph[u]) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                pq.offer(new int[]{v, dist[v]});
            }
        }
    }
    return dist;
}

// Network Delay Time (LC 743) ⭐
```

### Bellman-Ford — Handles Negative Edges

```java
// O(V * E) — detect negative cycles too
int[] bellmanFord(int src, int[][] edges, int n) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    
    for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
            }
        }
    }
    return dist;
}
```

---

## Cycle Detection

```java
// Undirected graph: DFS, if we visit a node that's already visited and it's not the parent
boolean hasCycleUndirected(int node, int parent, List<List<Integer>> graph, boolean[] visited) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            if (hasCycleUndirected(neighbor, node, graph, visited)) return true;
        } else if (neighbor != parent) {
            return true;
        }
    }
    return false;
}

// Directed graph: DFS with 3 colors (white/gray/black)
// Course Schedule (LC 207) ⭐
boolean hasCycleDirected(int node, List<List<Integer>> graph, int[] color) {
    color[node] = 1; // gray: being processed
    for (int neighbor : graph.get(node)) {
        if (color[neighbor] == 1) return true; // back edge = cycle
        if (color[neighbor] == 0 && hasCycleDirected(neighbor, graph, color)) return true;
    }
    color[node] = 2; // black: done
    return false;
}
```

---

## Multi-Source BFS

```java
// Rotting Oranges (LC 994) ⭐ — start BFS from ALL rotten oranges simultaneously
public int orangesRotting(int[][] grid) {
    Queue<int[]> queue = new ArrayDeque<>();
    int fresh = 0;
    
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == 2) queue.offer(new int[]{i, j});
            if (grid[i][j] == 1) fresh++;
        }
    }
    
    if (fresh == 0) return 0;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int minutes = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] cell = queue.poll();
            for (int[] d : dirs) {
                int nr = cell[0] + d[0], nc = cell[1] + d[1];
                if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length 
                    && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    fresh--;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        minutes++;
    }
    return fresh == 0 ? minutes - 1 : -1;
}
```

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Flood Fill (LC 733) | BFS/DFS on grid |
| 2 | Find if Path Exists (LC 1971) | BFS/DFS basic |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Number of Islands (LC 200) ⭐⭐ | BFS/DFS flood fill |
| 2 | Course Schedule (LC 207) ⭐⭐ | Cycle detection / topo sort |
| 3 | Course Schedule II (LC 210) ⭐ | Topological sort (Kahn's) |
| 4 | Clone Graph (LC 133) ⭐ | BFS/DFS + HashMap |
| 5 | Rotting Oranges (LC 994) ⭐ | Multi-source BFS |
| 6 | Pacific Atlantic Water Flow (LC 417) ⭐ | Reverse BFS from both oceans |
| 7 | Number of Connected Components (LC 323) | DFS/Union-Find |
| 8 | Graph Valid Tree (LC 261) | n-1 edges + connected |
| 9 | Network Delay Time (LC 743) ⭐ | Dijkstra's |
| 10 | Cheapest Flights Within K Stops (LC 787) | Modified Bellman-Ford |
| 11 | Surrounded Regions (LC 130) | BFS from border |
| 12 | Word Ladder (LC 127) ⭐ | BFS on word graph |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Word Ladder II (LC 126) | BFS + backtrack paths |
| 2 | Alien Dictionary (LC 269) ⭐ | Topo sort on characters |
| 3 | Shortest Path in Grid with Obstacles (LC 1293) | BFS + state (row, col, k) |
| 4 | Minimum Cost to Make at Least One Valid Path (LC 1368) | 0-1 BFS |
| 5 | Critical Connections in Network (LC 1192) | Tarjan's bridges |

---

*Next: [Trie — Prefix-Based Thinking](03-trie.md)*
