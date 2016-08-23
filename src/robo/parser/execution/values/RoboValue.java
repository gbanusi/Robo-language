package robo.parser.execution.values;

import robo.parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
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

    public abstract RoboValue unMinus();

    public abstract Object getValue();

    @Override
    public abstract String toString();

}
