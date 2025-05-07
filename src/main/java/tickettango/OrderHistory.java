package tickettango;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a user's order history
 */
public class OrderHistory {
    private ArrayList<Order> orders;
    
    /**
     * Constructor for OrderHistory
     */
    public OrderHistory() {
        this.orders = new ArrayList<>();
    }
    
    /**
     * Add an order to the history
     * 
     * @param order The order to add
     */
    public void addOrder(Order order) {
        orders.add(order);
    }
    
    /**
     * Get all orders
     * 
     * @return List of orders
     */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
    
    /**
     * Get the total number of orders
     * 
     * @return Number of orders
     */
    public int getOrderCount() {
        return orders.size();
    }
}
