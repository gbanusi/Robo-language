package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.node.NodeVisitor;
import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.Node;

import java.util.ArrayList;
import java.util.List;


public class DefStatement extends Node {

    private List<String> variables;

    private Type type;



    public DefStatement(List<String> nv, Type type) {
        this.variables = new ArrayList<>(nv);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public List<String> getVariables() {
        return new ArrayList<>(variables);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}