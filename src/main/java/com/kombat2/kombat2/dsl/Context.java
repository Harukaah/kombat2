package com.kombat2.kombat2.dsl;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Long> variables = new HashMap<>();
    private boolean done = false;

    public void assignVariable(String identifier, long value) {
        if (isReadOnly(identifier)) {
            return;
        }
        variables.put(identifier, value);
    }

    public long getVariable(String identifier) {
        return variables.getOrDefault(identifier, 0L);
    }

    private boolean isReadOnly(String identifier) {
        switch (identifier) {
            case "row":
            case "col":
            case "budget":
            case "int":
            case "maxbudget":
            case "spawnsleft":
            case "random":
                return true;
            default:
                return false;
        }
    }

    public void move(String direction) {
        System.out.println("Move command: direction = " + direction);
        // Here you would integrate board and budget logic.
    }

    public void shoot(String direction, long expenditure) {
        System.out.println("Shoot command: direction = " + direction + ", expenditure = " + expenditure);
        // Here you would integrate shooting logic.
    }

    public long getAllyInfo() {
        return 0;
    }

    public long getOpponentInfo() {
        return 0;
    }

    public long getNearbyInfo(String direction) {
        return 0;
    }

    public void done() {
        done = true;
    }

    public boolean isDone() {
        return done;
    }
}
