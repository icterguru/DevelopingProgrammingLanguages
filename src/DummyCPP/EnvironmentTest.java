/*
 * Author:Songhui Yue
 * 2014-10-04
 */
public class EnvironmentTest extends Types{
	
	public static void main(String args[]){
		Lexeme testVariable = new Lexeme(VARIABLE, "a");
		Lexeme testVariable2 = new Lexeme(VARIABLE, "test2");
		Lexeme testValue = new Lexeme(INTEGER, "3");
		Lexeme env = Environment.create();

		//test printEnvironment print empty environment
		System.out.println("testing printEnvironment....");
		Environment.printEnvironment(env);
		
		//test create
		System.out.println("testing create....");
		System.out.println("environment lexeme is: "+ env.type+"   |   "+env.word);
		Lexeme result1 = Environment.lookup(testVariable, env);
		//System.out.println(result.type+"   |   "+result.word);
		
		//test insert and lookup
		System.out.println("testing insert and lookup....");
		Environment.insert(testVariable, testValue, env);
		Lexeme result2 = Environment.lookup(testVariable, env);
		System.out.println(result2.type+"   ||   "+result2.word);
		
		//test printEnvironment
		System.out.println("testing printEnvironment....");
		Environment.printEnvironment(env);
		Lexeme joinleft1 = new Lexeme(JOIN);
		Lexeme joinleft2 = new Lexeme(JOIN);
		Lexeme joinright1 = new Lexeme(JOIN);
		Lexeme joinright2 = new Lexeme(JOIN);
		joinleft1.left=new Lexeme(VARIABLE, "test1");
		joinleft1.right=joinleft2;
		joinleft2.left=new Lexeme(VARIABLE, "test2");
		joinleft2.right=null;
		joinright1.left=new Lexeme(INTEGER, 99);
		joinright1.right=joinright2;
		joinright2.left=new Lexeme(INTEGER, 100);
		joinright2.right=null;
		
		//test extend
		System.out.println("testing extend....");
		Lexeme newEnv = Environment.extend(joinleft1, joinright1, env);
		Environment.printEnvironment(newEnv);
		
		//test update
		System.out.println("testing update....");
		Environment.update(new Lexeme(VARIABLE, "test1"), new Lexeme(INTEGER, 999), newEnv);
		Environment.printEnvironment(newEnv);
		
		//test lookup after extend the env
		System.out.println("testing lookup after extend....");
		Lexeme result3 = Environment.lookup(testVariable2, newEnv);
		System.out.println(result3.type+"   ||   "+result3.ival);
	}
}