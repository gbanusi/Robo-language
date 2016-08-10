package robo.parser.syntax.nodes.value.expression;

import robo.parser.lexical.TokenType;
import robo.parser.syntax.nodes.value.NodeExpression;

/**
 * Created by gregor on 10.08.16..
 */
public class NodeExpressionRelation extends NodeExpressionBinaryOper {

    private TokenType relation;

    /**
     * Konstruktor objekta koji predstavlja <tt>first + second</tt>.
     *
     * @param first  prvi izraz
     * @param second drugi izraz
     */
    public NodeExpressionRelation(NodeExpression first, NodeExpression second, TokenType relation) {
        super(first, second);
        this.relation = relation;
    }

    public TokenType getRelation() {
        return relation;
    }

    //	@Override
//	public void accept(ExpressionNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}
