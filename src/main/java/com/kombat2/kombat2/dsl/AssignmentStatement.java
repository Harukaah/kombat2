package com.kombat2.kombat2.dsl;

public class AssignmentStatement implements Statement {
    private final String identifier;
    private final Expression expression;

    public AssignmentStatement(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void execute(Context context) {
        long value = expression.evaluate(context);
        context.assignVariable(identifier, value);
    }
}
