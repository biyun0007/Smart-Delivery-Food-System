import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApplication {

    private static Scanner scanner = new Scanner(System.in);
    private static CityMap myMap = new CityMap();
    private static ManagementSystem managementSystem = new ManagementSystem();
    private static OrderCart cartStack = new OrderCart();
    private static OrderQueue orderQueue = new OrderQueue();
    private static NavigationSystem nav;
    private static DeliveryScheduler deliveryScheduler;
    private static List<Rider> systemRiders = new ArrayList<>();
    public static void main(String[] args) {
        // --- PERSON 1: Initialize Management System ---

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

        nav = new NavigationSystem(myMap);
        
        systemRiders.add(new Rider("R001","Rider_Amir", "KL Gate", 4.8));
        systemRiders.add(new Rider("R002","Rider_Siti", "PJ Gate", 4.9));
        systemRiders.add(new Rider("R003","Rider_Raju", "Main Library", 4.5));
        systemRiders.add(new Rider("R004","Rider_Li", "Mid Valley Mega Mall", 4.7));
        
        System.out.println("WELCOME TO SMART FOOD DELIVERY SYSTEM (GoodTech) ");
        System.out.println("Please select your portal to log in or sign up:");
        System.out.println("1. Customer Portal (Order Food & Track Delivery)");
        System.out.println("2. Restaurant Manager & Admin Portal");
        System.out.println("3. Exit System");
        System.out.print("Enter your choice (1-3): ");

        int portalChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        switch (portalChoice) {
            case 1:
                runCustomerPortal(scanner);
                break;
            case 2:
                System.out.println("\n[Restaurant Manager/Admin Portal] Feature coming soon...");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    //Customer Portal
    public static void runCustomerPortal(Scanner scanner) {
        //login/sign up process as USER
        System.out.print("Please enter your user ID to log in(Sign up by default if not found):");
        String userID = scanner.next();
        if(managementSystem.getUser(userID)==null){ 
            System.out.println("User not found. Creating new user profile...");
            System.out.print("Please enter your name:");
            String userName = scanner.next();
            System.out.print("Please enter your phone number:");
            String userPhoneNumber = scanner.next();
            System.out.print("Please enter your location (e.g., UM Central, KL Gate):");
            String userLocation = scanner.nextLine();

            String userPassword, confirmPassword;

            do{
                System.out.print("Create your password:");
                userPassword = scanner.nextLine();
                System.out.print("Confirm your password:");
                confirmPassword = scanner.nextLine();
                if (!userPassword.equals(confirmPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                } else {
                    System.out.println("User '" + userName + "' created successfully. Welcome, " + userName + "!");
                    break;
                }
            } while (true);

            managementSystem.addUser(new User(userID, userName, userPhoneNumber, userLocation, userPassword));
        }
        else{ 
            scanner.nextLine(); // Consume the newline
            do{
                System.out.print("Enter your password:");
                String userPassword = scanner.nextLine();
                if (userPassword.equals(managementSystem.getUser(userID).getUserPassword())) {
                    System.out.println("Welcome back, " + managementSystem.getUser(userID).getUserName() + "!");
                    break;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                    //scanner.nextLine(); // Consume the newline
                }
            } while (true);
        }

        //check if user wants to change delivery location
        String userLocation = managementSystem.getUser(userID).getUserAddressNode();
        System.out.println("Delivery location set to: " + userLocation);
        System.out.println("Change delivery location? (Y/N)");
        String changeLocation = scanner.nextLine();
        if (changeLocation.equalsIgnoreCase("Y")) {
            System.out.print("Please enter your new delivery location:");
            userLocation = scanner.nextLine();
            managementSystem.getUser(userID).setUserAddressNode(userLocation);
            System.out.println("Delivery location updated to: " + userLocation);
        }

        //menu for customer portal
        String restaurantID=null;
        boolean inCustomerMenu = true;
        while (inCustomerMenu) {
            System.out.println("\n--- GOODTECH CUSTOMER PORTAL ---");
            System.out.println("1. Displaying all available restaurants");
            System.out.println("2. Browse & Search Restaurant Menus");
            System.out.println("3. Choose a Restaurant to Order From");
            System.out.println("4. View Shopping Cart & Manage Orders");
            System.out.println("5. Undo Last Item Added to Cart");
            System.out.println("6. Confirm Order & Checkout");
            System.out.println("7. Track Active Order & Route Map");
            System.out.println("8. Logout & Back");
            System.out.print("Please select an option (1-8): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            switch (choice) {
                case 1:
                    System.out.println("\n[Person 1] Displaying all restaurants...");
                    managementSystem.displayAllRestaurants();
                    break;
                case 2:
                    System.out.println("\n[Person 3] Opening Food Menu Tree Search...");
                    // menuSystem.searchFoodTree();
                    break;

                case 3:
                    System.out.println("\n[Person 2] Please enter the Restaurant ID you want to order from:");
                    restaurantID = scanner.next();
                    Restaurant selectedRestaurant = managementSystem.getRestaurant(restaurantID);
                    if (selectedRestaurant != null) {
                        System.out.println("You have selected: " + selectedRestaurant.getRestaurantName());
                        // cartStack.addToCart(selectedRestaurant);
                        //display restaurant menu and allow user to add items to cart
                    } else {
                        System.out.println("Invalid Restaurant ID.");
                    }
                    break;
                case 4:
                    System.out.println("\n[Person 2] Displaying Cart.");
                    cartStack.displayCart();
                    break;
                case 5:
                    System.out.println("\n[Person 2] Press 'U' to Undo...");
                    cartStack.undoLastItem();
                    break;
                case 6:
                    if (cartStack.isEmpty()) {
                        System.out.println("Your cart is empty. Please add items before confirming your order.");
                        break;             
                    }
                    //Ask user to confirm order before proceeding to checkout
                    System.out.println("Are you sure you want to confirm your order? (Y/N)");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("\nSubmitting order to Processing Queue...");
                        Order newOrder = new Order(userID,managementSystem.getUser(userID),
                        managementSystem.getRestaurant(restaurantID), cartStack.confirmOrder());
                        orderQueue.receiveOrder(newOrder);
                        System.out.println("✅ Order confirmed and added to processing queue!");

                        //Assign delivery rider and calculate delivery route
                        System.out.println("[System] Matching optimal rider and path...");

                        String restaurantLocNode = managementSystem.getRestaurant(restaurantID).getLocationNode();

                        deliveryScheduler = new DeliveryScheduler(restaurantLocNode, nav);

                        //Add all system riders to the delivery scheduler's priority queue
                        for (Rider rider : systemRiders) {
                            deliveryScheduler.registerRider(rider);
                        }

                        Rider assignedRider = deliveryScheduler.assignBestRider();
                        
                        if (assignedRider != null) {
                            System.out.println("🛵 Successfully assigned " + assignedRider.getRiderName() + " to your order!");
                        }
                    }
                    break;
                case 7:
                    System.out.println("\n[Person 5] --- LIVE TRACKING ---");
                    nav.calculateShortestPath(managementSystem.getRestaurant(restaurantID).getLocationNode(), userLocation);
                    nav.simulateDeliveryRoute(nav.finalRoute,myMap);
                    break;
                case 8:
                    System.out.println("Logging out of customer session...");
                    inCustomerMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice! Select 1-8.");
            }
        }
    }
}