import Data.Customer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoeDatabaseMenu {
    final Scanner sc = new Scanner(System.in);
    final Repository repository = new Repository();
    public ShoeDatabaseMenu() throws IOException {

        System.out.println("Välkommen till Tobias skoaffär!");
        System.out.println("Vad vill du göra?");
        System.out.println("1. Lägg till en beställning");
        System.out.println("2. Rapporter");

        final int menuChoice = Integer.parseInt(sc.nextLine());
        switch (menuChoice) {
            case 1 -> {
                Customer c1 = logIn();
                new AddToCart(c1);
            }
            case 2 -> new SalesSupport();
            default -> throw new IllegalArgumentException("Fel val: " + menuChoice);
        }
    }

    private Customer logIn() throws IOException {
        while(true) {
            final List<Customer> customerList = repository.getCustomers();
            List<Customer> tempCustomerList = new ArrayList<>();
            System.out.println("Ange namn");
            String name = sc.nextLine();
            if (customerList.stream().noneMatch(c -> c.getName().equals(name))) {
                System.out.println("Namnet finns inte i databasen, försök igen");
                continue;
            } else {
                tempCustomerList = customerList.stream().filter(c -> c.getName().equals(name)).toList();
            }

            System.out.println("Ange lösenord");
            String password = sc.nextLine();
            if (tempCustomerList.stream().noneMatch(c -> c.getPassword().equals(password))) {
                System.out.println("Fel lösenord!");
                continue;
            }
            return customerList.stream().filter(c -> c.getName().equals(name) && c.getPassword().equals(password)).findAny().orElse(null);
        }
    }

    public static void main(String[] args) throws IOException {
        new ShoeDatabaseMenu();
    }
}