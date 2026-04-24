import java.util.*;

/**
 * LeetCode 49 - Group Anagrams ⭐⭐ (Meta, Amazon, Google top question)
 *
 * Given an array of strings, group anagrams together.
 *
 * Example:
 * Input:  ["eat","tea","tan","ate","nat","bat"]
 * Output: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
 *
 * THINK BEFORE CODING (spend 10 minutes):
 *
 * 1. What defines anagrams? → Same characters, same frequencies.
 * 2. How can we GROUP things? → HashMap: key → list of items with that key.
 * 3. What should the KEY be? → Something that's the same for all anagrams.
 *    Option A: Sort the string → "eat" → "aet", "tea" → "aet" ✓
 *    Option B: Frequency count string → "eat" → "1a1e1t"
 *
 * This problem combines TWO patterns:
 * - Anagram detection (from ValidAnagram)
 * - Grouping by key (HashMap<Key, List<Value>>)
 *
 * COMPLEXITY ANALYSIS:
 * Option A: O(n * k log k) where n = number of strings, k = max string length
 * Option B: O(n * k) — faster, but more code
 *
 * For interviews: Option A is cleaner. Mention Option B as a follow-up optimization.
 */
public class GroupAnagrams {

    // YOUR SOLUTION
    public List<List<String>> groupAnagrams_yours(String[] strs) {
        // TODO
        return new ArrayList<>();
    }

    // APPROACH 1: Sort as key — O(n * k log k)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            // computeIfAbsent: if key doesn't exist, create new list; then add to it
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    // APPROACH 2: Frequency count as key — O(n * k)
    public List<List<String>> groupAnagrams_freq(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) count[c - 'a']++;

            // Build key: "#1#0#0#0#1#0#..." (count of each letter)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append('#').append(count[i]);
            }
            String key = sb.toString();

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    // WHAT THE INTERVIEWER WANTS TO HEAR:
    // 1. "I need a canonical form for each anagram group — I'll sort the string as a key"
    // 2. "Time: O(n * k log k), Space: O(n * k)"
    // 3. "For optimization, I could use a frequency count string as the key → O(n * k)"
    // 4. "computeIfAbsent is cleaner than the if-else pattern for building map of lists"

    public static void main(String[] args) {
        GroupAnagrams sol = new GroupAnagrams();

        List<List<String>> result = sol.groupAnagrams(
            new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}
        );

        for (List<String> group : result) {
            System.out.println(group);
        }
        // Output (order may vary):
        // [eat, tea, ate]
        // [tan, nat]
        // [bat]

        // Edge case: empty strings
        List<List<String>> result2 = sol.groupAnagrams(new String[]{"", ""});
        System.out.println(result2); // [["", ""]]

        // Edge case: single character
        List<List<String>> result3 = sol.groupAnagrams(new String[]{"a"});
        System.out.println(result3); // [["a"]]

        System.out.println("\n✓ Manual verification complete");
    }
}
