package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteUtil;

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


    public static Booking getBookingsByScreeningId(int screeningId) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {screeningId};
        ResultSet resultSetBookings = SQLiteUtil.executeQuery(
                "SELECT * FROM Booking\n" +
                        "WHERE screeningId = ?", preparedStatementArgs);

        ObservableList<Booking> bookings = getBookingList(resultSetBookings);

        if (bookings.size() > 0) {
            return bookings.get(0);
        } else {
            return null;
        }
    }

    /**
     * Check if a seat for a certain screening has been booked
     *
     * @param seatId      the seat id
     * @param screeningId the screening id
     * @return {@link} the booking of that seat for that screening
     * @throws SQLException SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public static Booking getBooking(int seatId, int screeningId) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                seatId,
                screeningId,
        };

        ResultSet resultSetBookings = SQLiteUtil.executeQuery(
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
        ResultSet resultSetBookings = SQLiteUtil.executeQuery("SELECT * FROM Booking", null);

        return getBookingList(resultSetBookings);
    }

    private static ObservableList<Booking> getBookingList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Booking> bookingList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Booking booking = new Booking();
            booking.setId(resultSet.getInt("id"));
            booking.setCustomerId(resultSet.getInt("customerId"));
            booking.setSeatId(resultSet.getInt("seatId"));
            booking.setScreeningId(resultSet.getInt("screeningId"));

//            booking.setScreening(ScreeningDAO.getScreeningById());
            bookingList.add(booking);
        }

        return bookingList;
    }

    public static void insertBooking(int customerId, int seatId, int screeningId) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {
                customerId,
                seatId,
                screeningId};

        SQLiteUtil.execute(
                "INSERT INTO Booking\n" +
                        "(customerId, seatId, screeningId)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteBooking(int id) throws SQLException, ClassNotFoundException {
        Object[] preparedStatementArgs = {(id)};

        SQLiteUtil.execute(
                "DELETE FROM Booking\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }
}
