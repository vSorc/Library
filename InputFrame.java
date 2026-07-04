

import javax.swing.*;
import java.awt.*;

public class InputFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public InputFrame() {
        setTitle("User Input");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Name:");
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton saveExitButton = new JButton("Exit and Save");

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(new JLabel(""));
        add(loginButton);
        add(new JLabel(""));
        add(saveExitButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Person currentUser = MainGUI.store.login(username, password);

            if (currentUser != null) {
                JOptionPane.showMessageDialog(this, "Welcome To " + MainGUI.store.getName() + "\nLogged in as: " + currentUser.getName());
                dispose();

                if (currentUser instanceof Employee) {
                    new EmployeeFrame((Employee) currentUser);
                } else if (currentUser instanceof VIPCustomer) {
                    new VIPCustomerFrame((VIPCustomer) currentUser);
                } else if (currentUser instanceof Customer) {
                    new CustomerFrame((Customer) currentUser);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Name or Password. Try again.");
            }
        });

        saveExitButton.addActionListener(e -> {
            MainGUI.saveData();
            System.exit(0);
        });

        setVisible(true);
    }
}