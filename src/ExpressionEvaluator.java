// TODO: Auto-generated Javadoc
public class ExpressionEvaluator {
	// These are the required error strings for that MUST be returned on the appropriate error 
	// for the JUnit tests to pass
	private static final String PAREN_ERROR = "Paren Error: ";
	private static final String OP_ERROR = "Op Error: ";
	private static final String DATA_ERROR = "Data Error: ";
	private static final String DIV0_ERROR = "Div0 Error: ";

	// The placeholders for the two stacks
	private GenericStack<Double> dataStack;
	private GenericStack<String>  operStack;
	
	/**
	 * Convert to tokens. Takes a string and splits it into tokens that
	 * are either operators or data. This is where you should convert 
	 * implicit multiplication to explict multiplication. It is also a candidate
	 * for recognizing negative numbers, and then including that negative sign
	 * as part of the appropriate data token.
	 *
	 * @param str the str
	 * @return the string[]
	 */
	private String[] convertToTokens(String str) {
		str = padTokensWithSpaces(str);
		str = str.replaceAll("\\s+", " ");
		str = convertMultiplication(str);
		str = convertNegative(str);
		str = convertMultiplicationNegative(str);
		
		String[] tokens = str.split("\\s+");
		return tokens;
	}
	
	private String padTokensWithSpaces(String in) {
		return in.replaceAll("(\\-|\\+|\\*|\\/|\\(|\\))", " $1 ").replaceFirst("\\s*", "");
	}

	private String convertMultiplication(String in) {
		in = in.replaceAll("(\\)) (\\()", "$1 * $2");
		in = in.replaceAll("(\\d+(\\.\\d+)?) \\(", "$1 * (");
		in = in.replaceAll("(\\)) (\\d+(\\.\\d+)?)", "$1 * $2");
		return in;
	}
	
	private String convertNegative(String in) {
		return in.replaceAll("(\\-|\\+|\\*|\\/|\\() (\\-) (\\d+(\\.\\d+)?)", "$1 $2$3").replaceAll("^\\- (\\d+(\\.\\d+)?)", "-$1");
	}
	
	private String convertMultiplicationNegative(String in) {
		for (int i = 0; i < in.length() - 2; i++) {
			if (in.charAt(i) == '-' && in.charAt(i + 2) == '(') {
				int rightIdx = indexOfMatchingParen(in, i + 2);
				String before = in.substring(0, i);
				String between = in.substring(i + 2, rightIdx + 1);
				String after = in.substring(rightIdx + 1);
				if (!before.matches("\\s*") && !(i >= 2 && "+-*/".contains(in.substring(i - 2, i - 1)))) {
					before = before + " + ";
				}
				in = before + "( -1 * " + between + " ) " + after;
			}
		}
		
		return in;
	}
	
	private int indexOfMatchingParen(String in, int leftIdx) {
		int depth = 0;
		
		for (int i = leftIdx; i < in.length(); i++) {
			char c = in.charAt(i);
			
			if (c == '(') {
				depth++;
			} else if (c == ')') {
				depth--;
			}
			
			if (depth == 0) {
				return i;
			}
		}
		
		return -1;
	}
	
	private boolean areParenthesesBalanced(String in) {
		int depth = 0;
		
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			
			if (c == '(') {
				depth++;
			} else if(c == ')') {
				depth--;
			}
			
			if (depth < 0) {
				return false;
			}
		}
		
		return depth == 0;
	}
	
	private boolean validParens(String in) {
		if (!areParenthesesBalanced(in)) {
			return false;
		}
		
		if (in.matches("\\(\\s*\\)")) {
			return false;
		}
		
		return true;
	}
	
	private boolean validData(String in) {
		if (!in.matches("(\\d|\\-|\\+|\\*|\\/|\\(|\\)|\\.|\\s)+")) {
			return false;
		}
		
		if (in.matches(".*\\d+(\\.\\d+)?\\s+\\d+(\\.\\d+)?.*")) {
			return false;
		}
		
		return true;
	}
	
	private boolean noOpError(String in) {
		String numRegex = "\\d+(\\.\\d+)?";
		String opRegex = "(\\+|\\*|\\/)";
		String opWithMinusRegex = "(\\+|\\*|\\/|\\-)";
		
		if (in.matches(".*" + opRegex + "\\s*" + opRegex + ".*")) {
			return false;
		}
		
		if (in.trim().matches(".*" + opWithMinusRegex + "$")) { // operator at the very end
			return false;
		}
		
		if (in.trim().matches("^" + opRegex + ".*")) { // operator at the very start
			return false;
		}
		
		if (in.matches(".*\\-\\s*\\-\\s*" + opWithMinusRegex + ".*")) {
			return false;
		}
		
		if (in.matches(".*(\\-|\\+|\\*|\\/)(\\))(\\-|\\+|\\*|\\/).*")) {
			return false;
		}
		
		return true;
	}
	
	private String checkForErrors(String in) {
		if (!validParens(in)) {
			return PAREN_ERROR;
		}
		
		if (!validData(in)) {
			return DATA_ERROR;
		}
		
		if (!noOpError(in)) {
			return OP_ERROR;
		}
		
		return "";
	}
	
	/**
	 * Evaluate expression. This is it, the big Kahuna....
	 * It is going to be called by the GUI (or the JUnit tester),
	 * and:
	 * a) convert the string to tokens
	 * b) if conversion successful, perform static error checking
	 *    - Paren Errors
	 *    - Op Errors 
	 *    - Data Errors
	 * c) if static error checking is successful:
	 *    - evaluate the expression, catching any runtime errors.
	 *      For the purpose of this project, the only runtime errors are 
	 *      divide-by-0 errors.
	 *
	 * @param str the str
	 * @return the string
	 */
	protected String evaluateExpression(String str) {
		String checkForErrors = checkForErrors(str);
		
		if (!checkForErrors.equals("")) {
			return checkForErrors;
		}
		
        dataStack =  new GenericStack<Double>();
		operStack =  new GenericStack<String>();
		
		String origStr = str;
		String[] tokens = convertToTokens(str);
		
		try {
			for (String token : tokens) {
				if (token.matches("\\-?\\d+(\\.\\d+)?")) {
					dataStack.push(Double.parseDouble(token));
				} else if (token.length() == 1 && "-+*/()".contains(token)) {
					if (operStack.empty() || token.equals("(")) {
						operStack.push(token);
						continue;
					} else if (isHigherPrecedence(token, operStack.peek())) {
						operStack.push(token);
						continue;
					} else if (token.equals(")")) {
						while (!operStack.peek().equals("(")) {
							evaluateTOS();
						}
						operStack.pop();
						continue;
					}
					while (!operStack.empty() && !operStack.peek().equals("(") && !isHigherPrecedence(token, operStack.peek())) {
						evaluateTOS();
					}
					operStack.push(token);
				}
			}
			
			while (!operStack.empty()) {
				evaluateTOS();
			}
		} catch (ArithmeticException ex) {
			return DIV0_ERROR;
		}
		
		return (origStr + "=" + dataStack.peek());
	}
	
	private boolean isHigherPrecedence(String token, String stack) {
		return (token.equals("*") || token.equals("/")) && (stack.equals("+") || stack.equals("-"));
	}
	
	private void evaluateTOS() {
		double d2 = dataStack.pop();
		double d1 = dataStack.pop();
		String op = operStack.pop();
		
		dataStack.push(performAction(d1, d2, op));
	}
	
	private double performAction(double d1, double d2, String op) {
		switch (op) {
			case "+":
				return d1 + d2;
			case "-":
				return d1 - d2;
			case "*":
				return d1 * d2;
			case "/":
				if(d2 == 0)
					throw new ArithmeticException("Division by 0");
				return d1 / d2;
			default:
				System.out.println("Error: performAction() resulted in bad operation"); // prints error message if no valid operation type
				return 0.0;
		}
	}
}