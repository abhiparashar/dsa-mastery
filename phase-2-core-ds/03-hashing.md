# Phase 2.3: HashMap & HashSet — O(1) Lookup Power

> **First Principle**: A hash map converts a *key* to a *number* (hash), then uses that number as an array index. This gives O(1) average lookup. The cost? No ordering, and worst-case O(n) with collisions. But in practice, hashing is the single most useful tool in your arsenal — it turns O(n) searches into O(1) lookups.

---

## How Hashing Actually Works

```
Key: "hello" → hash function → 42 → array[42] = value

The hash function must be:
1. Deterministic: same key → same hash
2. Uniform: distribute keys evenly across buckets
3. Fast: O(1) to compute
```

### Java Internals You Should Know

```java
// HashMap: key → value mapping
Map<String, Integer> map = new HashMap<>();
map.put("key", 1);           // O(1) avg
map.get("key");               // O(1) avg, returns null if missing
map.getOrDefault("key", 0);   // O(1) avg, returns default if missing
map.containsKey("key");       // O(1) avg
map.remove("key");            // O(1) avg
map.size();
map.isEmpty();

// Iteration
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    int val = entry.getValue();
}
for (String key : map.keySet()) { ... }
for (int val : map.values()) { ... }

// Powerful methods
map.merge("key", 1, Integer::sum);         // increment or set to 1
map.computeIfAbsent("key", k -> new ArrayList<>()).add(val); // lazy init
map.putIfAbsent("key", 0);

// HashSet: just keys, no values
Set<String> set = new HashSet<>();
set.add("hello");     // O(1) avg
set.contains("hello"); // O(1) avg
set.remove("hello");   // O(1) avg

// LinkedHashMap: maintains insertion order
Map<String, Integer> lhm = new LinkedHashMap<>();

// TreeMap: maintains sorted order, O(log n) operations
TreeMap<Integer, String> tm = new TreeMap<>();
tm.floorKey(5);    // largest key ≤ 5
tm.ceilingKey(5);  // smallest key ≥ 5
tm.firstKey();     // minimum key
tm.lastKey();      // maximum key
```

---

## Core Patterns

### Pattern 1: Frequency Counting

**The bread and butter. Learn this first.**

```java
// Count frequency of elements
Map<Integer, Integer> freq = new HashMap<>();
for (int num : nums) {
    freq.merge(num, 1, Integer::sum);
}

// For lowercase letters only — use array instead (faster)
int[] count = new int[26];
for (char c : s.toCharArray()) {
    count[c - 'a']++;
}
```

### Pattern 2: Two Sum Pattern (Complement Lookup)

**The Idea**: Instead of searching for a pair, search for the *complement* of each element.

```java
// Two Sum (LC 1) ⭐⭐ — THE most classic problem
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (seen.containsKey(complement)) {
            return new int[]{seen.get(complement), i};
        }
        seen.put(nums[i], i);
    }
    return new int[]{};
}
// Why it works: for each number, we check if we've already seen its complement.
// One pass. O(n) time, O(n) space.
```

### Pattern 3: Grouping by Key

**The Idea**: Use a characteristic as the key to group related items.

```java
// Group Anagrams (LC 49) ⭐⭐ — Meta favorite
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }
    return new ArrayList<>(map.values());
}

// Alternative key: frequency count string "2a1b3c" — O(n*k) vs O(n*k*log k)
```

### Pattern 4: Existence / Deduplication

```java
// Contains Duplicate (LC 217)
public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) return true; // add returns false if already present
    }
    return false;
}

// Longest Consecutive Sequence (LC 128) ⭐ — Google favorite
public int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);
    
    int maxLen = 0;
    for (int num : set) {
        // Only start counting from the beginning of a sequence
        if (!set.contains(num - 1)) {
            int len = 1;
            while (set.contains(num + len)) len++;
            maxLen = Math.max(maxLen, len);
        }
    }
    return maxLen;
}
// Why O(n)? Each number is visited at most twice (once in outer loop, once in while).
```

### Pattern 5: Prefix Sum + HashMap

**Already covered in Arrays, but it's fundamentally a hashing pattern.**

```java
// Subarray Sum Equals K (LC 560) ⭐⭐
// "How many subarrays sum to k?"
// Key: prefix[j] - prefix[i] = k → count prefix sums seen before
```

### Pattern 6: Sliding Window + HashMap

```java
// Minimum Window Substring (LC 76) ⭐⭐
// Covered in Phase 3, but the data structure powering it is a HashMap
// tracking character frequencies in the window.
```

---

## Choosing the Right Map

| Need | Data Structure | Why |
|------|---------------|-----|
| O(1) lookup, no order needed | HashMap | Default choice |
| Maintain insertion order | LinkedHashMap | LRU cache, ordered iteration |
| Maintain sorted order | TreeMap | Range queries, floor/ceiling |
| O(1) lookup, keys only | HashSet | Existence checks |
| Sorted keys, keys only | TreeSet | Sorted dedup, range operations |

---

## Custom Hashing — When Default Isn't Enough

```java
// Problem: Group points by their slope from origin
// Slope = y/x, but floating point is imprecise!
// Solution: Store as reduced fraction using GCD

String slopeKey(int dy, int dx) {
    if (dx == 0) return "inf";
    if (dy == 0) return "0";
    int sign = (dy < 0) ^ (dx < 0) ? -1 : 1;
    dy = Math.abs(dy);
    dx = Math.abs(dx);
    int g = gcd(dy, dx);
    return sign * dy / g + "/" + dx / g;
}
```

---

## Edge Cases

| Case | Trap |
|------|------|
| Null keys | HashMap allows ONE null key, TreeMap doesn't |
| Hash collisions | Worst case O(n), but Java 8+ uses trees for long chains → O(log n) |
| Mutable keys | Never use mutable objects as keys — hash changes after modification! |
| Integer caching | `Integer.valueOf(127) == Integer.valueOf(127)` is true, but `128` is false. Use `.equals()` for Integer comparisons |
| Empty map | `map.get(key)` returns null, not an exception |
| Concurrent modification | Don't modify a map while iterating — use Iterator.remove() |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Two Sum (LC 1) ⭐⭐ | Complement lookup |
| 2 | Contains Duplicate (LC 217) | HashSet existence |
| 3 | Valid Anagram (LC 242) | Frequency count |
| 4 | Ransom Note (LC 383) | Frequency count |
| 5 | Intersection of Two Arrays II (LC 350) | Frequency match |
| 6 | Isomorphic Strings (LC 205) | Bijection mapping |
| 7 | Word Pattern (LC 290) | Bijection mapping |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Group Anagrams (LC 49) ⭐⭐ | Grouping by key |
| 2 | Subarray Sum Equals K (LC 560) ⭐⭐ | Prefix sum + map |
| 3 | Longest Consecutive Sequence (LC 128) ⭐ | Set + sequence start |
| 4 | Top K Frequent Elements (LC 347) ⭐ | Frequency map + bucket sort |
| 5 | Encode and Decode TinyURL (LC 535) | Hash design |
| 6 | 4Sum II (LC 454) ⭐ | Two-map complement |
| 7 | Contiguous Array (LC 525) | Prefix sum variant (0→-1) |
| 8 | Find All Anagrams in String (LC 438) | Sliding window + freq |
| 9 | Longest Substring Without Repeating (LC 3) ⭐⭐ | HashMap last-seen index |
| 10 | Copy List with Random Pointer (LC 138) | Node → clone mapping |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Minimum Window Substring (LC 76) ⭐⭐ | Sliding window + freq |
| 2 | LRU Cache (LC 146) ⭐⭐ | HashMap + Doubly LinkedList |
| 3 | LFU Cache (LC 460) | HashMap + LinkedHashSet |
| 4 | Alien Dictionary (LC 269) ⭐ | HashMap for adjacency + topological sort |
| 5 | Substring with Concatenation of All Words (LC 30) | HashMap frequency match |

---

*Next: [Heap / Priority Queue — Top-K Thinking](04-heap.md)*
