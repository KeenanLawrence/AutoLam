public class Step{
	String type;
	String body;
	String label;
	
	public Step(String type, String body, String label){
		this.type=type;
		this.body=body;
		this.label=label;
	}
	
	//in the case where the answer is passed as one string
	public Step(String alltext){
		this.type="";
		this.body="";
		this.label="";
		int bodyIndex=0;
		
		int firstSpace = alltext.indexOf(' ');
		if(firstSpace==-1){
			firstSpace=alltext.length();
		}
		String temp = alltext.substring(0, firstSpace);
		if(temp.equalsIgnoreCase("ALPHA")||temp.equalsIgnoreCase("BETA")||temp.equalsIgnoreCase("ETA")){
			this.type=temp;
			bodyIndex=firstSpace+1;
		}
		
		if(alltext.charAt(alltext.length()-1)==']'){
			int labelIndex=alltext.lastIndexOf('[');
			this.label=alltext.substring(labelIndex+1,alltext.length()-1);
			this.body=alltext.substring(bodyIndex, labelIndex-1);
			//System.out.println("here, the label is " + this.label + " and the body is " + this.body);
		}else{
			this.body=alltext.substring(bodyIndex);
		}
	}
	
	public String getType(){
		return type;
	}
	
	public String getBody(){
		return body;
	}
	
	public String getLabel(){
		return label;
	}
	
	public boolean isEqual(Step other){
		if(other.getBody().equalsIgnoreCase(this.getBody())&&other.getType().equalsIgnoreCase(this.getType())&&other.getLabel()==this.getLabel()){
			return true;
		}else{
			return false;
		}
	}
	
	public String toString(){
		String labelstr="";
		if(this.label.equalsIgnoreCase("")==false){
			labelstr=" ["+ this.label+"]";
		}
		if(this.type.equalsIgnoreCase("")){
			return this.body + labelstr;
		}else{
			return this.type+" "+ this.body + labelstr;
		}
	}
}