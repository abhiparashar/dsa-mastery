import java.util.*;

/**
 * LeetCode 22 - Generate Parentheses ⭐⭐ (Meta, Google favorite)
 *
 * Generate all valid combinations of n pairs of parentheses.
 *
 * Example: n=3 → ["((()))","(()())","(())()","()(())","()()()"]
 *
 * CONSTRAINTS for backtracking:
 * 1. Can add '(' if open < n
 * 2. Can add ')' if close < open (more opens than closes so far)
 * 3. Base case: length == 2*n
 */
public class GenerateParentheses {

    public List<String> generateParenthesis_yours(int n) {
        // TODO
        return new ArrayList<>();
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    void backtrack(List<String> result, StringBuilder sb, int open, int close, int n) {
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }
        if (open < n) {
            sb.append('(');
            backtrack(result, sb, open + 1, close, n);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (close < open) {
            sb.append(')');
            backtrack(result, sb, open, close + 1, n);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        GenerateParentheses sol = new GenerateParentheses();

        List<String> r1 = sol.generateParenthesis(3);
        System.out.println("n=3: " + r1);
        assert r1.size() == 5;

        List<String> r2 = sol.generateParenthesis(1);
        System.out.println("n=1: " + r2);
        assert r2.size() == 1 && r2.get(0).equals("()");

        System.out.println("\n✓ All tests passed!");
    }
}
