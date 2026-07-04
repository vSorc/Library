

import javax.swing.*;
import java.awt.*;

public class EmployeeFrame extends JFrame {
    private Employee employee;
    private JTextArea logArea;

    public EmployeeFrame(Employee employee) {
        this.employee = employee;
        setTitle("Employee Dashboard");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(230, 230, 230));
        JLabel nameLabel = new JLabel("User: " + employee.getName(), SwingConstants.CENTER);
        JLabel roleLabel = new JLabel("Role: Employee (" + employee.getRole() + ")", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(nameLabel);
        headerPanel.add(roleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 3, 5, 5));
        JButton[] btns = {
            new JButton("Add Product"), new JButton("Edit Product"), new JButton("Remove Product"),
            new JButton("Show All Products"), new JButton("Search (Name)"), new JButton("Search (ID)"),
            new JButton("Inventory Value"), new JButton("Add Employee"), new JButton("Add VIP"),
            new JButton("Remove User"), new JButton("Search User"), new JButton("Show All Users"),
            new JButton("View Salary & Bonus"), new JButton("Logout")
        };
        for (JButton btn : btns) buttonPanel.add(btn);
        add(buttonPanel, BorderLayout.CENTER);

        logArea = new JTextArea(12, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        btns[0].addActionListener(e -> {
            JPanel p = new JPanel(new GridLayout(4, 2));
            JTextField idF = new JTextField(); JTextField nameF = new JTextField();
            JTextField priceF = new JTextField(); JTextField qtyF = new JTextField();
            p.add(new JLabel("ID:")); p.add(idF); p.add(new JLabel("Name:")); p.add(nameF);
            p.add(new JLabel("Price:")); p.add(priceF); p.add(new JLabel("Qty:")); p.add(qtyF);
            if (JOptionPane.showConfirmDialog(null, p, "Add Product", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    Product newProd = new Product(Integer.parseInt(idF.getText()), nameF.getText(), Double.parseDouble(priceF.getText()), Integer.parseInt(qtyF.getText()));
                    if (MainGUI.store.addProduct(newProd))
                        logArea.append("Product added: " + nameF.getText() + "\n");
                    else
                        logArea.append("Failed to add product: Inventory full.\n");
                } catch (Exception ex) { logArea.append("Error: Invalid Input\n"); }
            }
        });

        btns[1].addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter Product ID:");
                if (idStr != null) {
                    Product p = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                    if (p != null) {
                        JPanel panel = new JPanel(new GridLayout(2, 2));
                        JTextField prF = new JTextField(String.valueOf(p.getPrice()));
                        JTextField stF = new JTextField(String.valueOf(p.getStock()));
                        panel.add(new JLabel("New Price:")); panel.add(prF);
                        panel.add(new JLabel("New Stock:")); panel.add(stF);
                        if (JOptionPane.showConfirmDialog(null, panel, "Edit Product", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                            p.setPrice(Double.parseDouble(prF.getText()));
                            p.setStock(Integer.parseInt(stF.getText()));
                            logArea.append("Product " + idStr + " updated.\n");
                        }
                    } else logArea.append("Product not found.\n");
                }
            } catch (Exception ex) { logArea.append("Invalid input!\n"); }
        });

        btns[2].addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter Product ID:");
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    Product p = MainGUI.store.searchProductById(id);
                    if (p != null) {
                        MainGUI.store.removeProduct(id);
                        logArea.append("Product has been removed.\n");
                    } else {
                        logArea.append("Product Not Found.\n");
                    }
                }
            } catch (Exception ex) { logArea.append("Invalid input!\n"); }
        });

        btns[3].addActionListener(e -> {
            logArea.append("--- Product List ---\n");
            logArea.append(MainGUI.store.showAllProducts(employee));
        });

        btns[4].addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Product Name:");
            if (name != null && !name.trim().isEmpty()) {
                Product p = MainGUI.store.searchProductByName(name);
                if (p != null) logArea.append(p.toString() + "\n");
                else logArea.append("Product Not Found.\n");
            }
        });

        btns[5].addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter Product ID:");
                if (idStr != null) {
                    Product p = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                    if (p != null) logArea.append(p.toString() + "\n");
                    else logArea.append("Product Not Found.\n");
                }
            } catch (Exception ex) { logArea.append("Invalid input!\n"); }
        });

        btns[6].addActionListener(e -> {
            double total = MainGUI.store.calculateTotalInventory(MainGUI.store.getInventory().getFirstNode());
            logArea.append("Total Inventory Value: " + total + "\n");
        });

        btns[7].addActionListener(e -> {
            JPanel p = new JPanel(new GridLayout(5, 2));
            JTextField idF = new JTextField(); JTextField nameF = new JTextField();
            JTextField passF = new JTextField(); JTextField roleF = new JTextField(); JTextField salF = new JTextField();
            p.add(new JLabel("ID:")); p.add(idF); p.add(new JLabel("Name:")); p.add(nameF);
            p.add(new JLabel("Pass:")); p.add(passF); p.add(new JLabel("Role:")); p.add(roleF); p.add(new JLabel("Salary:")); p.add(salF);
            if (JOptionPane.showConfirmDialog(null, p, "Add Employee", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    Employee newEmp = new Employee(Integer.parseInt(idF.getText()), nameF.getText(), passF.getText(), roleF.getText(), Double.parseDouble(salF.getText()));
                    if (MainGUI.store.addUser(newEmp))
                        logArea.append("Employee " + nameF.getText() + " added.\n");
                    else
                        logArea.append("Failed to add employee: User limit reached.\n");
                } catch (Exception ex) { logArea.append("Error adding employee.\n"); }
            }
        });

        btns[8].addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter VIP Customer ID:"));
                String name = JOptionPane.showInputDialog("Enter VIP Customer Name:");
                String pass = JOptionPane.showInputDialog("Enter VIP Customer Password:");
                if(MainGUI.store.addUser(new VIPCustomer(id, name, pass))) {
                    logArea.append("VIP Customer " + name + " added.\n");
                } else logArea.append("Users Array is full.\n");
            } catch (Exception ex) { logArea.append("Invalid input!\n"); }
        });

        btns[9].addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter User ID:");
                if (idStr != null) {
                    Person p = MainGUI.store.findUserByID(Integer.parseInt(idStr));
                    if (p != null) {
                        if (MainGUI.store.removeUser(p)) {
                            logArea.append("User " + p.getName() + " removed.\n");
                        } else {
                            logArea.append("Failed to remove user.\n");
                        }
                    } else logArea.append("User Not Found.\n");
                }
            } catch (Exception ex) { logArea.append("Invalid input!\n"); }
        });

        btns[10].addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter User ID:");
                if (idStr != null) {
                    Person p = MainGUI.store.findUserByID(Integer.parseInt(idStr));
                    if (p != null) logArea.append(p.toString() + "\n");
                    else logArea.append("User Not Found.\n");
                }
            } catch (Exception ex) { logArea.append("Invalid input!\n"); }
        });

        btns[11].addActionListener(e -> {
            logArea.append("--- Users List ---\n");
            logArea.append(MainGUI.store.showAllUsers());
        });

        btns[12].addActionListener(e -> {
            logArea.append("Your Salary: " + employee.getSalary() + " | Annual Bonus: " + employee.calculateAnnualBonus() + "\n");
        });

        btns[13].addActionListener(e -> {
            dispose();
            new MainGUI().setVisible(true);
        });

        setVisible(true);
    }
}