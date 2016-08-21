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
        return new RoboDouble(value + (Double) add.getValue());
    }

    @Override
    public RoboValue sub(RoboValue sub) {
        return new RoboDouble(value - (Double) sub.getValue());
    }

    @Override
    public RoboValue div(RoboValue div) {
        return new RoboDouble(value / (Double) div.getValue());
    }

    @Override
    public RoboValue mult(RoboValue mult) {
        return new RoboDouble(value * (Double) mult.getValue());
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
        return new RoboBool(aboutEqual(this.getValue(), ((RoboDouble) equal).getValue()));
    }

    public static Boolean aboutEqual(double x, double y) {
        double epsilon = Math.max(Math.abs(x), Math.abs(y)) * 1E-15;
        return Math.abs(x - y) <= epsilon;
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        return new RoboBool(!aboutEqual(this.getValue(), (Double) nequal.getValue()));
    }

    @Override
    public RoboValue lowerThan(RoboValue lowerThan) {
        return new RoboBool(value < (Double) lowerThan.getValue());
    }

    @Override
    public RoboValue lowerEqual(RoboValue lowerEqual) {
        return new RoboBool(value <= (Double) lowerEqual.getValue());
    }

    @Override
    public RoboValue greaterThan(RoboValue greaterThan) {
        return new RoboBool(value > (Double) greaterThan.getValue());
    }

    @Override
    public RoboValue greaterEqual(RoboValue greaterEqual) {
        return new RoboBool(value >= (Double) greaterEqual.getValue());
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
