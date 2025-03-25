package com.kombat2.kombat2.dsl;

public class NumberExpression implements Expression {
    private final long value;

    public NumberExpression(long value) {
        this.value = value;
    }

    @Override
    public long evaluate(Context context) {
        return value;
    }
}

