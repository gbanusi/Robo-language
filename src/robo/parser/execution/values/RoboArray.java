package robo.parser.execution.values;

import robo.parser.execution.ExecutionException;
import robo.parser.lexical.Type;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 20.08.16..
 */
public class RoboArray extends RoboValue {

    private Type type = Type.Array;

    private List<RoboValue> value;

    public RoboArray(List<RoboValue> value) {
        this.value = value;
    }


    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue add(RoboValue rv) {
        throw new ExecutionException("'+' operator not supported for array!");
    }

    @Override
    public RoboValue sub(RoboValue rv) {
        throw new ExecutionException("'-' operator not supported for array!");
    }

    @Override
    public RoboValue div(RoboValue rv) {
        throw new ExecutionException("'/' operator not supported for array!");
    }

    @Override
    public RoboValue mult(RoboValue rv) {
        throw new ExecutionException("'*' operator not supported for array!");
    }

    @Override
    public RoboValue and(RoboValue rv) {
        throw new ExecutionException("'&&' operator not supported for array!");
    }

    @Override
    public RoboValue or(RoboValue rv) {
        throw new ExecutionException("'||' operator not supported for array!");
    }

    @Override
    public RoboValue equal(RoboValue rv) {
        throw new ExecutionException("'==' operator not supported for array!");
    }

    @Override
    public RoboValue notEqual(RoboValue rv) {
        throw new ExecutionException("'!=' operator not supported for array!");
    }

    @Override
    public RoboValue lowerThan(RoboValue rv) {
        throw new ExecutionException("'<' operator not supported for array!");
    }

    @Override
    public RoboValue lowerEqual(RoboValue rv) {
        throw new ExecutionException("'<=' operator not supported for array!");
    }

    @Override
    public RoboValue greaterThan(RoboValue rv) {
        throw new ExecutionException("'>' operator not supported for array!");
    }

    @Override
    public RoboValue greaterEqual(RoboValue rv) {
        throw new ExecutionException("'>=' operator not supported for array!");
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        Integer i;
        if(indexes.size() == 0){
            throw new ExecutionException("Multidimensional (n > 2) arrays not supported and index must be provided!");
        } else if(indexes.size() == 1){
            i = (Integer) indexes.get(0).getValue();
            return value.get(i);
        } else {
            i = (Integer) indexes.get(0).getValue();
            if(! (value.get(i) instanceof RoboArray)){
                throw new ExecutionException("Cannot index non array type!");
            }
            return value.get(i).index(indexes.subList(1, indexes.size()));
        }
    }

    @Override
    public RoboValue unMinus() {
        throw new ExecutionException("'-' operator not supported for array!");
    }

    @Override
    protected void setValue(RoboValue rv) {
        value = new LinkedList<RoboValue>((List<RoboValue>)rv.getValue());
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
