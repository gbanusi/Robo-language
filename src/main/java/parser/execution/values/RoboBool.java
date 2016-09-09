package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;

import java.util.List;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboBool extends RoboValue {

    private Type type = Type.Bool;

    private Boolean value;

    public RoboBool(Boolean value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue add) {
        throw new ExecutionException("'+' operator not supported for boolean!");
    }

    @Override
    public RoboValue sub(RoboValue sub) {
        throw new ExecutionException("'-' operator not supported for boolean!");
    }

    @Override
    public RoboValue div(RoboValue div) {
        throw new ExecutionException("'/' operator not supported for boolean!");
    }

    @Override
    public RoboValue mult(RoboValue mult) {
        throw new ExecutionException("'*' operator not supported for boolean!");

    }

    @Override
    public RoboValue and(RoboValue and) {
        return new RoboBool(this.getValue() && ((RoboBool) and).getValue());
    }

    @Override
    public RoboValue or(RoboValue or) {
        return new RoboBool(this.getValue() || ((RoboBool) or).getValue());
    }

    @Override
    public RoboValue equal(RoboValue equal) {
        return new RoboBool(this.getValue().equals(((RoboBool) equal).getValue()));
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        return new RoboBool(value.equals(((RoboBool) nequal).getValue()));
    }

    @Override
    public RoboValue lowerThan(RoboValue lowerThan) {
        throw new ExecutionException("'<' operator not supported for boolean!");
    }

    @Override
    public RoboValue lowerEqual(RoboValue lowerEqual) {
        throw new ExecutionException("'<=' operator not supported for boolean!");
    }

    @Override
    public RoboValue greaterThan(RoboValue greaterThan) {
        throw new ExecutionException("'>' operator not supported for boolean!");
    }

    @Override
    public RoboValue greaterEqual(RoboValue greaterEqual) {
        throw new ExecutionException("'>=' operator not supported for boolean!");
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        throw new ExecutionException("'[]' operator not supported for boolean!");
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("Unary '-' operator not supported for boolean!");
    }

    @Override
    public RoboValue not() {
        return new RoboBool(!value);
    }

    @Override
    protected void setValue(RoboValue rv) {
        value = (Boolean) rv.getValue();
    }

    @Override
    public RoboValue duplicate() {
        return new RoboBool(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Boolean getValue() {
        return value;
    }
}
