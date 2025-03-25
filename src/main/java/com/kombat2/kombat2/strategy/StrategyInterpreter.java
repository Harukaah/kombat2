package com.kombat2.kombat2.strategy;

import com.kombat2.kombat2.dsl.*;
import com.kombat2.kombat2.model.Board;
import com.kombat2.kombat2.model.Minion;
import com.kombat2.kombat2.model.Player;

import java.util.List;

public class StrategyInterpreter {
    private Minion minion;
    private Board board;
    private Player player;

    public StrategyInterpreter(Minion minion, Board board, Player player) {
        this.minion = minion;
        this.board = board;
        this.player = player;
    }

    public void execute() {
        String script = minion.getStrategy();
        if (script == null || script.trim().isEmpty()) {
            return;
        }

        // Lexical analysis.
        Lexer lexer = new Lexer(script);
        List<Token> tokens = lexer.tokenize();

        // Parsing.
        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parseStrategy();

        // Create the context and initialize special variables.
        Context context = new Context();
        context.assignVariable("row", minion.getRow());
        context.assignVariable("col", minion.getCol());
        context.assignVariable("budget", (long) player.getBudget());

        // Execute each statement.
        for (Statement stmt : statements) {
            if (context.isDone()) break;
            stmt.execute(context);
        }
    }
}
