package cr.ac.una.datos.controller;

import cr.ac.una.datos.model.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class LevelsController extends Controller implements Initializable {

    @FXML
    private GridPane grpLevels;
    private Integer width = 0;
    private Integer height = 0;
    private List<List<Character>> board;
    private Integer gridPaneColumnWidth = 55;
    private Integer gridPaneRowHeight = 55;
    private Game game;


    @Override
    public void initialize() {
        loadBoardFromFile("src/main/resources/cr/ac/una/datos/resources/Levels/easy_level1.txt");
        resizeGridPane(width, height);
        loadGridPane();
        game = new Game(board);
        game.displayBoard(); // Display board for debugging or initial state
    }


// Dentro de la clase LevelsController...

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grpLevels.setFocusTraversable(true);  // Asegúrate de que el GridPane pueda recibir eventos de teclado
        grpLevels.setOnKeyPressed(this::handleKeyPress);  // Asocia el método de manejo de teclas
        initialize();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                movePlayer(-1, 0);  // Mover hacia arriba
                game.displayBoard();
                break;
            case S:
                movePlayer(1, 0);  // Mover hacia abajo
                game.displayBoard();
                break;
            case A:
                movePlayer(0, -1);  // Mover hacia la izquierda
                game.displayBoard();
                break;
            case D:
                movePlayer(0, 1);  // Mover hacia la derecha
                game.displayBoard();
                break;
            default:
                break;
        }
    }

    private void movePlayer(int rowOffset, int colOffset) {
        if (game.isValidMove(rowOffset, colOffset)) {
            game.movePlayer(rowOffset, colOffset);
            updateGridPane();  // Actualiza el GridPane después de mover al jugador
            game.displayBoard();  // Muestra el tablero actualizado en la consola (opcional)
            if (game.hasWon()) {
                System.out.println("¡Has ganado!");
            }
        }
    }

    private void updateGridPane() {
        grpLevels.getChildren().clear();
        loadGridPane();  // Recarga el GridPane con la nueva posición del jugador y cajas
    }


    private void resizeGridPane(int width, int height) {
        grpLevels.getColumnConstraints().clear();
        grpLevels.getRowConstraints().clear();
        grpLevels.getChildren().clear();

        for (int col = 0; col < width; col++) {
            grpLevels.getColumnConstraints().add(new ColumnConstraints(gridPaneColumnWidth));
        }
        for (int row = 0; row < height; row++) {
            grpLevels.getRowConstraints().add(new RowConstraints(gridPaneRowHeight));
        }
        System.out.println("Tamaño de gridpane en row: " + grpLevels.getRowCount());
        System.out.println("Tamaño de gridpane columnas: " + grpLevels.getColumnCount());
    }

    private void loadBoardFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                if (line.length() > width) {
                    width = line.length();
                }
            }
            height = lines.size();
            board = new ArrayList<>();

            for (String l : lines) {
                List<Character> row = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    if (j < l.length()) {
                        row.add(l.charAt(j));
                    } else {
                        row.add(' ');
                    }
                }
                board.add(row);
            }

            printBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGridPane() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                identifyBlocks(board.get(row).get(col), row, col);
            }
        }
    }

    public void identifyBlocks(char character, int row, int col) {
        if (character == '#') {
            cargarDatosImagenes("/cr/ac/una/datos/resources/blockTexture.png", row, col, 50, 50);
        } else if (character == '$') {
            cargarDatosImagenes("/cr/ac/una/datos/resources/boxTexture.png", row, col, 50, 50);
        } else if (character == '@') {
            cargarDatosImagenes("/cr/ac/una/datos/resources/personaje.png", row, col, 50, 60);
        } else if (character == '.') {
            cargarDatosImagenes("/cr/ac/una/datos/resources/checkpoint.png", row, col, 50, 50);

        }
        // Puedes añadir más condiciones si es necesario.
    }

    public void cargarDatosImagenes(String imagePath, int rowPos, int colPos, Integer imvWidth, Integer imvHeight) {
        try {
            ImageView imageView = new ImageView();
            Image image = new Image(getClass().getResourceAsStream(imagePath));

            imageView.setImage(image);
            imageView.setFitWidth(imvWidth);
            imageView.setFitHeight(imvHeight);
            grpLevels.add(imageView, colPos, rowPos);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printBoard() {
        for (List<Character> row : board) {
            for (Character c : row) {
                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println("Altura: " + height);
        System.out.println("Ancho: " + width);
    }
}
