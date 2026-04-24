import java.util.*;

/**
 * LeetCode 76 - Minimum Window Substring ⭐⭐ (Google, Meta, Amazon)
 * THE hardest standard sliding window problem.
 *
 * Given strings s and t, find the minimum window in s that contains
 * ALL characters of t (including duplicates).
 *
 * Example 1: s="ADOBECODEBANC", t="ABC" → "BANC"
 * Example 2: s="a", t="a"               → "a"
 * Example 3: s="a", t="aa"              → "" (need two a's, only have one)
 *
 * APPROACH:
 * Variable-size sliding window.
 * 1. Expand right until window contains all chars of t.
 * 2. Shrink left to minimize window while still containing all chars.
 * 3. Track the smallest valid window.
 *
 * STATE:
 * - need[c]: how many of char c we need (from t)
 * - have[c]: how many of char c we currently have in window
 * - formed: how many UNIQUE chars in t have sufficient count in window
 * - required: how many unique chars in t we need to satisfy
 *
 * When formed == required, the window is valid → try shrinking.
 */
public class MinWindowSubstring {

    // YOUR SOLUTION
    public String minWindow_yours(String s, String t) {
        // TODO: expand right, shrink left when all chars are covered
        return "";
    }

    // OPTIMAL — O(m + n) time
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

        int[] need = new int[128], have = new int[128];
        int required = 0;
        for (char c : t.toCharArray()) {
            if (need[c] == 0) required++; // count unique chars
            need[c]++;
        }

        int formed = 0, left = 0;
        int minLen = Integer.MAX_VALUE, minStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char rc = s.charAt(right);
            have[rc]++;

            // This char is now fully satisfied
            if (have[rc] == need[rc]) formed++;

            // Try to shrink
            while (formed == required) {
                // Update answer
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }

                // Remove left char
                char lc = s.charAt(left);
                if (have[lc] == need[lc]) formed--; // will become unsatisfied
                have[lc]--;
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    public static void main(String[] args) {
        MinWindowSubstring sol = new MinWindowSubstring();

        assert sol.minWindow("ADOBECODEBANC", "ABC").equals("BANC");
        System.out.println("Test 1 passed: \"ADOBECODEBANC\", \"ABC\" → \"BANC\"");

        assert sol.minWindow("a", "a").equals("a");
        System.out.println("Test 2 passed: \"a\", \"a\" → \"a\"");

        assert sol.minWindow("a", "aa").equals("");
        System.out.println("Test 3 passed: \"a\", \"aa\" → \"\" (insufficient)");

        assert sol.minWindow("abc", "bc").equals("bc");
        System.out.println("Test 4 passed: \"abc\", \"bc\" → \"bc\"");

        assert sol.minWindow("ab", "b").equals("b");
        System.out.println("Test 5 passed: \"ab\", \"b\" → \"b\"");

        assert sol.minWindow("bba", "ab").equals("ba");
        System.out.println("Test 6 passed: \"bba\", \"ab\" → \"ba\"");

        System.out.println("\n✓ All tests passed!");
    }
}
