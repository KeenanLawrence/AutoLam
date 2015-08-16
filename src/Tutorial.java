//Importing of necessary packages
import java.util.*;

//Class definition
public class Tutorial {
	public static void main (String [] args){
		
		Scanner input = new Scanner (System.in);
		
		System.out.println ("Please select an option: \n1. Alpha Conversion \n2. Beta Reduction \n3. Quit");
		int option = input.nextInt();
<<<<<<< HEAD
		input.nextLine();
		
		System.out.println ("Please enter an expression:");
		String expr = input.nextLine(); 
		
		Expression objExpression = new Expression (expr);
		Calculator objCal = new Calculator (expr);
		
		String result;
		
		if (objExpression.validExpression(expr) == true){
			System.out.println ("Valid expression");
			switch (option){
				case 1:
					System.out.println("Enter the variable you want to replace");
					String target = input.nextLine();
					System.out.println("Enter the variable you want to replace it with");
					String choice = input.nextLine();
					result = objCal.alphaConvert(expr, target, choice);
					if (result.equals("")){
						System.out.println("Illegal substitution");
					}
					else{
						System.out.println("Result");
					}
					System.out.println(result);
					break;
				case 2:
					break;
=======
		while(option!=3){
			input.nextLine();
			
			System.out.println ("Please enter an expression:");
			String expr = input.nextLine(); 
			
			Expression objExpression = new Expression (expr);
			Calculator objCal = new Calculator (expr);
			
			String result;
			
			if (objExpression.validExpression(expr) == true){
				System.out.println ("Valid expression");
				switch (option){
					case 1:
						System.out.println("Enter the variable you want to replace");
						String target = input.nextLine();
						System.out.println("Enter the variable you want to replace it with");
						String choice = input.nextLine();
						result = objCal.alphaConvert(expr, target, choice);
						System.out.println("Result");
						System.out.println(result);
						break;
					case 2:
						String oldResult=objCal.betaReduce(expr);
						System.out.println(oldResult);
						while(true){					
							String newResult = objCal.betaReduce(oldResult);
							if(oldResult.equalsIgnoreCase(newResult)){
								break;
							}
							oldResult=newResult;
							System.out.println(oldResult);
						}
						//System.out.println(objCal.betaReduce(expr));
						break;
				}
>>>>>>> 79b2163e9820b7ad31a538d328c3d920d8468f57
			}
			else{
				System.out.println ("The expression was invalid");
			}
			System.out.println ("Please select an option: \n1. Alpha Conversion \n2. Beta Reduction \n3. Quit");
			option = input.nextInt();
		}
		
		
		input.close();
	}
}
