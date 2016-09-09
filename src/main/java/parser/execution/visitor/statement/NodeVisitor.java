package parser.execution.visitor.statement;

import parser.syntax.nodes.ProgramNode;
import parser.syntax.nodes.statements.*;

/**
 * Created by gregor on 15.08.16..
 */
public interface NodeVisitor {

    void visit(AssignVarStatement node);

    void visit(BreakStatement node);

    void visit(DefFunctionStatement node);

    void visit(DefVarStatement node);

    void visit(DefArrayStatement node);

    void visit(DoStatement node);

    void visit(IfBlockStatement node);

    void visit(IfStatement node);

    void visit(PrintStatement node);

    void visit(ReturnStatement node);

    void visit(WhileStatement node);

    void visit(ProgramNode node);

    void visit(ContinueStatement continueStatement);

    void visit(AssignArrayIndexStatement assignArrayStatement);

    void visit(DefArrayType defArrayType);
}
