package parser.execution.values;

import parser.execution.ExecutionException;
import parser.lexical.Type;
import parser.lexical.TypeArray;
import parser.lexical.TypeMatrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gregor on 20.08.16..
 * TODO-0 reorganize with arrays everything
 */
public class RoboMatrix extends RoboArrays {

    private RoboValue[][] value;

    private TypeMatrix type;

    private Integer rows;

    private Integer cols;


    // TODO-2 implement withput list?
    public RoboMatrix(List<RoboValue> value, Type type) {
        this.type = (TypeMatrix) type;
        rows = this.type.getRows();
        cols = this.type.getCols();
        this.value = new RoboValue[rows][];
        for(int i = 0; i < rows; i++){
            this.value[i] = new RoboValue[cols];
            RoboArray ra = (RoboArray) value.get(i);
            this.value[i] = (RoboValue[]) (ra.getValue());
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public RoboValue index(List<RoboValue> indexes) {
        if(indexes.size() != 2){
            throw new ExecutionException("Matrix index must have 2 elements!");
        } else {
            int rows = (int) indexes.get(0).getValue();
            int cols = (int) indexes.get(1).getValue();
            return value[rows][cols];
        }
    }

    @Override
    protected void setValue(RoboValue rv) {
        if(! (rv instanceof RoboMatrix)){
            throw new ExecutionException("Array variable must be defined with an array!");
        }
        if(rv.getType() != type){
            throw new ExecutionException("Array are not the same dimension or type");
        }
        this.value = ((RoboMatrix) rv).value;
    }

    @Override
    public RoboValue duplicate() {
        List<RoboValue> row = new LinkedList<>();
        for(int i = 0; i < rows; i++){
            List<RoboValue> col = new LinkedList<>();
            for(int j = 0; j < cols; j++){
                col.add(value[i][j]);
            }
            row.add(new RoboArray(col, new TypeArray(type.getType(), col.size())));
        }
        return new RoboMatrix(row, type);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }

}
