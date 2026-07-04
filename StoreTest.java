
import java.util.Scanner;
import java.io.*; // Imported for File I/O
public class StoreTest {
    // Define the filename for our  data
    private static final String FILE_NAME = "store.dat";

    public static void main(String[] args) throws StoreException {
        Scanner input = new Scanner(System.in);
        Store store = null;

        // 1. if theres a file it tries to  load
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            store = (Store) ois.readObject();
            System.out.println(">>> Store data loaded successfully from file!");
        } catch (FileNotFoundException e) {
            System.out.println(">>> No saved data found. Initializing a new store with default data...");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(">>> Error reading data. Initializing a new store...");
            e.printStackTrace();
        }

        // 2. if no file is there, it creates default
        if (store == null) {
            store = new Store("My store");

            Employee employee1 = new Employee(0, "test", "123", "developer", 10000);
            Employee employee2 = new Employee(1, "admin", "1234", "manager", 12000);

            Product product1 = new Product(0, "phone", 300, 2);
            Product product2 = new Product(1, "laptop", 1000, 4);
            Product product3 = new Product(2, "headphones", 100, 1);
            Product product4 = new Product(3, "Book", 10, 10);

            Customer customer1 = new Customer(2, "customer1", "123");
            VIPCustomer customerVIP = new VIPCustomer(8, "customervip", "123");

            store.addProduct(product1);
            store.addProduct(product2);
            store.addProduct(product3);
            store.addProduct(product4);

            store.addUser(employee1);
            store.addUser(employee2);
            store.addUser(customer1);
            store.addUser(customerVIP);
        }

        Person currentUser;
        boolean isRunning = true;

        System.out.println("\nWelcome To " + store.getName());
        do {
            int choice = 0;
            System.out.println("\n1. Login");
            System.out.println("2. Sign In");
            System.out.println("3. Exit and Save");
            System.out.println("-------------------------------------------------");
            System.out.print("Please enter your choice: ");
            choice = input.nextInt();

            if (choice == 1) {
                System.out.println("\nPlease Enter Name and password:");
                System.out.print("Name: ");         String username = input.next(); input.nextLine();
                System.out.print("Password: ");     String password = input.nextLine();
                currentUser = store.login(username, password);

                if (currentUser instanceof Employee) {
                    Employee EmployeeUser = (Employee) currentUser;
                    do {
                        EmployeeUser.showMenu();
                        choice = input.nextInt();
                        System.out.println("");

                        switch (choice) {
                            case 1 -> {
                                System.out.print("Enter Product ID: "); int id = input.nextInt(); input.nextLine();
                                System.out.print("Enter Product Name: "); String productName = input.nextLine();
                                System.out.print("Enter Product Price: "); double productPrice = input.nextDouble();
                                System.out.print("Enter Product Quantity: "); int productQuantity = input.nextInt();
                                if(store.addProduct(new Product(id , productName , productPrice , productQuantity))) {
                                    System.out.println("Product " + productName + " has been added successfully.");
                                } else {
                                    System.out.println("Sorry the Inventory is full.");
                                }
                            }
                            case 2 -> {
                                System.out.print("Enter Product ID: ");  Product p =store.searchProductById(input.nextInt());
                                if(p != null) {
                                    System.out.println(p);
                                    boolean repeat = true;
                                    do {
                                        System.out.println("0. Go To Main Menu");
                                        System.out.println("1. Edit Price");
                                        System.out.println("2. Edit Stock");
                                        System.out.println("-------------------------------------------------");
                                        System.out.print("Please enter your choice: ");
                                        int editChoice = input.nextInt();
                                        switch(editChoice) {
                                            case 0 -> repeat = false;
                                            case 1 -> { System.out.print("Enter Edited Price: "); p.setPrice(input.nextDouble()); }
                                            case 2 -> { System.out.print("Enter Edited Stock: "); p.setStock(input.nextInt()); }
                                            default -> System.out.println("Invalid input! ,Try Again");
                                        }
                                    } while(repeat);
                                } else {
                                    System.out.println("Product Not Found.");
                                }
                            }
                            case 3 -> {
                                System.out.print("Enter Product ID: ");  Product p =store.searchProductById(input.nextInt());
                                if(p != null) { store.removeProduct(p.getId()); }
                                else { System.out.println("Product Not Found."); }
                            }
                            case 4 -> { store.showAllProducts(EmployeeUser); }
                            case 5 -> {
                                System.out.print("Enter Product Name: "); input.nextLine(); Product p =store.searchProductByName(input.nextLine());
                                if(p != null) { System.out.println(p); }
                                else { System.out.println("Product Not Found."); }
                            }
                            case 6 -> {
                                System.out.print("Enter Product ID: "); Product p =store.searchProductById(input.nextInt());
                                if(p != null) { System.out.println(p); }
                                else { System.out.println("Product Not Found."); }
                            }
                            case 7 -> { System.out.println("Total Inventory Value: " + store.calculateTotalInventory(store.getInventory().getFirstNode())); }
                            case 8 -> {
                                System.out.print("Enter Employee ID: ");     int employeeID = input.nextInt(); input.nextLine();
                                System.out.print("Enter Employee Name: "); String employeeName = input.nextLine();
                                System.out.print("Enter Employee Password: "); String employeePassword = input.nextLine();
                                System.out.print("Enter Employee Role: ");     String employeeRole = input.nextLine();
                                System.out.print("Enter Employee Salary: "); double employeeSalary = input.nextDouble();
                                if(store.addUser(new Employee (employeeID , employeeName, employeePassword , employeeRole, employeeSalary))) {
                                    System.out.println("The Employee " + employeeName + " has been added successfully.");
                                } else {
                                    System.out.println("Sorry, the Users Array is full.");
                                }
                            }
                            case 9 -> {
                                System.out.print("Enter VIP Customer ID: ");     int vipID = input.nextInt(); input.nextLine();
                                System.out.print("Enter VIP Customer Name: "); String vipName = input.nextLine();
                                System.out.print("Enter VIP Customer Password: "); String vipPassword = input.nextLine();
                                if(store.addUser(new VIPCustomer (vipID , vipName, vipPassword))) {
                                    System.out.println("The VIP Customer " + vipName + " has been added successfully.");
                                } else {
                                    System.out.println("Sorry, the Users Array is full.");
                                }
                            }
                            case 10 -> {
                                System.out.print("Enter User ID: ");  Person p =store.findUserByID(input.nextInt());
                                if(store.removeUser(p)) { System.out.println("User " + p.getName() + " has been Removed from the system."); }
                                else { System.out.println("Person Not Found."); }
                            }
                            case 11 -> {
                                System.out.print("Enter User ID: ");  Person p =store.findUserByID(input.nextInt());
                                if(p != null) { System.out.println(p); }
                                else { System.out.println("Person Not Found."); }
                            }
                            case 12 -> {
                                System.out.println("-------------------------------------------------");
                                store.showAllUsers();
                            }
                            case 13 -> {
                                System.out.println("Your Salary is: " + EmployeeUser.getSalary() +"\nYour Annual Bonus is: " + EmployeeUser.calculateAnnualBonus());
                            }
                            case 14 -> {
                                currentUser = null;
                                System.out.println("You've Logged Out Successfully.");
                            }
                            default -> { System.out.println("Invalid input! ,Try Again"); }
                        }
                    } while(choice != 14);

                } else if (currentUser instanceof VIPCustomer) {
                    VIPCustomer vipUser = (VIPCustomer) currentUser;
                    do {
                        vipUser.showMenu();
                        choice = input.nextInt();
                        System.out.println("");

                        switch (choice) {
                            case 1 -> { store.showAllProducts(vipUser); }
                            case 2 -> {
                                System.out.print("Enter Product Name: "); input.nextLine(); Product p =store.searchProductByName(input.nextLine());
                                if(p != null) { System.out.println(p); }
                                else { System.out.println("Product Not Found."); }
                            }
                            case 3 -> {
                                System.out.print("Enter Product ID: "); Product p =store.searchProductById(input.nextInt());
                                if(p != null) { System.out.println(p); }
                                else { System.out.println("Product Not Found."); }
                            }
                            case 4 -> {
                                store.showAllProducts(vipUser);
                                System.out.print("Enter product ID : "); Product p =  store.searchProductById(input.nextInt());
                                if(p != null) {
                                    System.out.println("Product Information: " + p);
                                    System.out.println("Are you Sure that you want to Proceed your Purchase For " + p.getName());
                                    System.out.println("1. Yes\n2. No\n-------------------------------------------------");
                                    System.out.print("Please enter your choice: "); int confirmChoice = input.nextInt();
                                    if(confirmChoice == 1) { store.purchaseProduct(vipUser, p); }
                                } else { System.out.println("Product Not Found."); }
                            }
                            case 5 -> {
                                store.showAllProducts(vipUser);
                                System.out.print("Enter product ID : "); Product p =  store.searchProductById(input.nextInt());
                                if(vipUser.saveProduct(p)) { System.out.println("Product has been saved."); }
                                else { System.out.println("Product Not Found"); }
                            }
                            case 6 -> {
                                vipUser.viewSavedProducts();
                                System.out.println("Do you want to remove a Product from the Saved Products List?\n1. Yes \n2. No\n-------------------------------------------------\nPlease enter your choice: ");
                                int removeChoice = input.nextInt();
                                if(removeChoice == 1 ) {
                                    System.out.print("Enter Product ID: "); Product removedProduct = store.searchProductById(input.nextInt());
                                    if(removedProduct != null) { vipUser.removeSavedProduct(removedProduct); }
                                    else { System.out.println("Product Not Found."); }
                                }
                            }
                            case 7 -> { vipUser.viewOrderHistory(); }
                            case 8 -> {
                                System.out.print("Enter the Amount that you want to add: "); double addBalance = input.nextDouble();
                                vipUser.addFunds(addBalance);
                                System.out.println("The amount " + addBalance + " has been added.");
                            }
                            case 9 -> {
                                System.out.println("Enter the amount of Loyalty Points you Want To Redeem: "); int points = input.nextInt();
                                vipUser.redeemLoyaltyPoints(points);
                            }
                            case 10 -> {
                                currentUser = null;
                                System.out.println("You've Logged Out Successfully.");
                            }
                        }
                    } while(choice != 10);
                } else if (currentUser instanceof Customer) {
                    Customer defaultUser = (Customer) currentUser;
                    do {
                        defaultUser.showMenu();
                        choice = input.nextInt();
                        System.out.println("");

                        switch (choice) {
                            case 1 -> { store.showAllProducts(defaultUser); }
                            case 2 -> {
                                System.out.print("Enter Product Name: "); input.nextLine(); Product p =store.searchProductByName(input.nextLine());
                                if(p != null) { System.out.println(p); }
                                else { System.out.println("Product Not Found."); }
                            }
                            case 3 -> {
                                System.out.print("Enter Product ID: "); Product p =store.searchProductById(input.nextInt());
                                if(p != null) { System.out.println(p); }
                                else { System.out.println("Product Not Found."); }
                            }
                            case 4 -> {
                                store.showAllProducts(defaultUser);
                                System.out.print("Enter product ID : "); Product p =  store.searchProductById(input.nextInt());
                                if(p != null) {
                                    System.out.println("Product Information: " + p);
                                    System.out.println("Are you Sure that you want to Proceed your Purchase For " + p.getName());
                                    System.out.println("1. Yes\n2. No\n-------------------------------------------------");
                                    System.out.print("Please enter your choice: "); int confirmChoice = input.nextInt();
                                    if(confirmChoice == 1) { store.purchaseProduct(defaultUser, p); }
                                } else { System.out.println("Product Not Found."); }
                            }
                            case 5 -> {
                                store.showAllProducts(defaultUser);
                                System.out.print("Enter product ID : "); Product p =  store.searchProductById(input.nextInt());
                                if(defaultUser.saveProduct(p)) { System.out.println("Product has been saved."); }
                                else { System.out.println("Product Not Found"); }
                            }
                            case 6 -> {
                                defaultUser.viewSavedProducts();
                                System.out.println("Do you want to remove a Product from the Saved Products List?\n1. Yes \n2. No\n-------------------------------------------------\nPlease enter your choice: ");
                                int removeChoice = input.nextInt();
                                if(removeChoice == 1 ) {
                                    System.out.println("Enter Product ID: ");Product removedProduct = store.searchProductById(input.nextInt());
                                    if(removedProduct != null) { defaultUser.removeSavedProduct(removedProduct); }
                                    else { System.out.println("Product Not Found."); }
                                }
                            }
                            case 7 -> { defaultUser.viewOrderHistory(); }
                            case 8 -> {
                                System.out.println("Enter the Amount that you want to add: "); double addBalance = input.nextDouble();
                                defaultUser.addFunds(addBalance);
                                System.out.println("\nThe amount " + addBalance + " has been added.");
                            }
                            case 9 -> {
                                currentUser = null;
                                System.out.println("You've Logged Out successfully.");
                            }
                        }
                    } while(choice != 9);
                }

            } else if (choice == 2) {
                System.out.print("Enter ID: ");     int ID = input.nextInt(); input.nextLine();
                System.out.print("Enter Name: "); String Name = input.nextLine();
                System.out.print("Enter Password: "); String Password = input.nextLine();
                System.out.println("");

                if (store.addUser(new Customer(ID, Name, Password))) {
                    System.out.println("The Customer " + Name + " has been added successfully.");
                } else {
                    System.out.println("Sorry, the Users Array is full.");
                }
            } else if (choice == 3) {

                System.out.println("Saving data...");
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                    oos.writeObject(store);
                    System.out.println("Data saved successfully. Goodbye!");
                    isRunning = false;
                } catch (IOException e) {
                    System.out.println("Failed to save data!");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid Input! Try Again");
            }
        } while (isRunning);
    }
}
