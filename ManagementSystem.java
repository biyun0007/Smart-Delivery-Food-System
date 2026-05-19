import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class ManagementSystem {
    private LinkedList<User> userList;
    private LinkedList<Restaurant> restaurantList;
    private final String userFile = "users.txt";

    // Use HashMap for fast data retrieval
    private HashMap<String,User> userIndex;
    private HashMap<String,Restaurant> restaurantIndex;

    public ManagementSystem() {
        this.userList = new LinkedList<>();
        this.restaurantList = new LinkedList<>();
        this.userIndex = new HashMap<>();
        this.restaurantIndex = new HashMap<>();
        loadUsersFromFile();
    }

    //Restaurant Management
    public void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
        restaurantIndex.put(restaurant.getRestaurantID(), restaurant); 
        //System.out.println("Restaurant '" + restaurant.getRestaurantName() + "' added successfully.");
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
        if (userIndex.containsKey(user.getUserID())) {
            System.out.println("User ID '" + user.getUserID() + "' already exists!");
            return; 
        }

        for (User existingUser : userList) {
            if (existingUser.getUserPhoneNumber().equals(user.getUserPhoneNumber())) {
                System.out.println("Phone number '" + user.getUserPhoneNumber() + "' is already registered for user '" + existingUser.getUserName() + "'.");
                return; 
            }
        }
  
        //Add user if no duplication
        userList.add(user);
        userIndex.put(user.getUserID(), user);
        System.out.println("User '" + user.getUserName() + "' added successfully.");

        saveUsersToFile();
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
            System.out.println(r.toString()+"\n-------------------");
        }
    }

    // Save all users currently into text file
    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (User user : userList) {
                writer.write(user.getUserID() + "," + user.getUserName() + "," + user.getUserPhoneNumber() + "," + user.getUserAddressNode());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users to text file: " + e.getMessage());
        }
    }

    //Load users from text file
    private void loadUsersFromFile() {
        File file = new File(userFile);
        if (!file.exists()) {
            return; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) 
                    continue;
                
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String phone = parts[2].trim();
                    String location = parts[3].trim();
                    
                    User user = new User(id, name, phone, location);
                    
                    userList.add(user);
                    userIndex.put(id, user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users' information from file: " + e.getMessage());
        }
    }
}