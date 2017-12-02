package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

public class Booking {

    private IntegerProperty id;
    private BooleanProperty paidFor;
    private IntegerProperty customerId;
    private IntegerProperty seatId;

    public Booking() {
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
}
