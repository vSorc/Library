
public class Product implements Identifiable {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) throws StoreException {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    public void updateStock(int quantity) throws StoreException {
        if (quantity < 0 && stock == 0) {
            throw new StoreException("Error: Quantity to update must be a positive number.");
        } else {
            this.stock += quantity;
        }
    }

    public boolean isAvailable() {
        return stock > 0;
    }

   
    public int getId() {
        return id;
    }

    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int quantity) throws StoreException {
        if (quantity >= 0) {
            this.stock = quantity;
        } else { 
            throw new StoreException("Error: Stock cannot be a negative number.");
        }
    }

    public void setPrice(double price) throws StoreException {
        if (price > 0) {
            this.price = price;
        } else {
            throw new StoreException("Error: Price must be greater than 0.");
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Price: " + price + ", Stock: " + stock;
    }
}