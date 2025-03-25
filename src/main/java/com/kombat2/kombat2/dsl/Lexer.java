package com.kombat2.kombat2.dsl;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos;
    private final int length;

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
        this.length = input.length();
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < length) {
            char c = peek();
            if (Character.isWhitespace(c)) {
                advance();
            } else if (Character.isDigit(c)) {
                tokens.add(lexNumber());
            } else if (Character.isLetter(c)) {
                tokens.add(lexIdentifierOrReserved());
            } else if (c == '(') {
                tokens.add(new Token(TokenType.PAREN_OPEN, "("));
                advance();
            } else if (c == ')') {
                tokens.add(new Token(TokenType.PAREN_CLOSE, ")"));
                advance();
            } else if (c == '{') {
                tokens.add(new Token(TokenType.BRACE_OPEN, "{"));
                advance();
            } else if (c == '}') {
                tokens.add(new Token(TokenType.BRACE_CLOSE, "}"));
                advance();
            } else if ("=+-*/%^".indexOf(c) != -1) {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(c)));
                advance();
            } else {
                // Skip unknown characters.
                advance();
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token lexNumber() {
        int start = pos;
        while (pos < length && Character.isDigit(peek())) {
            advance();
        }
        return new Token(TokenType.NUMBER, input.substring(start, pos));
    }

    private Token lexIdentifierOrReserved() {
        int start = pos;
        while (pos < length && (Character.isLetterOrDigit(peek()) || peek() == '_')) {
            advance();
        }
        String text = input.substring(start, pos);
        if (isReserved(text)) {
            return new Token(TokenType.RESERVED, text);
        }
        return new Token(TokenType.IDENTIFIER, text);
    }

    private boolean isReserved(String text) {
        switch (text) {
            case "if": case "then": case "else": case "while":
            case "move": case "shoot": case "done":
            case "up": case "down": case "upleft": case "upright":
            case "downleft": case "downright":
            case "ally": case "opponent": case "nearby":
                return true;
            default:
                return false;
        }
    }

    private char peek() {
        return input.charAt(pos);
    }

    private void advance() {
        pos++;
    }
}
