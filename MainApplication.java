import java.util.List;
import java.util.Scanner;

public class MainApplication {

    private static Scanner scanner = new Scanner(System.in);
    private static CityMap myMap = new CityMap();
    private static ManagementSystem managementSystem = new ManagementSystem();
    private static OrderCart cartStack = new OrderCart();
    private static OrderQueue orderQueue = new OrderQueue();
    private static NavigationSystem nav;
    private static DeliveryScheduler deliveryScheduler = new DeliveryScheduler();
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
            System.out.println("2. Search Food or Restaurant by Name/ID and add items to Cart");
            System.out.println("3. View Shopping Cart & Manage Orders");
            System.out.println("4. Undo Last Item Added to Cart");
            System.out.println("5. Confirm Order & Checkout");
            System.out.println("6. Track Active Order & Route Map");
            System.out.println("7. Logout & Back");
            System.out.println("8. Delete Account");
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
                    System.out.println("\nDisplaying all restaurants...");
                    managementSystem.displayAllRestaurants();
                    break;

                case 2:
                    System.out.print("\nEnter Restaurant Name, Location, or Cuisine keyword: ");
                    String searchTerm = scanner.nextLine().trim();

                    // 1. Get and store all partial keyword matches from the database
                    List<Restaurant> matches = managementSystem.searchRestaurantsByKeyword(searchTerm);

                    if (matches.isEmpty()) {
                        System.out.println("No restaurants found matching \"" + searchTerm + "\".");
                        break;
                    }

                    // This loop keeps the "Matches list" alive so users can return to it easily
                    boolean viewingShopList = true;
                    while (viewingShopList) {
                        Restaurant selectedRestaurant = null;

                        // 2. Resolve matching selections
                        if (matches.size() == 1) {
                            selectedRestaurant = matches.get(0);
                            System.out.println("\nFound: " + selectedRestaurant.getRestaurantName());
                            // Turn off list viewing since there's nowhere else to go back to
                            viewingShopList = false;
                        } else {
                            System.out.println("\n===== MATCHES FOUND FOR \"" + searchTerm.toUpperCase() + "\" =====");
                            for (int i = 0; i < matches.size(); i++) {
                                Restaurant r = matches.get(i);
                                System.out.printf("[%d] %s (%s) - %s\n", (i + 1), r.getRestaurantName(),
                                        r.getFoodCategory(), r.getLocationNode());
                            }
                            System.out.printf("[%d] Exit Search & Return to Main Portal\n", (matches.size() + 1));

                            System.out.print("Select a restaurant number: ");
                            int restNum = scanner.nextInt();
                            scanner.nextLine(); // Clear buffer

                            if (restNum > 0 && restNum <= matches.size()) {
                                selectedRestaurant = matches.get(restNum - 1);
                            } else if (restNum == matches.size() + 1) {
                                System.out.println("Exiting search...");
                                break; // Breaks the viewingShopList loop, heading back to main portal
                            } else {
                                System.out.println(" Invalid selection. Please try again.");
                                continue;
                            }
                        }

                        // 3. Prevent cross-ordering / Handle existing cart contents
                        String chosenID = selectedRestaurant.getRestaurantID();
                        if (restaurantID != null && !chosenID.equals(restaurantID) && !cartStack.isEmpty()) {
                            System.out.println("\n Warning! Your cart contains items from a different restaurant.");
                            System.out.print("Do you want to clear the previous cart and start a new order? (Y/N): ");
                            String confirmClear = scanner.nextLine().trim();

                            if (confirmClear.equalsIgnoreCase("Y")) {
                                while (!cartStack.isEmpty()) {
                                    cartStack.undoLastItem();
                                }
                                restaurantID = chosenID;
                                System.out.println(
                                        "🗑️ Cart cleared. Switched to: " + selectedRestaurant.getRestaurantName());
                            } else {
                                System.out.println("Returning to results list...");
                                continue; // Skips the menu loading and loops back to the list selection
                            }
                        } else {
                            restaurantID = chosenID;
                        }

                        // 4. Load the selected restaurant into Person 2's AVL Menu Subsystem
                        if (selectedRestaurant.getMenuItems().isEmpty()) {
                            System.out.println("This restaurant has no menu items listed yet.");
                            if (matches.size() == 1)
                                break;
                            continue;
                        }
                        menuSystem.loadFromRestaurant(selectedRestaurant);

                        // 5. Present Customer Browsing & Ordering Workflow Loop
                        boolean browsingMenu = true;
                        while (browsingMenu) {
                            System.out.println("\n---" + selectedRestaurant.getRestaurantName().toUpperCase() + "---");
                            System.out.println("1. Display sorted menu (A-Z)");
                            System.out.println("2. Search food item by name snippet");
                            System.out.println("3. Add items directly to your Cart");
                            System.out.println("4. Back to Shop List Results");
                            System.out.println("5. Back to Main Menu");
                            System.out.print("Select option (1-5): ");

                            int menuChoice = scanner.nextInt();
                            scanner.nextLine(); // Clean input stream buffer

                            switch (menuChoice) {
                                case 1:
                                    System.out.println("\n📖 ===== "
                                            + selectedRestaurant.getRestaurantName().toUpperCase() + " MENU =====");
                                    menuSystem.displaySortedMenu();
                                    break;

                                case 2:
                                    System.out.print("Enter food item name keyword to search: ");
                                    String searchName = scanner.nextLine().trim();

                                    List<FoodItem> foundItems = menuSystem.searchByKeyword(searchName);
                                    if (!foundItems.isEmpty()) {
                                        System.out.println("\n[MATCHING DISHES FOUND]:");
                                        for (FoodItem item : foundItems) {
                                            System.out.println("  • " + item.toString());
                                        }
                                    } else {
                                        System.out.println(" \"" + searchName + "\" is not available here.");
                                    }
                                    break;

                                case 3:
                                    System.out.println(
                                            "\n📥 Enter the EXACT Food Name to add to your cart (or type 'done' to finish):");
                                    while (true) {
                                        System.out.print("Item name: ");
                                        String foodName = scanner.nextLine().trim();
                                        if (foodName.equalsIgnoreCase("done")) {
                                            System.out.println("Finished adding items to cart.");
                                            break;
                                        }

                                        // Look up via exact name mapping rules (O(log n) efficiency check)
                                        FoodItem itemToAdd = menuSystem.searchByName(foodName);

                                        if (itemToAdd != null) {
                                            cartStack.addItem(new OrderItem(itemToAdd));
                                            System.out.printf("Current cart total: RM %.2f\n",
                                                    cartStack.calculateTotal());
                                        } else {
                                            System.out.println(
                                                    "Item not found. Please verify spelling from the A-Z menu selection.");
                                        }
                                    }
                                    break;

                                case 4:
                                    System.out.println("Returning to your search results roster...");
                                    browsingMenu = false; // Break out of this specific restaurant's hub loop
                                    break;

                                case 5:
                                    System.out.println("Returning to main portal...");
                                    browsingMenu = false; // Break out of this specific restaurant's hub loop
                                    viewingShopList = false; // Break out of background list loop too
                                    break;
                                default:
                                    System.out.println("Invalid choice selection.");
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.println("\nDisplaying Cart.");
                    cartStack.displayCart();
                    break;

                case 4:
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

                case 5:
                    if (cartStack.isEmpty() || restaurantID == null) {
                        System.out.println("Your cart is empty. Please add items before confirming your order.");
                        break;
                    }
                    System.out.println("\n--- ORDER CONFIRMATION ---");
                    System.out.println("You are about to place an order from: "
                            + managementSystem.getRestaurant(restaurantID).getRestaurantName());
                    System.out.printf("Delivery Location: %s\n", managementSystem.getUser(userID).getUserAddressNode());
                    System.out.println("Order Summary:");
                    cartStack.displayCart();
                    System.out.printf("Your current order total is: RM %.2f\n", cartStack.calculateTotal());
                    System.out.println("Are you sure you want to confirm your order? (Y/N): ");
                    String confirm = scanner.nextLine().trim();
                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("\nSubmitting order to Processing Queue...");

                        Order newOrder = new Order(managementSystem.getUser(userID),
                                managementSystem.getRestaurant(restaurantID), cartStack.confirmOrder());

                        orderQueue.receiveOrder(newOrder);
                        System.out.println("Order confirmed and added to processing queue!");
                        System.out.println("Your order id is: " + newOrder.getOrderID());
                        System.out
                                .println("Your order is being prepared. You can track it once it's out for delivery.");
                        // Assign delivery rider and calculate delivery route
                        System.out.println("Matching optimal rider and path...");

                        String restaurantLocNode = managementSystem.getRestaurant(restaurantID).getLocationNode();

                        // DEBUG
                        // System.out.println("\n===== RIDER DISTANCES =====");

                        // for (Rider r : deliveryScheduler.getRegisteredRiders()) {

                        // double distance =
                        // nav.calculateShortestPath(
                        // r.getCurrentLocation(),
                        // restaurantLocNode
                        // );

                        // System.out.println(
                        // r.getRiderName() + " distance = " + distance
                        // );
                        // }

                        DeliveryScheduler checkoutMatchEngine = new DeliveryScheduler(restaurantLocNode, nav);

                        for (Rider r : deliveryScheduler.getRegisteredRiders()) {
                            // for debug purposes
                            // System.out.println("Evaluating rider: " + r.getRiderName());
                            checkoutMatchEngine.registerRider(r);
                        }

                        Rider assignedRider = checkoutMatchEngine.assignBestRider();
                        if (assignedRider != null) {
                            System.out.println(
                                    "Successfully assigned " + assignedRider.getRiderName() + " to your order!");
                        }
                    } else {
                        System.out.println("Checkout cancelled. Returning to main menu.");
                    }
                    break;

                case 6:
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
                    System.out.println("Your order has been delivered! Thank you for using GOODTECH.");

                    restaurantID = null;
                    break;

                case 7:
                    System.out.println("Logging out of customer session...");
                    inCustomerMenu = false;
                    break;

                case 8:
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
                    System.out.println("Invalid choice! Select 1-8.");
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
                        managementSystem.appendMenuItemToFile(newDish);
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