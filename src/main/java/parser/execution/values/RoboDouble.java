package parser.execution.values;

import parser.lexical.type.Type;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboDouble extends RoboNumeric {

    private Type type = Type.Double;

    private Double value;

    public RoboDouble(double value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue rv) {
        return new RoboDouble(value + ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        return new RoboDouble(value - ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue div(RoboValue rv) {
        return new RoboDouble(value / ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        return new RoboDouble(value * ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue equal(RoboValue rv) {
        return new RoboBool(aboutEqual(this.getValue(), ((Number) rv.getValue()).doubleValue()));
    }

    public static Boolean aboutEqual(double x, double y) {
        double epsilon = Math.max(Math.abs(x), Math.abs(y)) * 1E-15;
        return Math.abs(x - y) <= epsilon;
    }

    @Override
    public RoboValue notEqual(RoboValue rv) {
        return new RoboBool(!aboutEqual(this.getValue(), ((Number) rv.getValue()).doubleValue()));
    }

    @Override
    public RoboValue lowerThan(RoboValue rv) {
        return new RoboBool(value < ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        return new RoboBool(value <= ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        return new RoboBool(value > ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        return new RoboBool(value >= ((Number) rv.getValue()).doubleValue());
    }

    @Override
    public RoboValue unMinus() {
        return new RoboDouble(-getValue());
    }

    @Override
    protected void setValue(RoboValue rv) {
        value = ((Number) rv.getValue()).doubleValue();
    }

    @Override
    public RoboValue duplicate() {
        return new RoboDouble((double) value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Double getValue() {
        return value;
    }


}
