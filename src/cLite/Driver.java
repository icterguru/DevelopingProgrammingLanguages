package cLite;


/**
 * The starting point of the Clite interpreter. From here,
 * we may run the Lexer, Parser, or Evaluator. We may also
 * examine the global environment after our source code has
 * been evaluated.
 * 
 * 
 */

public class Driver {
	public static void main(String[] args) {	
		// Produce a stream of lexemes from source
		Lexer lexer = new Lexer();
		lexer.scan();
		
		// Build a parse tree from source
		Parser parser = new Parser();
		Lexeme root = parser.parse();
		
		// Evaluate the program parse tree
		Evaluator evaluator = new Evaluator();
		Environment global = new Environment();
		evaluator.launchEval(root, global);
		
		System.out.println();
		System.out.println("\n-- GLOBAL ENVIRONMENT AFTER EVAL -- ");
		global.displayLocalEnvironment();
	}
}
