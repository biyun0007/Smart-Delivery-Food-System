import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OrderCart {

    // Stack = LIFO
    private Stack<OrderItem> cart;

    public OrderCart() {
        cart = new Stack<>();
    }

    // Push item
    public void addItem(OrderItem item) {

        cart.push(item);

        System.out.println(item.getFoodName() +" added to cart.");
    }

    // Pop item
    public void undoLastItem() {

        if (!cart.isEmpty()) {

            OrderItem removed = cart.pop();

            System.out.println("Removed: " + removed.getFoodName());

        } else {
            System.out.println("Cart is empty.");
        }
    }

    // Confirm order
    public List<OrderItem> confirmOrder() {
        List<OrderItem> finalizedItems =new ArrayList<>();
        while (!cart.isEmpty()) {
            finalizedItems.add(cart.pop());
        }
        return finalizedItems;
    }

    public void displayCart() {
        System.out.println("\n[Current Cart]");
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        for (OrderItem item : cart) {
            System.out.println(item);
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : cart) {
            total += item.getPrice();
        }
        return total;
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}