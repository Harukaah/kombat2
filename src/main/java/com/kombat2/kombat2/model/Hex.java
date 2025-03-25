package com.kombat2.kombat2.model;

public class Hex {
    private int row;
    private int col;
    private boolean spawnable;
    private Minion occupant;
    private Player owner; // New field to store the hex owner

    public Hex(int row, int col) {
        this.row = row;
        this.col = col;
        this.spawnable = false;
        this.occupant = null;
        this.owner = null; // Initially, no owner is assigned
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public boolean isSpawnable() {
        return spawnable;
    }
    public void setSpawnable(boolean spawnable) {
        this.spawnable = spawnable;
    }
    public boolean isOccupied() {
        return occupant != null;
    }
    public Minion getOccupant() {
        return occupant;
    }
    public void setOccupant(Minion occupant) {
        this.occupant = occupant;
    }

    // New getter and setter for owner
    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
