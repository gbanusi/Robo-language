package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
 * TODO-2 error prone cause of type checking...
 */
public class RoboInteger extends RoboNumeric {

    private Type type = Type.Int;

    private Integer value;

    public RoboInteger(Integer value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboInteger(value + (Integer) rv.getValue());
        }
        return rv.add(this);
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboInteger(value - (Integer) rv.getValue());
        } else if(rv instanceof  RoboDouble){
            return new RoboDouble(value.doubleValue() - (Double) rv.getValue());
        } else {
            return rv.sub(this);
        }
    }

    @Override
    public RoboValue div(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboInteger(value / (Integer) rv.getValue());
        } else if(rv instanceof  RoboDouble){
            return new RoboDouble(value.doubleValue() / (Double) rv.getValue());
        } else {
            return rv.div(this);
        }
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboInteger(value * (Integer) rv.getValue());
        }
        return rv.mult(this);
    }

    @Override
    public RoboValue equal(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboBool(value == rv.getValue());
        }
        return rv.equal(this);
    }

    @Override
    public RoboValue notEqual(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboBool(value != rv.getValue());
        }
        return rv.notEqual(this);
    }

    @Override
    public RoboValue lowerThan(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboBool(value < (Integer) rv.getValue());
        }
        return rv.greaterThan(this);
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboBool(value <= (Integer) rv.getValue());
        }
        return rv.greaterEqual(this);
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboBool(value > (Integer) rv.getValue());
        }
        return rv.lowerThan(this);
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        if (rv instanceof RoboInteger) {
            return new RoboBool(value >= (Integer) rv.getValue());
        }
        return rv.lowerEqual(this);
    }

    @Override
    public RoboValue unMinus() {
        return new RoboInteger(-getValue());
    }

    @Override
    protected void setValue(RoboValue rv) {
        value = ((Number) rv.getValue()).intValue();
    }

    @Override
    public RoboValue duplicate() {
        return new RoboInteger((int) value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Integer getValue() {
        return value;
    }

}
