package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class ReservationService {
    private static ReservationService instance; // Singleton instance
    private static final List<IRoom> rooms = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();

    private ReservationService() {

    }

    public static ReservationService getInstance() {
        if (instance == null) {
            synchronized (ReservationService.class) {
                if (instance == null) {
                    instance = new ReservationService();
                }
            }
        }
        return instance;
    }

    public static void addRoom(IRoom room) {
        // Prevent adding duplicate rooms
        if (!rooms.contains(room)) {
            rooms.add(room);
        } else {
            System.out.println("Room with the same room number already exists.");
        }
    }

    public static IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (customer == null || room == null) {
            throw new IllegalArgumentException("Cannot receive null input");
        }
        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            System.out.println("Room is already reserved for the specified period.");
            return null;
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        if (availableRooms.isEmpty()) {
            return null;
        }
        return availableRooms;
    }

    private static boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                Date reservationCheckInDate = reservation.getCheckInDate();
                Date reservationCheckOutDate = reservation.getCheckOutDate();
                if (checkInDate.before(reservationCheckOutDate) && checkOutDate.after(reservationCheckInDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printAllReservations() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
            System.out.println("----------------------------------------------------------------");
        }
    }



    // HELPER FUNCTION FOR HotelResource class
    public static Collection<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public static void printAllRooms() {
        for (IRoom room : rooms) {
            System.out.println(room.toString());
            System.out.println("----------------------------------------------------------------");
        }
    }

    public Date addWeekToDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

}
