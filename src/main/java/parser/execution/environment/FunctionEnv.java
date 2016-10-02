package parser.execution.environment;

import parser.execution.ExecutionException;
import parser.execution.environment.built.in.LanguageFunctions;
import parser.execution.environment.built.in.RoboConstructors;
import parser.execution.values.RoboValue;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.execution.visitor.statement.ProgramStatementVisitor;
import parser.lexical.type.Type;
import parser.lexical.type.TypeArray;
import parser.syntax.nodes.Node;
import parser.syntax.nodes.expression.value.right.NodeFunction;
import parser.syntax.nodes.statements.definition.DefFunctionStatement;
import parser.syntax.nodes.statements.definition.DefVarStatement;

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

    private static Set<String> builtInFunctions;

    static{
        builtInFunctions  = new HashSet<>();
        builtInFunctions.add("matrixLength");
        builtInFunctions.add("arrayLength");
        builtInFunctions.add("Vector3d");
    }

    public FunctionEnv(Map<String, DefFunctionStatement> declaredFunctions) {
        this.declaredFunctions = declaredFunctions;
    }

    public Map<String, DefFunctionStatement> getDeclaredFunctions() {
        return declaredFunctions;
    }

    public Type getFunctionReturnType(String funcName){
        if(builtInFunctions.contains(funcName)){
            switch (funcName){
                case "arrayLength":
                    return Type.Int;
                case "matrixLength":
                    return new TypeArray(Type.Int, 2);
                case "Vector3d":
                    return Type.Vector3d;
            }
            throw new ExecutionException("No built in method found, internal error!");
        }
        return declaredFunctions.get(funcName).getReturnType();
    }

    public static Type getCurrentReturnType(){
        return funcReturnType.peek();
    }

    public void declareFunc(String name, DefFunctionStatement val) {
        declaredFunctions.put(name, val);
    }

    public static boolean isFunctionExecuting(){
        return funcReturnType.size() > 0;
    }

    public void executeFunc(NodeFunction nf, ProgramStatementVisitor funcExec, ExpressionEvalVisitor expEval) {

        evaluateParams(nf, expEval);

        if(builtInFunctions.contains(nf.getfName())){
            executeBuiltInFunction(nf.getfName(), expEval, nf.getVars().size());
            return;
        }

        // for type checking
        funcReturnType.push(declaredFunctions.get(nf.getfName()).getReturnType());

        ExecutionEnv.createNewEnvironment();

        DefFunctionStatement dfs = declaredFunctions.get(nf.getfName());

        List<Node> params = dfs.getParameters();
        if(nf.getVars().size() != params.size()){
            throw new ExecutionException("Number of passed parameters must equal number of function parameters!");
        }


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

    public void evaluateParams(NodeFunction nf, ExpressionEvalVisitor expEval) {
        // calculate passing expressions and stack them
        for (int i = 0; i < nf.getVars().size(); i++) {
            nf.getVars().get(i).accept(expEval);
        }
    }

    private void executeBuiltInFunction(String s, ExpressionEvalVisitor expEval, int size) {
        //TODO-1 error prone -> stack gives variables in reversed order! watch out by defining functions
        switch (s){
            case "matrixLength":
                if(size != 1){
                    throw new ExecutionException("'matrixLength' function accepts only one parameter!");
                }
                LanguageFunctions.matrixLength(expEval.getResult());
                return;
            case "arrayLength":
                if(size != 1){
                    throw new ExecutionException("'matrixLength' function accepts only one parameter!");
                }
                LanguageFunctions.arrayLength(expEval.getResult());
                return;
            case "Vector3d":
                if(size != 3 && size != 1){
                    throw new ExecutionException("'matrixLength' function accepts only one parameter!");
                }
                RoboConstructors.roboVector3D(expEval.getResult(), expEval.getResult(), expEval.getResult());
                return;
        }
        throw new ExecutionException("No built in method found, internal error!");
    }

}
