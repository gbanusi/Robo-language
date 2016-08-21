package robo.parser.execution.visitor.statement;

import robo.parser.syntax.nodes.ProgramNode;
import robo.parser.syntax.nodes.statements.*;

/**
 * Created by gregor on 15.08.16..
 */
public interface NodeVisitor {

    public void visit(AsgnValStatement node);

    public void visit(BreakStatement node);

    public void visit(DefFunctionStatement node);

    public void visit(DefStatement node);

    public void visit(DoStatement node);

    public void visit(IfBlockStatement node);

    public void visit(IfStatement node);

    public void visit(PrintStatement node);

    public void visit(ReturnStatement node);

    public void visit(WhileStatement node);

    public void visit(ProgramNode node);

}
