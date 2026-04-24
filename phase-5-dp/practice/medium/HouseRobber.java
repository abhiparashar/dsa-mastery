/**
 * LeetCode 198 - House Robber ⭐⭐ (Classic take/skip DP)
 *
 * Rob houses in a row. Can't rob two adjacent houses.
 * Maximize total money.
 *
 * SUBPROBLEM: dp[i] = max money robbing houses 0..i
 * RECURRENCE: dp[i] = max(dp[i-1], dp[i-2] + nums[i])
 *   - Skip house i: dp[i-1]
 *   - Rob house i: dp[i-2] + nums[i] (must skip i-1)
 * BASE: dp[0] = nums[0], dp[1] = max(nums[0], nums[1])
 *
 * FIRST PRINCIPLES: At each house, you make a binary decision (rob or skip).
 * This is the foundation for 0/1 knapsack pattern.
 */
public class HouseRobber {

    public int rob_yours(int[] nums) {
        // TODO
        return 0;
    }

    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int curr = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        HouseRobber sol = new HouseRobber();

        assert sol.rob(new int[]{1,2,3,1}) == 4;
        System.out.println("Test 1: [1,2,3,1] → 4 (rob 1+3)");

        assert sol.rob(new int[]{2,7,9,3,1}) == 12;
        System.out.println("Test 2: [2,7,9,3,1] → 12 (rob 2+9+1)");

        assert sol.rob(new int[]{2,1,1,2}) == 4;
        System.out.println("Test 3: [2,1,1,2] → 4 (rob first+last)");

        assert sol.rob(new int[]{5}) == 5;
        System.out.println("Test 4: [5] → 5 (single house)");

        assert sol.rob(new int[]{1,2}) == 2;
        System.out.println("Test 5: [1,2] → 2 (pick bigger)");

        System.out.println("\n✓ All tests passed!");
    }
}
