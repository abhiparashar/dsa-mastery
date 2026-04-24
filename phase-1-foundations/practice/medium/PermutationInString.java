import java.util.Arrays;

/**
 * LeetCode 567 - Permutation in String ⭐
 *
 * Return true if s2 contains a permutation of s1.
 *
 * Example 1: s1="ab", s2="eidbaooo" → true ("ba" is permutation of "ab")
 * Example 2: s1="ab", s2="eidboaoo" → false
 *
 * KEY INSIGHT:
 * A permutation of s1 has the SAME character frequencies as s1.
 * So we slide a window of size len(s1) across s2 and check if
 * the frequency in the window matches s1's frequency.
 *
 * This is a FIXED-SIZE sliding window + frequency matching.
 */
public class PermutationInString {

    // YOUR SOLUTION
    public boolean checkInclusion_yours(String s1, String s2) {
        // TODO
        return false;
    }

    // OPTIMAL — O(n) time, O(1) space (fixed 26 chars)
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] need = new int[26], have = new int[26];
        for (char c : s1.toCharArray()) need[c - 'a']++;

        int k = s1.length();

        // Init first window
        for (int i = 0; i < k; i++) have[s2.charAt(i) - 'a']++;
        if (Arrays.equals(need, have)) return true;

        // Slide
        for (int i = k; i < s2.length(); i++) {
            have[s2.charAt(i) - 'a']++;       // add new right
            have[s2.charAt(i - k) - 'a']--;   // remove old left
            if (Arrays.equals(need, have)) return true;
        }

        return false;
    }

    // OPTIMIZED — O(n) with matches counter instead of array comparison
    public boolean checkInclusion_fast(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] count = new int[26];
        for (char c : s1.toCharArray()) count[c - 'a']++;

        int k = s1.length();
        int matches = 0;

        // Count how many of 26 chars have matching frequency
        for (int i = 0; i < k; i++) count[s2.charAt(i) - 'a']--;
        for (int c : count) if (c == 0) matches++;
        if (matches == 26) return true;

        for (int i = k; i < s2.length(); i++) {
            int addIdx = s2.charAt(i) - 'a';
            int remIdx = s2.charAt(i - k) - 'a';

            // Add new char
            if (count[addIdx] == 0) matches--;
            count[addIdx]--;
            if (count[addIdx] == 0) matches++;

            // Remove old char
            if (count[remIdx] == 0) matches--;
            count[remIdx]++;
            if (count[remIdx] == 0) matches++;

            if (matches == 26) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        PermutationInString sol = new PermutationInString();

        assert sol.checkInclusion("ab", "eidbaooo") == true;
        System.out.println("Test 1 passed: ab in eidbaooo → true");

        assert sol.checkInclusion("ab", "eidboaoo") == false;
        System.out.println("Test 2 passed: ab in eidboaoo → false");

        assert sol.checkInclusion("a", "a") == true;
        System.out.println("Test 3 passed: a in a → true");

        assert sol.checkInclusion("abc", "ab") == false;
        System.out.println("Test 4 passed: abc in ab → false (s1 longer)");

        assert sol.checkInclusion("adc", "dcda") == true;
        System.out.println("Test 5 passed: adc in dcda → true");

        System.out.println("\n✓ All tests passed!");
    }
}
