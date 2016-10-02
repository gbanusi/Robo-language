package parser.execution.visitor.statement;

import parser.execution.ExecutionException;
import parser.execution.RoboExec;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.RoboBool;
import parser.execution.values.RoboInteger;
import parser.execution.values.RoboString;
import parser.execution.values.RoboValue;
import parser.execution.visitor.TypeCheckingHelper;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.lexical.tokenizer.Tokenizer;
import parser.lexical.type.ArraysType;
import parser.lexical.type.Type;
import parser.syntax.SyntaxException;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.ProgramNode;
import parser.syntax.nodes.expression.NodeExpression;
import parser.syntax.nodes.expression.value.right.NodeFunction;
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
import parser.syntax.parser.Parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregor on 15.08.16..
 */
public class ProgramStatementVisitor implements NodeVisitor {

    @Override
    public void visit(AssignVarStatement node) {
        RoboValue val = calculateExpression(node.getExpression(), ExecutionEnv.getExecutionEnvironment().getVariableType(node.getVar()));
        ExecutionEnv.getExecutionEnvironment().defineVariable(node.getVar(), val, val.getType());
    }

    @Override
    public void visit(BreakStatement node) {
        ExecutionEnv.setBreakLoop(true);
    }

    @Override
    public void visit(ContinueStatement continueStatement) {
        ExecutionEnv.setContinueLoop(true);
    }

    // TODO-1 directly getting Array element by reference and changing the value, maybe sth better through ExecEnv?
    @Override
    public void visit(AssignArrayIndexStatement assignArrayStatement) {
        List<RoboValue> rvList = new ArrayList<>();
        for (NodeExpression ne : assignArrayStatement.getIndexes()) {
            RoboValue val = calculateExpression(ne, Type.Int);
            rvList.add(val);
        }

        RoboValue var = ExecutionEnv.getExecutionEnvironment().getVariableValueByReference(assignArrayStatement.getVar()).index(rvList);

        Type type = ((ArraysType)ExecutionEnv.getExecutionEnvironment().getVariableType(assignArrayStatement.getVar())).getType();
        RoboValue expression = calculateExpression(assignArrayStatement.getExpression(), type);
        var.setRoboValue(expression);
    }

    @Override
    public void visit(DefArrayType defArrayType) {
        // TODO-2 beautify
        RoboValue rows, cols;
        if (defArrayType.getMatrixDim().size() > 1) {
            rows = calculateExpression(defArrayType.getMatrixDim().get(0), Type.Int);
            cols = calculateExpression(defArrayType.getMatrixDim().get(1), Type.Int);
        } else if (defArrayType.getMatrixDim().size() == 1) {
            rows = new RoboInteger(1);
            cols = calculateExpression(defArrayType.getMatrixDim().get(0), Type.Int);
        } else {
            ExecutionEnv.createUnknownArrayType(defArrayType.getType());
            return;
        }

        if (!(rows instanceof RoboInteger) || !(cols instanceof RoboInteger)) {
            throw new SyntaxException("Column and row dimension must be integer number. E.g. [1, 2].");
        }

        // pushing the array type into the execution environment so anyone has access to it...
        if ((Integer) rows.getValue() == 1) {
            ExecutionEnv.createArrayType(defArrayType.getType(), (Integer) cols.getValue());
        } else if ((Integer) rows.getValue() > 1) {
            ExecutionEnv.createMatrixType((Integer) rows.getValue(), (Integer) cols.getValue(), defArrayType.getType());
        }
    }

    @Override
    public void visit(FunctionCallStatement functionCallStatement) {
        calculateExpression(functionCallStatement.getExpression(),
                ExecutionEnv.getExecutionEnvironment().getFunctionReturnType(functionCallStatement.getFuncName()));
    }



    // TODO-1 check if correct extension
    @Override
    public void visit(IncludeStatement includeStatement) {
        //TODO-0 add include statements
        if(ExecutionEnv.isFunctionExecuted()){
            throw new ExecutionException("Cannot include file inside function...");
        }
        String file;
        String program;
        RoboValue progName = calculateExpression(includeStatement.getValue(), Type.String);
        if (!(progName instanceof RoboString)) {
            throw new ExecutionException("Include must have path to file in string format.");
        }
        file = (String) progName.getValue();
        try {
            program = new String(Files.readAllBytes(Paths.get(file)));
        } catch (FileNotFoundException e) {
            throw new ExecutionException("Included file: '" + file + "' not found.");
        } catch (IOException e) {
            throw new ExecutionException("Cannot read included file: '" + file + "'.");
        }

        Parser parser = new Parser(new Tokenizer(program));
        ProgramNode includedFile = parser.getProgramNode();
        RoboExec exec = new RoboExec(includedFile);
        exec.execute();
    }

    @Override
    public void visit(LoopStatement loopStatement) {
        loopStatement.getVarStmt().accept(this);
        RoboValue rb = calculateExpression(loopStatement.getCondition(), Type.Bool);
        boolean expr = ((RoboBool) rb).getValue();
        LOOP:
        while (expr){
            for (Node child : loopStatement.getStatements()) {
                child.accept(this);
                if (ExecutionEnv.isLoopStopped()) {
                    ExecutionEnv.setBreakLoop(false);
                    break LOOP;
                } else if (ExecutionEnv.isLoopContinued()) {
                    ExecutionEnv.setContinueLoop(false);
                    continue LOOP;
                }
            }
            loopStatement.getStep().accept(this);
            rb = calculateExpression(loopStatement.getCondition(), Type.Bool);
            expr = ((RoboBool) rb).getValue();
        }
        ExecutionEnv.removeLoopVariable(loopStatement.getVarName());
    }

    @Override
    public void visit(ClassFunctionCallStatement classFunctionCallStatement) {
        calculateExpression(classFunctionCallStatement.getExpression(), Type.Unknown);
    }


    @Override
    public void visit(DefFunctionStatement node) {
        ExecutionEnv.getExecutionEnvironment().declareFunc(node.getfName(), node);
    }

    @Override
    public void visit(DefVarStatement node) {
        if (!node.isConst()) {
            declareVar(node, false, node.getType());
        } else {
            declareVar(node, true, node.getType());
        }
        node.getAssigns().stream().forEach(asgn -> asgn.accept(this));
    }

    @Override
    public void visit(DefArrayStatement node) {
        node.getDefArrayType().accept(this);
        if (!node.isConst()) {
            declareVar(node, false, ExecutionEnv.getDefArrayType());
        } else {
            declareVar(node, true, ExecutionEnv.getDefArrayType());
        }
        node.getAssigns().stream().forEach(asgn -> asgn.accept(this));
    }


    private void declareVar(DefVarStatement node, boolean isConst, Type type) {
        node.getVariables().stream().forEach(var -> ExecutionEnv.getExecutionEnvironment().declareVariable(var, isConst, type));
    }

    @Override
    public void visit(DoStatement node) {
        RoboValue rb = calculateExpression(node.getExpression(), Type.Bool);
        if (!(rb instanceof RoboBool)) {
            throw new ExecutionException("Only boolean value in 'do-while' condition are allowed.");
        }
        boolean expr = true;
        LOOP:
        do {
            for (Node child : node.getStatements()) {
                child.accept(this);
                if (ExecutionEnv.isLoopStopped()) {
                    ExecutionEnv.setBreakLoop(false);
                    break LOOP;
                } else if (ExecutionEnv.isLoopContinued()) {
                    ExecutionEnv.setContinueLoop(false);
                    continue LOOP;
                }
            }
            rb = calculateExpression(node.getExpression(), Type.Bool);
            expr = ((RoboBool) rb).getValue();
        } while (expr);
    }

    @Override
    public void visit(IfBlockStatement node) {
        for (Node child : node.getIfStatements()) {
            IfStatement is = (IfStatement) child;
            RoboValue rb = calculateExpression(is.getExpression(), Type.Bool);
            boolean cond = ((RoboBool) rb).getValue();
            if (cond) {
                is.accept(this);
                break;
            }
        }
    }

    @Override
    public void visit(IfStatement node) {
        for (Node child : node.getStatements()) {
            child.accept(this);
        }
    }

    // TODO-2 SEND TO BUILT IN FUNCTIONS
    @Override
    public void visit(PrintStatement node) {
        for (NodeExpression ne : node.getExpressions()) {
            RoboValue rb = calculateExpression(ne, Type.Unknown);
            System.out.print(rb + "  ");
        }
        System.out.print("\n");
    }

    @Override
    public void visit(ReturnStatement node) {
        RoboValue rv = calculateExpression(node.getValue(), ExecutionEnv.getCurrentFunctionReturnType());
        ExecutionEnv.pushExpression(rv);
        ExecutionEnv.stopFunction();
    }

    @Override
    public void visit(WhileStatement node) {
        RoboValue rb = calculateExpression(node.getCondition(), Type.Bool);
        if (!(rb instanceof RoboBool)) {
            throw new ExecutionException("Only boolean value in 'do-while' condition are allowed.");
        }
        boolean expr = ((RoboBool) rb).getValue();
        LOOP:
        while (expr) {
            for (Node child : node.getStatements()) {
                child.accept(this);
                if (ExecutionEnv.isLoopStopped()) {
                    ExecutionEnv.setBreakLoop(false);
                    break LOOP;
                } else if (ExecutionEnv.isLoopContinued()) {
                    ExecutionEnv.setContinueLoop(false);
                    continue LOOP;
                }
            }
            rb = calculateExpression(node.getCondition(), Type.Bool);
            expr = ((RoboBool) rb).getValue();
        }
    }

    @Override
    public void visit(ProgramNode node) {
        for (Node child : node.getStatements()) {
            child.accept(this);
        }
    }

    private RoboValue calculateExpression(NodeExpression node, Type type) {
        ExpressionEvalVisitor visitor;
        if(type instanceof ArraysType){
            visitor = new ExpressionEvalVisitor(this, ((ArraysType) type).getType());
        } else {
            visitor = new ExpressionEvalVisitor(this);
        }

        if(node instanceof NodeFunction){
            int k = 0;
            k++;
        }
        node.accept(visitor);
        return TypeCheckingHelper.convertType(visitor.getResult(), type);
    }


}
