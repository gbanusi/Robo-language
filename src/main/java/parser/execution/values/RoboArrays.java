package parser.execution.values;

import parser.execution.ExecutionException;

/**
 * Created by gregor on 20.08.16..
 * TODO-1 reorganize with arrays everything
 */
public abstract class RoboArrays extends RoboValue {

    public abstract RoboValue getLength();

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("'-' operator not supported for array!");
    }

    @Override
    public RoboValue not() {
        throw new ExecutionException("'!' operator not supported for array!");
    }

    @Override
    public RoboValue add(RoboValue rv) {
        throw new ExecutionException("'+' operator not supported for array!");
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        throw new ExecutionException("'-' operator not supported for array!");
    }

    @Override
    public RoboValue div(RoboValue rv) {
        throw new ExecutionException("'/' operator not supported for array!");
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        throw new ExecutionException("'*' operator not supported for array!");
    }

    @Override
    public RoboValue and(RoboValue rv) {
        throw new ExecutionException("'&&' operator not supported for array!");
    }

    @Override
    public RoboValue or(RoboValue rv) {
        throw new ExecutionException("'||' operator not supported for array!");
    }

    @Override
    public RoboValue equal(RoboValue rv) {
        throw new ExecutionException("'==' operator not supported for array!");
    }

    @Override
    public RoboValue notEqual(RoboValue rv) {
        throw new ExecutionException("'!=' operator not supported for array!");
    }

    @Override
    public RoboValue lowerThan(RoboValue rv) {
        throw new ExecutionException("'<' operator not supported for array!");
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        throw new ExecutionException("'<=' operator not supported for array!");
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        throw new ExecutionException("'>' operator not supported for array!");
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        throw new ExecutionException("'>=' operator not supported for array!");
    }
}
