package parser.lexical;

/**
 * Created by gregor on 06.09.16..
 */
public class TypeMatrix extends Type {

    private Type type;

    private Integer rows;

    private Integer cols;

    public TypeMatrix(Type type, Integer rows, Integer cols) {
        super("matrix", TokenType.BASIC, null, -1);
        this.type = type;
        this.rows = rows;
        this.cols = cols;
        this.width = type.getWidth() * rows * cols;

    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getCols() {
        return cols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TypeMatrix that = (TypeMatrix) o;

        if (!getType().equals(that.getType()) && max(getType(), that.getType()) == null) return false;
        if (!getRows().equals(that.getRows())) return false;
        return getCols().equals(that.getCols());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getRows().hashCode();
        result = 31 * result + getCols().hashCode();
        return result;
    }

    @Override
    public java.lang.String toString() {
        return "matrix of " + type.getLexeme();
    }
}