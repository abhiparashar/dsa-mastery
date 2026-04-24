# Phase 1.3: Math & Number Theory Basics

> **First Principle**: Most "math" problems in interviews aren't about knowing formulas — they're about recognizing that a mathematical property eliminates the need for brute force. GCD, modular arithmetic, and prime factorization are tools that turn O(n) into O(log n) or O(√n).

---

## Why Math Matters for DSA

Math problems show up in ~15% of interviews, but mathematical *thinking* shows up in 100%. When you realize "the answer must be one of two values" or "this reduces to counting pairs," that's math.

---

## Essential Math Concepts

### 1. GCD and LCM (Euclidean Algorithm)

**First Principle**: GCD(a, b) — the largest number that divides both a and b.

**Why the Euclidean algorithm works**: If `a = b * q + r`, then any number that divides both `a` and `b` must also divide `r`. So `GCD(a, b) = GCD(b, a % b)`.

```java
// O(log(min(a, b))) — each step reduces the larger number by at least half
int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

// LCM = (a * b) / GCD(a, b) — use long to avoid overflow
long lcm(long a, long b) {
    return a / gcd(a, b) * b; // divide first to prevent overflow
}
```

### 2. Modular Arithmetic

**Why it matters**: Many problems say "return answer mod 10^9 + 7" — because the answer is astronomically large.

```java
static final int MOD = 1_000_000_007;

// Key properties:
// (a + b) % MOD = ((a % MOD) + (b % MOD)) % MOD
// (a * b) % MOD = ((a % MOD) * (b % MOD)) % MOD
// (a - b) % MOD = ((a % MOD) - (b % MOD) + MOD) % MOD  ← add MOD to handle negative

// Fast exponentiation: a^n mod m in O(log n)
long power(long base, long exp, long mod) {
    long result = 1;
    base %= mod;
    while (exp > 0) {
        if ((exp & 1) == 1) {
            result = result * base % mod;
        }
        exp >>= 1;
        base = base * base % mod;
    }
    return result;
}
```

### 3. Prime Numbers

**Sieve of Eratosthenes** — find all primes up to n in O(n log log n):

```java
boolean[] sieve(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    
    for (int i = 2; i * i <= n; i++) {
        if (isPrime[i]) {
            for (int j = i * i; j <= n; j += i) {
                isPrime[j] = false;
            }
        }
    }
    return isPrime;
}

// Check if single number is prime: O(√n)
boolean isPrime(int n) {
    if (n < 2) return false;
    if (n < 4) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    for (int i = 5; i * i <= n; i += 6) {
        if (n % i == 0 || n % (i + 2) == 0) return false;
    }
    return true;
}
```

**Why √n?** If n has a factor > √n, it must also have a factor < √n. So we only need to check up to √n.

### 4. Counting and Combinatorics

```java
// nCr — combinations
// Use Pascal's triangle approach to avoid overflow
long[][] buildPascal(int n) {
    long[][] C = new long[n + 1][n + 1];
    for (int i = 0; i <= n; i++) {
        C[i][0] = 1;
        for (int j = 1; j <= i; j++) {
            C[i][j] = C[i-1][j-1] + C[i-1][j];
        }
    }
    return C;
}

// With modular arithmetic
long nCr(int n, int r, long mod) {
    if (r > n) return 0;
    long num = 1, den = 1;
    for (int i = 0; i < r; i++) {
        num = num * (n - i) % mod;
        den = den * (i + 1) % mod;
    }
    return num * power(den, mod - 2, mod) % mod; // Fermat's little theorem for modular inverse
}
```

### 5. Integer Properties

```java
// Count digits
int digits = (n == 0) ? 1 : (int) Math.log10(Math.abs(n)) + 1;

// Reverse a number
int reverse(int x) {
    int rev = 0;
    while (x != 0) {
        int digit = x % 10;
        // Overflow check
        if (rev > Integer.MAX_VALUE / 10 || rev < Integer.MIN_VALUE / 10) return 0;
        rev = rev * 10 + digit;
        x /= 10;
    }
    return rev;
}

// Check if power of 2
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

---

## Pattern Recognition — How to Spot Math Problems

| Signal in Problem | Math Concept | Example |
|---|---|---|
| "divisible by" | GCD/LCM | Water jug problem |
| "mod 10^9+7" | Modular arithmetic | Count arrangements |
| "prime" or "factors" | Sieve / factorization | Count primes |
| "permutations" or "combinations" | Combinatorics | Unique paths |
| "power of X" | Bit manipulation or log | Power of two/three |
| "palindrome number" | Digit reversal | Palindrome check |
| "overflow" | Long or careful math | Reverse integer |

---

## Edge Cases

| Case | Trap |
|------|------|
| n = 0 | Division by zero, log(0) undefined |
| n = 1 | Often a special case (1 is not prime) |
| Negative numbers | -7 % 3 = -1 in Java (not 2!) |
| Integer.MIN_VALUE | Math.abs(Integer.MIN_VALUE) overflows! |
| Large products | Use long: `(long) a * b` |

---

## Problem Set

### Easy
| # | Problem | Key Insight |
|---|---------|-------------|
| 1 | Palindrome Number (LC 9) | Reverse half the number |
| 2 | Power of Two (LC 231) | Bit trick: n & (n-1) == 0 |
| 3 | Count Primes (LC 204) | Sieve of Eratosthenes |
| 4 | Happy Number (LC 202) | Floyd's cycle detection on digit sum |
| 5 | Excel Sheet Column Number (LC 171) | Base-26 conversion |
| 6 | Fizz Buzz (LC 412) | Modulo basics |

### Medium
| # | Problem | Key Insight |
|---|---------|-------------|
| 1 | Reverse Integer (LC 7) ⭐ | Overflow check before multiply |
| 2 | Pow(x, n) (LC 50) ⭐ | Fast exponentiation, handle negative n |
| 3 | Unique Paths (LC 62) | Combinatorics: C(m+n-2, m-1) |
| 4 | Multiply Strings (LC 43) | Digit-by-digit multiplication |
| 5 | Fraction to Recurring Decimal (LC 166) ⭐ | Long division + cycle detection |

### Hard
| # | Problem | Key Insight |
|---|---------|-------------|
| 1 | Max Points on a Line (LC 149) | GCD for slope representation |
| 2 | Integer to English Words (LC 273) ⭐ | Systematic decomposition |
| 3 | Basic Calculator (LC 224) | Stack + recursion for parentheses |

---

## Interviewer's Perspective

Math problems are polarizing — candidates either love them or panic. What I look for:

1. **Don't panic.** Most "math" problems have 3-5 lines of actual math. The rest is coding.
2. **Think about representation.** How do you represent a fraction? A large number? This matters more than the math itself.
3. **Overflow awareness is mandatory.** If you multiply two ints without checking overflow, that's a yellow flag at Meta.

---

*Next: Phase 2 — [LinkedList — Pointer Manipulation](../phase-2-core-ds/01-linkedlist.md)*
