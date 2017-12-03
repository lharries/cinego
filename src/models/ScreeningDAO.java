package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ScreeningDAO {
    private static Screening getScreeningFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Screening screening = new Screening();
            screening.setId(resultSet.getInt("id"));
            screening.setFilmId(resultSet.getInt("filmId"));
            screening.setDate(resultSet.getDate("date"));
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
            screening.setDate(resultSet.getDate("date"));
            screeningList.add(screening);
        }

        return screeningList;
    }

    public static void insertScreening(int filmId, Date date) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(null, title, null),
                new PreparedStatementArg(null, description, null),
                new PreparedStatementArg(null, imagePath, null)
        };

        SQLiteConnection.execute(
                "INSERT INTO Screening\n" +
                        "(title, description, imagePath)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteScreening(int id) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(id, null, null)
        };

        SQLiteConnection.execute(
                "DELETE FROM Screening\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }


}
