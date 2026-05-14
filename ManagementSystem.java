import java.util.LinkedList;
import java.util.HashMap;

public class ManagementSystem {
    private LinkedList<User> userList;
    private LinkedList<Restaurant> restaurantList;

    // Use HashMap for fast data retrieval
    private HashMap<String,User> userIndex;
    private HashMap<String,Restaurant> restaurantIndex;

    public ManagementSystem() {
        this.userList = new LinkedList<>();
        this.restaurantList = new LinkedList<>();
        this.userIndex = new HashMap<>();
        this.restaurantIndex = new HashMap<>();
    }

    //Restaurant Management
    public void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
        restaurantIndex.put(restaurant.getRestaurantID(), restaurant); 
        System.out.println("Restaurant '" + restaurant.getRestaurantName() + "' added successfully.");
    }

    public void removeRestaurant(String restaurantID) {
        Restaurant res = restaurantIndex.get(restaurantID);
        if (res != null) {
            restaurantList.remove(res); 
            restaurantIndex.remove(restaurantID); 
            System.out.println("Restaurant ID " + restaurantID + " removed.");
        } else {
            System.out.println("Restaurant not found.");
        }
    }

    //User Management
    public void addUser(User user) {
        userList.add(user);
        userIndex.put(user.getUserID(),user);
        System.out.println("User '" + user.getUserName() + "' added successfully.");
    }
    
    public void removeUser(String userID) {
        User user = userIndex.get(userID);
        if (user != null) {
            userList.remove(user);
            userIndex.remove(userID);
            System.out.println("User ID " + userID + " removed.");
        } else {
            System.out.println("User not found.");
        }
    }

    //Provide fast access to user profiles and order details
    public User getUser(String userID) {
        return userIndex.get(userID);
    }

    public Restaurant getRestaurant(String restaurantID) {
        return restaurantIndex.get(restaurantID);  
    }

    public void displayAllRestaurants() {
        System.out.println("\nRestaurant List: ");
        for (Restaurant r : restaurantList) {
            System.out.println(r.toString());
        }
    }
}
