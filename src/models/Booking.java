package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.SQLException;

/**
 * A booking of a screening of a movie.
 * Contains the properties corresponding to the columns in the database for the booking.
 * Has getter and setter functions to store data retrieved from the {@link BookingDAO} class.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class Booking {

    private IntegerProperty id;
    private IntegerProperty customerId;
    private IntegerProperty seatId;
    private IntegerProperty screeningId;

    public Booking() {
        this.id = new SimpleIntegerProperty();
        this.customerId = new SimpleIntegerProperty();
        this.seatId = new SimpleIntegerProperty();
        this.screeningId = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public int getSeatId() {
        return seatId.get();
    }

    public IntegerProperty seatIdProperty() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId.set(seatId);
    }

    public int getScreeningId() {
        return screeningId.get();
    }

    public IntegerProperty screeningIdProperty() {
        return screeningId;
    }

    public void setScreeningId(int screeningId) {
        this.screeningId.set(screeningId);
    }

    /**
     * Get the customer who made the booking
     *
     * @return the {@link Customer} the customer who made the booking
     * @throws SQLException SQLException
     * @throws ClassNotFoundException ClassNotFoundException the JDBC SQLite library needs to be registered as a dependency
     */
    public Customer getCustomer() throws SQLException, ClassNotFoundException {
        return CustomerDAO.getById(getCustomerId());
    }
}
