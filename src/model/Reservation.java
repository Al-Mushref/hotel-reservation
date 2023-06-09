package model;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (customer == null || room == null) {
            throw new IllegalArgumentException("Cannot receive null input");
        }
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public final Customer getCustomer() {
        return customer;
    }

    public final IRoom getRoom() {
        return room;
    }

    public final Date getCheckInDate() {
        return checkInDate;
    }

    public final Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "Reservation Details:\n" +
                "Customer: " + customer.getFirstName() + " " + customer.getLastName() + "\n" +
                "Room: " + room.toString() + "\n" +
                "Check-in Date: " + dateFormat.format(checkInDate) + "\n" +
                "Check-out Date: " + dateFormat.format(checkOutDate);
    }
}
