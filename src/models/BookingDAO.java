package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDAO {
    public static void main(String[] args) {

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
            bookingList.add(booking);
        }

        return bookingList;
    }
}
