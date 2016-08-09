package robo.parser.lexical;

public class Type extends Word {

    /**
     * Used for storage allocation.
     */
    public int width = 0;

    public Type(String s, TokenType tokenType, Object value, int w) {
        super(s, tokenType, value);
        width = w;
    }

    public static final Type
            Int   = new Type("int", TokenType.BASIC, null, 4),
            Double = new Type("double", TokenType.BASIC, null, 8),
            Char  = new Type("char", TokenType.BASIC, null, 1),
            Bool  = new Type("bool", TokenType.BASIC, null, 1);

    public static boolean numeric(Type p){
        if( p == Type.Char || p == Type.Int || p == Type.Double){
            return true;
        }
        return false;
    }

    /**
     * Get the most specific common type of parameters p1 and p2.
     * @param p1 is the first variable type.
     * @param p2 is the second variable type.
     * @return the most specific common type for given two variables
     */
    public static Type max(Type p1, Type p2){
        if( ! numeric(p1) || ! numeric(p2)){
            return null;
        } else if(p1 == Type.Double || p2 == Type.Double){
            return Type.Double;
        } else if(p1 == Type.Int || p2 == Type.Int) {
            return Type.Int;
        } else {
            return Type.Char;
        }
    }




}