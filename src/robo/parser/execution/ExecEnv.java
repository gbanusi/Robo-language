package robo.parser.execution;

import robo.parser.execution.values.RoboNull;
import robo.parser.execution.values.RoboReference;
import robo.parser.execution.values.RoboValue;
import robo.parser.execution.visitor.node.ProgramExecutorVisitor;
import robo.parser.syntax.nodes.Node;
import robo.parser.syntax.nodes.expression.NodeExpression;
import robo.parser.syntax.nodes.expression.NodeFunction;
import robo.parser.syntax.nodes.statements.AsgnValStatement;
import robo.parser.syntax.nodes.statements.DefFunctionStatement;
import robo.parser.syntax.nodes.statements.DefStatement;
import robo.parser.syntax.nodes.statements.LoopStatement;

import java.util.*;

/**
 * Created by gregor on 17.08.16..
 * <p>
 * Execution environment.
 */
public class ExecEnv {

    private static LinkedList<ExecEnv> envList = new LinkedList<>();

    static {
        createExecEnvInstance();
    }

    private Map<String, RoboValue> vars;

    private Stack<LoopStatement> loops;

    private static Map<String, DefFunctionStatement> functions = new HashMap<>();

    private Stack<RoboValue> exprsCalculated = new Stack<>();

    private ExecEnv() {
        this.vars = new HashMap<>();
        this.loops = new Stack<>();
        this.exprsCalculated = new Stack<>();
    }

    private static void createExecEnvInstance() {
        envList.push(new ExecEnv());
    }

    public static ExecEnv currentEnv() {
        return envList.peek();
    }


    public static void pushLoop(LoopStatement ls) {
        envList.peek().loops.push(ls);
    }

    public static void popLoop() {
        envList.peek().loops.pop();
    }


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


    public static void declareVar(String name) {
        if (envList.peek().vars.get(name) != null) {
            throw new ExecutionException("Variable '" + name + "' already defined!");
        }
        envList.peek().vars.put(name, new RoboNull());
    }

    public RoboValue getEnvVariableValue(String name) {
        return vars.get(name);
    }

    public static RoboValue getVarValue(String name) {
        for (ExecEnv e : envList) {
            if (e.getEnvVariableValue(name) != null) {
                return e.getEnvVariableValue(name);
            }
        }
        throw new ExecutionException("Variable, '" + name + "' not defined!");
    }

    public static void asgnVarValue(String name, RoboValue val) {
        for (ExecEnv e : envList) {
            if (e.getEnvVariableValue(name) != null) {
                if(getVarValue(name) instanceof RoboReference){
                    ((RoboReference) getVarValue(name)).setValue(val);
                } else {
                    e.vars.put(name, val);
                }
                return;
            }
        }
        throw new ExecutionException("Variable, '" + name + "' not defined!");
    }


    public static void declareFunc(String name, DefFunctionStatement val) {
        functions.put(name, val);
    }

    public static void executeFunc(NodeFunction nf, ProgramExecutorVisitor funcExec) {
        createExecEnvInstance();

        DefFunctionStatement dfs = functions.get(nf.getfName());
        for (int i = 0; i < nf.getVars().size(); i++) {
            Node defs = dfs.getParameters().get(i);
            defs.accept(funcExec);
            String varName = ((DefStatement) defs).getVariables().get(0);
            NodeExpression ne = nf.getVars().get(i);
            funcExec.visit(new AsgnValStatement(varName, ne));
        }
        for (Node n : dfs.getStatements()) {
            n.accept(funcExec);
        }
        // TODO smisliti bolji algoritam
        Map<String, RoboValue> varMap = currentEnv().vars;
        RoboValue returnValue = currentEnv().popExpr();
        envList.pop();
        for (String key : varMap.keySet()) {
            if(varMap.get(key) instanceof RoboReference){
                RoboReference rr = (RoboReference) varMap.get(key);
                asgnVarValue(rr.getVarName(), rr.getValue());
            }
        }

        currentEnv().pushExpr(returnValue);
    }

}
