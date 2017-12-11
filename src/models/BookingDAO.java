package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Data access object responsible for getting and saving the {@link Booking} data.
 * <p>
 * Based on the DAO design pattern.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class BookingDAO {
    public static void main(String[] args) {
        try {
            insertBooking(1, false, 3, 2);
            deleteBooking(1);
            System.out.println(getBookingObservableList());

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    /**
     * Check if a seat for a certain screening has been booked
     *
     * @param seatId      the seat id
     * @param screeningId the screening id
     * @return {@link} the booking of that seat for that screening
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Booking getBooking(int seatId, int screeningId) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                seatId,
                screeningId,
        };

        ResultSet resultSetBookings = SQLiteConnection.executeQuery(
                "SELECT * FROM Booking\n" +
                        "WHERE seatId = ? AND screeningId = ?", preparedStatementArgs);

        ObservableList<Booking> bookings = getBookingList(resultSetBookings);

        if (bookings.size() == 1) {
            return bookings.get(0);
        } else {
            return null;
        }
    }

    public static ObservableList<Booking> getBookingObservableList() throws SQLException, ClassNotFoundException {
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

//            booking.setScreening(ScreeningDAO.getScreeningById());
            bookingList.add(booking);
        }

        return bookingList;
    }

    public static void insertBooking(int customerId, boolean paidFor, int seatId, int screeningId) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                customerId,
                paidFor,
                seatId,
                screeningId};

        SQLiteConnection.execute(
                "INSERT INTO Booking\n" +
                        "(customerId, paidFor, seatId, screeningId)\n" +
                        "VALUES\n" +
                        "(?, ?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteBooking(int id) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {(id)};

        SQLiteConnection.execute(
                "DELETE FROM Booking\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }
}
