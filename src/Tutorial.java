import java.util.*;

public class Tutorial {
	public static void main (String [] args){
		Scanner input = new Scanner (System.in);
		
		
		System.out.println ("Please enter the expression you'd like to reduce/convert");
		String expr = input.nextLine(); 
		input.close();
		
		Expression objExpression = new Expression (expr);
		
		if (objExpression.validExpression(expr) == true){
			//eg Calculator.betaReduce(expr);
		}
		//TODO
	}
}
