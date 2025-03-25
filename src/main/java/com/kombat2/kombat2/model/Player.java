package com.kombat2.kombat2.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private double budget;
    private List<Minion> minions;
    private int spawnsLeft;

    public Player(int id, double initBudget, int maxSpawns) {
        this.id = id;
        this.budget = initBudget;
        this.spawnsLeft = maxSpawns;
        this.minions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public double getBudget() {
        return budget;
    }
    public void addBudget(double amount) {
        this.budget += amount;
    }
    public void decreaseBudget(double amount) {
        this.budget -= amount;
    }
    public int getSpawnsLeft() {
        return spawnsLeft;
    }
    public void decreaseSpawnsLeft() {
        this.spawnsLeft--;
    }
    public List<Minion> getMinions() {
        return minions;
    }
    public void addMinion(Minion m) {
        minions.add(m);
    }
}
