package edu.wallawalla.cs.bierca.reversi;


class Board
{
    Cell[][] cells = new Cell[8][8];;


    ////////////////////
    // Initialization //
    ////////////////////

    public Board() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                cells[row][col] = new Cell();
            }
        }    

        cells[3][3].setState('W');
        cells[3][4].setState('B');
        cells[4][3].setState('B');
        cells[4][4].setState('W');
        
        setCellNeighbors();

    }

    // Get the neighbors of a cell so that the information can then be stored in the cell itself.
    public Cell[] findCellNeighbors(int row, int col) {
        Cell[] neighbors = new Cell[8];

        // We'll use this to traverse counter-clockwise around the cell to look for neighbors.
        int[][] neighborOperations = {
            {0, -1,  0,  0,  1,  1,  0,  0}, // Rows
            {1,  0, -1, -1,  0,  0,  1,  1}  // Columns
        };

        for (int direction = 0; direction < 8; direction++) {
            // Apply the operation necessary to get to the next direction.
            row += neighborOperations[0][direction];
            col += neighborOperations[1][direction];

            // If we fall outside of these bounds, the neighbor will be null.
            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                neighbors[direction] = cells[row][col];
            }
        }

        return neighbors;

    }

    // For each cell, initialize its neighbors to enable easy traversal. 
    public void setCellNeighbors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Cell cell = cells[row][col];
                cell.setNeighbors(findCellNeighbors(row, col));
            }
        }
    }
    

    ///////////////
    // ACCESSORS //
    ///////////////

    public Cell getCellAt(int row, int col) {
        return cells[row][col];
    }

    // Formats the board as a printable string.
    public String getBoardAsString() {
        StringBuilder board = new StringBuilder("   1 2 3 4 5 6 7 8\n");

        for (int row = 0; row < 8; row++) {
            // Label each row with its appropriate letter.
            board.append('\n').appendCodePoint(row + 65).append(' ');

            for (int col = 0; col < 8; col++) {
                board.append(' ').append(cells[row][col].getStateAsChar());
            }
        }

        board.append('\n');

        return board.toString();
    }


    //////////////
    // GAMEPLAY //
    //////////////


    // Scan each cell, returning true as soon as a legal move is found, or false if none are found.
    // 0 = B, 1 = W
    public boolean checkForLegalMoves(boolean player) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.checkForLegalMoves(player)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int[] getScores() {
        int[] scores = new int[2];

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getState() == 1) {
                    scores[0]++;
                } else if (cell.getState() == 2) {
                    scores[1]++;
                }
            }
        }

        return scores;
    }

}