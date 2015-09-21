


public class Question {
	Expression body;
	int allocation;
	
	public Question(Expression e, int a){
		this.body=e;
		this.allocation=a;
	}
	
	public boolean check(){
		return this.body.validExpression();
	}
	
	public String toString(){
		return body.toString()+" ["+allocation+"]";
	}
	
}
