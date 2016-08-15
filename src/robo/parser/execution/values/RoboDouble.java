package robo.parser.execution.values;

import robo.parser.execution.ExecutionException;
import robo.parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboDouble extends RoboValue {

    private Type type = Type.Double;

    private Double value;

    public RoboDouble(Double value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue add) {
        checkType(add);
        return new RoboDouble(value + ((RoboDouble) add).getValue());
    }

    @Override
    public RoboValue sub(RoboValue sub) {
        checkType(sub);
        return new RoboDouble(value - ((RoboDouble) sub).getValue());
    }

    @Override
    public RoboValue div(RoboValue div) {
        checkType(div);
        return new RoboDouble(value / ((RoboDouble) div).getValue());
    }

    @Override
    public RoboValue mult(RoboValue mult) {
        checkType(mult);
        return new RoboDouble(value * ((RoboDouble) mult).getValue());
    }

    @Override
    public RoboValue and(RoboValue and) {
        throw new ExecutionException("'&&' operator not supported for double!");
    }

    @Override
    public RoboValue or(RoboValue or) {
        throw new ExecutionException("'||' operator not supported for double!");
    }

    @Override
    public RoboValue equal(RoboValue equal) {
        checkType(equal);
        return new RoboBoolean(aboutEqual(this.getValue(), ((RoboDouble) equal).getValue()));
    }

    public static Boolean aboutEqual(double x, double y) {
        double epsilon = Math.max(Math.abs(x), Math.abs(y)) * 1E-15;
        return Math.abs(x - y) <= epsilon;
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        checkType(nequal);
        return new RoboBoolean(!aboutEqual(this.getValue(), ((RoboDouble) nequal).getValue()));
    }

    @Override
    public RoboValue lowerThan(RoboValue lowerThan) {
        checkType(lowerThan);
        return new RoboBoolean(value < ((RoboDouble) lowerThan).getValue());
    }

    @Override
    public RoboValue lowerEqual(RoboValue lowerEqual) {
        checkType(lowerEqual);
        return new RoboBoolean(value <= ((RoboDouble) lowerEqual).getValue());
    }

    @Override
    public RoboValue greaterThan(RoboValue greaterThan) {
        checkType(greaterThan);
        return new RoboBoolean(value > ((RoboDouble) greaterThan).getValue());
    }

    @Override
    public RoboValue greaterEqual(RoboValue greaterEqual) {
        checkType(greaterEqual);
        return new RoboBoolean(value >= ((RoboDouble) greaterEqual).getValue());
    }

    @Override
    public RoboValue unMinus() {
        return new RoboDouble(-getValue());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Double getValue() {
        return value;
    }
}
