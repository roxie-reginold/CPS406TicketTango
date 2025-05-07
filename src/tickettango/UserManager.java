package tickettango;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Manages user authentication and user data
 */
public class UserManager {
    private Map<String, User> users;
    private User currentUser;
    
    /**
     * Constructor loads users from file or uses defaults
     */
    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }
    
    /**
     * Load users from the UserDatabase.txt file or create defaults
     */
    private void loadUsers() {
        // Path to the user database file
        String userDatabasePath = "/Users/innovation/Documents/CPS406TicketTango/Jar Application/UserDatabase.txt";
        File file = new File(userDatabasePath);
        
        try {
            // Attempt to read from file
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String email = parts[0].trim();
                    String password = parts[1].trim();
                    
                    // Create user with default names if only email and password are provided
                    String firstName = parts.length > 2 ? parts[2].trim() : "User";
                    String lastName = parts.length > 3 ? parts[3].trim() : "Account";
                    
                    users.put(email, new User(email, password, firstName, lastName));
                }
            }
            scanner.close();
            System.out.println("Loaded " + users.size() + " users from database file");
        } catch (FileNotFoundException e) {
            System.out.println("User database file not found. Using default credentials.");
            createDefaultUsers();
        }
        
        // If no users were loaded, create default ones
        if (users.isEmpty()) {
            createDefaultUsers();
        }
    }
    
    /**
     * Create default users if no file is found
     */
    private void createDefaultUsers() {
        // Add known user credentials from the system
        addUser("roxie@ticket.tango.ca", "admin41", "Roxie", "Admin");
        addUser("shanaya@ticket.tango.ca", "admin64", "Shanaya", "Admin");
        addUser("rr7972@gmail.com", "roller002", "Richard", "Roller");
        addUser("roxie@tmu.ca", "Rainbow456", "Roxie", "Student");
        addUser("Amy@gmail.com", "Sunflower", "Amy", "Johnson");
        addUser("shanaya@tmu.ca", "Happy123", "Shanaya", "Student");
        addUser("cats@cats.ca", "12345678", "Cat", "Lover");
    }
    
    /**
     * Add a user to the system
     * 
     * @param email User email
     * @param password User password
     * @param firstName User first name
     * @param lastName User last name
     */
    public void addUser(String email, String password, String firstName, String lastName) {
        users.put(email, new User(email, password, firstName, lastName));
    }
    
    /**
     * Authenticate a user
     * 
     * @param email User email
     * @param password User password
     * @return true if authentication successful, false otherwise
     */
    public boolean authenticate(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }
    
    /**
     * Get the current logged-in user
     * 
     * @return Current user or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Log out the current user
     */
    public void logout() {
        currentUser = null;
    }
    
    /**
     * Check if a user is logged in
     * 
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
