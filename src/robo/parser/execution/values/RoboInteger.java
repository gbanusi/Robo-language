package robo.parser.execution.values;

import robo.parser.execution.ExecutionException;
import robo.parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
 */
public class RoboInteger extends RoboValue {

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
        if(rv instanceof RoboInteger){
            return new RoboInteger(value + (Integer)rv.getValue());
        }
        return rv.add(this);
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboInteger(value - (Integer)rv.getValue());
        }
        return rv.sub(this);
    }

    @Override
    public RoboValue div(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboInteger(value / (Integer)rv.getValue());
        }
        return rv.div(rv);
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboInteger(value * (Integer)rv.getValue());
        }
        return rv.mult(this);
    }

    @Override
    public RoboValue and(RoboValue and) {
        throw new ExecutionException("'&&' operator not supported for integer!");
    }

    @Override
    public RoboValue or(RoboValue or) {
        throw new ExecutionException("'||' operator not supported for integer!");
    }

    @Override
    public RoboValue equal(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboBool(value == rv.getValue());
        }
        return rv.equal(this);
    }

    @Override
    public RoboValue notEqual(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboBool(value != rv.getValue());
        }
        return rv.notEqual(this);
    }

    @Override
    public RoboValue lowerThan(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboBool(value < (Integer)rv.getValue());
        }
        return rv.greaterThan(this);
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboBool(value <= (Integer)rv.getValue());
        }
        return rv.greaterEqual(this);
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboBool(value > (Integer)rv.getValue());
        }
        return rv.lowerThan(this);
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        if(rv instanceof RoboInteger){
            return new RoboBool(value >= (Integer)rv.getValue());
        }
        return rv.lowerEqual(this);
    }

    @Override
    public RoboValue unMinus() {
        return new RoboInteger(-getValue());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Integer getValue() {
        return value;
    }
}
