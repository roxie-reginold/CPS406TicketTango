package tickettango;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an order in the TicketTango system
 */
public class Order {
    private static int nextOrderId = 1000; // Starting order ID
    
    private int orderId;
    private String orderDate;
    private ArrayList<CartItem> items;
    private double subtotal;
    private double tax;
    private double total;
    private String paymentMethod;
    
    /**
     * Constructor for Order
     * 
     * @param cart The shopping cart
     * @param paymentMethod The payment method used
     */
    public Order(Cart cart, String paymentMethod) {
        this.orderId = nextOrderId++;
        
        // Format current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.orderDate = dateFormat.format(new Date());
        
        // Copy items from cart
        this.items = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            this.items.add(new CartItem(item.getEvent(), item.getQuantity()));
        }
        
        // Set financial details
        this.subtotal = cart.getSubtotal();
        this.tax = cart.getTax();
        this.total = cart.getTotal();
        this.paymentMethod = paymentMethod;
    }
    
    /**
     * Get the order ID
     * 
     * @return Order ID
     */
    public int getOrderId() {
        return orderId;
    }
    
    /**
     * Get the order date
     * 
     * @return Order date as string
     */
    public String getOrderDate() {
        return orderDate;
    }
    
    /**
     * Get all items in the order
     * 
     * @return List of order items
     */
    public List<CartItem> getItems() {
        return items;
    }
    
    /**
     * Get the subtotal amount
     * 
     * @return Subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }
    
    /**
     * Get the tax amount
     * 
     * @return Tax
     */
    public double getTax() {
        return tax;
    }
    
    /**
     * Get the total amount
     * 
     * @return Total
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Get the payment method
     * 
     * @return Payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }
}
