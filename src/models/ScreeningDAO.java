package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * The Data access object responsible for getting and saving the {@link Screening} data.
 * <p>
 * Based on the DAO design pattern.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class ScreeningDAO {

    public static void main(String[] args) {
        try {

            String dateString = new Date().toString();

            insertScreening(1, dateString, "hardCodedTitle");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static Screening getScreeningFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Screening screening = new Screening();
            screening.setId(resultSet.getInt("id"));
            screening.setFilmId(resultSet.getInt("filmId"));
            screening.setDate(resultSet.getString("date"));

            //added
//            screening.setFilmTitle(resultSet.getString("filmTitle"));

            return screening;
        } else {
            return null;
        }
    }

    public static ObservableList<Screening> getScreeningObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetScreenings = SQLiteConnection.executeQuery("SELECT * FROM Screening", null);

        return getScreeningList(resultSetScreenings);
    }

    private static ObservableList<Screening> getScreeningList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Screening> screeningList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Screening screening = new Screening();
            screening.setId(resultSet.getInt("id"));
            screening.setFilmId(resultSet.getInt("filmId"));
            screening.setDate(resultSet.getString("date"));
            //added
            screening.setFilmTitle(resultSet.getString("filmTitle"));
            screeningList.add(screening);
        }

        return screeningList;
    }

    public static ObservableList<Screening> getScreeningObservableListByFilmId(int filmId) throws SQLException, ClassNotFoundException {
        ResultSet resultSetScreenings = SQLiteConnection.executeQuery(
                "SELECT * FROM Screening\n" +
                        "WHERE filmId = ?", new Object[]{
                        filmId
                });

        return getScreeningList(resultSetScreenings);
    }

    public static void insertScreening(int filmId, String date, String filmTitle) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                filmId,
                date,
                filmTitle
        };

        SQLiteConnection.execute(
                "INSERT INTO Screening\n" +
                        "(filmId, date, filmTitle)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteScreening(int id) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                id
        };

        SQLiteConnection.execute(
                "DELETE FROM Screening\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }

    public static Screening getScreeningById(int id) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                id
        };

        ResultSet resultSet = SQLiteConnection.executeQuery("SELECT * FROM Screening\n" +
                "WHERE id = ?", preparedStatementArgs);

        // TODO deal with not being able to do .next();
        if (resultSet.wasNull() || !resultSet.next()) {
            return null;
        }
        Screening screening = new Screening();

        screening.setId(resultSet.getInt("id"));
        screening.setFilmId(resultSet.getInt("filmId"));
        screening.setDate(resultSet.getString("date"));
        screening.setFilmTitle(resultSet.getString("filmTitle"));

        return screening;

    }

}
