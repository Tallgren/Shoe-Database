import Data.Customer;
import Data.Order;
import Data.Shoe;

@FunctionalInterface
public interface CustomerSearchInterface {
    boolean search(Order o, String searchWord);

}
