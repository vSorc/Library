

import java.io.Serializable;

public class Store implements Serializable{
	private String name;
	private int numOfProducts;  
	private int numOfUsers;  
	private List<Product> inventory;
	private	List<Person> users;

	public Store(String name) {
		this.name = name;
		numOfProducts = 0;
		numOfUsers = 0;
		this.inventory = new List<Product>();
		this.users = new List<Person>();
	}

	public boolean addProduct (Product p) {
		inventory.insertAtFront(p);
		return true;
	}
	public List<Product> getInventory() {
		return inventory;
	} 
	public void removeProduct (int id ) { // removing product without Sorting
		Product removed = searchProductById(id);
		if (removed != null) {
			inventory.remove(removed);
			numOfProducts--;
		}
		
	}

	public Product searchProductById(int id) {
		Node<Product> current = inventory.getFirstNode();
		while (current != null) {
			if (current.getData().getId() == id) {
				return current.getData();
			}
			current = current.nextNode;
			
		}
		return null;
	}
	public Product searchProductByName(String name) {
		Node<Product> current = inventory.getFirstNode();
		while (current != null) {
			if (current.getData().getName().equalsIgnoreCase(name)) {
				return current.getData();
			}
			current = current.nextNode;
			
		}
		return null;
	}

	public double calculateTotalInventory (Node<Product> current) {  // a recursive method that gets a sum of all of the inventory by multiplying the stock*price
		if (current == null)
			return 0;
		double productValue = current.getData().getPrice() * current.getData().getStock();
		return productValue + calculateTotalInventory(current.nextNode);
		
	}

	public boolean addUser(Person p) {
		users.insertAtFront(p);
		return true;
	}

	public boolean removeUser(Person p) {     // removing user and Sorting the array after removal
		Person removed = p;
		if (removed != null) {
			users.remove(removed);
			numOfUsers--;
			return true;
		}
		return false;
		}
		

	

	public Person findUserByID(int id) {
		Node<Person> current = users.getFirstNode();
		while (current != null) {
			if (current.getData().getId() == id) {
				return current.getData();
			}
			current = current.nextNode;
			
		}
		return null;
	}

	public Person login(String name , String password) {
		Node<Person> current = users.getFirstNode();
		while(current != null) {
			if(name.equalsIgnoreCase(current.getData().getName())&& password.equals(current.getData().getPassword())) {
				return current.getData();
			}
			current = current.nextNode;
		}
		return null;
	}

	public int getNumOfProducts() {
		return numOfProducts;
	}

	public String showAllUsers() {
		Node<Person> current = users.getFirstNode();
	    String s = "";
	    while(current != null) {
	        s += current.getData().toString() + "\n";
	        s += "----------------------------------------\n";
			current = current.nextNode;
	    }
	    return s;
	}
	public String showAllProducts(Person person) {
		Node<Product> current = inventory.getFirstNode();
		String s = "";
		if(person instanceof VIPCustomer) {
			while(current != null) {
				s += current.getData() + "\n";
				s += "Discounted Price: " + ((VIPCustomer)person).calculateDiscountedPrice(current.getData().getPrice())+"\n----------------------------------------\n";
				current = current.nextNode;
			}
			return s;
		}

		while(current != null) {
			s += current.getData() +"\n----------------------------------------\n";
			current = current.nextNode;
		}
		return s;
	}

	public boolean purchaseProduct(Customer c , Product p) throws StoreException { //this method is to process the purchase of the Item
		if((p.isAvailable())) {
			double Price;
			if(c instanceof VIPCustomer) { // checks if customer is vip

				Price = ((VIPCustomer)c).calculateDiscountedPrice(p.getPrice());
			}
			else {
				Price = p.getPrice();
			}

			if(c.deductBalance(Price)) { // checks if the balance is enough for the price
				p.updateStock(-1);   	// changes the Stock of the product
				c.addOrderToHistory(new Order(p , Price)); // adds the Order to the History of the Customer
				if(c instanceof VIPCustomer) {
					((VIPCustomer)c).addLoyaltyPoints(Price);
				}
				System.out.println("Purchase has been processed successfully. \nThanks for Shopping!");
				return true;
			}
			System.out.println("Balance "+ c.getBalance()+ " is Not Enough. "); 
			return false;
		}
		System.out.println("Sorry, the Item "+ p.getName() + "is Not in Stock");
		return false;

	}
	public String getName() {
		return name;
	}
}