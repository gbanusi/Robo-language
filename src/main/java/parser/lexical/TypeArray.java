package parser.lexical;

/**
 * Created by gregor on 06.09.16..
 */
public class TypeArray extends Type {

    private Type type;

    private Integer length;

    public TypeArray(Type type, Integer length) {
        super("array", TokenType.BASIC, null, -1);
        this.type = type;
        this.length = length;
        this.width = type.getWidth();
        width = width * length;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TypeArray typeArray = (TypeArray) o;

        if (!getType().equals(typeArray.getType()) && max(getType(), typeArray.getType()) == null) return false;
        return getLength().equals(typeArray.getLength());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getLength().hashCode();
        return result;
    }

    @Override
    public java.lang.String toString() {
        return "array of " + type.getLexeme();
    }
}