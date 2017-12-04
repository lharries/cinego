package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatDAO {


    private static Seat getSeatFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Seat seat = new Seat();
            seat.setId(resultSet.getInt("id"));
            seat.setXPosition(resultSet.getInt("xPosition"));
            seat.setYPosition(resultSet.getInt("yPosition"));
            seat.setName(resultSet.getString("name"));
            return seat;
        } else {
            return null;
        }
    }

    public static ObservableList<Seat> getSeatObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetSeats = SQLiteConnection.executeQuery("SELECT * FROM Seat", null);

        return getSeatList(resultSetSeats);
    }

    private static ObservableList<Seat> getSeatList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Seat> seatList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Seat seat = new Seat();
            seat.setId(resultSet.getInt("id"));
            seat.setXPosition(resultSet.getInt("xPosition"));
            seat.setYPosition(resultSet.getInt("yPosition"));
            seat.setName(resultSet.getString("name"));
            seatList.add(seat);
        }

        return seatList;
    }

    public static void insertSeat(int xPosition, int yPosition, String name) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(xPosition),
                new PreparedStatementArg(yPosition),
                new PreparedStatementArg(name)
        };

        SQLiteConnection.execute(
                "INSERT INTO Seat\n" +
                        "(xPosition, yPosition, name)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteSeat(int id) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(id)
        };

        SQLiteConnection.execute(
                "DELETE FROM Seat\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }
}
