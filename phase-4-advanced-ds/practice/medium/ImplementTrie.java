/**
 * LeetCode 208 - Implement Trie (Prefix Tree) ⭐⭐
 *
 * Design a trie supporting: insert, search, startsWith.
 *
 * STRUCTURE: Each node has children[26] (for a-z) and an isEnd flag.
 * Follow the path character by character.
 *
 * WHY TRIE OVER HASHSET:
 * - Prefix queries (startsWith) in O(L) vs O(N*L) with set
 * - Autocomplete, spell check, IP routing
 *
 * COMPLEXITY: O(L) for all operations where L = word length.
 */
public class ImplementTrie {

    // --- YOUR SOLUTION ---
    static class Trie_yours {
        // TODO: implement insert, search, startsWith
        public void insert(String word) {}
        public boolean search(String word) { return false; }
        public boolean startsWith(String prefix) { return false; }
    }

    // --- OPTIMAL SOLUTION ---
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    static class Trie {
        TrieNode root = new TrieNode();

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            TrieNode node = find(word);
            return node != null && node.isEnd;
        }

        public boolean startsWith(String prefix) {
            return find(prefix) != null;
        }

        private TrieNode find(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) return null;
                node = node.children[idx];
            }
            return node;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");
        assert trie.search("apple") == true;
        System.out.println("Test 1: search('apple') → true ✓");

        assert trie.search("app") == false;
        System.out.println("Test 2: search('app') → false ✓ (not a complete word)");

        assert trie.startsWith("app") == true;
        System.out.println("Test 3: startsWith('app') → true ✓");

        trie.insert("app");
        assert trie.search("app") == true;
        System.out.println("Test 4: after insert('app'), search('app') → true ✓");

        assert trie.search("apples") == false;
        System.out.println("Test 5: search('apples') → false ✓");

        assert trie.startsWith("b") == false;
        System.out.println("Test 6: startsWith('b') → false ✓");

        trie.insert("banana");
        assert trie.startsWith("ban") == true;
        System.out.println("Test 7: insert('banana'), startsWith('ban') → true ✓");

        System.out.println("\n✓ All tests passed!");
    }
}
