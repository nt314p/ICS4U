package com.bayviewglen.postfix;

public class Parser {
	
	public static final String NUMBERS = "1234567890.";
	public static final String PARENTHESIS = "()";
	public static final String OPERATORS = "+-*/^";
	
	public static void main(String[] args) {

		String str = "10 2 8 * + 16 - 2 ^";
		TermStack expression = new TermStack();

		String[] parts = str.split(" ");
		int ctr = 0;
		while (ctr <= parts.length) {
			if (ctr < parts.length)
				expression.push(new Term(parts[ctr]));
			if (expression.peek().isOperator()) {
				String operator = expression.pop().getOperator();
				double b = expression.pop().getOperand();
				double a = expression.pop().getOperand();
				switch (operator) {
				case "+":
					expression.push(new Term(a + b + ""));
					break;
				case "-":
					expression.push(new Term(a - b + ""));
					break;
				case "*":
					expression.push(new Term(a * b + ""));
					break;
				case "/":
					expression.push(new Term(a / b + ""));
					break;
				case "^":
					expression.push(new Term(Math.pow(a, b) + ""));
				}
			}
			ctr++;
		}

		while (!expression.isEmpty()) {
			System.out.println(expression.pop().getValue());
		}

	}

	private TermStack parseTerms(String s) {
		TermStack stack = new TermStack();
		String term = s.substring(0, 1);
		for (int i = 1; i < s.length(); i++) {
			String test = s.charAt(i) + "";
			try {
				Double.parseDouble(term); // term is a number
				if (NUMBERS.indexOf(test) != -1) { // new term is a number
					term += test;
				} else {
					stack.push(new Term(term));
					term = "";
				}
			} catch (NumberFormatException e) { // term is not a number
				if (" ".equals(test)) { // space
					continue;
				}
				if (PARENTHESIS.indexOf(test) != -1) {
					stack.push(new Term(test));
				}
			}

		}
		return null;
	}
}
