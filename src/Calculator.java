//Import necessary packages
import java.util.*;

//Class definition
public class Calculator {
	
	//Instance variables
	List <String> func; 
	List <String> bound;

	//Constructor mainly used to access methods
	public Calculator(){
		func = new ArrayList <String> ();
		bound = new ArrayList <String> ();
	}
	
	//Method checks if a character is in the list of defined alpha characters
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
		//Empty the contents of the lists
		func = new ArrayList <String> ();
		bound = new ArrayList <String> ();
		//Temp string used for manipulation
		String temp = "";
		
		//parenthCount keeps track of brackets, index is for the list referencing, j is for 
		//multiple lambdas in succession
		int parenthCount = 0, index = 0, j = 0;
		
		for (int i = 0; i < e.length(); i++){
			
			//Increment or decrement the bracket count appropriately
			if (e.charAt(i) == '('){
				parenthCount++;
			}
			if (e.charAt(i) == ')'){
				parenthCount--;
			}
			
			//If it's a lambda,
			if (e.charAt(i) == '/'){
				/*j=i;
				//and there are more in  succession,
				while (e.charAt(j+4) == '/'){
					j=j+4;
				}
				//add them to the list 
				*/
				func.add(index, Character.toString(e.charAt(i+1)));
				//System.out.println("Adding func" + e.charAt(i+1));
				//i=j;
			}
			//If it's a period, 
			if (e.charAt(i) == '.'){
				//and it's enclosed in brackets,
				if (e.charAt(i+1) == '('){
					//add everything up until the matching bracket
					temp = e.substring(i+1, findMatchingBrace(e, i+1)+1);
					//System.out.println("Adding bound: " + Expression.removeOuterBrackets(temp));
					bound.add(index, Expression.removeOuterBrackets(temp));
					index++; //move the index by one
				}
				//If it's not enclosed in brackets,
				else if (e.charAt(i+1) == '(' && parenthCount != 0){
					try{
							//add it
							temp = e.substring(i+1, e.indexOf(')'+1, i+1));
							//System.out.println("Adding bound: " + Expression.removeOuterBrackets(temp));
							bound.add(index, Expression.removeOuterBrackets(temp));
							index++;
					}
					//It may not have a ')'
					catch(Exception error){
						if (e.charAt(i+1) == ')' && e.charAt(i) == ')'){
							//System.out.println("Adding bound: " + e.substring(i));
							bound.add(index, e.substring(i));
							index++;
						}
					}
				}
				else{
						if (e.substring(i).contains("/")){
							System.out.println("here");
							try{
								bound.add(index, e.substring(i+1, e.indexOf("/")));
							}
							catch (Exception error3){
								bound.add(index, e.substring(i+1));
							}
							finally{
								index++;
							}
						}
						else{
							System.out.println("there");
							bound.add(index, e.substring(i+1));
							index++;
						}	
				}
						//System.out.println("Error");
			}
		}
	}
	
	//The method that does the alpha conversion if given a target and a replacement (called choice)
	public String alphaConvert (String expr, String target, String choice){
		String tempExpr = "";
		findBinding(expr);
		
		//If the choice of substitution is already in the expression,
		//or the thing to substitute does not exist, return an error.
		if (expr.contains(choice) || !expr.contains(target) || !isAlpha(choice.charAt(0))){
			tempExpr = "Illegal substitution";
			return tempExpr;
		}
		
		/*
		for (int i = 0; i < func.size(); i++){
			System.out.println("Func and bound:");
			System.out.println(func.get(i) + " and " + bound.get(i));
		}
		*/
		//Checks if the variable to replace is a function
		for (int i = 0; i < func.size(); i++){
			if (func.get(i).contains(target) && bound.get(i).contains(target)){
				//If it is, check if there's a bounded variable of the same name
				//Replace the first occurrence of the function name
				//System.out.println("Before replace: func\n" + tempExpr);
				tempExpr = expr.replaceFirst(func.get(i), func.get(i).replace(target, choice));
				//System.out.println("After replace: \n" + tempExpr);
				//Replace the bounded variable name
				if (bound.get(i).contains("/")){
					if (bound.get(i).substring(0, 1).equals(target)){
						if (bound.get(i).contains("/"+target)){
						tempExpr = tempExpr.replaceFirst(target, choice);
						}
						else{
							tempExpr = tempExpr.replace(target, choice);
						}
					}
					else{
					System.out.println("before replace: bound is " + bound.get(i).substring(1) + "\n" + tempExpr);
					tempExpr = tempExpr.substring(0, findMatchingBrace(tempExpr, 0)).replace(bound.get(i).substring(1), bound.get(i).substring(1).replace(target, choice)) + tempExpr.substring(findMatchingBrace(tempExpr, 0));
					System.out.println("After replace: bound\n" + tempExpr);
					}
				}
				else{
				//	System.out.println("before replace: bound\n" + tempExpr);
					tempExpr = tempExpr.replaceFirst(bound.get(i), bound.get(i).replace(target, choice));
				//	System.out.println("After replace: bound\n" + tempExpr);
				}
				break;
			}
			
			//If it's a function and there's no matching bound variable, just change the function name
			if (func.get(i).contains(target) && !bound.get(i).contains(target)){
			//	System.out.println("before replace: bound\n" + tempExpr);
						tempExpr = expr.replaceFirst(target, choice);
				//		System.out.println("After replace: bound\n" + tempExpr);
			}
		}
		
		if(tempExpr.equals("")){
			tempExpr = "Illegal substitution";
		}
		return tempExpr; 
	}
	
	//Method to automatically alpha convert an expression
	//It does this by changing everything it can legally change, to "unused" characters.
	public String autoAlphaConvert(String expr){
		String temp = Expression.autocorrectExpression(expr);
		findBinding(temp);
		
		//Variable used for neater code
		String characterList = Expression.getAlpha();
		int j = 0; //Used as an index for the characterList
		
		//Go through the list of lambdas
		for(int i = 0; i < func.size(); i++){
			//If there was multiple lambdas in one index, go through it
			for (int k = 0; k < func.get(i).length(); k = k+3){
				//If an alpha conversion fails with the first character,
				if ((alphaConvert(temp, Character.toString(func.get(i).charAt(k)), Character.toString(characterList.charAt(j))).equals ("Illegal substitution"))){
					while ((alphaConvert(temp, Character.toString(func.get(i).charAt(k)), Character.toString(characterList.charAt(j))).equals ("Illegal substitution"))){
						j++; //keep trying until something works
					}
					//When it works, save it to the temporary variable
					temp = alphaConvert(temp, Character.toString(func.get(i).charAt(k)), Character.toString(characterList.charAt(j)));
					System.out.println(temp);
				}
				//If it didn't fail, go ahead
				else{
					temp = alphaConvert(temp, Character.toString(func.get(i).charAt(k)), Character.toString(characterList.charAt(j)));
					System.out.println(temp);
					j++; //but move to the next character in characterList
				}
				//Rework the binding, because the expression we're working with has changed
				findBinding(temp);
			}
			findBinding(temp);
		}
		return temp;
	}

	//Method checks for alpha equivalence.
	public boolean alphaEquivalent (String expr1, String expr2){
		//Correct the expressions first
		expr1 = Expression.autocorrectExpression(expr1);
		
		expr2 = Expression.autocorrectExpression(expr2);
		System.out.println("checking alpha equivalence between " + expr1 + " and "+ expr2);
		//If the two auto alpha conversions are the same, then okay...
		System.out.println(autoAlphaConvert(expr1));
		System.out.println(autoAlphaConvert(expr2));
		if (autoAlphaConvert(expr1).equals(autoAlphaConvert(expr2))){
			return true;
		}
		//Otherwise, there're likely to be wrong
		else{
			return false;
		}
	}
	
	//does what you think it does
	public String betaReduce (String expr){
		String tempExpr = expr;
		int fLam = expr.indexOf('/')+1;
		//if(fLam!=0 && expr.charAt(fLam-1)=='('){//this
		//	expr = insertBraces(expr, fLam+2,findMatchingBrace(expr,fLam-1));//might be
		//}//necessary
		int fSpace = findRelevantSpace(expr,0);//this space separates a function from its argument in an application
		if(fSpace==-1){//case where this is not an application
			int firstBrace=expr.indexOf('(');
			if(firstBrace==-1){
				return tempExpr;
			}else{
				//System.out.println("no application, let's strip things down a bit");
				//System.out.println("fLam is currently "+fLam);
				//if(fLam>=2 && expr.charAt(fLam-2)=='('){//this
				//	tempExpr = insertBraces(tempExpr, fLam+2,findMatchingBrace(expr,fLam-2));//might be
				//	System.out.println("here, expr is now: " +expr);
				//}//necessary
				String inside = tempExpr.substring(firstBrace+1, tempExpr.length()-1);
				
				return tempExpr.substring(0, firstBrace)+ betaReduce(inside);
			}
		}else{
			//int fLam = expr.indexOf('/')+1;
			
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
					//String otherArgs = tempExpr.substring(argSpace);//all the arguments after the one we are using
					String tempExprBind=expr.substring(0, fSpace);//everything between start and argument
					//System.out.println("unstripped: " + tempExprBind);
					//tempExprBind = stripUpTo(tempExprBind, fLam+2);//stripped up to the start of the binding
					tempExprBind = tempExprBind.substring(0, fLam-1)+tempExprBind.substring(fLam+2);
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
	
	//public String insertBraces(String e,int posOpen, int posClose){
	//	return e.substring(0,posOpen)+"("+e.substring(posOpen, posClose)+")"+e.substring(posClose);
	//}
	
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
	
	//Unimplemented method
	public String etaConvert (String expr){
		String tempExpr = expr;
		//TODO
		return tempExpr;
	}
	
	String fullReduce(String expr){
		expr = Expression.autocorrectExpression(expr);
		System.out.println("Correct to " + expr);
		String oldResult=betaReduce(expr);
		System.out.println(oldResult);
		String out = "diverges";
		int divcount = 0;
		while(divcount<=20){
			String newResult = betaReduce(oldResult);
			if(oldResult.equalsIgnoreCase(newResult)){
				out = oldResult;
				break;
			}
			oldResult=newResult;
			//System.out.println(oldResult);
			divcount++;
		}
		return out;
	}
}
