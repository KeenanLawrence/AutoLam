import java.util.Scanner;
import java.io.*;

public class NewTut{
	Calculator d = new Calculator();
	Question [] questions = new Question[100];
	int qcount=0;
	Answer[] answers = new Answer[100];
	int acount = 0;
	float[] marks = new float[100];
	int fcount = 0;
	
	
	
	public void addQuestion(Question q){
		questions[qcount]=q;
		qcount++;
	}
	
	
	public void addMark(float f){
		marks[fcount]=f;
		fcount++;
	}
	
	public float finalMark(){
		int bigMark=0;
		float qmark=0;
		for(int i =0;i!= qcount;i++){
			qmark+=marks[i]*questions[i].allocation/100;
			bigMark += questions[i].allocation;
		}
		return qmark*100/bigMark;
	}
	
	/*public static void main(String[] args){
		try{
			Scanner in = new Scanner(new File("test.txt"));
			int i = 1;
			while(in.hasNextLine()){
				String pr = in.nextLine();
				if(pr.equals("")){
					System.out.println(i+". ");
				}
				i++;
			}
		}catch(IOException e){
			System.out.println(e);
		}
		
		
		
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
		System.out.printf("%.2f",ans.percentage());
		System.out.println("%");
	}*/
	
	public boolean readAns(String fileName){
		boolean out = true;
		if(fileName==null){
			return false;
		}
		try{
			marks = new float[100];
			fcount = 0;
			acount=0;
			Scanner in = new Scanner(new File(fileName));
			int ansNum = 0;//the number of each answer
			Step[] eachAnswer = new Step[20];//used to create each answer as we get it
			int stepcount=0;//used to track how many steps there are per answer
			String line="";
			Step temp;//used to store steps as they come
			String lastLine="";
			while(in.hasNextLine()){
				line=in.nextLine();
				lastLine=line;
				if(line.equals("")==false && "123456789".indexOf(line.charAt(0))!=-1){//number signals the start of a question
					ansNum = Integer.parseInt(""+line.charAt(0))-1;
					line = line.substring(3);
					//System.out.println("I'M READING IN ANSWER " + ansNum + ", AND IT LOOKS LIKE: "+line);
				}
				if(line.equals("")==false){
					temp = new Step(line);
					eachAnswer[stepcount]=temp;
					stepcount++;
					if(stepcount==20){
						System.out.println("answer takes up too many lines!");
						break;
					}
				}else{
					answers[ansNum] = new Answer(eachAnswer,stepcount);
					acount++;
					//System.out.println("I ADDED A NEW ANSWER THERE ARE NOW " + acount + " ANSWERS");
					eachAnswer = new Step[20];
					stepcount=0;
				}
			}
			if(!(lastLine.equals(""))){
				answers[ansNum]=new Answer(eachAnswer,stepcount);
				acount++;
			}
		}catch(IOException e){
			out = false;
			System.out.println(e);
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return out;
	}
	
	
}