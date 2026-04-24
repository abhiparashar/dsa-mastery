import java.util.*;

/**
 * LeetCode 212 - Word Search II ⭐⭐ (HARD — Trie + Backtracking)
 *
 * Given a board and a list of words, find all words that exist in the board.
 * Same cell can't be used twice per word.
 *
 * APPROACH: Build a Trie from the word list, then DFS from each cell.
 * Instead of searching for each word separately (O(words * m*n * 4^L)),
 * we search for ALL words simultaneously using the Trie.
 *
 * OPTIMIZATION: Remove word from Trie after finding it (prune dead branches).
 */
public class WordSearchII {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;
    }

    public List<String> findWords_yours(char[][] board, String[] words) {
        // TODO
        return new ArrayList<>();
    }

    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);
        List<String> result = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, result);
            }
        }
        return result;
    }

    void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
        char c = board[i][j];
        if (c == '#' || node.children[c - 'a'] == null) return;

        node = node.children[c - 'a'];
        if (node.word != null) {
            result.add(node.word);
            node.word = null; // avoid duplicates
        }

        board[i][j] = '#';
        dfs(board, i + 1, j, node, result);
        dfs(board, i - 1, j, node, result);
        dfs(board, i, j + 1, node, result);
        dfs(board, i, j - 1, node, result);
        board[i][j] = c;
    }

    TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.word = word;
        }
        return root;
    }

    public static void main(String[] args) {
        WordSearchII sol = new WordSearchII();

        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'}
        };
        List<String> r1 = sol.findWords(board, new String[]{"oath","pea","eat","rain"});
        Collections.sort(r1);
        assert r1.equals(List.of("eat", "oath")) : "Got: " + r1;
        System.out.println("Test 1 passed: found [eat, oath]");

        char[][] b2 = {{'a','b'},{'c','d'}};
        List<String> r2 = sol.findWords(b2, new String[]{"abcb"});
        assert r2.isEmpty();
        System.out.println("Test 2 passed: no reuse → empty");

        char[][] b3 = {{'a'}};
        List<String> r3 = sol.findWords(b3, new String[]{"a"});
        assert r3.equals(List.of("a"));
        System.out.println("Test 3 passed: single cell match");

        System.out.println("\n✓ All tests passed!");
    }
}
