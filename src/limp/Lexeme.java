/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limp;


/**
 *
 * @author Michael Walker
 */
//public class Lexeme extends Types {
public class Lexeme //extends Types 
{
    String type;
    String str;
    int val;
    
    Lexeme left = null;
    Lexeme right = null;
    
    int lineError;
    
    // empty lexemes
	/**
	 * Creates an empty Lexeme with a type.
	 */

    public Lexeme (String type) {
        this.type = type;
    }
    
    // single-character Lexemes
    /**
	 * Creates a new single-character Lexeme.
	 */

    public Lexeme (String type, int intVal) {
        this.type = type;
        this.val = intVal;
    }
    
    // multi-character lexemes
    /**
	 * Creates a new multi-character Lexeme.
	 */

    public Lexeme (String type, String strVal)
    {
    	this.type = type;
    	this.str = strVal;
    
    }
}
