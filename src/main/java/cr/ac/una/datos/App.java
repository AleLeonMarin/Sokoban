package cr.ac.una.datos;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import cr.ac.una.datos.util.AppContext;
import cr.ac.una.datos.util.FlowController;

public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        AppContext.getInstance().set("StartAnimation", true);
        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goMain();
    }

    public static void main(String[] args) {
        launch(args);
    }

}