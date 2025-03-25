package com.kombat2.kombat2.dsl;

public class ShootCommand implements Statement {
    private final String direction;
    private final Expression expenditure;

    public ShootCommand(String direction, Expression expenditure) {
        this.direction = direction;
        this.expenditure = expenditure;
    }

    @Override
    public void execute(Context context) {
        long exp = expenditure.evaluate(context);
        context.shoot(direction, exp);
    }
}
