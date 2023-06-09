package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;
import java.sql.Date;

public class HotelResource {
    private static ReservationService reservationService = ReservationService.getInstance();
    public static Customer getCustomer(String email) {
        for (Customer customer : CustomerService.getAllCustomer()) {
            if (email.equals(customer.getEmail())) {
                return customer;
            }
        }
        // Customer not found
        return null;
    }
    public static void  createACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }
    public static IRoom getRoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = CustomerService.getCustomer(customerEmail);
        if (customer == null) {
            System.out.println("Customer with email " + customerEmail + " does not exist.");
            return null;
        }
        return ReservationService.reserveARoom(customer, room,  checkInDate, checkOutDate);
    }
    public static Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = CustomerService.getCustomer(customerEmail);
        if (customer == null) {
            System.out.println("Customer with email " + customerEmail + " does not exist.");
            return Collections.emptyList();
        }
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : ReservationService.getReservations()) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.findRooms(checkIn, checkOut);
    }

    public static Date addAWeekToDate(Date date) {
        return reservationService.addWeekToDate(date);
    }


}
