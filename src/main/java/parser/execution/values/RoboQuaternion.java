package parser.execution.values;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.core.Quaternion;
import parser.execution.values.core.Vector3;
import parser.lexical.type.Type;

import java.util.List;

/**
 * Created by gregor on 21.09.16..
 */
public class RoboQuaternion extends RoboObject{

    private Quaternion value;

    private Type type;

    public RoboQuaternion(double x, double y, double z, double w) {
        this.value = new Quaternion(x, y, z, w);
        this.type = Type.Quaternion;
    }

    public RoboQuaternion(Vector3 add, double angle) {
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
        return  value.toString();
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
        checkParamNum(size, 3, "rotate");
        RoboDouble yaw = (RoboDouble) ExecutionEnv.popExpression();
        RoboDouble pitch = (RoboDouble) ExecutionEnv.popExpression();
        RoboDouble roll = (RoboDouble) ExecutionEnv.popExpression();
        ExecutionEnv.pushExpression(new RoboQuaternion(this.value.setEulerAngles(yaw.getValue(), pitch.getValue(), roll.getValue())));
    }


}
