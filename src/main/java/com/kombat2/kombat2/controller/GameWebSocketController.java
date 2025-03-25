package com.kombat2.kombat2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kombat2.kombat2.model.Game;
import com.kombat2.kombat2.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameWebSocketController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public String startGame() {
        gameService.startNewGame();
        return "New game started!";
    }

    @GetMapping("/state")
    public Game getGameState() {
        return gameService.getGame();
    }

    /**
     * Spawns a new minion based on the provided GameCommand payload.
     * Example JSON payload:
     * {
     *   "playerId": 1,
     *   "row": 0,
     *   "col": 0,
     *   "strategy": "default_strategy",
     *   "defense": 0,
     *   "type": "TYPE1",
     *   "damage": 10
     * }
     */
    @PostMapping("/spawn")
    public String spawnMinion(@RequestBody GameCommand command) {
        boolean success = gameService.spawnMinion(
                command.getPlayerId(),
                command.getRow(),
                command.getCol(),
                command.getStrategy(),
                command.getDefense(),
                command.getType(),
                command.getDamage()
        );
        return success ? "Minion spawned." : "Failed to spawn minion.";
    }

    @PostMapping("/purchase")
    public String purchaseHex(@RequestBody GameCommand command) {
        boolean success = gameService.purchaseHex(
                command.getPlayerId(),
                command.getRow(),
                command.getCol()
        );
        return success ? "Hex purchased and now spawnable." : "Failed to purchase hex.";
    }

    @PostMapping("/nextTurn")
    public String nextTurn() {
        gameService.nextTurn();
        return "Turn advanced.";
    }
}

