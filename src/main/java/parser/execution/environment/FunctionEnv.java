package parser.execution.environment;

import parser.execution.values.RoboValue;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.execution.visitor.statement.ProgramStatementVisitor;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.NodeFunction;
import parser.syntax.nodes.statements.DefFunctionStatement;
import parser.syntax.nodes.statements.DefVarStatement;
import parser.syntax.nodes.statements.ReturnStatement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gregor on 28.08.16..
 */
public class FunctionEnv {

    private Map<String, DefFunctionStatement> declaredFunctions = new HashMap<>();

    public FunctionEnv(VariableEnv initializationVarEnv, Map<String, DefFunctionStatement> declaredFunctions) {
        this.declaredFunctions = declaredFunctions;
    }

    public Map<String, DefFunctionStatement> getDeclaredFunctions() {
        return declaredFunctions;
    }

    public void declareFunc(String name, DefFunctionStatement val) {
        declaredFunctions.put(name, val);
    }

    public void executeFunc(NodeFunction nf, ProgramStatementVisitor funcExec, ExpressionEvalVisitor expEval) {

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
            ExecutionEnv.getExecEnv().defineVariable(varName, rv, rv.getType());
        }

        for (Node n : dfs.getStatements()) {
            n.accept(funcExec);
            if (n instanceof ReturnStatement){
                break;
            }
        }

        ExecutionEnv.removeEnvironment();

    }

}
