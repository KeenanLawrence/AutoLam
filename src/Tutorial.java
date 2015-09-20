//Importing of necessary packages
import java.util.*;

//Class definition
public class Tutorial {
	public static void main (String [] args){
		
		Expression objExpression;
		Calculator objCal;
		String result;
		
		Scanner input = new Scanner (System.in);
		
		System.out.println ("Please select an option: \n1. Alpha Conversion \n2. Beta Reduction \n3. Auto Alpha Convert \n4. Alpha Equivalence \n5. Quit");
		int option = input.nextInt();

		while(option!=5){
			input.nextLine();
			
			System.out.println ("Please enter an expression:");
			String expr = input.nextLine(); 
			
			objExpression = new Expression (expr);
			objCal = new Calculator ();
			
			System.out.println();
			System.out.println("Autocorrecting Expression...");
			objExpression.setExpression(objExpression.autocorrectExpression(expr));
			
			if (objExpression.validExpression(objExpression.getExpression()) == true){
				System.out.println ("Valid expression");
				
				System.out.println();
				switch (option){
					case 1:
						System.out.println("Enter the variable you want to replace");
						String target = input.nextLine();
						System.out.println("Enter the variable you want to replace it with");
						String choice = input.nextLine();
						result = objCal.alphaConvert(objExpression.getExpression(), target, choice);
						System.out.println("Result");
						System.out.println(result);
						break;
					case 2:
						String oldResult=objCal.betaReduce(objExpression.getExpression());
						System.out.println(oldResult);
						while(true){					
							String newResult = objCal.betaReduce(oldResult);
							if(oldResult.equalsIgnoreCase(newResult)){
								break;
							}
							oldResult=newResult;
							System.out.println(oldResult);
						}
						break;
					case 3:
						objCal.autoAlphaConvert(objExpression.getExpression());
						break;
					case 4:
						System.out.println("Enter the second expression: ");
						String expr2 = input.nextLine();
						if (objCal.alphaEquivalent(objExpression.getExpression(), expr2)){
							System.out.println("The expressions are equivalent");
						}
						else{
							System.out.println("The expressions are not equivalent");
						}
				}
			}
			else{
				System.out.println ("The expression was invalid");
			}
			System.out.println();
			System.out.println ("Please select an option: \n1. Alpha Conversion \n2. Beta Reduction \n3. Auto Alpha Convert \n4. Alpha Equivalence \n5. Quit");
			option = input.nextInt();
		}
		input.close();
	}
}
