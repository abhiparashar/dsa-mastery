/**
 * LeetCode 190 - Reverse Bits ⭐
 *
 * Reverse bits of a 32-bit unsigned integer.
 *
 * APPROACH: Extract each bit from right, place it from left.
 * For each of 32 bits:
 *   1. result <<= 1          (make room)
 *   2. result |= (n & 1)     (place rightmost bit of n)
 *   3. n >>>= 1              (move to next bit of n)
 *
 * NOTE: Use >>> (unsigned right shift) since Java has no unsigned int.
 */
public class ReverseBits {

    public int reverseBits_yours(int n) {
        // TODO
        return 0;
    }

    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result |= (n & 1);
            n >>>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        ReverseBits sol = new ReverseBits();

        // 00000010100101000001111010011100 → 00111001011110000010100101000000
        int r1 = sol.reverseBits(43261596);
        assert r1 == 964176192;
        System.out.println("Test 1: 43261596 → 964176192");

        // All 1s stays all 1s
        int r2 = sol.reverseBits(-1);
        assert r2 == -1;
        System.out.println("Test 2: -1 (all 1s) → -1");

        int r3 = sol.reverseBits(0);
        assert r3 == 0;
        System.out.println("Test 3: 0 → 0");

        int r4 = sol.reverseBits(1);
        assert r4 == Integer.MIN_VALUE;
        System.out.println("Test 4: 1 → MIN_VALUE (1 followed by 31 zeros)");

        System.out.println("\n✓ All tests passed!");
    }
}
