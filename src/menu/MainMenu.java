package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.sql.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    private static String email;
    private static final String dateRegex = "^(0?[1-9]|1[0-9]|2[0-9]|3[01])/(0?[1-9]|1[012])/\\d{4}$";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final Pattern datePattern = Pattern.compile(dateRegex);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        int choice;
        do {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Main Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("----------------------------------------------------------------");
            System.out.print("Please select a number for the menu option: ");
            choice = getIntInput();

            switch (choice) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    AdminMenu.adminMenu();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        } while (choice != 5);
    }

    private static void findAndReserveRoom() {
        Date checkIn = getDateFromUser("Enter a check-in date in the format (dd/MM/yyyy): ");
        Date checkOut = getDateFromUser("Enter a check-out date in the format (dd/MM/yyyy): ");
        // Find and display rooms
        Collection<IRoom> rooms = HotelResource.findARoom(checkIn, checkOut);
        if (rooms == null) {
            System.out.println("We could not find a room for the specified date, but here are some recommendations");
            checkIn = HotelResource.addAWeekToDate(checkIn);
            checkOut = HotelResource.addAWeekToDate(checkOut);
            Collection<IRoom> altRooms = HotelResource.findARoom(checkIn, checkOut);
            System.out.println("Check in: " + checkIn);
            System.out.println("Check out "  + checkOut);
            for (IRoom room : altRooms) {
                System.out.println(room.toString());
            }
        } else {
            for (IRoom room : rooms) {
                System.out.println(room.toString());
            }
        }
            // Reserve a room
            String reserveChoice = getUserChoice("Would you like to reserve a room (y/n)? ");
            if (reserveChoice.equals("y")) {
                String accountChoice = getUserChoice("Do you have an account with us (y/n)? ");
                if (accountChoice.equals("y")) {
                    email = getUserEmail();
                    while (!isValidEmail(email)) {
                        System.out.print("Please enter a valid email: ");
                        email = getUserEmail();
                    }
                    System.out.print("Please enter the room number: ");
                    String roomID = getStringInput();
                    IRoom room = HotelResource.getRoom(roomID);
                    HotelResource.bookARoom(email, room, checkIn, checkOut);
                    System.out.println("Your reservation was successful.");
                } else {
                    System.out.println("You need to create an account first.");
                    createAccount();
                    findAndReserveRoom();
                }
            }
    }

    private static void seeMyReservations() {
        System.out.println("Displaying reservations...");
        List<Reservation> reservations = (List<Reservation>) HotelResource.getCustomersReservations(email);
        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
            System.out.println("----------------------------------------------------------------");
        }
    }

    private static void createAccount() {
        System.out.println("Creating an account...");
        System.out.print("Enter your First Name: ");
        String firstName = getStringInput().trim();
        System.out.print("Enter your Last Name: ");
        String lastName = getStringInput().trim();
        System.out.print("Enter your email: ");
        email = getStringInput().trim();
        HotelResource.createACustomer(email, firstName, lastName);
    }

    // HELPER FUNCTIONS
    private static boolean isValidEmail(String email) {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static String getUserChoice(String message) {
        String choice;
        do {
            System.out.print(message);
            choice = getStringInput().trim();
        } while (!choice.equals("y") && !choice.equals("n"));
        return choice;
    }

    private static String getUserEmail() {
        System.out.print("Please enter your email: ");
        return getStringInput().trim();
    }

    private static Date getDateFromUser(String prompt) {
        Date date;
        boolean valid;

        do {
            System.out.print(prompt);
            String dateStr = getStringInput().trim();
            valid = datePattern.matcher(dateStr).matches();
            if (!valid) {
                System.out.println("Invalid date format. Please try again.");
            } else {
                try {
                    java.util.Date utilDate = dateFormat.parse(dateStr);
                    date = new Date(utilDate.getTime());
                    return date;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } while (true);
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    private static String getStringInput() {
        return scanner.nextLine();
    }
}
