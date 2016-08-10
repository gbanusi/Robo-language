package robo.parser.lexical;

import robo.parser.Vector;

import java.util.*;


public class Tokenizer {


    private char[] data;

    private int curPos;

    private Token currentToken;


    private static final Map<Character, TokenType> charMapper;

    static {
        charMapper = new HashMap<>();
        charMapper.put(Character.valueOf(';'), TokenType.SEMICOLON);
        charMapper.put(Character.valueOf('+'), TokenType.OP_PLUS);
        charMapper.put(Character.valueOf('-'), TokenType.OP_MINUS);
        charMapper.put(Character.valueOf('*'), TokenType.OP_MULT);
        charMapper.put(Character.valueOf('/'), TokenType.OP_DIV);
        charMapper.put(Character.valueOf(','), TokenType.COMMA);
        charMapper.put(Character.valueOf('='), TokenType.ASSIGN);
        charMapper.put(Character.valueOf('('), TokenType.OPEN_PARENTHESES);
        charMapper.put(Character.valueOf(')'), TokenType.CLOSED_PARENTHESES);
        charMapper.put(Character.valueOf('{'), TokenType.OPEN_CURLY);
        charMapper.put(Character.valueOf('}'), TokenType.CLOSED_CURLY);
        charMapper.put(Character.valueOf('<'), TokenType.LT);
        charMapper.put(Character.valueOf('>'), TokenType.GT);
    }

    private static final Map<String, Word> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("function", Word.function);
        keywords.put("break", Word._break);
        keywords.put("do", Word._do);
        keywords.put("while", Word._while);
        keywords.put("print", Word.print);
        keywords.put("if", Word._if);
        keywords.put("else", Word._else);
        keywords.put("false", Word._false);
        keywords.put("true", Word._true);
        keywords.put("int", Type.Int);
        keywords.put("double", Type.Double);
        keywords.put("char", Type.Char);
        keywords.put("bool", Type.Bool);

    }

    public Tokenizer(String program) {
        data = program.toCharArray();
        curPos = 0;
        extractNextToken();
    }

    /**
     * Metoda dohvaća trenutni peek. Metoda se može zvati više puta i uvijek
     * će vratiti isti peek, sve dok se ne zatraži izlučivanje sljedećeg
     * tokena.
     *
     * @return trenutni peek
     */
    public Token getCurrentToken() {
        return currentToken;
    }

    /**
     * Metoda izlučuje sljedeći peek, postavlja ga kao trenutnog i odmah ga
     * i vraća.
     *
     * @throws TokenizerException ako dođe do problema pri tokenizaciji
     */
    public Token nextToken() {
        extractNextToken();
        return getCurrentToken();
    }

    /**
     * Metoda izlučuje sljedeći peek iz izvornog koda.
     *
     * @throws TokenizerException ako dođe do pogreške pri tokenizaciji
     */
    private void extractNextToken() {
        // Ako je već prije utvrđen kraj, ponovni poziv metode je greška:
        if (currentToken != null && currentToken.getTokenType() == TokenType.EOF) {
            throw new TokenizerException("No tokens available.");
        }

        // Inače preskoči praznine:
        skipBlanks();

        // Ako više nema znakova, generiraj peek za kraj izvornog koda programa
        if (curPos >= data.length) {
            currentToken = new Token(TokenType.EOF, null);
            return;
        }

        // Provjeri je li neka posebna riječ koja počinje sa jednim od zauzetih karaktera
        if (curPos < data.length - 2) {
            switch (data[curPos]) {
                case '&':
                    if (data[curPos + 1] == '&') {
                        curPos += 2;
                        currentToken = new Token(Word.and.getTokenType(), null);
                        return;
                    }
                    break;
                case '|':
                    if (data[curPos + 1] == '|') {
                        curPos += 2;
                        currentToken = new Token(Word.or.getTokenType(), null);
                        return;
                    }
                    break;
                case '=':
                    if (data[curPos + 1] == '=') {
                        curPos += 2;
                        currentToken = new Token(Word.eq.getTokenType(), null);
                        return;
                    }
                    break;
                case '!':
                    if (data[curPos + 1] == '=') {
                        curPos += 2;
                        currentToken = new Token(Word.ne.getTokenType(), null);
                        return;
                    }
                    break;
                case '<':
                    if (data[curPos + 1] == '=') {
                        curPos += 2;
                        currentToken = new Token(Word.le.getTokenType(), null);
                        return;
                    }
                    break;
                case '>':
                    if (data[curPos + 1] == '=') {
                        curPos += 2;
                        currentToken = new Token(Word.ge.getTokenType(), null);
                        return;
                    }
                    break;
            }
        }

        // Vidi je li trenutni znak neki od onih koji direktno generiraju peek:
        TokenType mappedType = charMapper.get(Character.valueOf(data[curPos]));
        if (mappedType != null) {
            // provjera je li unarny ili binarny minus
            if (!currentToken.getTokenType().equals(TokenType.IDENT)
                    && !currentToken.getTokenType().equals(TokenType.CONSTANT)
                    && !currentToken.getTokenType().equals(TokenType.CLOSED_PARENTHESES)
                    && mappedType.equals(TokenType.OP_MINUS)) {
                currentToken = new Token(TokenType.UN_MINUS, null);
            } else {
                currentToken = new Token(mappedType, null);
            }
            // Postavi trenutnu poziciju na sljedeći znak:
            curPos++;
            return;
        }

        // Ako znak direktno ne generira peek, provjeri što je.
        if(Character.isLetter(data[curPos])){


        }

        // Ako znak direktno ne generira peek, provjeri što je.
        if (Character.isLetter(data[curPos])) {
            StringBuffer b = new StringBuffer();
            do{
                b.append(data[curPos]); curPos++;
            } while( Character.isLetterOrDigit(data[curPos]) && curPos < data.length);
            String s = b.toString();
            if (keywords.containsKey(s)) {
                currentToken = new Token(keywords.get(s).getTokenType(), keywords.get(s));
            } else {
                currentToken = new Token(TokenType.IDENT, s);
            }
            return;
        }

        if(Character.isDigit(data[curPos])){
            int v = 0;
            do {
                v = v*10 + Character.digit(data[curPos], 10); curPos++;
            } while (Character.isDigit(data[curPos]));

            if(data[curPos] != '.') {
                currentToken = new Token(TokenType.CONSTANT, Integer.valueOf(v));
                return;
            }
            float x = v; float d = 10;
            for(;;){
                curPos++;
                if(!Character.isDigit(data[curPos])){
                    break;
                }
                x = x + Character.digit(data[curPos], 10)/d; d *= 10;
            }
            currentToken = new Token(TokenType.CONSTANT, Double.valueOf(v));

        }

        // Ako pak imamo početak vektorske konstante:
        if (data[curPos] == '[') {
            curPos++;
            skipBlanks();
            List<Double> components = new ArrayList<>();
            components.add(extractNumber());
            while (true) {
                skipBlanks();
                if (curPos >= data.length) throw new TokenizerException("Invalid vector constant.");
                if (data[curPos] == ']') {
                    curPos++;
                    break;
                }
                if (data[curPos] != ',') throw new TokenizerException("Invalid vector constant.");
                curPos++;
                skipBlanks();
                components.add(extractNumber());
            }
            double[] values = new double[components.size()];
            for (int i = 0, n = components.size(); i < n; i++) {
                values[i] = components.get(i).doubleValue();
            }
            currentToken = new Token(TokenType.VECTOR_CONSTANT, new Vector(values));
            return;
        }

        // Inače nije ništa što razumijemo:
        throw new TokenizerException("Invalid character found: '" + data[curPos] + "'.");
    }

    /**
     * Metoda izlučuje decimalni broj u formatu predznak, cijeli dio, opcionalna točka i decimalni dio.
     *
     * @return decimalni broj zapisan u izvornom kodu programa na trenutnoj poziciji
     */
    private Double extractNumber() {
        if (curPos >= data.length) throw new TokenizerException("Invalid vector constant.");

        // Zapamti početak:
        int startIndex = curPos;

        // Pređi preko cijelobrojnog dijela:
        while (curPos < data.length && Character.isDigit(data[curPos])) {
            curPos++;
        }

        // Ako smo došli do decimalne točke:
        if (curPos < data.length && data[curPos] == '.') {
            curPos++;
            // Pređi preko decimalnog dijela:
            while (curPos < data.length && Character.isDigit(data[curPos])) {
                curPos++;
            }
        }

        // Zapamti kraj i dohvati prihvaćeni dio kao string:
        int endIndex = curPos;
        String value = new String(data, startIndex, endIndex - startIndex);

        // Ako broj nema znamenaka ili ima samo točku, baci iznimku:
        if (endIndex == startIndex || (endIndex == startIndex + 1 && data[startIndex] == '.')) {
            throw new TokenizerException("Invalid decimal number: '" + value + "'.");
        }

        // Inače konvertiraj i vrati broj:
        double d = Double.parseDouble(value);
        return Double.valueOf(d);
    }

    /**
     * Metoda pomiče kazaljku trenutnog znaka tako da preskače sve prazne znakove
     * (razmaci, prelasci u novi red, tabulatori).
     */
    private void skipBlanks() {
        while (curPos < data.length) {
            char c = data[curPos];
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                curPos++;
                continue;
            }
            break;
        }
    }

}
