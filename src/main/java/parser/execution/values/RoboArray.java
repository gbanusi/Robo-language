package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 20.08.16..
 */
public class RoboArray extends RoboValue {

    private List<RoboValue> value;

    private Type type;

    public RoboArray(List<RoboValue> value, Type type) {
        this.value = value;
        this.type = type;
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
    public RoboValue not() {
        throw new ExecutionException("'!' operator not supported for array!");
    }

    @Override
    protected void setValue(RoboValue rv) {
        if(! (rv instanceof RoboArray)){
            throw new ExecutionException("Array variable must be defined with an array!");
        }
        if(rv.getType() != type){
            throw new ExecutionException("Array are not the same dimension or type");
        }
        value = (List<RoboValue>) ((RoboArray) rv).getValue();
    }

    @Override
    public RoboValue duplicate() {
        List<RoboValue> vals = new LinkedList<>();
        for(RoboValue rv : value){
            vals.add(rv.duplicate());
        }
        return new RoboArray(vals, type);
    }

    @Override
    public Object getValue() {
        return this.duplicate();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
