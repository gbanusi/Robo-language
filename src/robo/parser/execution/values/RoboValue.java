package robo.parser.execution.values;

import robo.parser.lexical.Type;

/**
 * Created by gregor on 15.08.16..
 */
public abstract class RoboValue {

    public abstract Type getType();

    public abstract RoboValue add(RoboValue add);

    public abstract RoboValue sub(RoboValue sub);

    public abstract RoboValue div(RoboValue div);

    public abstract RoboValue mult(RoboValue mult);

    public abstract RoboValue and(RoboValue and);

    public abstract RoboValue or(RoboValue or);

    public abstract RoboValue equal(RoboValue equal);

    public abstract RoboValue notEqual(RoboValue nequal);

    public abstract RoboValue lowerThan(RoboValue lowerThan);

    public abstract RoboValue lowerEqual(RoboValue lowerEqual);

    public abstract RoboValue greaterThan(RoboValue greaterThan);

    public abstract RoboValue greaterEqual(RoboValue greaterEqual);

    public abstract RoboValue unMinus();

    public abstract Object getValue();

    @Override
    public abstract String toString();

}
