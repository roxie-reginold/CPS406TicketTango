package tickettango;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Admin Dashboard for TicketTango application
 * Provides administrative tools for managing events and users
 */
public class AdminDashboard extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTable eventsTable;
    private JTable usersTable;
    private JButton addEventButton;
    private JButton removeEventButton;
    private JButton editEventButton;
    private JButton addUserButton;
    private JButton removeUserButton;
    private JButton logoutButton;
    
    private Admin admin;
    private TicketmasterAPIWrapper apiWrapper;
    private ArrayList<Event> currentEvents;
    
    /**
     * Constructor for AdminDashboard
     * 
     * @param admin The admin user
     */
    public AdminDashboard(Admin admin) {
        super("TicketTango Admin Dashboard");
        this.admin = admin;
        this.apiWrapper = new TicketmasterAPIWrapper();
        this.currentEvents = new ArrayList<>();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        setupUI();
        loadEvents();
    }
    
    /**
     * Set up the admin dashboard UI
     */
    private void setupUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel userLabel = new JLabel("Logged in as: " + admin.getEmail());
        logoutButton = new JButton("Logout");
        userPanel.add(userLabel);
        userPanel.add(logoutButton);
        headerPanel.add(userPanel, BorderLayout.EAST);
        
        // Create tabbed pane for different admin functions
        tabbedPane = new JTabbedPane();
        
        // Events tab
        JPanel eventsPanel = createEventsPanel();
        tabbedPane.addTab("Events Management", eventsPanel);
        
        // Users tab
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("User Management", usersPanel);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add logout action
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close dashboard
                new AdminLoginScreen().setVisible(true);
            }
        });
        
        setContentPane(mainPanel);
    }
    
    /**
     * Create the events management panel
     * 
     * @return The events panel
     */
    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create table for events
        String[] columns = {"Event ID", "Name", "Location", "Date", "Tickets", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        eventsTable = new JTable(model);
        eventsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        
        // Create search panel
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search Events");
        JButton refreshButton = new JButton("Refresh");
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        addEventButton = new JButton("Add Event");
        removeEventButton = new JButton("Remove Event");
        editEventButton = new JButton("Edit Event");
        
        buttonPanel.add(addEventButton);
        buttonPanel.add(removeEventButton);
        buttonPanel.add(editEventButton);
        
        // Add action listeners
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                searchEvents(keyword);
            }
        });
        
        refreshButton.addActionListener(e -> loadEvents());
        
        addEventButton.addActionListener(e -> showAddEventDialog());
        
        removeEventButton.addActionListener(e -> {
            int selectedRow = eventsTable.getSelectedRow();
            if (selectedRow != -1) {
                Event event = currentEvents.get(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove event: " + event.getName() + "?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    admin.removeEvent(event);
                    loadEvents();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select an event to remove",
                        "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        editEventButton.addActionListener(e -> {
            int selectedRow = eventsTable.getSelectedRow();
            if (selectedRow != -1) {
                Event event = currentEvents.get(selectedRow);
                showEditEventDialog(event);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select an event to edit",
                        "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Add components to panel
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create the users management panel
     * 
     * @return The users panel
     */
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // Create table for users
        String[] columns = {"Email", "Name", "Role", "Last Login"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        usersTable = new JTable(model);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        
        // Create search panel
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search Users");
        JButton refreshButton = new JButton("Refresh");
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        addUserButton = new JButton("Add User");
        removeUserButton = new JButton("Remove User");
        
        buttonPanel.add(addUserButton);
        buttonPanel.add(removeUserButton);
        
        // Add components to panel
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add sample users (in a real app, this would come from a database)
        model.addRow(new Object[]{"roxie@ticket.tango.ca", "Roxie Admin", "Customer", "2025-05-06"});
        model.addRow(new Object[]{"shanaya@ticket.tango.ca", "Shanaya Admin", "Customer", "2025-05-05"});
        model.addRow(new Object[]{"rr7972@gmail.com", "Richard Roller", "Customer", "2025-05-01"});
        model.addRow(new Object[]{"roxie@tmu.ca", "Roxie Student", "Customer", "2025-04-30"});
        model.addRow(new Object[]{"Amy@gmail.com", "Amy Johnson", "Customer", "2025-04-28"});
        model.addRow(new Object[]{"admin@ticket.tango.ca", "System Administrator", "Admin", "2025-05-06"});
        
        return panel;
    }
    
    /**
     * Load events from the API
     */
    private void loadEvents() {
        try {
            currentEvents = apiWrapper.getUpcomingEvents(50); // Load more events for admin view
            updateEventsTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading events: " + e.getMessage(),
                    "API Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Search for events
     * 
     * @param keyword Search keyword
     */
    private void searchEvents(String keyword) {
        try {
            currentEvents = apiWrapper.searchEvents(keyword, 50);
            updateEventsTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error searching events: " + e.getMessage(),
                    "API Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Update the events table with current events
     */
    private void updateEventsTable() {
        DefaultTableModel model = (DefaultTableModel) eventsTable.getModel();
        model.setRowCount(0);
        
        for (Event event : currentEvents) {
            model.addRow(new Object[]{
                    event.getEventId(),
                    event.getName(),
                    event.getLocation(),
                    event.getDate(),
                    event.getAvailableTickets(),
                    "$" + String.format("%.2f", event.getPrice())
            });
        }
    }
    
    /**
     * Show dialog for adding a new event
     */
    private void showAddEventDialog() {
        JDialog dialog = new JDialog(this, "Add New Event", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Event Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);
        
        panel.add(new JLabel("Location:"));
        JTextField locationField = new JTextField();
        panel.add(locationField);
        
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        JTextField dateField = new JTextField();
        panel.add(dateField);
        
        panel.add(new JLabel("Available Tickets:"));
        JTextField ticketsField = new JTextField();
        panel.add(ticketsField);
        
        panel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField();
        panel.add(priceField);
        
        panel.add(new JLabel("Image URL (optional):"));
        JTextField imageUrlField = new JTextField();
        panel.add(imageUrlField);
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String location = locationField.getText().trim();
                String date = dateField.getText().trim();
                int tickets = Integer.parseInt(ticketsField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                String imageUrl = imageUrlField.getText().trim();
                
                if (name.isEmpty() || location.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please fill all required fields",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create new event
                Event event = new Event(name, location, date, tickets, price);
                event.setEventId("MANUAL-" + System.currentTimeMillis());
                event.setImageUrl(imageUrl);
                
                // Add event (in a real app, this would update a database)
                admin.addEvent(event);
                currentEvents.add(event);
                updateEventsTable();
                
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter valid numbers for tickets and price",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.setContentPane(mainPanel);
        dialog.setVisible(true);
    }
    
    /**
     * Show dialog for editing an existing event
     * 
     * @param event The event to edit
     */
    private void showEditEventDialog(Event event) {
        JDialog dialog = new JDialog(this, "Edit Event", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Event Name:"));
        JTextField nameField = new JTextField(event.getName());
        panel.add(nameField);
        
        panel.add(new JLabel("Location:"));
        JTextField locationField = new JTextField(event.getLocation());
        panel.add(locationField);
        
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        JTextField dateField = new JTextField(event.getDate());
        panel.add(dateField);
        
        panel.add(new JLabel("Available Tickets:"));
        JTextField ticketsField = new JTextField(String.valueOf(event.getAvailableTickets()));
        panel.add(ticketsField);
        
        panel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField(String.valueOf(event.getPrice()));
        panel.add(priceField);
        
        panel.add(new JLabel("Image URL:"));
        JTextField imageUrlField = new JTextField(event.getImageUrl() != null ? event.getImageUrl() : "");
        panel.add(imageUrlField);
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String location = locationField.getText().trim();
                String date = dateField.getText().trim();
                int tickets = Integer.parseInt(ticketsField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                String imageUrl = imageUrlField.getText().trim();
                
                if (name.isEmpty() || location.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please fill all required fields",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update event
                Event updatedEvent = new Event(name, location, date, tickets, price);
                updatedEvent.setEventId(event.getEventId());
                updatedEvent.setImageUrl(imageUrl.isEmpty() ? null : imageUrl);
                
                // Update in the system (in a real app, this would update a database)
                admin.updateEvent(event, updatedEvent);
                
                // Update in current events list
                int index = currentEvents.indexOf(event);
                if (index != -1) {
                    currentEvents.set(index, updatedEvent);
                    updateEventsTable();
                }
                
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter valid numbers for tickets and price",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.setContentPane(mainPanel);
        dialog.setVisible(true);
    }
}
