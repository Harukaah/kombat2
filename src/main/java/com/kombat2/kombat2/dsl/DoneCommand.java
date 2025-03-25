package com.kombat2.kombat2.dsl;

public class DoneCommand implements Statement {
    @Override
    public void execute(Context context) {
        context.done();
    }
}
