package com.kombat2.kombat2.model;

public class Board {
    private final int rows = 8;
    private final int cols = 8;
    private Hex[][] grid;

    public Board() {
        grid = new Hex[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Hex(r, c);
            }
        }
    }

    public Hex getHex(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }
        return grid[row][col];
    }

    public Hex[][] getGrid() {
        return grid;
    }

    // Returns an adjacent hex based on the direction.
    public Hex getAdjacentHex(int row, int col, String direction) {
        int newRow = row;
        int newCol = col;
        switch (direction.toLowerCase()) {
            case "up":
                newRow = row - 1;
                break;
            case "down":
                newRow = row + 1;
                break;
            case "upleft":
                newRow = row - 1;
                newCol = col - 1;
                break;
            case "upright":
                newRow = row - 1;
                newCol = col + 1;
                break;
            case "downleft":
                newRow = row + 1;
                newCol = col - 1;
                break;
            case "downright":
                newRow = row + 1;
                newCol = col + 1;
                break;
        }
        return getHex(newRow, newCol);
    }

    public void moveMinion(Minion minion, Hex target) {
        Hex current = getHex(minion.getRow(), minion.getCol());
        if (current != null) {
            current.setOccupant(null);
        }
        minion.setRow(target.getRow());
        minion.setCol(target.getCol());
        target.setOccupant(minion);
    }
}
