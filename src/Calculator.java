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
	
	public String alphaConvert (String expr, String target, String choice){
		List <String> var = new ArrayList <String> ();
		List <String> args = new ArrayList <String> ();
		List <String> bound = new ArrayList <String> ();
		
		if (expr.contains(choice) || !expr.contains(target)){
			tempExpr = "Illegal substitution";
			return tempExpr;
		}
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
				System.out.println(tempExpr);
				System.out.println(bound.get(i));
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
