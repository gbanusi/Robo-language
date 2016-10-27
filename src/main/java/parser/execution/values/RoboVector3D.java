package parser.execution.values;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.core.Vector3;
import parser.lexical.type.Type;

import java.util.List;

import static parser.lexical.type.Type.Double;
import static parser.lexical.type.Type.Vector3d;

/**
 * Created by gregor on 21.09.16..
 */
public class RoboVector3D extends RoboObject {

    private Double length;

    private Vector3 value;

    private Type type;

    public RoboVector3D(double x, double y, double z) {
        this.value = new Vector3(x, y, z);
        this.length = -1.0;
        this.type = Type.Vector3d;
    }

    public RoboVector3D(Vector3 add) {
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
        if (!(rv instanceof RoboVector3D)) {
            throw new ExecutionException("Cannot add vector3d with sth that is not v3d.");
        }
        RoboVector3D v3d = (RoboVector3D) rv;
        return new RoboVector3D(value.add(v3d.value));
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        if (!(rv instanceof RoboVector3D)) {
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
        this.value = (Vector3) rv.getValue();
    }

    @Override
    public RoboValue duplicate() {
        return new RoboVector3D(value.getX(), value.getY(), value.getZ());
    }

    @Override
    public Vector3 getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
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

    public void normalize(int size) {
        checkParamNum(size, 0, "normalize");
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.normalize()));
    }

    public void length(int size) {
        checkParamNum(size, 0, "length");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.len()));
    }

    public void length2(int size) {
        checkParamNum(size, 0, "length2");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.len2()));
    }

    public void dot(int size) {
        checkParamNum(size, 0, "dot");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(new RoboDouble(this.value.dot((Vector3) rv.getValue())));
    }

    public void cross(int size) {
        checkParamNum(size, 1, "cross");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.cross((Vector3) rv.getValue())));
    }

    public void rotate(int size) {
        checkParamNum(size, 2, "rotate");
        RoboDouble degree = (RoboDouble) ExecutionEnv.popExpression();
        RoboVector3D axis = (RoboVector3D) ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.rotate((Vector3) axis.getValue(), degree.getValue())));
    }

    public void distance(int size) {
        checkParamNum(size, 1, "distance");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(new RoboDouble(this.value.distance((Vector3) rv.getValue())));
    }

    public void distance2(int size) {
        checkParamNum(size, 1, "distance");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(new RoboDouble(this.value.distance2((Vector3) rv.getValue())));
    }

    public void scalar(int size) {
        checkParamNum(size, 1, "scalar");
        RoboValue rv = ExecutionEnv.popExpression();
        if (rv.getType() == Double) {
            this.value.scl((double) rv.getValue());
            ExecutionEnv.pushExpression(this);
            return;
        } else if (rv.getType() == Vector3d) {
            this.value.scl((Vector3) rv.getValue());
            ExecutionEnv.pushExpression(this);
            return;
        }
        throw new ExecutionException("Illegal parameter");
    }

    public void negate(int size) {
        checkParamNum(size, 1, "negate");
        this.value.scl(-1);
        ExecutionEnv.pushExpression(this);
    }

}
