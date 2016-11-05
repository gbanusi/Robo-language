package parser.execution.environment;

import parser.execution.values.RoboValue;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.execution.visitor.statement.ProgramStatementVisitor;
import parser.lexical.type.Type;
import parser.lexical.type.TypeArray;
import parser.lexical.type.TypeFuncArray;
import parser.lexical.type.TypeMatrix;
import parser.syntax.nodes.expression.value.right.NodeFunction;
import parser.syntax.nodes.statements.definition.DefFunctionStatement;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by gregor on 28.08.16..
 * TODO-2 remove every method into his own environment?
 */
public class ExecutionEnv {

    //--------------------------------------STATIC-----------------------------------------
    private static Stack<ExecutionEnv> environment;

    private static ExpressionEnv expEnv;

    private static Type lastArrayType;

    private static boolean stopFunction;

    private static boolean breakLoop;

    private static boolean continueLoop;

    static {
        environment = new Stack<>();
        expEnv = new ExpressionEnv();
        lastArrayType = null;
        stopFunction = false;
        breakLoop = false;
        continueLoop = false;
        createNewEnvironment();
    }

    //------------------------------------EXECUTION ENVIRONMENT------------------------------------------------

    private FunctionEnv funcEnv;

    private VariableEnv varEnv;

    private ExecutionEnv(Map<String, DefFunctionStatement> declaredFunctions) {
        this.varEnv = new VariableEnv();
        this.funcEnv = new FunctionEnv(declaredFunctions);
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

    public static ExecutionEnv getExecutionEnvironment(){
        return environment.peek();
    }

    //--------------------------------------VARIABLE------------------------------------------

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

    public Type getVariableType(String name){
        return varEnv.getVariableType(name);
    }

    //---------------------------------------FUNCTION-----------------------------------------

    public void declareFunc(String name, DefFunctionStatement val){
        funcEnv.declareFunc(name, val);
    }

    public void executeFunc(NodeFunction nf, ProgramStatementVisitor funcExec, ExpressionEvalVisitor expEval){
        funcEnv.executeFunc(nf, funcExec, expEval);
    }

    public Type getFunctionReturnType(String funcName){
        return funcEnv.getFunctionReturnType(funcName);
    }

    public static Type getCurrentFunctionReturnType(){
        return FunctionEnv.getCurrentReturnType();
    }


    public static void stopFunction(){
        stopFunction = true;
    }

    public static boolean isFunctionExecuted(){
        return FunctionEnv.isFunctionExecuting();
    }

    protected static boolean isFunctionStopped(){
        if(stopFunction){
            stopFunction = false;
            return true;
        }
        return false;
    }

    public void evaluateFunctionParameters(NodeFunction nf, ExpressionEvalVisitor ex){
        funcEnv.evaluateParams(nf, ex);
    }

    public static RoboValue getFunctionParam(){
        return getExecutionEnvironment().funcEnv.popFunctionParam();
    }

    //---------------------------------------EXPRESSION-----------------------------------------

    public static RoboValue popExpression() {
        return expEnv.popValue();
    }

    public static RoboValue peekExpression() {
        return expEnv.peekValue();
    }

    public static void pushExpression(RoboValue rv) {
        expEnv.pushValue(rv);
    }

    public static int expressionStackSize() {
        return expEnv.sizeValue();
    }

    //---------------------------------------TYPE CREATION--------------------------------------

    public static void createMatrixType(Integer rows, Integer cols, Type type){
        lastArrayType = new TypeMatrix(type, rows, cols) ;
    }

    public static void createArrayType(Type type, Integer length){
        lastArrayType = new TypeArray(type, length) ;
    }

    public static Type getDefArrayType(){
        return lastArrayType;
    }

    public static void createUnknownArrayType(Type type) {
        lastArrayType = new TypeFuncArray(type);
    }

    //----------------------------------------LOOPS---------------------------------------------

    public static boolean isLoopStopped() {
        return breakLoop;
    }

    public static boolean isLoopContinued() {
        return continueLoop;
    }

    public static void setBreakLoop(boolean breakLoop) {
        ExecutionEnv.breakLoop = breakLoop;
    }

    public static void setContinueLoop(boolean continueLoop) {
        ExecutionEnv.continueLoop = continueLoop;
    }

    public static void removeLoopVariable(String varName) {
        ExecutionEnv.getExecutionEnvironment().varEnv.removeLoopVariable(varName);
    }

}
