package com.kombat2.kombat2.dsl;

public class BinaryExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final char operator;

    public BinaryExpression(Expression left, char operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public long evaluate(Context context) {
        long l = left.evaluate(context);
        long r = right.evaluate(context);
        switch (operator) {
            case '+': return l + r;
            case '-': return l - r;
            case '*': return l * r;
            case '/': return (r == 0) ? 0 : l / r;
            case '%': return (r == 0) ? 0 : l % r;
            case '^':
                double val = Math.pow(l, r);
                if (val > Long.MAX_VALUE) return Long.MAX_VALUE;
                if (val < Long.MIN_VALUE) return Long.MIN_VALUE;
                return (long) val;
            default: return 0;
        }
    }
}
