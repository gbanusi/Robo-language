package robo.parser.lexical;

/**
 * All peek types
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

    /** OPERATIONS **/
    OP_PLUS("OPERATOR"),
    OP_MINUS("OPERATOR"),
    OP_DIV("OPERATOR"),
    OP_MULT("OPERATOR"),
    UN_MINUS("OPERATOR"),
    ASSIGN("OPERATOR"),

    /** KEYWORDS **/
    PRINT("KEYWORD"),
    IDENT("KEYWORD"),
    KEYWORD("KEYWORD"),
    FUNCTION("KEYWORD"),
    BREAK("KEYWORD"),
    DO("KEYWORD"),
    WHILE("KEYWORD"),
    ELSE("KEYWORD"),
    IF("KEYWORD"),

    /** TYPES **/
    BASIC("TYPE"),
    TRUE("TYPE"),
    FALSE("TYPE"),
    CONSTANT("TYPE"),
    VECTOR_CONSTANT("TYPE"),

    /** LOGICAL OP **/
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
