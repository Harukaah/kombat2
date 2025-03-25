package com.kombat2.kombat2.dsl;

public interface Statement extends ASTNode {
    void execute(Context context);
}
