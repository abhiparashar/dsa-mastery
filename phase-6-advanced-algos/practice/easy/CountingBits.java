/**
 * LeetCode 338 - Counting Bits ⭐
 *
 * For each i in [0..n], return number of 1-bits in its binary representation.
 *
 * APPROACH (DP + bit trick):
 * dp[i] = dp[i >> 1] + (i & 1)
 *
 * WHY: i >> 1 removes the last bit. (i & 1) checks if last bit is 1.
 * So the count for i = count for i/2 + whether last bit is set.
 *
 * Example: 5 = 101 → dp[5] = dp[2] + 1 = 1 + 1 = 2 ✓
 */
public class CountingBits {

    public int[] countBits_yours(int n) {
        // TODO
        return new int[]{};
    }

    public int[] countBits(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i >> 1] + (i & 1);
        }
        return dp;
    }

    public static void main(String[] args) {
        CountingBits sol = new CountingBits();

        int[] r1 = sol.countBits(2);
        assert r1[0] == 0 && r1[1] == 1 && r1[2] == 1;
        System.out.println("Test 1: n=2 → [0,1,1]");

        int[] r2 = sol.countBits(5);
        assert r2[0]==0 && r2[1]==1 && r2[2]==1 && r2[3]==2 && r2[4]==1 && r2[5]==2;
        System.out.println("Test 2: n=5 → [0,1,1,2,1,2]");

        int[] r3 = sol.countBits(0);
        assert r3.length == 1 && r3[0] == 0;
        System.out.println("Test 3: n=0 → [0]");

        System.out.println("\n✓ All tests passed!");
    }
}
