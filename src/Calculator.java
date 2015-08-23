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
	
	//Method to remove the outermost brackets in the expression
	public String stripBrackets (String e){
		int firstBracket = 0, lastBracket = e.length();
		
		if (e.indexOf("(") == 0){
			firstBracket = e.indexOf('(');
			lastBracket = e.lastIndexOf(')');
		
			System.out.println(e.substring(firstBracket + 1, lastBracket));
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
		tempExpr = "";
		findBinding(expr);
		
		//If the choice of substitution is already in the expression,
		//or the thing to substitute does not exist, return an error.
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
		for (int i = 0; i < bound.size(); i++){
			System.out.println("Bound");
			System.out.println(bound.get(i));
		}
		*/
		
		//Checks if the variable to replace is a function
		for (int i = 0; i < func.size(); i++){
			if (func.get(i).contains(target)){
				//If it is, check if there's a bounded variable of the same name
				for (int k = 0; k < bound.size(); k++){
					if (bound.get(k).contains(target)){
						//Replace the first occurrence of the function name
						tempExpr = expr.replaceFirst(target, func.get(i).replace(target, choice));
						//Replace the bounded variable name
						tempExpr = tempExpr.replaceFirst(target, choice);
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
		if(tempExpr.equals("")){
			tempExpr = "Illegal Substitution";
		}
		return tempExpr; 
	}
	
	//does what you think it does (actually probably doesn't though)
	public String betaReduce (String expr){
		tempExpr = expr;
		int fSpace = findRelevantSpace(expr,0);//this space seperates a function from its argument in an application
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
		out=e.substring((tempPoint-removed));//accomodates for the brackets that we have already deleted
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
