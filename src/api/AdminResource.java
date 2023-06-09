package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }
    public static void addRooms(List<IRoom> rooms) {
        if (rooms == null) {
            throw new IllegalArgumentException("Invalid rooms input");
        }
        for (IRoom room : rooms) {
            ReservationService.addRoom(room);
        }
    }
    public static Collection<Customer> getllCustomers() {
        return CustomerService.getAllCustomer();
    }
    public static void displayAllReservations() {
        ReservationService.printAllReservations();
    }
    public static void displayAllRooms() {
        ReservationService.printAllRooms();
    }
}
