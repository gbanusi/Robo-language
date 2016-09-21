package parser.execution.values;

import parser.execution.ExecutionException;
import parser.execution.values.core.Vector3D;
import parser.lexical.type.Type;

import java.util.List;

/**
 * Created by gregor on 21.09.16..
 */
public class RoboVector3D extends RoboValue{

    private Double length;

    private Vector3D value;

    private Type type;

    public RoboVector3D(double x, double y, double z) {
        this.value = new Vector3D(x, y, z);
        this.length = -1.0;
        this.type = Type.Vector3d;
    }

    public RoboVector3D(Vector3D add) {
        this.value = add;
        this.length = -1.0;
        this.type = Type.Vector3d;
    }

    public double getLength() {
        return length;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue rv) {
        if(! (rv instanceof RoboVector3D)){
            throw new ExecutionException("Cannot add vector3d with sth that is not v3d.");
        }
        RoboVector3D v3d = (RoboVector3D) rv;
        return new RoboVector3D(value.add(v3d.value));
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        if(! (rv instanceof RoboVector3D)){
            throw new ExecutionException("Cannot sub vector3d with sth that is not v3d.");
        }
        RoboVector3D v3d = (RoboVector3D) rv;
        return new RoboVector3D(value.sub(v3d.value));
    }

    @Override
    public RoboValue div(RoboValue rv) {
        throw new ExecutionException("'/' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        throw new ExecutionException("'*' for Vector3D is not implemented.");

    }

    @Override
    public RoboValue and(RoboValue rv) {
        throw new ExecutionException("'&&' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue or(RoboValue rv) {
        throw new ExecutionException("'||' for Vector3D is not implemented.");
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
        throw new ExecutionException("'<' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        throw new ExecutionException("'<=' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        throw new ExecutionException("'>' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        throw new ExecutionException("'>=' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        throw new ExecutionException("'[]' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("unary '-' for Vector3D is not implemented.");
    }

    @Override
    public RoboValue not() {
        throw new ExecutionException("'!' for Vector3D is not implemented.");
    }

    @Override
    protected void setValue(RoboValue rv) {
        this.value = (Vector3D) rv.getValue();
    }

    @Override
    public RoboValue duplicate() {
        return new RoboVector3D(value.getX(), value.getY(), value.getZ());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Vector3D(" + value.getX() + ", " + value.getY() + ", " + value.getZ() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoboVector3D that = (RoboVector3D) o;

        if (!getValue().equals(that.getValue())) return false;
        return getType().equals(that.getType());

    }

    @Override
    public int hashCode() {
        int result = getValue().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
}
