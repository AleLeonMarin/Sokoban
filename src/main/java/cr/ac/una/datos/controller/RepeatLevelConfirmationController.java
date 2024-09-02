package cr.ac.una.datos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RepeatLevelConfirmationController extends Controller implements Initializable {

    @FXML
    private Button btnContinue;

    @FXML
    private Button btnRepeatLevelMovements;

    public boolean resultConfirmation;

    @FXML
    void btnContinueAction(ActionEvent event) {
        resultConfirmation = true;
        Stage stage = (Stage) btnContinue.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnRepeatLevelMovementsAction(ActionEvent event) {
        resultConfirmation = false;
        Stage stage = (Stage) btnRepeatLevelMovements.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public boolean getResultConfirmation() {
        return resultConfirmation;
    }
}
