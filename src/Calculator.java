import java.util.*;

public class Calculator {
	//Expression expression;
	String expression;
	List <String> bound; 
	Question question;
	boolean redundantConversion;
	List <String> history; 
	String tempExpr = "";
	
	public Calculator(String e){
		expression = e;
		bound = new ArrayList <String> ();
	}
	
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
		String [] tempBound;
		int parenthCount = 0;
		int index = 0;
		
		while (!stripBrackets(e).equals("Halt")){
			e = stripBrackets(e);
		}
		
		
		if (e.contains(" ")){
			tempBound = e.split(" ");
			
			for (int i = 0; i < tempBound.length; i++){
				if(tempBound[i].contains("/")){
					if (tempBound[i].contains("(")){
						tempBound[i] = stripBrackets(tempBound[i]);
						bound.add(tempBound[i]);
					}
					else{
						bound.add(tempBound[i]);
					}
				}
			}
		}
		else{
			bound.add(e);
		}
		for (int i = 0; i <bound.size(); i++){
			System.out.println("In bound");
			System.out.println(bound.get(i));
		}
	}
	public String alphaConvert (String expr, String target, String choice){
		
		findBinding(expr);
		
		if (expr.contains(choice) || !expr.contains(target)){
			tempExpr = "Illegal substitution";
			return tempExpr;
		}
		
		for (int i = 0; i < bound.size(); i++){
			if (bound.get(i).contains(target) && !bound.get(i).contains("(")){
				tempExpr = bound.get(i).replace(target, choice);
				System.out.println("Target");
				System.out.println(bound.get(i));
				System.out.println("Replacement");
				System.out.println(tempExpr);
				tempExpr = expr.replaceFirst(bound.get(i), tempExpr);
				break;
			}
			else {
				tempExpr = "Invalid substitution";
			}
			}
			//tempExpr = expr.replace(target, choice);
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
