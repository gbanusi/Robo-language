package robo.parser.execution.values;

import robo.parser.execution.ExecutionException;
import robo.parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboNull extends RoboValue {
    @Override
    public Type getType() {
        return null;
    }

    @Override
    public RoboValue add(RoboValue add) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue sub(RoboValue sub) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue div(RoboValue div) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue mult(RoboValue mult) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue and(RoboValue and) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue or(RoboValue or) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue equal(RoboValue equal) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue lowerThan(RoboValue lowerThan) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue lowerEqual(RoboValue lowerEqual) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue greaterThan(RoboValue greaterThan) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue greaterEqual(RoboValue greaterEqual) {
        throw new ExecutionException("null occured!");
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("null occured!");
    }

    @Override
    public String toString() {
        return "null";
    }
}
