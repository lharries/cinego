package models;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.Date;

public class Screening {

    private IntegerProperty id;
    private IntegerProperty filmId;
    private StringProperty date;

    public Screening() {
        this.id = new SimpleIntegerProperty();
        this.filmId = new SimpleIntegerProperty();
        this.date = new SimpleStringProperty();
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

    public int getFilmId() {
        return filmId.get();
    }

    public IntegerProperty filmIdProperty() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId.set(filmId);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

}
