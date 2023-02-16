import Data.Customer;
import Data.Order;
import Data.Shoe;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public class AddToCart {
    final Scanner sc = new Scanner(System.in);
    final Repository repository = new Repository();
    final List<Shoe> shoeList = repository.getShoeList();
    List<Shoe> tempShoeList = new ArrayList<>();

    public AddToCart(Customer c) throws IOException {

        printShoes(shoeList);

        filterShoes(s -> true, "Välj märke: ", shoeList);
        filterShoes(s -> true, "Välj färg: ", tempShoeList);
        filterShoes(s -> true, "Välj storlek: ", tempShoeList);
        System.out.println("Vill du lägga en order på denna sko? [y/n]");
        sc.nextLine();

        if(sc.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Hur många?");
            final int antal = sc.nextInt();
            List<Order> tempOrderList = repository.getOrderList();
            final int order;

            if (tempOrderList.stream().map(Order::getShoeList).toList().contains(tempShoeList.get(0))) {
                order = tempShoeList.get(0).getId();
            } else {
                order = -1;
            }

            repository.callStoredProcedure(c,order,tempShoeList.get(0),antal);
        } else if (sc.nextLine().equalsIgnoreCase("n")) {
            System.out.println("Ordern slutfördes inte");
        } else {
            System.out.println("Du svarade något konstigt. Avslutar program");
            System.exit(0);
        }
    }
    private void filterShoes(Predicate<Shoe> filter, String prompt, List<Shoe> shoeList) {
        while(true) {
            System.out.println(prompt);
            try {
                if (prompt.equals("Välj märke: ")) {
                    String brand = sc.nextLine();
                    if (shoeList.stream().noneMatch(s -> s.getBrand().getBrand().equals(brand))) {
                        System.out.println("Märket finns inte.");
                        continue;
                    }
                    tempShoeList = shoeList.stream().filter(s -> s.getBrand().getBrand().equals(brand)).toList();
                } else if (prompt.equals("Välj färg: ")) {
                    String color = sc.nextLine();
                    if (shoeList.stream().noneMatch(s -> s.getColor().getColor().equals(color))) {
                        System.out.println("Färgen finns inte.");
                        continue;
                    }
                    tempShoeList = shoeList.stream().filter(s -> s.getColor().getColor().equals(color)).toList();
                } else {
                    int size = sc.nextInt();
                    if (shoeList.stream().noneMatch(s -> s.getSize().getSize() == size)) {
                        System.out.println("Storleken finns inte.");
                        continue;
                    }
                    tempShoeList = shoeList.stream().filter(s -> s.getSize().getSize() == size).toList();
                }
                printShoes(tempShoeList);
                return;
            } catch (InputMismatchException e) {
                System.out.println("Fel input, försök igen");
                //e.printStackTrace();
                System.exit(0);
            }
        }
    }

    private void printShoes(List<Shoe> shoeList) {
        System.out.println("Märke:\tFärg:\tStorlek:\tPris:\tLagerstatus:");
        shoeList.stream().sorted(Comparator.comparingInt(Shoe::getId)).forEach(s -> System.out.println(s.getBrand().getBrand()+"\t"
                +s.getColor().getColor()+" \t"+ s.getSize().getSize()+"\t\t\t"+s.getPrice()+" \t"+s.getStock()));
    }
}
