package com.kombat2.kombat2.dsl;

public class IfStatement implements Statement {
    private final Expression condition;
    private final Statement thenStmt;
    private final Statement elseStmt;

    public IfStatement(Expression condition, Statement thenStmt, Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public void execute(Context context) {
        long condVal = condition.evaluate(context);
        if (condVal > 0) {
            thenStmt.execute(context);
        } else {
            elseStmt.execute(context);
        }
    }
}
