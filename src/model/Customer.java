package model;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    private static final String emailRegex = "^(.+)@(.+).com$";


    public Customer(String firstName, String lastName, String email) {
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Please enter a valid email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    // Implemented to enable access for the Reservation class
    public final String getFirstName() {
        return firstName;
    }
    // Implemented to enable access for the Reservation class
    public final String getLastName() {
        return lastName;
    }

    public final String getEmail() { return email; }

    @Override
    public String toString() {
        return "First Name: " + firstName + '\n' +
                "Last Name: " + lastName + '\n' +
                "Email : " + email;
    }
}
