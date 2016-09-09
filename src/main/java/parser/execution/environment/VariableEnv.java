package parser.execution.environment;

import parser.execution.ExecutionException;
import parser.execution.values.RoboValue;
import parser.execution.values.RoboVariable;
import parser.lexical.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gregor on 28.08.16..
 *
 */
public class VariableEnv {

    private Map<String, RoboVariable> vars;

    public VariableEnv(){
        vars = new HashMap<>();
    }

    /**
     *
     * @param name
     * @param isConstant
     * @param type
     */
    public void declareVariable(String name, boolean isConstant, Type type) {
        if(vars.get(name) != null){
            throw new ExecutionException("Variable '" + name + "' already declared!");
        }
        vars.put(name, new RoboVariable(name, isConstant, type));
    }

    /**
     *
     * @param name
     * @return
     */
    public RoboValue getVariableValue(String name) {
        RoboValue val = vars.get(name);
        if(val == null){
            throw new ExecutionException("Variable, '" + name + "' not declared!");
        }
        return val.duplicate();
    }

    /**
     *
     * @param name
     * @return
     */
    public RoboValue getVariableValueByReference(String name) {
        RoboValue val = vars.get(name);
        if(val == null){
            throw new ExecutionException("Variable, '" + name + "' not declared!");
        }
        return val;
    }

    /**
     * Method assigns given value val to variable which name is given.
     * Method checks whether variable is reference or not and handles i that way.
     *  @param name
     * @param val
     * @param type
     */
    public void defineVariable(String name, RoboValue val, Type type) {
        if (! vars.get(name).getType().equals(type)){
            throw new ExecutionException("Assigning '" + type + "' to variable '" + name + "' with type '" + vars.get(name).getType() + "'.");
        }
        vars.get(name).setValue(val);
    }
}
