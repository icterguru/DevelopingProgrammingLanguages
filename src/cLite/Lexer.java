package cLite;


/**
 * The Clite Programming Language
 * 
 * A Lexer reads a stream of characters, identifies the lexemes in the
 * stream, and then categorizes them into types.
 * 
 * 
 */


import java.io.InputStream;

public class Lexer {

	Input input;

	public Lexer() {
		input = new Input();
	}

	public Lexer(InputStream in) {
		input = new Input(in);
	}

	/**
	 * Scan the input source and produce a list of lexemes.
	 */
	public void scan() {
		System.out.println("-- LEXER OUTPUT -- ");
		Lexeme currentLexeme = lex();
		while (currentLexeme.getType() != "END") {
			currentLexeme.display();
			currentLexeme = lex();
		}
		System.out.println();
	}

	/**
	 * Grabs one character at a time from the input. When enough characters have
	 * been read, returns a lexeme.
	 */
	public Lexeme lex() {
		int readchar;
		input.skipWhitespace();
		readchar = input.getCharacter();
		if (readchar == -1 || readchar == 65535)
			return new Lexeme("END");

		switch ((char) readchar) {
		case '(':
			return new Lexeme("OPAREN", readchar);
		case ')':
			return new Lexeme("CPAREN", readchar);
		case '[':
			return new Lexeme("OBRACKET", readchar);
		case ']':
			return new Lexeme("CBRACKET", readchar);
		case ',':
			return new Lexeme("COMMA", readchar);
		case '+':
			return new Lexeme("PLUS", readchar);
		case '-':
			return new Lexeme("MINUS", readchar);
		case '/':
			return new Lexeme("DIVIDES", readchar);
		case '*':
			return new Lexeme("TIMES", readchar);
		case '=':
			return new Lexeme("ASSIGN", readchar);
		case '%':
			return new Lexeme("MODULO", readchar);
		case ':':
			return new Lexeme("COLON", readchar);
		case ';':
			return new Lexeme("SEMICOLON", readchar);
		case '>':
			return new Lexeme("GREATERTHAN", readchar);
		case '<':
			return new Lexeme("LESSTHAN", readchar);
		case '&':
			return new Lexeme("AND", readchar);
		case '|':
			return new Lexeme("OR", readchar);
		case '#': {
			input.skipComment();
			return lex();
		}
		

		
		default:
			// multi-character tokens
			// (numbers, variables/keywords, strings)
			if (isAlpha(readchar)) {
				input.pushback((char) readchar);
				return getVariable();
			} else if (isDigit(readchar)) {
				input.pushback((char) readchar);
				return getNumber();
			} else if (readchar == '\"') {
				return getString();
			}
		}

		return new Lexeme("BAD-CHARACTER", readchar);
	}

	
	/**
	 * VARIABLE: a letter followed by any number of letters or digits This
	 * method is also responsible for returning the lexemes of KEYWORDS.
	 */
	private Lexeme getVariable() {
		String token = "";
		int c = input.getCharacter();

		while (isAlpha(c) || isDigit(c)) {
			token = token + (char) c;
			c = input.getCharacter();
		}

		input.pushback((char) c);

		if (Keywords.isKeyword(token)) {
			return new Lexeme(token.toUpperCase());
		}

		return new Lexeme("VARIABLE", token);
	}

	/**
	 * NUMBER: a digit followed by any number of digits
	 */
	private Lexeme getNumber() {
		String token = "";
		int c = input.getCharacter();

		while (isDigit(c)) {
			token = token + (char) c;
			c = input.getCharacter();
		}

		input.pushback((char) c);

		return new Lexeme("NUMBER", Integer.parseInt(token));
	}

	/**
	 * STRING: a set of characters enclosed in quotes
	 */
	private Lexeme getString() {
		int c = input.getCharacter();
		String token = "";

		while (c != '\"') {
			token = token + (char) c;
			c = input.getCharacter();
		}

		return new Lexeme("STRING", token);
	}

	/**
	 * Returns true if the character is an alphabet letter.
	 */
	private boolean isAlpha(int ch) {
		if (ch <= 122 && ch >= 65)
			return true;
		else
			return false;
	}

	/**
	 * Returns true if the character is a digit.
	 */
	private boolean isDigit(int ch) {
		if (ch <= 57 && ch >= 48)
			return true;
		else
			return false;
	}
}
