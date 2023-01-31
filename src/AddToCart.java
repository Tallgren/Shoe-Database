import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AddToCart {

    public AddToCart() throws IOException {

        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Toby9\\IdeaProjects\\FunkProgInl1\\src\\Settings.properties"));

        try (Connection con = DriverManager.getConnection(p.getProperty("ConnectionString"),
                p.getProperty("Name"), p.getProperty("Password"))) {

            System.out.println("Ange namn och lösenord");
            /*
            CallableStatement stm = con.prepareCall("Call addToCart(?,?,?,?)");
            stm.setInt(1, 1);
            stm.setInt(2, 1);
            stm.setInt(3, 4);
            stm.setInt(4, 2);
            stm.execute();
             */


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new AddToCart();
    }
}
