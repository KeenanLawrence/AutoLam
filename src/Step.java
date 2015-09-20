Public class Step{
	String type;
	String body;
	String label;
	
	public step(String type, String body, String label){
		this.type=type;
		this.body=body;
		this.label=label;
	}
	
	//in the case where the answer is passed as one string
	public step(String alltext){
		this.type="";
		this.body="";
		this.label="";
		int bodyIndex=0;
		
		
		int firstSpace = alltext.indexOf(' ');
		String temp = alltext.substring(0, firstSpace);
		if(temp.equalsIgnoreCase("ALPHA")||temp.equalsIgnoreCase("BETA")||temp.equalsIgnoreCase("ETA")){
			this.type=temp;
			bodyIndex=firstSpace+1;
		}
		
		if(alltext.charAt(alltext.length()-1)==']'){
			int labelIndex=alltext.lastIndexOf('[');
			this.label=alltext.substring(labelIndex);
			this.body=alltext.substring(bodyIndex, labelIndex-1);
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
	
	
	
	
}