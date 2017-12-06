package cLite;


/**
 

* The Clite Programming Language
 * 
 * The Input class handles the input source for the Lexer.
 * 
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;

public class Input {

	// change the program source
	private static final String sourcefile = "sample-programs/whilestatement.clt";
	private PushbackReader reader;

	Input() {
		try {
			reader = new PushbackReader(new FileReader(sourcefile));
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file " + sourcefile);
			e.printStackTrace();
		}
	}

	public Input(InputStream in) {
		reader = new PushbackReader(new InputStreamReader(in));
	}

	/**
	 * Gets the next char from the character stream (cast int value to char to
	 * get the char value).
	 */
	public int getCharacter() {
		try {
			return reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Skips whitespace in the current stream.
	 */
	public void skipWhitespace() {
		int x;
		try {
			x = reader.read();
						
			while (isWhitespace(x)) {
				x = reader.read();
			}
			pushback((char) x);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Determines if a char is considered "whitespace."
	 */
	private boolean isWhitespace(int ch) {
		if (ch == 32 || ch == 13 || ch == 10 || ch == 9)
			return true;
		return false;
	}

	/**
	 * Push back a character onto the character stream.
	 */
	public void pushback(char x) {
		try {
			reader.unread(x);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Skip all read-in characters until another comment char
	 * is encountered.
	 */
	public void skipComment() {
		try {
			int x = reader.read();
			while(x != '#') {
				x = reader.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}