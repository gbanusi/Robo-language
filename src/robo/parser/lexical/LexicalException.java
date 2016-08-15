package robo.parser.lexical;

import robo.parser.RoboException;

/**
 * Iznimka koja opisuje pogreške nastale pri sintaksnoj analizi
 * programa.
 * 
 * @author marcupic
 */
public class LexicalException extends RoboException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public LexicalException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public LexicalException(String message) {
		super(message);
	}

}
