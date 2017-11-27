package main;

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Booking> bookings = new ArrayList<Booking>();

    public Customer(String firstName, String lastName, String email, String password, String username) {
        super(firstName, lastName, email, password, username);
    }

    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }

    public ArrayList<Booking> getUpcomingBookings() {
        // TODO
        return this.bookings;
    }

    public Booking createBooking(boolean paidFor, Seat seat) {
        return new Booking(paidFor, seat);
    }
}
