package parser.execution.visitor.statement;

import parser.syntax.nodes.ProgramNode;
import parser.syntax.nodes.statements.assignation.AssignArrayIndexStatement;
import parser.syntax.nodes.statements.assignation.AssignVarStatement;
import parser.syntax.nodes.statements.definition.DefArrayStatement;
import parser.syntax.nodes.statements.definition.DefArrayType;
import parser.syntax.nodes.statements.definition.DefFunctionStatement;
import parser.syntax.nodes.statements.definition.DefVarStatement;
import parser.syntax.nodes.statements.function.ClassFunctionCallStatement;
import parser.syntax.nodes.statements.function.FunctionCallStatement;
import parser.syntax.nodes.statements.function.PrintStatement;
import parser.syntax.nodes.statements.keyword.IfBlockStatement;
import parser.syntax.nodes.statements.keyword.IfStatement;
import parser.syntax.nodes.statements.keyword.IncludeStatement;
import parser.syntax.nodes.statements.keyword.ReturnStatement;
import parser.syntax.nodes.statements.loop.DoStatement;
import parser.syntax.nodes.statements.loop.LoopStatement;
import parser.syntax.nodes.statements.loop.WhileStatement;
import parser.syntax.nodes.statements.loop.extra.BreakStatement;
import parser.syntax.nodes.statements.loop.extra.ContinueStatement;

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

    void visit(FunctionCallStatement functionCallStatement);

    void visit(IncludeStatement includeStatement);

    void visit(LoopStatement loopStatement);

    void visit(ClassFunctionCallStatement classFunctionCallStatement);
}
