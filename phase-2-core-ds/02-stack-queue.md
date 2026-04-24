# Phase 2.2: Stack & Queue — LIFO/FIFO Thinking

> **First Principle**: A stack processes the *most recent* item first (LIFO). A queue processes the *oldest* item first (FIFO). These aren't just data structures — they're **processing orders**. When your problem needs "match the most recent open bracket" or "process nodes level by level," you've found your tool.

---

## When to Think Stack

Ask yourself: **"Does the answer depend on the most recent unresolved item?"**

- Matching parentheses → most recent open bracket
- Next greater element → most recent element without a greater successor
- Undo operations → most recent action
- Expression evaluation → most recent operator
- DFS traversal → most recent unvisited node

## When to Think Queue

Ask yourself: **"Do I need to process items in the order they arrived?"**

- BFS traversal → process nodes level by level
- Sliding window (deque) → maintain window in order
- Task scheduling → process in arrival order
- Stream processing → first in, first out

---

## Java Implementations

```java
// Stack — use ArrayDeque, NOT Stack class (Stack is legacy, synchronized, slow)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);       // add to top
stack.pop();         // remove from top — throws if empty
stack.peek();        // look at top — throws if empty
stack.isEmpty();     // check empty

// Queue — use ArrayDeque or LinkedList
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(1);      // add to back
queue.poll();        // remove from front — returns null if empty
queue.peek();        // look at front — returns null if empty

// Deque (Double-ended queue) — can act as both stack and queue
Deque<Integer> deque = new ArrayDeque<>();
deque.offerFirst(1); // add to front
deque.offerLast(2);  // add to back
deque.pollFirst();   // remove from front
deque.pollLast();    // remove from back
```

---

## Stack Patterns

### Pattern 1: Valid Parentheses

**The Idea**: Every close bracket must match the most recent unmatched open bracket.

```java
// Valid Parentheses (LC 20) ⭐⭐ — asked everywhere
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(') stack.push(')');
        else if (c == '{') stack.push('}');
        else if (c == '[') stack.push(']');
        else if (stack.isEmpty() || stack.pop() != c) return false;
    }
    return stack.isEmpty();
}
```

**Why this works**: We push the EXPECTED closing bracket. When we see a closing bracket, we just check if it matches what we expect.

### Pattern 2: Monotonic Stack (Next Greater/Smaller Element)

**First Principle**: Maintain a stack where elements are in monotonic order (all increasing or all decreasing). When a new element violates the order, pop elements — each popped element has found its "next greater/smaller."

**This is one of the most powerful patterns in competitive programming.**

```java
// Next Greater Element pattern
// For each element, find the next element that is greater
// Template: O(n) time, O(n) space
int[] nextGreater(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>(); // stores INDICES
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    return result;
}

// Daily Temperatures (LC 739) ⭐ — classic monotonic stack
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int prev = stack.pop();
            answer[prev] = i - prev;
        }
        stack.push(i);
    }
    return answer;
}
```

**Why O(n)?** Each element is pushed once and popped at most once. Total operations = 2n.

### Pattern 3: Calculator / Expression Evaluation

```java
// Basic Calculator II (LC 227) ⭐ — no parentheses
public int calculate(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    int num = 0;
    char op = '+';
    
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isDigit(c)) {
            num = num * 10 + (c - '0');
        }
        if ((!Character.isDigit(c) && c != ' ') || i == s.length() - 1) {
            switch (op) {
                case '+': stack.push(num); break;
                case '-': stack.push(-num); break;
                case '*': stack.push(stack.pop() * num); break;
                case '/': stack.push(stack.pop() / num); break;
            }
            op = c;
            num = 0;
        }
    }
    
    int result = 0;
    for (int n : stack) result += n;
    return result;
}
```

### Pattern 4: Min Stack — O(1) for Everything

```java
// Min Stack (LC 155) ⭐
class MinStack {
    Deque<long[]> stack = new ArrayDeque<>(); // [value, min_at_this_point]
    
    public void push(int val) {
        long min = stack.isEmpty() ? val : Math.min(val, stack.peek()[1]);
        stack.push(new long[]{val, min});
    }
    
    public void pop() { stack.pop(); }
    public int top() { return (int) stack.peek()[0]; }
    public int getMin() { return (int) stack.peek()[1]; }
}
```

---

## Queue Patterns

### Pattern 1: BFS with Queue

```java
// See Phase 4 for full BFS coverage. Quick template:
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(start);
Set<Integer> visited = new HashSet<>();
visited.add(start);

while (!queue.isEmpty()) {
    int size = queue.size(); // process level by level
    for (int i = 0; i < size; i++) {
        int curr = queue.poll();
        for (int neighbor : getNeighbors(curr)) {
            if (visited.add(neighbor)) {
                queue.offer(neighbor);
            }
        }
    }
}
```

### Pattern 2: Sliding Window Maximum (Monotonic Deque)

**This is one of the hardest and most beautiful patterns. It's the deque version of monotonic stack.**

```java
// Sliding Window Maximum (LC 239) ⭐⭐ — Google, Amazon favorite
public int[] maxSlidingWindow(int[] nums, int k) {
    // Deque stores INDICES. Front of deque = index of max in current window.
    // Invariant: values in deque are in DECREASING order.
    // Why? If nums[i] >= nums[j] and i > j, then j is useless — 
    // i is both larger AND stays in the window longer.
    
    Deque<Integer> deque = new ArrayDeque<>();
    int[] result = new int[nums.length - k + 1];
    
    for (int i = 0; i < nums.length; i++) {
        // Remove indices outside the window
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }
        // Remove smaller elements — they'll never be the max
        while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
            deque.pollLast();
        }
        deque.offerLast(i);
        
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    return result;
}
```

---

## Stack as Recursion Replacement

Every recursive algorithm can be converted to iterative using a stack. This matters when stack overflow is a concern.

```java
// Iterative DFS using explicit stack
void dfs(TreeNode root) {
    if (root == null) return;
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        process(node);
        if (node.right != null) stack.push(node.right); // right first
        if (node.left != null) stack.push(node.left);   // so left is processed first
    }
}
```

---

## Edge Cases

| Case | Trap |
|------|------|
| Empty stack pop | Check `isEmpty()` before `pop()` or `peek()` |
| Single element | Stack/queue with one element — operations should still work |
| All same elements | Monotonic stack may pop nothing or everything |
| Negative numbers | Min stack must handle negatives |
| Expression edge | "3+2*2" — operator precedence matters |
| Overflow | Large expressions may produce values beyond int |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Valid Parentheses (LC 20) ⭐⭐ | Stack matching |
| 2 | Min Stack (LC 155) ⭐ | Track min alongside values |
| 3 | Implement Queue using Stacks (LC 232) ⭐ | Two stacks |
| 4 | Implement Stack using Queues (LC 225) | Single queue rotation |
| 5 | Baseball Game (LC 682) | Stack simulation |
| 6 | Next Greater Element I (LC 496) | Monotonic stack intro |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Daily Temperatures (LC 739) ⭐⭐ | Monotonic decreasing stack |
| 2 | Evaluate Reverse Polish Notation (LC 150) ⭐ | Stack evaluation |
| 3 | Decode String (LC 394) ⭐ | Stack for nested structure |
| 4 | Basic Calculator II (LC 227) ⭐ | Stack with operator precedence |
| 5 | Asteroid Collision (LC 735) ⭐ | Stack simulation |
| 6 | Online Stock Span (LC 901) | Monotonic stack variant |
| 7 | Remove K Digits (LC 402) ⭐ | Monotonic increasing stack |
| 8 | Simplify Path (LC 71) | Stack + string split |
| 9 | Car Fleet (LC 853) | Sort + stack |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Largest Rectangle in Histogram (LC 84) ⭐⭐ | Monotonic stack — THE classic |
| 2 | Sliding Window Maximum (LC 239) ⭐⭐ | Monotonic deque |
| 3 | Maximal Rectangle (LC 85) ⭐ | Histogram per row + LC 84 |
| 4 | Basic Calculator (LC 224) | Stack + recursion for parentheses |
| 5 | Trapping Rain Water (LC 42) ⭐ | Stack or two-pointer |

---

## Interviewer's Perspective

The Largest Rectangle in Histogram (LC 84) is my favorite interview problem. Here's why:

1. **Brute force** O(n²) — for each bar, expand left and right. Shows basic understanding.
2. **Stack O(n)** — shows you understand monotonic stack pattern. This is the "hire" level.
3. **Communication** — explaining WHY the stack works (not just how) is what gets "strong hire."

If a candidate can derive the monotonic stack approach from scratch (not recite it), I know they understand the pattern deeply enough to apply it to problems they've never seen.

---

*Next: [HashMap & HashSet — O(1) Lookup Power](03-hashing.md)*
