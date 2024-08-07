/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.datos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.datos.util.AnimationManager;
import cr.ac.una.datos.util.FlowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;


import io.github.palexdev.materialfx.controls.MFXButton;

/**
 * FXML Controller class
 *
 * @author Kendall Fonseca
 */
public class StartMenuController extends Controller implements Initializable{

    /**
     * Initializes the controller class.
     */

    @FXML
    private ImageView imgLogo;

    @FXML
    private BorderPane root;

    @FXML
    private MFXButton btnPlay;

    AnimationManager animationManager = AnimationManager.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animationManager.applyFadeAnimation(imgLogo);
        animationManager.applyFloatingAnimation(imgLogo);
    }

    @Override
    public void initialize(){

    }

    @FXML
    void onActionBtnPlay(ActionEvent event) {
        FlowController.getInstance().goView("LevelsSelectorView");
    }






}

