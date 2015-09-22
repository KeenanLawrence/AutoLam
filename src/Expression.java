//Class definition
public class Expression {
	
	//Instance variable
	private String expression;

	//Hard-coded string used to define valid symbols to be used in lambda expressions
	private static final String ALPHA = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
	private final String special = ". ()";
	private final String lambda = "/";
	
	private final String validSymbols = ALPHA + special + lambda;
	
	//Constructor sets expression
	public Expression (String e){
		setExpression(autocorrectExpression(e));
	}
	
	//Mutator method to set expression
	public void setExpression (String e){
		expression = e;
	}
	
	//Accessor method used to get expression
	public String getExpression(){
		return expression;
	}
	
	//Accessor method used to get a list of valid alpha characters
	public static String getAlpha (){
		return ALPHA;
	}
	
	//Method to remove outermost brackets
	public static String removeOuterBrackets (String expr){
		String tempExpr = expr;

		//If the expression starts with a bracket,
		while (tempExpr.charAt(0) == '('){
			//but there's a closing bracket before an opening bracket,
			if (tempExpr.indexOf(')', 1) < tempExpr.indexOf('(', 1)){
				//do nothing because the closing bracket isn't at the end of the expression, eg. (/x.x y) (/z.z a)
				break; 
			}
			
			//Otherwise, remove the outer brackets and check that new string again
			if (tempExpr.lastIndexOf(')') == tempExpr.length() - 1){
				tempExpr = tempExpr.substring (1, tempExpr.length() - 1);
			}
			else{
				//Stop if there is no bracket on the end, eg. (/x.x) y
				break;
			}
		}
		return tempExpr;
	}
	
	//A method to insert whitespace at appropriate places
	//This is important for the betaReduce method in the Calculator class
	public static String insertWhitespace (String expr){
		String tempExpr = expr;
		
		//Loop through the string and insert a space if:
		for (int i = 0; i < tempExpr.length(); i++){
			try{
				//There are two brackets next to each other, )( or there is a closing brace and an alpha character, )a
				if (tempExpr.charAt(i) == ')' && (tempExpr.charAt(i+1) == '(' || getAlpha().contains(Character.toString(tempExpr.charAt(i+1))))){
					tempExpr = tempExpr.substring(0, i+1) + " " + tempExpr.substring(i+1);
				}
				//There is an opening bracket with two alpha characters after it, (ab
				if (tempExpr.charAt(i) == '(' && getAlpha().contains(Character.toString(tempExpr.charAt(i+1))) && getAlpha().contains(Character.toString(tempExpr.charAt(i+2)))){
					tempExpr = tempExpr.substring(0, i+2) + " " + tempExpr.substring(i+2);
				}
				//There is an alpha character followed by an opening bracket, a(
				if (tempExpr.charAt(i+1) == '(' && getAlpha().contains(Character.toString(tempExpr.charAt(i)))){
					tempExpr = tempExpr.substring(0, i+1) + " " + tempExpr.substring(i+1);
				}
				//There are two alpha characters in succession, ab
				if (getAlpha().contains(Character.toString(tempExpr.charAt(i+1))) && getAlpha().contains(Character.toString(tempExpr.charAt(i)))){
					tempExpr = tempExpr.substring(0, i+1) + " " + tempExpr.substring(i+1);
				}
				//There is an alpha character followed by a lambda
				if (getAlpha().contains(Character.toString(tempExpr.charAt(i))) && tempExpr.charAt(i+1) == '/'){
					tempExpr = tempExpr.substring(0, i+1) + " " + tempExpr.substring(i+1);
				}
				
			}
			catch (Exception e){
				//Do nothing, it might be at the end of the string
			}
		}
		return tempExpr;
	}
	
	//A method to insert a lambda into the expression
	//This method is important for the alphaConvert and betaReduce methods in the Calculator class
	public static String insertLambda (String expr){
		String tempExpr = expr;
		
		//Cycle through the string,
		for (int i = 0; i < tempExpr.length(); i++){
			try{
				//and if you find a lambda and the second character after the lambda is an alpha, then it's also a function
				if (tempExpr.charAt(i) == '/' && getAlpha().contains(Character.toString(tempExpr.charAt(i+2)))){
					tempExpr = tempExpr.substring(0, i+2) + "./" + tempExpr.substring(i+2);
				}
			}
			catch (Exception e){
				//Do nothing, it might be at the end of the string
			}
		}
		return tempExpr;
	}
	
	//A method to remove all whitespace from an expression
	public static String removeWhitespace (String expr){
		String tempExpr = expr;
		tempExpr = tempExpr.replaceAll("\\s+", "");
		return tempExpr;
	}
	
	//This method auto-corrects the expression based on the above methods.
	//The order in which they execute is important
	public static String autocorrectExpression (String expr){
		String tempExpr = expr.trim();
		tempExpr = removeWhitespace(tempExpr);
		tempExpr = insertLambda(tempExpr);
		tempExpr = insertWhitespace(tempExpr);
		//tempExpr = removeOuterBrackets(tempExpr);
		tempExpr = putInBrackets(tempExpr);
		return tempExpr;
	}
	
	public static String putInBrackets(String expr){
		Calculator c = new Calculator();
		for(int i = 0; i!= expr.length();i++){
			if (expr.charAt(i) == '.' && expr.charAt(i+1) == '(' ){
				
			}
			else if(expr.charAt(i)=='(' && expr.charAt(i+1)=='/' && expr.charAt(i+4)!='('){
				if(expr.charAt(i)=='(' && expr.charAt(i+1)=='/' && expr.charAt(i+4)!=')'){
					
				
				expr = expr.substring(0,i+4)+"("+expr.substring(i+4, c.findMatchingBrace(expr,i))+")"+expr.substring(c.findMatchingBrace(expr,i));
				}
			}
			else if (expr.charAt(i) == '.' && expr.charAt(i+1) == '/'){
				expr = expr.substring(0, i+1) + "(" + expr.substring(i+1) + ")";
			}
		}
		return expr;
	}
	
	
	
	/*	Method to check if an expression is a valid lambda expression.
	 *  1.) It may only contain the predefined symbols.
	 *  2.) Parentheses need to match up.
	 *  3.) There cannot be two lambdas in succession.
	 *  4.) There cannot be two periods '.' in succession.
	 *  5.) A lambda cannot come directly before a period.
	 *  6.) A closing brace cannot come directly after an opening brace.
	 */
	public boolean validExpression (){
		String expr = this.expression;
		//Keeps track of how many opening and closing braces there are.
		//If the count is positive, there's excess opening braces. Zero for equality, negative for excess closing braces. 
		int parenthCount = 0;
		
		//Breaks the expression into a collection of symbols
		char [] symbols = new char [expr.length()];
		
		//If there's an invalid symbol anywhere, stop checking
		for (int i = 0; i < expr.length(); i++){
			symbols [i] = expr.charAt(i);
			if (!validSymbols.contains(Character.toString(symbols[i]))){
				return false;
			}
		}
		
		//Checks that the start of the expression is valid
		if (symbols[0] == '.' || symbols[0] == ')'){
			return false;
		}
		
		//If it is, check that the rest of the expression is valid, based on the six rules above
		for (int i = 0; i < symbols.length; i++){
				switch (symbols[i]){
				case '/':
					try{
						if (symbols[i+1] == '/' || symbols[i+1] == ')' || symbols[i+1] == '.' || symbols[i+1] == ' '){
							return false;
						}
						if (symbols[i+2] != '.'){
							return false;
						}
					}
					catch (Exception e){
						return false;
					}
					break;
				case '.':
					try{
						if (symbols[i+1] == ')' || symbols[i+1] == ' ' || symbols[i+1] == '.'){
							return false;
						}
					}
					catch (Exception e){
						return false;
					}
					try{
						if(symbols[i+2] == '.'){
							return false;
						}
					}
					catch (Exception e){
						//Do nothing, it might be a simple expression such as /x.x
					}
					break;
				case '(':
					parenthCount++;
					try{
						if (symbols[i+1] == ')' || symbols[i+1] == '.' || symbols[i+1] == ' '){
							return false;
						}
					}
					catch (Exception e){
						return false;
					}
					break;
				case ')':
					if (parenthCount == 0){
						return false;
					}
					parenthCount--;
					try{
						if (symbols[i+1] == ' ' || symbols[i+1] == ')'){
						}
						else{
							return false;
						}
					}
					catch (Exception e){
						//Do nothing, might be at end of String
					}
					break;
				case ' ':
					try{
						if (symbols[i+1] == ' ' || symbols[i+1] == ')' || symbols[i+1] == '.'){
							return false;
						}
					}
					catch (Exception e){
						//Do nothing, expression might end with whitespace. Should be trimmed by expr.trim()
					}
					break;
				}
		}
		//If the number of closing braces and opening braces don't match up, it's invalid.
		if (parenthCount != 0){
			return false;
		}
		return true;
	}
	
	public String toString(){
		return this.expression;
	}
}

