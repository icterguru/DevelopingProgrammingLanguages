package DCPP;


/*
 * Author:Songhui Yue
 * 2014-10-04
 */
public class Environment extends Types{
	public static Lexeme env;
	public Environment(){
	}
	public Environment(Lexeme env){
		this.env=env;
	}
	public static Lexeme create(){
		Lexeme env = new Lexeme(ENV);
		Lexeme values= new Lexeme(VALUES);
		env.left=null;
		env.right=values;
		values.left=null;
		values.right=null;
		return env;
	}
	
	public static Lexeme lookup(Lexeme variable, Lexeme env){
		Lexeme v = null;
		while(env!=null){
			Lexeme vars = car(env);
			Lexeme vals = cadr(env);
			while(vars != null){
				if(variable.word.equals(vars.left.word)&&variable.type.equals(vars.left.type)){
					return  car(vals);
				}
				vars = vars.right;
				vals = vals.right;
			}
			env = env.right.right;
		}
		return v;
	}

	public static boolean existsInCurrentScope(Lexeme variable, Lexeme env){
		if(env!=null){
			Lexeme vars = car(env);
			Lexeme vals = cadr(env);
			//System.out.println("Hello*************************hello2");
			while(vars != null){
				if(variable.word.equals(vars.left.word)&&variable.type.equals(vars.left.type)){
					//System.out.println("I am comparing : "+vars.left.word);
					return true;
				}
				vars = vars.right;
				vals = vals.right;
			}
		}
		return false;
	}
	public static boolean existsInAllScope(Lexeme variable, Lexeme env){
		while(env!=null){
			Lexeme vars = car(env);
			Lexeme vals = cadr(env);
			while(vars != null){
				if(variable.word.equals(vars.left.word)&&variable.type.equals(vars.left.type)){
					return true;
				}
				vars = vars.right;
				vals = vals.right;
			}
			env = env.right.right;
		}
		return false;
	}
	public static Lexeme update(Lexeme variable, Lexeme newValue, Lexeme env){
		a:while(env!=null){
			Lexeme vars = env.left;
			Lexeme vals = env.right.left;
			b:while(vars != null){
				if(variable.word.equals(vars.left.word)&&variable.type.equals(vars.left.type)){
					//System.out.println("**************************");
					//PrettyPrinter.printTree(newValue);
					//System.out.println(newValue);
					if(newValue.type==INTEGER){
						vals.left=new Lexeme(newValue.type, newValue.ival);
					}else if(newValue.type==STRING){
						vals.left=new Lexeme(newValue.type, newValue.word);
					}else if(newValue.type==ENV){
						//System.out.println("***********HAHAHAHA***************");
						vals.left=newValue;
					}
					break a;
				}
				vars = vars.right;
				vals = vals.right;
			}
			env = env.right.right;
		}
		return newValue;
	}
	
	public static Lexeme insert(Lexeme variable, Lexeme value, Lexeme env){
		Lexeme join1 = new Lexeme(JOIN);
		join1.left=variable;
		join1.right=env.left;
		env.left=join1;
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
		//PrettyPrinter.printTree(variable);
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
		//PrettyPrinter.printTree(value);
		//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
		Lexeme join2 = new Lexeme(JOIN);
		join2.left=value;
		join2.right=env.right.left;
		env.right.left=join2;
		
		return value;
	}
	
	public static Lexeme extend(Lexeme variables, Lexeme values, Lexeme env){
		Lexeme newEnv = new Lexeme(ENV);
		Lexeme newValues = new Lexeme(VALUES);
		newEnv.left = variables;
		newEnv.right = newValues;
		newValues.left=values;
		newValues.right=env;
		return newEnv;
	}
	
	public static void printEnvironment(Lexeme env) {
		System.out.println("Printing environment started......");
        while (env != null) {
            Lexeme variable;
            Lexeme value;
            Lexeme leftEnv = car(env);
            Lexeme rightEnv = cadr(env);
            // print the variable
            while (leftEnv != null&&rightEnv != null) {
                if (car(leftEnv) != null) {
                	variable = car(leftEnv);
                    System.out.print("Variable: " + variable.word + "      ");
                }
                leftEnv = leftEnv.right;
                
                if ((value = car(rightEnv)) != null) {
                    if (value.type.equals(INTEGER))
                        System.out.println("Value: " + value.ival);
                    else
                        System.out.println("Value: " + value.word);
                }
                rightEnv = rightEnv.right;
            }
            env = cddr(env); // move to the next one
        }
        
        System.out.println("Printing environment finished......");
    }
	
	
    public static Lexeme cons(String type, Lexeme left, Lexeme right) {
        Lexeme tree = new Lexeme(type);
        tree.left = left;
        tree.right = right;
        return tree;
    }
    
    public static Lexeme car(Lexeme env) {
        return env.left;
    }
    
    public static Lexeme cdr(Lexeme env) {
        return env.right;
    }
    
    public static Lexeme cadr(Lexeme env) {
        return car(cdr(env));
    }
    
    public static Lexeme cddr(Lexeme env) {
        return cdr(cdr(env));
    }
    
	public static Lexeme setCar(Lexeme env, Lexeme tree) {
        env.left = tree;
        return env;
    }
    
	
}
