/*
 * Author:Songhui Yue
 * 2014-10-04
 */
public class Interpreter extends Types{
	public static int a = 0;
	public static Lexeme eval(Lexeme tree, Lexeme env){
		boolean funcCall = false;
		boolean classCall = false;
		boolean fieldAccess = false;
		boolean fieldAssign = false;
		boolean arrayAccess = false;
		boolean arrayAssign = false;
		if(tree!=null&&tree.right!=null){
			if(tree.right.type==FUNC_CALL){
				//System.out.println("here I am in the eval about: "+tree.right.type);
				funcCall=true;
			}
		}
		if(tree!=null&&tree.right!=null){
			if(tree.right.type==FUNC_CALL){
				//System.out.println("here I am in the eval about: "+tree.right.type);
				funcCall=true;
			}
		}
		if(tree!=null&&tree.left!=null&&tree.left.right!=null&&tree.left.right.type=="array_call"&&tree.right!=null){
			//System.out.println("here I am in the eval array0: "+tree.type);
			//System.out.println("here I am in the eval array0000: "+tree.left.right.type);
			if(tree.type==ASSIGN&&tree.left.right.left.type==LEFT_SQUARE_BRACKET){
				//System.out.println("here I am in the eval array1: "+tree.right.type);
				arrayAssign=true;
			}
		}else if(tree!=null&&tree.right!=null&&tree.right.left!=null){
			if(tree.right.left.type==LEFT_SQUARE_BRACKET){
				//System.out.println("here I am in the eval array2: "+tree.right.type);
				arrayAccess=true;
			}
		}
		if(tree!=null&&tree.right!=null){
			if(tree.right.type==CLASS_CALL){
				//System.out.println("here I am in the eval about: "+tree.right.type);
				classCall=true;
			}
		}
		if(tree!=null&&tree.left!=null&&tree.left.right!=null&&tree.right!=null){
			if(tree.type==ASSIGN&&tree.left.right.type==POINT){
				//System.out.println("here I am in the eval fieldAssign1: "+tree.left.word);
				//System.out.println("here I am in the eval fieldAssign2: "+tree.right.ival);
				fieldAssign=true;
			}
		}else if(tree!=null&&tree.right!=null){
			if(tree.right.type==POINT){
				//System.out.println("here I am in the eval fieldAccess1: "+tree.word);
				//System.out.println("here I am in the eval fieldAccess2: "+tree.right.type);
				fieldAccess=true;
			}
		}
		//System.out.println("here I am in the eval again: "+tree.type);
		if(FUNC_GLUE.equals(tree.type)||GLUE.equals(tree.type)){
			return evalFuncGlue(tree, env);
		}else if(funcCall){
			return evalFuncCall(tree, env);
		}else if(arrayAccess){
			return evalArrayAccess(tree, env);
		}else if(arrayAssign){
			return evalArrayAssign(tree, env);
		}else if(classCall){
			return evalClassCall(tree, env);
		}else if(fieldAccess){
			return evalFieldAccess(tree, env);
		}else if(fieldAssign){
			return evalFieldAssign(tree, env);
		}else if(INTEGER.equals(tree.type)){
			return tree;
		}else if(DOUBLE.equals(tree.type)){
			return tree;
		}else if(STRING.equals(tree.type)){
			return tree;
		}else if(VARIABLE.equals(tree.type)){
			if(tree.right!=null&&tree.right.type==SEMICOLON){
				return evalDeclare(tree, env);
			}
			return Environment.lookup(tree, env);
		}else if(OPAREN.equals(tree.type)){
			return eval(tree.right, env);
		}else if(PLUS.equals(tree.type)
				||MINUS.equals(tree.type)
				||DIVIDES.equals(tree.type)
				||TIMES.equals(tree.type)){
			return evalSimpleOp(tree, env);
		}else if(OR.equals(tree.type)){
			return evalShortCircuitOp(tree, env);
		}else if(POINT.equals(tree.type)){
			return evalPoint(tree, env);
		}else if(ASSIGN.equals(tree.type)){
			return evalAssign(tree, env);
		}else if(EQUALEQUAL.equals(tree.type)){
			return evalEqualEqual(tree, env);
		}else if(GREATERTHAN.equals(tree.type)){
			return evalGreaterThan(tree, env);
		}else if(LESSTHAN.equals(tree.type)){
			//System.out.println("I am eval less");
			return evalLessThan(tree, env);
		}else if(GREATEROREQUALTHAN.equals(tree.type)){
			return evalGreaterOrEqualThan(tree, env);
		}else if(LESSOREQUALTHAN.equals(tree.type)){
			return evalLessOrEqualThan(tree, env);
		}else if(FUNC_DEF.equals(tree.type)){
			//System.out.println("Good luck 111111111111");
			//System.out.println("Good luck 1111111111112"+tree.right.type+" "+tree.right.word);
			return evalFuncDef(tree, env);
		}else if(CLASS_DEF.equals(tree.type)){
			//System.out.println("Good luck 111111111111");
			//System.out.println("Good luck 1111111111112"+tree.right.type+" "+tree.right.word);
			return evalClassDef(tree, env);
		}else if(MAIN_DEF.equals(tree.type)){
			//System.out.println("Good luck 2222222222");
			//System.out.println("Good luck 1111111111112"+tree.right.type+" "+tree.right.word);
			return eval(tree.right.right.right, env);
		}else if(IF.equals(tree.type)){
			return evalIf(tree, env);
		}else if(PRINTLN.equals(tree.type)){
			return evalPrint(tree, env);
		}else if(WHILE.equals(tree.type)){
			return evalWhile(tree, env);
		}else if(BLOCK.equals(tree.type)){
			return evalBlock(tree, env);
		}else if(ASSIGN.equals(tree.type)){
			return evalAssign(tree, env);
		}else if(OPEN_BRACE.equals(tree.type)){
			eval(tree.left,env);
			eval(tree.right,env);
			return env;
		}else{
			return null;
		}
	}

	public static Lexeme evalFuncDef(Lexeme tree, Lexeme env){
		Lexeme closure = Environment.cons(CLOSURE, env, tree);
		Environment.insert(getFuncDefName(tree), closure, env);
		return closure;
	}
	public static Lexeme evalClassDef(Lexeme tree, Lexeme env){
		Lexeme closure = Environment.cons(CLOSURE, env, tree);
		Environment.insert(getFuncDefName(tree), closure, env);
		return closure;
	}
	public static Lexeme evalFuncCall(Lexeme tree, Lexeme env){
		Lexeme fname = getFuncCallName(tree);
		Lexeme closure;
		Lexeme args = getFuncCallArgs(tree);
		//System.out.println("this is fname: "+fname.word);
		//System.out.println("this is args left: "+args.left.word);
		if("delay".equals(fname.word)){
			//System.out.println("this is args left1111: "+args.left.word);
			Lexeme fatherenv= env.right.right;
			//PrettyPrinter.printTree(fatherenv);
			//System.out.println("^^^^^^^father env :  "+ fatherenv.left.right.left.word);
			//System.out.println("this is args 22222: "+eval(args.left,env).word);
			Lexeme p_result = new Lexeme(eval(args.left,env).type, eval(args.left,env).word);
			//System.out.println("this is args 33333: "+eval(p_result, fatherenv).ival);
			return eval(p_result, fatherenv);
		}else{
			closure = eval(getFuncCallName(tree), env);
		}
		Lexeme params = getClosureParams(closure);
		Lexeme body = getClosureBody(closure);
		Lexeme senv = getClosureEnvironment(closure);
		//System.out.println("this is params left: "+params.left.word);
		if(params!=null&&params.left!=null&&"delay".equals(params.left.word)){
			params=params.right;
			senv = env;
			Lexeme xenv = Environment.extend(params, args, senv);
			return eval(body, xenv);
		}
		Lexeme eargs = evalArgs(args, env);
		Lexeme xenv = Environment.extend(params, eargs, senv);

		return eval(body, xenv);
	}
	public static Lexeme evalFuncCall_backup(Lexeme tree, Lexeme env){
		Lexeme closure = eval(getFuncCallName(tree), env);
		Lexeme args = getFuncCallArgs(tree);
		Lexeme params = getClosureParams(closure);
		Lexeme body = getClosureBody(closure);
		Lexeme senv = getClosureEnvironment(closure);
		Lexeme eargs = evalArgs(args, env);
		Lexeme xenv = Environment.extend(params, eargs, senv);

		return eval(body, xenv);
	}
	public static Lexeme evalArrayAccess(Lexeme tree, Lexeme env){
		Lexeme variable = new Lexeme(tree.type, tree.word);
		Lexeme value = eval(variable, env);
		int numberPassed = tree.right.left.right.ival;
		if(value.arrayType==INTEGER){
			return new Lexeme(INTEGER, value.iarray[numberPassed-1]);
		}else{
			return new Lexeme(STRING, value.sarray[numberPassed-1]);
		}
	}
	public static Lexeme evalArrayAssign(Lexeme tree, Lexeme env){
		Lexeme variable = tree.left;
		Lexeme value = eval(tree.right, env);
		int numberPassed = variable.right.left.right.ival;
		variable.isArray = true;
		if(variable.arraySize==0){
			variable.arraySize = numberPassed;
			if(value.type==INTEGER){
				variable.iarray = new int[variable.arraySize];
				variable.arrayType = INTEGER;
			}else if(value.type==STRING){
				variable.sarray = new String[variable.arraySize];
				variable.arrayType = STRING;
			}
		}
		if(value.type==INTEGER){
			variable.iarray[numberPassed-1]=value.ival;
		}else if(value.type==STRING){
			variable.sarray[numberPassed-1]=value.word;
		}
		//System.out.println("Hello*************************hello1");
		if(Environment.existsInCurrentScope(variable,env)){
			//System.out.println("Hello*************************hello3333333 "+tree.left.word);
			Lexeme old = Environment.lookup(variable,env);
			if(old.arrayType==INTEGER){
				old.iarray[numberPassed-1]=value.ival;
			}else if(old.arrayType==STRING){
				old.sarray[numberPassed-1]=value.word;
			}
			Environment.update(old, old, env);
		}else{
			//System.out.println("Hello*************************hello4 "+tree.left.word);
			Environment.insert(variable, variable, env);
		}
		return value;
	}
	public static Lexeme evalClassCall(Lexeme tree, Lexeme env){
		Lexeme closure = eval(getFuncCallName(tree), env);
		Lexeme body = getClassClosureBody(closure);
		Lexeme classEnv = Environment.create();
		eval(body, classEnv);
		return classEnv;
	}
	public static Lexeme evalFieldAccess(Lexeme tree, Lexeme env){
		Lexeme variable = new Lexeme(tree.type, tree.word);
		Lexeme xenv = eval(variable, env);
//		System.out.println("fieldaccess1 xenv name is: "+variable.word);
//		System.out.println("fieldaccess2 xenv type is: "+xenv.type);
//		System.out.println("fieldaccess3 xenv is: "+xenv.left.left.type);
//		System.out.println("fieldaccess4 xenv is: "+xenv.left.left.word);
//		System.out.println("fieldaccess5 xenv is: "+xenv.right.left.type);
		Lexeme field = new Lexeme(tree.right.left.type, tree.right.left.word);
//		System.out.println("now evalfield field is: "+field.word);
		return eval(field, xenv);
	}
	public static Lexeme evalFieldAssign(Lexeme tree, Lexeme env){
		Lexeme variable = new Lexeme(tree.left.type, tree.left.word);
		Lexeme xenv = eval(variable, env);
		Lexeme field = new Lexeme(tree.left.right.left.type, tree.left.right.left.word);
		Lexeme value = eval(tree.right, env);
		//System.out.println("evalFieldAssign 1: "+value.type);
		//System.out.println("evalFieldAssign 2: "+field.word);
		//System.out.println("evalFieldAssign 3: "+variable.word);
		Lexeme newTree = new Lexeme(ASSIGN);
		newTree.left = field;
		newTree.right = value;
		//System.out.println("name if xenv is: "+variable.word);
		//System.out.println("name if field is: "+field.word);
		return eval(newTree, xenv);
	}
	public static Lexeme getFuncCallName(Lexeme tree){
		Lexeme name = new Lexeme(tree.type, tree.word);
		return name;
	}
	public static Lexeme getFuncCallArgs(Lexeme tree){
		Lexeme args = null;
		args=tree.right.left.right;
		// or name=tree.left.right; // connct with evalArgs()
		return args;
	}
	public static Lexeme getClosureParams(Lexeme closure){
		Lexeme params = null;
		params = closure.right.right.left.right;
		return params;
	}
	public static Lexeme getClosureBody(Lexeme closure){
		Lexeme body = null;
		body = closure.right.right.right.right;
		return body;
	}
	public static Lexeme getClassClosureBody(Lexeme closure){
		Lexeme body = null;
		//PrettyPrinter.printTree(closure);
		//System.out.println("the type is : "+closure.right.right.right.type);
		body = closure.right.right.right;
		return body;
	}
	public static Lexeme getClosureEnvironment(Lexeme closure){
		Lexeme senv = null;
		senv = closure.left;
		return senv;
	}
	public static Lexeme evalArgs(Lexeme args, Lexeme env){
		Lexeme eargs = null;
		if(args!=null){
			eargs = new Lexeme(JOIN);
			eargs.left = eval(args.left, env);
			eargs.right = evalArgs(args.right,env);
		}
		return eargs;
	}
	public static Lexeme getFuncDefName(Lexeme tree){
		Lexeme name = new Lexeme();
		name.type = tree.right.type;
		name.word = tree.right.word;
		return name;
	}
	public static Lexeme evalFuncGlue(Lexeme tree, Lexeme env){
		Lexeme result = null;
		while(tree!=null&&tree.left!=null){
			result = eval(tree.left, env);
			tree=tree.right;
		}
		return result;
	}
	public static Lexeme evalSimpleOp(Lexeme tree, Lexeme env){
		if(tree.type==PLUS){
			return evalPlus(tree, env);
		}else if(tree.type==MINUS){
			return evalMinus(tree, env);
		}else if(tree.type==TIMES){ 
			return evalTimes(tree, env);
		}else if(tree.type==DIVIDES){
			return evalDivides(tree, env);
		}
		return null;
	}

	public static Lexeme evalPlus(Lexeme tree, Lexeme env){
		//System.out.println("###################################start");
		//PrettyPrinter.printTree(tree);
		//System.out.println("###################################end");
		Lexeme left = eval(tree.left, env);
		//PrettyPrinter.printTree(left);
		Lexeme right = eval(tree.right, env);
		//PrettyPrinter.printTree(right);
		if(left.type==INTEGER && right.type == INTEGER){
			//System.out.println("I am at:  "+left.type+ " and "+right.type);
			return new Lexeme(INTEGER, (left.ival + right.ival));
		}else if(left.type==STRING && right.type == INTEGER){
			//System.out.println("I am at:  "+left.type+ " and "+right.type);
			return new Lexeme(STRING, left.word+String.valueOf(right.ival));
		}else if(left.type==INTEGER && right.type == STRING){
			//System.out.println("I am at:  "+left.type+ " and "+right.type);
			return new Lexeme(STRING, String.valueOf(left.ival)+right.word);
		}else{
			System.out.println("the variables types are not matched: "+left.type+ " and "+right.type);
		}
		return null;
	}

	public static Lexeme evalMinus(Lexeme tree, Lexeme env){
		Lexeme left = eval(tree.left, env);
		Lexeme right = eval(tree.right, env);
		if(left.type==INTEGER && right.type == INTEGER){
			return new Lexeme(INTEGER, (left.ival - right.ival));
		}else{
			System.out.println("the variables types are not matched: "+left.type+ " and "+right.type);
		}
		return null;
	}

	public static Lexeme evalTimes(Lexeme tree, Lexeme env){
		Lexeme left = eval(tree.left, env);
		Lexeme right = eval(tree.right, env);
		if(left.type==INTEGER && right.type == INTEGER){
			return new Lexeme(INTEGER, (left.ival * right.ival));
		}else{
			System.out.println("the variables types are not matched: "+left.type+ " and "+right.type);
		}
		return null;
	}
	
	public static Lexeme evalDivides(Lexeme tree, Lexeme env){
		Lexeme left = eval(tree.left, env);
		Lexeme right = eval(tree.right, env);
		if(left.type==INTEGER && right.type == INTEGER){
			return new Lexeme(INTEGER, (left.ival / right.ival));
		}else{
			System.out.println("the variables types are not matched: "+left.type+ " and "+right.type);
		}
		return null;
	}
	public static Lexeme evalEqualEqual(Lexeme tree, Lexeme env){
		Lexeme left = eval(tree.left, env);
		Lexeme right = eval(tree.right, env);
		if(left.type==INTEGER && right.type == INTEGER && left.ival==right.ival){
			return new Lexeme(TRUE);
		}else if(left.type==STRING && right.type == STRING && left.word.equals(right.word)){
			return new Lexeme(TRUE);
		}else if(left.type==DOUBLE && right.type == DOUBLE && left.real==right.real){
			return new Lexeme(TRUE);
		}
		return new Lexeme(FALSE);
	}
	public static Lexeme evalGreaterThan(Lexeme tree, Lexeme env){//to be better
		Lexeme left = eval(tree.left, env);
		Lexeme right = eval(tree.right, env);
		if(left.type==INTEGER && right.type == INTEGER && left.ival>right.ival){
			return new Lexeme(TRUE);
		}
		return new Lexeme(FALSE);
	}
	public static Lexeme evalLessThan(Lexeme tree, Lexeme env){//to be better
		Lexeme left = eval(tree.left, env);
		//PrettyPrinter.printTree(left);
		Lexeme right = eval(tree.right, env);
		//PrettyPrinter.printTree(right);
		if(left.type==INTEGER && right.type == INTEGER && left.ival<right.ival){
			return new Lexeme(TRUE);
		}
		return new Lexeme(FALSE);
	}
	public static Lexeme evalGreaterOrEqualThan(Lexeme tree, Lexeme env){//to be better
		Lexeme left = eval(tree.left, env);
		Lexeme right = eval(tree.right, env);
		if(left.type==INTEGER && right.type == INTEGER && left.ival>=right.ival){
			return new Lexeme(TRUE);
		}
		return new Lexeme(FALSE);
	}
	public static Lexeme evalLessOrEqualThan(Lexeme tree, Lexeme env){//to be better
		Lexeme left = eval(tree.left, env);
		Lexeme right = eval(tree.right, env);
		if(left.type==INTEGER && right.type == INTEGER && left.ival<=right.ival){
			return new Lexeme(TRUE);
		}
		return new Lexeme(FALSE);
	}
	public static Lexeme evalAssign(Lexeme tree, Lexeme env){
		//System.out.println("Hello*************************hello1");
		//System.out.println("Hello*************************hello1: "+tree.right.type);
		Lexeme value;
		if(tree.right.type=="env"){
			value = tree.right;
			//System.out.println("evalAssign I am now inserting env111: "+ tree.left.word);
		}else {
			//System.out.println("evalAssign I am assigning the env222 : "+ tree.left.word);
			//PrettyPrinter.printTree(tree.right);
			value = eval(tree.right, env);
			if(value.type=="env"){
				//System.out.println("evalAssign I am assigning the env333 : "+ tree.left.word);
			}
		}
		//System.out.println("Hello*************************hello1");
		if(Environment.existsInCurrentScope(tree.left,env)){
			//System.out.println("Hello*************************hello44444 "+tree.left.word);
			Environment.update(tree.left, value, env);
		}else{
			//System.out.println("Hello*************************hello55555 "+tree.left.word);
			Environment.insert(tree.left, value, env);
		}
		return value;
	}

	public static Lexeme evalDeclare(Lexeme tree, Lexeme env){
		Lexeme variable = new Lexeme(tree.type, tree.word);
		Lexeme value = new Lexeme(NULL);
		Environment.insert(variable, value, env);
		return value;
	}
	public static Lexeme evalBlock(Lexeme tree, Lexeme env){
		Lexeme result=null;
		while(tree!=null){
			//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			//PrettyPrinter.printTree(tree.left);
			result = eval(tree.left, env);
			tree=tree.right;
		}
		return result;
	}
	
	public static Lexeme evalShortCircuitOp(Lexeme tree, Lexeme env){//this is useless, done by other one
		return null;
	}
	
	public static Lexeme evalPoint(Lexeme tree, Lexeme env){//this is useless, done by other one
		System.out.println("I am now at eval point");
		if(tree.type==INTEGER){
			return evalPlus(tree, env);
		}else if(tree.type==MINUS){
			return evalMinus(tree, env);
		}
		return null;
	}
	
	public static Lexeme evalIf(Lexeme tree, Lexeme env){
		Lexeme result1 = eval(tree.left.right, env);
		//PrettyPrinter.printTree(result1);
		if(eval(tree.left.right, env).type==TRUE){
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			Lexeme tmp = eval(tree.right.left.right, env);
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^2");
			//PrettyPrinter.printTree(Environment.lookup(new Lexeme(VARIABLE, "y"), env));
			//PrettyPrinter.printTree(tree.right.left);
			return tmp;
		}else if(tree.right.right!=null){
			//System.out.println("******************************************************************");
			return eval(tree.right.right.right.right, env);
		}
		return null;
	}
	public static Lexeme evalPrint(Lexeme tree, Lexeme env){
		Lexeme result = null;
		if(tree.left.right==null){
			System.out.println();
		}else{
			Lexeme ex = tree.left.right;
			Lexeme re = eval(ex,env);
			if(re.type==INTEGER){
				System.out.println(re.ival);
			}else{
				String tmp = re.word;
				String firstletter = tmp.substring(0, 1);
				if("\"".equals(firstletter)){
					System.out.println(tmp.substring(1).replace('"', ' '));
				}else{
					System.out.println(tmp);
				}
			}
		}
		return result;
	}
	
	public static Lexeme evalWhile(Lexeme tree, Lexeme env){
		Lexeme result = null;
		while(eval(tree.left.right,env).type==TRUE){
			result=eval(tree.right.right,env);
		}
		return result;
	}
}
