package cr.ac.una.datos.model;

import javafx.beans.property.SimpleStringProperty;

public class SavedLevel {
    private final SimpleStringProperty name;
    private final SimpleStringProperty date;

    public SavedLevel(String name, String date) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
    }

    public String getName() {
        return name.get();
    }

    public String getDate() {
        return date.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public void setDate(String value) {
        date.set(value);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }
}

