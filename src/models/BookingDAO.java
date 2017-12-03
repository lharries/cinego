package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDAO {
    public static void main(String[] args) {
        try {
            insertBooking(1,false,3,2);
            deleteBooking(1);
            System.out.println(getBookingObservableList());

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    private static ObservableList<Booking> getBookingObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetBookings = SQLiteConnection.executeQuery("SELECT * FROM Booking", null);

        return getBookingList(resultSetBookings);
    }

    private static ObservableList<Booking> getBookingList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Booking> bookingList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Booking booking = new Booking();
            booking.setId(resultSet.getInt("id"));
            booking.setCustomerId(resultSet.getInt("customerId"));
            booking.setPaidFor(resultSet.getBoolean("paidFor"));
            booking.setSeatId(resultSet.getInt("seatId"));
            booking.setScreeningId(resultSet.getInt("screeningId"));
            bookingList.add(booking);
        }

        return bookingList;
    }

    public static void insertBooking(int customerId, boolean paidFor, int seatId, int screeningId) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(customerId, null, null),
                new PreparedStatementArg(null, null, paidFor),
                new PreparedStatementArg(seatId, null, null),
                new PreparedStatementArg(screeningId, null, null),
        };

        SQLiteConnection.execute(
                "INSERT INTO Booking\n" +
                        "(customerId, paidFor, seatId, screeningId)\n" +
                        "VALUES\n" +
                        "(?, ?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteBooking(int id) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(id, null, null)
        };

        SQLiteConnection.execute(
                "DELETE FROM Booking\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }
}
