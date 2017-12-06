/*
 * Author:Songhui Yue
 * 2014-10-04
 */
public class PrettyPrinter extends Types{
	
	public static void main(String args[]){
		Parser1122 p = new Parser1122("/Users/songhuiyue/Downloads/Grammer/program2.txt");
		Lexeme root= p.getTree();
		prettyPrint(root);
	}
	
	public static void prettyPrint(Lexeme tree){
		if(tree==null){
			return;
		}else if(tree.type==GLUE){
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type==BLOCK){
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type=="func_glue"){
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type=="func_def"){
			prettyPrint(tree.right);
		}else if(tree.type==CLASS_DEF){
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type==CLASS){
			System.out.print("class ");
		}else if(tree.type==MAIN_DEF){
			prettyPrint(tree.right);
		}else if(tree.type==MAIN){
			System.out.print(tree.type);
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type=="func_call"){
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type=="array_call"){
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type=="class_call"){
			System.out.print("$");
			prettyPrint(tree.right);
		}else if(tree.type=="return"){
			System.out.print("return ");
			prettyPrint(tree.right);
			System.out.println(";");
		}else if(tree.type==COMMA){
			System.out.print(",");
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type==INTEGER){
			System.out.print(tree.ival);
		}else if(tree.type==DOUBLE){
			System.out.print(tree.real);
		}else if(tree.type==VARIABLE){ // then print left and right?
			System.out.print(tree.word);
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type==STRING){
			System.out.print(tree.word);
		}else if(tree.type==OPAREN){
			System.out.print("(");
			prettyPrint(tree.right);
			System.out.print(")");
			//System.out.println();
		}else if(tree.type==LEFT_SQUARE_BRACKET){
			System.out.print("[");
			prettyPrint(tree.right);
			System.out.print("]");
			//System.out.println();
		}else if(tree.type==OPEN_BRACE){
			System.out.println("{");
			prettyPrint(tree.left); //to do
			prettyPrint(tree.right);
			System.out.print("}");
			System.out.println();
		}else if(tree.type==POINT){
			System.out.print(".");
			prettyPrint(tree.left);
			prettyPrint(tree.right);
		}else if(tree.type==MINUS){
			prettyPrint(tree.left);
			System.out.print("-");
			prettyPrint(tree.right);
		}else if(tree.type==PLUS){
			prettyPrint(tree.left);
			System.out.print("+");
			prettyPrint(tree.right);
		}else if(tree.type==DIVIDES){
			prettyPrint(tree.left);
			System.out.print("/");
			prettyPrint(tree.right);
		}else if(tree.type==ASSIGN){
			prettyPrint(tree.left);
			System.out.print("=");
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==GREATERTHAN){
			prettyPrint(tree.left);
			System.out.print(">");
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==LESSTHAN){
			prettyPrint(tree.left);
			System.out.print("<");
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==EQUALEQUAL){
			prettyPrint(tree.left);
			System.out.print("==");
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==MODE){
			prettyPrint(tree.left);
			System.out.print("%");
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==WHILE){
			System.out.print(WHILE);
			prettyPrint(tree.left);
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==IF){
			System.out.print(IF);
			prettyPrint(tree.left);
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==ELSE){
			System.out.print(ELSE);
			prettyPrint(tree.left);
			prettyPrint(tree.right);
			//System.out.println(";");
		}else if(tree.type==PRINTLN){
			System.out.print(PRINTLN);
			prettyPrint(tree.left);
			prettyPrint(tree.right);
			System.out.println(";");
		}else if(tree.type==SEMICOLON){
			System.out.println(";");
		}
		
		else{
			System.out.println("where is bad: "+tree.type);
			System.out.println("Bad expression!");
		}
    }
	
	public static void printTree(Lexeme tree){
		if(tree!=null){
			if(tree.type==INTEGER){
				System.out.println(tree.type+"   "+tree.ival);
			}else{
				System.out.println(tree.type+"   "+tree.word);
			}
			System.out.print("I am left : ");
			printTree(tree.left);
			System.out.print("I am right : ");
			printTree(tree.right);
		}else{
			System.out.println("end by null ");
		}
	}
}
