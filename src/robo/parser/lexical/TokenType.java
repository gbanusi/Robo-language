package robo.parser.lexical;

/**
 * All peek value
 */
public enum TokenType {

    /** END OF FILE **/
    EOF("END"),

    /** HELPERS **/
    SEMICOLON("HELPER"),
    COMMA("HELPER"),

    /** BRACKETS **/
    OPEN_PARENTHESES("BRACKET"),
    CLOSED_PARENTHESES("BRACKET"),
    OPEN_CURLY("BRACKET"),
    CLOSED_CURLY("BRACKET"),
    OPEN_SQUARE("BRACKET"),
    CLOSED_SQUARE("BRACKET"),

    /** OPERATIONS **/
    OP_PLUS("OPERATOR"),
    OP_MINUS("OPERATOR"),
    OP_DIV("OPERATOR"),
    OP_MULT("OPERATOR"),
    UN_MINUS("OPERATOR"),
    UN_REFERENCE("OPERATOR"),
    ASSIGN("OPERATOR"),

    /** KEYWORDS **/
    PRINT("KEYWORD"),
    FUNCTION("KEYWORD"),
    BREAK("KEYWORD"),
    DO("KEYWORD"),
    WHILE("KEYWORD"),
    ELSE("KEYWORD"),
    IF("KEYWORD"),
    RETURN("KEYWORD"),
    CONST("KEYWORD"),
    CONTINUE("KEYWORD"),

    /** TYPES **/
    IDENT("TYPE"),
    BASIC("TYPE"),
    TRUE("TYPE"),
    FALSE("TYPE"),
    CONSTANT("TYPE"),
    VECTOR_CONSTANT("TYPE"),

    /** LOGICAL OP **/
    // TODO NOT (!) TO AD
    AND("LOGOP"),
    OR("LOGOP"),

    /** RELATIONS **/
    LE("RELATION"),
    NE("RELATION"),
    EQ("RELATION"),
    GE("RELATION"),
    LT("RELATION"),
    GT("RELATION");

    private final String code;

    TokenType(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
