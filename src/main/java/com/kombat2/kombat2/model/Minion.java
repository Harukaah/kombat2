package com.kombat2.kombat2.model;

public class Minion {
    private int id;
    private int hp;
    private int defense;
    private int row;
    private int col;
    private String strategy;

    // New fields for ownership, type, and damage.
    private Player owner;  // Who owns this minion
    private MinionType type;  // The type of the minion
    private int damage;       // Damage this minion deals

    public Minion(int id, int initHP, int defense, int row, int col, String strategy, MinionType type, int damage) {
        this.id = id;
        this.hp = initHP;
        this.defense = defense;
        this.row = row;
        this.col = col;
        this.strategy = strategy;
        this.type = type;
        this.damage = damage;
    }

    // Existing getters and setters
    public int getId() {
        return id;
    }
    public int getHP() {
        return hp;
    }
    public void setHP(int hp) {
        this.hp = hp;
    }
    public int getDefense() {
        return defense;
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

    // New getters and setters for owner, type, and damage
    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
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

