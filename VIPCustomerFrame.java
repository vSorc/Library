

import javax.swing.*;
import java.awt.*;

public class VIPCustomerFrame extends JFrame {
    private VIPCustomer vip;
    private JTextArea logArea;
    private JLabel infoLabel;

    public VIPCustomerFrame(VIPCustomer vip) {
        this.vip = vip;
        setTitle("VIP Dashboard");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new GridLayout(1, 1));
        header.setBackground(new Color(230, 230, 230));
        infoLabel = new JLabel(vip.toString(), SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        header.add(infoLabel);
        add(header, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        JButton b1 = new JButton("Show All Products");
        JButton b2 = new JButton("Search Product By Name");
        JButton b3 = new JButton("Search Product By ID");
        JButton b4 = new JButton("Purchase Product");
        JButton b5 = new JButton("Save Product");
        JButton b6 = new JButton("View Saved Products");
        JButton b7 = new JButton("View Order History");
        JButton b8 = new JButton("Add Funds");
        JButton b9 = new JButton("Redeem Loyalty Points");
        JButton b10 = new JButton("Logout");

        btnPanel.add(b1); btnPanel.add(b2); btnPanel.add(b3); btnPanel.add(b4);
        btnPanel.add(b5); btnPanel.add(b6); btnPanel.add(b7); btnPanel.add(b8);
        btnPanel.add(b9); btnPanel.add(b10);
        add(btnPanel, BorderLayout.CENTER);

        logArea = new JTextArea(12, 50);
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            logArea.append("--- All Products ---\n");
            logArea.append(MainGUI.store.showAllProducts(vip));
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
                logArea.append(MainGUI.store.showAllProducts(vip));
                String idStr = JOptionPane.showInputDialog("Enter product ID to purchase:");
                if (idStr != null) {
                    Product p = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                    if(p != null) {
                        int confirm = JOptionPane.showConfirmDialog(null, "Product Information: " + p.toString() + "\nProceed Purchase?", "Confirm", JOptionPane.YES_NO_OPTION);
                        if(confirm == JOptionPane.YES_OPTION) {
                            if (MainGUI.store.purchaseProduct(vip, p)) {
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
                        if (vip.saveProduct(p)) {
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
            logArea.append(vip.viewSavedProducts());
            int confirm = JOptionPane.showConfirmDialog(null, "Remove Product from Saved List?", "Remove", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    String idStr = JOptionPane.showInputDialog("Enter Product ID:");
                    if (idStr != null) {
                        Product removedProduct = MainGUI.store.searchProductById(Integer.parseInt(idStr));
                        if(removedProduct != null) {
                            vip.removeSavedProduct(removedProduct);
                            logArea.append("Product has been Removed.\n");
                        } else logArea.append("Product Not Found.\n");
                    }
                } catch(Exception ex) { logArea.append("Invalid input!\n"); }
            }
        });

        b7.addActionListener(e -> {
            logArea.append("--- Order History ---\n");
            logArea.append(vip.viewOrderHistory());
        });

        b8.addActionListener(e -> {
            try {
                String amountStr = JOptionPane.showInputDialog("Enter Amount to add:");
                if (amountStr != null) {
                    double amount = Double.parseDouble(amountStr);
                    vip.addFunds(amount);
                    logArea.append("Added " + amountStr + " to VIP balance.\n");
                    updateHeader();
                }
            } catch(Exception ex) { logArea.append("Invalid input!\n"); }
        });

        b9.addActionListener(e -> {
            try {
                String pointsStr = JOptionPane.showInputDialog("Enter Loyalty Points to Redeem:");
                if (pointsStr != null) {
                    int points = Integer.parseInt(pointsStr);
                    vip.redeemLoyaltyPoints(points);
                    logArea.append("Loyalty points redemption processed.\n");
                    updateHeader();
                }
            } catch(Exception ex) { logArea.append("Invalid input!\n"); }
        });

        b10.addActionListener(e -> {
            dispose();
            new MainGUI().setVisible(true);
        });

        setVisible(true);
    }

    private void updateHeader() {
        infoLabel.setText(vip.toString());
    }
}