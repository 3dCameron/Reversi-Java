package edu.wallawalla.cs.bierca.reversi;

import java.util.Scanner;

public class Game
{
    Board board = new Board();
    boolean turn = false;
    boolean active = false;
    Scanner scanner = new Scanner(System.in);

    //////////////
    // GAMEPLAY //
    //////////////

    public void start() {
        active = true;

        while (active) {
            active = (takeTurn() && board.checkForLegalMoves(turn));
        }

        endGame();
    }

    // Prompt the user for input, displaying an error message if the previous input was invalid.
    private String requestInput(boolean error) {
        clearScreen();
        System.out.println(board.getBoardAsString());
        if (error) {
            System.out.println("That was not a valid move. Please try again.");
        }
        System.out.println((turn ? "White" : "Black") + ", type \"EXIT\" to end the game, or enter the cell in which to place your next piece (for example, \"I9\"):");
        return scanner.nextLine();

    }

    // Get user input and try to complete a move.
    private boolean takeTurn() {
        boolean success = true;

        String input;

        do {
            input = requestInput(!success);

            if (input.toUpperCase().equals("EXIT")) {
                return false;
            }

            success = moveFromInput(input);
        } while (!success);
        
        nextTurn();

        return true;
    }

    private boolean moveFromInput(String input) {
        int row = 0;
        int col = 0;

        // Make sure the input meets the length requirement before parsing
        if (input.length() == 2) {
            row = (int)(java.lang.Character.toUpperCase(input.charAt(0))) - 65;
            col = java.lang.Character.getNumericValue(input.charAt(1)) - 1;

            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                return (board.getCellAt(row, col).placePiece(turn));
            }
        }

        return false;
    }

    private void nextTurn() {
        turn = !turn;
    }

    // Tally the score and declare a winner
    public void endGame() {
        // This has likely already been made false, but let's be safe
        active = false;

        int[] scores = board.getScores();

        String message = "Black wins!";

        if (scores[0] == scores[1]) {
            message = "The game is a draw.";
        } else if (scores[0] < scores[1]) {
            message = "White wins!";
        }

        clearScreen();

        System.out.println(
            board.getBoardAsString() +
            "\nGame Over!\n" +
            "The final score is:\n" +
            "Black: " + scores[0] + "\tWhite: " + scores[1] + "\n" +
            message
        );
        
    }

    // From https://stackoverflow.com/a/32295974
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

}