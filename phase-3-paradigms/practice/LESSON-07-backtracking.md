# Lesson 7: Recursion & Backtracking — Generate All Solutions

> Backtracking = DFS on a decision tree with undo. Three foundational problems: Subsets, Permutations, Combination Sum. Every backtracking problem is a variant.

---

## The Template

```java
void backtrack(result, path, start/state) {
    if (complete) { result.add(new ArrayList<>(path)); return; }
    for (choice in choices) {
        if (!valid(choice)) continue;  // prune
        path.add(choice);              // choose
        backtrack(result, path, next); // explore
        path.remove(path.size() - 1); // undo
    }
}
```

## Problems

**1. Subsets (LC 78)** — MEDIUM ⭐⭐: Include/exclude each element  
**2. Permutations (LC 46)** — MEDIUM ⭐⭐: Used array tracking  
**3. Combination Sum (LC 39)** — MEDIUM ⭐⭐: Unlimited reuse  
**4. Generate Parentheses (LC 22)** — MEDIUM ⭐⭐: Constraint-based  
**5. Word Search (LC 79)** — MEDIUM ⭐⭐: Grid DFS + backtrack  
**6. N-Queens (LC 51)** — HARD ⭐: Row-by-row placement  

See Java files and full theory in `phase-3-paradigms/04-recursion-backtracking.md`.
