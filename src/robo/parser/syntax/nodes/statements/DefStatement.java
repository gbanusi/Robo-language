package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.statement.NodeVisitor;
import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.Node;

import java.util.ArrayList;
import java.util.List;


public class DefStatement extends Node {

    private List<String> variables;

    private Type type;

    private boolean isConst;

    public DefStatement(List<String> nv, Type type, boolean isConst) {
        this.variables = new ArrayList<>(nv);
        this.type = type;
        this.isConst = isConst;
    }

    public Type getType() {
        return type;
    }

    public List<String> getVariables() {
        return new ArrayList<>(variables);
    }

    public boolean isConst() {
        return isConst;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}