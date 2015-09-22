//import java.util.*;

public class Answer{
	Step[] steps;
	String[] feedback;
	int stepcount;
	int mark;
	int outof;
	Calculator c = new Calculator ();
	
	public Answer(Step[] steps, int stepcount){
		this.steps = steps;
		this.feedback = new String[50];
		for(int i = 0;i!=50;i++){
			feedback[i]="";
		}
		this.mark = 0;
		this.outof=0;
		this.stepcount=stepcount;
	}
	
	public void markAnswer(){
		for(int i = 1; i<stepcount;i++){
			if(steps[i].isEqual(steps[i-1])==false){
				mark+=markStep(i);
				outof+=3;
			}
		}
	}
	
	
	public int markStep(int pos){
		
		int out = 3;
		String arg1, arg2, tempLabel="";
		Step s = steps[pos];
		//System.out.println("Marking step " + s.getBody());
		Step prevStep = steps[pos-1];
		System.out.println(pos+"."+s.toString());
		//Alpha conversion expected
		if(s.getType().equalsIgnoreCase("ALPHA")){
			tempLabel=s.getLabel();
			if(tempLabel.equalsIgnoreCase("")){
				out-=1;
				feedback[pos]+="Line " + pos + ": no label\n";
				if(isCorrectAlphaConversion(s.getBody(),prevStep.getBody())==false){
					out-=1;
					feedback[pos]+="Line "+ pos + ": type does not match reduction/conversion\n";
					if(isCorrectBetaReduction(s.getBody(),prevStep.getBody())==false){
						out-=1;
						feedback[pos]+="Line " + pos + ": invalid reduction/conversion\n";
					}					
				}
			}else{
				if(isCorrectAlphaConversion(s.getBody(),prevStep.getBody())==false){
					out-=1;
					feedback[pos]+="Line "+ pos + ": type does not match reduction/conversion\n";
					if(isCorrectBetaReduction(s.getBody(),prevStep.getBody())==false){
						out-=2;
						feedback[pos]+="Line " + pos + ": invalid reduction/conversion\n";
					}
					
				}else{				
					arg1=tempLabel.substring(0, tempLabel.indexOf('|'));
					arg2=tempLabel.substring(tempLabel.indexOf('|')+1);
					System.out.println(prevStep.getBody() +"," + arg2 + "," + arg1);
					String projectedAns=c.alphaConvert(Expression.autocorrectExpression(prevStep.getBody()),arg2,arg1);
					//if(Expression.autoCorrectExpression(projectedAns).equalsIgnoreCase(Expression.autocorrectExpression(s.getBody()))==false){
					if(projectedAns.equalsIgnoreCase(Expression.autocorrectExpression(s.getBody()))==false){
						out-=1;
						feedback[pos]+="Line " + pos + ": label does not match alpha conversion\n";
					}else if (c.alphaConvert(projectedAns,arg2,getOtherLetter(arg2)).equalsIgnoreCase("Illegal substitution")){
						out = 0;
						outof -= 3;
						feedback[pos]+="Line " + pos +": redundant conversion\n";
					}
				}
			}
			
			//Beta reduction expected	
		}else if(s.getType().equalsIgnoreCase("BETA")){
			tempLabel=s.getLabel();
			if(tempLabel.equalsIgnoreCase("")){
				out-=1;
				feedback[pos]+="Line " + pos + ": no label\n";
			}
			
			if(isCorrectBetaReduction(s.getBody(),prevStep.getBody())==false){
				out-=1;
				feedback[pos]+="Line "+ pos + ": type does not match reduction/conversion\n";
				if(isCorrectAlphaConversion(s.getBody(),prevStep.getBody())==false){
					out-=1;
					feedback[pos]+="Line " + pos + ": invalid reduction/conversion\n";
				}else{
					int splitpos=tempLabel.indexOf('|');
					if(splitpos!=-1){
						arg1=tempLabel.substring(0, tempLabel.indexOf('|'));
						arg2=tempLabel.substring(tempLabel.indexOf('|')+1);
						String projectedAns=c.alphaConvert(prevStep.getBody(),arg2,arg1);
						//if(Expression.autoCorrectExpression(projectedAns).equalsIgnoreCase(Expression.autocorrectExpression(s.getBody()))==false){
						if(projectedAns.equalsIgnoreCase(s.getBody())==false){
							out-=1;
							feedback[pos]+="Line " + pos + ": label does not match alpha conversion\n";
						}else if (c.alphaConvert(projectedAns,arg2,getOtherLetter(arg2)).equalsIgnoreCase("Illegal substitution")){
							out = 0;
							outof -= 3;
							feedback[pos]+="Line " + pos +": redundant conversion\n";
						}
					}
					
				}
			}
			
			
		//could be either	
		}else{
			out-=1;
			feedback[pos]+="Line " + pos + ": no type\n";
			tempLabel=s.getLabel();
			if(tempLabel.equalsIgnoreCase("")){
				out-=1;
				feedback[pos]+="Line " + pos + ": no label\n";
			}
			
			if(isCorrectAlphaConversion(s.getBody(),prevStep.getBody())==false){
				if(isCorrectBetaReduction(s.getBody(),prevStep.getBody())==false){
					out-=1;
					feedback[pos]+="Line " + pos + ": invalid reduction/conversion\n";
				}
				
			}else{
				if(tempLabel.equalsIgnoreCase("")==false){
					int splitpos=tempLabel.indexOf('|');
					if(splitpos!=-1){
						arg1=tempLabel.substring(0, tempLabel.indexOf('|'));
						arg2=tempLabel.substring(tempLabel.indexOf('|')+1);
						String projectedAns=c.alphaConvert(prevStep.getBody(),arg2,arg1);
						//if(Expression.autoCorrectExpression(projectedAns).equalsIgnoreCase(Expression.autocorrectExpression(s.getBody()))==false){
						if(projectedAns.equalsIgnoreCase(s.getBody())==false){
							out-=1;
							feedback[pos]+="Line " + pos + ": label does not match alpha conversion\n";
						}else if (c.alphaConvert(projectedAns,arg2,getOtherLetter(arg2)).equalsIgnoreCase("Illegal substitution")){
							out = 0;
							outof -= 3;
							feedback[pos]+="Line " + pos +": redundant conversion\n";
						}
					}
				}				
			}	
		}
		return out;
	}
	
	public boolean isCorrectBetaReduction(String first, String second){
		//System.out.println("We're standing here in correct beta reduction check");
		if(c.alphaEquivalent(first,second)==false){
			//System.out.println("\nReducing: "+first);
			String fnform = c.fullReduce(first);
			System.out.println(fnform);
			//System.out.println("\nReducing: "+second);
			String snform = c.fullReduce(second);
			System.out.println(snform);
			return(c.alphaEquivalent(fnform,snform));
		}else{
			return false;
		}
	}
	
	public boolean isCorrectAlphaConversion(String first, String second){
		if(!first.equalsIgnoreCase(second)){
			return(c.alphaEquivalent(first,second));
		}else{
			return false;
		}
	}
	
	public String feedbackToString(){
		String out = "";
		for(int i=0;i!=stepcount;i++){
			//System.out.println("I am here at line " + i +"'s feedback");
			//System.out.println(feedback[i]);
			if(feedback[i].equalsIgnoreCase("")==false){
				//System.out.println("I am here at line " + i +"'s feedback");
				out += feedback[i];
			}
		}
		return out;
	}
	
	public float percentage(){
		float m = this.mark;
		float o = this.outof;
		if(o==0){
			return 0;
		}else{
			return m/o*100;
		}
	}
	
	//the most ridiculous method I've ever written
	public String getOtherLetter(String lett){
		if(lett.charAt(0)=='a'){
			return "b";
		}else{
			return "a";
		}
	}
	
}