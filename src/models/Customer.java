package models;

import java.util.ArrayList;

public class Customer extends User {
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

    public Booking createBooking() {
        return new Booking();
    }
}
