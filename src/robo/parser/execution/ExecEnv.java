package robo.parser.execution;

import robo.parser.execution.values.RoboNull;
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

    private static LinkedList<ExecEnv> currEnv = new LinkedList<>();

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

    private static void createExecEnvInstance(){
        currEnv.push(new ExecEnv());
    }

    public static ExecEnv currentEnv(){
        return currEnv.peek();
    }



    public static void pushLoop(LoopStatement ls) {
        currEnv.peek().loops.push(ls);
    }

    public static void popLoop(){
        currEnv.peek().loops.pop();
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





    public static void defineVar(String name) {
        if(currEnv.peek().vars.get(name) != null){
            throw new ExecutionException("Variable '" + name + "' already defined!");
        }
        currEnv.peek().vars.put(name, new RoboNull());
    }

    public RoboValue getEnvVariableValue(String name){
        return vars.get(name);
    }

    public static RoboValue getVarValue(String name) {
        for (ExecEnv e : currEnv) {
            if (e.getEnvVariableValue(name) != null) {
                return e.getEnvVariableValue(name);
            }
        }
        throw new ExecutionException("Variable, '" + name + "' not defined!");
    }

    public static void asgnVarValue(String name, RoboValue val) {
        for (ExecEnv e : currEnv) {
            if (e.getEnvVariableValue(name) != null) {
                e.vars.put(name, val);
                return;
            }
        }
        throw new ExecutionException("Variable, '" + name + "' not defined!");
    }



    public static void defineFunc(String name, DefFunctionStatement val) {
        functions.put(name, val);
    }

    public static void executeFunc(NodeFunction nf, ProgramExecutorVisitor funcExec) {
        createExecEnvInstance();

        DefFunctionStatement dfs = functions.get(nf.getfName());
        for (int i = 0; i < nf.getVars().size(); i++) {
            Node defs = dfs.getParameters().get(i);
            String varName = ((DefStatement) defs).getVariables().get(0);
            NodeExpression ne = nf.getVars().get(0);
            funcExec.visit(new AsgnValStatement(varName, ne));
        }
        for (Node n : dfs.getStatements()) {
            n.accept(funcExec);
        }

        currEnv.pop();
    }

}
