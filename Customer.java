
public class Customer extends Person {
	protected double balance;
	private int numofSaved;
	private int numofOrders;
	private List<Order> orders;
	private List<Product> savedProducts;
	public Customer(int id, String name, String password) {
		super(id, name, password);
		this.balance = 0;
		this.numofSaved = 0;
		this.numofOrders = 0;
		this.savedProducts = new List<Product>();
		this.orders = new List<Order>();
	}
	public void addFunds(double amount) {
		balance += amount;
	}
	public boolean deductBalance(double amount) {   ///////////////////////////////////////////////////////////////////////////////////////////
		if(amount <= balance) {
			balance -= amount;
			return true;
		}
		return false;

	}
	public double getBalance() {
		return balance;
	}
	public boolean saveProduct(Product p) {
		savedProducts.insertAtFront(p);
		numofSaved++;
		return true;
	}
	public String viewSavedProducts() {
		Node<Product> current = savedProducts.getFirstNode();
		String s = "Saved Products:\n";
		while (current != null) {
			s = current.getData().toString() + "\n";
			current = current.nextNode;
		}
		return s;
	}
	public void removeSavedProduct(Product p) {
		Product removed = p;
		if (removed != null) {
			savedProducts.remove(p);
			numofSaved--;
			
		}
		
	}
	public void addOrderToHistory(Order o) {
		orders.insertAtFront(o);
		

	}

	public String viewOrderHistory() {
		Node<Order> current = orders.getFirstNode();
		String s = "Order History:\n";
		while (current != null) {
			s += current.getData().toString()+"\n";
			current = current.nextNode;
		}
		return s;
	}


	public void showMenu() {
		System.out.print("-------------------------------------------------\n"
				+ "      Customer  Menu | Welcome Name: "+toString()+"\n"
				+ "-------------------------------------------------\n"
				+ "1. View All Products\n"
				+ "2. Search for a Product by Name\n"
				+ "3. Search for a product by ID\n"
				+ "4. Purchase Product \n"
				+ "5. Save for Later \n"
				+ "6. View Saved Items\n"
				+ "7. View Order History \n"
				+ "8. Add Funds to Balance\n"
				+ "9. Logout\n"
				+ "-------------------------------------------------\n"
				+ "Please enter your choice: ");
	} 
	public String toString() {
		return super.toString() + ", Balance: " + balance;
	}
}
