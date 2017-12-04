package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Seat {

    private IntegerProperty id;
    private IntegerProperty xPosition;
    private IntegerProperty yPosition;
    private StringProperty name;

    public Seat() {
        this.id = new SimpleIntegerProperty();
        this.xPosition = new SimpleIntegerProperty();
        this.yPosition = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getXPosition() {
        return xPosition.get();
    }

    public IntegerProperty xPositionProperty() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition.set(xPosition);
    }

    public int getYPosition() {
        return yPosition.get();
    }

    public IntegerProperty yPositionProperty() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition.set(yPosition);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
