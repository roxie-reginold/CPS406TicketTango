package tickettango;

/**
 * Represents a user in the TicketTango system
 */
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Cart cart;
    private OrderHistory orderHistory;
    
    /**
     * Constructor for User
     */
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cart = new Cart();
        this.orderHistory = new OrderHistory();
    }
    
    // Getters and setters
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }
}
