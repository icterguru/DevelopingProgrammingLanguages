
package DCPP;


/*
 * Author:Songhui Yue
 * 2014-10-04
 */
public class Lexeme extends Types{
	String type;
	String word;
	
	double real;
	char cval;
	boolean bval;
	int ival;
	int[] iarray;
	String[] sarray;
	int arraySize=0;
	boolean isArray = false;
	String arrayType;
	
	boolean isNode = false;

	Lexeme left;
	Lexeme right;

	public Lexeme(){
	}
	
	public Lexeme(String type){
		this.type=type;
	}
	public Lexeme(String type, String word){
		this.type=type;
		this.word=word;
	}
	public Lexeme(String type, int word){
		this.type=type;
		this.ival=word;
	}
	public Lexeme(String type, double word){
		this.type=type;
		this.real=word;
	}
	public Lexeme(String type, char word){
		this.type=type;
		this.cval=word;
	}
	public Lexeme(String type, boolean word){
		this.type=type;
		this.bval=word;
	}
	public String getType(){
		return this.type;
	}

	public boolean getIsNode() {
		return isNode;
	}

	public void setIsNode(boolean isNode) {
		this.isNode = isNode;
	}
}