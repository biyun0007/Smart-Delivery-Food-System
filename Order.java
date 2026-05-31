import java.util.List;

public class Order {
    private String orderID;
    private User customer;
    private Restaurant restaurant;
    private List<OrderItem> items;

    public Order(User customer,Restaurant restaurant,List<OrderItem> items) {
        this.orderID = "ORD" + System.currentTimeMillis(); // Simple unique ID generation
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = items;
    }

    public String getOrderID() {
        return orderID;
    }

    public User getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    // Calculate total price
    public double calculateTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {

        return "\n[Order Details]" +
                "\nOrder ID   : " + orderID +
                "\nCustomer   : " + customer.getUserName() +
                "\nRestaurant : " + restaurant.getRestaurantName() +
                "\nItems      : " + items +
                "\nTotal      : $" + calculateTotal();
    }
}