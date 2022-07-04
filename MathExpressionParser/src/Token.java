
public class Token {
	public static final char plus = '+';
	public static final char minus = '-';
	public static final char multiply = '*';
	public static final char divide = '/';
	public static final char exponentiate = '^';
	public static final char openParenthesis = '(';
	public static final char closeParenthesis = ')';
	private static final String operators = "+-*/^";

	public static boolean isOperator(char c) {
		return operators.indexOf(c) != -1;
	}

	public static int priority(char operator) {
		if (operator == plus || operator == minus) {
			return 1;
		}
		if (operator == multiply || operator == divide) {
			return 2;
		}
		if (operator == exponentiate) {
			return 3;
		}
		return -1;
	}

}
