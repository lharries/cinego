package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A seat within the cinema room.
 * Contains the properties corresponding to the columns in the database for the seat.
 * Has getter and setter functions to store data retrieved using the {@link SeatDAO} class.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class Seat {

    private IntegerProperty id;
    private IntegerProperty column;
    private StringProperty row;
    private StringProperty name;

    public Seat() {
        this.id = new SimpleIntegerProperty();
        this.column = new SimpleIntegerProperty();
        this.row = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
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

    public int getColumn() {
        return column.get();
    }

    public IntegerProperty columnProperty() {
        return column;
    }

    public void setColumn(int column) {
        this.column.set(column);
    }

    public String getRow() {
        return row.get();
    }

    public StringProperty rowProperty() {
        return row;
    }

    public void setRow(String row) {
        this.row.set(row);
    }

    @Override
    public String toString() {
        return getName();
    }
}
