package parser.execution.environment;

import parser.execution.ExecutionException;
import parser.execution.values.RoboValue;
import parser.execution.values.RoboVariable;
import parser.lexical.type.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gregor on 28.08.16..
 * Regulating if the variable is passed by reference or by value.
 */
public class VariableEnv {

    private Map<String, RoboVariable> vars;

    public VariableEnv() {
        vars = new HashMap<>();
    }

    /**
     * @param name
     * @param isConstant
     * @param type
     */
    public void declareVariable(String name, boolean isConstant, Type type) {
        if (vars.get(name) != null) {
            throw new ExecutionException("Variable '" + name + "' already declared!");
        }
        vars.put(name, new RoboVariable(name, isConstant, type));
    }

    /**
     * @param name
     * @return
     */
    public RoboValue getVariableValue(String name) {
        RoboValue val = vars.get(name);
        if (val == null) {
            throw new ExecutionException("Variable, '" + name + "' not declared!");
        }
        return val.duplicate();
    }

    public Type getVariableType(String name){
        RoboValue val = vars.get(name);
        if (val == null) {
            throw new ExecutionException("Variable, '" + name + "' not declared!");
        }
        return val.getType();
    }

    /**
     * @param name
     * @return
     */
    public RoboValue getVariableValueByReference(String name) {
        RoboValue val = vars.get(name);
        if (val == null) {
            throw new ExecutionException("Variable, '" + name + "' not declared!");
        }
        return val;
    }

    /**
     * Method assigns given value val to variable which name is given.
     * Method checks whether variable is reference or not and handles i that way.
     *
     * @param name
     * @param val
     * @param type
     */
    public void defineVariable(String name, RoboValue val, Type type) {
        vars.get(name).setType(type);
        vars.get(name).setValue(val);
    }

    public void removeLoopVariable(String varName) {
        vars.remove(varName);
    }
}
