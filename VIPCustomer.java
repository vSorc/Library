
public class VIPCustomer extends Customer {
    private double discountRate;
    private int loyaltyPoints;
    

    public VIPCustomer(int id, String name, String password) {
        super(id, name, password); // Call the superclass constructor with appropriate parameters
        this.discountRate = 0.10;
        this.loyaltyPoints = 0;
        
    }

    public double calculateDiscountedPrice(double price) {
        return price * (1 - discountRate);
    }
    public void addLoyaltyPoints(double amountpaid) {
        loyaltyPoints += (int) amountpaid;
    }
    public void redeemLoyaltyPoints(int points) {
        if (points <= loyaltyPoints) {
            loyaltyPoints -= points;
            balance += points * 0.01; 
            System.out.println("Redeemed " + points + " points. Remaining points: " + loyaltyPoints);
            
        } else {
            System.out.println("Not enough loyalty points to redeem.");
        }
    }
    public String toString() {
        return super.toString() + ", VIP Discount: " + (discountRate * 100) + "%, Loyalty Points: " + loyaltyPoints;
    }

   
    public void showMenu() {
        System.out.print("-------------------------------------------------\n"
        		+ "     VIP Customer Menu | Welcome:"+toString() + "\n"
        		+ "-------------------------------------------------\n"
        		+ "1. View Available Products\n"
        		+ "2. Search for a Product by Name\n"
        		+ "3. Search for a product by ID\n"
        		+ "4. Purchase Product \n"
        		+ "5. Save for Later\n"
        		+ "6. View Saved Items\n"
        		+ "7. View Order History \n"
        		+ "8. Add Funds to Balance\n"
        		+ "9. Redeem Loyalty Points to Balance\n"
        		+ "10. Logout\n"
        		+ "-------------------------------------------------\n"
        		+ "Please enter your choice: ");
        }
}