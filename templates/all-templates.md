# All Java Templates — Quick Reference

> Print this. Keep it next to your desk. Before every problem, check which template applies.

---

## 1. Binary Search

```java
// Find leftmost true (lower bound)
int lo = 0, hi = n;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (condition(mid)) hi = mid;
    else lo = mid + 1;
}
return lo;

// Find rightmost true (upper bound)
int lo = 0, hi = n;
while (lo < hi) {
    int mid = lo + (hi - lo + 1) / 2; // ceiling!
    if (condition(mid)) lo = mid;
    else hi = mid - 1;
}
return lo;

// Binary search on answer
int lo = MIN_ANSWER, hi = MAX_ANSWER;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (feasible(mid)) hi = mid;
    else lo = mid + 1;
}
return lo;
```

## 2. Sliding Window (Variable Size)

```java
int left = 0;
for (int right = 0; right < n; right++) {
    // expand: add nums[right] to window
    while (windowInvalid()) {
        // shrink: remove nums[left]
        left++;
    }
    answer = Math.max(answer, right - left + 1);
}
```

## 3. Two Pointers (Converging)

```java
int lo = 0, hi = n - 1;
while (lo < hi) {
    if (condition) lo++;
    else hi--;
}
```

## 4. BFS

```java
Queue<T> queue = new ArrayDeque<>();
Set<T> visited = new HashSet<>();
queue.offer(start);
visited.add(start);
int level = 0;

while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        T curr = queue.poll();
        if (isGoal(curr)) return level;
        for (T next : neighbors(curr)) {
            if (visited.add(next)) queue.offer(next);
        }
    }
    level++;
}
```

## 5. DFS (Recursive)

```java
void dfs(int node, boolean[] visited) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) dfs(neighbor, visited);
    }
}
```

## 6. Topological Sort (Kahn's BFS)

```java
int[] inDegree = new int[n];
// build graph and compute in-degrees
Queue<Integer> queue = new ArrayDeque<>();
for (int i = 0; i < n; i++) if (inDegree[i] == 0) queue.offer(i);
List<Integer> order = new ArrayList<>();
while (!queue.isEmpty()) {
    int node = queue.poll();
    order.add(node);
    for (int next : graph.get(node)) {
        if (--inDegree[next] == 0) queue.offer(next);
    }
}
// order.size() == n means no cycle
```

## 7. Backtracking

```java
void backtrack(List<List<Integer>> result, List<Integer> path, int start) {
    if (isComplete(path)) {
        result.add(new ArrayList<>(path));
        return;
    }
    for (int i = start; i < n; i++) {
        if (!isValid(i)) continue;
        path.add(nums[i]);
        backtrack(result, path, i + 1); // i+1 for subsets, i for reuse, 0 for permutations
        path.remove(path.size() - 1);
    }
}
```

## 8. Union-Find

```java
int[] parent, rank;
void init(int n) {
    parent = new int[n]; rank = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
}
int find(int x) {
    if (parent[x] != x) parent[x] = find(parent[x]);
    return parent[x];
}
boolean union(int x, int y) {
    int px = find(x), py = find(y);
    if (px == py) return false;
    if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
    parent[py] = px;
    if (rank[px] == rank[py]) rank[px]++;
    return true;
}
```

## 9. Trie

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}
void insert(TrieNode root, String word) {
    TrieNode node = root;
    for (char c : word.toCharArray()) {
        int i = c - 'a';
        if (node.children[i] == null) node.children[i] = new TrieNode();
        node = node.children[i];
    }
    node.isEnd = true;
}
```

## 10. Monotonic Stack

```java
Deque<Integer> stack = new ArrayDeque<>(); // stores indices
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
        int idx = stack.pop();
        result[idx] = nums[i]; // or i - idx for distance
    }
    stack.push(i);
}
```

## 11. Prefix Sum

```java
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];
// sum(i, j) = prefix[j + 1] - prefix[i]
```

## 12. Kadane's Algorithm

```java
int currMax = nums[0], globalMax = nums[0];
for (int i = 1; i < n; i++) {
    currMax = Math.max(nums[i], currMax + nums[i]);
    globalMax = Math.max(globalMax, currMax);
}
```

## 13. Linked List Reverse

```java
ListNode prev = null, curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev;
```

## 14. Tree DFS (Bottom-Up)

```java
int dfs(TreeNode node) {
    if (node == null) return 0;
    int left = dfs(node.left);
    int right = dfs(node.right);
    // combine left and right with current node
    return result;
}
```

## 15. Dijkstra's Shortest Path

```java
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[src] = 0;
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
pq.offer(new int[]{src, 0});
while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int u = curr[0], d = curr[1];
    if (d > dist[u]) continue;
    for (int[] edge : graph[u]) {
        int v = edge[0], w = edge[1];
        if (dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
            pq.offer(new int[]{v, dist[v]});
        }
    }
}
```

## 16. GCD

```java
int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }
```

## 17. Fast Power

```java
long power(long base, long exp, long mod) {
    long result = 1;
    base %= mod;
    while (exp > 0) {
        if ((exp & 1) == 1) result = result * base % mod;
        exp >>= 1;
        base = base * base % mod;
    }
    return result;
}
```

## 18. Sieve of Eratosthenes

```java
boolean[] isPrime = new boolean[n + 1];
Arrays.fill(isPrime, true);
isPrime[0] = isPrime[1] = false;
for (int i = 2; i * i <= n; i++) {
    if (isPrime[i]) {
        for (int j = i * i; j <= n; j += i) isPrime[j] = false;
    }
}
```

## 19. Binary Indexed Tree (Fenwick)

```java
int[] bit = new int[n + 1];
void update(int i, int delta) { for (i++; i <= n; i += i & (-i)) bit[i] += delta; }
int query(int i) { int s = 0; for (i++; i > 0; i -= i & (-i)) s += bit[i]; return s; }
```

## 20. Grid Directions

```java
int[][] dirs4 = {{0,1},{0,-1},{1,0},{-1,0}};
int[][] dirs8 = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
```

---

*Keep this file as your quick reference. Before every problem: identify the pattern → grab the template → adapt.*
