import java.util.*;

public class Answer{
	Step[] steps;
	String[] feedback;
	int stepcount;
	int mark;
	int outof;
	Calculator c = new Calculator;
	
	public answer(Step[] steps, int stepcount){
		this.steps = steps;
		this.feedback = new String[50];
		this.mark = 0;
		this.outof=0;
		this.stepcount=stepcount;
	}
	
	public void markAnswer(){
		for(int i = 1; i!=stepcount;i++){
			if(steps[i].isEqual(steps[i-1])==false){
				mark+=markstep(i);
				outof+=3;
			}
		}
	}
	
	
	public int markStep(int pos){
		int out = 3;
		Step s = steps[pos];
		Step prevStep = steps[pos-1];
		//Alpha conversion expected
		if(s.getType.equalsIgnoreCase("ALPHA")){
			String tempLabel=s.getLabel();
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
					arg2=tempLabel.substring(tempLabel.indexOf(|)+1);
					String projectedAns=c.alphaConvert(prevStep.getBody(),arg2,arg1);
					if(c.alphaEquivalent(projectedAns,s.getBody())==false){
						out-=1;
						feedback[pos]+="Line " + pos + ": label does not match alpha conversion\n";
					}									
				}
			}
			
			//Beta reduction expected	
		}else if(s.getType.equalsIgnoreCase("BETA")){
			String tempLabel=s.getLabel();
			if(tempLabel.equalsIgnoreCase("")){
				out-=1;
				feedback[pos]+="Line " + pos + ": no label\n";
			}else{
				if(isCorrectBetaReduction(s.getBody(),prevStep.getBody())==false){
					out-=1;
					feedback[pos]+="Line "+ pos + ": type does not match reduction/conversion\n";
					if(isCorrectAlphaConversion(s.getBody(),prevStep.getBody())==false){
						out-=2;
						feedback[pos]+="Line " + pos + ": invalid reduction/conversion\n";
					}else{
						arg1=tempLabel.substring(0, tempLabel.indexOf('|'));
						arg2=tempLabel.substring(tempLabel.indexOf(|)+1);
						String projectedAns=c.alphaConvert(prevStep.getBody(),arg2,arg1);
						if(c.alphaEquivalent(projectedAns,s.getBody())==false){
							out-=1;
							feedback[pos]+="Line " + pos + ": label does not match alpha conversion\n";
						}
					}
				}
			}
			
		//could be either	
		}else{
			out-=1;
			feedback[pos]+="Line " + pos + ": no type\n";
			if(tempLabel.equalsIgnoreCase("")){
				out-=1;
				feedback[pos]+="Line " + pos + ": no label\n";
			}else{
				if(isCorrectAlphaConversion(s.getBody(),prevStep.getBody())==false){
					if(isCorrectBetaReduction(s.getBody(),prevStep.getBody())==false){
						out-=2;
						feedback[pos]+="Line " + pos + ": invalid reduction/conversion\n";
					}
					
				}else{
					if(tempLabel.equalsIgnoreCase("")==false){
						arg1=tempLabel.substring(0, tempLabel.indexOf('|'));
						arg2=tempLabel.substring(tempLabel.indexOf(|)+1);
						String projectedAns=c.alphaConvert(prevStep.getBody(),arg2,arg1);
						if(c.alphaEquivalent(projectedAns,s.getBody())==false){
							out-=1;
							feedback[pos]+="Line " + pos + ": label does not match alpha conversion\n";
						}
					}				
				}
			}
		}
		return out;
	}
	
	public boolean isCorrectBetaReduction(String first, String second){
		if(c.alphaEquivalent(first,second)==false){
			String fnform = c.fullReduce(first);
			String snform = c.fullReduce(second);
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
	
}