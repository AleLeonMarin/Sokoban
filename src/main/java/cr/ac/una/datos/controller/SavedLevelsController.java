package cr.ac.una.datos.controller;



import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import cr.ac.una.datos.model.Player;
import cr.ac.una.datos.model.SavedLevel;
import cr.ac.una.datos.util.AppContext;
import cr.ac.una.datos.util.FlowController;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SavedLevelsController extends Controller implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private Button btnExit;

    @FXML
    private TableView<SavedLevel> tbvSavedLevels;

    @FXML
    private TableColumn<SavedLevel, String> colLevelName;

    @FXML
    private TableColumn<SavedLevel, String> colDateSaved;

    SavedLevel saved;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void handleTableClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            selectMatch();
        }
    }

    private void selectMatch() {
        SavedLevel selectedLevel = tbvSavedLevels.getSelectionModel().getSelectedItem();
        if (selectedLevel != null) {
            AppContext.getInstance().set("level", selectedLevel.getName());

            // Carga el nivel guardado en el LevelsController
            LevelsController levelsController = (LevelsController) FlowController.getInstance().getController("LevelsView");
            levelsController.loadSavedGame(selectedLevel.getName());  // Cargar el archivo guardado

            FlowController.getInstance().goView("LevelsView");
        } else {
            System.out.println("No se ha seleccionado ningún nivel");
        }
    }




    private void loadSavedLevels() {
        ObservableList<SavedLevel> levels = FXCollections.observableArrayList();

        // Cargar los archivos de la carpeta saved_levels
        File folder = new File("src/main/resources/cr/ac/una/datos/resources/Levels/saved_levels");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    // Verifica si el nivel ya ha sido añadido a la lista
                    String levelName = file.getName();
                    if (levels.stream().noneMatch(level -> level.getName().equals(levelName))) {
                        levels.add(new SavedLevel(levelName, new Date(file.lastModified()).toString()));
                    }
                }
            }
        }

        tbvSavedLevels.setItems(levels);
    }



    @Override
    public void initialize() {
        colLevelName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDateSaved.setCellValueFactory(new PropertyValueFactory<>("date"));

        tbvSavedLevels.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends SavedLevel> observable, SavedLevel oldValue,
                              SavedLevel newValue) -> {
                    if (newValue != null) {
                        this.saved = newValue;
                    }
                });

        loadSavedLevels();

        tbvSavedLevels.setOnMouseClicked(this::handleTableClick);
    }

    @FXML
    void onActionBtnExit(ActionEvent event) {
        FlowController.getInstance().goView("StartMenuView");
        Player.getInstance().loadData();
    }


}
