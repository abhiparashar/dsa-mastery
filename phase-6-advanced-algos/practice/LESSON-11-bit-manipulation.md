# Lesson 11: Bit Manipulation — XOR is Your Best Friend

> Most bit problems use XOR. `a ^ a = 0`, `a ^ 0 = a`. If you XOR all elements and one appears once, you get it for free.

---

## Essential Bit Operations

```java
// Check if bit k is set
(n >> k) & 1

// Set bit k
n | (1 << k)

// Clear bit k
n & ~(1 << k)

// Toggle bit k
n ^ (1 << k)

// Check power of 2
n > 0 && (n & (n - 1)) == 0

// Count set bits (Brian Kernighan's)
int count = 0;
while (n != 0) { n &= (n - 1); count++; }

// Lowest set bit
n & (-n)
```

## XOR Properties

- `a ^ a = 0` (cancel out)
- `a ^ 0 = a` (identity)
- Commutative and associative
- XOR of all elements from 1 to n: pattern repeats every 4

## Problems

**1. Single Number (LC 136)** — EASY ⭐  
**2. Counting Bits (LC 338)** — EASY ⭐  
**3. Reverse Bits (LC 190)** — EASY ⭐  
