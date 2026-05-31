public class OrderItem {
    private String foodName;
    private double price;

    public OrderItem(String foodName, double price) {
        this.foodName = foodName;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return foodName + " ($" + price + ")";
    }
}