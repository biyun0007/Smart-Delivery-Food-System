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
                runRestaurantPortal(scanner);
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
            System.out.println("9. Delete Account");
            System.out.print("Please select an option (1-9): ");

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
                case 9:
                    System.out.println("Are you sure you want to delete your account? This action cannot be undone. (Y/N)");
                    String deleteConfirm = scanner.nextLine();  
                    if (deleteConfirm.equalsIgnoreCase("Y")) {
                        managementSystem.removeUser(userID);
                        System.out.println("Your account has been deleted. Returning to main menu...");
                        inCustomerMenu = false;
                    }
                default:
                    System.out.println("Invalid choice! Select 1-9.");
            }
        }
    }
    public static void runRestaurantPortal(Scanner scanner) {
        //login/sign up process as USER
        System.out.print("Please enter your admin ID to log in: ");
        String adminID = scanner.next();
        if(managementSystem.getAdmin(adminID)==null){
            System.out.println("Admin user not found. Please try again.");
            return;
        }
        System.out.print("Enter your password: ");
        String password = scanner.next();
        if (! managementSystem.getAdmin(adminID).getAdminPassword().equals(password)) {
            System.out.println("Incorrect password. Please try again.");
            return;
        } 
        System.out.println("Admin " + managementSystem.getAdmin(adminID).getAdminName() + " logged in successfully.");

        //restaurant manager/admin portal menu
        boolean inAdminMenu = true;
        while (inAdminMenu) {   
            System.out.println("\n--- GOODTECH RESTAURANT MANAGER/ADMIN PORTAL ---");
            System.out.println("1. Add New Restaurant");
            System.out.println("2. Remove Existing Restaurant");
            System.out.println("3. View All Restaurants");
            System.out.println("4. Update Restaurant Information");
            System.out.println("5. update Restaurant Menu");
            System.out.println("6. Manage User Accounts");
            System.out.println("7. Display All Admin Accounts");
            System.out.println("8. Logout & Back");
            System.out.print("Please select an option (1-8): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            switch (choice) {
                case 1:
                    System.out.println("Adding new restaurant...");
                    String newRestaurantId= "R" + String.format("%03d", managementSystem.getTotalRestaurants()+ 1);
                    //prompt admin to enter restaurant details and add to management system
                    System.out.print("Enter restaurant name:");
                    String restaurantName = scanner.nextLine();
                    System.out.print("Enter restaurant location:");
                    String location = scanner.nextLine();
                    System.out.print("Enter food category:");
                    String foodCategory = scanner.nextLine();
                    Restaurant newRestaurant = new Restaurant(newRestaurantId, restaurantName, location, foodCategory);
                    managementSystem.addRestaurant(newRestaurant);
                    break;
                case 2:
                    System.out.println("Removing a restaurant...");
                    //prompt admin to enter restaurant ID and remove from management system
                    System.out.print("Enter restaurant ID to remove:");
                    String restaurantIdToRemove = scanner.nextLine();
                    managementSystem.removeRestaurant(restaurantIdToRemove);
                    break;
                case 3:
                    System.out.println("Displaying all restaurants...");
                    managementSystem.displayAllRestaurants();
                    break;
                case 4:
                    System.out.println("Updating restaurant information...");
                    //prompt admin to enter restaurant ID and update details
                    System.out.print("Enter restaurant ID to update:");
                    String restaurantIdToUpdate = scanner.nextLine();
                    Restaurant restaurantToUpdate = managementSystem.getRestaurant(restaurantIdToUpdate);
                    if (restaurantToUpdate != null) {
                        System.out.print("Enter new restaurant name:");
                        String newRestaurantName = scanner.nextLine();
                        System.out.print("Enter new restaurant location:");
                        String newLocation = scanner.nextLine();
                        System.out.print("Enter new food category:");
                        String newFoodCategory = scanner.nextLine();
                        restaurantToUpdate.setRestaurantName(newRestaurantName);
                        restaurantToUpdate.setLocationNode(newLocation);
                        restaurantToUpdate.setFoodCategory(newFoodCategory);
                        System.out.println("Restaurant information updated successfully.");
                    } else {
                        System.out.println("Restaurant not found.");
                    }
                    break;
                case 5:
                    //[Person 3] Update restaurant menu
                    System.out.println("Updating restaurant menu...");
                    //prompt admin to enter restaurant ID and update menu
                    System.out.print("Enter restaurant ID to update menu:");
                    String restaurantIdToUpdateMenu = scanner.nextLine();
                    Restaurant restaurantToUpdateMenu = managementSystem.getRestaurant(restaurantIdToUpdateMenu);
                    if (restaurantToUpdateMenu != null) {
                        System.out.print("Enter new menu item:");
                        String newMenuItem = scanner.nextLine();
                        restaurantToUpdateMenu.addMenuItem(newMenuItem);
                        System.out.println("Menu item added successfully.");
                    } else {
                        System.out.println("Restaurant not found.");
                    }
                    break;
                case 6:
                    // Implement user account management logic here
                    System.out.print("Remove user account by ID:");
                    String userIdToRemove = scanner.nextLine();
                    managementSystem.removeUser(userIdToRemove);
                    break;
                case 7:
                    System.out.println("Displaying all admin accounts...");
                    managementSystem.displayAllAdmins();
                    break;
                case 8:
                    System.out.println("Logging out of admin session...");
                    inAdminMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice! Select 1-8.");
            }
        }
    }
}