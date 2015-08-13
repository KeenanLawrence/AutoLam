import java.util.*;

public class Tutorial {
	public static void main (String [] args){
		
		Scanner input = new Scanner (System.in);
		
		System.out.println ("Please select an option: \n1. Alpha Conversion \n2. Beta Reduction");
		int choice = input.nextInt();
		
		System.out.println ("Please enter an expression:");
		String expr = input.next(); 
		input.close();
		
		Expression objExpression = new Expression (expr);
		
		if (objExpression.validExpression(expr) == true){
			System.out.println ("Valid expression");
			switch (choice){
				case 1:
					//Alpha convert
					//TODO
					break;
				case 2:
					//Beta Reduce
					//TODO
					break;
			}
		}
		else{
			System.out.println ("The expression was invalid");
		}
		//TODO
	}
}
