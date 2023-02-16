package Data;
import java.util.List;

public class Order {
    protected int orderId;
    protected Customer customer;
    protected List<Shoe> shoeList;
    protected int amount;
    public Order() {}

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public List<Shoe> getShoeList() {
        return shoeList;
    }
    public void setShoeList(List<Shoe> shoeList) {
        this.shoeList = shoeList;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
