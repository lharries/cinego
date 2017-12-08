package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    private static Film getFilmFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Film film = new Film();
            film.setId(resultSet.getInt("id"));
            film.setTitle(resultSet.getString("title"));
            film.setDescription(resultSet.getString("description"));
            film.setImagePath(resultSet.getString("imagePath"));
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
            film.setScreenings(ScreeningDAO.getScreeningObservableListByFilmId(film.getId()));
            filmList.add(film);
        }

        return filmList;
    }

    public static void insertFilm(String title, String description, String imagePath) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(title),
                new PreparedStatementArg(description),
                new PreparedStatementArg(imagePath)
        };

        SQLiteConnection.execute(
                "INSERT INTO Film\n" +
                        "(title, description, imagePath)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteFilm(int id) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(id)
        };

        SQLiteConnection.execute(
                "DELETE FROM Film\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }


}