
import java.io.Serializable;

public class Order implements Serializable {
    private static int orderCount = 0; 
    private int orderId;
    private String ProductName;
    private double PaidPrice;

    public Order(Product product, double PaidPrice) {
        orderCount++;
        orderId = orderCount;
        this.ProductName = product.getName();
        this.PaidPrice = PaidPrice;
    }

    public Order(Order o) {
        this.orderId = o.orderId;
        this.ProductName = o.ProductName;
        this.PaidPrice = o.PaidPrice;
    }

    public String toString() {
        return "Order ID: " + orderId + ", Product: " + ProductName + ", Paid: " + PaidPrice;
    }
}