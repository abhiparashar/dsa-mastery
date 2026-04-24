import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 20 - Valid Parentheses ⭐⭐ (asked EVERYWHERE)
 *
 * Given string with (){}[], determine if it's valid.
 * Valid = every open bracket has matching close in correct order.
 *
 * Example: "()" → true, "()[]{}" → true, "(]" → false, "([)]" → false
 *
 * TRICK: Push the EXPECTED closing bracket. When you see a close bracket,
 * just check if it matches the top of the stack.
 */
public class ValidParentheses {

    public boolean isValid_yours(String s) {
        // TODO
        return false;
    }

    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(') stack.push(')');
            else if (c == '{') stack.push('}');
            else if (c == '[') stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c) return false;
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParentheses sol = new ValidParentheses();

        assert sol.isValid("()") == true;
        assert sol.isValid("()[]{}") == true;
        assert sol.isValid("(]") == false;
        assert sol.isValid("([)]") == false;
        assert sol.isValid("{[]}") == true;
        assert sol.isValid("") == true;
        assert sol.isValid("(") == false;
        assert sol.isValid(")") == false;

        System.out.println("✓ All tests passed!");
    }
}
