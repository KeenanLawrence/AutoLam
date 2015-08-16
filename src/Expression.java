
public class Expression {
	
	//Instance variables
	private String expression;
	private String[] labels;
	
	//Hard-coded string used to define valid symbols to be used in lambda expressions (should allow for config in final version)
	private final String alpha = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
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
		setExpression(e);
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
	
	public String getAlpha (){
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

		boolean check = false;
		
		//Keeps track of how many opening and closing braces there are.
		//If the count is positive, there's excess opening braces. Zero for equality, negative for excess closing braces. 
		int parenthCount = 0;
		
		//Breaks the expression into a collection of symbols
		char [] symbols = new char [expr.length()];
		
		for (int i = 0; i < expr.length(); i++){
			symbols [i] = expr.charAt(i);
		}
		
		//If it is, check that the rest of the expression is valid
		for (int i = 0; i < symbols.length; i++){
				
			//Start by checking that the symbols are valid
			if (validSymbols.contains(Character.toString(symbols[i]))){
				//Determines what to do with a given symbol
				switch (symbols [i]){
					case '/':
						try{
							//Checks if the next character is a variable
							if (!alpha.contains(Character.toString(symbols [i+1]))){
								check = false;
								break;
							}
							//Enforces single name variable, like x and not x1
							if (symbols [i+2] != '.'){
								check = false;
								break;
							}
							break;
						}
						//Expressions cannot end with a lambda or lambda <variable>
						catch (Exception e){
							check = false;
							break;
						}
				
					case '.':
						try{
							if (!alpha.contains(Character.toString(symbols[i-1]))){
								check = false;
								break;
							}
							if (!alpha.contains(Character.toString(symbols[i+1])) && symbols [i+1] != '/'){
								check = false;
								break;
							}
							break;
						}
						catch (Exception e){
							check = false;
							break;
						}
						
					case ' ':
						try{
							if (symbols [i+1] == ' ' || symbols [i+1] == ')' || symbols [i+1] == '.'){
								check = false;
								break;
							}
							break;
						}
						catch (Exception e){
							setExpression(expr.trim());
							break;
						}
					
					case '(':
						parenthCount ++;
						try{
							if (symbols [i+1] != '/' || symbols [i+1] == '('){
								check = false;
								break;
							}
							break;
						}
						catch (Exception e){
							check = false;
							break;
						}
					
					case ')':
						parenthCount --;
						try{
							if (symbols [i+1] != ' ' || symbols [i+1] == ')'){
								check = false;
								break;
							}
							break;
						}
						catch (Exception e){
							//Do nothing, this might be the closing brace of the entire expression. We'll strip braces later.
						}
				}
				//This is to verify that the symbol at i is a valid symbol
				check = true;
			}
				//If it's not a valid symbol, stop checking and return false
			else{
					check = false;
					break;
			}
		}
		
		//If the number of closing braces and opening braces don't match up, it's invalid.
		if (parenthCount != 0){
			check = false;
		}
		
		return check;
	}
}