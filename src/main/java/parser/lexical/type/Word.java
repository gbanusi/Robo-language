package parser.lexical.type;

import parser.lexical.tokenizer.TokenType;
import parser.lexical.tokenizer.Token;

/**
 * Created by gregor on 07.08.16..
 */
public class Word extends Token {

    public String lexeme = "";

    public Word(String s, TokenType tokenType, Object value) {
        super(tokenType, value);
        lexeme = s;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    public static final Word
            and = new Word("&&", TokenType.AND, null),
            or = new Word("||", TokenType.OR, null),
            eq = new Word("==", TokenType.EQ, null),
            le = new Word("<=", TokenType.LE, null),
            ge = new Word(">=", TokenType.GE, null),
            ne = new Word("!=", TokenType.NE, null),
            function = new Word("function", TokenType.FUNCTION, null),
            print = new Word("print", TokenType.PRINT, null),
            _do = new Word("do", TokenType.DO, null),
            _while = new Word("while", TokenType.WHILE, null),
            _break = new Word("break", TokenType.BREAK, null),
            _return = new Word("return", TokenType.RETURN, null),
            _if = new Word("if", TokenType.IF, null),
            _else = new Word("else", TokenType.ELSE, null),
            _const = new Word("const", TokenType.CONST, true),
            _true = new Word("true", TokenType.TRUE, true),
            _false = new Word("false", TokenType.FALSE, false),
            _continue = new Word("continue", TokenType.CONTINUE, false),
            _include = new Word("include", TokenType.INCLUDE, null),
            loop = new Word("loop", TokenType.LOOP, null),
            arrayLength = new Word("arrayLength", TokenType.ARRAY_LENGTH, null),
            matrixLength = new Word("matrixLength", TokenType.MATRIX_LENGTH, null),
            sin = new Word("sin", TokenType.SIN, null),
            cos = new Word("cos", TokenType.COS, null),
            tan = new Word("tan", TokenType.TAN, null),
            ctg = new Word("ctg", TokenType.CTG, null);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return !(lexeme != null ? !lexeme.equals(word.lexeme) : word.lexeme != null);

    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public int hashCode() {
        return lexeme != null ? lexeme.hashCode() : 0;
    }
}
