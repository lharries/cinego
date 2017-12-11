package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Data access object responsible for getting and saving the {@link Film} data.
 * <p>
 * Based on the DAO design pattern.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class FilmDAO {

    // TODO Log the erros

    public static void main(String[] args) {

        try {
            insertFilm("Test", "description -13;24;/' ", "image path");
            System.out.println(getFilmObservableList());

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static Film getFilmById(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLiteUtil.executeQuery("SELECT * FROM Film", null);

        // TODO deal with not being able to do .next();
        resultSet.next();
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setTitle(resultSet.getString("title"));
        film.setDescription(resultSet.getString("description"));
        film.setFileName(resultSet.getString("fileName"));
        //added
        return film;

    }

    private static Film getFilmFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Film film = new Film();
            film.setId(resultSet.getInt("id"));
            film.setTitle(resultSet.getString("title"));
            film.setDescription(resultSet.getString("description"));
            film.setFileName(resultSet.getString("fileName"));
            return film;
        } else {
            return null;
        }
    }

    public static ObservableList<Film> getFilmObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetFilms = SQLiteUtil.executeQuery("SELECT * FROM Film", null);

        return getFilmList(resultSetFilms);
    }

    private static ObservableList<Film> getFilmList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Film> filmList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Film film = new Film();
            film.setId(resultSet.getInt("id"));
            film.setTitle(resultSet.getString("title"));
            film.setDescription(resultSet.getString("description"));
            film.setFileName(resultSet.getString("fileName"));
            filmList.add(film);
        }

        return filmList;
    }

    public static void insertFilm(String title, String description, String fileName) throws SQLException, ClassNotFoundException {
        Object[] args = {title, description, fileName};

        SQLiteUtil.execute("INSERT INTO Film\n" + "(title, description, fileName)\n" + "VALUES\n" + "(?, ?, ?);", args);
    }

    public static void deleteFilm(int id) throws SQLException, ClassNotFoundException {
        Object[] args = {id};

        SQLiteUtil.execute("DELETE FROM Film\n" + "WHERE id = ?", args);
    }


    public static ResultSet getCSVResultSet() throws SQLException, ClassNotFoundException {
        ResultSet resultSetFilms = SQLiteUtil.executeQuery("SELECT film.title, film.description, screening.date, COUNT (booking.seatId) AS seatsBooked, (40- COUNT(booking.seatId)) AS seatsAvailable\n" + "FROM screening\n" + "LEFT JOIN Film ON screening.filmId = Film.id\n" + "LEFT JOIN Booking ON screening.id = Booking.screeningId\n" + "GROUP BY screening.id;", null);

        return resultSetFilms;

    }


}