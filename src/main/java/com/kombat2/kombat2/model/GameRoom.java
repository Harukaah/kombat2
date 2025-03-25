package com.kombat2.kombat2.model;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {
    private String roomId;
    private List<Player> players;
    private GameMode mode;

    public GameRoom(String roomId, GameMode mode) {
        this.roomId = roomId;
        this.mode = mode;
        this.players = new ArrayList<>();
    }

    public String getRoomId() { return roomId; }
    public List<Player> getPlayers() { return players; }
    public GameMode getMode() { return mode; }
    public void addPlayer(Player player) {
        if (players.size() < 2) {
            players.add(player);
        }
    }
}

