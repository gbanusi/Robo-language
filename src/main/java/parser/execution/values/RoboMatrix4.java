package parser.execution.values;

import parser.execution.ExecutionException;
import parser.execution.values.core.Matrix4;
import parser.execution.values.core.Quaternion;
import parser.execution.values.core.Vector3;
import parser.lexical.type.Type;

import java.util.List;

/**
 * Created by gregor on 21.09.16..
 */
public class RoboMatrix4 extends RoboObject{

    private Matrix4 value;

    private Type type;

    public RoboMatrix4() {
        this.value = new Matrix4();
        this.type = Type.Matrix4;
    }

    public RoboMatrix4(Vector3 add, double angle) {
        this.value = new Matrix4(add, angle);
        this.type = Type.Matrix4;
    }

    public RoboMatrix4(Vector3 position, Quaternion rotation) {
        this.value = new Matrix4(position, rotation);
        this.type = Type.Matrix4;
    }

    public RoboMatrix4(Matrix4 m) {
        this.value = m;
        this.type = Type.Matrix4;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue rv) {
        throw new ExecutionException("'+' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        throw new ExecutionException("'-' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue div(RoboValue rv) {
        throw new ExecutionException("'/' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        throw new ExecutionException("'*' for Matrix4 is not implemented.");

    }

    @Override
    public RoboValue and(RoboValue rv) {
        throw new ExecutionException("'&&' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue or(RoboValue rv) {
        throw new ExecutionException("'||' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue equal(RoboValue rv) {
        return new RoboBool(rv.equals(this));
    }

    @Override
    public RoboValue notEqual(RoboValue rv) {
        return new RoboBool(!rv.equals(this));
    }

    @Override
    public RoboValue lowerThan(RoboValue rv) {
        throw new ExecutionException("'<' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        throw new ExecutionException("'<=' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        throw new ExecutionException("'>' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        throw new ExecutionException("'>=' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        throw new ExecutionException("'[]' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("unary '-' for Matrix4 is not implemented.");
    }

    @Override
    public RoboValue not() {
        throw new ExecutionException("'!' for Matrix4 is not implemented.");
    }

    @Override
    protected void setValue(RoboValue rv) {
        this.value = (Matrix4) rv.getValue();
    }

    @Override
    public RoboValue duplicate() {
        return new RoboMatrix4(this.value.cpy());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoboMatrix4 that = (RoboMatrix4) o;

        return !(getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) && !(getType() != null ? !getType().equals(that.getType()) : that.getType() != null);

    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    private void checkParamNum(int size, int expectedSize, String name) {
        if(size > 0){
            throw new ExecutionException("'" + name + "' method accepts no parameters!");
        }
    }


}
