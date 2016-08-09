package robo.parser.syntax;

import robo.parser.RoboException;

/**
 * Iznimka koja opisuje pogreške nastale pri sintaksnoj analizi
 * programa.
 * 
 * @author marcupic
 */
public class SyntaxException extends RoboException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public SyntaxException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public SyntaxException(String message) {
		super(message);
	}

}
