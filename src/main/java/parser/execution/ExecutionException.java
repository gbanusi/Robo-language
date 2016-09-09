package parser.execution;

import parser.RoboException;

/**
 * Iznimka koja opisuje pogreške nastale pri sintaksnoj analizi
 * programa.
 * 
 * @author marcupic
 */
public class ExecutionException extends RoboException {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public ExecutionException() {
	}

	/**
	 * Konstruktor.
	 * @param message opis pogreške
	 */
	public ExecutionException(String message) {
		super(message);
	}

}
