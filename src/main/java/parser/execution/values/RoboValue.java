package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.type.Type;

import java.util.List;

/**
 * Created by gregor on 14.08.16..
 *
 */
public abstract class RoboValue {

    public abstract Type getType();

    public abstract RoboValue add(RoboValue rv);

    public abstract RoboValue sub(RoboValue rv);

    public abstract RoboValue div(RoboValue rv);

    public abstract RoboValue mult(RoboValue rv);

    public abstract RoboValue and(RoboValue rv);

    public abstract RoboValue or(RoboValue rv);

    public abstract RoboValue equal(RoboValue rv);

    public abstract RoboValue notEqual(RoboValue rv);

    public abstract RoboValue lowerThan(RoboValue rv);

    public abstract RoboValue lowerEqual(RoboValue rv);

    public abstract RoboValue greaterThan(RoboValue rv);

    public abstract RoboValue greaterEqual(RoboValue rv);

    public abstract RoboValue index(List<RoboValue> indexes);

    public abstract RoboValue unMinus();

    public abstract RoboValue not();

    public void setRoboValue(RoboValue rv){
        if(getType() != rv.getType()){
            throw new ExecutionException("Cannot assign '"+ getType() +"' to '" + rv.getType() + "'");
        }
        setValue(rv);
    }

    protected abstract void setValue(RoboValue rv);

    public abstract RoboValue duplicate();

    public abstract Object getValue();

    @Override
    public abstract String toString();

}
