

import javax.swing.*;
import java.awt.*;

public class CustomerFrame extends JFrame {
    private Customer customer;
    private JTextArea logArea;
    private JLabel infoLabel;

    public CustomerFrame(Customer customer) {
        this.customer = customer;
        setTitle("Customer Dashboard");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new GridLayout(1, 1));
        header.setBackground(new Color(230, 230, 230));
        infoLabel = new JLabel(customer.toString(), SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        header.add(infoLabel);
        add(header, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        JButton b1 = new JButton("Show All Products");
        JButton b2 = new JButton("Search Product By Name");
        JButton b3 = new JButton("Search Product By ID");
        JButton b4 = new JButton("Purchase Product");
        JButton b5 = new JButton("Save Product");
        JButton b6 = new JButton("View Saved Products");
        JButton b7 = new JButton("View Order History");
        JButton b8 = new JButton("Add Funds");
        JButton b9 = new JButton("Logout");

        btnPanel.add(b1); btnPanel.add(b2); btnPanel.add(b3);
        btnPanel.add(b4); btnPanel.add(b5); btnPanel.add(b6);
        btnPanel.add(b7); btnPanel.add(b8); btnPanel.add(b9);
        add(btnPanel, BorderLayout.CENTER);

        logArea = new JTextArea(12, 50);
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            logArea.append("--- All Products ---\n");
            logArea.append(MainGUI.store.showAllProducts(customer));
        });

        b2.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Product Name:");
            if (name != null && !name.trim().isEmpty()) {
                Product p = MainGUI.store.searchProductByName(name);
                if(p != null) logArea.append(p.toString() + "\n");
                else logArea.append("Product Not Found.\n");
            }
        });

        b3.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter Product ID:");
                if (idStr != null) {
                    Product p = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                    if(p != null) logArea.append(p.toString() + "\n");
                    else logArea.append("Product Not Found.\n");
                }
            } catch(Exception ex) { logArea.append("Invalid input!\n"); }
        });

        b4.addActionListener(e -> {
            try {
                logArea.append(MainGUI.store.showAllProducts(customer));
                String idStr = JOptionPane.showInputDialog("Enter product ID to purchase:");
                if (idStr != null) {
                    Product p = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                    if(p != null) {
                        int confirm = JOptionPane.showConfirmDialog(null, "Product Information: " + p.toString() + "\nProceed Purchase?", "Confirm", JOptionPane.YES_NO_OPTION);
                        if(confirm == JOptionPane.YES_OPTION) {
                            if (MainGUI.store.purchaseProduct(customer, p)) {
                                logArea.append("Purchase has been processed successfully.\n");
                                updateHeader();
                            } else {
                                logArea.append("Purchase failed: Not enough balance or stock.\n");
                            }
                        }
                    } else logArea.append("Product Not Found.\n");
                }
            } catch(Exception ex) { logArea.append("Invalid input!\n"); }
        });

        b5.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog("Enter product ID to save:");
                if (idStr != null) {
                    Product p = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                    if(p != null) {
                        if (customer.saveProduct(p)) {
                            logArea.append("Product has been saved.\n");
                        } else {
                            logArea.append("Saved products limit reached.\n");
                        }
                    }
                    else logArea.append("Product Not Found.\n");
                }
            } catch(Exception ex) { logArea.append("Invalid input!\n"); }
        });

        b6.addActionListener(e -> {
            logArea.append("--- Saved Products ---\n");
            logArea.append(customer.viewSavedProducts());
            int confirm = JOptionPane.showConfirmDialog(null, "Remove a Product from the Saved List?", "Remove", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    String idStr = JOptionPane.showInputDialog("Enter Product ID:");
                    if (idStr != null) {
                        Product removedProduct = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                        if(removedProduct != null) {
                            customer.removeSavedProduct(removedProduct);
                            logArea.append("Product has been Removed.\n");
                        } else logArea.append("Product Not Found.\n");
                    }
                } catch(Exception ex) { logArea.append("Invalid input!\n"); }
            }
        });

        b7.addActionListener(e -> {
            logArea.append("--- Order History ---\n");
            logArea.append(customer.viewOrderHistory());
        });

        b8.addActionListener(e -> {
            try {
                String amountStr = JOptionPane.showInputDialog("Enter Amount to add:");
                if (amountStr != null) {
                    double amount = Double.parseDouble(amountStr);
                    customer.addFunds(amount);
                    logArea.append("Added " + amountStr + " to balance.\n");
                    updateHeader();
                }
            } catch(Exception ex) { logArea.append("Invalid input!\n"); }
        });

        b9.addActionListener(e -> {
            dispose();
            new MainGUI().setVisible(true);
        });

        setVisible(true);
    }

    private void updateHeader() {
        infoLabel.setText(customer.toString());
    }
}