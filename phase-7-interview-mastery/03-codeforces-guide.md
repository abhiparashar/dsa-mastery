# Phase 7.3: Codeforces Transition Guide — From Interviews to Competitive Programming

> Interviews and CP are different games. Interviews test communication + medium-difficulty coding. CP tests raw problem-solving speed + mathematical thinking at extreme difficulty. Here's how to bridge the gap.

---

## Interview Skills → CP Skills

| Interview Skill | CP Equivalent | Gap to Fill |
|---|---|---|
| HashMap, two pointers, sliding window | Same | You're ready |
| Binary search on sorted array | BS on answer space, BS on doubles | Need more advanced BS |
| Basic DP (1D, 2D) | DP with bitmask, digit DP, tree DP | Significant gap |
| BFS/DFS on graphs | Advanced graph (flows, bridges, SCC) | Large gap |
| Clean code, variable names | Speed, template-based code | Different priority |
| Communication | NA (solo) | Non-transferable |

---

## Codeforces Rating Progression Plan

### Newbie → Pupil (800-1200): Weeks 1-4
**Focus**: Implementation, greedy, basic math, simple simulation

- Solve Div 3 A-C problems
- Topics: if/else, loops, arrays, basic sorting, basic math (GCD, parity)
- **Goal**: 100+ problems at this level

### Pupil → Specialist (1200-1400): Weeks 5-10
**Focus**: Binary search, two pointers, prefix sums, basic DP, basic graphs

- Solve Div 2 A-B, Div 3 D-E
- Topics: BS on answer, BFS, DFS, basic DP, frequency counting
- **Goal**: Comfortable with 1200-rated problems in < 20 min

### Specialist → Expert (1400-1600): Weeks 11-20
**Focus**: DP variations, advanced binary search, combinatorics, number theory

- Solve Div 2 B-C
- Topics: Segment tree, DSU, bitmask, modular arithmetic, LIS
- **Goal**: Solve most 1400-rated problems, some 1600

### Expert → Candidate Master (1600-1900): Months 5-8
**Focus**: Advanced DP, graph algorithms, advanced math

- Solve Div 2 C-D, Div 1 A-B
- Topics: Flows, bridges, SCC, centroid decomposition, digit DP, convex hull trick

### CM → Master → Grandmaster (1900+): Months 9+
**Focus**: Original problem solving, speed, advanced techniques

---

## HFT-Specific Preparation

HFT companies (Citadel, Jane Street, Two Sigma, HRT, Jump Trading) value:

### Technical
1. **Speed**: Solve medium problems in < 10 minutes
2. **Math**: Probability, expected value, combinatorics
3. **Advanced Data Structures**: Segment trees, Fenwick trees, order statistics
4. **Bit manipulation**: Essential for hardware-adjacent thinking
5. **C++ speed tricks** (though some accept Java)

### Problem Types Asked at HFT
| Type | Example |
|------|---------|
| Probability puzzles | "Expected number of coin flips to get 3 heads in a row" |
| Game theory | Nim, Sprague-Grundy |
| Optimization under constraints | Greedy + math |
| Brain teasers with math proofs | "Prove this greedy approach is optimal" |
| Speed-critical implementation | "Solve this in O(n) not O(n log n)" |

---

## CP Java Template

```java
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
    
    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine().trim());
        while (t-- > 0) {
            solve();
        }
        out.flush();
        out.close();
    }
    
    static void solve() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        // Your solution here
        
        out.println("answer");
    }
    
    static int[] readArray(int n) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());
        return arr;
    }
}
```

---

## Daily CP Routine

```
[20 min] Virtual contest on Codeforces (past Div 2/3)
[40 min] Upsolve: solve problems you couldn't during contest
[20 min] Read editorials for problems 1-2 levels above your rating
[10 min] Add new patterns to your notes

Weekly: Participate in at least one live contest
Monthly: Review all problems you couldn't solve — patterns should click now
```

---

## Resources

| Resource | What For |
|----------|----------|
| Codeforces.com | Contests + problem archive |
| CSES Problem Set | 300 curated problems covering all CP topics |
| CP-Algorithms (cp-algorithms.com) | Algorithm reference |
| Competitive Programmer's Handbook (Laaksonen) | Free PDF, great intro |
| Atcoder Beginner Contests | Clean problems, good for practice |
| USACO Guide | Structured learning path |

---

## The Path to Grandmaster

```
Rating 800    → Learn to code solutions (implementation)
Rating 1200   → Learn to think in patterns (pattern matching)
Rating 1400   → Learn to combine patterns (multi-step problems)
Rating 1600   → Learn to derive approaches (original thinking)
Rating 1900   → Learn to prove correctness (mathematical rigor)
Rating 2100   → Master speed + accuracy (no wrong submissions)
Rating 2400+  → Original insight under time pressure (grandmaster territory)
```

Each level is roughly 2x harder than the previous. The jump from 1600 to 1900 is where most people plateau. Breaking through requires solving problems ABOVE your level consistently and learning from failures.

---

*This is the complete roadmap. Start with Phase 1 and build forward. Remember: the struggle is the learning. Don't skip the hard parts.*
