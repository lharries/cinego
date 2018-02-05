package models;

import javafx.beans.property.IntegerProperty;

/**
 * Defines that all database models should have an idea where the client
 * API has the ability to get and set this id.
 */
public class DBModel {

    protected IntegerProperty id;

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public Integer getId() {
        return id.get();
    }
}
