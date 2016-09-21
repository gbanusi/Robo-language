package parser.execution.environment.built.in;

import parser.execution.ExecutionException;
import parser.execution.environment.ExecutionEnv;
import parser.execution.values.RoboDouble;
import parser.execution.values.RoboNumeric;
import parser.execution.values.RoboValue;
import parser.execution.values.RoboVector3D;
import parser.lexical.type.Type;

/**
 * Created by gregor on 21.09.16..
 */
public class RoboConstructors {

    /**
     * Variables reversed because of using stack.
     * @param z
     * @param y
     * @param x
     */
    public static void roboVector3D(RoboValue z, RoboValue y, RoboValue x) {
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
}
