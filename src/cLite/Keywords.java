package cLite;


/**
 * The Clite Programming Language
 * 
 * Keywords is a static class that keeps track of the keywords in
 * Clite. 
 * 
 * 
 **/

public class Keywords {
	
	// Modify this array to add keywords
	private final static String[] keywordsList = {"function",
			"if", "else", "while", "true", "false", "eq", "print", "println"};

	/**
	 * Determine if a given token is a keyword in the language.
	 */
	public static boolean isKeyword(String token) {
		for (int i = 0; i < keywordsList.length; i++) {
			if (keywordsList[i].equals(token))
				return true;
		}
		return false;
	}

}
