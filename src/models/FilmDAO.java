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

    /**
     * get the film by id
     *
     * @param id id
     * @return Film
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
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

    /**
     * get convert the results set to the film
     *
     * @param resultSet resultSet
     * @return Film
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
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

    /**
     * Get the film observable list
     *
     * @return film observable list
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static ObservableList<Film> getFilmObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetFilms = SQLiteUtil.executeQuery("SELECT * FROM Film", null);

        return getFilmList(resultSetFilms);
    }

    /**
     * convert the film result set to film observable list
     *
     * @param resultSet resultSet
     * @return films observable list
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
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


    /**
     * Insert the film into the database
     *
     * @param title       title
     * @param description description
     * @param imageName   imageName
     * @param trailerURL  trailerURL
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static void insertFilm(String title, String description, String imageName, String trailerURL) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {title, description, imageName, trailerURL};

        SQLiteUtil.execute("INSERT INTO Film\n" + "(title, description, imageName, trailerURL)\n" + "VALUES\n" + "(?, ?, ?, ?);", preparedStatementArgs);
    }

    /**
     * Delete the film from the database
     *
     * @param id filmId
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static void deleteFilm(int id) throws SQLException, ClassNotFoundException {
        Object[] args = {id};

        SQLiteUtil.execute("DELETE FROM Film\n" + "WHERE id = ?", args);
    }

    /**
     * Update the film in the database
     *
     * @param title       title
     * @param description description
     * @param imageName   imageName
     * @param trailerURL  trailerURL
     * @param id          id
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
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

    /**
     * get the data required for the csv results set
     *
     * @return results set of csv data
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static ResultSet getCSVResultSet() throws SQLException, ClassNotFoundException {
        ResultSet resultSetFilms = SQLiteUtil.executeQuery("SELECT film.title, film.description, screening.date, COUNT (booking.seatId) AS seatsBooked, (40- COUNT(booking.seatId)) AS seatsAvailable, ((40.0- (CAST(COUNT(booking.seatId) AS FLOAT)) )/ 40.0) * 100.0 AS lostSales\n" + "FROM screening\n" + "LEFT JOIN Film ON screening.filmId = Film.id\n" + "LEFT JOIN Booking ON screening.id = Booking.screeningId\n" + "GROUP BY screening.id;", null);

        return resultSetFilms;
    }


}