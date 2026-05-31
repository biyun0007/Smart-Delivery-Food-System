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
    private static MenuTree menuSystem = new MenuTree();

    public static void main(String[] args) {
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

        systemRiders.add(new Rider("R001", "Rider_Amir", "KL Gate", 4.8));
        systemRiders.add(new Rider("R002", "Rider_Siti", "PJ Gate", 4.9));
        systemRiders.add(new Rider("R003", "Rider_Raju", "Main Library", 4.5));
        systemRiders.add(new Rider("R004", "Rider_Li", "Mid Valley Mega Mall", 4.7));

        // Pre-load food menu items — inserted out of order to demonstrate AVL Tree
        // sorting
        Restaurant r001 = managementSystem.getRestaurant("R001");
        if (r001 != null) {
            r001.addMenuItem(new FoodItem("R001", "Pepperoni Pizza", 14.90, "Italian"));
            r001.addMenuItem(new FoodItem("R001", "Garlic Bread", 5.90, "Italian"));
            r001.addMenuItem(new FoodItem("R001", "Tiramisu", 8.90, "Italian"));
            r001.addMenuItem(new FoodItem("R001", "Margherita Pizza", 12.90, "Italian"));
            r001.addMenuItem(new FoodItem("R001", "BBQ Chicken Pizza", 15.90, "Italian"));
        }
        Restaurant r002 = managementSystem.getRestaurant("R002");
        if (r002 != null) {
            r002.addMenuItem(new FoodItem("R002", "Tuna Roll", 12.90, "Japanese"));
            r002.addMenuItem(new FoodItem("R002", "Gyoza", 8.90, "Japanese"));
            r002.addMenuItem(new FoodItem("R002", "Salmon Sushi", 15.90, "Japanese"));
            r002.addMenuItem(new FoodItem("R002", "Matcha Ice Cream", 7.90, "Japanese"));
            r002.addMenuItem(new FoodItem("R002", "Miso Soup", 5.90, "Japanese"));
        }
        Restaurant r003 = managementSystem.getRestaurant("R003");
        if (r003 != null) {
            r003.addMenuItem(new FoodItem("R003", "Onion Rings", 6.90, "American"));
            r003.addMenuItem(new FoodItem("R003", "Beef Burger", 13.90, "American"));
            r003.addMenuItem(new FoodItem("R003", "French Fries", 5.90, "American"));
            r003.addMenuItem(new FoodItem("R003", "Cheese Burger", 14.90, "American"));
            r003.addMenuItem(new FoodItem("R003", "Chicken Burger", 12.90, "American"));
        }
        Restaurant r004 = managementSystem.getRestaurant("R004");
        if (r004 != null) {
            r004.addMenuItem(new FoodItem("R004", "Quesadilla", 10.90, "Mexican"));
            r004.addMenuItem(new FoodItem("R004", "Beef Taco", 11.90, "Mexican"));
            r004.addMenuItem(new FoodItem("R004", "Nachos", 8.90, "Mexican"));
            r004.addMenuItem(new FoodItem("R004", "Churros", 6.90, "Mexican"));
            r004.addMenuItem(new FoodItem("R004", "Chicken Burrito", 13.90, "Mexican"));
        }
        Restaurant r005 = managementSystem.getRestaurant("R005");
        if (r005 != null) {
            r005.addMenuItem(new FoodItem("R005", "Lasagna", 15.90, "Italian"));
            r005.addMenuItem(new FoodItem("R005", "Aglio Olio", 13.90, "Italian"));
            r005.addMenuItem(new FoodItem("R005", "Penne Arrabbiata", 12.90, "Italian"));
            r005.addMenuItem(new FoodItem("R005", "Bruschetta", 7.90, "Italian"));
            r005.addMenuItem(new FoodItem("R005", "Carbonara", 14.90, "Italian"));
        }
        Restaurant r006 = managementSystem.getRestaurant("R006");
        if (r006 != null) {
            r006.addMenuItem(new FoodItem("R006", "Mango Lassi", 5.90, "Indian"));
            r006.addMenuItem(new FoodItem("R006", "Garlic Naan", 3.90, "Indian"));
            r006.addMenuItem(new FoodItem("R006", "Chicken Briyani", 14.90, "Indian"));
            r006.addMenuItem(new FoodItem("R006", "Dal Tadka", 9.90, "Indian"));
            r006.addMenuItem(new FoodItem("R006", "Butter Chicken", 13.90, "Indian"));
        }
        Restaurant r007 = managementSystem.getRestaurant("R007");
        if (r007 != null) {
            r007.addMenuItem(new FoodItem("R007", "Smoothie", 7.90, "Healthy"));
            r007.addMenuItem(new FoodItem("R007", "Greek Salad", 11.90, "Healthy"));
            r007.addMenuItem(new FoodItem("R007", "Grilled Chicken Bowl", 14.90, "Healthy"));
            r007.addMenuItem(new FoodItem("R007", "Caesar Salad", 12.90, "Healthy"));
            r007.addMenuItem(new FoodItem("R007", "Quinoa Bowl", 13.90, "Healthy"));
        }
        Restaurant r008 = managementSystem.getRestaurant("R008");
        if (r008 != null) {
            r008.addMenuItem(new FoodItem("R008", "Waffles", 10.90, "Desserts"));
            r008.addMenuItem(new FoodItem("R008", "Chocolate Lava Cake", 9.90, "Desserts"));
            r008.addMenuItem(new FoodItem("R008", "Macarons", 6.90, "Desserts"));
            r008.addMenuItem(new FoodItem("R008", "Crepe", 8.90, "Desserts"));
            r008.addMenuItem(new FoodItem("R008", "Ice Cream Sundae", 7.90, "Desserts"));
        }
        Restaurant r009 = managementSystem.getRestaurant("R009");
        if (r009 != null) {
            r009.addMenuItem(new FoodItem("R009", "Zinger Burger", 13.90, "Fast Food"));
            r009.addMenuItem(new FoodItem("R009", "Coleslaw", 4.90, "Fast Food"));
            r009.addMenuItem(new FoodItem("R009", "Spicy Chicken", 12.90, "Fast Food"));
            r009.addMenuItem(new FoodItem("R009", "Mashed Potato", 5.90, "Fast Food"));
            r009.addMenuItem(new FoodItem("R009", "Original Fried Chicken", 11.90, "Fast Food"));
        }
        Restaurant r010 = managementSystem.getRestaurant("R010");
        if (r010 != null) {
            r010.addMenuItem(new FoodItem("R010", "Tofu Stir Fry", 10.90, "Vegan"));
            r010.addMenuItem(new FoodItem("R010", "Acai Bowl", 13.90, "Vegan"));
            r010.addMenuItem(new FoodItem("R010", "Veggie Wrap", 9.90, "Vegan"));
            r010.addMenuItem(new FoodItem("R010", "Mushroom Burger", 12.90, "Vegan"));
            r010.addMenuItem(new FoodItem("R010", "Avocado Toast", 11.90, "Vegan"));
        }
        Restaurant r011 = managementSystem.getRestaurant("R011");
        if (r011 != null) {
            r011.addMenuItem(new FoodItem("R011", "Zus Cold Brew", 10.90, "Cafe"));
            r011.addMenuItem(new FoodItem("R011", "Americano", 7.90, "Cafe"));
            r011.addMenuItem(new FoodItem("R011", "Matcha Latte", 9.90, "Cafe"));
            r011.addMenuItem(new FoodItem("R011", "Croissant", 6.90, "Cafe"));
            r011.addMenuItem(new FoodItem("R011", "Caramel Latte", 9.90, "Cafe"));
        }

        // Keeps returning to portal selection after each logout
        boolean running = true;
        while (running) {
            System.out.println("\nWELCOME TO GOOD TECH SMART FOOD DELIVERY SYSTEM");
            System.out.println("Please select your portal to log in or sign up:");
            System.out.println("1. Customer Portal (Order Food & Track Delivery)");
            System.out.println("2. Restaurant Manager & Admin Portal");
            System.out.println("3. Exit System");
            System.out.print("Enter your choice (1-3): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input!");
                scanner.nextLine();
                continue;
            }

            int portalChoice = scanner.nextInt();
            scanner.nextLine();

            switch (portalChoice) {
                case 1:
                    runCustomerPortal(scanner);
                    break;
                case 2:
                    runRestaurantPortal(scanner);
                    break;
                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    // Customer Portal
    public static void runCustomerPortal(Scanner scanner) {
        // login/sign up process as USER
        System.out.print("Please enter your user ID to log in / Sign up by default if not found):");
        String userID = scanner.next();
        if (managementSystem.getUser(userID) == null) {
            System.out.println("User not found. Creating new user profile...");
            System.out.print("Please enter your name: ");
            String userName = scanner.next();
            scanner.nextLine(); // Consume the newline
            System.out.print("Please enter your phone number: ");
            String userPhoneNumber = scanner.next();
            scanner.nextLine(); // Consume the newline
            System.out.print("Please enter your location (e.g., UM Central, KL Gate): ");
            String userLocation = scanner.nextLine();

            String userPassword, confirmPassword;

            do {
                System.out.print("Create your password: ");
                userPassword = scanner.nextLine();
                System.out.print("Confirm your password: ");
                confirmPassword = scanner.nextLine();
                if (!userPassword.equals(confirmPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                } else {
                    System.out.println("User '" + userName + "' created successfully. Welcome " + userName + "!");
                    break;
                }
            } while (true);
            managementSystem.addUser(new User(userID, userName, userPhoneNumber, userLocation, userPassword));

        } else {
            scanner.nextLine(); // Consume the newline
            do {
                System.out.print("Enter your password:");
                String userPassword = scanner.nextLine();
                if (userPassword.equals(managementSystem.getUser(userID).getUserPassword())) {
                    System.out.println("Welcome back " + managementSystem.getUser(userID).getUserName() + "!");
                    break;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                    // scanner.nextLine(); // Consume the newline
                }
            } while (true);
        }

        // check if user wants to change delivery location
        String userLocation = managementSystem.getUser(userID).getUserAddressNode();
        System.out.println("Delivery location set to: " + userLocation);
        System.out.print("Change delivery location? (Y/N): ");
        String changeLocation = scanner.next().trim();
        scanner.nextLine();
        if (changeLocation.equalsIgnoreCase("Y")) {
            System.out.print("Please enter your new delivery location: ");
            userLocation = scanner.nextLine();
            managementSystem.getUser(userID).setUserAddressNode(userLocation);
            System.out.println("Delivery location updated to: " + userLocation);
        }

        // menu for customer portal
        String restaurantID = null;
        boolean inCustomerMenu = true;

        while (inCustomerMenu) {
            System.out.println("\n--- GOODTECH CUSTOMER PORTAL ---");
            System.out.println("1. Displaying all available restaurants");
            System.out.println("2. Browse & Search Restaurant Menu");
            System.out.println("3. Choose a Restaurant to Order From");
            System.out.println("4. View Shopping Cart & Manage Orders");
            System.out.println("5. Undo Last Item Added to Cart");
            System.out.println("6. Confirm Order & Checkout");
            System.out.println("7. Track Active Order & Route Map");
            System.out.println("8. Logout & Back");
            System.out.println("9. Delete Account");
            System.out.print("Please select an option (1-9): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nDisplaying all restaurants...");
                    managementSystem.displayAllRestaurants();
                    break;

                case 2:
                    System.out.print("\nEnter Restaurant ID to browse menu: ");
                    String menuRestID = scanner.nextLine().trim().toUpperCase();
                    Restaurant menuRest = managementSystem.getRestaurant(menuRestID);

                    if (menuRest == null) {
                        System.out.println("Restaurant not found.");
                        break;
                    }
                    if (menuRest.getMenuItems().isEmpty()) {
                        System.out.println("This restaurant has no menu items yet.");
                        break;
                    }

                    menuSystem.loadFromRestaurant(menuRest);

                    System.out.println("1. Display sorted menu (A-Z)");
                    System.out.println("2. Search food by name");
                    System.out.print("Select option: ");
                    int menuChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (menuChoice == 1) {
                        menuSystem.displaySortedMenu();
                    } else if (menuChoice == 2) {
                        System.out.print("Enter food name to search: ");
                        String searchName = scanner.nextLine().trim();
                        FoodItem found = menuSystem.searchByName(searchName);
                        if (found != null) {
                            System.out.println("\nItem found:");
                            System.out.println("  " + found.toString());
                        } else {
                            System.out.println("\"" + searchName + "\" not found in this menu.");
                        }
                    }
                    break;

                case 3:
                    System.out.print("\nPlease enter the Restaurant ID you want to order from: ");
                    String chosenID = scanner.nextLine().trim();

                    Restaurant selectedRestaurant = managementSystem.getRestaurant(chosenID);
                    if (selectedRestaurant != null) {
                        // If user switches restaurants, clear the previous cart
                        if (restaurantID != null && !chosenID.equals(restaurantID) && !cartStack.isEmpty()) {
                            System.out.println("Warning! Your cart contains items from a different restaurant. ");
                            System.out.print("Do you want to clear the previous cart and start a new order? (Y/N): ");
                            String confirmClear = scanner.nextLine().trim();
                            if (confirmClear.equalsIgnoreCase("Y")) {
                                while (!cartStack.isEmpty()) {
                                    cartStack.undoLastItem();
                                }
                                restaurantID = chosenID;
                                System.out.println(
                                        "Cart cleared. Switched to: " + selectedRestaurant.getRestaurantName());
                            } else {
                                System.out.println("Returning to main menu without changing restaurant.");
                                break;
                            }
                        } else {
                            restaurantID = chosenID;
                            System.out.println("You have selected: " + selectedRestaurant.getRestaurantName());
                        }
                    } else {
                        System.out.println("Invalid Restaurant ID.");
                    }
                    break;

                case 4:
                    System.out.println("\nDisplaying Cart.");
                    cartStack.displayCart();
                    break;

                case 5:
                    if (cartStack.isEmpty()) {
                        System.out.println("Your cart is already empty! Nothing to undo.");
                    } else {
                        System.out.print("Are you sure you want to remove the last added item? (Y/N): ");
                        String confirmUndo = scanner.nextLine().trim();
                        if (confirmUndo.equalsIgnoreCase("Y")) {
                            cartStack.undoLastItem();
                        } else {
                            System.out.println("Undo cancelled.");
                        }
                    }
                    break;

                case 6:
                    if (cartStack.isEmpty() || restaurantID == null) {
                        System.out.println("Your cart is empty. Please add items before confirming your order.");
                        break;
                    }

                    System.out.println("Are you sure you want to confirm your order? (Y/N): ");
                    String confirm = scanner.nextLine().trim();
                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("\nSubmitting order to Processing Queue...");

                        Order newOrder = new Order(userID, managementSystem.getUser(userID),
                                managementSystem.getRestaurant(restaurantID), cartStack.confirmOrder());

                        orderQueue.receiveOrder(newOrder);
                        System.out.println("Order confirmed and added to processing queue!");

                        // Assign delivery rider and calculate delivery route
                        System.out.println("Matching optimal rider and path...");

                        String restaurantLocNode = managementSystem.getRestaurant(restaurantID).getLocationNode();

                        deliveryScheduler = new DeliveryScheduler(restaurantLocNode, nav);

                        // Add all system riders to the delivery scheduler's priority queue
                        for (Rider rider : systemRiders) {
                            deliveryScheduler.registerRider(rider);
                        }

                        Rider assignedRider = deliveryScheduler.assignBestRider();

                        if (assignedRider != null) {
                            System.out.println(
                                    "Successfully assigned " + assignedRider.getRiderName() + " to your order!");
                        }
                    } else {
                        System.out.println("Checkout cancelled. Returning to main menu.");
                    }
                    break;

                case 7:
                    if (restaurantID == null) {
                        System.out.println("No active order found. Please place an order first.");
                        break;
                    }

                    Restaurant currentRest = managementSystem.getRestaurant(restaurantID);
                    if (currentRest == null) {
                        System.out.println("Error! Restaurant not found for tracking.");
                        break;
                    }

                    System.out.println("\n--- LIVE TRACKING ---");
                    nav.calculateShortestPath(managementSystem.getRestaurant(restaurantID).getLocationNode(),
                            userLocation);
                    nav.simulateDeliveryRoute(nav.finalRoute, myMap);

                    restaurantID = null;
                    break;

                case 8:
                    System.out.println("Logging out of customer session...");
                    inCustomerMenu = false;
                    break;

                case 9:
                    System.out.println(
                            "Are you sure you want to delete your account? This action cannot be undone. (Y/N): ");
                    String deleteConfirm = scanner.nextLine().trim();
                    if (deleteConfirm.equalsIgnoreCase("Y")) {
                        managementSystem.removeUser(userID);
                        System.out.println("Your account has been permanently deleted.");
                        inCustomerMenu = false;
                    } else {
                        System.out.println("Account deletion cancelled. Returning to main menu.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice! Select 1-9.");
            }
        }
    }

    public static void runRestaurantPortal(Scanner scanner) {
        System.out.print("Please enter your admin ID to log in: ");
        String adminID = scanner.next();
        if (managementSystem.getAdmin(adminID) == null) {
            System.out.println("Admin ID " + adminID + " not found. Please try again.");
            return;
        }
        System.out.print("Enter your password: ");
        String password = scanner.next();

        if (!managementSystem.getAdmin(adminID).getAdminPassword().equals(password)) {
            System.out.println("Incorrect password. Please try again.");
            return;
        }
        System.out.println("Admin " + managementSystem.getAdmin(adminID).getAdminName() + " logged in successfully.");

        boolean inAdminMenu = true;
        while (inAdminMenu) {
            System.out.println("\n--- GOODTECH RESTAURANT MANAGER/ADMIN PORTAL ---");
            System.out.println("1. Add New Restaurant");
            System.out.println("2. Remove Existing Restaurant");
            System.out.println("3. View All Restaurants");
            System.out.println("4. Update Restaurant Information");
            System.out.println("5. Update Restaurant Menu");
            System.out.println("6. Manage User Accounts");
            System.out.println("7. Display All Admin Accounts");
            System.out.println("8. Logout & Back");
            System.out.print("Please select an option (1-8): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Adding new restaurant...");
                    int counter = managementSystem.getTotalRestaurants() + 1;
                    String newRestaurantId;
                    do {
                        newRestaurantId = "R" + String.format("%03d", counter++);
                    } while (managementSystem.getRestaurant(newRestaurantId) != null);

                    System.out.print("Enter restaurant name: ");
                    String restaurantName = scanner.nextLine().trim();
                    System.out.print("Enter restaurant location: ");
                    String location = scanner.nextLine().trim();
                    System.out.print("Enter food category: ");
                    String foodCategory = scanner.nextLine().trim();

                    Restaurant newRestaurant = new Restaurant(newRestaurantId, restaurantName, location, foodCategory);
                    managementSystem.addRestaurant(newRestaurant);
                    System.out.println(
                            "Successfully registered restaurant " + restaurantName + " with ID: " + newRestaurantId);
                    break;

                case 2:
                    System.out.println("Removing a restaurant...");

                    System.out.print("Enter restaurant ID to remove: ");
                    String restaurantIdToRemove = scanner.nextLine().trim();
                    if (managementSystem.getRestaurant(restaurantIdToRemove) != null) {
                        managementSystem.removeRestaurant(restaurantIdToRemove);
                        System.out.println("Restaurant removed successfully.");
                    } else {
                        System.out.println("Restaurant ID " + restaurantIdToRemove + " not found.");
                    }
                    break;

                case 3:
                    System.out.println("Displaying all restaurants...");
                    managementSystem.displayAllRestaurants();
                    break;

                case 4:
                    System.out.println("Updating restaurant information...");

                    System.out.print("Enter restaurant ID to update: ");
                    String restaurantIdToUpdate = scanner.nextLine().trim();
                    Restaurant restaurantToUpdate = managementSystem.getRestaurant(restaurantIdToUpdate);
                    if (restaurantToUpdate != null) {
                        System.out.print("Enter new name (Current: " + restaurantToUpdate.getRestaurantName() + "): ");
                        String newRestaurantName = scanner.nextLine().trim();
                        System.out
                                .print("Enter new map node (Current: " + restaurantToUpdate.getLocationNode() + "): ");
                        String newLocation = scanner.nextLine().trim();
                        System.out
                                .print("Enter new category (Current: " + restaurantToUpdate.getFoodCategory() + "): ");
                        String newFoodCategory = scanner.nextLine().trim();

                        restaurantToUpdate.setRestaurantName(newRestaurantName);
                        restaurantToUpdate.setLocationNode(newLocation);
                        restaurantToUpdate.setFoodCategory(newFoodCategory);
                        System.out.println("Restaurant " + restaurantIdToUpdate + " details updated successfully.");
                    } else {
                        System.out.println("Restaurant not found.");
                    }
                    break;

                case 5:
                    System.out.println("Updating restaurant menu...");

                    System.out.print("Enter restaurant ID to update menu:");
                    String restaurantIdToUpdateMenu = scanner.nextLine().trim();
                    Restaurant restaurantToUpdateMenu = managementSystem.getRestaurant(restaurantIdToUpdateMenu);

                    if (restaurantToUpdateMenu != null) {
                        System.out.print("Enter new food item name: ");
                        String itemName = scanner.nextLine().trim();

                        System.out.print("Enter price for " + itemName + ": ");
                        while (!scanner.hasNextDouble()) {
                            System.out.print("Invalid price number! Try again: ");
                            scanner.nextLine();
                        }
                        double itemPrice = scanner.nextDouble();
                        scanner.nextLine();

                        FoodItem newDish = new FoodItem(restaurantIdToUpdateMenu, itemName, itemPrice,
                                restaurantToUpdateMenu.getFoodCategory());
                        restaurantToUpdateMenu.addMenuItem(newDish);
                        System.out.println("'" + itemName + "' (RM " + itemPrice + ") successfully added to "
                                + restaurantToUpdateMenu.getRestaurantName() + "'s menu.");
                    } else {
                        System.out.println("Restaurant not found.");
                    }
                    break;

                case 6:
                    // Implement user account management logic here
                    System.out.print("Remove user account by ID: ");
                    String userIdToRemove = scanner.nextLine().trim();
                    if (managementSystem.getUser(userIdToRemove) != null) {
                        managementSystem.removeUser(userIdToRemove);
                        System.out.println("User ID " + userIdToRemove + " has been removed.");
                    } else {
                        System.out.println("User ID not found.");
                    }
                    break;

                case 7:
                    System.out.println("Displaying active admin accounts...");
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