package cr.ac.una.datos.controller;

import cr.ac.una.datos.model.Game;
import cr.ac.una.datos.util.AppContext;
import cr.ac.una.datos.util.FlowController;
import cr.ac.una.datos.util.Mensaje;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LevelsController extends Controller implements Initializable {

    @FXML
    private GridPane grpLevels;
    @FXML
    private StackPane root;
    @FXML
    private Text labelLvl;
    @FXML
    private Text labelMovements;
    @FXML
    private Button btnSaveAndExit;

    private Integer width = 0;
    private Integer height = 0;
    private List<List<Character>> board;
    private Integer gridPaneColumnWidth = 55;
    private Integer gridPaneRowHeight = 55;
    private Game game;
    private List<Character> playerMovements = new ArrayList<>();
    private int movementCounter = 0;

    private static final String LEVELS_PATH = "src/main/resources/cr/ac/una/datos/resources/Levels/level";
    private static final int LEVEL_COUNT = 5;
    private static final char UP = 'u';
    private static final char DOWN = 'd';
    private static final char LEFT = 'l';
    private static final char RIGHT = 'r';

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grpLevels.setFocusTraversable(true);
        grpLevels.setOnKeyPressed(this::handleKeyPress);
        setUpLevel();
    }

    @Override
    public void initialize() {
        grpLevels.setFocusTraversable(true);
        grpLevels.setOnKeyPressed(this::handleKeyPress);
        setUpLevel();
    }

    private void setUpLevel() {
        int level = (int) AppContext.getInstance().get("level");
        if (level >= 1 && level <= LEVEL_COUNT) {
            loadBoardFromFile(LEVELS_PATH + level + ".txt");
            labelLvl.setText("Nivel " + level);
        }
        resizeGridPane(width, height);
        loadGridPane();
        game = new Game(board);
        movementCounter = 0;
        updateLabelMovements();
        game.displayBoard();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                movePlayer(-1, 0, UP);
                break;
            case S:
                movePlayer(1, 0, DOWN);
                break;
            case A:
                movePlayer(0, -1, LEFT);
                break;
            case D:
                movePlayer(0, 1, RIGHT);
                break;
            case R:
                setUpLevel();
                this.playerMovements.clear();
                break;
            default:
                break;
        }
    }

    private void movePlayer(int rowOffset, int colOffset, char direction) {
        if (game.isValidMove(rowOffset, colOffset)) {
            playerMovements.add(direction);
            game.movePlayer(rowOffset, colOffset);
            movementCounter++;
            updateLabelMovements();
            updateGridPane();
            if (game.isHasWon()) {
                handleLevelCompletion();
            }
        }
    }

    private void updateLabelMovements() {
        labelMovements.setText(String.valueOf(movementCounter));
    }

    private void handleLevelCompletion() {
        game.setIsHasWon(false);

        Platform.runLater(() -> {
            FlowController.getInstance().goViewInWindowModal("RepeatLevelConfirmationView",
                    ((Stage) root.getScene().getWindow()), false);
            RepeatLevelConfirmationController repeatLevelConfirmationController = (RepeatLevelConfirmationController) FlowController
                    .getInstance().getController("RepeatLevelConfirmationView");
            boolean newFlag = (boolean) repeatLevelConfirmationController.getResultConfirmation();
            if (newFlag) {
                setUpLevel();
                this.playerMovements.clear();
                new Mensaje().showModal(AlertType.INFORMATION, "Nivel Completado", getStage(), "Has completado el nivel");
                FlowController.getInstance().goView("LevelsSelectorView");
            } else {
                setUpLevel();
                resumeLevel();
            }
        });
    }


    private void resumeLevel() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(playerMovements.size());

        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(500),
                event -> {

                    char character = playerMovements.get(movementCounter);
                    if (character == UP)
                        movePlayerRepetitions(-1, 0);
                    else if (character == DOWN)
                        movePlayerRepetitions(1, 0);
                    else if (character == LEFT)
                        movePlayerRepetitions(0, -1);
                    else if (character == RIGHT)
                        movePlayerRepetitions(0, 1);

                    movementCounter++;
                }
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        timeline.setOnFinished(event -> {
            movementCounter = 0;
        });
    }


    private void movePlayerRepetitions(int rowOffset, int colOffset) {
        game.movePlayer(rowOffset, colOffset);
        updateLabelMovements();
        updateGridPane();
        game.displayBoard();
        if (game.isHasWon()) {
            handleLevelCompletion();
        }
    }

    private void updateGridPane() {
        grpLevels.getChildren().clear();
        loadGridPane();
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
                    row.add(j < l.length() ? l.charAt(j) : ' ');
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
        switch (character) {
            case '#':
                cargarDatosImagenes("/cr/ac/una/datos/resources/blockTexture.png", row, col, 50, 50);
                break;
            case '$':
                cargarDatosImagenes("/cr/ac/una/datos/resources/boxTexture.png", row, col, 50, 50);
                break;
            case '@':
                cargarDatosImagenes("/cr/ac/una/datos/resources/personaje.png", row, col, 50, 50);
                break;
            case '.':
                cargarDatosImagenes("/cr/ac/una/datos/resources/checkpoint.png", row, col, 50, 50);
                break;
            case '!':
                cargarDatosImagenes("/cr/ac/una/datos/resources/boxTexture.png", row, col, 50, 50);
                break;
            case '+':
                ImageView checkpointView = cargarDatosImagenes("/cr/ac/una/datos/resources/checkpoint.png", row, col, 50, 50);
                checkpointView.setOpacity(0.5);
                cargarDatosImagenes("/cr/ac/una/datos/resources/personaje.png", row, col, 50, 50);
                break;
            default:
                break;
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
        board.forEach(row -> {
            row.forEach(System.out::print);
            System.out.println();
        });
        System.out.println("Altura: " + height);
        System.out.println("Ancho: " + width);
    }

    @FXML
    private void handleSaveAndExit() {
        saveCurrentGame();
        FlowController.getInstance().goView("LevelsSelectorView");
    }

    private void saveCurrentGame() {
        String saveDirectoryPath = "src/main/resources/cr/ac/una/datos/resources/Levels/savedGame.txt";
        File saveDirectory = new File(saveDirectoryPath);
        if (!saveDirectory.exists()) {
            try {
                saveDirectory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter fileWriter = new FileWriter(saveDirectoryPath);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(labelLvl.getText());
            printWriter.println(labelMovements.getText());
            for (Character movement : playerMovements) {
                printWriter.print(movement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}