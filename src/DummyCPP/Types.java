/*
 * Author:Songhui Yue
 * 2014-10-04
 */
public class Types {
	public static  String GLUE = "glue";
	public static  String INTEGER = "INTEGER";
	public static  String VARIABLE = "VARIABLE";
	public static  String NULL = "null";
	public static  String ARRAY = "ARRAY";
	public static  String PRINTLN = "println";
	public static  String VOID = "void";
	public static  String VAR = "var";
	public static  String STRING = "STRING";
	public static  String BOOL = "BOOL";
	public static  String DOUBLE = "DOUBLE";
	public static  String IF = "if";
	public static String ELSE="else";
	public static String WHILE="while";
	public static String RETURN="return";
	public static String OPAREN="(";
	public static String CPAREN=")";
	public static String OPEN_BRACE="{";
	public static String CLOSE_BRACE="}";
	public static String LEFT_SQUARE_BRACKET="[";
	public static String RIGHT_SQUARE_BRACKET="]";
	public static String COMMA=",";
	public static String POINT=".";
	public static String PLUS="+";
	public static String PLUSPLUS="++";
	public static String TIMES="*";
	public static String MINUS="-";
	public static String MINUSMINUS="--";
	public static String DIVIDES="/";
	public static String MODE="%";
	public static String DOLLAR="$";
	public static String QUESTIONMARK="?";
	public static String EXCLAMATIONMARK="!";
	public static String LESSTHAN="<";
	public static String LESSOREQUALTHAN="<=";
	public static String LESSTHANLESSTHAN="<<";
	public static String GREATERTHANGREATERTHAN=">>";
	public static String GREATERTHAN=">";
	public static String GREATEROREQUALTHAN=">=";
	public static String ASSIGN="=";
	public static String EQUALEQUAL="==";
	public static String NOTEQUAL="!=";
	public static String SEMICOLON=";";
	public static String COLON=":";
	public static String AND="&&";
	public static String OR="||";
	public static String UNKNOWN="unknown";
	public static String TRUE="true";
	public static String FALSE="false";
	public static String ENV="env";
	public static String VALUES="values";
	public static String JOIN="join";
	

	public static String FUNC_DEF="func_def";
	public static String MAIN="main";
	public static String CLASS="class";
	public static String IMPLEMENTS="implements";
	public static String EXTENDS="extends";
	public static String CLASS_DEF="class_def";
	public static String MAIN_DEF="main_def";
	public static String FUNC_CALL="func_call";
	public static String ARRAY_CALL="array_call";
	public static String CLASS_CALL="class_call";
	public static String FUNC_GLUE="func_glue";
	public static String VAR_DEF="var_def";
	public static String BLOCK="block";
	public static String CLOSURE="closure";

	public static String endOfFile="EndOfFile";
	
	public static String[] reservedWord={"var", "bool","else","int", "true", "false",  
			"void", "do", "if", "return," ,"while", "static", "double"};

	public static String[] punctuation={"{","(",";", "*", "||", "==", "<=", 
			")", "}", ",", "/" ,"!", "!=", ">=", 
			"[", ".", "+", "%", "?", "<", "<<", 
			"]", ":", "-", "&&", "=", ">", ">>"};
	
	public static boolean isPunctuation(String tmp){
		
		for(int i=0; i<punctuation.length; i++){
			if(punctuation[i].equals(tmp)){
				return true;
			}
		}
		return false;
	}

	public static boolean isReservedWord(String tmp){
		
		for(int i=0; i<reservedWord.length; i++){
			if(reservedWord[i].equals(tmp)){
				return true;
			}
		}
		return false;
	}
	
}