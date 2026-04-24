/**
 * LeetCode 136 - Single Number ⭐
 *
 * Every element appears twice except one. Find it.
 * Must use O(1) space.
 *
 * APPROACH: XOR all elements. Pairs cancel out (a^a=0), leaving the single one.
 *
 * WHY IT WORKS: XOR is commutative and associative.
 * [4,1,2,1,2] → 4^1^2^1^2 = 4^(1^1)^(2^2) = 4^0^0 = 4
 */
public class SingleNumber {

    public int singleNumber_yours(int[] nums) {
        // TODO
        return 0;
    }

    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) result ^= num;
        return result;
    }

    public static void main(String[] args) {
        SingleNumber sol = new SingleNumber();

        assert sol.singleNumber(new int[]{2,2,1}) == 1;
        System.out.println("Test 1: [2,2,1] → 1");

        assert sol.singleNumber(new int[]{4,1,2,1,2}) == 4;
        System.out.println("Test 2: [4,1,2,1,2] → 4");

        assert sol.singleNumber(new int[]{1}) == 1;
        System.out.println("Test 3: [1] → 1");

        assert sol.singleNumber(new int[]{-1,1,1}) == -1;
        System.out.println("Test 4: [-1,1,1] → -1 (negatives work too)");

        System.out.println("\n✓ All tests passed!");
    }
}
