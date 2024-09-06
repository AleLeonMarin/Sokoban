package cr.ac.una.datos.controller;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import cr.ac.una.datos.model.SavedLevel;
import cr.ac.una.datos.util.AppContext;
import cr.ac.una.datos.util.FlowController;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SavedLevelsController extends Controller implements Initializable {

    @FXML
    private StackPane root;

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
            // Guarda el nivel seleccionado en el contexto de la aplicación
            AppContext.getInstance().set("selectedLevel", selectedLevel);

            // Usa FlowController para ir a la vista de selección de niveles
            AppContext.getInstance().set("StartAnimation", false);
            FlowController.getInstance().goView("LevelsSelectorView");
        } else {
            // Mensaje de error o alerta si no se ha seleccionado ningún nivel
            System.out.println("No se ha seleccionado un nivel");
            // Puedes mostrar un mensaje en la interfaz de usuario si lo deseas
        }
    }

    private void loadSavedLevels() {
        ObservableList<SavedLevel> levels = FXCollections.observableArrayList();

        // Cargar los datos desde los archivos
        File folder = new File("src/main/resources/cr/ac/una/datos/resources/Levels/saved_levels/savedGame.txt");
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

    @Override
    public void initialize() {
        colLevelName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDateSaved.setCellValueFactory(new PropertyValueFactory<>("date"));

        tbvSavedLevels.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends SavedLevel> observable, SavedLevel oldValue,
                              SavedLevel newValue) -> {
                    if (newValue != null) {
                        // Obtiene el jugador seleccionado en la tabla
                        this.saved = newValue;
                    }
                });

        loadSavedLevels();

        // Add double-click event handler
        tbvSavedLevels.setOnMouseClicked(this::handleTableClick);
    }
}
