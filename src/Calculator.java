import java.util.*;

public class Calculator {
	//Expression expression;
	String expression;
	Question question;
	boolean redundantConversion;
	List <String> history; 
	String tempExpr = "";
	
	public Calculator(String e){
		expression = e;
	}
	
	public String stripBrackets (String e){
		int firstBracket = 0, lastBracket = e.length();
		
		if (e.contains("(")){
			firstBracket = e.indexOf('(');
			lastBracket = e.lastIndexOf(')');
		}
		
		return e.substring(firstBracket + 1, lastBracket);
	}
	
	public List <String> findBinding (String e){
		List <String> bound = new ArrayList <String> ();
		
		int parenthCount = 0;
		int index = 0;

		for (int i = 0; i <e.length(); i++){
			tempExpr = stripBrackets(e);
		}
		return bound;
	}
	public String alphaConvert (String expr, String target, String choice){
		List <String> var = new ArrayList <String> ();
		List <String> args = new ArrayList <String> ();
		List <String> bound = new ArrayList <String> ();
		
		if (expr.contains(choice) || !expr.contains(target)){
			tempExpr = "Illegal substitution";
			return tempExpr;
		}
		
		//Rework binding
		else{
			for (int i = 0; i < expr.length(); i++){
				if (expr.charAt(i) == '/'){
					var.add(expr.substring(i+1, expr.indexOf('.', i))); //change to single character
				}
				if (expr.contains(Character.toString('(')) || expr.contains(Character.toString(')')) || expr.contains(Character.toString('/'))){
					if (expr.charAt(i) == '('){
						bound.add(expr.substring(i+2, expr.indexOf (')')));
					}
					if (expr.charAt(i) == '/'){
						if (expr.contains(Character.toString(' ')) && !expr.contains(Character.toString('('))){
							bound.add(expr.substring(i+1, expr.indexOf (' ')));
						}
						else{
							bound.add(expr.substring(i+1));
						}
					}
				}
			}
			
			for (int i = 0; i < bound.size(); i++){
				System.out.println(bound.get(i));
			
			if (bound.get(i).contains(target) && !bound.get(i).contains("(")){
				tempExpr = bound.get(i).replace(target, choice);
				System.out.println(bound.get(i));
				System.out.println(tempExpr);
				tempExpr = expr.replaceFirst(bound.get(i), tempExpr);
				break;
			}
			else {
				tempExpr = "Invalid substitution";
			}
			}
			//tempExpr = expr.replace(target, choice);
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
	//This is a change to check if branches are cool
}
