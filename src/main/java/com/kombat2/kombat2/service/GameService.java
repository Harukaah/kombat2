package com.kombat2.kombat2.service;

import com.kombat2.kombat2.config.GameConfig;
import com.kombat2.kombat2.model.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Game game;
    private GameConfig config;
    private int minionIdCounter = 1;

    @Autowired
    public GameService(GameConfig config) {
        this.config = config;
        startNewGame();
    }

    public void startNewGame() {
        game = new Game(config);

        // Mark five spawnable hexes for Player 1 (top-left corner)
        markSpawnableHex(0, 0, game.getPlayer1());
        markSpawnableHex(0, 1, game.getPlayer1());
        markSpawnableHex(1, 0, game.getPlayer1());
        markSpawnableHex(1, 1, game.getPlayer1());
        markSpawnableHex(2, 0, game.getPlayer1());

        // Mark five spawnable hexes for Player 2 (bottom-right corner)
        markSpawnableHex(7, 7, game.getPlayer2());
        markSpawnableHex(7, 6, game.getPlayer2());
        markSpawnableHex(6, 7, game.getPlayer2());
        markSpawnableHex(6, 6, game.getPlayer2());
        markSpawnableHex(5, 7, game.getPlayer2());

        // Spawn one initial minion per player on a spawnable hex.
        spawnInitialMinion(game.getPlayer1(), 0, 0, "default_strategy", MinionType.TYPE1, 10);
        spawnInitialMinion(game.getPlayer2(), 7, 7, "default_strategy", MinionType.TYPE1, 10);
    }

    private void markSpawnableHex(int row, int col, Player owner) {
        Hex hex = game.getBoard().getHex(row, col);
        if (hex != null) {
            hex.setSpawnable(true);
            hex.setOwner(owner);
        }
    }

    public Game getGame() {
        return game;
    }

    public Board getBoard() {
        return game.getBoard();
    }

    public void spawnInitialMinion(Player player, int row, int col, String strategy, MinionType type, int damage) {
        if (player.getSpawnsLeft() <= 0) return;
        Hex hex = game.getBoard().getHex(row, col);
        // Ensure the hex is valid, not occupied, spawnable and owned by the player.
        if (hex == null || hex.isOccupied() || !hex.isSpawnable() || hex.getOwner() != player) return;
        Minion minion = new Minion(minionIdCounter++, (int) config.initHP, 0, row, col, strategy, type, damage);
        minion.setOwner(player);
        hex.setOccupant(minion);
        player.addMinion(minion);
        player.decreaseSpawnsLeft();
    }

    public boolean spawnMinion(int playerId, int row, int col, String strategy, int defense, MinionType type, int damage) {
        Player player = (playerId == 1) ? game.getPlayer1() : game.getPlayer2();
        if (player.getSpawnsLeft() <= 0) return false;
        Hex hex = game.getBoard().getHex(row, col);
        if (hex == null || hex.isOccupied() || !hex.isSpawnable() || hex.getOwner() != player) return false;
        if (player.getBudget() < config.spawnCost) return false;
        player.decreaseBudget(config.spawnCost);
        Minion minion = new Minion(minionIdCounter++, (int) config.initHP, defense, row, col, strategy, type, damage);
        minion.setOwner(player);
        hex.setOccupant(minion);
        player.addMinion(minion);
        player.decreaseSpawnsLeft();
        return true;
    }

    public boolean purchaseHex(int playerId, int row, int col) {
        Player player = (playerId == 1) ? game.getPlayer1() : game.getPlayer2();
        Hex hex = game.getBoard().getHex(row, col);
        if (hex == null || hex.isSpawnable()) return false;

        // Check adjacency using hex-specific neighbor logic.
        boolean isAdjacent = false;
        List<Hex> neighbors = getHexNeighbors(row, col);
        for (Hex n : neighbors) {
            if (n != null && n.isSpawnable()) {
                isAdjacent = true;
                break;
            }
        }
        if (!isAdjacent) return false;
        if (player.getBudget() < config.hexPurchaseCost) return false;
        player.decreaseBudget(config.hexPurchaseCost);
        hex.setSpawnable(true);
        hex.setOwner(player);
        return true;
    }

    public void nextTurn() {
        // Copy minion lists to avoid modification during iteration.
        List<Minion> p1Minions = new ArrayList<>(game.getPlayer1().getMinions());
        List<Minion> p2Minions = new ArrayList<>(game.getPlayer2().getMinions());

        // Process moves for both players.
        for (Minion m : p1Minions) {
            processMinion(m, 1); // For Player 1, move "south" (increasing row)
        }
        for (Minion m : p2Minions) {
            processMinion(m, -1); // For Player 2, move "north" (decreasing row)
        }

        game.incrementTurn();
    }

    /**
     * Processes a single minion's turn:
     * - Check all 6 neighboring hexes for an enemy minion; if found, attack and remove it.
     * - Otherwise, move one hex toward the opponent in a vertical direction (adjust if needed).
     *
     * @param minion The minion to process.
     * @param verticalDir Direction to move vertically (1 for Player 1, -1 for Player 2).
     */
    private void processMinion(Minion minion, int verticalDir) {
        int row = minion.getRow();
        int col = minion.getCol();
        // Get all 6 neighbors for the hex.
        List<Hex> neighbors = getHexNeighbors(row, col);
        boolean attacked = false;

        // Look for an adjacent enemy minion.
        for (Hex n : neighbors) {
            if (n != null && n.isOccupied()) {
                Minion enemy = n.getOccupant();
                if (enemy != null && enemy.getOwner() != minion.getOwner()) {
                    // Attack: For simplicity, remove enemy immediately.
                    enemy.setHP(0);
                    n.setOccupant(null);
                    enemy.getOwner().getMinions().remove(enemy);
                    attacked = true;
                    break;
                }
            }
        }

        // If no attack occurred, move minion toward the opponent.
        if (!attacked) {
            // For movement, try to move in a neighbor that is closer to the opponent.
            // For Player 1, assume opponent is at higher row indices; for Player 2, lower.
            Hex targetHex = null;
            for (Hex n : neighbors) {
                if (n != null && !n.isOccupied()) {
                    int dr = n.getRow() - row;
                    if ((verticalDir > 0 && dr > 0) || (verticalDir < 0 && dr < 0)) {
                        targetHex = n;
                        break;
                    }
                }
            }
            if (targetHex != null) {
                Hex currentHex = game.getBoard().getHex(row, col);
                if (currentHex != null) {
                    currentHex.setOccupant(null);
                }
                targetHex.setOccupant(minion);
                minion.setRow(targetHex.getRow());
                minion.setCol(targetHex.getCol());
            }
        }
    }

    /**
     * Returns a list of neighbor hexes for a given (row, col) coordinate on an odd-r offset grid.
     * Adjust the offsets if your grid orientation is different.
     */
    private List<Hex> getHexNeighbors(int row, int col) {
        List<Hex> neighbors = new ArrayList<>();
        // Define neighbor offsets for odd-r layout.
        // For even rows:
        int[][] evenRowOffsets = {
                {-1, -1}, {-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 0}
        };
        // For odd rows:
        int[][] oddRowOffsets = {
                {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}, {1, 1}
        };

        int[][] offsets = (row % 2 == 0) ? evenRowOffsets : oddRowOffsets;
        for (int[] offset : offsets) {
            int nRow = row + offset[0];
            int nCol = col + offset[1];
            Hex neighbor = game.getBoard().getHex(nRow, nCol);
            neighbors.add(neighbor);
        }
        return neighbors;
    }
}


