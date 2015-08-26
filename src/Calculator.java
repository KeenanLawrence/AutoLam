import java.util.*;

public class Calculator {
	
	List <String> func; 
	List <String> bound;
	
	String tempExpr;
	//Expression expression;
	String expression;


	public Calculator(String e){
		func = new ArrayList <String> ();
		bound = new ArrayList <String> ();
		tempExpr = "";
	}
	
	public List <String> getFunc (){
		return func;
	}
	
	public List <String> getBound(){
		return bound;
	}
	
	public boolean isAlpha (char c){
		if (Expression.getAlpha().contains(Character.toString(c))){
			return true;
		}
		else{
			return false;
		}
	}
	//Method to find function names and things that are bound to the function
	public void findBinding (String e){
		func = new ArrayList <String> ();
		bound = new ArrayList <String> ();
		tempExpr = e;
		
		//Used to keep track of parentheses
		int parenthCount = 0;
		
		//Variable used for linking functions with its bound variables
		int index = -1;
		
		//Loop through the entire string and determine what to do 
		for (int i = 0; i < e.length(); i++){
			//If it's a lambda,
			if (e.charAt(i) == '/'){
				index++;
				//Add it to the list of functions
				func.add(index, Character.toString(e.charAt(i+1)));
				
				//If you don't encounter a whitespace,
				while (e.charAt(i+1) != ' '){
					//and you encounter a lambda
					if (e.charAt(i+1) == '/'){
						try{
							//and the lambda is enclosed in a bracket
							if (e.charAt(i) == '('){
								func.add(index+1, Character.toString(e.charAt(i+2)));
							}
							//and the lambda is not enclosed in a bracket
							else{
								func.set(index, func.get(index) + "./" + Character.toString(e.charAt(i+2)));
							}
						}
						catch (Exception error){
							//Index out of bounds
						}
					}
					
					//If it was a period, determine the things that are bound to it.
					else if (e.charAt(i) == '.'){
							try{
								
								if (e.charAt(i+2) == ' '){
									System.out.println("Adding " + e.charAt(i+1));
									bound.add(index, Character.toString(e.charAt(i+1)));
								
								}
								
								//If it is enclosed in parentheses, include everything up until that point.
								else{
									if (e.charAt(i+1) == '('){
										int startBound = i+2;
										int endBound = i+2;
										parenthCount = 0;
										parenthCount++;
										while (parenthCount != 0){
										if (e.charAt(endBound) == '('){
											parenthCount++;
										}
										if (e.charAt(endBound) == ')'){
											parenthCount--;
										}
										endBound++;
										}
										bound.add(index, Expression.removeOuterBrackets(e.substring(startBound, endBound - 1)));
									}
								//It also may have occurred towards the end of a section (eg /x.(/y.y))
									else if (e.charAt(i+2) == ')'){
										bound.add(index, Character.toString(e.charAt(i+1)));
									}
								}
							}
							catch (Exception error){
								if (isAlpha(e.charAt(i+1))){
									System.out.println("Adding " + e.charAt(i+1));
									bound.add(index, Character.toString(e.charAt(i+1)));
								}
							}
						}
					i++;
					if (i == e.length() - 1){
						break;
					}
				}			
			}
		}
	}
				
	//The method that does the alpha conversion if given a target and a replacement (called choice)
	public String alphaConvert (String expr, String target, String choice){
		tempExpr = "";
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
			if (func.get(i).contains(target) && bound.get(i).contains(target)){
				//If it is, check if there's a bounded variable of the same name
				//Replace the first occurrence of the function name
				tempExpr = expr.replaceFirst(func.get(i), func.get(i).replace(target, choice));
				//Replace the bounded variable name
				tempExpr = tempExpr.replaceFirst(bound.get(i), bound.get(i).replace(target, choice));
				break;
			}
			
			//If it's a function and there's no matching bound variable, just change the function name
			if (func.get(i).contains(target) && !bound.get(i).contains(target)){
						tempExpr = expr.replaceFirst(target, choice);
			}
		}
		
		if(tempExpr.equals("")){
			tempExpr = "Illegal Substitution";
		}
		return tempExpr; 
	}
	
	//TODO
	public String autoAlphaConvert(String expr){
		tempExpr = "";
		findBinding(expr);
		String funcNames [] = new String [func.size()];
		int index = 0;
		boolean check = false;
		for (int i = 0; i < func.size(); i++){
			funcNames [i] = func.get(i);
			
		}
		
		while (index + 1 < func.size()){
			if (funcNames[index+1].contains(funcNames[index])){
				check = true;
			}
			index++;
		}
		if (check){
			System.out.println("It worked");
		}
		return tempExpr;
	}
	
	//TODO
	//Works on basic cases
	public boolean alphaEquivalent (String expr1, String expr2){
		boolean equiv = false;
		tempExpr = "";
		
		if (expr1.equals(expr2)){
			return true;
		}
		
		Calculator objCal1 = new Calculator (expr1);
		Calculator objCal2 = new Calculator (expr2);
		
		objCal1.findBinding(expr1);
		objCal2.findBinding(expr2);
		List <String> tempFunc1 = objCal1.getFunc();
		List <String> tempFunc2 = objCal2.getFunc();
		
		for (int i = 0; i < tempFunc1.size(); i++){
			if (!tempFunc1.get(i).contains(tempFunc2.get(i))){
				for (int k = 0; k < tempFunc2.get(i).length(); k++)
				if(Expression.getAlpha().contains(Character.toString(tempFunc2.get(i).charAt(k)))){
					tempExpr = alphaConvert(expr1, Character.toString(tempFunc1.get(i).charAt(i)), Character.toString(tempFunc2.get(i).charAt(k)));
					break;
				}
			}
		}
		if (tempExpr.equals(expr2)){
			return true;
		}
		return equiv;
	}
	
	//does what you think it does (actually probably doesn't though)
	public String betaReduce (String expr){
		tempExpr = expr;
		int fSpace = findRelevantSpace(expr,0);//this space separates a function from its argument in an application
		if(fSpace==-1){//case where this is not an application
			int firstBrace=expr.indexOf('(');
			if(firstBrace==-1){
				return tempExpr;
			}else{
				//System.out.println("no application, let's strip things down a bit");
				return tempExpr.substring(0, firstBrace)+ betaReduce(tempExpr.substring(firstBrace+1, tempExpr.length()-1));
			}
		}else{
			int fLam = expr.indexOf('/')+1;
			fSpace = findRelevantSpace(expr,0);
			if(fLam==0){
				return "(" + expr + ")";
			}else if(fLam>fSpace){
				return tempExpr.substring(0, fSpace)+betaReduce(tempExpr.substring(fSpace));//no reduction needs to be done on whatever is before the space.
			}else{
				if(fLam>2){
					//System.out.println("fSpace needs to change from " + fSpace + " to " + ((findMatchingBrace(tempExpr,(fLam-2)))+1));
					fSpace=((findMatchingBrace(tempExpr,(fLam-2)))+1);
				}
				String boundVar=tempExpr.charAt(fLam)+"";//find the first bound variable
				//System.out.println("I have found the bound variable and it is " + boundVar);				
				int argSpace = findRelevantSpace(expr,fSpace+1);//this is the end of the first argument
				//System.out.println("keeping an eye on argSpace, currently it's at "+argSpace);
				if(argSpace==-1){
					argSpace=tempExpr.length();
					String arg = tempExpr.substring(fSpace+1, argSpace);//extracting the argument
					//System.out.println("The relevant space is at position " + fSpace + " and the argument is " + arg);
					String otherArgs = tempExpr.substring(argSpace);//all the arguments after the one we are using
					String tempExprBind=expr.substring(0, fSpace);//everything between start and argument
					//System.out.println("unstripped: " + tempExprBind);
					tempExprBind = stripUpTo(tempExprBind, fLam+2);//stripped up to the start of the binding
					//System.out.println("we will be replacing all the " + boundVar + "'s in " + tempExprBind + " with " + arg);
					tempExprBind = tempExprBind.replaceAll(boundVar, arg);//the reduction bit
					//String test = "x";
					//System.out.println("Testing: " + test.replaceAll("x", "y"));
					//System.out.println("behold, the reduced form of the bound section: " + tempExprBind);
					tempExpr = tempExprBind; //putting the reduced section back with the remaining args
				}else{
					//System.out.println("the expression we are working with is " + tempExpr);
					//int tArg=argSpace;
					String secondPart = tempExpr.substring(argSpace);
					String firstPart=betaReduce(tempExpr.substring(0, argSpace));
					tempExpr=firstPart+secondPart;
				}	
			}		
		}
		return tempExpr;
	}
	
	public int findMatchingBrace(String expr, int pos){
		int out = -1;
		int bracketCount=0;
		for(int i = pos; i!= expr.length();i++){
			if(expr.charAt(i)=='('){
				bracketCount++;
			}
			if(expr.charAt(i)==')'){
				bracketCount--;
			}
			if(bracketCount==0){
				out=i;
				break;		
			}
		}
		return out;
	}
	
	//Glorified substring method - takes away all characters up to (but not including) a specified point. Also removing matching closed braces for any open braces in removed section.
	public String stripUpTo(String e, int point){
		String out = e;
		//System.out.println("Removing up to position: " + point);
		int removed=0;//keep track of how many opening braces have been removed
		int tempPoint=point;
		for(int i =0;i!=point;i++){//find all the opening brackets in the section to be removed and delete the matching pairs
			if(e.charAt(i)=='('){
				e=stripMatchingBracket(e,i);
				//System.out.println("I stripped something!");
				removed++;
				i--;
				point--;
			}
		}
		//System.out.println("accommodating for " + (point-removed) + " bracket deletions");
		out=e.substring((tempPoint-removed));//accommodates for the brackets that we have already deleted
		return out;
	}
	
	//given a position (at which there is an opening brace), finds the closing brace and removes both of them
	public String stripMatchingBracket(String expr, int pos){
		String before=expr.substring(0, pos);//everything before the brace
		String middle="";//everything between the two braces
		String after="";//everything after the brace 
		int bracketCount=0;
		int i=pos;
		for(i = pos; i!= expr.length();i++){
			if(expr.charAt(i)=='('){
				bracketCount++;
			}
			if(expr.charAt(i)==')'){
				bracketCount--;
			}
			if(bracketCount==0){
				middle=expr.substring(pos+1, i);
				break;		
			}
		}
		after=expr.substring(i+1);
		return before+middle+after;
	}
	
	public int findRelevantSpace(String expr, int startPos){
		int out = -1;
		int bCount=0;//brace count
		for(int i = startPos; i!=expr.length();i++){
			if(expr.charAt(i)=='('){
				bCount++;
			}
			if(expr.charAt(i)==')'){
				bCount--;
			}
			if((expr.charAt(i)==' ') && (bCount<=0)){
				out = i;
				break;
			}
		}
		return out;
	}
	
	public String etaConvert (String expr){
		tempExpr = expr;
		//TODO
		return tempExpr;
	}
}
