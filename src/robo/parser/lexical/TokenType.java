package robo.parser.lexical;

/**
 * All currToken types
 */
public enum TokenType {

    /** END OF FILE **/
    EOF,

    /** HELPERS **/
    SEMICOLON,
    COMMA,

    /** BRACKETS **/
    OPEN_PARENTHESES,
    CLOSED_PARENTHESES,
    OPEN_CURLY,
    CLOSED_CURLY,

    /** OPERATIONS **/
    OP_PLUS,
    OP_MINUS,
    OP_DIV,
    OP_MULT,
    UN_MINUS,
    ASSIGN,

    /** KEYWORDS **/
    PRINT,
    IDENT,
    KEYWORD,
    FUNCTION,
    BREAK,
    DO,
    WHILE,
    ELSE,
    IF,

    /** TYPES **/
    BASIC,
    TRUE,
    FALSE,
    CONSTANT,
    VECTOR_CONSTANT,

    /** LOGICAL OP **/
    AND,
    OR,

    /** RELATIONS **/
    LE,
    NE,
    EQ,
    GE,
    LT,
    GT
}
