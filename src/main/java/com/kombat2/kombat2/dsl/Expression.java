package com.kombat2.kombat2.dsl;

public interface Expression extends ASTNode {
    long evaluate(Context context);
}
