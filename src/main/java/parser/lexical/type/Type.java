package parser.lexical.type;

import parser.execution.values.*;
import parser.lexical.tokenizer.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Type extends Word {

    /**
     * Used for storage allocation.
     */
    public int width = 0;

    public Type(String s, TokenType tokenType, Object value, int w) {
        super(s, tokenType, value);
        width = w;
    }

    public static final Type Int;
    public static final Type Double;
    public static final Type Bool;
    public static final Type String;
    public static final Type Line;
    public static final Type Frame;
    public static final Type Quaternion;
    public static final Type Vector3d;
    public static final Type Matrix4;
    public static final Type Unknown;

    static {
        Int = new Type("int", TokenType.BASIC, null, 4);
        Double = new Type("double", TokenType.BASIC, null, 8);
        Bool = new Type("bool", TokenType.BASIC, null, 1);
        String = new Type("string", TokenType.BASIC, null, 1);
        Line = new Type("line", TokenType.BASIC, null, 1);
        Frame = new Type("frame", TokenType.BASIC, null, 1);
        Quaternion = new Type("quaternion", TokenType.BASIC, null, 1);
        Vector3d = new Type("vector3d", TokenType.BASIC, null, 1);
        Matrix4 = new Type("matrix4", TokenType.BASIC, null, 1);
        Unknown = new Type("unknown", TokenType.BASIC, null, 1);
    }


    public static Map<Type, RoboValue> defaultVals;

    static {
        defaultVals = new HashMap<>();
        defaultVals.put(Int, new RoboInteger(0));
        defaultVals.put(Double, new RoboDouble(0));
        defaultVals.put(Bool, new RoboBool(true));
        defaultVals.put(String, new RoboString(""));
    }

    public static boolean numeric(Type p) {
        if (p == Type.Int || p == Type.Double) {
            return true;
        }
        return false;
    }

    public static RoboValue getDefaultValue(Type type) {
        return defaultVals.get(type) != null ? defaultVals.get(type).duplicate() : RoboNull.roboNull;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Get the most specific common value of parameters p1 and p2.
     *
     * @param p1 is the first variable value.
     * @param p2 is the second variable value.
     * @return the most specific common value for given two variables
     */
    public static Type max(Type p1, Type p2) {
        if (!numeric(p1) || !numeric(p2)) {
            return null;
        } else if (p1 == Type.Double || p2 == Type.Double) {
            return Type.Double;
        } else {
            return Type.Int;
        }
    }
}