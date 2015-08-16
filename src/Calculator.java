import java.util.*;

public class Calculator {
	//Expression expression;
	String expression;
	Question question;
	boolean redundantConversion;
	List <String> history; 
	List <String> func; 
	List <String> arg; 
	String tempExpr;
	
	public Calculator(String e){
		expression = e;
		func = new ArrayList <String> ();
		arg = new ArrayList <String> ();
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
	
	public void findBinding (String e){
		int parenthCount = 0;
		int index = 0;
		
		while (!stripBrackets(e).equals("Halt")){
			e = stripBrackets(e);
		}
		
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
				arg.add(e.substring(i+1, index));
				}
				else{
					arg.add(e.substring(i+1));
					
				}
			}
		}
	}
	public String alphaConvert (String expr, String target, String choice){
		boolean random = false;
		tempExpr = "";
		findBinding(expr);
		
		if (expr.contains(choice) || !expr.contains(target)){
			tempExpr = "Illegal substitution";
			return tempExpr;
		}
		
		//For debugging purposes
		/*
		for (int i = 0; i < func.size(); i++){
			System.out.println("Func");
			System.out.println(func.get(i));
		}
		for (int i = 0; i < arg.size(); i++){
			System.out.println("Arg");
			System.out.println(arg.get(i));
		}
		*/
		
		for (int i = 0; i < func.size(); i++){
			
			if (func.get(i).contains(target) && arg.get(i).contains(target)){
				tempExpr = expr.replaceFirst(target, func.get(i).replace(target, choice));
				tempExpr = tempExpr.replaceFirst(target, choice);
				random = true;
				
			}
			if (!func.get(i).contains(target) && arg.get(i).contains(target) && random){
				tempExpr = tempExpr.replace(target, choice);
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
