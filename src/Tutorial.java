//Importing of necessary packages
import java.util.*;

//Class definition
public class Tutorial {
	public static void main (String [] args){
		
		Expression objExpression;
		Calculator objCal;
		String result;
		
		Scanner input = new Scanner (System.in);
		
		System.out.println ("Please select an option: \n1. Alpha Conversion \n2. Beta Reduction \n3. Quit");
		int option = input.nextInt();

		while(option!=3){
			input.nextLine();
			
			System.out.println ("Please enter an expression:");
			String expr = input.nextLine(); 
			
			objExpression = new Expression (expr);
			objCal = new Calculator (objExpression.getExpression());
			
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
				}
			}
			else{
				System.out.println ("The expression was invalid");
			}
			System.out.println();
			System.out.println ("Please select an option: \n1. Alpha Conversion \n2. Beta Reduction \n3. Quit");
			option = input.nextInt();
		}
		input.close();
	}
}
