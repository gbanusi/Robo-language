package robo.parser.execution.values;

import robo.parser.execution.ExecutionException;
import robo.parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboString extends RoboValue {

    private Type type = Type.String;

    private String value;

    public RoboString(String value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue add) {
        return new RoboString(value + add.toString());
    }

    @Override
    public RoboValue sub(RoboValue sub) {
        throw new ExecutionException("'-' operator not supported for string!");
    }

    @Override
    public RoboValue div(RoboValue div) {
        throw new ExecutionException("'/' operator not supported for string!");
    }

    @Override
    public RoboValue mult(RoboValue mult) {
        throw new ExecutionException("'*' operator not supported for string!");
    }

    @Override
    public RoboValue and(RoboValue and) {
        throw new ExecutionException("'&&' operator not supported for string!");
    }

    @Override
    public RoboValue or(RoboValue or) {
        throw new ExecutionException("'||' operator not supported for string!");
    }

    @Override
    public RoboValue equal(RoboValue equal) {
        return new RoboBool(this.getValue().equals(equal));
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        return new RoboBool(!value.equals(nequal));
    }

    @Override
    public RoboValue lowerThan(RoboValue lowerThan) {
        throw new ExecutionException("'<' operator not supported for string!");
    }

    @Override
    public RoboValue lowerEqual(RoboValue lowerEqual) {
        throw new ExecutionException("'<=' operator not supported for string!");
    }

    @Override
    public RoboValue greaterThan(RoboValue greaterThan) {
        throw new ExecutionException("'>' operator not supported for string!");
    }

    @Override
    public RoboValue greaterEqual(RoboValue greaterEqual) {
        throw new ExecutionException("'>=' operator not supported for string!");
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("Unary '-' operator not supported for string!");
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoboString that = (RoboString) o;

        return !(getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null);

    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
