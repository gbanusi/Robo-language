package robo.parser.syntax.nodes.statements;

import robo.parser.execution.visitor.node.NodeVisitor;
import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregor on 13.08.16..
 */
public class DefFunctionStatement extends Node {


    private List<Node> statements = new ArrayList<>();

    private List<Node> parameters = new ArrayList<>();

    private Type returnType;

    private String fName;

    public DefFunctionStatement(List<Node> statements, List<Node> parameters, Type returnType, String fName) {
        this.statements = statements;
        this.parameters = parameters;
        this.returnType = returnType;
        this.fName = fName;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
