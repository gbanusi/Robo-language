package parser.lexical.type;

import parser.lexical.tokenizer.TokenType;

/**
 * Created by gregor on 06.09.16..
 */
public class TypeFuncArray extends Type implements ArraysType {

    private Type type;


    // TODO-2 unify with Integer ... dim?
    public TypeFuncArray(Type type) {
        super("array", TokenType.BASIC, null, 0);
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TypeFuncArray that = (TypeFuncArray) o;

        return !(getType() != null ? !getType().equals(that.getType()) : that.getType() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "array of " + type.getLexeme();
    }
}