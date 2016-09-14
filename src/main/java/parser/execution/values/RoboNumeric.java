package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;

import java.util.List;

/**
 * Created by gregor on 23.08.16..
 */
public abstract class RoboNumeric extends RoboValue {

    @Override
    public abstract Type getType();

    @Override
    public abstract RoboValue add(RoboValue rv);

    @Override
    public abstract RoboValue sub(RoboValue rv);

    @Override
    public abstract RoboValue div(RoboValue rv);

    @Override
    public abstract RoboValue mult(RoboValue rv);

    @Override
    public RoboValue and(RoboValue rv) {
        throw new ExecutionException("'&&' operator not supported for numeric " + getType() + "!");
    }

    @Override
    public RoboValue or(RoboValue rv) {
        throw new ExecutionException("'||' operator not supported for numeric " + getType() + "!");
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        throw new ExecutionException("'[]' operator not supported for numeric " + getType() + "!");
    }

    @Override
    public RoboValue not() {
        throw new ExecutionException("'!' operator not supported for numeric " + getType() + "!");
    }

    @Override
    public RoboValue equal(RoboValue rv) {
        return null;
    }

    @Override
    public RoboValue notEqual(RoboValue rv) {
        return null;
    }

    @Override
    public RoboValue lowerThan(RoboValue rv) {
        return null;
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        return null;
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        return null;
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        return null;
    }

    @Override
    public RoboValue unMinus() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public static RoboNumeric convert(RoboNumeric rn, Type type) {
        if (type == Type.Double) {
            return new RoboDouble(((Number) rn.getValue()).doubleValue());
        } else if (type == Type.Int) {
            return new RoboInteger(((Number) rn.getValue()).intValue());
        }
        throw new ExecutionException("Type given is neither Integer nor Double! Call the dealer.");
    }

    @Override
    public void setRoboValue(RoboValue rv) {
        if (!(rv instanceof RoboNumeric)) {
            throw new ExecutionException("Cannot assign '" + getType() + "' to '" + rv.getType() + "'");
        }
        setValue(rv);
    }

}
