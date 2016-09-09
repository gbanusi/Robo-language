package parser.lexical;


public class Token {

	/**
	 * Vrsta tokena.
	 */
	private TokenType tokenType;
	/**
	 * Vrijednost tokena.
	 */
	private Object value;
	
	/**
	 * Konstruktor.
	 * @param tokenType vrsta tokena
	 * @param value vrijednost tokena
	 */
	public Token(TokenType tokenType, Object value) {
		super();
		if(tokenType==null) throw new IllegalArgumentException("Token value can not be null.");
		this.tokenType = tokenType;
		this.value = value;
	}
	
	/**
	 * Dohvat vrste tokena.
	 * @return vrsta tokena
	 */
	public TokenType getTokenType() {
		return tokenType;
	}
	
	/**
	 * Dohvat vrijednosti tokena.
	 * @return vrijednost tokena ili <code>null</code> ako peek ove vrste nema pridruženu vrijednost
	 */
	public Object getValue() {
		return value;
	}
}
