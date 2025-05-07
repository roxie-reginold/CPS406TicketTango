package tickettango;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 * Main application class for TicketTango
 * Implements the complete user flow: login, browse events, cart, checkout, and order history
 */
public class TicketTangoApp extends JFrame {
    // Components for different screens
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Panels for different screens
    private JPanel loginPanel;
    private JPanel eventsPanel;
    private JPanel cartPanel;
    private JPanel checkoutPanel;
    private JPanel orderHistoryPanel;
    
    // Login components
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton adminLoginButton;
    
    // Events panel components
    private JTable eventTable;
    private JComboBox<String> searchField;
    private JButton searchButton;
    private JButton saveSearchButton;
    private JButton viewEventButton;
    private JButton addToCartButton;
    
    // Search experience enhancement elements
    private ArrayList<String> searchHistory;
    private ArrayList<String> savedSearches;
    private DefaultComboBoxModel<String> searchSuggestions;
    
    // Cart panel components
    private JTable cartTable;
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;
    private JButton checkoutButton;
    
    // Checkout panel components
    private JTextField nameField;
    private JTextField addressField;
    private JTextField creditCardField;
    private JComboBox<String> paymentMethodCombo;
    private JButton confirmOrderButton;
    
    // Order history components
    private JTable orderHistoryTable;
    private JTable orderItemsTable;
    
    // Navigation components
    private JButton homeButton;
    private JButton cartButton;
    private JButton profileButton;
    private JButton logoutButton;
    
    // Application state
    private UserManager userManager;
    private TicketmasterAPIWrapper apiWrapper;
    private ArrayList<Event> currentEvents;
    
    /**
     * Constructor initializes the application
     */
    public TicketTangoApp() {
        super("TicketTango");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Initialize managers
        userManager = new UserManager();
        apiWrapper = new TicketmasterAPIWrapper();
        currentEvents = new ArrayList<>();
        
        // Initialize search experience components
        searchHistory = new ArrayList<>();
        savedSearches = new ArrayList<>();
        searchSuggestions = new DefaultComboBoxModel<>();
        
        // Add some common search suggestions
        addSearchSuggestion("concert");
        addSearchSuggestion("festival");
        addSearchSuggestion("sports");
        addSearchSuggestion("family");
        addSearchSuggestion("theater");
        addSearchSuggestion("comedy");
        
        // Set up UI
        setupUI();
        
        // Show login screen initially
        cardLayout.show(mainPanel, "login");
    }
    
    /**
     * Set up the main UI components
     */
    private void setupUI() {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Main panel with card layout to switch between screens
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        
        // Create panels for different screens
        createLoginPanel();
        createEventsPanel();
        createCartPanel();
        createCheckoutPanel();
        createOrderHistoryPanel();
        
        // Add panels to card layout
        mainPanel.add(loginPanel, "login");
        mainPanel.add(eventsPanel, "events");
        mainPanel.add(cartPanel, "cart");
        mainPanel.add(checkoutPanel, "checkout");
        mainPanel.add(orderHistoryPanel, "orderHistory");
        
        // Set main panel as content pane
        setContentPane(mainPanel);
    }
    
    /**
     * Create the login panel
     */
    private void createLoginPanel() {
        // Login panel implementation
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
        
        // Logo panel
        JPanel logoPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("TicketTango", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        
        // Email field
        JPanel emailPanel = new JPanel(new BorderLayout());
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        // Add default value for testing
        emailField.setText("roxie@ticket.tango.ca");
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailPanel.add(emailField, BorderLayout.CENTER);
        
        // Password field
        JPanel passwordPanel = new JPanel(new BorderLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        // Add default value for testing
        passwordField.setText("admin41");
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Login");
        adminLoginButton = new JButton("Admin Login");
        loginButton.setPreferredSize(new Dimension(150, 40));
        adminLoginButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(loginButton);
        buttonPanel.add(adminLoginButton);
        
        // Status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel statusLabel = new JLabel("Use existing TicketTango credentials", JLabel.CENTER);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        
        // Add components to form panel
        formPanel.add(emailPanel);
        formPanel.add(passwordPanel);
        formPanel.add(buttonPanel);
        formPanel.add(statusPanel);
        
        // Add panels to login panel
        loginPanel.add(logoPanel, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add login and admin login button actions
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            // Validate input
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter your email address.",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                emailField.requestFocus();
                return;
            }
            
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid email address.",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                emailField.requestFocus();
                return;
            }
            
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter your password.",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                passwordField.requestFocus();
                return;
            }
            
            if (userManager.authenticate(email, password)) {
                // Clear fields
                emailField.setText("");
                passwordField.setText("");
                
                // Load events and show events panel
                loadEvents();
                cardLayout.show(mainPanel, "events");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid email or password. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Admin login button action
        adminLoginButton.addActionListener(e -> {
            setVisible(false); // Hide main login window
            new AdminLoginScreen().setVisible(true);
        });
    }
    
    /**
     * Create the events panel for browsing and searching events
     */
    private void createEventsPanel() {
        eventsPanel = new JPanel(new BorderLayout(10, 10));
        eventsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header panel with navigation
        JPanel headerPanel = createHeaderPanel();
        
        // Create search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        
        // Events header with brown background
        JPanel eventsHeaderPanel = new JPanel(new BorderLayout());
        JLabel eventsLabel = new JLabel("Events", JLabel.CENTER);
        eventsLabel.setOpaque(true);
        eventsLabel.setBackground(new Color(184, 134, 84)); // Brown color
        eventsLabel.setForeground(Color.WHITE);
        eventsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        eventsLabel.setPreferredSize(new Dimension(0, 30));
        eventsHeaderPanel.add(eventsLabel, BorderLayout.CENTER);
        
        // Create advanced search panel with multiple options
        JPanel advancedSearchPanel = new JPanel(new BorderLayout());
        
        // Basic search controls in the top row
        JPanel searchControlPanel = new JPanel();
        searchControlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create searchField as a ComboBox with auto-suggestions
        searchField = new JComboBox<>(searchSuggestions);
        searchField.setEditable(true);
        searchField.setPreferredSize(new Dimension(250, 30)); // Make search box larger
        searchField.setToolTipText("Enter a keyword to search for events (artist, venue, event name)");
        
        // Add auto-complete functionality
        JTextComponent editor = (JTextComponent) searchField.getEditor().getEditorComponent();
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> updateSuggestions());
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> updateSuggestions());
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events
            }
        });
        
        // Create prominent search buttons with icons and tooltips
        searchButton = new JButton("Search Events");
        searchButton.setToolTipText("Search for events using the keyword");
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setBackground(new Color(59, 89, 182));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        
        saveSearchButton = new JButton("Save Search");
        saveSearchButton.setToolTipText("Save this search for later use");
        
        JButton historyButton = new JButton("History");
        historyButton.setToolTipText("View your search history");
        
        JButton advancedButton = new JButton("Advanced Search");
        advancedButton.setToolTipText("Search using advanced filters");
        
        JButton refreshButton = new JButton("View All Events");
        refreshButton.setToolTipText("Show all upcoming events");
        
        searchControlPanel.add(new JLabel("Search: "));
        searchControlPanel.add(searchField);
        searchControlPanel.add(searchButton);
        searchControlPanel.add(saveSearchButton);
        searchControlPanel.add(historyButton);
        searchControlPanel.add(advancedButton);
        searchControlPanel.add(refreshButton);
        
        // Create advanced search options panel (initially hidden)
        JPanel advancedOptionsPanel = new JPanel(new GridLayout(1, 4, 10, 5));
        advancedOptionsPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        advancedOptionsPanel.setVisible(false);
        
        // City/location filter
        JPanel locationPanel = new JPanel(new BorderLayout());
        locationPanel.add(new JLabel("City/Location:"), BorderLayout.NORTH);
        JTextField locationField = new JTextField();
        locationPanel.add(locationField, BorderLayout.CENTER);
        
        // Date filter
        JPanel datePanel = new JPanel(new BorderLayout());
        datePanel.add(new JLabel("Date (YYYY-MM-DD):"), BorderLayout.NORTH);
        JTextField dateField = new JTextField();
        datePanel.add(dateField, BorderLayout.CENTER);
        
        // Category filter
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.add(new JLabel("Category:"), BorderLayout.NORTH);
        String[] categories = {"All", "Music", "Sports", "Arts", "Family", "Comedy"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryPanel.add(categoryCombo, BorderLayout.CENTER);
        
        // Price filter
        JPanel pricePanel = new JPanel(new BorderLayout());
        pricePanel.add(new JLabel("Max Price:"), BorderLayout.NORTH);
        JSlider priceSlider = new JSlider(0, 500, 500);
        priceSlider.setMajorTickSpacing(100);
        priceSlider.setPaintTicks(true);
        priceSlider.setPaintLabels(true);
        pricePanel.add(priceSlider, BorderLayout.CENTER);
        
        // Add all filter panels
        advancedOptionsPanel.add(locationPanel);
        advancedOptionsPanel.add(datePanel);
        advancedOptionsPanel.add(categoryPanel);
        advancedOptionsPanel.add(pricePanel);
        
        // Add action for advanced search button
        advancedButton.addActionListener(e -> {
            advancedOptionsPanel.setVisible(!advancedOptionsPanel.isVisible());
            advancedButton.setText(advancedOptionsPanel.isVisible() ? "Hide Advanced" : "Advanced Search");
        });
        
        // Create combined search panel
        advancedSearchPanel.add(searchControlPanel, BorderLayout.NORTH);
        advancedSearchPanel.add(advancedOptionsPanel, BorderLayout.CENTER);
        
        searchPanel.add(eventsHeaderPanel, BorderLayout.NORTH);
        searchPanel.add(advancedSearchPanel, BorderLayout.CENTER);
        
        // Create events table
        String[] columns = {"Event Name", "Location", "Date", "Available Tickets", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventTable = new JTable(model);
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventTable.setRowHeight(25);
        
        JScrollPane tableScrollPane = new JScrollPane(eventTable);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        viewEventButton = new JButton("View Event Details");
        addToCartButton = new JButton("Add to Cart");
        
        buttonPanel.add(viewEventButton);
        buttonPanel.add(addToCartButton);
        
        // Create a proper layout for the events panel with search at the top
        eventsPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create a center panel that contains both search and table
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        // Create the search bar component
        SimpleSearchBar searchBar = new SimpleSearchBar(this, eventTable, currentEvents);
        
        // Add search bar to a container with some padding
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchContainer.add(searchBar, BorderLayout.CENTER);
        
        // Add search container at the top and table below it
        centerPanel.add(searchContainer, BorderLayout.NORTH); 
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Add the center panel and button panel to the events panel
        eventsPanel.add(centerPanel, BorderLayout.CENTER);
        eventsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Search is now handled by the SimpleSearchBar component
        
        viewEventButton.addActionListener(e -> {
            int selectedRow = eventTable.getSelectedRow();
            if (selectedRow != -1) {
                // Make sure we don't exceed the array bounds
                if (selectedRow < currentEvents.size()) {
                    // Get the event name from the table to double-check
                    String selectedEventName = (String) eventTable.getValueAt(selectedRow, 0);
                    Event selectedEvent = currentEvents.get(selectedRow);
                    
                    // Extra verification to ensure we have the right event
                    if (!selectedEventName.equals(selectedEvent.getName())) {
                        // If names don't match, find the event by name
                        for (Event event : currentEvents) {
                            if (event.getName().equals(selectedEventName)) {
                                selectedEvent = event;
                                break;
                            }
                        }
                    }
                    
                    displayEventDetails(selectedEvent);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error: Selected row data is not available",
                            "Selection Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select an event first",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        
        addToCartButton.addActionListener(e -> {
            int selectedRow = eventTable.getSelectedRow();
            if (selectedRow != -1) {
                // Make sure we don't exceed the array bounds
                if (selectedRow < currentEvents.size()) {
                    // Get the event name from the table to double-check
                    String selectedEventName = (String) eventTable.getValueAt(selectedRow, 0);
                    Event selectedEvent = currentEvents.get(selectedRow);
                    
                    // Extra verification to ensure we have the right event
                    if (!selectedEventName.equals(selectedEvent.getName())) {
                        // If names don't match, find the event by name
                        for (Event event : currentEvents) {
                            if (event.getName().equals(selectedEventName)) {
                                selectedEvent = event;
                                break;
                            }
                        }
                    }
                    
                    int quantity = promptForQuantity(selectedEvent);
                    
                    if (quantity > 0) {
                        userManager.getCurrentUser().getCart().addItem(selectedEvent, quantity);
                        JOptionPane.showMessageDialog(this,
                                quantity + " ticket(s) for " + selectedEvent.getName() + " added to cart",
                                "Added to Cart",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error: Selected row data is not available",
                            "Selection Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select an event first",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    
    /**
     * Create the cart panel for viewing and managing cart items
     */
    private void createCartPanel() {
        cartPanel = new JPanel(new BorderLayout(10, 10));
        cartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header panel with navigation
        JPanel headerPanel = createHeaderPanel();
        
        // Create cart header
        JPanel cartHeaderPanel = new JPanel(new BorderLayout());
        JLabel cartLabel = new JLabel("Shopping Cart", JLabel.CENTER);
        cartLabel.setOpaque(true);
        cartLabel.setBackground(new Color(184, 134, 84)); // Brown color
        cartLabel.setForeground(Color.WHITE);
        cartLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cartLabel.setPreferredSize(new Dimension(0, 30));
        cartHeaderPanel.add(cartLabel, BorderLayout.CENTER);
        
        // Create cart table
        String[] columns = {"Event Name", "Location", "Date", "Quantity", "Price", "Total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        cartTable = new JTable(model);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.setRowHeight(25);
        
        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        
        // Create summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(4, 2, 10, 5));
        summaryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        summaryPanel.add(new JLabel("Subtotal:", JLabel.RIGHT));
        subtotalLabel = new JLabel("$0.00");
        summaryPanel.add(subtotalLabel);
        
        summaryPanel.add(new JLabel("Tax (13%):", JLabel.RIGHT));
        taxLabel = new JLabel("$0.00");
        summaryPanel.add(taxLabel);
        
        summaryPanel.add(new JLabel("Total:", JLabel.RIGHT));
        totalLabel = new JLabel("$0.00");
        summaryPanel.add(totalLabel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton removeButton = new JButton("Remove Selected");
        checkoutButton = new JButton("Proceed to Checkout");
        JButton continueShoppingButton = new JButton("Continue Shopping");
        
        buttonPanel.add(removeButton);
        buttonPanel.add(continueShoppingButton);
        buttonPanel.add(checkoutButton);
        
        // Create right panel for summary and buttons
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(summaryPanel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add components to cart panel
        cartPanel.add(headerPanel, BorderLayout.NORTH);
        cartPanel.add(cartHeaderPanel, BorderLayout.NORTH);
        cartPanel.add(tableScrollPane, BorderLayout.CENTER);
        cartPanel.add(rightPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        removeButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                userManager.getCurrentUser().getCart().removeItem(selectedRow);
                updateCartDisplay();
            }
        });
        
        continueShoppingButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "events");
        });
        
        checkoutButton.addActionListener(e -> {
            if (!userManager.getCurrentUser().getCart().isEmpty()) {
                cardLayout.show(mainPanel, "checkout");
                updateCheckoutDisplay();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Your cart is empty",
                        "Empty Cart",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    /**
     * Create the checkout panel for completing an order
     */
    private void createCheckoutPanel() {
        checkoutPanel = new JPanel(new BorderLayout(10, 10));
        checkoutPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header panel with navigation
        JPanel headerPanel = createHeaderPanel();
        
        // Create checkout header
        JPanel checkoutHeaderPanel = new JPanel(new BorderLayout());
        JLabel checkoutLabel = new JLabel("Checkout", JLabel.CENTER);
        checkoutLabel.setOpaque(true);
        checkoutLabel.setBackground(new Color(184, 134, 84)); // Brown color
        checkoutLabel.setForeground(Color.WHITE);
        checkoutLabel.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutLabel.setPreferredSize(new Dimension(0, 30));
        checkoutHeaderPanel.add(checkoutLabel, BorderLayout.CENTER);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Shipping Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);
        
        formPanel.add(new JLabel("Credit Card Number:"));
        creditCardField = new JTextField();
        formPanel.add(creditCardField);
        
        formPanel.add(new JLabel("Payment Method:"));
        paymentMethodCombo = new JComboBox<>(new String[] {"Credit Card", "PayPal", "Apple Pay"});
        formPanel.add(paymentMethodCombo);
        
        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        
        summaryPanel.add(new JLabel("Subtotal:"));
        JLabel checkoutSubtotalLabel = new JLabel("$0.00");
        summaryPanel.add(checkoutSubtotalLabel);
        
        summaryPanel.add(new JLabel("Tax (13%):"));
        JLabel checkoutTaxLabel = new JLabel("$0.00");
        summaryPanel.add(checkoutTaxLabel);
        
        summaryPanel.add(new JLabel("Total:"));
        JLabel checkoutTotalLabel = new JLabel("$0.00");
        summaryPanel.add(checkoutTotalLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backToCartButton = new JButton("Back to Cart");
        confirmOrderButton = new JButton("Confirm Order");
        
        buttonPanel.add(backToCartButton);
        buttonPanel.add(confirmOrderButton);
        
        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(summaryPanel, BorderLayout.CENTER);
        
        // Add components to checkout panel
        checkoutPanel.add(headerPanel, BorderLayout.NORTH);
        checkoutPanel.add(checkoutHeaderPanel, BorderLayout.NORTH);
        checkoutPanel.add(centerPanel, BorderLayout.CENTER);
        checkoutPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        backToCartButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "cart");
        });
        
        confirmOrderButton.addActionListener(e -> {
            // First check if cart is empty
            if (userManager.getCurrentUser().getCart().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Your cart is empty. Please add some items before checking out.",
                        "Empty Cart",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (validateCheckoutForm()) {
                try {
                    // Create order
                    Order order = new Order(
                            userManager.getCurrentUser().getCart(),
                            paymentMethodCombo.getSelectedItem().toString());
                    
                    // Verify ticket availability one more time
                    boolean allAvailable = true;
                    StringBuilder unavailableItems = new StringBuilder();
                    
                    for (CartItem item : userManager.getCurrentUser().getCart().getItems()) {
                        Event event = item.getEvent();
                        if (item.getQuantity() > event.getAvailableTickets()) {
                            allAvailable = false;
                            unavailableItems.append("- ").append(event.getName())
                                           .append(" (requested: ").append(item.getQuantity())
                                           .append(", available: ").append(event.getAvailableTickets())
                                           .append(")\n");
                        }
                    }
                    
                    if (!allAvailable) {
                        JOptionPane.showMessageDialog(this,
                                "Some items in your cart are no longer available in the requested quantity:\n" +
                                unavailableItems.toString() +
                                "\nPlease update your cart before proceeding.",
                                "Availability Changed",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    // Add to order history
                    userManager.getCurrentUser().getOrderHistory().addOrder(order);
                    
                    // Update ticket availability for purchased events
                    for (CartItem item : userManager.getCurrentUser().getCart().getItems()) {
                        Event event = item.getEvent();
                        event.setAvailableTickets(event.getAvailableTickets() - item.getQuantity());
                    }
                    
                    // Clear cart
                    userManager.getCurrentUser().getCart().clear();
                    
                    // Show confirmation
                    JOptionPane.showMessageDialog(this,
                            "Your order has been placed successfully!\n" +
                            "Order #" + order.getOrderId(),
                            "Order Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    // Return to events page
                    cardLayout.show(mainPanel, "events");
                    loadEvents(); // Refresh events to show updated availability
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                            "An error occurred while processing your order: " + ex.getMessage(),
                            "Order Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the order history panel
     */
    private void createOrderHistoryPanel() {
        orderHistoryPanel = new JPanel(new BorderLayout(10, 10));
        orderHistoryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header panel with navigation
        JPanel headerPanel = createHeaderPanel();
        
        // Create history header
        JPanel historyHeaderPanel = new JPanel(new BorderLayout());
        JLabel historyLabel = new JLabel("Order History", JLabel.CENTER);
        historyLabel.setOpaque(true);
        historyLabel.setBackground(new Color(184, 134, 84)); // Brown color
        historyLabel.setForeground(Color.WHITE);
        historyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        historyLabel.setPreferredSize(new Dimension(0, 30));
        historyHeaderPanel.add(historyLabel, BorderLayout.CENTER);
        
        // Create orders table
        String[] orderColumns = {"Order ID", "Order Date", "# Items", "Total", "Payment Method"};
        DefaultTableModel orderModel = new DefaultTableModel(orderColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        orderHistoryTable = new JTable(orderModel);
        orderHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderHistoryTable.setRowHeight(25);
        
        JScrollPane orderScrollPane = new JScrollPane(orderHistoryTable);
        
        // Create items table
        String[] itemColumns = {"Event", "Location", "Date", "Quantity", "Price", "Total"};
        DefaultTableModel itemModel = new DefaultTableModel(itemColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        orderItemsTable = new JTable(itemModel);
        orderItemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderItemsTable.setRowHeight(25);
        
        JScrollPane itemsScrollPane = new JScrollPane(orderItemsTable);
        
        // Create split panel
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                orderScrollPane,
                itemsScrollPane);
        splitPane.setDividerLocation(200);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back to Events");
        
        buttonPanel.add(backButton);
        
        // Add components to panel
        orderHistoryPanel.add(headerPanel, BorderLayout.NORTH);
        orderHistoryPanel.add(historyHeaderPanel, BorderLayout.NORTH);
        orderHistoryPanel.add(splitPane, BorderLayout.CENTER);
        orderHistoryPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        orderHistoryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = orderHistoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    updateOrderItemsTable(selectedRow);
                }
            }
        });
        
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "events");
        });
    }
    
    /**
     * Create the header panel with navigation buttons
     * 
     * @return The header panel
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel logoLabel = new JLabel("TicketTango");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoPanel.add(logoLabel);
        
        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        homeButton = new JButton("Home");
        cartButton = new JButton("Cart");
        profileButton = new JButton("Profile");
        logoutButton = new JButton("Logout");
        
        navPanel.add(homeButton);
        navPanel.add(cartButton);
        navPanel.add(profileButton);
        navPanel.add(logoutButton);
        
        // Add panels to header
        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);
        
        // Add navigation button listeners
        homeButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "events");
        });
        
        cartButton.addActionListener(e -> {
            updateCartDisplay();
            cardLayout.show(mainPanel, "cart");
        });
        
        profileButton.addActionListener(e -> {
            updateOrderHistoryDisplay();
            cardLayout.show(mainPanel, "orderHistory");
        });
        
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to log out?",
                    "Logout", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                userManager.logout();
                cardLayout.show(mainPanel, "login");
            }
        });
        
        return headerPanel;
    }
    
    /**
     * Load events from the Ticketmaster API
     */
    private void loadEvents() {
        // Show waiting cursor while loading
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        try {
            // Try to get events from the API
            ArrayList<Event> apiEvents = apiWrapper.getUpcomingEvents(20);
            
            // If we have events from the API, use them
            if (apiEvents != null && !apiEvents.isEmpty()) {
                currentEvents = apiEvents;
                System.out.println("Successfully loaded " + currentEvents.size() + " events from API");
            } else {
                // If API returns no events, generate sample events as fallback
                System.out.println("No events from API, using fallback sample events");
                loadSampleEvents();
            }
            
            updateEventTable();
        } catch (Exception e) {
            // If API call fails, show error and load sample events
            System.out.println("Error loading events from API: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Could not fetch events from Ticketmaster. Using sample events instead.",
                    "Loading Events", JOptionPane.INFORMATION_MESSAGE);
            
            // Load sample events as fallback
            loadSampleEvents();
            updateEventTable();
        } finally {
            // Restore cursor
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Load sample events as fallback when API is not available
     */
    private void loadSampleEvents() {
        currentEvents.clear();
        System.out.println("Loading sample events as fallback...");
        
        // Toronto Blue Jays baseball games
        currentEvents.add(new Event(
            "TBJ-NYY-20250621", 
            "Toronto Blue Jays vs. New York Yankees", 
            "Rogers Centre, Toronto", 
            "2025-06-21", 
            72, 
            49.99, 
            "https://image.cnbcfm.com/api/v1/image/107223645-1681327492895-gettyimages-1244911843-8512.jpeg", 
            "Join us for an exciting baseball game as the Toronto Blue Jays take on the New York Yankees. Located in the heart of downtown Toronto, Rogers Centre offers an unmatched fan experience with its retractable roof and state-of-the-art facilities."
        ));
        
        currentEvents.add(new Event(
            "TBJ-BOS-20250808", 
            "Toronto Blue Jays vs. Boston Red Sox", 
            "Rogers Centre, Toronto", 
            "2025-08-08", 
            30, 
            54.99, 
            "https://cdn.theathletic.com/app/uploads/2023/07/24121901/GettyImages-1496991113-1024x683.jpg", 
            "The Toronto Blue Jays face the Boston Red Sox in this American League East rivalry. This matchup features two of the most exciting teams in the division, with both clubs featuring top-tier talent and championship aspirations."
        ));
        
        currentEvents.add(new Event(
            "TBJ-BAL-20250628", 
            "Toronto Blue Jays vs. Baltimore Orioles", 
            "Rogers Centre, Toronto", 
            "2025-06-28", 
            34, 
            49.99, 
            "https://static.broadcasteronline.com/wjon/sites/28/2023/10/bluejays.jpg", 
            "Toronto Blue Jays game day experience, including optional surveys regarding your baseball experience. Ticket holder assumes all risk of injury from balls and bats entering the stands. Please note that protective netting is used in Rogers Centre to ensure fan safety. For more information, please visit bluejays.com. The number of innings in a regulation game shall be determined by MLB and may be shortened in accordance with MLB rules."
        ));
        
        currentEvents.add(new Event(
            "TBJ-TB-20250516", 
            "Toronto Blue Jays vs. Tampa Bay Rays", 
            "Rogers Centre, Toronto", 
            "2025-05-16", 
            66, 
            59.99, 
            "https://www.tsn.ca/polopoly_fs/1.1927450!/fileimage/httpImage/image.jpg_gen/derivatives/landscape_620/toronto-blue-jays.jpg", 
            "AL East rivalry matchup! Experience the excitement as the Blue Jays take on the Tampa Bay Rays in this highly anticipated divisional game at Rogers Centre."
        ));
        
        // Concerts with distinct details
        currentEvents.add(new Event(
            "TS-ERAS-20250713", 
            "Taylor Swift - The Eras Tour", 
            "Scotiabank Arena, Toronto", 
            "2025-07-13", 
            25, 
            159.99, 
            "https://media.cnn.com/api/v1/images/stellar/prod/taylor-swift-eras-tour-032723.jpg", 
            "Taylor Swift brings her record-breaking Eras Tour to Toronto for an unforgettable performance. This three-hour musical journey spans her entire career, featuring songs from all her albums and spectacular production values."
        ));
        
        currentEvents.add(new Event(
            "WEEKND-20250815", 
            "The Weeknd - After Hours Tour", 
            "Scotiabank Arena, Toronto", 
            "2025-08-15", 
            50, 
            129.99, 
            "https://www.theweeknd.com/files/2022/01/DAWN-FM_3000x3000_RGB_72-1-scaled.jpg", 
            "Toronto's own global superstar The Weeknd returns to his hometown for a spectacular show featuring songs from his chart-topping Dawn FM album and all his greatest hits. Don't miss this homecoming performance at the Scotiabank Arena."
        ));
        
        // Theatre and shows
        currentEvents.add(new Event(
            "MIRVISH-LION-20250615", 
            "The Lion King - Broadway Show", 
            "Princess of Wales Theatre, Toronto", 
            "2025-06-15", 
            88, 
            199.99, 
            "https://www.mirvish.com/resize?path=/client/22f1e0ab1de3fe9d63b83fc7f24959/resize_assets/shows/lion2/img/the-lion-king-share.jpg&width=1200&height=630", 
            "Experience the groundbreaking musical phenomenon that is Disney's The Lion King at Toronto's Princess of Wales Theatre. This visually stunning production brings the African savanna to life with stunning costumes, puppets, and timeless music by Elton John and Tim Rice."
        ));
        
        currentEvents.add(new Event(
            "RAPTORS-FINALS-20250610", 
            "NBA Finals Game 3 - Toronto Raptors", 
            "Scotiabank Arena, Toronto", 
            "2025-06-10", 
            36, 
            225.99, 
            "https://globalnews.ca/wp-content/uploads/2019/06/championship.jpg", 
            "The Toronto Raptors host Game 3 of the NBA Finals. Don't miss this historic matchup as the Raptors look to secure another championship. Every seat offers an excellent view of the action in Toronto's premier sports venue."
        ));
        
        // Toronto-specific events
        currentEvents.add(new Event(
            "FAN-EXPO-20250720", 
            "Fan Expo Canada 2025", 
            "Metro Toronto Convention Centre", 
            "2025-07-20", 
            120, 
            75.99, 
            "https://fanexpohq.com/fanexpocanada/wp-content/uploads/sites/11/2023/04/FXC23-websitebanner-2048x940-1.jpg", 
            "Canada's largest comics, sci-fi, horror, anime, and gaming event! Meet your favorite actors, comic creators, and fellow fans at this celebration of all things pop culture. Featuring exclusive panels, photo ops, and merchandise."
        ));
        
        currentEvents.add(new Event(
            "TASTE-TO-20250605", 
            "Taste of Toronto Festival", 
            "Distillery District, Toronto", 
            "2025-06-05", 
            200, 
            45.50, 
            "https://i0.wp.com/torontofoodtrucks.ca/wp-content/uploads/2019/04/Taste-Of-Toronto.jpg", 
            "Taste your way through Toronto's diverse culinary scene at the annual Taste of Toronto Festival. This gastronomic celebration features the city's top restaurants, cooking demonstrations from celebrity chefs, and unlimited food samples included with your ticket."
        ));
        
        System.out.println("Loaded " + currentEvents.size() + " sample events");
    }
    
    /**
     * Search for events based on keyword
     * 
     * @param keyword Search keyword
     */
    /**
     * Search for events based on keyword
     * 
     * @param keyword Search keyword
     */
    private void searchEvents(String keyword) {
        try {
            // Display a searching message to the user
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            JOptionPane.getRootFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            // Perform the search
            currentEvents = apiWrapper.searchEvents(keyword, 20);
            updateEventTable();
            
            // Reset cursor and show results
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.getRootFrame().setCursor(Cursor.getDefaultCursor());
            
            // Show results count
            String message = "Found " + currentEvents.size() + " events matching '" + keyword + "'";
            JOptionPane.showMessageDialog(this, message, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.getRootFrame().setCursor(Cursor.getDefaultCursor());
            
            JOptionPane.showMessageDialog(this,
                    "Error searching events: " + e.getMessage(),
                    "API Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Update the event table with current events
     */
    private void updateEventTable() {
        DefaultTableModel model = (DefaultTableModel) eventTable.getModel();
        model.setRowCount(0);
        
        for (Event event : currentEvents) {
            model.addRow(new Object[] {
                    event.getName(),
                    event.getLocation(),
                    event.getDate(),
                    event.getAvailableTickets(),
                    "$" + String.format("%.2f", event.getPrice())
            });
        }
    }
    
    /**
     * Display detailed information about an event
     * 
     * @param event The event to display
     */
    private void displayEventDetails(Event event) {
        // Create a dialog to display event details
        JDialog dialog = new JDialog(this, "Event Details: " + event.getName(), true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Event header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(event.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Create content panel with image and details
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        
        // Left panel for image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imagePanel.setPreferredSize(new Dimension(250, 250));
        
        JLabel imageLabel = new JLabel("No Image", JLabel.CENTER);
        String imageUrl = event.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
                Image image = imageIcon.getImage();
                Image scaledImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                imageLabel.setText("");
            } catch (Exception e) {
                // If image can't be loaded, leave the "No Image" text
                System.out.println("Error loading image: " + e.getMessage());
            }
        }
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        // Details panel on the right
        JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Event Details"));
        
        detailsPanel.add(new JLabel("Event:", JLabel.RIGHT));
        detailsPanel.add(new JLabel(event.getName()));
        
        detailsPanel.add(new JLabel("Location:", JLabel.RIGHT));
        detailsPanel.add(new JLabel(event.getLocation()));
        
        detailsPanel.add(new JLabel("Date:", JLabel.RIGHT));
        detailsPanel.add(new JLabel(event.getDate()));
        
        detailsPanel.add(new JLabel("Available Tickets:", JLabel.RIGHT));
        detailsPanel.add(new JLabel(String.valueOf(event.getAvailableTickets())));
        
        detailsPanel.add(new JLabel("Price:", JLabel.RIGHT));
        detailsPanel.add(new JLabel("$" + String.format("%.2f", event.getPrice())));
        
        // Description panel
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));
        
        JTextArea descriptionArea = new JTextArea(5, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        
        String description = event.getDescription();
        if (description == null || description.isEmpty()) {
            description = "Join us for this amazing event at " + event.getLocation() + 
                     " on " + event.getDate() + ". Get your tickets now!";
        }
        descriptionArea.setText(description);
        
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        descriptionPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add details to right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(detailsPanel, BorderLayout.NORTH);
        rightPanel.add(descriptionPanel, BorderLayout.CENTER);
        
        // Add panels to content panel
        contentPanel.add(imagePanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.CENTER);
        
        // Button panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addToCartButton = new JButton("Add to Cart");
        JButton closeButton = new JButton("Close");
        
        buttonPanel.add(addToCartButton);
        buttonPanel.add(closeButton);
        
        // Add action listeners
        addToCartButton.addActionListener(e -> {
            if (event.getAvailableTickets() <= 0) {
                JOptionPane.showMessageDialog(dialog,
                        "Sorry, this event is sold out!",
                        "No Tickets Available", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int quantity = promptForQuantity(event);
            if (quantity > 0) {
                // Validate against available tickets
                if (quantity > event.getAvailableTickets()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Sorry, only " + event.getAvailableTickets() + " tickets are available.",
                            "Not Enough Tickets", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                userManager.getCurrentUser().getCart().addItem(event, quantity);
                JOptionPane.showMessageDialog(dialog,
                        quantity + " ticket(s) for " + event.getName() + " added to cart",
                        "Added to Cart", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });
        
        closeButton.addActionListener(e -> dialog.dispose());
        
        // Add panels to main panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
    
    /**
     * Prompt user for ticket quantity
     * 
     * @param event The event to purchase tickets for
     * @return Number of tickets, or 0 if canceled
     */
    private int promptForQuantity(Event event) {
        String[] options = new String[Math.min(10, event.getAvailableTickets())];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i + 1);
        }
        
        if (options.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Sorry, this event is sold out!",
                    "No Tickets Available", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        
        String result = (String) JOptionPane.showInputDialog(this,
                "How many tickets for " + event.getName() + "?",
                "Select Quantity", JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        
        if (result != null) {
            return Integer.parseInt(result);
        }
        
        return 0;
    }
    
    /**
     * Update the cart display with current cart items
     */
    private void updateCartDisplay() {
        // Update cart table
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        model.setRowCount(0);
        
        Cart cart = userManager.getCurrentUser().getCart();
        for (CartItem item : cart.getItems()) {
            Event event = item.getEvent();
            model.addRow(new Object[] {
                    event.getName(),
                    event.getLocation(),
                    event.getDate(),
                    item.getQuantity(),
                    "$" + String.format("%.2f", event.getPrice()),
                    "$" + String.format("%.2f", item.getTotal())
            });
        }
        
        // Update summary labels
        subtotalLabel.setText("$" + String.format("%.2f", cart.getSubtotal()));
        taxLabel.setText("$" + String.format("%.2f", cart.getTax()));
        totalLabel.setText("$" + String.format("%.2f", cart.getTotal()));
    }
    
    /**
     * Update the checkout display with current cart summary
     */
    private void updateCheckoutDisplay() {
        Cart cart = userManager.getCurrentUser().getCart();
        
        // Get labels from summary panel
        Component comp = checkoutPanel.getComponent(2); // Center panel
        JPanel centerPanel = (JPanel) comp;
        JPanel summaryPanel = (JPanel) centerPanel.getComponent(1); // Summary panel
        
        // Update summary labels
        JLabel subtotalLabel = (JLabel) summaryPanel.getComponent(1);
        JLabel taxLabel = (JLabel) summaryPanel.getComponent(3);
        JLabel totalLabel = (JLabel) summaryPanel.getComponent(5);
        
        subtotalLabel.setText("$" + String.format("%.2f", cart.getSubtotal()));
        taxLabel.setText("$" + String.format("%.2f", cart.getTax()));
        totalLabel.setText("$" + String.format("%.2f", cart.getTotal()));
        
        // Prefill name with user info
        User user = userManager.getCurrentUser();
        nameField.setText(user.getFirstName() + " " + user.getLastName());
    }
    
    /**
     * Validate the checkout form input
     * 
     * @return true if valid, false otherwise
     */
    /**
     * Validate checkout form with comprehensive input checking
     * 
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateCheckoutForm() {
        // Validate name - must contain both first and last name
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your full name",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        if (!name.contains(" ") || name.indexOf(" ") == name.length() - 1) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both your first and last name",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        // Validate address - must be at least 10 characters and contain numbers
        String address = addressField.getText().trim();
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your shipping address",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            addressField.requestFocus();
            return false;
        }
        
        if (address.length() < 10) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your complete address including street number, name, city and postal code",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            addressField.requestFocus();
            return false;
        }
        
        boolean containsDigit = false;
        for (char c : address.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }
        
        if (!containsDigit) {
            JOptionPane.showMessageDialog(this,
                    "Please include your street number in the address",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            addressField.requestFocus();
            return false;
        }
        
        // Validate credit card - must be 13-16 digits with no spaces or dashes
        String cardNumber = creditCardField.getText().trim();
        if (cardNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your credit card number",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            creditCardField.requestFocus();
            return false;
        }
        
        // Remove spaces and dashes that might have been entered
        cardNumber = cardNumber.replaceAll("[\\s-]", "");
        
        if (!cardNumber.matches("\\d{13,16}")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid credit card number (13-16 digits)",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            creditCardField.requestFocus();
            return false;
        }
        
        // Basic credit card validation using Luhn algorithm
        if (!isValidCreditCard(cardNumber)) {
            JOptionPane.showMessageDialog(this,
                    "The credit card number appears to be invalid. Please check and try again.",
                    "Form Error", JOptionPane.ERROR_MESSAGE);
            creditCardField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Update the order history display
     */
    private void updateOrderHistoryDisplay() {
        // Update orders table
        DefaultTableModel orderModel = (DefaultTableModel) orderHistoryTable.getModel();
        orderModel.setRowCount(0);
        
        OrderHistory history = userManager.getCurrentUser().getOrderHistory();
        List<Order> orders = history.getOrders();
        
        for (Order order : orders) {
            orderModel.addRow(new Object[] {
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getItems().size(),
                    "$" + String.format("%.2f", order.getTotal()),
                    order.getPaymentMethod()
            });
        }
        
        // Clear items table
        DefaultTableModel itemModel = (DefaultTableModel) orderItemsTable.getModel();
        itemModel.setRowCount(0);
    }
    
    /**
     * Update the order items table for a selected order
     * 
     * @param orderIndex Index of the selected order
     */
    private void updateOrderItemsTable(int orderIndex) {
        DefaultTableModel itemModel = (DefaultTableModel) orderItemsTable.getModel();
        itemModel.setRowCount(0);
        
        OrderHistory history = userManager.getCurrentUser().getOrderHistory();
        List<Order> orders = history.getOrders();
        
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            Order order = orders.get(orderIndex);
            
            for (CartItem item : order.getItems()) {
                Event event = item.getEvent();
                itemModel.addRow(new Object[] {
                        event.getName(),
                        event.getLocation(),
                        event.getDate(),
                        item.getQuantity(),
                        "$" + String.format("%.2f", event.getPrice()),
                        "$" + String.format("%.2f", item.getTotal())
                });
            }
        }
    }
    
    /**
     * Get the current search text from the combobox
     * 
     * @return The search text
     */
    private String getSearchText() {
        if (searchField.getEditor().getItem() instanceof String) {
            return ((String) searchField.getEditor().getItem()).trim();
        } else {
            return searchField.getEditor().getItem().toString().trim();
        }
    }
    
    /**
     * Update search suggestions based on current text
     */
    private void updateSuggestions() {
        String text = getSearchText().toLowerCase();
        if (text.isEmpty()) {
            return;
        }
        
        // Create a new model with matching suggestions
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        
        // First add from saved searches
        for (String saved : savedSearches) {
            if (saved.toLowerCase().contains(text)) {
                model.addElement(saved);
            }
        }
        
        // Then add from search history
        for (String history : searchHistory) {
            if (history.toLowerCase().contains(text) && !containsIgnoreCase(model, history)) {
                model.addElement(history);
            }
        }
        
        // Finally add from default suggestions
        for (int i = 0; i < searchSuggestions.getSize(); i++) {
            String suggestion = searchSuggestions.getElementAt(i);
            if (suggestion.toLowerCase().contains(text) && !containsIgnoreCase(model, suggestion)) {
                model.addElement(suggestion);
            }
        }
        
        if (model.getSize() > 0) {
            searchField.setModel(model);
            searchField.setSelectedItem(text);
            searchField.showPopup();
        }
    }
    
    /**
     * Add a search term to history
     * 
     * @param term Search term to add
     */
    private void addToSearchHistory(String term) {
        // Remove if it already exists (to avoid duplicates)
        searchHistory.remove(term);
        
        // Add to beginning of list (most recent first)
        searchHistory.add(0, term);
        
        // Keep history at a reasonable size
        if (searchHistory.size() > 20) {
            searchHistory.remove(searchHistory.size() - 1);
        }
        
        // Add to suggestions if not already there
        addSearchSuggestion(term);
    }
    
    /**
     * Save a search term for future reference
     * 
     * @param term Search term to save
     */
    private void saveSearch(String term) {
        if (!savedSearches.contains(term)) {
            savedSearches.add(term);
        }
        addSearchSuggestion(term);
    }
    
    /**
     * Add a search suggestion to the model
     * 
     * @param suggestion Suggestion to add
     */
    private void addSearchSuggestion(String suggestion) {
        if (!containsIgnoreCase(searchSuggestions, suggestion)) {
            searchSuggestions.addElement(suggestion);
        }
    }
    
    /**
     * Check if a combo box model contains a string (case insensitive)
     * 
     * @param model The combo box model
     * @param str The string to check for
     * @return true if found, false otherwise
     */
    private boolean containsIgnoreCase(ComboBoxModel<String> model, String str) {
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Show dialog with search history and saved searches
     */
    private void showSearchHistoryDialog() {
        JDialog dialog = new JDialog(this, "Search History", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create tabbed pane for history and saved searches
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Recent searches panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> historyModel = new DefaultListModel<>();
        for (String term : searchHistory) {
            historyModel.addElement(term);
        }
        JList<String> historyList = new JList<>(historyModel);
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane historyScroll = new JScrollPane(historyList);
        
        JPanel historyButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton useHistoryButton = new JButton("Use This Search");
        JButton clearHistoryButton = new JButton("Clear History");
        historyButtonPanel.add(useHistoryButton);
        historyButtonPanel.add(clearHistoryButton);
        
        historyPanel.add(historyScroll, BorderLayout.CENTER);
        historyPanel.add(historyButtonPanel, BorderLayout.SOUTH);
        
        // Saved searches panel
        JPanel savedPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> savedModel = new DefaultListModel<>();
        for (String term : savedSearches) {
            savedModel.addElement(term);
        }
        JList<String> savedList = new JList<>(savedModel);
        savedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane savedScroll = new JScrollPane(savedList);
        
        JPanel savedButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton useSavedButton = new JButton("Use This Search");
        JButton deleteSavedButton = new JButton("Delete Saved Search");
        savedButtonPanel.add(useSavedButton);
        savedButtonPanel.add(deleteSavedButton);
        
        savedPanel.add(savedScroll, BorderLayout.CENTER);
        savedPanel.add(savedButtonPanel, BorderLayout.SOUTH);
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Recent Searches", historyPanel);
        tabbedPane.addTab("Saved Searches", savedPanel);
        
        // Add action listeners
        useHistoryButton.addActionListener(e -> {
            int selectedIndex = historyList.getSelectedIndex();
            if (selectedIndex != -1) {
                String term = historyModel.getElementAt(selectedIndex);
                searchField.setSelectedItem(term);
                searchEvents(term);
                dialog.dispose();
            }
        });
        
        clearHistoryButton.addActionListener(e -> {
            searchHistory.clear();
            historyModel.clear();
        });
        
        useSavedButton.addActionListener(e -> {
            int selectedIndex = savedList.getSelectedIndex();
            if (selectedIndex != -1) {
                String term = savedModel.getElementAt(selectedIndex);
                searchField.setSelectedItem(term);
                searchEvents(term);
                dialog.dispose();
            }
        });
        
        deleteSavedButton.addActionListener(e -> {
            int selectedIndex = savedList.getSelectedIndex();
            if (selectedIndex != -1) {
                String term = savedModel.getElementAt(selectedIndex);
                savedSearches.remove(term);
                savedModel.remove(selectedIndex);
            }
        });
        
        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        
        // Add components to panel
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
    
    /**
     * Perform advanced search for events using multiple filters
     * 
     * @param keyword Search keyword (optional)
     * @param location Location filter (optional)
     * @param date Date filter (optional)
     * @param category Category filter (optional)
     * @param maxPrice Maximum price filter
     */
    private void advancedSearchEvents(String keyword, String location, String date, String category, int maxPrice) {
        try {
            // First get all matching events by keyword
            ArrayList<Event> results;
            if (!keyword.isEmpty()) {
                results = apiWrapper.searchEvents(keyword, 50);
            } else {
                results = apiWrapper.getUpcomingEvents(50);
            }
            
            // Apply additional filters
            ArrayList<Event> filteredResults = new ArrayList<>();
            for (Event event : results) {
                boolean matchesAll = true;
                
                // Check location filter
                if (!location.isEmpty() && !event.getLocation().toLowerCase().contains(location.toLowerCase())) {
                    matchesAll = false;
                }
                
                // Check date filter
                if (!date.isEmpty() && !event.getDate().equals(date)) {
                    matchesAll = false;
                }
                
                // Check category filter (if implemented in Event class)
                // For now, we'll just assume all events match if "All" is selected
                if (!category.equals("All")) {
                    // This would need event.getCategory() to be implemented
                    // For now, we'll do a simple check in the name or description
                    boolean categoryMatch = event.getName().toLowerCase().contains(category.toLowerCase());
                    if (!categoryMatch) {
                        matchesAll = false;
                    }
                }
                
                // Check price filter
                if (event.getPrice() > maxPrice) {
                    matchesAll = false;
                }
                
                if (matchesAll) {
                    filteredResults.add(event);
                }
            }
            
            // Update the display with filtered results
            currentEvents = filteredResults;
            updateEventTable();
            
            // Display result count in a dialog
            JOptionPane.showMessageDialog(this,
                    "Found " + filteredResults.size() + " matching events",
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error searching events: " + e.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Check if a string is a valid email address
     * 
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    /**
     * Sanitize user input to prevent injection issues
     * 
     * @param input User input string
     * @return Sanitized string
     */
    private String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        // Remove any HTML/script tags
        String sanitized = input.replaceAll("<[^>]*>", "");
        // Limit the length
        if (sanitized.length() > 50) {
            sanitized = sanitized.substring(0, 50);
        }
        return sanitized;
    }
    
    /**
     * Validate credit card number using Luhn algorithm
     * 
     * @param cardNumber Credit card number to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidCreditCard(String cardNumber) {
        // Remove any non-digit characters
        cardNumber = cardNumber.replaceAll("\\D", "");
        
        if (cardNumber.length() < 13 || cardNumber.length() > 16) {
            return false;
        }
        
        // Luhn algorithm implementation
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
    
    // Helper methods removed to simplify code
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicketTangoApp app = new TicketTangoApp();
            app.setVisible(true);
        });
    }
}
