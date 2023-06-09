package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void adminMenu() {
        int choice;
        do {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Admin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("----------------------------------------------------------------");
            System.out.print("Please select a number for the menu option: ");
            choice = getIntInput();

            switch (choice) {
                case 1:
                    seeAllCustomers();
                    break;
                case 2:
                    seeAllRooms();
                    break;
                case 3:
                    seeAllReservations();
                    break;
                case 4:
                    addRoom();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    MainMenu.mainMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        } while (choice != 5);
    }

    private static void seeAllCustomers() {
        System.out.println("Displaying all customers...");
        System.out.println("----------------------------------------------------------------");
        Collection<Customer> customers = CustomerService.getAllCustomer();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
            System.out.println("----------------------------------------------------------------");
        }
    }

    private static void seeAllRooms() {
        System.out.println("Displaying all rooms...");
        System.out.println("----------------------------------------------------------------");
        AdminResource.displayAllRooms();
        System.out.println("----------------------------------------------------------------");
    }

    private static void seeAllReservations() {
        System.out.println("Displaying all reservations...");
        AdminResource.displayAllReservations();
    }

    private static void addRoom() {
        List<IRoom> rooms = new ArrayList<>();
        boolean done = false;
        do {
            System.out.println("Adding a room...");
            System.out.print("Enter Room Number: ");
            String roomNumber = getStringInput();
            System.out.print("Enter Room Price: ");
            Double price = getDoubleInput();
            System.out.print("Enter room Type Single or Double (s/d): ");
            RoomType roomType;
            do {
                String type = getStringInput().toLowerCase();
                if (type.equals("s")) {
                    roomType = RoomType.SINGLE;
                    break;
                } else if (type.equals("d")) {
                    roomType = RoomType.DOUBLE;
                    break;
                } else {
                    System.out.print("Please enter a valid room type (s/d): ");
                }
            } while (true);

            IRoom room = new Room(roomNumber, price, roomType);
            if (!rooms.contains(room)) {
                rooms.add(room);
                System.out.print("Would you like to add another room (y/n): ");
                String input;
                do {
                    input = getStringInput().toLowerCase();
                    if (input.equals("n")) {
                        done = true;
                        break;
                    } else if (input.equals("y")) {
                        break;
                    } else {
                        System.out.print("Please enter 'y' or 'n': ");
                    }
                } while (true);
            } else {
                System.out.println("Room already exists.");
            }
        } while (!done);

        AdminResource.addRooms(rooms);
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

    private static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    private static String getStringInput() {
        return scanner.nextLine();
    }
}
