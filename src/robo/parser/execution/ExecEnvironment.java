package robo.parser.execution;

import robo.parser.execution.values.RoboReference;
import robo.parser.execution.values.RoboValue;
import robo.parser.execution.values.RoboVariable;
import robo.parser.execution.visitor.expression.ExpressionEvalVisitor;
import robo.parser.execution.visitor.statement.ProgramStatementVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;
import robo.parser.syntax.nodes.expression.NodeFunction;
import robo.parser.syntax.nodes.statements.DefFunctionStatement;
import robo.parser.syntax.nodes.statements.DefStatement;
import robo.parser.syntax.nodes.statements.LoopStatement;

import java.util.*;

/**
 * Created by gregor on 17.08.16..
 * <p>
 * Execution environment.
 */
public class ExecEnvironment {

    private static LinkedList<ExecEnvironment> envList = new LinkedList<>();

    static {
        createExecEnvInstance();
    }

    private Map<String, RoboVariable> vars;

    private Stack<LoopStatement> loops;

    private static Map<String, DefFunctionStatement> functions = new HashMap<>();

    private Stack<RoboValue> exprsCalculated = new Stack<>();

    private ExecEnvironment() {
        this.vars = new HashMap<>();
        this.loops = new Stack<>();
        this.exprsCalculated = new Stack<>();
    }

    private static void createExecEnvInstance() {
        envList.push(new ExecEnvironment());
    }

    public static ExecEnvironment currentEnv() {
        return envList.peek();
    }

    // LOOP AREA

    public static void pushLoop(LoopStatement ls) {
        envList.peek().loops.push(ls);
    }

    public static void popLoop() {
        envList.peek().loops.pop();
    }

    // EXPRESSION AREA

    public RoboValue popExpr() {
        return exprsCalculated.pop();
    }

    public RoboValue peekExpr() {
        return exprsCalculated.peek();
    }

    public void pushExpr(RoboValue rv) {
        exprsCalculated.push(rv);
    }

    public int sizeExpr() {
        return exprsCalculated.size();
    }


    public static void declareVar(String name, boolean isConstant) {
        if (currentEnv().vars.get(name) != null) {
            throw new ExecutionException("Variable '" + name + "' already defined!");
        }
        currentEnv().vars.put(name, new RoboVariable(name, isConstant));
    }

    // VARIABLE AREA

    public RoboVariable getEnvVariableValue(String name) {
        return vars.get(name);
    }


    public static RoboValue getVarValue(String name) {
        if (currentEnv().getEnvVariableValue(name) != null) {
            return currentEnv().getEnvVariableValue(name).getValue();
        }
        throw new ExecutionException("Variable, '" + name + "' not defined!");
    }

    /**
     * Method assigns given value val to variable which name is given.
     * Method checks whether variable is reference or not and handles i that way.
     * @param name
     * @param val
     */
    public static void asgnVarValue(String name, RoboValue val) {
        if (currentEnv().getEnvVariableValue(name) != null) {
            if (getVarValue(name) instanceof RoboReference) {
                ((RoboReference) getVarValue(name)).setValue(val);
            } else {
                currentEnv().getEnvVariableValue(name).setValue(val);
            }
            return;
        }

        throw new ExecutionException("Variable, '" + name + "' not defined!");
    }

    // FUNCTION AREA

    public static void declareFunc(String name, DefFunctionStatement val) {
        functions.put(name, val);
    }

    public static void executeFunc(NodeFunction nf, ProgramStatementVisitor funcExec, ExpressionEvalVisitor expEval) {
        createExecEnvInstance();
        DefFunctionStatement dfs = functions.get(nf.getfName());

        for (int i = 0; i < nf.getVars().size(); i++) {
            String varName = defineVariable(funcExec, dfs, i);
            calcExpressionFromLastEnvironment(nf, expEval, i);
            asignValueToVar(expEval, varName);
        }

        for (Node n : dfs.getStatements()) {
            n.accept(funcExec);
        }

        // TODO smisliti bolji algoritam
        Map<String, RoboVariable> varMap = currentEnv().vars;
        RoboValue returnValue = currentEnv().popExpr();
        envList.pop();
        for (String key : varMap.keySet()) {
            if (varMap.get(key).getValue() instanceof RoboReference) {
                RoboReference rr = (RoboReference) varMap.get(key).getValue();
                asgnVarValue(rr.getVarName(), rr.getValue());
            }
        }

        currentEnv().pushExpr(returnValue);
    }

    private static void asignValueToVar(ExpressionEvalVisitor expEval, String varName) {
        RoboValue rv = expEval.getResult();
        asgnVarValue(varName, rv);
    }

    private static void calcExpressionFromLastEnvironment(NodeFunction nf, ExpressionEvalVisitor expEval, int i) {
        NodeExpression ne = nf.getVars().get(i);
        ExecEnvironment temp = envList.pop();
        ne.accept(expEval);
        envList.push(temp);
    }

    private static String defineVariable(ProgramStatementVisitor funcExec, DefFunctionStatement dfs, int i) {
        Node defs = dfs.getParameters().get(i);
        defs.accept(funcExec);
        return ((DefStatement) defs).getVariables().get(0);
    }

}
