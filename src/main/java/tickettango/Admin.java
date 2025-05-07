package tickettango;

import java.util.ArrayList;

/**
 * Admin class extends User with administrative privileges
 * Provides special functionality for managing events and users
 */
public class Admin extends User {
    
    /**
     * Constructor for Admin
     * 
     * @param email Admin email
     * @param password Admin password
     * @param firstName Admin first name
     * @param lastName Admin last name
     */
    public Admin(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }
    
    /**
     * Add a new event to the system
     * 
     * @param event Event to add
     */
    public void addEvent(Event event) {
        // Add event implementation
        // Would typically add to a database or repository
    }
    
    /**
     * Remove an event from the system
     * 
     * @param event Event to remove
     */
    public void removeEvent(Event event) {
        // Remove event implementation
    }
    
    /**
     * Add a new user to the system
     * 
     * @param user User to add
     */
    public void addUser(User user) {
        // Add user implementation
    }
    
    /**
     * Remove a user from the system
     * 
     * @param user User to remove
     */
    public void removeUser(User user) {
        // Remove user implementation
    }
    
    /**
     * Get all events in the system for admin view
     * 
     * @return List of all events
     */
    public ArrayList<Event> getAllEvents() {
        // Get all events implementation
        return new ArrayList<>();
    }
    
    /**
     * Get all users in the system for admin view
     * 
     * @return List of all users
     */
    public ArrayList<User> getAllUsers() {
        // Get all users implementation
        return new ArrayList<>();
    }
    
    /**
     * Update event details
     * 
     * @param event Event to update
     * @param updatedEvent New event details
     */
    public void updateEvent(Event event, Event updatedEvent) {
        // Update event implementation
    }
}
