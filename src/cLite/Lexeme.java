package cLite;

/**

 * The Clite Programming Language
 * 
 * A Lexeme is a pair that contains a word (value) and its part of speech (type).
 * 
 * A Lexeme may hold left and right pointers to other Lexemes. In this way, 
 * we may build parse trees with Lexemes. Depending on its type, a Lexeme may 
 * also have other attributes. For example, a Lexeme of type "FUNCTION" 
 * holds a reference to its Defining Environment, so the values of variables
 * may be retrieved during a function call.
 * 
 * 
 */

import java.util.ArrayList;

public class Lexeme {

	private String lexType;
	private int charCode;
	private String strValue;
	private Lexeme left, right;
	private ArrayList<Lexeme> list;
	private Environment definingEnv;

	/**
	 * Create an empty lexeme with a type.
	 */
	public Lexeme(String lexType) {
		this.lexType = lexType;
	}

	/**
	 * Create a new single-character Lexeme.
	 */
	public Lexeme(String lexType, int charCode) {
		this.lexType = lexType;
		this.charCode = charCode;
	}

	/**
	 * Create a new multi-character Lexeme.
	 */
	public Lexeme(String strType, String svalue) {
		this.lexType = strType;
		this.strValue = svalue;
	}
	
	public void setLeft(Lexeme tree) { left = tree; }
	public void setRight(Lexeme tree) { right = tree; }
	public Lexeme right() { return right; }
	public Lexeme left() { return left; }
	public String getType() { return lexType; }
	public void setType(String t) { lexType = t; }
	
	/**
	 * Retrieve the Defining Environment of Closures
	 */
	public Environment getDefiningEnv() {
		return definingEnv;
	}

	/**
	 * Set the Defining Environment of a Closure.
	 */
	public void setDefiningEnv(Environment definingEnv) {
		this.definingEnv = definingEnv;
	}
	
	public String getStrValue() {
		if (lexType.equals("NUMBER")) return Integer.toString(charCode);
		return strValue; 
	}
	
	public int getIntValue() { return charCode; }
	
	// Only applies to Lexemes of Type "NUMBER"
	public int getNumberValue() {
		if (strValue != null) return Integer.parseInt(strValue);
		return charCode;
	}
	
	// Only applies to Lexemes of Type "ARRAY"
	public ArrayList<Lexeme> getArrayList() { return list;}
	public void setArrayList(ArrayList<Lexeme> aList) { this.list = aList; }
	
	@Override
	public String toString() {
		if (lexType.equals("NUMBER")) {
 			return (lexType + " " + charCode);
		}
		
		else if (lexType.equals("VARIABLE")) {
			return (lexType + " " + strValue);
		}
		
		else if (lexType.equals("FUNCTION")) {
			return (lexType);
		}
		
		else if (lexType.equals("ARRAY")) {
			String str = "[";
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					if (i != list.size() - 1)
						str = str + list.get(i) + ", ";
					else
						str = str + list.get(i);
				}
			}
			str = str + "]"; 
			return str;
		}
	
		else if (charCode != 0)  {
			return (lexType + " " + "(char " + charCode + ") " + (char) charCode);
		}
		
		else {
			return (lexType + " " + strValue);
		}
		
		
	}

	/**
	 * Display the single lexeme in the terminal.
	 */
	public void display() {
		System.out.println(this.toString());
	}
	
	/**
	 * Print the single lexeme.
	 */
	public void print() {
		System.out.print(this.toString());
	}
	
	/**
	 * Display the single lexeme in the terminal. Using this method
	 * within a traversal allows us to quickly visualize the 
	 * structure of parse trees.
	 */
	public void displayNode() {
		System.out.print(this.toString());
		if (left != null) { 
			System.out.print(" | LEFT: " + left.toString());
		} 
		if (right != null) {
			System.out.print(" | RIGHT: " + right.toString());
		}
		System.out.println();
	}
	
	/**
	 * Preorder traversal and printing of the tree of lexemes.
	 */
	public void preorderTraversal(Lexeme lexeme) {
		if (lexeme == null) return;
		lexeme.displayNode();
		preorderTraversal(lexeme.left);
		preorderTraversal(lexeme.right);
	}
	
	/**
	 * Two lexemes are equal if they have the same strValue.
	 */
	@Override
	public boolean equals(Object obj) {
		Lexeme lexeme = (Lexeme) obj;
		if (strValue.equals(lexeme.getStrValue())) {
			return true;
		}
		return false;
    }
	

	@Override
    public int hashCode() {
        return strValue.hashCode();
    }
}
