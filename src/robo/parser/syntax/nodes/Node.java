package robo.parser.syntax.nodes;

/**
 * Općeniti čvor stabla programa napisanog jezikom <i>vlang</i>
 * pri čemu konkretne implementacije ovog čvora predstavljaju
 * pojedine naredbe (definicije, dodjeljivanja, ispis).
 * 
 * @author marcupic
 */
public abstract class Node {

	/**
	 * Konstruktor.
	 */
	public Node() {
	}

	/**
	 * Prihvat posjetitelja.
	 * @param visitor posjetitelj
	 */
//	public abstract void accept(VLangNodeVisitor visitor);
}
