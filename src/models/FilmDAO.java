package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmDAO {

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
}
