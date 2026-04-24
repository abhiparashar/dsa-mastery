# Phase 6.3: Bit Manipulation

> **First Principle**: Every integer is a sequence of bits. Bit operations (AND, OR, XOR, shift) operate on all bits simultaneously in O(1). When a problem involves pairs, toggles, or "find the unique element," bits are your friend.

---

## Essential Bit Operations

```java
// XOR properties (the most useful):
// a ^ a = 0        (cancels out)
// a ^ 0 = a        (identity)
// a ^ b = b ^ a    (commutative)
// (a ^ b) ^ c = a ^ (b ^ c)  (associative)

// Single Number (LC 136) ⭐ — find the one element that appears once
public int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) result ^= num;
    return result;
}
// All pairs cancel out via XOR, leaving the single element.

// Counting Bits (LC 338)
public int[] countBits(int n) {
    int[] ans = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        ans[i] = ans[i >> 1] + (i & 1);
    }
    return ans;
}

// Power of Two: n & (n - 1) == 0 (only one bit set)
// Number of 1 bits: Integer.bitCount(n) or Brian Kernighan's trick
int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1); // clears lowest set bit
        count++;
    }
    return count;
}

// Subsets via bitmask (alternative to backtracking)
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    for (int mask = 0; mask < (1 << nums.length); mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if ((mask & (1 << i)) != 0) subset.add(nums[i]);
        }
        result.add(subset);
    }
    return result;
}
```

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Single Number (LC 136) ⭐ | XOR all |
| 2 | Number of 1 Bits (LC 191) | Brian Kernighan |
| 3 | Power of Two (LC 231) | n & (n-1) |
| 4 | Counting Bits (LC 338) | DP on bits |
| 5 | Reverse Bits (LC 190) | Bit by bit |
| 6 | Missing Number (LC 268) | XOR or math |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Single Number II (LC 137) | Count bits mod 3 |
| 2 | Single Number III (LC 260) | XOR + split by bit |
| 3 | Bitwise AND of Range (LC 201) | Common prefix |
| 4 | Maximum XOR of Two Numbers (LC 421) | Bitwise trie |
| 5 | Subsets (LC 78) | Bitmask enumeration |

---

*Next: [Advanced Math](04-advanced-math.md)*
