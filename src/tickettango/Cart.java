package tickettango;

import java.util.ArrayList;

/**
 * Represents a shopping cart for event tickets
 */
public class Cart {
    private ArrayList<CartItem> items;
    
    /**
     * Constructor for Cart
     */
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    /**
     * Add an item to the cart
     * 
     * @param event The event to add
     * @param quantity The number of tickets
     */
    public void addItem(Event event, int quantity) {
        // Check if the event is already in cart
        for (CartItem item : items) {
            if (item.getEvent().getName().equals(event.getName())) {
                // Update the quantity instead of adding new item
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        
        // Add as new item if not found
        items.add(new CartItem(event, quantity));
    }
    
    /**
     * Remove an item from the cart
     * 
     * @param index Index of the item to remove
     */
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }
    
    /**
     * Get all items in the cart
     * 
     * @return List of cart items
     */
    public ArrayList<CartItem> getItems() {
        return items;
    }
    
    /**
     * Clear the cart
     */
    public void clear() {
        items.clear();
    }
    
    /**
     * Calculate the subtotal of all items in the cart
     * 
     * @return The subtotal price
     */
    public double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : items) {
            subtotal += item.getQuantity() * item.getEvent().getPrice();
        }
        return subtotal;
    }
    
    /**
     * Calculate the tax (13%) on the cart items
     * 
     * @return The tax amount
     */
    public double getTax() {
        return getSubtotal() * 0.13; // 13% tax
    }
    
    /**
     * Calculate the total price including tax
     * 
     * @return The total price
     */
    public double getTotal() {
        return getSubtotal() + getTax();
    }
    
    /**
     * Check if the cart is empty
     * 
     * @return true if cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Get the number of items in the cart
     * 
     * @return Number of items
     */
    public int getItemCount() {
        return items.size();
    }
}
