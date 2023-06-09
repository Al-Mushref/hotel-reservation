import model.Customer;

public class Driver {
    public static void main(String[] args) {
        // TEST 1
        Customer customer = new Customer("first", "second", "j@domain.com");
        System.out.println(customer);

        // TEST 2
        Customer customer2 = new Customer("first", "second", "email");
    }
}
