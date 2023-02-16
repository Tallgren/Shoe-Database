import Data.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
    private final Properties p = new Properties();

    public Repository() {
        try {
            p.load(new FileInputStream("C:\\Users\\Toby9\\IdeaProjects\\FunkProgInl1\\src\\Settings.properties"));
        } catch(IOException e) {
            System.out.println("Kunde inte ladda properties");
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers() throws IOException {
        List<Customer> customerList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("ConnectionString"), p.getProperty("Name"), p.getProperty("Password"))) {
            Statement stmt = con.createStatement();
            String query = "SELECT * from kund inner join stad on kund.stadid = stad.id";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("namn"));
                customer.setPassword(rs.getString("lösenord"));
                customer.setCity(new City(rs.getString("stad")));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return customerList;
    }

    public List<Shoe> getShoeList() throws IOException {
        List<Shoe> shoeList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("ConnectionString"), p.getProperty("Name"), p.getProperty("Password"))) {
            Statement stmt = con.createStatement();

            String query = "SELECT * from skor " +
                    "inner join märke\n" +
                    "on skor.märkeid = märke.id \n" +
                    "inner join färg \n" +
                    "on skor.färgid = färg.id\n" +
                    "inner join storlek \n" +
                    "on skor.storlekid = storlek.id\n";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Shoe shoe = new Shoe();
                shoe.setId(rs.getInt("Id"));
                shoe.setBrand(new Brand(rs.getString("Märke")));
                shoe.setColor(new Color(rs.getString("Färg")));
                shoe.setSize(new Size(rs.getInt("Storlek")));
                shoe.setPrice(rs.getInt("Pris"));
                shoe.setStock(rs.getInt("Lager"));
                shoeList.add(shoe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return shoeList;
    }

    public List<Order> getOrderList() throws IOException {
        List<Order> orderList = new ArrayList<>();
        List<Customer> tempCustomerList = getCustomers();
        List<Shoe> tempShoeList = getShoeList();

        try (Connection con = DriverManager.getConnection(p.getProperty("ConnectionString"), p.getProperty("Name"), p.getProperty("Password"))) {
            Statement stmt = con.createStatement();

            String query =  "select * from beställningsvara " +
                            "inner join beställning " +
                            "on beställning.id = beställningsvara.beställningsid " +
                            "inner join skor " +
                            "on beställningsvara.skoid = skor.id";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("id"));
                int customerId = rs.getInt("kundid");
                order.setCustomer(tempCustomerList.stream().filter(c -> c.getId() == customerId).findAny().orElse(null));
                int shoeId = rs.getInt("skoid");
                order.setShoeList(tempShoeList.stream().filter(c -> c.getId() == shoeId).toList());
                order.setAmount(rs.getInt("antal"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return orderList;
    }
    public void callStoredProcedure(Customer c, int o, Shoe s, int antal) {
        //addToCart(kundID, orderID, produktId, kvantitet)
        try (Connection con = DriverManager.getConnection(p.getProperty("ConnectionString"),
                p.getProperty("Name"), p.getProperty("Password"))) {
            CallableStatement stm = con.prepareCall("CALL addToCart(?,?,?,?)");
            stm.setInt(1, c.getId());
            stm.setInt(2, o);
            stm.setInt(3, s.getId());
            stm.setInt(4, antal);
            stm.executeQuery();
            System.out.println("Beställningen lyckades!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
