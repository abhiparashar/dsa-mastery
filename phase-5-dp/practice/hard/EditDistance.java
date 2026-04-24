/**
 * LeetCode 72 - Edit Distance ⭐⭐ (HARD — The ultimate 2D DP problem)
 *
 * Given two strings, find minimum operations (insert, delete, replace)
 * to convert word1 to word2.
 *
 * SUBPROBLEM: dp[i][j] = min edits to convert word1[0..i-1] to word2[0..j-1]
 * RECURRENCE:
 *   If chars match: dp[i][j] = dp[i-1][j-1]  (no edit needed)
 *   Else: dp[i][j] = 1 + min(
 *       dp[i-1][j-1],  // replace word1[i-1] with word2[j-1]
 *       dp[i-1][j],    // delete from word1
 *       dp[i][j-1]     // insert into word1
 *   )
 * BASE: dp[i][0] = i (delete all), dp[0][j] = j (insert all)
 *
 * FIRST PRINCIPLES: Each cell asks "what's the cheapest way to align
 * these two prefixes?" — same structure as LCS but min cost instead of max length.
 */
public class EditDistance {

    public int minDistance_yours(String word1, String word2) {
        // TODO
        return 0;
    }

    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                                   Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        EditDistance sol = new EditDistance();

        assert sol.minDistance("horse", "ros") == 3;
        System.out.println("Test 1: horse → ros = 3 (h→r, r→del, e→del)");

        assert sol.minDistance("intention", "execution") == 5;
        System.out.println("Test 2: intention → execution = 5");

        assert sol.minDistance("", "abc") == 3;
        System.out.println("Test 3: '' → 'abc' = 3 (insert all)");

        assert sol.minDistance("abc", "") == 3;
        System.out.println("Test 4: 'abc' → '' = 3 (delete all)");

        assert sol.minDistance("abc", "abc") == 0;
        System.out.println("Test 5: identical → 0");

        assert sol.minDistance("a", "b") == 1;
        System.out.println("Test 6: single char replace → 1");

        System.out.println("\n✓ All tests passed!");
    }
}
