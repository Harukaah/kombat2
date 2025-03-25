package com.kombat2.kombat2.dsl;

public enum TokenType {
    IDENTIFIER,
    NUMBER,
    RESERVED,      // e.g., if, then, else, while, move, shoot, done, up, down, etc.
    OPERATOR,      // e.g., =, +, -, *, /, %, ^
    PAREN_OPEN,
    PAREN_CLOSE,
    BRACE_OPEN,
    BRACE_CLOSE,
    EOF
}
