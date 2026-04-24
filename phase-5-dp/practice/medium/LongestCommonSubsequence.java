/**
 * LeetCode 1143 - Longest Common Subsequence ⭐⭐ (Classic 2D DP)
 *
 * Given two strings, find the length of their longest common subsequence.
 *
 * SUBPROBLEM: dp[i][j] = LCS of text1[0..i-1] and text2[0..j-1]
 * RECURRENCE:
 *   If text1[i-1] == text2[j-1]: dp[i][j] = dp[i-1][j-1] + 1  (both match, extend)
 *   Else: dp[i][j] = max(dp[i-1][j], dp[i][j-1])              (skip one char)
 * BASE: dp[0][j] = dp[i][0] = 0 (empty string has LCS 0)
 *
 * VISUAL: Draw a grid. Match = go diagonal+1. No match = take max(up, left).
 */
public class LongestCommonSubsequence {

    public int longestCommonSubsequence_yours(String text1, String text2) {
        // TODO
        return 0;
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        LongestCommonSubsequence sol = new LongestCommonSubsequence();

        assert sol.longestCommonSubsequence("abcde", "ace") == 3;
        System.out.println("Test 1: 'abcde' vs 'ace' → 3 (ace)");

        assert sol.longestCommonSubsequence("abc", "abc") == 3;
        System.out.println("Test 2: identical → 3");

        assert sol.longestCommonSubsequence("abc", "def") == 0;
        System.out.println("Test 3: no common → 0");

        assert sol.longestCommonSubsequence("abcba", "abcbcba") == 5;
        System.out.println("Test 4: 'abcba' vs 'abcbcba' → 5");

        assert sol.longestCommonSubsequence("", "abc") == 0;
        System.out.println("Test 5: empty string → 0");

        System.out.println("\n✓ All tests passed!");
    }
}
