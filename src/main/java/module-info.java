module proyecto {

    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;
    requires MaterialFX;
    requires javafx.media;


    opens cr.ac.una.datos to javafx.fxml, javafx.graphics;
    exports cr.ac.una.datos;
    exports cr.ac.una.datos.controller;
    opens cr.ac.una.datos.controller to javafx.fxml, javafx.graphics;
    exports cr.ac.una.datos.model;
    exports cr.ac.una.datos.util;

    
}
