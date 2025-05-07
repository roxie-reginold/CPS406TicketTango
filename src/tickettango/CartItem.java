package tickettango;

/**
 * Represents an item in the shopping cart
 */
public class CartItem {
    private Event event;
    private int quantity;
    
    /**
     * Constructor for CartItem
     * 
     * @param event The event for this cart item
     * @param quantity Number of tickets
     */
    public CartItem(Event event, int quantity) {
        this.event = event;
        this.quantity = quantity;
    }
    
    /**
     * Get the event for this cart item
     * 
     * @return The event
     */
    public Event getEvent() {
        return event;
    }
    
    /**
     * Get the quantity of tickets
     * 
     * @return Ticket quantity
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Set the quantity of tickets
     * 
     * @param quantity New ticket quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Calculate the total price for this cart item
     * 
     * @return Total price
     */
    public double getTotal() {
        return quantity * event.getPrice();
    }
}
