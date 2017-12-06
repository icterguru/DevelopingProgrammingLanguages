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

public class Scanner extends Types{
	public static String endOfFile="EndOfFile";
	public static void main(String[] args) throws IOException {

	  Lexer l = new Lexer(args[0]);
	  //Lexer l = new Lexer("/Users/songhuiyue/Downloads/Grammer/program2.txt");
	  Lexeme token = l.lex();
	  int i=0;
	  while(true)
	  {
		  if("EndOfFile".equals(token.type)){
			  System.out.println(endOfFile);
			  break;
		  }
		  if("notes".equals(token.type)){
			  //print notes type or print nothing
			  System.out.println(token.type);
		  }else if(token.word!=null||token.type==INTEGER||token.type==DOUBLE){
			  if(token.type==INTEGER){
				  System.out.println(token.type+"\t"+token.ival);
			  }else if(token.type==DOUBLE){
				  System.out.println(token.type+"\t"+token.real);
			  }else{
				  System.out.println(token.type+"\t"+token.word);
			  }
		  }else
			  System.out.println(token.type);

		  token = l.lex();
		  i++;
	  }
	}
}
