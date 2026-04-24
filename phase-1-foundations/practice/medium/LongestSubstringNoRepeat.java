import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 3 - Longest Substring Without Repeating Characters ⭐⭐
 * (Meta, Amazon, Google, Microsoft — top 5 most asked)
 *
 * Given a string, find the length of the longest substring without
 * repeating characters.
 *
 * Example 1: "abcabcbb" → 3 ("abc")
 * Example 2: "bbbbb"    → 1 ("b")
 * Example 3: "pwwkew"   → 3 ("wke")
 *
 * THINK BEFORE CODING:
 * - Brute force: check all substrings → O(n³) or O(n²) with a set
 * - Key observation: once we find a duplicate, we don't need to restart
 *   from scratch. We just need to move past the previous occurrence.
 * - This is a VARIABLE sliding window where the constraint is "no duplicates."
 *
 * TWO APPROACHES:
 * A) HashMap storing last-seen index → can JUMP left pointer directly
 * B) HashSet + shrink one-by-one → simpler but slightly slower
 *
 * Approach A is preferred in interviews — shows deeper understanding.
 */
public class LongestSubstringNoRepeat {

    // YOUR SOLUTION
    public int lengthOfLongestSubstring_yours(String s) {
        // TODO
        return 0;
    }

    // APPROACH A: HashMap (last-seen index) — O(n) time, single pass
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLen = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If char was seen and is within current window, jump left past it
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }

            lastSeen.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    // APPROACH B: int[128] for ASCII (faster than HashMap for ASCII inputs)
    public int lengthOfLongestSubstring_array(String s) {
        int[] lastSeen = new int[128];
        java.util.Arrays.fill(lastSeen, -1);

        int maxLen = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (lastSeen[c] >= left) {
                left = lastSeen[c] + 1;
            }

            lastSeen[c] = right;
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringNoRepeat sol = new LongestSubstringNoRepeat();

        assert sol.lengthOfLongestSubstring("abcabcbb") == 3;
        System.out.println("Test 1 passed: \"abcabcbb\" → 3");

        assert sol.lengthOfLongestSubstring("bbbbb") == 1;
        System.out.println("Test 2 passed: \"bbbbb\" → 1");

        assert sol.lengthOfLongestSubstring("pwwkew") == 3;
        System.out.println("Test 3 passed: \"pwwkew\" → 3");

        assert sol.lengthOfLongestSubstring("") == 0;
        System.out.println("Test 4 passed: \"\" → 0");

        assert sol.lengthOfLongestSubstring(" ") == 1;
        System.out.println("Test 5 passed: \" \" → 1 (space counts)");

        assert sol.lengthOfLongestSubstring("dvdf") == 3;
        System.out.println("Test 6 passed: \"dvdf\" → 3 (tricky: 'vdf' not 'dvd')");

        assert sol.lengthOfLongestSubstring("abcdef") == 6;
        System.out.println("Test 7 passed: \"abcdef\" → 6 (all unique)");

        // Verify both approaches match
        String test = "abcabcbb";
        assert sol.lengthOfLongestSubstring(test) == sol.lengthOfLongestSubstring_array(test);
        System.out.println("Test 8 passed: both approaches agree");

        System.out.println("\n✓ All tests passed!");
    }
}
