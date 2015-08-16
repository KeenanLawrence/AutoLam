import java.util.*;

public class Calculator {
	
	List <String> func; 
	List <String> bound;
	
	String tempExpr;
	
	public Calculator(String e){
		func = new ArrayList <String> ();
		bound = new ArrayList <String> ();
		tempExpr = "";
	}
	
	//Method to remove the outermost brackets in the expression
	public String stripBrackets (String e){
		int firstBracket = 0, lastBracket = e.length();
		
		if (e.indexOf("(") == 0){
			firstBracket = e.indexOf('(');
			lastBracket = e.lastIndexOf(')');
		
			return e.substring(firstBracket + 1, lastBracket);
		}
		else{
			return "Halt";
		}
	}
	
	//Method to find function names and things that are bound to the function
	public void findBinding (String e){
		int parenthCount = 0;
		int index = 0;
		
		//Remove all outermost brackets
		while (!stripBrackets(e).equals("Halt")){
			e = stripBrackets(e);
		}
		
		//Loop through the entire string and determine what to do 
		for (int i = 0; i < e.length(); i++){
			if (e.charAt(i) == '/'){
				func.add(Character.toString(e.charAt(i+1)));
			}
			if (e.charAt(i) == '.'){
				if (e.charAt(i+1)  == '('){
					index = i + 1;
					parenthCount++;
				
				while (parenthCount != 0){
					index++;
					if (e.charAt(index) == '('){
						parenthCount++;
					}
					if (e.charAt(index) == ')'){
						parenthCount--;
					}
				}
				bound.add(e.substring(i+2, index));
				}
				else{
					if (e.charAt(i+1) != '/'){
						bound.add(e.substring(i+1));
					}
					
				}
			}
		}
	}
	
	public String alphaConvert (String expr, String target, String choice){
		
		findBinding(expr);
		
		//If the choice of substitution is already in the expression,
		//or the thing to substitute does not exist, return an error.
		if (expr.contains(choice) || !expr.contains(target)){
			tempExpr = "Illegal substitution";
			return tempExpr;
		}
		
		//For debugging purposes
		for (int i = 0; i < func.size(); i++){
			System.out.println("Func");
			System.out.println(func.get(i));
		}
		for (int i = 0; i < bound.size(); i++){
			System.out.println("Bound");
			System.out.println(bound.get(i));
		}
		
		//Checks if the variable to replace is a function
		for (int i = 0; i < func.size(); i++){
			if (func.get(i).contains(target)){
				//If it is, check if there's a bounded variable of the same name
				for (int k = 0; k < bound.size(); k++){
					if (bound.get(k).contains(target)){
						//Replace the first occurrence of the function name
						tempExpr = expr.replaceFirst(target, func.get(i).replace(target, choice));
						System.out.println(tempExpr);
						//Replace the bounded variable name
						tempExpr = tempExpr.replaceFirst(target, bound.get(k).replace(target, choice));
						break;
					}
				}
			}
			
			if (func.get(i).contains(target)){
				//Check if there's a bounded variable of the same name. If not, just change the function name
				for (int k = 0; k < bound.size(); k++){
					if (!bound.get(k).contains(target)){
						tempExpr = expr.replaceFirst(target, choice);
						break;
					}	
				}
			}
		}
		return tempExpr; 
	}
	
	public String betaReduce (String expr){
		tempExpr = expr;
		//TODO
		return tempExpr;
	}
	
	public String etaConvert (String expr){
		tempExpr = expr;
		//TODO
		return tempExpr;
	}
}
