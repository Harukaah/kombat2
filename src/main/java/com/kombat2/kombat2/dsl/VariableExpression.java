package com.kombat2.kombat2.dsl;

public class VariableExpression implements Expression {
    private final String identifier;

    public VariableExpression(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public long evaluate(Context context) {
        return context.getVariable(identifier);
    }
}

