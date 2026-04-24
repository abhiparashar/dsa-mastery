# Phase 6.4: Advanced Math & Number Theory (CF/HFT Prep)

> These topics bridge interviews to competitive programming. Essential for Codeforces rated contests and HFT companies that test math-heavy problem solving.

---

## Topics for CP / HFT

### Modular Inverse (for nCr mod p)
```java
// Fermat's little theorem: a^(-1) ≡ a^(p-2) mod p (when p is prime)
long modInverse(long a, long mod) {
    return power(a, mod - 2, mod);
}

// Precompute factorials and inverse factorials for fast nCr
long[] fact, invFact;
void precompute(int n, long mod) {
    fact = new long[n + 1];
    invFact = new long[n + 1];
    fact[0] = 1;
    for (int i = 1; i <= n; i++) fact[i] = fact[i-1] * i % mod;
    invFact[n] = modInverse(fact[n], mod);
    for (int i = n - 1; i >= 0; i--) invFact[i] = invFact[i+1] * (i+1) % mod;
}

long nCr(int n, int r, long mod) {
    if (r < 0 || r > n) return 0;
    return fact[n] % mod * invFact[r] % mod * invFact[n-r] % mod;
}
```

### Matrix Exponentiation (for linear recurrences)
```java
// Compute F(n) in O(log n) using matrix [[1,1],[1,0]]^n
long[][] matMul(long[][] A, long[][] B, long mod) {
    int n = A.length;
    long[][] C = new long[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            for (int k = 0; k < n; k++)
                C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % mod;
    return C;
}

long[][] matPow(long[][] M, long p, long mod) {
    int n = M.length;
    long[][] result = new long[n][n];
    for (int i = 0; i < n; i++) result[i][i] = 1; // identity
    while (p > 0) {
        if ((p & 1) == 1) result = matMul(result, M, mod);
        M = matMul(M, M, mod);
        p >>= 1;
    }
    return result;
}
```

### Inclusion-Exclusion Principle
```
|A ∪ B ∪ C| = |A| + |B| + |C| - |A∩B| - |A∩C| - |B∩C| + |A∩B∩C|
```
Used for counting problems with multiple constraints.

### Chinese Remainder Theorem
Solve system of congruences: x ≡ a₁ (mod m₁), x ≡ a₂ (mod m₂), ...

---

## Problem Set (CF-oriented)

| # | Problem | Concept |
|---|---------|---------|
| 1 | Pow(x, n) (LC 50) | Fast exponentiation |
| 2 | Super Pow (LC 372) | Modular exponentiation |
| 3 | Count Primes (LC 204) | Sieve |
| 4 | Ugly Number II (LC 264) | Min-heap/DP |
| 5 | Perfect Squares (LC 279) | BFS or DP (Lagrange's theorem) |
| 6 | Nth Digit (LC 400) | Math pattern |

---

*Next: Phase 7 — [Problem Recognition System](../phase-7-interview-mastery/01-recognition-system.md)*
