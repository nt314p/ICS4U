import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Stack;

public class Parser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Reader reader = new InputStreamReader(System.in);

		Stack<Character> output = new Stack<Character>();
		Stack<Character> operatorStack = new Stack<Character>();

		while (true) {
			int ch = 0;
			try {
				ch = reader.read();
			} catch (IOException e) {

			}
			if (ch == '\r') {
				break;
			}

			char token = (char) ch;

			if (token == ' ') {
				continue;
			}

			if (Character.isLetterOrDigit(token)) {
				output.push(token);
			}

			if (Token.isOperator(token)) {
				int currentPriority = Token.priority(token);
				while (!operatorStack.isEmpty() && Token.priority(operatorStack.peek()) >= currentPriority) {
					output.push(operatorStack.pop());
				}
				operatorStack.push(token);
			}

			if (token == Token.openParenthesis) {
				operatorStack.push(token);
			}

			if (token == Token.closeParenthesis) {
				while (operatorStack.peek() != Token.openParenthesis) {
					output.push(operatorStack.pop());
				}
				operatorStack.pop();
			}
		}

		while (!operatorStack.isEmpty()) {
			output.push(operatorStack.pop());
		}

		Stack<Character> printStack = new Stack<Character>();

		while (!output.isEmpty()) {
			printStack.push(output.pop());
		}

		while (!printStack.isEmpty()) {
			System.out.print(printStack.pop());
		}

		System.out.println("\nFinished");

		try {
			reader.close();
		} catch (IOException e) {

		}
	}

}
