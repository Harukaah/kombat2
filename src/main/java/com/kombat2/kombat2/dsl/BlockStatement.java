package com.kombat2.kombat2.dsl;

import java.util.List;

public class BlockStatement implements Statement {
    private final List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute(Context context) {
        for (Statement stmt : statements) {
            if (context.isDone()) break;
            stmt.execute(context);
        }
    }
}
