/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.datos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.datos.util.AnimationManager;
import cr.ac.una.datos.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Kendall Fonseca
 */
public class LevelsSelectorController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */

    
    @FXML
    private ImageView imgCover1;

    @FXML
    private ImageView imgCover2;

    @FXML
    private ImageView imgCover3;

    @FXML
    private ImageView imgCover4;

    @FXML
    private ImageView imgCover5;

    @FXML
    private ImageView imgLevel1;

    @FXML
    private Button btnExit;

    private Runnable onFinishOut;

    
    AnimationManager animationManager = AnimationManager.getInstance();
    
    @Override
    public void initialize(){  
        disableCover();
    }
    
    @FXML
    void onMouseClicked(MouseEvent event) {
        FlowController.getInstance().goView("LevelsView");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private void disableCover(){
         onFinishOut = () -> {
            imgCover1.setDisable(true);
        };
        animationManager.animarFadeOut(imgCover1, onFinishOut);
    }

    public void onActionBtnExit(ActionEvent event) {
        FlowController.getInstance().goView("StartMenuView");
    }
}
