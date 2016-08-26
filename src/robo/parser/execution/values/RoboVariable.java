package robo.parser.execution.values;

import robo.parser.execution.ExecutionException;
import robo.parser.lexical.Type;

import java.util.List;

/**
 * Created by gregor on 20.08.16..
 */
public class RoboVariable extends RoboValue {

    private Type type;

    private RoboValue value;

    private String varName;

    private boolean isConstant;

    public RoboVariable(String varName, boolean isConstant, Type type) {
        this.varName = varName;
        this.isConstant = isConstant;
        this.value = RoboNull.roboNull;
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue add) {
        return value.add(add);
    }

    @Override
    public RoboValue sub(RoboValue sub) {
        return value.sub(sub);
    }

    @Override
    public RoboValue div(RoboValue div) {
        return value.div(div);
    }

    @Override
    public RoboValue mult(RoboValue mult) {
        return value.mult(mult);
    }

    @Override
    public RoboValue and(RoboValue and) {
        return value.and(and);
    }

    @Override
    public RoboValue or(RoboValue or) {
        return value.or(or);
    }

    @Override
    public RoboValue equal(RoboValue equal) {
        return value.equal(equal);
    }

    @Override
    public RoboValue notEqual(RoboValue nequal) {
        return value.notEqual(nequal);
    }

    @Override
    public RoboValue lowerThan(RoboValue lowerThan) {
        return value.lowerThan(lowerThan);
    }

    @Override
    public RoboValue lowerEqual(RoboValue lowerEqual) {
        return value.lowerEqual(lowerEqual);
    }

    @Override
    public RoboValue greaterThan(RoboValue greaterThan) {
        return value.greaterThan(greaterThan);
    }

    @Override
    public RoboValue greaterEqual(RoboValue greaterEqual) {
        return value.greaterEqual(greaterEqual);
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        return value.index(indexes);
    }

    @Override
    public RoboValue unMinus() {
        return value.unMinus();
    }

    @Override
    public String toString() {
        return varName + " -> " + value.toString();
    }

    public String getVarName() {
        return varName;
    }

    public RoboValue getValue() {
        return value;
    }

    public void setValue(RoboValue value) {
        if(this.value == RoboNull.roboNull || !isConstant) {
            this.type = value.getType();
            this.value = value;
            return;
        }
        throw new ExecutionException("Cannot assign value to constant variable more than once!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoboVariable that = (RoboVariable) o;

        return getVarName().equals(that.getVarName());

    }

    @Override
    public int hashCode() {
        return getVarName().hashCode();
    }
}
