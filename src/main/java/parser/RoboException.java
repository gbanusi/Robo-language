package parser;

/**
 * Najopćenitija iznimka vezana uz prevođenje i izvođenje programa
 * pisanog jezikom <i>vlang</i>.
 * 
 * @author marcupic
 */
public class RoboException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public RoboException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public RoboException(String message) {
		super(message);
	}

}
