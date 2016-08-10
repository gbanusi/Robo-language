package robo.parser.syntax.nodes.statements;

import robo.parser.lexical.Token;
import robo.parser.lexical.Type;
import robo.parser.syntax.nodes.Node;

import java.util.ArrayList;
import java.util.List;


public class DefStatement extends Node {


    private List<Token> variables;

    private Type type;


    public DefStatement(List<String> nv, Type type) {
        this.variables = new ArrayList<>(variables);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public List<Token> getVariables() {
        return new ArrayList<>(variables);
    }

//	@Override
//	public void accept(VLangNodeVisitor visitor) {
//		visitor.visit(this);
//	}
}