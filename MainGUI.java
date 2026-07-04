

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainGUI extends JFrame {

    public static Store store;
    private static final String FILE_NAME = "store.dat";

    private JButton signInButton;
    private JButton loginButton;
    private JButton exitButton;

    public MainGUI() {
        setTitle("E-commerce Store Management System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 15, 15));
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to the Store", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        signInButton = new JButton("Sign in (New User)");
        loginButton = new JButton("Login (Existing User)");
        exitButton = new JButton("Exit and Save");

        signInButton.addActionListener(e -> openRegistrationForm());

        loginButton.addActionListener(e -> {
            new InputFrame();
            dispose();
        });

        exitButton.addActionListener(e -> {
            saveData();
            System.exit(0);
        });

        add(welcomeLabel);
        add(signInButton);
        add(loginButton);
        add(exitButton);
    }

    private void openRegistrationForm() {
        JFrame regFrame = new JFrame("New User Registration");
        regFrame.setSize(350, 250);
        regFrame.setLayout(new GridLayout(4, 2, 10, 10));
        regFrame.setLocationRelativeTo(this);

        JTextField idField = new JTextField();
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton register = new JButton("Register");

        regFrame.add(new JLabel("ID:"));
        regFrame.add(idField);
        regFrame.add(new JLabel("Username:"));
        regFrame.add(userField);
        regFrame.add(new JLabel("Password:"));
        regFrame.add(passField);
        regFrame.add(new JLabel(""));
        regFrame.add(register);

        register.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = userField.getText();
                String password = new String(passField.getPassword());
                
                if (store.addUser(new Customer(id, name, password))) {
                    JOptionPane.showMessageDialog(regFrame, "Account created successfully for " + name);
                    regFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(regFrame, "Error: System storage is full.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(regFrame, "Invalid input! Please check the ID and fields.");
            }
        });

        regFrame.setVisible(true);
    }

    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(store);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws StoreException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            store = (Store) ois.readObject();
            System.out.println(">>> Store data loaded successfully from file!");
        } catch (FileNotFoundException e) {
            System.out.println(">>> No saved data found. Initializing a new store with default data...");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(">>> Error reading data. Initializing a new store...");
            e.printStackTrace();
        }

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

        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }
}