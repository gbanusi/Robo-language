package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;

import java.util.List;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboNull extends RoboValue {

    public static RoboNull roboNull = new RoboNull();

    private RoboNull() {
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public RoboValue add(RoboValue add) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue sub(RoboValue sub) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue div(RoboValue div) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue mult(RoboValue mult) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue and(RoboValue and) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue or(RoboValue or) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue equal(RoboValue equal) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue lowerThan(RoboValue lowerThan) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue lowerEqual(RoboValue lowerEqual) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue greaterThan(RoboValue greaterThan) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue greaterEqual(RoboValue greaterEqual) {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("'null' occured!");
    }

    @Override
    public RoboValue not() {
        throw new ExecutionException("'!' on 'null' occured!");
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        throw new ExecutionException("'[]' on 'null' occured!");
    }

    @Override
    protected void setValue(RoboValue rv) {
        throw new ExecutionException("setValue function on 'null' occured!");
    }

    @Override
    public RoboValue duplicate() {
        return new RoboNull();
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return "null";
    }
}
