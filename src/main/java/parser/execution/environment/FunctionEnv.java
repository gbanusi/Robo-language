package parser.execution.environment;

import parser.execution.values.RoboValue;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.execution.visitor.statement.ProgramStatementVisitor;
import parser.lexical.Type;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeFunction;
import parser.syntax.nodes.statements.DefFunctionStatement;
import parser.syntax.nodes.statements.DefVarStatement;

import java.util.*;

/**
 * Created by gregor on 28.08.16..
 */
public class FunctionEnv {

    private static Stack<Type> funcReturnType;

    static{
        funcReturnType = new Stack<>();
    }

    private Map<String, DefFunctionStatement> declaredFunctions = new HashMap<>();

    public FunctionEnv(Map<String, DefFunctionStatement> declaredFunctions) {
        this.declaredFunctions = declaredFunctions;
    }

    public Map<String, DefFunctionStatement> getDeclaredFunctions() {
        return declaredFunctions;
    }

    public Type getFunctionReturnType(String funcName){
        return declaredFunctions.get(funcName).getReturnType();
    }

    public static Type getCurrentReturnType(){
        return funcReturnType.peek();
    }

    public void declareFunc(String name, DefFunctionStatement val) {
        declaredFunctions.put(name, val);
    }

    public void executeFunc(NodeFunction nf, ProgramStatementVisitor funcExec, ExpressionEvalVisitor expEval) {

        funcReturnType.push(declaredFunctions.get(nf.getfName()).getReturnType());

        // calculate passing expressions and stack them
        for (int i = 0; i < nf.getVars().size(); i++) {
            nf.getVars().get(i).accept(expEval);
        }

        ExecutionEnv.createNewEnvironment();

        DefFunctionStatement dfs = declaredFunctions.get(nf.getfName());
        List<Node> params = dfs.getParameters();
        // last expression is on top of stack!
        Collections.reverse(params);

        for (int i = 0; i < nf.getVars().size(); i++) {
            params.get(i).accept(funcExec);
            String varName = ((DefVarStatement) params.get(i)).getVariables().get(0);
            RoboValue rv = expEval.getResult();
            ExecutionEnv.getExecutionEnvironment().defineVariable(varName, rv, rv.getType());
        }

        for (Node n : dfs.getStatements()) {
            n.accept(funcExec);
            if (ExecutionEnv.isFunctionStopped()){
                break;
            }
        }

        funcReturnType.pop();
        ExecutionEnv.removeEnvironment();

    }

}
