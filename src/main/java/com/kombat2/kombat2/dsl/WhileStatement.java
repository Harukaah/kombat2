package com.kombat2.kombat2.dsl;

public class WhileStatement implements Statement {
    private final Expression condition;
    private final Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Context context) {
        int loopCount = 0;
        while (!context.isDone() && loopCount < 10000) {
            long condVal = condition.evaluate(context);
            if (condVal <= 0) break;
            body.execute(context);
            loopCount++;
        }
    }
}

