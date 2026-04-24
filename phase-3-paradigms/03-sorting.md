# Phase 3.3: Sorting — Not Just Arrays

> **First Principle**: Sorting imposes order, and order enables algorithms. Two pointers need sorted input. Binary search needs sorted input. Greedy interval problems need sorted intervals. Sorting is rarely the answer itself — it's the enabler that makes O(n) or O(n log n) solutions possible.

---

## Sorting Algorithms You Must Know

### Why Know Internal Details?

You won't implement sorting in interviews (use `Arrays.sort()`). But understanding HOW sorts work helps you:
1. Choose the right sort for the situation
2. Recognize merge sort / quicksort patterns in other problems
3. Answer "what sorting algorithm does Java use?" (interviewers ask this)

### Java's Sort

```java
// Primitive arrays: Dual-Pivot Quicksort — O(n log n) avg, O(n²) worst
Arrays.sort(intArray);

// Object arrays: TimSort (merge sort variant) — O(n log n) guaranteed, STABLE
Arrays.sort(objectArray);
Collections.sort(list);

// Custom comparator
Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // sort by first element
Arrays.sort(strings, (a, b) -> (a + b).compareTo(b + a)); // custom order
```

### Merge Sort — The Divide & Conquer Sort

**Why you need to know it**: Merge sort pattern appears in:
- Count inversions
- Sort linked list
- Merge K sorted lists
- External sorting

```java
void mergeSort(int[] arr, int l, int r) {
    if (l >= r) return;
    int mid = l + (r - l) / 2;
    mergeSort(arr, l, mid);
    mergeSort(arr, mid + 1, r);
    merge(arr, l, mid, r);
}

void merge(int[] arr, int l, int mid, int r) {
    int[] temp = new int[r - l + 1];
    int i = l, j = mid + 1, k = 0;
    while (i <= mid && j <= r) {
        if (arr[i] <= arr[j]) temp[k++] = arr[i++];
        else temp[k++] = arr[j++];
    }
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= r) temp[k++] = arr[j++];
    System.arraycopy(temp, 0, arr, l, temp.length);
}
```

### Quick Select — O(n) Average Kth Element

```java
// Kth Largest Element (LC 215) — QuickSelect approach
public int findKthLargest(int[] nums, int k) {
    int target = nums.length - k; // convert to kth smallest
    return quickSelect(nums, 0, nums.length - 1, target);
}

int quickSelect(int[] nums, int lo, int hi, int k) {
    int pivot = partition(nums, lo, hi);
    if (pivot == k) return nums[pivot];
    if (pivot < k) return quickSelect(nums, pivot + 1, hi, k);
    return quickSelect(nums, lo, pivot - 1, k);
}

int partition(int[] nums, int lo, int hi) {
    int pivot = nums[hi];
    int i = lo;
    for (int j = lo; j < hi; j++) {
        if (nums[j] <= pivot) {
            swap(nums, i, j);
            i++;
        }
    }
    swap(nums, i, hi);
    return i;
}
```

### Counting Sort / Bucket Sort — O(n + k)

When values are bounded, we can sort in linear time.

```java
// Sort when values are in range [0, k]
void countingSort(int[] arr, int k) {
    int[] count = new int[k + 1];
    for (int num : arr) count[num]++;
    int idx = 0;
    for (int val = 0; val <= k; val++) {
        while (count[val]-- > 0) arr[idx++] = val;
    }
}

// Bucket sort for Top K Frequent Elements (LC 347) — O(n)
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);
    
    List<Integer>[] buckets = new List[nums.length + 1];
    for (var entry : freq.entrySet()) {
        int f = entry.getValue();
        if (buckets[f] == null) buckets[f] = new ArrayList<>();
        buckets[f].add(entry.getKey());
    }
    
    int[] result = new int[k];
    int idx = 0;
    for (int i = buckets.length - 1; i >= 0 && idx < k; i--) {
        if (buckets[i] != null) {
            for (int num : buckets[i]) {
                result[idx++] = num;
                if (idx == k) break;
            }
        }
    }
    return result;
}
```

---

## Sorting as a Problem-Solving Enabler

### Pattern 1: Sort + Two Pointers
Already covered in 3.1 (3Sum, etc.)

### Pattern 2: Sort Intervals

```java
// Merge Intervals (LC 56) ⭐⭐ — Meta, Google, Amazon favorite
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> merged = new ArrayList<>();
    
    for (int[] interval : intervals) {
        if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
            merged.add(interval);
        } else {
            merged.get(merged.size() - 1)[1] = 
                Math.max(merged.get(merged.size() - 1)[1], interval[1]);
        }
    }
    return merged.toArray(new int[0][]);
}

// Non-overlapping Intervals (LC 435) ⭐ — sort by END time (greedy)
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // sort by end!
    int count = 0, prevEnd = Integer.MIN_VALUE;
    for (int[] interval : intervals) {
        if (interval[0] >= prevEnd) {
            prevEnd = interval[1];
        } else {
            count++;
        }
    }
    return count;
}
```

### Pattern 3: Custom Sort Order

```java
// Largest Number (LC 179) ⭐
public String largestNumber(int[] nums) {
    String[] strs = new String[nums.length];
    for (int i = 0; i < nums.length; i++) strs[i] = String.valueOf(nums[i]);
    
    Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
    
    if (strs[0].equals("0")) return "0";
    
    StringBuilder sb = new StringBuilder();
    for (String s : strs) sb.append(s);
    return sb.toString();
}
```

### Pattern 4: Count Inversions (Merge Sort Application)

```java
// Count of Smaller Numbers After Self (LC 315) ⭐
// During merge sort, when an element from the right half is placed before 
// elements in the left half, those left elements have one more "inversion"
```

---

## Edge Cases

| Case | Trap |
|------|------|
| Empty array | Nothing to sort |
| Single element | Already sorted |
| All equal elements | Sort is O(n) but behavior must be correct |
| Comparator overflow | `a[0] - b[0]` overflows if values near Integer.MAX_VALUE. Use `Integer.compare` |
| Stability matters | When equal elements' relative order matters (use `Collections.sort`, not `Arrays.sort` on int[]) |
| Custom objects | Must implement Comparable or provide Comparator |

---

## Problem Set

### Easy
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Sort Colors (LC 75) ⭐ | Dutch National Flag, no actual sort |
| 2 | Merge Sorted Array (LC 88) ⭐ | Merge from end |
| 3 | Valid Anagram (LC 242) | Sort + compare or freq count |
| 4 | Squares of Sorted Array (LC 977) | Two pointers, fill from ends |

### Medium
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Merge Intervals (LC 56) ⭐⭐ | Sort by start, merge overlapping |
| 2 | Insert Interval (LC 57) ⭐ | Sort not needed, but same merge logic |
| 3 | Non-overlapping Intervals (LC 435) ⭐ | Sort by end (greedy) |
| 4 | Meeting Rooms II (LC 253) ⭐ | Sort + min-heap |
| 5 | Largest Number (LC 179) ⭐ | Custom comparator |
| 6 | Kth Largest Element (LC 215) ⭐⭐ | QuickSelect |
| 7 | Top K Frequent Elements (LC 347) ⭐ | Bucket sort |
| 8 | Sort List (LC 148) | Merge sort on linked list |

### Hard
| # | Problem | Pattern |
|---|---------|---------|
| 1 | Count of Smaller Numbers After Self (LC 315) | Merge sort count inversions |
| 2 | Maximum Gap (LC 164) | Bucket sort / pigeonhole |
| 3 | Reverse Pairs (LC 493) | Merge sort variant |

---

*Next: [Recursion & Backtracking](04-recursion-backtracking.md)*
