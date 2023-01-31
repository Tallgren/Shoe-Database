public class Order {

    Customer customer;
    Shoe shoe;
    int amount;

    public Order(Customer customer, Shoe shoe, int amount) {
        this.customer = customer;
        this.shoe = shoe;
        this.amount = amount;
    }
}
