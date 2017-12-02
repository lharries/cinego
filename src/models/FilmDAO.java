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
            // TODO: DEALT WITH DODGY SYMBOLS e.g. -1-1
            insertFilm("Test","description -13;24;/' ","image path");
            System.out.println( getFilmObservableList());

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
            filmList.add(film);
        }

        return filmList;
    }

    public static void insertFilm(String title, String description, String imagePath) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(null, title, null),
                new PreparedStatementArg(null, description, null),
                new PreparedStatementArg(null, imagePath, null)
        };

        SQLiteConnection.execute(
                "INSERT INTO Film\n" +
                        "(title, description, imagePath)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                preparedStatementArgs
              );
    }


}