package com.bayviewglen.postfix;

public class Term {
	private int type = 0; // number = 0; parenthesis = 1; operator = 2; 
	private String value;

	public Term(String value) {

		if (Parser.OPERATORS.indexOf(value) != -1) {
			this.value = value;
			type = 2;
		} else if (Parser.PARENTHESIS.indexOf(value) != -1) {
			this.value = value;
			type = 1;
		} else {
			try {
				Double.parseDouble(value);
			} catch (NumberFormatException e) {
				throw new IllegalStateException(value + " umm error!");
			}
			this.value = value;
			type = 0;
		}
	}
	
	public boolean isOperator() {
		return type == 2;
	}
	
	public Double getOperand() {
		if (isOperator())
			throw new IllegalStateException("Not an operand.");
		return Double.parseDouble(value);
	}
	
	public String getOperator() {
		if (!isOperator())
			throw new IllegalStateException("Not an operator.");
		return value;
	}
	
	public String getValue() {
		return value;
	}

}
