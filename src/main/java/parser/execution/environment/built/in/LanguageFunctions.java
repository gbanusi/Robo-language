package parser.execution.environment.built.in;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.RoboArray;
import parser.execution.values.RoboMatrix;
import parser.execution.values.RoboValue;
import parser.execution.visitor.expression.ExpressionEvalVisitor;

/**
 * Created by gregor on 16.09.16..
 */
public class LanguageFunctions {


    public static void arrayLength(ExpressionEvalVisitor expEval) {
        RoboValue array = expEval.getResult();
        if (array instanceof RoboArray) {
            ExecutionEnv.pushExpression(((RoboArray) array).getLength());
            return;
        }
        throw new ExecutionException("Function 'arrayLength' can be called only with array or matrix parameter");
    }

    public static void matrixLength(ExpressionEvalVisitor expEval) {
        RoboValue array = expEval.getResult();
        if (array instanceof RoboMatrix) {
            ExecutionEnv.pushExpression(((RoboMatrix)array).getLength());
            return;
        }
        throw new ExecutionException("Function 'matrixLength' can be called only with array or matrix parameter");
    }
}
