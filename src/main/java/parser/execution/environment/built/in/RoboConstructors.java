package parser.execution.environment.built.in;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.*;
import parser.execution.values.core.Matrix4;
import parser.execution.values.core.Quaternion;
import parser.execution.values.core.Vector;
import parser.lexical.type.Type;

/**
 * Created by gregor on 21.09.16..
 *
 * Constructor variables reversed because of using stack.
 */
public class RoboConstructors {

    public static void roboVector3D(RoboValue x, RoboValue y, RoboValue z) {
        if (!(x instanceof RoboNumeric)
                || !(y instanceof RoboNumeric)
                || !(z instanceof RoboNumeric)) {
            throw new ExecutionException("Vector3D constructor accepts only numeric arguments in constructor.");
        }
        RoboDouble xd = (RoboDouble) RoboNumeric.convert((RoboNumeric) x, Type.Double);
        RoboDouble yd = (RoboDouble) RoboNumeric.convert((RoboNumeric) y, Type.Double);
        RoboDouble zd = (RoboDouble) RoboNumeric.convert((RoboNumeric) z, Type.Double);
        ExecutionEnv.pushExpression(new RoboVector3D(xd.getValue(), yd.getValue(), zd.getValue()));
    }

    public static void roboQuaternion(RoboValue vector, RoboValue angle) {
        if (!(vector instanceof RoboVector3D)
                || !(angle instanceof RoboNumeric)) {
            throw new ExecutionException("Quaternion constructor accepts only numeric arguments in constructor.");
        }
        RoboVector3D vectord = (RoboVector3D) vector;
        RoboDouble angled = (RoboDouble) RoboNumeric.convert((RoboNumeric) angle, Type.Double);
        ExecutionEnv.pushExpression(new RoboQuaternion((Vector)vectord.getValue(), angled.getValue()));
    }

    public static void roboQuaternion(RoboValue x, RoboValue y, RoboValue z, RoboValue w) {
        if (!(x instanceof RoboNumeric)
                || !(y instanceof RoboNumeric)
                || !(z instanceof RoboNumeric)) {
            throw new ExecutionException("Quaternion constructor accepts only numeric arguments in constructor.");
        }
        RoboDouble xd = (RoboDouble) RoboNumeric.convert((RoboNumeric) x, Type.Double);
        RoboDouble yd = (RoboDouble) RoboNumeric.convert((RoboNumeric) y, Type.Double);
        RoboDouble zd = (RoboDouble) RoboNumeric.convert((RoboNumeric) z, Type.Double);
        RoboDouble wd = (RoboDouble) RoboNumeric.convert((RoboNumeric) w, Type.Double);
        ExecutionEnv.pushExpression(new RoboQuaternion(xd.getValue(), yd.getValue(), zd.getValue(), wd.getValue()));
    }

    public static void roboMatrix4() {
        ExecutionEnv.pushExpression(new RoboMatrix4());
    }

    public static void roboMatrix4(RoboValue vector, RoboValue angle) {
        RoboVector3D vectord = (RoboVector3D) vector;
        if(angle instanceof  RoboNumeric){
            RoboDouble angled = (RoboDouble) RoboNumeric.convert((RoboNumeric) angle, Type.Double);
            ExecutionEnv.pushExpression(new RoboMatrix4(vectord.getValue(), angled.getValue()));
            return;
        } else if(angle instanceof RoboQuaternion){
            RoboQuaternion angled = (RoboQuaternion) angle;
            ExecutionEnv.pushExpression(new RoboMatrix4(vectord.getValue(), (Quaternion)angled.getValue()));
            return;
        }
        throw new ExecutionException("Matrix4 constructor wrong.");
    }

    public static void roboMatrix4(RoboValue matrix) {
        if(!(matrix instanceof RoboMatrix)){
            throw new ExecutionException("Matrix4 constructor wrong.");
        }
        RoboMatrix rm = (RoboMatrix) matrix;
        if(rm.getCols() != 4 || rm.getRows() != 4){
            throw new ExecutionException("Matrix dimension false.");
        } else {
            double[] mat = new double[16];
            for(int i = 0; i < 16; i++){
                mat[i] = (double)rm.index(i/4, i%4).getValue();
            }
            ExecutionEnv.pushExpression(new RoboMatrix4(new Matrix4(mat)));
        }
    }
}
