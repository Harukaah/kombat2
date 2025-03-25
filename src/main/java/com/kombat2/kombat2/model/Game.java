package com.kombat2.kombat2.model;

import com.kombat2.kombat2.config.GameConfig;
import com.kombat2.kombat2.strategy.StrategyInterpreter;

public class Game {
    private GameConfig config;
    private Board board;
    private Player player1;
    private Player player2;
    private int currentTurn;

    public Game(GameConfig config) {
        this.config = config;
        this.board = new Board();
        this.currentTurn = 1;
        this.player1 = new Player(1, config.initBudget, (int) config.maxSpawns);
        this.player2 = new Player(2, config.initBudget, (int) config.maxSpawns);
    }

    public Board getBoard() {
        return board;
    }
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public int getCurrentTurn() {
        return currentTurn;
    }

    public void processTurn(Player player) {
        
    }

    public boolean isGameOver() {
        return player1.getMinions().isEmpty() ||
                player2.getMinions().isEmpty() ||
                currentTurn > config.maxTurns;
    }

    public void nextTurn() {
        processTurn(player1);
        if (isGameOver()) return;
        processTurn(player2);
        currentTurn++;
    }

    public void incrementTurn() {
    }
}
