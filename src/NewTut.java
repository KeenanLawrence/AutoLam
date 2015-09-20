public class NewTut{
	
	public static void main(String[] args){
		Calculator d = new Calculator();
		System.out.println(d.alphaEquivalent("/z.z y","/x.x y"));
		Step one = new Step("/x.x y");
		Step two = new Step("ALPHA /z.z y [p|x]");
		Step three = new Step("BETA y [cheating]");
		System.out.println("Question:"+ one.getBody()+"\nYour Answer:");
		Step[] steps = new Step[3];
		steps[0]=one;
		steps[1]=two;
		steps[2]=three;
		Answer ans = new Answer(steps,3);
		ans.markAnswer();
		System.out.println("Errors:\n" + ans.feedbackToString());
		System.out.println(ans.mark + " out of " + ans.outof);
	}
	
	
}