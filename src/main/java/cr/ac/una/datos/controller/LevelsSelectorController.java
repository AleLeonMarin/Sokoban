/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.datos.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.datos.util.AnimationManager;
import cr.ac.una.datos.util.AppContext;
import cr.ac.una.datos.util.FlowController;
import cr.ac.una.datos.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

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
    private ImageView imgLevel1;

    @FXML
    private ImageView imgLevel2;

    @FXML
    private ImageView imgLevel3;

    @FXML
    private ImageView imgLevel4;

    @FXML
    private ImageView imgLevel5;

    @FXML
    private ImageView imgLock2;

    @FXML
    private ImageView imgLock3;

    @FXML
    private ImageView imgLock4;

    @FXML
    private ImageView imgLock5;

    @FXML
    private Button btnExit;

    @FXML
    private StackPane root;

    private Runnable onFinishOut;

    
    AnimationManager animationManager = AnimationManager.getInstance();

    @Override
    public void initialize(){
        Player.getInstance().loadData();
        setEnviroment();

    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        if (event.getSource().equals(imgLevel1)) {
            AppContext.getInstance().set("level", 1);
            Player.getInstance().setNivelActual(0);
        } else if (event.getSource().equals(imgLevel2)) {
            AppContext.getInstance().set("level", 2);
            Player.getInstance().setNivelActual(1);
        } else if (event.getSource().equals(imgLevel3)) {
            AppContext.getInstance().set("level", 3);
            Player.getInstance().setNivelActual(2);
        } else if (event.getSource().equals(imgLevel4)) {
            AppContext.getInstance().set("level", 4);
            Player.getInstance().setNivelActual(3);
        } else if (event.getSource().equals(imgLevel5)) {
            AppContext.getInstance().set("level", 5);
            Player.getInstance().setNivelActual(4);
        }

        FlowController.getInstance().goView("LevelsView");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
//    private void disableCover(){
////         onFinishOut = () -> {
////            imgCover1.setDisable(true);
////        };
////        animationManager.animarFadeOut(imgCover1, onFinishOut);
////    }

    public void onActionBtnExit(ActionEvent event) {
        FlowController.getInstance().goView("StartMenuView");
        Player.getInstance().loadData();
    }

    public void setEnviroment(){

        Player.getInstance().loadData();

        imgLock2.setVisible(false);
        imgLock3.setVisible(false);
        imgLock4.setVisible(false);
        imgLock5.setVisible(false);

        animationManager.applyFloatingAnimation(imgLock2, Duration.seconds(0.5));
        animationManager.applyFloatingAnimation(imgLock3, Duration.seconds(1));
        animationManager.applyFloatingAnimation(imgLock4, Duration.seconds(1.5));
        animationManager.applyFloatingAnimation(imgLock5, Duration.seconds(2));

        if (Player.getInstance().getLevels() < 1) {
            imgLevel2.setDisable(true);
            imgLevel2.setOpacity(0.5);
            imgLock2.setVisible(true);
            imgLevel3.setDisable(true);
            imgLevel3.setOpacity(0.5);
            imgLock3.setVisible(true);
            imgLevel4.setDisable(true);
            imgLevel2.setOpacity(0.5);
            imgLock4.setVisible(true);
            imgLevel5.setDisable(true);
            imgLevel5.setOpacity(0.5);
            imgLock5.setVisible(true);
        }
        else if (Player.getInstance().getLevels() < 2) {
            imgLevel2.setOpacity(1);
            imgLevel2.setDisable(false);
            imgLevel3.setDisable(true);
            imgLevel3.setOpacity(0.5);
            imgLock3.setVisible(true);
            imgLevel4.setDisable(true);
            imgLevel4.setOpacity(0.5);
            imgLock4.setVisible(true);
            imgLevel5.setDisable(true);
            imgLevel5.setOpacity(0.5);
            imgLock5.setVisible(true);
        }
        else if (Player.getInstance().getLevels() < 3) {
            imgLevel3.setOpacity(1);
            imgLevel3.setDisable(false);
            imgLevel4.setDisable(true);
            imgLevel4.setOpacity(0.5);
            imgLock4.setVisible(true);
            imgLevel5.setDisable(true);
            imgLevel5.setOpacity(0.5);
            imgLock5.setVisible(true);
        }
        else if (Player.getInstance().getLevels() < 4) {
            imgLevel4.setOpacity(1);
            imgLevel4.setDisable(false);
            imgLevel5.setDisable(true);
            imgLevel5.setOpacity(0.5);
            imgLock5.setVisible(true);
        }
        else if (Player.getInstance().getLevels() < 5) {
            imgLevel5.setOpacity(1);
            imgLevel5.setDisable(false);
        }
    }



}


