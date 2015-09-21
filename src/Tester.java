public class Tester{
	
	

	public static void main(String[] args){
		NewTut t = new NewTut();
		t.readAns("marktest.txt");
		/*Step one = new Step("/x.x y");
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
		System.out.printf("%.2f",ans.percentage());*/
		for (int i=0;i<t.acount;i++){
			t.answers[i].markAnswer();
			//show the question number
			int qnum = i+1;
			System.out.println("Question " + qnum + "\n");
			float perc = t.answers[i].percentage();
			t.addMark(perc);
			//show the mark
			String percString = Float.toString(perc);
			System.out.println("Mark:" + percString + "\n");
			//show the feedback
			System.out.println(t.answers[i].feedbackToString());
				
		}
	}
	
	
}