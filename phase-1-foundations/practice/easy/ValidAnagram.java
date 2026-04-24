import java.util.Arrays;

/**
 * LeetCode 242 - Valid Anagram
 *
 * Given two strings s and t, return true if t is an anagram of s.
 * An anagram uses the exact same characters with the exact same frequencies.
 *
 * Example 1: s = "anagram", t = "nagaram" → true
 * Example 2: s = "rat",     t = "car"     → false
 *
 * THINK BEFORE CODING:
 * 1. What makes two strings anagrams? Same character frequencies.
 * 2. Approach 1: Sort both strings, compare. O(n log n)
 * 3. Approach 2: Count character frequencies. O(n)
 *    - For lowercase English letters, int[26] is faster than HashMap
 *    - Why? No boxing, no hashing, direct array access
 *
 * FOLLOW-UP: What if the inputs contain Unicode characters?
 * Answer: Use HashMap<Character, Integer> instead of int[26]
 */
public class ValidAnagram {

    // YOUR SOLUTION
    public boolean isAnagram_yours(String s, String t) {
        // TODO
        return false;
    }

    // APPROACH 1: Sort — O(n log n) time, O(n) space
    public boolean isAnagram_sort(String s, String t) {
        if (s.length() != t.length()) return false;
        char[] a = s.toCharArray(), b = t.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    // APPROACH 2: Frequency count — O(n) time, O(1) space (fixed 26 chars)
    // THIS IS THE INTERVIEW-PREFERRED APPROACH
    public boolean isAnagram_freq(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] count = new int[26];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }

        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }

    // WHY int[26] WORKS:
    // 'a' - 'a' = 0, 'b' - 'a' = 1, ..., 'z' - 'a' = 25
    // So each lowercase letter maps to index 0-25
    // We increment for s, decrement for t
    // If all counts are 0, frequencies match perfectly

    public static void main(String[] args) {
        ValidAnagram sol = new ValidAnagram();

        System.out.println(sol.isAnagram_freq("anagram", "nagaram")); // true
        System.out.println(sol.isAnagram_freq("rat", "car"));         // false
        System.out.println(sol.isAnagram_freq("a", "a"));             // true
        System.out.println(sol.isAnagram_freq("a", "b"));             // false
        System.out.println(sol.isAnagram_freq("", ""));               // true (edge: empty strings)
        System.out.println(sol.isAnagram_freq("ab", "a"));            // false (different lengths)

        System.out.println("\n✓ Manual verification complete");
    }
}
