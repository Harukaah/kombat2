package com.kombat2.kombat2.dsl;

public class MoveCommand implements Statement {
    private final String direction;

    public MoveCommand(String direction) {
        this.direction = direction;
    }

    @Override
    public void execute(Context context) {
        context.move(direction);
    }
}
