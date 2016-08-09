package robo.parser.lexical;

import robo.parser.RoboException;

/**
 * Iznimka koja opisuje pogreške pri tokenizaciji programa.
 * 
 * @author marcupic
 */
public class TokenizerException extends RoboException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public TokenizerException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public TokenizerException(String message) {
		super(message);
	}

}
