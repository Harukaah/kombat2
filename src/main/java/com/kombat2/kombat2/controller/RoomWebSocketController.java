package com.kombat2.kombat2.controller;

import com.kombat2.kombat2.model.GameMode;
import com.kombat2.kombat2.model.GameRoom;
import com.kombat2.kombat2.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RoomWebSocketController {

    @Autowired
    private RoomService roomService;

    @MessageMapping("/room/create")
    @SendTo("/topic/room")
    public GameRoom createRoom(String modeStr) {
        GameMode mode = GameMode.valueOf(modeStr.toUpperCase());
        return roomService.createRoom(mode);
    }

    @MessageMapping("/room/join")
    @SendTo("/topic/room")
    public GameRoom joinRoom(String roomIdAndPlayerId) {
        // Assume payload is "roomId,playerId"
        String[] parts = roomIdAndPlayerId.split(",");
        String roomId = parts[0].trim();
        int playerId = Integer.parseInt(parts[1].trim());
        return roomService.joinRoom(roomId, playerId);
    }
}
