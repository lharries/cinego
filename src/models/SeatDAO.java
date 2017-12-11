package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Data access object responsible for getting and saving the {@link Seat} data.
 * <p>
 * Based on the DAO design pattern.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class SeatDAO {

    /**
     * Selects all the seats from the database
     *
     * @return observable list of all the seats
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ObservableList<Seat> getSeatObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetSeats = SQLiteConnection.executeQuery("SELECT * FROM Seat", null);

        return getSeatList(resultSetSeats);
    }

    private static ObservableList<Seat> getSeatList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Seat> seatList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Seat seat = new Seat();
            seat.setId(resultSet.getInt("id"));
            seat.setColumn(resultSet.getInt("column"));
            seat.setRow(resultSet.getString("row"));
            seat.setName(resultSet.getString("name"));
            seatList.add(seat);
        }

        return seatList;
    }

    /**
     * Inserts a new instance of the seat into the database.
     * <p>
     * Used with a for loop in order to populate the databse with seats
     *
     * @param column the column of the seat from 1-8
     * @param row    the row of the seat from A-E
     * @param name   the name of the seat in the format row column e.g. A9
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertSeat(int column, String row, String name) throws SQLException, ClassNotFoundException {
        Object[] args = {
                column,
                row,
                name
        };

        SQLiteConnection.execute(
                "INSERT INTO Seat\n" +
                        "(column, row, name)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                args
        );
    }


    /**
     * Removes the {@link Seat} from the database with id
     *
     * @param id the {@link Seat} instance to remove
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteSeat(int id) throws SQLException, ClassNotFoundException {
        Object[] args = {
                id
        };

        SQLiteConnection.execute(
                "DELETE FROM Seat\n" +
                        "WHERE id = ?",
                args
        );
    }

    /**
     * Gets the {@link Seat} instance by column number and row letter
     *
     * @param column the column that the seat is in
     * @param row    the row that the seat is in
     * @return the {@link Seat} instance
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Seat getSeatByLocation(int column, String row) throws SQLException, ClassNotFoundException {
        Object[] args = {
                column,
                row,
        };

        ResultSet resultSetSeats = SQLiteConnection.executeQuery(
                "SELECT * FROM Seat WHERE column = ? AND row = ?", args);

        if (resultSetSeats != null) {
            return getSeatList(resultSetSeats).get(0);

        } else {
            return null;
        }
    }

    /**
     * Gets the seat id
     *
     * @param id the seat id
     * @return the {@link Seat} instance
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Seat getSeatsById(int id) throws SQLException, ClassNotFoundException {


        Object[] args = {
                id
        };

        ResultSet resultSetSeats = SQLiteConnection.executeQuery(
                "SELECT * FROM Seat\n" +
                        "WHERE id = ?",
                args
        );

        if (resultSetSeats != null) {
            return getSeatList(resultSetSeats).get(0);

        } else {
            return null;
        }
    }

    /**
     * Converts the resultSet from {@link SeatDAO#getSeatObservableList()} into a {@link Seat} instance.
     *
     * @param resultSet the seat data retrieved from the database
     * @return {@link Seat} instance
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static Seat getSeatFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Seat seat = new Seat();
            seat.setId(resultSet.getInt("id"));
            seat.setColumn(resultSet.getInt("column"));
            seat.setRow(resultSet.getString("row"));
            seat.setName(resultSet.getString("name"));
            return seat;
        } else {
            return null;
        }
    }
}
