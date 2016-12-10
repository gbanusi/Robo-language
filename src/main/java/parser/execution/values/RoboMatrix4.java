package parser.execution.values;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.core.Matrix4;
import parser.execution.values.core.Quaternion;
import parser.execution.values.core.Vector;
import parser.lexical.type.Type;

import java.util.List;

/**
 * Created by gregor on 21.09.16..
 */
public class RoboMatrix4 extends RoboObject {

    private Matrix4 value;

    private Type type;

    public RoboMatrix4() {
        this.value = new Matrix4();
        this.type = Type.Matrix4;
    }

    public RoboMatrix4(Vector add, double angle) {
        this.value = new Matrix4(add, angle);
        this.type = Type.Matrix4;
    }

    public RoboMatrix4(Vector position, Quaternion rotation) {
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
        RoboMatrix4 m4 = (RoboMatrix4) rv;
        this.value.add(m4.getValue());
        return this;
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        RoboMatrix4 m4 = (RoboMatrix4) rv;
        this.value.sub(m4.getValue());
        return this;
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
    public Matrix4 getValue() {
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

    public void getxAxis(int size) {
        checkParamNum(size, 0, "getxAxis");
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.getxAxis()));
    }

    public void getyAxis(int size) {
        checkParamNum(size, 0, "getyAxis");
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.getyAxis()));
    }

    public void getzAxis(int size) {
        checkParamNum(size, 0, "getzAxis");
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.getzAxis()));
    }

    public void getPos(int size) {
        checkParamNum(size, 0, "getPos");
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.getPos()));
    }

    public void cpy(int size) {
        checkParamNum(size, 0, "cpy");
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.cpy()));
    }

    public void addTranslation(int size) {
        checkParamNum(size, 1, "addTranslation");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.addTranslation(rv.getValue())));
    }

    public void mul(int size) {
        checkParamNum(size, 1, "mul");
        RoboMatrix4 rv = (RoboMatrix4) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.mul(rv.getValue())));
    }

    public void mulLeft(int size) {
        checkParamNum(size, 1, "mulLeft");
        RoboMatrix4 rv = (RoboMatrix4) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.mulLeft(rv.getValue())));
    }

    public void transpose(int size) {
        checkParamNum(size, 0, "transpose");
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.transpose()));
    }

    public void makeIdentity(int size) {
        checkParamNum(size, 0, "makeIdentity");
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.idt()));
    }

    public void invert(int size) {
        checkParamNum(size, 0, "inverse");
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.inverse()));
    }

    public void determinant(int size) {
        checkParamNum(size, 0, "determinant");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.det()));
    }

    public void setTranslation(int size) {
        checkParamNum(size, 1, "setTranslation");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.setTranslation(rv.getValue())));
    }

    public void setToTranslation(int size) {
        checkParamNum(size, 1, "setToTranslation");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.setToTranslation(rv.getValue())));
    }

    public void setToRotation(int size) {
        checkParamNum(size, 2, "setToRotation");
        RoboVector3D v1 = (RoboVector3D) ExecutionEnv.getFunctionParam();
        RoboVector3D v2 = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.setToRotation(v1.getValue(), v2.getValue())));
    }

    public void setFromEulerAngles(int size) {
        checkParamNum(size, 3, "setFromEulerAngles");
        RoboDouble yaw = (RoboDouble) ExecutionEnv.getFunctionParam();
        RoboDouble pitch = (RoboDouble) ExecutionEnv.getFunctionParam();
        RoboDouble roll = (RoboDouble) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.setFromEulerAngles(yaw.getValue(), pitch.getValue(), roll.getValue())));
    }

    public void getTranslation(int size) {
        checkParamNum(size, 1, "getTranslation");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.getTranslation(rv.getValue())));
    }

    public void getRotation(int size) {
        if (size == 1) {
            RoboQuaternion quat = (RoboQuaternion) ExecutionEnv.getFunctionParam();
            ExecutionEnv.pushExpression(new RoboQuaternion(this.value.getRotation(quat.getValue())));
        } else if (size == 2) {
            RoboQuaternion quat = (RoboQuaternion) ExecutionEnv.getFunctionParam();
            RoboBool normalizeAxes = (RoboBool) ExecutionEnv.getFunctionParam();
            ExecutionEnv.pushExpression(new RoboQuaternion(this.value.getRotation(quat.getValue(), normalizeAxes.getValue())));
        } else {
            checkParamNum(size, -1, "getRotation");
        }

    }

    public void getScaleXSquared(int size) {
        checkParamNum(size, 0, "getScaleXSquared");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getScaleXSquared()));
    }

    public void getScaleYSquared(int size) {
        checkParamNum(size, 0, "getScaleYSquared");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getScaleYSquared()));
    }

    public void getScaleZSquared(int size) {
        checkParamNum(size, 0, "getScaleZSquared");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getScaleZSquared()));
    }

    public void getScaleX(int size) {
        checkParamNum(size, 0, "getScaleX");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getScaleX()));
    }

    public void getScaleY(int size) {
        checkParamNum(size, 0, "getScaleY");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getScaleY()));
    }

    public void getScaleZ(int size) {
        checkParamNum(size, 0, "getScaleZ");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getScaleZ()));
    }

    public void getScale(int size) {
        checkParamNum(size, 1, "getScale");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.getScale(rv.getValue())));
    }

    public void toNormalMatrix(int size) {
        checkParamNum(size, 0, "toNormalMatrix");
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.toNormalMatrix()));
    }

    public void rotate(int size) {
        if (size == 1) {
            RoboQuaternion quat = (RoboQuaternion) ExecutionEnv.getFunctionParam();
            ExecutionEnv.pushExpression(new RoboMatrix4(this.value.rotate(quat.getValue())));
        } else if (size == 2) {
            RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
            RoboValue val = ExecutionEnv.getFunctionParam();
            if (val instanceof RoboDouble) {
                RoboDouble degrees = (RoboDouble) val;
                ExecutionEnv.pushExpression(new RoboMatrix4(this.value.rotate(rv.getValue(), degrees.getValue())));
            } else {
                RoboVector3D degrees = (RoboVector3D) val;
                ExecutionEnv.pushExpression(new RoboMatrix4(this.value.rotate(rv.getValue(), degrees.getValue())));
            }
        } else {
            checkParamNum(size, -1, "getRotation");
        }
    }

    public void getMatrix(int size) {
        checkParamNum(size, 0, "getMatrix");
        ExecutionEnv.pushExpression(new RoboMatrix(this.value.getVal()));
    }
}
