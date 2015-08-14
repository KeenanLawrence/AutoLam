
public class Expression {
	
	//Instance variables
	private String expression;
	private String[] labels;
	
	//Hard-coded string used to define valid symbols to be used in lambda expressions (should allow for config in final version)
	private final String validSymbols = "qwertyuiopasdfghjklzxcvbnm. 1234567890()QWERTYUIOPASDFGHJKLZXCVBNM/";
	
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
	public boolean validExpression (String e){

		boolean check = false;
		
		//Keeps track of how many opening and closing braces there are.
		//If the count is positive, there's excess opening braces. Zero for equality, negative for excess closing braces. 
		int parenthCount = 0;
		
		//Breaks the expression into a collection of symbols
		char [] symbols = new char [e.length()];
		for (int i = 0; i < e.length(); i++){
			symbols [i] = e.charAt(i);
		}
		
		//Checks that the start of the expression is valid
		if (symbols[0] == '/' || symbols[0] == '('){
			System.out.println("Yes!");
		
			//If it is, check that the rest of the expression is valid
	
			for (int i = 0; i < symbols.length; i++){
			System.out.println("Yes!");
			//Start by checking that the symbols are valid
			if (validSymbols.contains(Character.toString(symbols[i]))){
				
				//If it's a lambda, make sure that the next character isn't a lambda, period or closing brace
				if (symbols[i]=='/'){
					try {
							if (symbols[i+1]== '.' || symbols[i+1] == '/' || symbols[i+1] == ')'){
								check = false;
								break;
							}
					}
					//Catches an index out of bounds error, but returns false since an expression cannot end with these combination of symbols
					catch (Exception e1){
						check = false;
						break;
					}
				}
				
				//If it's a period, make sure that the next character isn't a period or closing brace
				if (symbols[i]=='.'){
					try {
							if (symbols[i+1]== '.' || symbols[i+1] == ')'){
								check = false;
								break;
							}
					}
					//Catches an index out of bounds error, but returns false since an expression cannot end with these combination of symbols
					catch (Exception e1){
						check = false;
						break;
					}
				}
				
				//If it's an opening brace, the next character cannot be a period or closing brace
				if (symbols[i] == '('){
					parenthCount++;
					
					try {
							if (symbols[i+1] == '.' || symbols[i+1] == ')'){
								check = false;
								break;
							}
					}
					//Catches index out of bounds exception but returns false since an expression cannot end with a closing brace.
					catch (Exception e2){
						check = false;
						break;
					}
					
				}
				
				//If it's an closing brace, the next character cannot be a period
				if (symbols[i] == ')'){
					parenthCount--;
					
					try {
							if (symbols[i+1]== '.'){
								check = false;
								break;
							}
					}
					catch (Exception e3){
						//Index out of bounds
					}
				}
				
				//Check for double spacing
				if (symbols[i] == ' '){
					try {
							if (symbols[i+1]== ' '){
								check = false;
								break;
							}
					}
					catch (Exception e3){
						//TODO
						//Remove the space
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
		}
		else {
			return false;
		}
		return check;
	}
}