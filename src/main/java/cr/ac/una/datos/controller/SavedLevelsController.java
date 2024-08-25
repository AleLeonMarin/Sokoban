/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.datos.controller;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import cr.ac.una.datos.model.SavedLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Kendall Fonseca
 */
public class SavedLevelsController extends Controller implements Initializable  {

    /**
     * Initializes the controller class.
     */

    @FXML
    private StackPane root;

    @FXML
    private TableView<SavedLevel> tbvSavedLevels;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colLevelName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDateSaved.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadSavedLevels();
    }
    @Override
    public void initialize() {
        // TODO
    }


    @FXML
    private TableColumn<SavedLevel, String> colLevelName;
    @FXML
    private TableColumn<SavedLevel, String> colDateSaved;



    private void loadSavedLevels() {
        ObservableList<SavedLevel> levels = FXCollections.observableArrayList();

        // Cargar los datos desde los archivos
        File folder = new File("src/main/resources/cr/ac/una/datos/resources/Levels/saved_levels");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {

                    levels.add(new SavedLevel(file.getName(), new Date(file.lastModified()).toString()));
                }
            }
        }

        tbvSavedLevels.setItems(levels);
    }


}
