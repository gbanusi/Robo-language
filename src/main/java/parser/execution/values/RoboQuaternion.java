package parser.execution.values;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.core.Quaternion;
import parser.execution.values.core.Vector;
import parser.lexical.type.Type;

import java.util.List;

/**
 * Created by gregor on 21.09.16..
 */
public class RoboQuaternion extends RoboObject {

    private Quaternion value;

    private Type type;

    public RoboQuaternion(double x, double y, double z, double w) {
        this.value = new Quaternion(x, y, z, w);
        this.type = Type.Quaternion;
    }

    public RoboQuaternion(Vector add, double angle) {
        this.value = new Quaternion(add, angle);
        this.type = Type.Quaternion;
    }

    public RoboQuaternion(Quaternion q) {
        this.value = q;
        this.type = Type.Quaternion;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue rv) {
        throw new ExecutionException("'+' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        throw new ExecutionException("'-' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue div(RoboValue rv) {
        throw new ExecutionException("'/' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        throw new ExecutionException("'*' for Quaternion is not implemented.");

    }

    @Override
    public RoboValue and(RoboValue rv) {
        throw new ExecutionException("'&&' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue or(RoboValue rv) {
        throw new ExecutionException("'||' for Quaternion is not implemented.");
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
        throw new ExecutionException("'<' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        throw new ExecutionException("'<=' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        throw new ExecutionException("'>' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        throw new ExecutionException("'>=' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        throw new ExecutionException("'[]' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("unary '-' for Quaternion is not implemented.");
    }

    @Override
    public RoboValue not() {
        throw new ExecutionException("'!' for Quaternion is not implemented.");
    }

    @Override
    protected void setValue(RoboValue rv) {
        this.value = (Quaternion) rv.getValue();
    }

    @Override
    public RoboValue duplicate() {
        return new RoboQuaternion(this.value.cpy());
    }

    @Override
    public Quaternion getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoboQuaternion that = (RoboQuaternion) o;

        return !(getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) && !(getType() != null ? !getType().equals(that.getType()) : that.getType() != null);

    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }


    public void setEulerAngles(int size) {
        checkParamNum(size, 3, "setEulerAngles");
        RoboDouble yaw = (RoboDouble) ExecutionEnv.getFunctionParam();
        RoboDouble pitch = (RoboDouble) ExecutionEnv.getFunctionParam();
        RoboDouble roll = (RoboDouble) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.setEulerAngles(yaw.getValue(), pitch.getValue(), roll.getValue())));
    }

    public void cpy(int size) {
        checkParamNum(size, 0, "cpy");
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.cpy()));
    }

    public void len(int size) {
        checkParamNum(size, 0, "len");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.len()));
    }

    public void len2(int size) {
        checkParamNum(size, 0, "len2");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.len2()));
    }

    public void setEulerAnglesRad(int size) {
        checkParamNum(size, 3, "setEulerAnglesRad");
        RoboDouble yaw = (RoboDouble) ExecutionEnv.getFunctionParam();
        RoboDouble pitch = (RoboDouble) ExecutionEnv.getFunctionParam();
        RoboDouble roll = (RoboDouble) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.setEulerAnglesRad(yaw.getValue(), pitch.getValue(), roll.getValue())));
    }

    public void getGimbalPole(int size) {
        checkParamNum(size, 0, "getGimbalPole");
        ExecutionEnv.pushExpression(new RoboInteger(this.value.getGimbalPole()));
    }

    public void getRollRad(int size) {
        checkParamNum(size, 0, "getRollRad");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getRollRad()));
    }

    public void getRoll(int size) {
        checkParamNum(size, 0, "getRoll");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getRoll()));
    }

    public void getPitchRad(int size) {
        checkParamNum(size, 0, "getPitchRad");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getPitchRad()));
    }

    public void getPitch(int size) {
        checkParamNum(size, 0, "getPitch");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getPitch()));
    }

    public void getYaw(int size) {
        checkParamNum(size, 0, "getYaw");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getYaw()));
    }

    public void nor(int size) {
        checkParamNum(size, 0, "nor");
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.nor()));
    }

    public void conjugate(int size) {
        checkParamNum(size, 0, "conjugate");
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.conjugate()));
    }

    public void transform(int size) {
        checkParamNum(size, 0, "transform");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboVector3D(this.value.transform(rv.getValue())));
    }

    public void mul(int size) {
        checkParamNum(size, 1, "mul");
        RoboQuaternion rv = (RoboQuaternion) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.mul(rv.getValue())));
    }

    public void mulLeft(int size) {
        checkParamNum(size, 1, "mulLeft");
        RoboQuaternion rv = (RoboQuaternion) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.mulLeft(rv.getValue())));
    }

    public void add(int size) {
        checkParamNum(size, 1, "add");
        RoboQuaternion rv = (RoboQuaternion) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.add(rv.getValue())));
    }
    
    public void toMatrix(int size) {
        checkParamNum(size, 1, "toMatrix");
        RoboQuaternion rv = (RoboQuaternion) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboMatrix4(this.value.toMatrix()));
    }

    public void setFromCross(int size) {
        checkParamNum(size, 2, "setFromCross");
        RoboVector3D rv1 = (RoboVector3D) ExecutionEnv.getFunctionParam();
        RoboVector3D rv2 = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.setFromCross(rv1.getValue(), rv2.getValue())));
    }

    public void setFromAxisRad(int size) {
        checkParamNum(size, 2, "setFromAxisRad");
        RoboVector3D rv1 = (RoboVector3D) ExecutionEnv.getFunctionParam();
        RoboDouble rv2 = (RoboDouble) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.setFromAxisRad(rv1.getValue(), rv2.getValue())));
    }

    public void setFromAxis(int size) {
        checkParamNum(size, 2, "setFromAxis");
        RoboVector3D rv1 = (RoboVector3D) ExecutionEnv.getFunctionParam();
        RoboDouble rv2 = (RoboDouble) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.setFromAxis(rv1.getValue(), rv2.getValue())));
    }

    public void setFromMatrix(int size) {
        checkParamNum(size, 2, "setFromMatrix");
        RoboBool rv1 = (RoboBool) ExecutionEnv.getFunctionParam();
        RoboMatrix4 rv2 = (RoboMatrix4) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.setFromMatrix(rv1.getValue(), rv2.getValue())));
    }

    public void idt(int size) {
        checkParamNum(size, 0, "idt");
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.idt()));
    }

    public void isIdentity(int size) {
        checkParamNum(size, 1, "isIdentity");
        RoboDouble rv = (RoboDouble) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboBool(this.value.isIdentity(rv.getValue())));
    }

    public void getAxisAngle(int size) {
        checkParamNum(size, 1, "getAxisAngle");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getAxisAngle(rv.getValue())));
    }

    public void getAxisAngleRad(int size) {
        checkParamNum(size, 1, "getAxisAngleRad");
        RoboVector3D rv = (RoboVector3D) ExecutionEnv.getFunctionParam();
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getAxisAngleRad(rv.getValue())));
    }

    public void getAngleRad(int size) {
        checkParamNum(size, 0, "getAngleRad");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getAngleRad()));
    }

    public void getAngle(int size) {
        checkParamNum(size, 0, "getAngle");
        ExecutionEnv.pushExpression(new RoboDouble(this.value.getAngle()));
    }

}
