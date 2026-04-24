# Phase 4.3: Trie — Prefix-Based Thinking

> **First Principle**: A Trie (prefix tree) stores strings character by character in a tree structure where shared prefixes share the same path. This gives O(L) lookup/insert (L = word length) regardless of how many words are stored. When you need "all words starting with X" or "longest common prefix," a Trie is the answer.

---

## When to Think Trie

| Signal | Why Trie |
|--------|----------|
| "Find all words with prefix X" | Trie naturally groups by prefix |
| "Autocomplete" | Walk the trie to find suggestions |
| "Word search in grid with dictionary" | Trie + DFS is the standard approach |
| "Longest common prefix" | Trie until branching occurs |
| "XOR maximum/minimum" | Bitwise Trie (bit by bit) |

---

## Implementation

```java
class Trie {
    private Trie[] children = new Trie[26];
    private boolean isEnd = false;
    
    public void insert(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new Trie();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }
    
    public boolean search(String word) {
        Trie node = find(word);
        return node != null && node.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        return find(prefix) != null;
    }
    
    private Trie find(String s) {
        Trie node = this;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return null;
            node = node.children[idx];
        }
        return node;
    }
}
```

---

## Classic Problems

```java
// Word Search II (LC 212) ⭐ — Trie + Backtracking
public List<String> findWords(char[][] board, String[] words) {
    Trie root = new Trie();
    for (String word : words) root.insert(word);
    
    List<String> result = new ArrayList<>();
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs(board, i, j, root, result);
        }
    }
    return result;
}

void dfs(char[][] board, int i, int j, Trie node, List<String> result) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
    char c = board[i][j];
    if (c == '#' || node.children[c - 'a'] == null) return;
    
    node = node.children[c - 'a'];
    if (node.isEnd) {
        result.add(node.word); // store word at end node
        node.isEnd = false;    // dedup
    }
    
    board[i][j] = '#';
    dfs(board, i + 1, j, node, result);
    dfs(board, i - 1, j, node, result);
    dfs(board, i, j + 1, node, result);
    dfs(board, i, j - 1, node, result);
    board[i][j] = c;
}

// Design Add and Search Words (LC 211) ⭐ — Trie with '.' wildcard
// '.' matches any character → need to branch at '.' positions
boolean searchWithDot(Trie node, String word, int idx) {
    if (idx == word.length()) return node.isEnd;
    char c = word.charAt(idx);
    if (c == '.') {
        for (Trie child : node.children) {
            if (child != null && searchWithDot(child, word, idx + 1)) return true;
        }
        return false;
    }
    if (node.children[c - 'a'] == null) return false;
    return searchWithDot(node.children[c - 'a'], word, idx + 1);
}
```

---

## Problem Set

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Implement Trie (LC 208) ⭐ | Basic implementation |
| 2 | Design Add and Search Words (LC 211) ⭐ | Trie + DFS for wildcard |
| 3 | Replace Words (LC 648) | Prefix replacement |
| 4 | Map Sum Pairs (LC 677) | Trie with values |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Word Search II (LC 212) ⭐⭐ | Trie + grid backtracking |
| 2 | Palindrome Pairs (LC 336) | Trie + palindrome check |
| 3 | Maximum XOR of Two Numbers (LC 421) | Bitwise Trie |

---

*Next: [Union-Find / Disjoint Set](04-union-find.md)*
