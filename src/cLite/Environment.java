package cLite;

/**

 * The Clite Programming Language
 * 
 * An Environment holds the bindings between variables and their current
 * values and implements scope. 
 * 
 * 
 */

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

public class Environment {

	// Represent environment as a Linked List of Hashtables
	private LinkedList<Hashtable<Lexeme, Lexeme>> environment;

	/**
	 * Create a new empty Environment. We may represent a new Environment as a
	 * linked list containing one Hashtable.
	 */
	Environment() {
		Hashtable<Lexeme, Lexeme> table = new Hashtable<Lexeme, Lexeme>();
		environment = new LinkedList<Hashtable<Lexeme, Lexeme>>();
		environment.add(table);
	}

	@SuppressWarnings("unchecked")
	public Environment(Environment env) {
		environment = (LinkedList<Hashtable<Lexeme, Lexeme>>) env.environment.clone();
	}

	/**
	 * Insert a (variable, value) pair into the first Hashtable in the List.
	 */
	public void insert(Lexeme variable, Lexeme value) {
		environment.get(0).put(variable, value);
	}

	/**
	 * To find the value of a variable, we look up the variable in the first
	 * Hashtable in the list. If it is not there, we proceed to the next, and so
	 * on.
	 */
	public Lexeme lookup(Lexeme var) {
		for (int i = 0; i < environment.size(); i++) {
			if (environment.get(i).get(var) != null) {
				return environment.get(i).get(var);
			}
		}
		return null;
	}

	/**
	 * To update the value of a variable, we look up the variable the first
	 * Hashtable in the list. If we find it, we update its value. Otherwise, we
	 * proceed to the next Hashtable until the variable is found. If we do not find
	 * it, we insert it in the local Environment.
	 */
	public void update(Lexeme var, Lexeme value) {
		boolean found = false;
		for (int i = 0; i < environment.size(); i++) {
			if (environment.get(i).get(var) != null) {
				environment.get(i).put(var, value);
				found = true;
			}
		}
		if (!found) {
			insert(var, value);
		}
	}

	/**
	 * Extension is performed for a function call. A new environment is created,
	 * populated with the local parameters and values, and pointed to the
	 * defining environment.
	 */
	public Environment extend(LinkedList<Lexeme> vars, LinkedList<Lexeme> vals) {
		// Create a new table
		Hashtable<Lexeme, Lexeme> table = new Hashtable<Lexeme, Lexeme>();

		// Populate the table with the passed variables and values
		for (int i = 0; i < vars.size(); i++) {
			table.put(vars.get(i), vals.get(i));
		}

		Environment extended = new Environment(this);
		
		// Place the table at the front of extended environment
		extended.environment.add(0, table);

		return extended;
	}
	
	public Environment extend(Lexeme params, Lexeme eargs) {	
		// Create a new table
		Hashtable<Lexeme, Lexeme> table = new Hashtable<Lexeme, Lexeme>();
		
		// Populate the table with the passed variables and values
		while (eargs.left() != null) { 
			table.put(params.left(), eargs.left());
			params = params.right();
			eargs = eargs.right();
		}
		
		Environment extended = new Environment(this);
		
		// Place the table at the front of extended environment
		extended.environment.add(0, table);
		
		return extended;
	}

	/**
	 * Display a table at an index in the environment.
	 */
	private void displayTable(int index) {
		Enumeration<Lexeme> variables;
		variables = environment.get(index).keys();
		while (variables.hasMoreElements()) {
			Lexeme var = (Lexeme) variables.nextElement();
			Lexeme val = environment.get(index).get(var);
			var.print();
			System.out.print(" : ");
			val.print();
			System.out.println();
		}
	}
	
	/**
	 * Display the local table in the environment.
	 */
	public void displayLocalEnvironment() {
		displayTable(0);
	}

	/**
	 * Display the enclosing environment by individually display each table
	 * excluding the first.
	 */
	public void displayEnclosingEnvironment() {
		if (environment.size() > 1) {
			for (int i = 1; i < environment.size(); i++) {
				displayTable(i);
				System.out.println();
			}
		}
	}
}