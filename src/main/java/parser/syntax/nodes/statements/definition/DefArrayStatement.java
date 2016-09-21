package parser.syntax.nodes.statements.definition;

import parser.execution.visitor.statement.NodeVisitor;
import parser.syntax.nodes.Node;

import java.util.List;


public class DefArrayStatement extends DefVarStatement {

    private Node defArrayType;

    public DefArrayStatement(List<String> nv, boolean isConst, List<Node> assigns, Node defArrayType) {
        super(nv, null, isConst, assigns);
        this.defArrayType = defArrayType;
    }

    public Node getDefArrayType() {
        return defArrayType;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}



