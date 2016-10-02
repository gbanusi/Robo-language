package parser.execution.values;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.visitor.expression.ExpressionEvalVisitor;
import parser.syntax.nodes.expression.value.right.NodeFunction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by gregor on 02.10.16..
 */
public abstract class RoboObject extends RoboValue {

    public void callFunction(NodeFunction nf, ExpressionEvalVisitor ex){
        ExecutionEnv.getExecutionEnvironment().evaluateFunctionParameters(nf, ex);
        Method method;
        try {
            method = this.getClass().getMethod(nf.getfName(), int.class );
            method.invoke(this, nf.getVars().size());
        } catch (SecurityException e) {}
        catch (NoSuchMethodException e) {
            throw new ExecutionException("Method does not exist.");
        } catch (InvocationTargetException e) {
            throw new ExecutionException("Call the dealer.");
        } catch (IllegalAccessException e) {
            throw new ExecutionException("Call the dealer.");
        }
    }
}
