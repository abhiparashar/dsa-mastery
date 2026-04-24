/**
 * LeetCode 33 - Search in Rotated Sorted Array ⭐⭐ (Meta, Google, Amazon)
 *
 * Array was sorted then rotated at some pivot. Find target index or -1.
 * All values are UNIQUE.
 *
 * Example: nums=[4,5,6,7,0,1,2], target=0 → 4
 *
 * KEY INSIGHT:
 * After rotation, at least ONE half (left or right of mid) is sorted.
 * 1. Find which half is sorted
 * 2. Check if target falls in that sorted half
 * 3. Eliminate the other half
 *
 * HOW TO IDENTIFY SORTED HALF:
 * If nums[lo] <= nums[mid] → left half [lo..mid] is sorted
 * Else → right half [mid..hi] is sorted
 */
public class SearchRotatedArray {

    public int search_yours(int[] nums, int target) {
        // TODO
        return -1;
    }

    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (nums[mid] == target) return mid;

            // Left half is sorted
            if (nums[lo] <= nums[mid]) {
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1; // target in sorted left half
                } else {
                    lo = mid + 1; // target in right half
                }
            }
            // Right half is sorted
            else {
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1; // target in sorted right half
                } else {
                    hi = mid - 1; // target in left half
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        SearchRotatedArray sol = new SearchRotatedArray();

        assert sol.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0) == 4;
        System.out.println("Test 1 passed: find 0 in [4,5,6,7,0,1,2] → 4");

        assert sol.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3) == -1;
        System.out.println("Test 2 passed: find 3 → -1 (not found)");

        assert sol.search(new int[]{1}, 0) == -1;
        System.out.println("Test 3 passed: [1], find 0 → -1");

        assert sol.search(new int[]{1}, 1) == 0;
        System.out.println("Test 4 passed: [1], find 1 → 0");

        assert sol.search(new int[]{3, 1}, 1) == 1;
        System.out.println("Test 5 passed: [3,1], find 1 → 1");

        assert sol.search(new int[]{1, 3}, 3) == 1;
        System.out.println("Test 6 passed: [1,3], find 3 → 1 (no rotation)");

        System.out.println("\n✓ All tests passed!");
    }
}
