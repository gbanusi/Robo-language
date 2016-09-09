package parser.execution.environment;

import parser.execution.values.RoboValue;

import java.util.Stack;

/**
 * Created by gregor on 28.08.16..
 */
public class ExpressionEnv {

    private Stack<RoboValue> exprsCalculated;

    public ExpressionEnv() {
        this.exprsCalculated = new Stack<>();
    }

    public RoboValue popValue() {
        return exprsCalculated.pop();
    }

    public RoboValue peekValue() {
        return exprsCalculated.peek();
    }

    public void pushValue(RoboValue rv) {
        exprsCalculated.push(rv);
    }

    public int sizeValue() {
        return exprsCalculated.size();
    }
}
