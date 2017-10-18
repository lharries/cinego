package main;

public class Booking {

    boolean paidFor = false;

    Seat seat;

    public Booking(boolean paidFor, Seat seat) {
        this.paidFor = paidFor;
        this.seat = seat;
    }

    public Seat getSeat() {
        return seat;
    }

    public boolean isPaidFor() {
        return paidFor;
    }
}
