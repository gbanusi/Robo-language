package parser.execution.visitor;

import parser.execution.ExecutionException;
import parser.execution.values.RoboNumeric;
import parser.execution.values.RoboValue;
import parser.lexical.type.Type;
import parser.lexical.type.TypeFuncArray;

/**
 * Created by gregor on 14.09.16..
 */
public class TypeCheckingHelper {

    /**
     * Function used for checking if we got the variable type we expected from a concrete statements,
     * cause everything is predefined. Some exceptions occur like we don't need to know for print var
     * what type we need or for function array/matrix what it dimension is.
     * @param res is the result of expression visitor.
     * @param type is the type we expected, with some exceptions.
     * @return the origin or converted value if the conversion went successful.
     */
    public static RoboValue convertType(RoboValue res, Type type) {
        if(type.equals(Type.Unknown)){
            return res;
        } else if(type instanceof TypeFuncArray){
            return res;
        } else if(! type.equals(res.getType())){
            if(Type.max(type, res.getType()) != null){
                return RoboNumeric.convert((RoboNumeric) res, type);
            }
            throw new ExecutionException("Variable of type '" + type + "' cannot be assigned with value of type '" + res.getType() + "' !"  );
        }
        return res;
    }
}
