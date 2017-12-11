package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmDAO {

    // TODO Log the errors

    public static void main(String[] args) {

        try {
            insertFilm("Test", "description -13;24;/' ", "image path", "trailer URL");
            System.out.println(getFilmObservableList());

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static Film getFilmById(int id) throws SQLException, ClassNotFoundException {

        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{new PreparedStatementArg(id)};
        ResultSet resultSet = SQLiteConnection.executeQuery("SELECT * FROM Film WHERE id = ?", preparedStatementArgs);

        // TODO deal with not being able to do .next();
        resultSet.next();
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setTitle(resultSet.getString("title"));
        film.setDescription(resultSet.getString("description"));
        film.setImagePath(resultSet.getString("imagePath"));
        film.setTrailerURL(resultSet.getString("trailerURL"));

        return film;
    }

    private static Film getFilmFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Film film = new Film();
            film.setId(resultSet.getInt("id"));
            film.setTitle(resultSet.getString("title"));
            film.setDescription(resultSet.getString("description"));
            film.setImagePath(resultSet.getString("imagePath"));
            film.setTrailerURL(resultSet.getString("trailerURL"));
            return film;
        } else {
            return null;
        }
    }

    public static ObservableList<Film> getFilmObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetFilms = SQLiteConnection.executeQuery("SELECT * FROM Film", null);

        return getFilmList(resultSetFilms);
    }

    private static ObservableList<Film> getFilmList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Film> filmList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Film film = new Film();
            film.setId(resultSet.getInt("id"));
            film.setTitle(resultSet.getString("title"));
            film.setDescription(resultSet.getString("description"));
            film.setImagePath(resultSet.getString("imagePath"));
            film.setTrailerURL(resultSet.getString("trailerURL"));

            filmList.add(film);
        }

        return filmList;
    }

    public static void insertFilm(String title, String description, String imagePath, String trailerURL) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{new PreparedStatementArg(title), new PreparedStatementArg(description), new PreparedStatementArg(imagePath),new PreparedStatementArg(trailerURL)};

        SQLiteConnection.execute("INSERT INTO Film\n" + "(title, description, imagePath, trailerURL)\n" + "VALUES\n" + "(?, ?, ?, ?);", preparedStatementArgs);
    }

    public static void deleteFilm(int id) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{new PreparedStatementArg(id)};

        SQLiteConnection.execute("DELETE FROM Film\n" + "WHERE id = ?", preparedStatementArgs);
    }

    public static ResultSet getCSVResultSet() throws SQLException, ClassNotFoundException {
        ResultSet resultSetFilms = SQLiteConnection.executeQuery("SELECT film.title, film.description, screening.date, COUNT (booking.seatId) AS seatsBooked, (40- COUNT(booking.seatId)) AS seatsAvailable\n" + "FROM screening\n" + "LEFT JOIN Film ON screening.filmId = Film.id\n" + "LEFT JOIN Booking ON screening.id = Booking.screeningId\n" + "GROUP BY screening.id;", null);

        return resultSetFilms;
    }


    //TODO: add updating image url to query & method parameter
    public static void updateMovieDetails(String title, String description, String trailerURL, int movieId) throws SQLException, ClassNotFoundException {

        String query = "UPDATE Film SET title = ?, description = ? , trailerURL = ? WHERE id = ?";

        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(title),
                new PreparedStatementArg(description),
                new PreparedStatementArg(trailerURL),
                new PreparedStatementArg(movieId)
        };
        SQLiteConnection.executeUpdate(query, preparedStatementArgs);
    }





}