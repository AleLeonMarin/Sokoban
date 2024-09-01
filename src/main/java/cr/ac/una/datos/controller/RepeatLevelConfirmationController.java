package cr.ac.una.datos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class RepeatLevelConfirmationController extends Controller implements Initializable {

    @FXML
    private Button btnContinue;

    @FXML
    private Button btnRepeatLevelMovements;

    private boolean resultConfirmation;

    @FXML
    void btnContinueAction(ActionEvent event) {
        resultConfirmation = true;
    }

    @FXML
    void btnRepeatLevelMovementsAction(ActionEvent event) {
        resultConfirmation = false;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultConfirmation = true;
    }

    public boolean getResultConfirmation() {
        return resultConfirmation;
    }
}
