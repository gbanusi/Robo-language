package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;
import parser.lexical.TypeArray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 20.08.16..
 * TODO-0 reorganize with arrays everything
 */
public class RoboArray extends RoboArrays {

    private RoboValue[] value;

    private TypeArray type;

    private Integer length;


    // TODO-2 implement withput list?
    public RoboArray(List<RoboValue> value, Type type) {
        this.type = (TypeArray) type;
        length = this.type.getLength();
        this.value = new RoboValue[length];
        for(int i = 0; i < length; i++){
            this.value[i] = value.get(i);
        }
    }

    @Override
    public Object getValue(){
        return value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        if(indexes.size() > 2){
            throw new ExecutionException("Indexes can have 2 elements maximum!");
        } else if (indexes.size() > 1 && (int)indexes.get(0).getValue() != 1){
            throw new ExecutionException("Arrays have only one row, remember indexes are ordered in RS format (RS -> row, column)!");
        } else if(indexes.size() > 1){
            int cols = (int) indexes.get(1).getValue();
            return value[cols];
        } else {
            int cols = (int) indexes.get(0).getValue();
            return value[cols];
        }
    }

    @Override
    protected void setValue(RoboValue rv) {
        if(! (rv instanceof RoboArray)){
            throw new ExecutionException("Array variable must be defined with an array!");
        }
        if(rv.getType() != type){
            throw new ExecutionException("Array are not the same dimension or type");
        }
        this.value = ((RoboArray) rv).value;
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
    public String toString() {
        return Arrays.toString(value);
    }

}
