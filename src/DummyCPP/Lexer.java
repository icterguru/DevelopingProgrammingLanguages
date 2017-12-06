/*
 * Author:Songhui Yue
 * 2014-10-04
 */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

public class Lexer extends Types{
	public String fileName;
	public static List charList= new ArrayList();
	public static int index=0;
	public static PushbackReader reader;
	public static String test;
	public Lexer(String fileName){
		this.fileName=fileName;
		File file = new File(fileName); 
		try{
			reader = new PushbackReader(new FileReader(fileName));
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	}

	public static Lexeme lex(){
		
		Lexeme white = skipWhiteSpace();
		if(white!=null&&white.type==DIVIDES){
			return white;
		}
		int r=-2;
		try{
			r=reader.read();
			//System.out.println("what am i now : "+(char)r);

		}catch(IOException e){
			e.printStackTrace();
		}
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}
		char tmp = (char)r;
		//System.exit(1);
		switch(tmp){
		//single character tokens
		case '(':
			return new Lexeme(OPAREN);
		case ')':
			return new Lexeme(CPAREN);
		case '$':
			return new Lexeme(DOLLAR);
		case '{':
			return new Lexeme(OPEN_BRACE);
		case '}':
			return new Lexeme(CLOSE_BRACE);
		case '[':
			return new Lexeme(LEFT_SQUARE_BRACKET);
		case ']':
			return new Lexeme(RIGHT_SQUARE_BRACKET);
		case ',':
			return new Lexeme(COMMA);
		case '.':
			return new Lexeme(POINT);
		case '+':
			return checkPlus();
		case '*':
			return new Lexeme(TIMES);
		case '-':
			return checkMinus();
		case '/':
			Lexeme frank = checkNotesOrDivides();
			return frank;
		case '%':
			return new Lexeme(MODE);
		case '?':
			return new Lexeme(QUESTIONMARK);
		case '!':
			return new Lexeme(EXCLAMATIONMARK);
		case '<':
			return checkLessThan();
		case '>':
			return checkMoreThan();
		case '=':
			return checkAssign();
		case ':':
			return new Lexeme(COLON);
		case ';':
			return new Lexeme(SEMICOLON);
		case '&':
			return checkAnd();
		case '|':
			return checkOr();
		default:
			if(Character.isDigit(tmp)){
				pushback(r);
				Lexeme ltmp= lexNumber();
				return ltmp;
			}else if(Character.isLetter(tmp)){
				pushback(r);
				Lexeme ltmp= lexVariable();
				return ltmp;
			}else if(tmp=='\"'){
				pushback(r);
				Lexeme ltmp= lexString();
				return ltmp;
			}
			else
				System.out.println(r);
				return new Lexeme(UNKNOWN, (char)r);
		}
	}
	
	public static Lexeme skipWhiteSpace(){
		int r=readnext();
		Lexeme tmp = null;
		while(' '==(char)r||'\t'==(char)r||'\n'==(char)r||'\b'==(char)r||'\r'==(char)r){
			r=readnext();
		}
		if('/'==(char)r)
		{
			tmp = checkNotesOrDivides();
			skipWhiteSpace();
		}else{
			pushback(r);
		}
		return tmp;
	}
	

	public static void skipThisLine(){
		int r=readnext();
			
		while('\n'!=(char)r){
			r=readnext();
			if(r==-1){
				return;
			}
		}
		//pushback(r);
	}

	public static void skipToNoteEnd(){
		int r=readnext();
			
		while('*'!=(char)r){
			r=readnext();
			if(r==-1){
				return;
			}
		}
		if('*'==(char)r){
			r=readnext();
			if(r==-1){
				return;
			}else if('/'==(char)r){
				return;
			}else{
				skipToNoteEnd();
			}
		}
	}
	
	public static void pushback(int r){
		try{
			reader.unread(r);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public static int readnext(){
		int r=-1;
		try{
			r=reader.read();
		}catch(IOException e){
			e.printStackTrace();
		}
		return r;
	}
	
	public static Lexeme lexNumber(){
		int r=-1;
		r=readnext();
		char tmp=(char)r;
		String number="";
		boolean onePoint=false;
		while(Character.isDigit(tmp)){
			number+=tmp;
			r=readnext();
			tmp=(char)r;
			if(".".equals(Character.toString(tmp))&&!onePoint){
				number+=tmp;
				r=readnext();
				tmp=(char)r;
				onePoint=true;
			}
		}
		pushback(r);
		if(onePoint==true){
			return new Lexeme(DOUBLE, Double.parseDouble(number));
		}else{
			return new Lexeme(INTEGER, Integer.parseInt(number));
		}
	}
	public static Lexeme lexVariable(){
		int r=-1;
		r=readnext();
		char tmp=(char)r;
		String word="";
		while(Character.isLetter(tmp)||Character.isDigit(tmp)||isUnderScore(tmp)){
			//this is syntactic things if no punctuation after variable
			if(r==-1||r>=65535||r<=-65535){
				return new Lexeme(endOfFile);
			}
			word+=tmp;
			r=readnext();
			tmp=(char)r;
		}
		pushback(r);
		if("true".equals(word)){
			return new Lexeme(TRUE, word);
		}else if("false".equals(word)){
			return new Lexeme(FALSE, word);
		}else if("if".equals(word)){
			return new Lexeme(IF);
		}else if("var".equals(word)){
			return new Lexeme(VAR);
		}else if("else".equals(word)){
			return new Lexeme(ELSE);
		}else if("while".equals(word)){
			return new Lexeme(WHILE);
		}else if("return".equals(word)){
			return new Lexeme(RETURN);
		}else if("void".equals(word)){
			return new Lexeme(VOID);
		}else if("main".equals(word)){
			return new Lexeme(MAIN);
		}else if("class".equals(word)){
			return new Lexeme(CLASS);
		}else if("println".equals(word)){
			return new Lexeme(PRINTLN);
		}else if(isReservedWord(word)){
			return new Lexeme(word);
		}else
			return new Lexeme(VARIABLE, word);
	}
	
	public static Lexeme lexString(){
		int r=-1;
		r=readnext();
		char tmp=(char)r;
		String word="";
		word+=tmp;
		r=readnext();
		tmp=(char)r;
		while(!"\"".equals(Character.toString(tmp))){
			//this is lexical error
			if(r==-1){
				System.out.println("String input error: "+word);
				System.exit(0);
			}
			if("\\".equals(Character.toString(tmp))){
				r=readnext();
				if(r==-1){
					System.out.println("String input error: "+word);
					System.exit(0);
				}
				tmp=(char)r;
				if("\\".equals(Character.toString(tmp))){
					word+=tmp;
					r=readnext();
					if(r==-1){
						System.out.println("String input error: "+word);
						System.exit(0);
					}
					tmp=(char)r;
					continue;
				}else if("\"".equals(Character.toString(tmp))){
					word+=tmp;
					r=readnext();
					if(r==-1){
						System.out.println("String input error: "+word);
						System.exit(0);
					}
					tmp=(char)r;
					continue;
				}else if("t".equals(Character.toString(tmp))){
					word+="\t";
					r=readnext();
					if(r==-1){
						System.out.println("String input error: "+word);
						System.exit(0);
					}
					tmp=(char)r;
					continue;
				}else if("n".equals(Character.toString(tmp))){
					word+="\n";
					r=readnext();
					if(r==-1){
						System.out.println("String input error: "+word);
						System.exit(0);
					}
					tmp=(char)r;
					continue;
				}else if("r".equals(Character.toString(tmp))){
					word+="\r";
					r=readnext();
					if(r==-1){
						System.out.println("String input error: "+word);
						System.exit(0);
					}
					tmp=(char)r;
					continue;
				}
			}
			word+=tmp;
			r=readnext();
			tmp=(char)r;
		}
		word+=tmp;
		return new Lexeme(STRING, word);
	}
	
	public static boolean isUnderScore(char tmp){
		return "_".equals(Character.toString(tmp));
	}
	public static Lexeme checkPlus(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('+'==(char)r){
			return new Lexeme(PLUSPLUS);
		}else{
			pushback(r);
			return new Lexeme(PLUS);
		}
	}
	public static Lexeme checkMinus(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('-'==(char)r){
			return new Lexeme(MINUSMINUS);
		}else{
			pushback(r);
			return new Lexeme(MINUS);
		}
	}
	public static Lexeme checkAssign(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('='==(char)r){
			return new Lexeme(EQUALEQUAL);
		}else{
			pushback(r);
			return new Lexeme(ASSIGN);
		}
	}
	public static Lexeme checkLessThan(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('<'==(char)r){
			return new Lexeme(LESSTHANLESSTHAN);
		}else if('='==(char)r){
			return new Lexeme(LESSOREQUALTHAN);
		}else{
			pushback(r);
			return new Lexeme(LESSTHAN);
		}
	}
	public static Lexeme checkMoreThan(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('>'==(char)r){
			return new Lexeme(GREATERTHANGREATERTHAN);
		}else if('='==(char)r){
			return new Lexeme(GREATEROREQUALTHAN);
		}else{
			pushback(r);
			return new Lexeme(GREATERTHAN);
		}
	}
	public static Lexeme checkNotesOrDivides(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('/'==(char)r){
			skipThisLine();
			return new Lexeme("notes");
		}else if('*'==(char)r){
			skipToNoteEnd();
			return new Lexeme("notes");
		}
		pushback(r);
		return new Lexeme(DIVIDES);
	}

	public static Lexeme checkAnd(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('&'==(char)r){
			return new Lexeme(AND);
		}else{
			System.out.println("Single & error");
			System.exit(0);
		}
		return new Lexeme(AND);
	}
	public static Lexeme checkOr(){
		int r=-1;
		r=readnext();
		////this is syntactic things
		if(r==-1||r>=65535||r<=-65535){
			return new Lexeme(endOfFile);
		}else if('|'==(char)r){
			return new Lexeme(OR);
		}else{
			System.out.println("Single | error");
			System.exit(0);
		}
		return new Lexeme(OR);
	}
	
}


