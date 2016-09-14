package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;

import java.util.List;

/**
 * Created by gregor on 20.08.16..
 */
public class RoboVariable extends RoboValue {

    private Type type;

    private RoboValue value;

    private String varName;

    private boolean isConstant;

    /**
     * For function initialization, if variable is assigned by reference in function.
     */
    private boolean defaultInit;

    public RoboVariable(String varName, boolean isConstant, Type type) {
        this.varName = varName;
        this.isConstant = isConstant;
        this.value = Type.getDefaultValue(type);
        this.type = type;
        this.defaultInit = true;
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
    public RoboValue not() {
        return value.not();
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

    public void setValue(RoboValue rv) {

        if(this.value == RoboNull.roboNull) {
            this.value = rv;
        } else if (defaultInit){
            defaultInit = false;
            this.value = rv;
        } else if (!isConstant){
            this.value.setValue(rv);
        } else {
            throw new ExecutionException("Cannot assign value to constant variable more than once!");
        }
    }

    @Override
    public RoboValue duplicate() {
        return  value.duplicate();
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

    /**
     * Setter primarly created for unknown dimension arrays or matrices.
     * Used only in variable environment class (VariableEnv).
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
    }
}
