/**
 * LeetCode 875 - Koko Eating Bananas ⭐ (Binary Search on Answer)
 *
 * Koko has piles of bananas. She eats at speed k bananas/hour.
 * Each hour she picks a pile, eats k bananas (or finishes the pile if < k remain).
 * Find the MINIMUM speed k to eat all bananas within h hours.
 *
 * Example: piles=[3,6,7,11], h=8 → 4
 *
 * WHY BINARY SEARCH ON ANSWER:
 * - Answer space: k ∈ [1, max(piles)]
 * - Monotonic: if speed k works, any speed > k also works
 * - Feasibility check: can she finish in h hours at speed k? → O(n)
 * - Binary search for smallest feasible k → O(n log M) where M = max pile
 */
public class KokoEatingBananas {

    public int minEatingSpeed_yours(int[] piles, int h) {
        // TODO: binary search on answer space
        return 0;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int lo = 1;
        int hi = 0;
        for (int pile : piles) hi = Math.max(hi, pile);

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canFinish(piles, h, mid)) {
                hi = mid;       // this speed works, try slower
            } else {
                lo = mid + 1;   // too slow
            }
        }
        return lo;
    }

    private boolean canFinish(int[] piles, int h, int speed) {
        int hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed; // ceiling division
        }
        return hours <= h;
    }

    public static void main(String[] args) {
        KokoEatingBananas sol = new KokoEatingBananas();

        assert sol.minEatingSpeed(new int[]{3, 6, 7, 11}, 8) == 4;
        System.out.println("Test 1 passed: [3,6,7,11], h=8 → 4");

        assert sol.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5) == 30;
        System.out.println("Test 2 passed: [30,11,23,4,20], h=5 → 30");

        assert sol.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 6) == 23;
        System.out.println("Test 3 passed: [30,11,23,4,20], h=6 → 23");

        assert sol.minEatingSpeed(new int[]{1}, 1) == 1;
        System.out.println("Test 4 passed: [1], h=1 → 1");

        assert sol.minEatingSpeed(new int[]{1000000000}, 2) == 500000000;
        System.out.println("Test 5 passed: large pile → 500000000");

        System.out.println("\n✓ All tests passed!");
    }
}
