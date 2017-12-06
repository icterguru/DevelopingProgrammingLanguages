/*
 * Author:Songhui Yue
 * 2014-10-04
 */
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

public class Parser1122 extends Types{
	public static String endOfFile="EndOfFile";
	 List tokens = null;
	public  Lexeme token = null;
	public static boolean functionTag = true;
	public  int count = 0;
	private String fileName;
	public Lexer lexer;
	//public  Lexer l = new Lexer("/Users/songhuiyue/Downloads/Grammer/program2.txt");
	public Parser1122(String fileName){
		this.fileName=fileName;
		this.lexer = new Lexer(fileName);
	}
	public Lexeme getTree(){
		advance();
		Lexeme root = program();
		return root;
	}
    public  boolean check(String type) 
    { 	
    	return type.equals(token.getType()); 
    }

    public  boolean checkSpecial(String type) 
    { 	/*if(token.word.length()>9)
    		System.out.println(token.word.substring(0, 9));
    	System.out.println(type.equals(token.getType())
			&&((token.word.length()>9&&!"function_".equals(token.word.substring(0, 9)))
					||(token.word.length()<9)));*/
    	return type.equals(token.getType())
    			&&((token.word.length()>9&&!"function_".equals(token.word.substring(0, 9)))
    					||(token.word.length()<9)); 
    }

    public void advance() 
    {
    	token = lexer.lex();
    } 

    public Lexeme match(String type) 
    { 
    	Lexeme tree = matchNoAdvance(type); 
    	advance(); 
    	return tree;
    }

    public Lexeme matchNoAdvance(String type)
    {
    	Lexeme node = null;
    	if (!check(type)){
    		System.out.println("Syntax Error at: "+token.word+token.type);
    		System.out.println("supposed type is : "+type);
        	advance(); 
    		System.out.println("next token is: "+token.word+token.type);
        	advance(); 
    		System.out.println("next token is: "+token.word+token.type);
        	advance(); 
    		System.out.println("next token is: "+token.word+token.type);
    		advance(); 
    		System.out.println("next token is: "+token.word+token.type);
    		advance(); 
    		System.out.println("next token is: "+token.word+token.type);
    		advance(); 
    		Lexeme a=null; //this is a place that I throw exception on purpose
    		if(a.type=="bcd"){
        		System.out.println("next token is: "+token.word+token.type);
    		}
			exitProgram();
    	}else{
    		//System.out.println("this is "+token.type+"and "+token.word);
    		node= token;
    	}
    	return node;
    }
	public void exitProgram(){
		System.exit(1);
	}
	public Lexeme program () {//done
		Lexeme tree = definitionList();
		return tree;
	}
	public Lexeme definitionList () { //done
		Lexeme tree = new Lexeme(FUNC_GLUE);
		if(functionDefinitionPending()){
			tree.left=functionDefinition();
			tree.right=definitionList();
		}else if(mainDefinitionPending()){
			tree.left=mainDefinition();
			tree.right=definitionList();
		}else if(classDefinitionPending()){
			tree.left=classDefinition();
			tree.right=definitionList();
		}else{
			match(endOfFile);
		}
		return tree;
	}
	public Lexeme definitionListWithoutEndOfFile () { //done
		Lexeme tree = new Lexeme(FUNC_GLUE);
		if(functionDefinitionPending()){
			tree.left=functionDefinition();
			tree.right=definitionListWithoutEndOfFile();
		}else if(mainDefinitionPending()){
			tree.left=mainDefinition();
			tree.right=definitionListWithoutEndOfFile();
		}else if(classDefinitionPending()){
			tree.left=classDefinition();
			tree.right=definitionListWithoutEndOfFile();
		}
		return tree;
	}
	public boolean classDefinitionPending () {//done
		return check(CLASS);
	}
	public boolean mainDefinitionPending () {//done
		return check(MAIN);
	}
	public boolean functionDefinitionPending () {//done
		return check(VARIABLE);
	}
	public Lexeme classDefinition () {//done
		Lexeme class_def = new Lexeme(CLASS_DEF);
		class_def.left=match(CLASS);
		Lexeme tree = match(VARIABLE);
		class_def.right=tree;
		tree.left = relationDefinition();
		Lexeme brace = match(OPEN_BRACE);
		tree.right = brace;
		brace.left = basicStatementList();
		brace.right = definitionListWithoutEndOfFile();
		match(CLOSE_BRACE);
		return class_def;
	}
	public Lexeme relationDefinition(){
		Lexeme relationdef = null;
		if(relationPending()){
			relationdef = new Lexeme(GLUE);
			relationdef.left = relation();
			relationdef.right = match(VARIABLE);
		}
		return null;
	}
	public boolean relationPending(){
		return check(IMPLEMENTS)||check(EXTENDS);
	}
	public Lexeme relation(){
		Lexeme relation = null;
		if(check(IMPLEMENTS)){
			relation=match(IMPLEMENTS);
		}else if(check(EXTENDS)){
			relation=match(EXTENDS);
		}
		return relation;
	}
	public Lexeme mainDefinition () {//done
		Lexeme func_def = new Lexeme(MAIN_DEF);
		Lexeme tree = match(MAIN);
		func_def.right=tree;
		Lexeme tmp = match(OPAREN);
		tmp.left=null;
		tmp.right=optExpressionList();
		match(CPAREN);
		tree.left=tmp;
		tree.right=block();
		return func_def;
	}
	public Lexeme functionDefinition () {//done
		Lexeme func_def = new Lexeme(FUNC_DEF);
		Lexeme tree = match(VARIABLE);
		func_def.right=tree;
		Lexeme tmp = match(OPAREN);
		tmp.left=null;
		tmp.right=optExpressionList();
		match(CPAREN);
		tree.left=tmp;
		tree.right=block();
		return func_def;
	}
	public Lexeme optExpressionList(){ //done
		Lexeme tree = null;
		if(expressionPending()){
			tree = new Lexeme(GLUE);
			tree.left=expression();
			tree.right=moreExpressions();
		}
		return tree;
	}

	public Lexeme moreExpressions(){ //done
		Lexeme tree = null;
		if(check(COMMA))
		{
			tree = match(COMMA);
			tree.left= expression();
			tree.right = moreExpressions();
		}
		return tree;
	}
	public Lexeme expression(){ //done
		Lexeme tree = null;
		Lexeme tmp = primary();
		if(operatorPending()){
			tree = operator();
			tree.left=tmp;
			tree.right=expression();
		}else{
			tree=tmp;
		}
		return tree;
	}
	public boolean operatorPending(){ //
		return check(PLUS)||check(TIMES)||check(MINUS)||check(PLUSPLUS)||check(MINUSMINUS)||check(DIVIDES)
				||check(MODE)||check(EXCLAMATIONMARK)||check(LESSTHAN)||check(LESSOREQUALTHAN)||check(GREATERTHAN)
				||check(GREATEROREQUALTHAN)||check(ASSIGN)||check(EQUALEQUAL)||check(NOTEQUAL);
	}

	public Lexeme operator(){ //
		Lexeme tree = token;
		advance();
		return tree;
	}
	
	public boolean expressionPending(){ //done 222
		return primaryPending();
	}

	public boolean primaryPending(){//DONE
		return check(VARIABLE)||check(INTEGER)||check(TRUE)||check(FALSE)||check(OPAREN)||check(STRING);
	}
	
	public Lexeme block(){ //done
		Lexeme tree = match(OPEN_BRACE);
		tree.left=null;
		tree.right = statementList();
		match(CLOSE_BRACE);
		return tree;
	}
	
	public Lexeme statementList(){ //done
		Lexeme tree = null;
		if(statementPending()){
			tree = new Lexeme(BLOCK);
			tree.left = statement();
			tree.right =statementList();
		}
		return tree;
	}
	public boolean statementPending(){ //done 222
		return expressionPending()||check(IF)||check(VARIABLE)
				||check(WHILE)||check(RETURN)||check(PRINTLN);
	}
	
	public Lexeme statement(){ //done
		Lexeme tree = null;
		if(expressionPending()){
			tree = new Lexeme(GLUE);
			tree.left=expression();
			tree.right=match(SEMICOLON);
		}else if(check(IF)){
			tree = ifStatement();
		}else if(check(VARIABLE)){
			tree = basicStatement();
		}else if(check(WHILE)){
			tree = whileStatement();
		}else if(check(RETURN)){
			tree = returnStatement();
		}else if(check(PRINTLN)){
			tree = printStatement();
		}
		return tree;
	}

	public Lexeme basicStatement(){ // how about the semicolon, deal with it inside pretty printer
		Lexeme tree = null;
		Lexeme left = match(VARIABLE);
		if(check(SEMICOLON)){
			tree = match(SEMICOLON);
			left.right=tree;
			return left;
		}
		tree = match(ASSIGN);
		tree.left= left;
		Lexeme right = new Lexeme(GLUE);
		tree.right = right;
		right.left=expression();
		right.right=match(SEMICOLON);
		return tree;
	}
	public Lexeme basicStatementList(){
		Lexeme tree = null;
		if(checkSpecial(VARIABLE)){
			tree = new Lexeme(GLUE);
			tree.left = basicStatement();
			tree.right = basicStatementList();
		}else if(check(PRINTLN)){
			tree = new Lexeme(GLUE);
			tree.left = printStatement();
			tree.right = basicStatementList();
		}
		return tree;
	}

	public Lexeme printStatement(){
		Lexeme tree = match(PRINTLN);
		Lexeme tree2 = match(OPAREN);
		tree.left = tree2;
		tree2.right=expression();
		match(CPAREN);
		match(SEMICOLON);
		return tree;
	}
	
	public Lexeme ifStatement(){
		Lexeme tree = match(IF);
		Lexeme tree2 = match(OPAREN);
		Lexeme tree3 = new Lexeme(GLUE);
		tree.left = tree2;
		tree.right=tree3;
		tree2.right=expression();
		match(CPAREN);
		tree3.left=block();
		tree3.right=optElse();
		return tree;
	}

	public Lexeme optElse(){
		Lexeme tree = null;
		if(check(ELSE)){
			tree = match(ELSE);
			tree.right = block();
		}
		return tree;
	}
	public Lexeme whileStatement(){
		Lexeme tree = match(WHILE);
		Lexeme tmp=match(OPAREN);
		tmp.left=null;
		tmp.right=expression();
		tree.left=tmp;
		match(CPAREN);
		tree.right=block();
		return tree;
	}
	public Lexeme returnStatement(){
		Lexeme tree = match(RETURN);
		tree.left=null;
		tree.right=optionalExpression();
		match(SEMICOLON);
		return tree;
	}
	public Lexeme optionalExpression(){
		Lexeme tree = null;
		if(expressionPending()){
			tree=expression();
		}
		return tree;
	}
	public Lexeme primary(){//done
		Lexeme tree = null;
		if(check(VARIABLE)){
			tree=match(VARIABLE);
			tree.left=null;
			tree.right=morePrimary();
		}else if(check(INTEGER)){
			tree=match(INTEGER);
		}else if(check(TRUE)){
			tree=match(TRUE);
		}else if(check(FALSE)){
			tree=match(FALSE);
		}else if(check(STRING)){
			tree=match(STRING);
		}else if(check(OPAREN)){
			tree=match(OPAREN);
			tree.left=null;
			tree.right=expression();
			match(CPAREN);
		}
		return tree;
	}
	public Lexeme morePrimary(){//done
		Lexeme tree=null;
		if(ArrayAccessPending()){
			tree=ArrayAccess();
		}else if(FieldAccessPending()){
			tree=FieldAccess();
		}else if(FunctionCallPending()){
			tree=FunctionCall();
		}else if(ClassCallPending()){
			tree=ClassCall();
		}
		return tree;
	}
	public boolean ArrayAccessPending(){//done
		return check(LEFT_SQUARE_BRACKET);
	}
	public boolean FieldAccessPending(){//done
		return check(POINT);
	}
	public boolean FunctionCallPending(){//done
		return check(OPAREN);
	}
	public boolean ClassCallPending(){//done
		return check(DOLLAR);
	}
	public Lexeme ArrayAccess(){//DONE
		Lexeme tree = new Lexeme(ARRAY_CALL);
		Lexeme tmp = match(LEFT_SQUARE_BRACKET);
		tmp.left=null;
		tmp.right=expression();
		tree.left=tmp;
		match(RIGHT_SQUARE_BRACKET);
		tree.right=morePrimary();
		return tree;
	}
	public Lexeme FieldAccess(){//DONE
		Lexeme tree = match(POINT);
		tree.left=match(VARIABLE);
		tree.right=morePrimary();
		return tree;
	}
	public Lexeme FunctionCall(){//DONE
		Lexeme tree = new Lexeme(FUNC_CALL);
		Lexeme tmp = match(OPAREN);
		tmp.left=null;
		tmp.right=optExpressionList();
		tree.left=tmp;
		match(CPAREN);
		tree.right=morePrimary();
		return tree;
	}
	public Lexeme ClassCall(){//DONE
		Lexeme tree = new Lexeme(CLASS_CALL);
		match(DOLLAR);
		tree.right=morePrimary();
		return tree;
	}
	public boolean VariablePending(){//DONE
		return check(VARIABLE);
	}
	
	public boolean BlockPending(){//DONE
		return check(OPEN_BRACE);
	}
	
}
