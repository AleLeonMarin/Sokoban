/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.datos.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * FXML Controller class
 *
 * @author Kendall Fonseca
 */
public class LevelsController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    private GridPane grpLevels;
    Integer width = 0;
    Integer height = 0;
    private char[][] board;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    @Override
    public void initialize() {
        loadBoardFromFile("src/main/resources/cr/ac/una/datos/resources/Levels/easy_level2.txt");
        resizeGridPane(width, height);
        loadGridPane();
    }


    private void resizeGridPane(int width, int height) {
        Integer collumnWitdh = 50;
        Integer rowHeight = 40;
        grpLevels.getColumnConstraints().clear();
        grpLevels.getRowConstraints().clear();
        grpLevels.getChildren().clear();

        for (int col = 0; col < width; col++) {
            grpLevels.getColumnConstraints().add(new ColumnConstraints(collumnWitdh));
        }
        for (int row = 0; row < height; row++) {
            grpLevels.getRowConstraints().add(new RowConstraints(rowHeight));
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
            board = new char[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (j < lines.get(i).length()) {
                        board[i][j] = lines.get(i).charAt(j);
                    } else {
                        board[i][j] = ' ';
                    }
                }
            }

            printBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGridPane() {
        for (int indexHeight = 0; indexHeight < height; indexHeight++) {
            for (int indexWidth = 0; indexWidth < width; indexWidth++) {
                identifyBlocks(board[indexHeight][indexWidth], indexHeight, indexWidth);
            }
        }
    }

    public void identifyBlocks(char caracter, int widthPos, int heightPos) {//agregar mas imagenes para meter
        if (caracter == '#') {
            cargarDatosImagenes("/cr/ac/una/datos/resources/blockTexture.png", widthPos, heightPos);
        } else if (caracter == '$') {
            cargarDatosImagenes("/cr/ac/una/datos/resources/boxTexture.png", widthPos, heightPos);
        }
    }

    public void cargarDatosImagenes(String imagePath, int widthPos, int heightPos) {
        try {
            Integer imvWitdth = 40;
            Integer imvHeight = 40;

            ImageView imvBlock = new ImageView();
            Image imgBlock = new Image(getClass().getResourceAsStream(imagePath));

            imvBlock.setImage(imgBlock);
            imvBlock.setFitWidth(imvWitdth);
            imvBlock.setFitHeight(imvHeight);
            grpLevels.add(imvBlock, heightPos, widthPos);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }

        System.out.println("Altura: " + height);
        System.out.println("Ancho: " + width);
    }
}
