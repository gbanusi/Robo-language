package robo.parser.execution.values;

import robo.parser.execution.ExecutionException;
import robo.parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboBoolean extends RoboValue {

    private Type type = Type.Bool;

    private Boolean value;

    public RoboBoolean(Boolean value) {
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
        checkType(and);
        return new RoboBoolean(this.getValue() && ((RoboBoolean) and).getValue());
    }

    @Override
    public RoboValue or(RoboValue or) {
        checkType(or);
        return new RoboBoolean(this.getValue() || ((RoboBoolean) or).getValue());
    }

    @Override
    public RoboValue equal(RoboValue equal) {
        checkType(equal);
        return new RoboBoolean(this.getValue().equals(((RoboBoolean) equal).getValue()));
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        checkType(nequal);
        return new RoboBoolean(value.equals(((RoboBoolean) nequal).getValue()));
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
    public RoboValue unMinus() {
        throw new ExecutionException("Unary '-' operator not supported for boolean!");
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Boolean getValue() {
        return value;
    }
}
