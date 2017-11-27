package models;

public class Booking {
    boolean paidFor = false;
    Seat seat;

    public Booking(){

    }

    public Booking(boolean paidFor, Seat seat) {
        this.paidFor = paidFor;
        this.seat = seat;
    }
}
