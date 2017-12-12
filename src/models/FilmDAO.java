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

    public static Film getFilmById(int id) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {id};
        ResultSet resultSet = SQLiteUtil.executeQuery("SELECT * FROM Film WHERE id = ?", preparedStatementArgs);

        resultSet.next();
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setTitle(resultSet.getString("title"));
        film.setDescription(resultSet.getString("description"));
        film.setImageName(resultSet.getString("imageName"));
        film.setTrailerURL(resultSet.getString("trailerURL"));

        return film;
    }

    private static Film getFilmFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Film film = new Film();
            film.setId(resultSet.getInt("id"));
            film.setTitle(resultSet.getString("title"));
            film.setDescription(resultSet.getString("description"));
            film.setImageName(resultSet.getString("imageName"));
            film.setTrailerURL(resultSet.getString("trailerURL"));
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
            film.setImageName(resultSet.getString("imageName"));
            film.setTrailerURL(resultSet.getString("trailerURL"));

            filmList.add(film);
        }

        return filmList;
    }


    public static void insertFilm(String title, String description, String imageName, String trailerURL) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {title, description, imageName, trailerURL};

        SQLiteUtil.execute("INSERT INTO Film\n" + "(title, description, imageName, trailerURL)\n" + "VALUES\n" + "(?, ?, ?, ?);", preparedStatementArgs);
    }

    public static void deleteFilm(int id) throws SQLException, ClassNotFoundException {
        Object[] args = {id};

        SQLiteUtil.execute("DELETE FROM Film\n" + "WHERE id = ?", args);
    }

    public static ResultSet getCSVResultSet() throws SQLException, ClassNotFoundException {
        ResultSet resultSetFilms = SQLiteUtil.executeQuery("SELECT film.title, film.description, screening.date, COUNT (booking.seatId) AS seatsBooked, (40- COUNT(booking.seatId)) AS seatsAvailable, ((40- COUNT(booking.seatId)) / 40) AS lostSales\n" + "FROM screening\n" + "LEFT JOIN Film ON screening.filmId = Film.id\n" + "LEFT JOIN Booking ON screening.id = Booking.screeningId\n" + "GROUP BY screening.id;", null);

        return resultSetFilms;
    }

    public static void updateFilm(String title, String description, String imageName, String trailerURL, Integer id) throws SQLException, ClassNotFoundException {

        String query = "UPDATE Film SET title = ?, description = ? , imageName = ?, trailerURL = ? WHERE id = ?";

        Object[] preparedStatementArgs = {
                title,
                description,
                imageName,
                trailerURL,
                id
        };
        SQLiteUtil.executeUpdate(query, preparedStatementArgs);
    }


}