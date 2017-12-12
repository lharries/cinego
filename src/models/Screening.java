package models;

import javafx.beans.property.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * The screening of the film.
 * Contains the properties corresponding to the columns in the database for the screening.
 * Has getter and setter functions to store data retrieved from the {@link ScreeningDAO} class.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class Screening {

    public static void main(String[] args) throws ParseException {
        Screening screening = new Screening();
        screening.setDate(new Date().toString());
        System.out.println(screening.getShortDate());
    }

    private static DateFormat longFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    private static DateFormat mediumFormat = new SimpleDateFormat("HH:mm EEEE dd/MM", Locale.ENGLISH);
    private static DateFormat shortFormat = new SimpleDateFormat("EEE HH:mm", Locale.ENGLISH);
    private static DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    private IntegerProperty id;
    private IntegerProperty filmId;
    private StringProperty date;
    private StringProperty filmTitle; // TODO Remove this

    public Screening() {
        this.id = new SimpleIntegerProperty();
        this.filmId = new SimpleIntegerProperty();
        this.date = new SimpleStringProperty();
        this.filmTitle = new SimpleStringProperty();
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

    public Date getDateObject() throws ParseException {
        if (getDate() != null) {
            return longFormat.parse(getDate());
        } else {
            return null;
        }
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getFilmTitle() {
        return filmTitle.get();
    }

    public StringProperty filmTitleProperty() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle.set(filmTitle);
    }

    public String getShortDate() throws ParseException {
        Date date = getDateObject();
        return shortFormat.format(date);
    }

    public String getMediumDate() throws ParseException {
        Date date = getDateObject();
        return mediumFormat.format(date); // 08:54 Saturday 09/12
    }

    public String getTime() throws ParseException {
        Date date = getDateObject();
        return timeFormat.format(date); // 08:54
    }

    public boolean isInPast() throws ParseException {
        Date today = new Date();

        return today.compareTo(getDateObject()) > 0;
    }

    public Film getFilm() throws SQLException, ClassNotFoundException {
        return FilmDAO.getFilmById(this.getFilmId());
    }

}
