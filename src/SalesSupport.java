import Data.Order;
import Data.Shoe;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SalesSupport {
    final Scanner sc = new Scanner(System.in);
    final Repository repository = new Repository();
    final List<Order> orderList = repository.getOrderList();
    final CustomerSearchInterface sizeSearch = (o, search) -> o.getShoeList().stream().anyMatch(shoe -> shoe.getSize().getSize()==(Integer.parseInt(search)));
    final CustomerSearchInterface colorSearch = (o, search) -> o.getShoeList().stream().anyMatch(shoe -> shoe.getColor().getColor().equals(search));
    final CustomerSearchInterface brandSearch = (o, search) -> o.getShoeList().stream().anyMatch(shoe -> shoe.getBrand().getBrand().equals(search));

    public SalesSupport() throws IOException {

        System.out.println("Välkommen till supporten\n" +
                "1. Lista Kunder beroende på vad de handlat\n" +
                "2. Antal ordrar per kund\n" +
                "3. Varje kunds totalsumma\n" +
                "4. Beställningsvärde per ort\n" +
                "5. Topplista produkter");
        try {
        final int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> listClients();
            case 2 -> listOrdersPerCustomer();
            case 3 -> listCustomerTotalSum();
            default -> throw new IllegalArgumentException("Fel val: " + choice);
            }
        } catch (NumberFormatException e) {
        System.out.println("Något gick fel, avslutar");
        System.exit(0);
        }
    }

    private void listClients() {
        System.out.println("Vad vill du söka på?\n1. Storlek\n2. Färg\n3. Märke\n");
        try {
            final int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    System.out.print("Ange en storlek: ");
                    final String size = sc.nextLine();
                    printCustomers(sizeSearch, size);
                }
                case 2 -> {
                    System.out.print("Ange en färg: ");
                    final String color = sc.nextLine();
                    printCustomers(colorSearch, color);
                }
                case 3 -> {
                    System.out.print("Ange ett märke: ");
                    final String brand = sc.nextLine();
                    printCustomers(brandSearch, brand);
                }
                default -> throw new IllegalArgumentException("Fel val: " + choice);
            }
        } catch (InputMismatchException e) {
            System.out.println("Något gick fel, avslutar");
            System.exit(0);
        }
    }

    private void listOrdersPerCustomer() {
        orderList.stream().map(Order::getCustomer).distinct().forEach(s -> System.out.println(s.getName()+" "+orderList.stream().filter(c -> c.getCustomer().equals(s)).count()));
    }

    private void listCustomerTotalSum() {
        orderList.stream().map(Order::getCustomer).distinct().forEach(s -> System.out.println(s.getName() + " " + orderList.stream().filter(c -> c.getCustomer().equals(s)).flatMap(l -> l.getShoeList().stream()).mapToInt(Shoe::getPrice).sum()));
    }

    private void printCustomers(CustomerSearchInterface csi, String searchWord) {
        orderList.stream().filter(o -> csi.search(o, searchWord)).map(Order::getCustomer).distinct().forEach(c -> System.out.println(c.getName() +" - "+ c.getCity().getCity()));
    }
}
