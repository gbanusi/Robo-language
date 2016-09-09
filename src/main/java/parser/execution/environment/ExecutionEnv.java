package parser.execution.environment;

import parser.execution.values.RoboValue;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.execution.visitor.statement.ProgramStatementVisitor;
import parser.lexical.Type;
import parser.lexical.TypeArray;
import parser.lexical.TypeMatrix;
import parser.syntax.nodes.expression.NodeFunction;
import parser.syntax.nodes.statements.DefFunctionStatement;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by gregor on 28.08.16..
 */
public class ExecutionEnv {

    private static Stack<ExecutionEnv> environment;

    private static ExpressionEnv expEnv;

    // TODO-2 implement better?
    private static Type lastArrayType;

    static {
        environment = new Stack<>();
        expEnv = new ExpressionEnv();
        lastArrayType = null;
        createNewEnvironment();
    }

    private FunctionEnv funcEnv;

    private VariableEnv varEnv;

    private ExecutionEnv(Map<String, DefFunctionStatement> declaredFunctions) {
        this.varEnv = new VariableEnv();
        this.funcEnv = new FunctionEnv(varEnv, declaredFunctions);
    }

    public static ExecutionEnv createNewEnvironment() {
        if (! environment.empty()) {
            environment.push(new ExecutionEnv(new HashMap<>(environment.peek().funcEnv.getDeclaredFunctions())));
        } else {
            environment.push(new ExecutionEnv(new HashMap<>()));
        }
        return environment.peek();
    }

    public static void removeEnvironment() {
        environment.pop();
    }

    public static ExecutionEnv getExecEnv(){
        return environment.peek();
    }

    public void declareVariable(String name, boolean isConstant, Type type) {
        varEnv.declareVariable(name, isConstant, type);
    }

    public RoboValue getVariableValue(String name) {
        return varEnv.getVariableValue(name);
    }

    public RoboValue getVariableValueByReference(String name){
        return varEnv.getVariableValueByReference(name);
    }

    public void defineVariable(String name, RoboValue val, Type type){
        varEnv.defineVariable(name, val, type);
    }

    public void declareFunc(String name, DefFunctionStatement val){
        funcEnv.declareFunc(name, val);
    }

    public void executeFunc(NodeFunction nf, ProgramStatementVisitor funcExec, ExpressionEvalVisitor expEval){
        funcEnv.executeFunc(nf, funcExec, expEval);
    }

    public static RoboValue popExpr() {
        return expEnv.popValue();
    }

    public static RoboValue peekValue() {
        return expEnv.peekValue();
    }

    public static void pushValue(RoboValue rv) {
        expEnv.pushValue(rv);
    }

    public static int sizeValue() {
        return expEnv.sizeValue();
    }

    public static void createMatrixType(Integer rows, Integer cols, Type type){
        lastArrayType = new TypeMatrix(type, rows, cols) ;
    }

    public static void createArrayType(Type type, Integer length){
        lastArrayType = new TypeArray(type, length) ;
    }

    public static Type getDefArrayType(){
        return lastArrayType;
    }

}
