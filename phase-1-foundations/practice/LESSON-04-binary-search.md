# Lesson 4: Binary Search — Beyond Sorted Arrays

> **Core insight**: Binary search isn't "find target in sorted array." It's "find the boundary where a predicate changes from false to true." This reframing unlocks an entire class of problems.

---

## The Two Templates (Use These Every Time)

### Template 1: Find Leftmost True

```java
int lo = LEFT_BOUND, hi = RIGHT_BOUND;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (condition(mid)) hi = mid;     // mid might be answer
    else lo = mid + 1;                // mid is too small
}
return lo; // lo == hi == answer
```

### Template 2: Find Rightmost True

```java
int lo = LEFT_BOUND, hi = RIGHT_BOUND;
while (lo < hi) {
    int mid = lo + (hi - lo + 1) / 2; // CEILING to avoid infinite loop
    if (condition(mid)) lo = mid;      // mid might be answer
    else hi = mid - 1;                 // mid is too big
}
return lo;
```

**Rule**: Always use `lo < hi` (not `<=`). When loop ends, `lo == hi` = answer.

---

## Category 1: Search in Sorted Array

Standard binary search, first/last occurrence, rotated arrays.

## Category 2: Binary Search on Answer Space

**The power move.** When you can't search the input directly but can check "is X feasible?" — binary search on X.

Pattern:
```
lo = minimum possible answer
hi = maximum possible answer
while (lo < hi) {
    mid = (lo + hi) / 2
    if (feasible(mid)) → search for smaller answer
    else → need bigger answer
}
```

**Signal phrases**: "Minimize the maximum", "Maximum the minimum", "Minimum speed such that..."

---

## Problems to Solve

**1. Binary Search (LC 704)** — EASY: Basic template  
**2. First Bad Version (LC 278)** — EASY: Leftmost true  
**3. Search in Rotated Sorted Array (LC 33)** — MEDIUM ⭐⭐: Identify sorted half  
**4. Find Minimum in Rotated Array (LC 153)** — MEDIUM ⭐: Compare mid with right  
**5. Koko Eating Bananas (LC 875)** — MEDIUM ⭐: BS on answer  
**6. Median of Two Sorted Arrays (LC 4)** — HARD ⭐⭐: BS on partition  

See Java files for full solutions with tests.

---

*Full theory in `phase-3-paradigms/02-binary-search.md`. This lesson focuses on practice.*
