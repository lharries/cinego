package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Date;

public class Screening {

    private IntegerProperty id;
    private IntegerProperty filmId;
    private ObjectProperty<Date> date;

    public Screening() {
        this.id = new SimpleIntegerProperty();
        this.filmId = new SimpleIntegerProperty();
        this.date = new SimpleObjectProperty<Date>();
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

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

}
