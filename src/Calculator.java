import java.util.*;

public class Calculator {
	//Expression expression;
	String expression;
	Question question;
	boolean redundantConversion;
	List history; 
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
				if (expr.charAt(i) == '^'){
					var.add(expr.substring(i+1, expr.indexOf('.', i)));
				}
				if (expr.contains(Character.toString('(')) || expr.contains(Character.toString(')'))){
					if (expr.charAt(i) == '.'){
					args.add(expr.substring(i+1, expr.indexOf(')', i)));
					}
					if (expr.charAt(i) == '('){
						bound.add(expr.substring(i+1, expr.indexOf (')')));
					}
				}
			}
			
			for (int i = 0; i < bound.size(); i++){
				System.out.println(bound.get(i));
			
			if (bound.get(i).contains(target) && !bound.get(i).contains("(")){
				tempExpr = bound.get(i).replace(target, choice);
				tempExpr = expr.replace(bound.get(i), tempExpr);
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
