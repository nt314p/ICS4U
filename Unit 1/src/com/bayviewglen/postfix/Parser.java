package com.bayviewglen.postfix;

public class Parser {

	public static final String NUMBERS = "1234567890.";
	public static final String PARENTHESIS = "()";
	public static final String OPERATORS = "+-*/^";

	public static void main(String[] args) {

		String str = "10 2 8 * + 16 - 2 ^";
		TermStack expression = new TermStack();
		// Double.parseDouble("1.");
		//printStack(parseTerms("(3.14*10^2)*7.6 + 910 - 3"));
		printStack(parseTerms(str));
		/*
		 * String[] parts = str.split(" "); int ctr = 0; while (ctr <= parts.length) {
		 * if (ctr < parts.length) expression.push(new Term(parts[ctr])); if
		 * (expression.peek().isOperator()) { String operator =
		 * expression.pop().getOperator(); double b = expression.pop().getOperand();
		 * double a = expression.pop().getOperand(); switch (operator) { case "+":
		 * expression.push(new Term(a + b + "")); break; case "-": expression.push(new
		 * Term(a - b + "")); break; case "*": expression.push(new Term(a * b + ""));
		 * break; case "/": expression.push(new Term(a / b + "")); break; case "^":
		 * expression.push(new Term(Math.pow(a, b) + "")); } } ctr++; }
		 * 
		 * while (!expression.isEmpty()) {
		 * System.out.println(expression.pop().getValue()); }
		 */

	}

	private static void printStack(TermStack expression) {
		while (!expression.isEmpty()) {
			System.out.println(expression.pop().getValue());
		}
	}

	private static TermStack parseTerms(String s) {
		TermStack stack = new TermStack();
		String number = "";
		String test = "";
		for (int i = 0; i < s.length(); i++) {
			test = s.charAt(i) + "";

			try {
				if (" ".equals(test) && !number.equals("")) {
					Double.parseDouble("LOLXD!");
				}
				Double.parseDouble(number + test); // term is a number
				number += test;
			} catch (NumberFormatException e) { // term is not a number
				if (!number.equals(""))
					stack.push(new Term(number));
				number = "";
			}
			if (" ".equals(test)) { // space
				continue;
			} else if (PARENTHESIS.indexOf(test) != -1 && number.equals("")) {
				stack.push(new Term(test));
			} else if (OPERATORS.indexOf(test) != -1 && number.equals("")) {
				stack.push(new Term(test));
			}
		}
		try {
			Double.parseDouble(number);
			stack.push(new Term(number));
		} catch (NumberFormatException e) {
		}

		return stack;
	}
}
