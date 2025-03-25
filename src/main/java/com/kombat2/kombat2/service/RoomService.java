package com.kombat2.kombat2.service;

import com.kombat2.kombat2.model.GameMode;
import com.kombat2.kombat2.model.GameRoom;
import com.kombat2.kombat2.model.Player;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RoomService {

    private Map<String, GameRoom> roomMap = new HashMap<>();

    public GameRoom createRoom(GameMode mode) {
        String roomId = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        GameRoom room = new GameRoom(roomId, mode);
        roomMap.put(roomId, room);
        return room;
    }

    public GameRoom joinRoom(String roomId, int playerId) {
        GameRoom room = roomMap.get(roomId);
        if (room != null && room.getPlayers().size() < 2) {
            // Create a new player (for testing, use default values).
            Player player = new Player(playerId, 10000, 47);
            room.addPlayer(player);
            return room;
        }
        return null;
    }
}
