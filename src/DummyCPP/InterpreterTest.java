/*
 * Author:Songhui Yue
 * 2014-10-04
 */
public class InterpreterTest extends Types{
	public static void main(String args[]){
		//Parser1122 p = new Parser1122("/Users/songhuiyue/Downloads/Grammer/program2.txt");
		Parser1122 p = new Parser1122(args[0]);
		Lexeme root= p.getTree();
		//PrettyPrinter.prettyPrint(root);
		Lexeme env = Environment.create();
		//Environment.printEnvironment(env);
		Lexeme result = Interpreter.eval(root, env);
		//Environment.printEnvironment(env);
		//Lexeme tmp = new Lexeme(VARIABLE, "a");
		//Environment.lookup(tmp, result);
		//System.out.println(Interpreter.eval(root, env));
	}
}
