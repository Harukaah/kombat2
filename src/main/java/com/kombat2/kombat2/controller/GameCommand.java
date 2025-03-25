package com.kombat2.kombat2.controller;

import com.kombat2.kombat2.model.MinionType;

public class GameCommand {
    private int playerId;
    private int row;
    private int col;
    private String strategy;
    private int defense;
    // New fields for minion type and damage.
    private MinionType type;
    private int damage;

    // Getters and setters
    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public String getStrategy() {
        return strategy;
    }
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public MinionType getType() {
        return type;
    }
    public void setType(MinionType type) {
        this.type = type;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
