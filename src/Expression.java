
public class Expression {
	private String expression;
	private String[] labels;
	
	public Expression (String e, String [] l){
		setExpression(e);
		setLabels(l);
	}
	
	public void setExpression (String e){
		expression = e;
	}
	
	public void setLabels (String [] l){
		int length = l.length;
		labels = new String [length];
		
		for (int i = 0; i < length; i++){
			labels[i] = l[i];
		}
	}
	
	public String getExpression(){
		return expression;
	}
	
	public String [] getLables(){
		return labels;
	}
	
	public boolean validExpression (String e){
		boolean check = false;
		//Checks if expression is valid
		return check;
	}
	
}
