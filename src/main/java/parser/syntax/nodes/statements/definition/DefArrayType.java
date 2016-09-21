package parser.syntax.nodes.statements.definition;

import parser.execution.visitor.statement.NodeVisitor;
import parser.lexical.type.Type;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeExpression;

import java.util.List;

/**
 * Created by gregor on 28.08.16..
 */
public class DefArrayType extends Node {

    // TODO-3 implement with cols and rows?
    private List<NodeExpression> matrixDim;

    private Type type;

    public DefArrayType(Type type, List<NodeExpression> matrixDim) {
        this.type = type;
        this.matrixDim = matrixDim;
    }

    public Type getType() {
        return type;
    }

    public List<NodeExpression> getMatrixDim() {
        return matrixDim;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
