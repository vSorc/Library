
public class Employee extends Person {
	private String role;
	private double salary;

	public Employee(int id, String name, String password, String role, double salary) {
		super(id, name, password);
		this.role = role;
		this.salary = salary;
	}

	public double getSalary() {
		return salary;
	}

	public String getRole() {
		return role;
	}

	public double calculateAnnualBonus() {
		return salary * 0.15;
	}

	public String toString(){
		return super.toString() + " , Role : " + role ;
	}

	public void showMenu() {
		System.out.println("-------------------------------------------------");
		System.out.println("Employee Menu | Welcome " + toString()); 
		System.out.println("-------------------------------------------------");
		System.out.println("      -- Inventory Management --");
		System.out.println("1. Add New Product to Inventory");
		System.out.println("2. Edit Existing Product");
		System.out.println("3. Remove Product from Inventory");
		System.out.println("4. View All Available Products");
		System.out.println("5. Search Product by Name");
		System.out.println("6. Search Product by ID");
		System.out.println("7. Calculate Total Inventory Value");
		System.out.println("      -- User Management --");
		System.out.println("8. Add New Employee");
		System.out.println("9. Add New VIP Customer");
		System.out.println("10. Remove User from System");
		System.out.println("11. Search User by ID");
		System.out.println("12. View All Registered Users");
		System.out.println("      -- Personal --");
		System.out.println("13. View My Salary and Annual Bonus");
		System.out.println("14. Logout");
		System.out.println("-------------------------------------------------");
		System.out.print("Please enter your choice: ");
	}
}
