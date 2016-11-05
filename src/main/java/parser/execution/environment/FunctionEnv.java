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

    private static Stack<RoboValue> funcParamStack = new Stack<>();

    // TODO-1 change to sth else so multiple functions with same name can be declared
    private Map<String, DefFunctionStatement> declaredFunctions = new HashMap<>();

    private static Set<String> builtInFunctions;

    static{
        builtInFunctions  = new HashSet<>();
        builtInFunctions.add("matrixLength");
        builtInFunctions.add("arrayLength");
        builtInFunctions.add("Vector3d");
        builtInFunctions.add("Matrix4");
        builtInFunctions.add("Quaternion");
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
                case "Matrix4":
                    return Type.Matrix4;
                case "Quaternion":
                    return Type.Quaternion;
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
            executeBuiltInFunction(nf.getfName(), nf.getVars().size());
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

        for (int i = 0; i < nf.getVars().size(); i++) {
            params.get(i).accept(funcExec);
            String varName = ((DefVarStatement) params.get(i)).getVariables().get(0);
            RoboValue rv = funcParamStack.pop();
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

        for(int i = 0; i < nf.getVars().size(); i++){
            funcParamStack.push(ExecutionEnv.popExpression());
        }
    }

    public RoboValue popFunctionParam(){
        return funcParamStack.pop();
    }

    private void executeBuiltInFunction(String s, int size) {
        switch (s){
            case "matrixLength":
                if(size != 1){
                    throw new ExecutionException("'matrixLength' function accepts only 1 parameter!");
                }
                LanguageFunctions.matrixLength(funcParamStack.pop());
                return;
            case "arrayLength":
                if(size != 1){
                    throw new ExecutionException("'arrayLength' function accepts only 1 parameter!");

                }
                LanguageFunctions.arrayLength(funcParamStack.pop());
                return;
            case "Vector3d":
                if(size != 3){
                    throw new ExecutionException("'Vector3d' function accepts only 3 parameters!");

                }
                RoboConstructors.roboVector3D(funcParamStack.pop(), funcParamStack.pop(), funcParamStack.pop());
                return;
            case "Matrix4":
                if(size == 2){
                    RoboConstructors.roboMatrix4(funcParamStack.pop(), funcParamStack.pop());
                    return;
                } else if (size == 0){
                    RoboConstructors.roboMatrix4();
                    return;
                }else if (size == 1){
                    RoboConstructors.roboMatrix4(funcParamStack.pop());
                    return;
                }
                throw new ExecutionException("'Matrix4' function wrong parameters number!");
            case "Quaternion":
                if(size == 2){
                    RoboConstructors.roboQuaternion(funcParamStack.pop(), funcParamStack.pop());
                    return;
                }else if(size == 4){
                    RoboConstructors.roboQuaternion(funcParamStack.pop(), funcParamStack.pop(), funcParamStack.pop(), funcParamStack.pop());
                    return;
                }
                throw new ExecutionException("'Quaternion' function accepts only 4 or 2 parameters!");

        }
        throw new ExecutionException("No built in method found, internal error!");
    }

}
