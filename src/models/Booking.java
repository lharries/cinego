package models;

import javafx.beans.property.*;

public class Booking {

    private IntegerProperty id;
    private BooleanProperty paidFor;
    private IntegerProperty customerId;
    private IntegerProperty seatId;
    private IntegerProperty screeningId;
    private ObjectProperty<Screening> screening;
    private ObjectProperty<Seat> seat;
    private ObjectProperty<Customer> customer;

    public Booking() {
        this.id = new SimpleIntegerProperty();
        this.paidFor = new SimpleBooleanProperty();
        this.customerId = new SimpleIntegerProperty();
        this.seatId = new SimpleIntegerProperty();
        this.screeningId = new SimpleIntegerProperty();
        this.screening = new SimpleObjectProperty<>();
        this.seat = new SimpleObjectProperty<>();
        this.customer = new SimpleObjectProperty<>();
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

    public boolean isPaidFor() {
        return paidFor.get();
    }

    public BooleanProperty paidForProperty() {
        return paidFor;
    }

    public void setPaidFor(boolean paidFor) {
        this.paidFor.set(paidFor);
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

    public Screening getScreening() {
        return screening.get();
    }

    public ObjectProperty<Screening> screeningProperty() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening.set(screening);
    }

    public Seat getSeat() {
        return seat.get();
    }

    public ObjectProperty<Seat> seatProperty() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat.set(seat);
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }
}
