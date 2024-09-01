package cr.ac.una.datos.controller;

import cr.ac.una.datos.model.Game;
import cr.ac.una.datos.util.AppContext;
import cr.ac.una.datos.util.FlowController;
import cr.ac.una.datos.util.Formato;
import cr.ac.una.datos.util.Mensaje;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class LevelsController extends Controller implements Initializable {

    @FXML
    private GridPane grpLevels;
    @FXML
    private StackPane root;
    private Integer width = 0;
    private Integer height = 0;
    private List<List<Character>> board;
    private Integer gridPaneColumnWidth = 55;
    private Integer gridPaneRowHeight = 55;
    private Game game;
    private List<Character> playerMovements;


    private final String LEVEL1 = "src/main/resources/cr/ac/una/datos/resources/Levels/level1.txt";
    private final String LEVEL2 = "src/main/resources/cr/ac/una/datos/resources/Levels/level2.txt";
    private final String LEVEL3 = "src/main/resources/cr/ac/una/datos/resources/Levels/level3.txt";
    private final String LEVEL4 = "src/main/resources/cr/ac/una/datos/resources/Levels/level4.txt";
    private final String LEVEL5 = "src/main/resources/cr/ac/una/datos/resources/Levels/level5.txt";
    private final char UP = 'u';
    private final char DOWN = 'd';
    private final char LEFT = 'l';
    private final char RIGHT = 'r';


    private int movementCounter = 0;


    @FXML
    private Text labelLvl;
    @FXML
    private Text labelMovements;

    @FXML
    private Button btnSaveAndExit;

    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.playerMovements = new ArrayList<>();
        grpLevels.setFocusTraversable(true);
        grpLevels.setOnKeyPressed(this::handleKeyPress);
        setUp();
    }

    private void setUp() {
        if ((int) AppContext.getInstance().get("level") == 1) {
            loadBoardFromFile(LEVEL1);
            labelLvl.setText("Nivel 1");
        } else if ((int) AppContext.getInstance().get("level") == 2) {
            loadBoardFromFile(LEVEL2);
            labelLvl.setText("Nivel 2");
        } else if ((int) AppContext.getInstance().get("level") == 3) {
            loadBoardFromFile(LEVEL3);
            labelLvl.setText("Nivel 3");
        } else if ((int) AppContext.getInstance().get("level") == 4) {
            loadBoardFromFile(LEVEL4);
            labelLvl.setText("Nivel 4");
        } else if ((int) AppContext.getInstance().get("level") == 5) {
            loadBoardFromFile(LEVEL5);
            labelLvl.setText("Nivel 5");
        }

        resizeGridPane(width, height);
        loadGridPane();
        game = new Game(board);
        movementCounter = 0;
        game.displayBoard();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                if (game.isValidMove(-1, 0)) {
                    playerMovements.add(UP);
                }
                movePlayer(-1, 0);
                break;
            case S:
                if (game.isValidMove(1, 0)) {
                    playerMovements.add(DOWN);
                }
                movePlayer(1, 0);
                break;
            case A:
                if (game.isValidMove(0, -1)) {
                    playerMovements.add(LEFT);
                }
                movePlayer(0, -1);
                break;
            case D:
                if (game.isValidMove(0, 1)) {
                    playerMovements.add(RIGHT);
                }
                movePlayer(0, 1);
                break;
            case R:
                restartLevel();
                break;
            default:
                break;
        }
    }


    private void uptadeStats() {
        labelMovements.setText(String.valueOf(movementCounter));
    }

    private void movePlayer(int rowOffset, int colOffset) {
        if (game.isValidMove(rowOffset, colOffset)) {
            game.movePlayer(rowOffset, colOffset);
            movementCounter++;
            uptadeStats();
            updateGridPane();
            game.displayBoard();
            if (game.isHasWon()) {
                game.setIsHasWon(false);
                restartLevel();
                resumeLevel();
//                boolean flag = false;
//                RepeatLevelConfirmationController repeatLevelConfirmationController = (RepeatLevelConfirmationController) FlowController.getInstance().getController("RepeatLevelConfirmationView");
//                FlowController.getInstance().goViewInWindowModal("RepeatLevelConfirmationView", ((Stage) root.getScene().getWindow()), true);
//                flag = (boolean) repeatLevelConfirmationController.getResultConfirmation();
//                if (flag) {
//                    System.out.println("¡Has ganado!");
//                    new Mensaje().showModal(AlertType.INFORMATION, "Nivel Completado", getStage(), "Has completado el nivel");
//                    FlowController.getInstance().goView("LevelsSelectorView");
//                } else {
//                    restartLevel();
//                    resumeLevel();
//                }
            }
        }
    }

    public void resumeLevel() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(playerMovements.size());

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> {
            if (movementCounter < playerMovements.size()) {
                char c = playerMovements.get(movementCounter);
                if (c == UP) {
                    movePlayer(-1, 0);
                } else if (c == DOWN) {
                    movePlayer(1, 0);
                } else if (c == LEFT) {
                    movePlayer(0, -1);
                } else if (c == RIGHT) {
                    movePlayer(0, 1);
                }
            }
        }));

        timeline.play();
    }

    private void updateGridPane() {
        grpLevels.getChildren().clear();
        loadGridPane();
    }

    private void restartLevel() {
        setUp();
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
        } else if (character == '!') {
            cargarDatosImagenes("/cr/ac/una/datos/resources/boxTexture.png", row, col, 50, 50);
        } else if (character == '+') {
            ImageView checkpointView = cargarDatosImagenes("/cr/ac/una/datos/resources/checkpoint.png", row, col, 50, 50);
            checkpointView.setOpacity(0.5);
            ImageView playerView = cargarDatosImagenes("/cr/ac/una/datos/resources/personaje.png", row, col, 50, 60);
        }
    }

    public ImageView cargarDatosImagenes(String imagePath, int row, int col, int width, int height) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        grpLevels.add(imageView, col, row);

        return imageView;
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

    @FXML
    private void handleSaveAndExit() {
        saveCurrentGame();
        FlowController.getInstance().goView("LevelsSelectorView");
    }

    private void saveCurrentGame() {
        String saveDirectoryPath = "src/main/resources/cr/ac/una/datos/resources/Levels/saved_levels";
        File directory = new File(saveDirectoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String saveFilePath = saveDirectoryPath + "/saved_game.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath))) {
            for (List<Character> row : board) {
                for (Character ch : row) {
                    writer.write(ch);
                }
                writer.newLine();
            }
            System.out.println("Partida guardada en: " + saveFilePath);
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
            e.printStackTrace();
        }

    }

}