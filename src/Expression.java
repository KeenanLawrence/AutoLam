
public class Expression {
	
	//Instance variables
	private String expression;
	private String[] labels;
	
	//Hard-coded string used to define valid symbols to be used in lambda expressions (should allow for config in final version)
	private static final String alpha = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	//private final String numeric = "1234567890";
	private final String special = ". ()";
	private final String lambda = "/";
	
	//Numeric not included for prototype
	private final String validSymbols = alpha + special + lambda;
	
	//Constructor with labels
	public Expression (String e, String [] l){
		setExpression(e);
		setLabels(l);
	}
	
	//Constructor without labels (used in prototype)
	public Expression (String e){
		setExpression(e.trim());
	}
	
	public void setExpression (String e){
		expression = e;
	}
	
	public void setLabels (String [] l){
		int length = l.length;
		labels = new String [length];
		
		for (int i = 0; i < length; i++){
			labels[i] = l[i];
		}
	}
	
	public String getExpression(){
		return expression;
	}
	
	public String [] getLables(){
		return labels;
	}
	
	public static String getAlpha (){
		return alpha;
	}
	/*	Method to check if an expression is a valid lambda expression.
	 *  1.) It may only contain the predefined symbols.
	 *  2.) Parentheses need to match up.
	 *  3.) There cannot be two lambdas in succession.
	 *  4.) There cannot be two periods '.' in succession.
	 *  5.) A lambda cannot come directly before a period.
	 *  6.) A closing brace cannot come directly after an opening brace.
	 *  7.) No two spaces in a row.
	 *  More rules to be added here...
	 */
	public boolean validExpression (String expr){

		//Keeps track of how many opening and closing braces there are.
		//If the count is positive, there's excess opening braces. Zero for equality, negative for excess closing braces. 
		int parenthCount = 0;
		
		//Breaks the expression into a collection of symbols
		char [] symbols = new char [expr.length()];
		
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
		
		//If it is, check that the rest of the expression is valid
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
	
	//Functionality to be decided
	/*
	public String removeWhitespace (String expr, int index){
		String tempExpr = expr.substring(index);
		return tempExpr;
	}
	*/
}