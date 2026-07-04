
public abstract class Person implements Identifiable {
	protected int id;
	protected String name;
	protected String password;

	public Person(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public int getId() { 
		return id; 
	}
	public String getName() { 
		return name; 
	}

	public String getPassword() { 
		return password; 
	}

	public abstract void showMenu();

	public String toString() {
		return "ID: " + id + ", Name: " + name;
	}
}
