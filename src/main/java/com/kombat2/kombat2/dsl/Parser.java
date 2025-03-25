package com.kombat2.kombat2.dsl;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token consume() {
        return tokens.get(pos++);
    }

    private boolean match(TokenType type, String text) {
        if (pos < tokens.size() && peek().type == type && peek().text.equals(text)) {
            consume();
            return true;
        }
        return false;
    }

    public List<Statement> parseStrategy() {
        List<Statement> statements = new ArrayList<>();
        while (peek().type != TokenType.EOF) {
            statements.add(parseStatement());
        }
        return statements;
    }

    private Statement parseStatement() {
        Token token = peek();
        if (token.type == TokenType.BRACE_OPEN) {
            return parseBlockStatement();
        }
        if (token.type == TokenType.RESERVED && token.text.equals("if")) {
            return parseIfStatement();
        }
        if (token.type == TokenType.RESERVED && token.text.equals("while")) {
            return parseWhileStatement();
        }
        return parseCommand();
    }

    private Statement parseBlockStatement() {
        consume(); // consume '{'
        List<Statement> stmts = new ArrayList<>();
        while (peek().type != TokenType.BRACE_CLOSE && peek().type != TokenType.EOF) {
            stmts.add(parseStatement());
        }
        match(TokenType.BRACE_CLOSE, "}");
        return new BlockStatement(stmts);
    }

    private Statement parseIfStatement() {
        consume(); // consume 'if'
        match(TokenType.PAREN_OPEN, "(");
        Expression condition = parseExpression();
        match(TokenType.PAREN_CLOSE, ")");
        match(TokenType.RESERVED, "then");
        Statement thenStmt = parseStatement();
        match(TokenType.RESERVED, "else");
        Statement elseStmt = parseStatement();
        return new IfStatement(condition, thenStmt, elseStmt);
    }

    private Statement parseWhileStatement() {
        consume(); // consume 'while'
        match(TokenType.PAREN_OPEN, "(");
        Expression condition = parseExpression();
        match(TokenType.PAREN_CLOSE, ")");
        Statement body = parseStatement();
        return new WhileStatement(condition, body);
    }

    private Statement parseCommand() {
        Token token = peek();
        if (token.type == TokenType.IDENTIFIER) {
            Token next = tokens.get(pos + 1);
            if (next.type == TokenType.OPERATOR && next.text.equals("=")) {
                return parseAssignmentStatement();
            }
        }
        if (token.type == TokenType.RESERVED && token.text.equals("done")) {
            consume();
            return new DoneCommand();
        }
        if (token.type == TokenType.RESERVED && token.text.equals("move")) {
            return parseMoveCommand();
        }
        if (token.type == TokenType.RESERVED && token.text.equals("shoot")) {
            return parseShootCommand();
        }
        consume();
        return new DoneCommand();
    }

    private Statement parseAssignmentStatement() {
        Token identifierToken = consume(); // <identifier>
        match(TokenType.OPERATOR, "=");
        Expression expr = parseExpression();
        return new AssignmentStatement(identifierToken.text, expr);
    }

    private Statement parseMoveCommand() {
        consume(); // consume 'move'
        Token dirToken = consume(); // direction
        return new MoveCommand(dirToken.text);
    }

    private Statement parseShootCommand() {
        consume(); // consume 'shoot'
        Token dirToken = consume(); // direction
        Expression expenditure = parseExpression();
        return new ShootCommand(dirToken.text, expenditure);
    }

    private Expression parseExpression() {
        Expression left = parseTerm();
        while (peek().type == TokenType.OPERATOR &&
                (peek().text.equals("+") || peek().text.equals("-"))) {
            char op = consume().text.charAt(0);
            Expression right = parseTerm();
            left = new BinaryExpression(left, op, right);
        }
        return left;
    }

    private Expression parseTerm() {
        Expression left = parseFactor();
        while (peek().type == TokenType.OPERATOR &&
                (peek().text.equals("*") || peek().text.equals("/") || peek().text.equals("%"))) {
            char op = consume().text.charAt(0);
            Expression right = parseFactor();
            left = new BinaryExpression(left, op, right);
        }
        return left;
    }

    private Expression parseFactor() {
        Expression left = parsePower();
        while (peek().type == TokenType.OPERATOR && peek().text.equals("^")) {
            consume(); // '^'
            Expression right = parseFactor();
            left = new BinaryExpression(left, '^', right);
        }
        return left;
    }

    private Expression parsePower() {
        Token token = peek();
        switch (token.type) {
            case NUMBER:
                consume();
                return new NumberExpression(Long.parseLong(token.text));
            case IDENTIFIER:
                consume();
                return new VariableExpression(token.text);
            case PAREN_OPEN:
                consume();
                Expression expr = parseExpression();
                match(TokenType.PAREN_CLOSE, ")");
                return expr;
            case RESERVED:
                return parseInfoExpression();
            default:
                consume();
                return new NumberExpression(0);
        }
    }

    private Expression parseInfoExpression() {
        Token infoToken = consume(); // ally, opponent, or "nearby"
        if (infoToken.text.equals("nearby")) {
            Token dirToken = consume(); // direction
            return new InfoExpression("nearby", dirToken.text);
        } else {
            return new InfoExpression(infoToken.text, null);
        }
    }
}
