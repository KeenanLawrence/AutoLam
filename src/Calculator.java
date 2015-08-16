import java.util.*;

public class Calculator {
	//Expression expression;
	String expression;
	List <String> bound; 
	//Question question;
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
	//This is a change to check if branches are cool
}
