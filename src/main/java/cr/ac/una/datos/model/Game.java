package cr.ac.una.datos.model;

import java.util.List;

import javafx.scene.shape.Box;

public class Game {
    private static final char PLAYER = '@';
    private static final char BOX = '$';
    private static final char GOAL = '.';
    private static final char WALL = '#';
    private static final char EMPTY = ' ';
    private static final char BOX_ON_GOAL = '!';
    private static final char PLAYER_ON_GOAL = '+';


    private List<List<Character>> board;
    private int playerRow, playerCol;

    public Game(List<List<Character>> board) {
        this.board = board;
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                char ch = board.get(i).get(j);
                if (ch == PLAYER || ch == PLAYER_ON_GOAL) {
                    playerRow = i;
                    playerCol = j;
                }
            }
        }
    }

    public void displayBoard() {
        for (List<Character> row : board) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int rowOffset, int colOffset) {
        int newRow = playerRow + rowOffset;
        int newCol = playerCol + colOffset;

        // Asegura que el nuevo movimiento esté dentro del tablero
        if (newRow < 0 || newRow >= board.size() || newCol < 0 || newCol >= board.get(0).size()) {
            return false;
        }

        char destination = board.get(newRow).get(newCol);

        // Verifica si el destino es una pared o un lugar fuera de los límites
        if (destination == WALL) {
            return false;
        }

        // Si el destino es una caja, verifica si la caja puede moverse
        if (destination == BOX || destination == BOX_ON_GOAL) {
            int boxNewRow = newRow + rowOffset;
            int boxNewCol = newCol + colOffset;

            // Verifica si la posición a la que se mueve la caja es válida
            if (boxNewRow < 0 || boxNewRow >= board.size() || boxNewCol < 0 || boxNewCol >= board.get(0).size()) {
                return false;
            }

            char boxNextDestination = board.get(boxNewRow).get(boxNewCol);
            return (boxNextDestination == EMPTY || boxNextDestination == GOAL);
        }

        return true;
    }

    public void movePlayer(int rowOffset, int colOffset) {
        int newRow = playerRow + rowOffset;
        int newCol = playerCol + colOffset;

        char destination = board.get(newRow).get(newCol);
        char ifWasBoxOnGoal = destination;

        if (destination == BOX || destination == BOX_ON_GOAL) {
            ifWasBoxOnGoal = destination;
            int boxNewRow = newRow + rowOffset;
            int boxNewCol = newCol + colOffset;
            board.get(boxNewRow).set(boxNewCol, board.get(boxNewRow).get(boxNewCol) == GOAL ? BOX_ON_GOAL : BOX);
            board.get(newRow).set(newCol, destination == BOX_ON_GOAL ? GOAL : EMPTY);
        }

        board.get(playerRow).set(playerCol, board.get(playerRow).get(playerCol) == PLAYER_ON_GOAL ? GOAL : EMPTY);
        playerRow = newRow;
        playerCol = newCol;
        board.get(playerRow).set(playerCol, destination == GOAL ? PLAYER_ON_GOAL : PLAYER);

        if (ifWasBoxOnGoal == BOX_ON_GOAL) {
            board.get(playerRow).set(playerCol, PLAYER_ON_GOAL);
        }
    }

    public boolean hasWon() {
        for (List<Character> row : board) {
            for (char cell : row) {
                if (cell == BOX) {
                    return false;
                }
            }
        }
        return true;
    }
}
