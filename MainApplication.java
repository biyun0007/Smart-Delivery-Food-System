import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CityMap myMap = new CityMap();
        
        // --- PERSON 1: Initialize Management System ---
        ManagementSystem managementSystem = new ManagementSystem();
        
        managementSystem.addRestaurant(new Restaurant("R001", "Pizza Place", "Mid Valley Megamall Food Court", "Italian"));
        managementSystem.addRestaurant(new Restaurant("R002", "Sushi Spot", "Mid Valley Megamall Food Court", "Japanese"));
        managementSystem.addRestaurant(new Restaurant("R003", "Burger Joint", "KL Gateway Mall", "American"));
        managementSystem.addRestaurant(new Restaurant("R004", "Taco Fiesta", "Mid Valley Megamall Food Court", "Mexican"));
        managementSystem.addRestaurant(new Restaurant("R005", "Pasta House", "KL Gateway Mall", "Italian"));
        managementSystem.addRestaurant(new Restaurant("R006", "Curry Corner", "KL Gateway Mall", "Indian"));
        managementSystem.addRestaurant(new Restaurant("R007", "Salad Bar", "Mid Valley Megamall Food Court", "Healthy"));
        managementSystem.addRestaurant(new Restaurant("R008", "Dessert Haven", "Mid Valley Megamall Food Court", "Desserts"));
        managementSystem.addRestaurant(new Restaurant("R009", "KFC", "KFC University", "Fast Food"));
        managementSystem.addRestaurant(new Restaurant("R010", "Vegan Delights", "Mid Valley Megamall Food Court", "Vegan"));    
        managementSystem.addRestaurant(new Restaurant("R011", "Zus Coffee", "Zus Coffee University Malaya", "Cafe"));

        // --- PERSON 5: Setup the Map Data ---
        myMap.addLocation("UM Central");
        myMap.addLocation("KK12 Hostel");
        myMap.addLocation("Faculty of Computer Science");
        myMap.addLocation("Main Library");
        myMap.addLocation("DTC");
        myMap.addLocation("KL Gateway Mall");
        myMap.addLocation("KL Gate");
        myMap.addLocation("Mid Valley Megamall Food Court");
        myMap.addLocation("KFC University");
        myMap.addLocation("Zus Coffee University Malaya");
        myMap.addLocation("PJ Gate");


        myMap.addRoad("UM Central", "DTC", 0.8, true); // oneway
        myMap.addRoad("UM Central", "Faculty of Computer Science", 1.3, false); // Two-way
        myMap.addRoad("UM Central", "Main Library", 0.45, true); // oneway
        myMap.addRoad("DTC", "KL Gate", 1.4, true); 
        myMap.addRoad("DTC", "KK12 Hostel", 0.9, true);
        myMap.addRoad("KK12 Hostel", "KL Gate", 1.0, true);
        myMap.addRoad("KK12 Hostel", "Faculty of Computer Science", 2.5, false);
        myMap.addRoad("Zus Coffee University Malaya", "UM Central", 0.35, true);
        myMap.addRoad("Zus Coffee University Malaya", "DTC", 0.65, true);
        myMap.addRoad("PJ Gate", "UM Central", 0.75, true);
        myMap.addRoad("Main Library", "Zus Coffee University Malaya", 0.1, true);
        myMap.addRoad("KL Gate", "Main Library", 1.1, true);
        myMap.addRoad("PJ Gate", "Main Library", 0.35, true);
        myMap.addRoad("KL Gate", "PJ Gate", 1.3, true);
        myMap.addRoad("KL Gate", "Mid Valley Megamall Food Court", 3.2, false);
        myMap.addRoad("KL Gate", "KL Gateway Mall", 1.8, false);
        myMap.addRoad("KL Gateway Mall", "Mid Valley Megamall Food Court", 2.0, false);
        myMap.addRoad("KL Gateway Mall", "KFC University", 4.0, false);
        myMap.addRoad("KL Gateway Mall", "KL Gate", 3.7, false);
        myMap.addRoad("PJ Gate", "KFC University", 1.0, false);

        NavigationSystem nav = new NavigationSystem(myMap);

        //login/sign up process
        System.out.println("Please enter your user ID to log in(Sign up by default if not found):");
            String userID = scanner.next();
            if(managementSystem.getUser(userID)==null){ 
                System.out.println("User not found. Creating new user profile...");
                System.out.println("Please enter your name:");
                String userName = scanner.next();
                System.out.println("Please enter your phone number:");
                String userPhoneNumber = scanner.next();
                System.out.println("Please enter your location (e.g., UM Central, KL Gate):");
                String userLocation = scanner.next();
                managementSystem.addUser(new User(userID, userName, userPhoneNumber, userLocation));
            }
            else{
                System.out.println("Welcome back, " + managementSystem.getUser(userID).getUserName() + "!");
            }
        System.out.println("\n=== SMART FOOD DELIVERY SYSTEM (GoodTech) ===");
        
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Manage Users/Restaurants (Person 1)");
            System.out.println("2. Search & View Menu (Person 3)");
            System.out.println("3. Place Order & Undo (Person 2)");
            System.out.println("4. Assign Delivery Rider (Person 4)");
            System.out.println("5. Calculate Delivery Route (Person 5)");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:


                    managementSystem.displayAllRestaurants();
                    break;
                case 2:
                    System.out.println("Calling Person 3's AVL Tree Search...");
                    break;
                case 3:
                    System.out.println("Calling Person 2's Order Queue & Stack...");
                    break;
                case 4:
                    System.out.println("Calling Person 4's Priority Queue Rider Match...");
                    break;
                case 5:
                    nav.calculateShortestPath("Restaurant", "Customer");
                    break;
                case 6:
                    System.out.println("Exiting System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}