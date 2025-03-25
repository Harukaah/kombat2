package com.kombat2.kombat2.dsl;

public class InfoExpression implements Expression {
    private final String infoType;
    private final String direction;

    public InfoExpression(String infoType, String direction) {
        this.infoType = infoType;
        this.direction = direction;
    }

    @Override
    public long evaluate(Context context) {
        switch (infoType) {
            case "ally": return context.getAllyInfo();
            case "opponent": return context.getOpponentInfo();
            case "nearby": return context.getNearbyInfo(direction);
            default: return 0;
        }
    }
}
