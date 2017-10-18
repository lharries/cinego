package main;

import java.util.ArrayList;

public class Customer {
    private ArrayList<Booking> bookings = new ArrayList<Booking>();

    public Customer() {
    }

    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }

    public ArrayList<Booking> getUpcomingBookings() {
        // TODO
        return this.bookings;
    }

    public Booking createBooking(boolean paidFor, Seat seat) {
        return new Booking(paidFor, seat;
    }
}
