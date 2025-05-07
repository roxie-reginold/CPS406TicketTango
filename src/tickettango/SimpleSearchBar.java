package tickettango;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A simple search bar component for searching events
 */
public class SimpleSearchBar extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private TicketmasterAPIWrapper apiWrapper;
    private JTable eventTable;
    private ArrayList<Event> currentEvents;
    private JFrame parentFrame;
    
    /**
     * Create a simple search bar
     * 
     * @param parentFrame The parent frame
     * @param eventTable The table to update with search results
     * @param currentEvents The current events list to update
     */
    public SimpleSearchBar(JFrame parentFrame, JTable eventTable, ArrayList<Event> currentEvents) {
        this.parentFrame = parentFrame;
        this.eventTable = eventTable;
        this.currentEvents = currentEvents;
        this.apiWrapper = new TicketmasterAPIWrapper();
        
        initializeUI();
    }
    
    /**
     * Set up the UI
     */
    private void initializeUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create components
        JLabel searchLabel = new JLabel("Search Events:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        
        // Add components
        add(searchLabel);
        add(searchField);
        add(searchButton);
        
        // Add action listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        // Add enter key action
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }
    
    /**
     * Perform the search
     */
    private void performSearch() {
        String keyword = searchField.getText().trim();
        
        // Validate search term
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Please enter a search keyword.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Sanitize input
        keyword = sanitizeInput(keyword);
        
        if (keyword.length() < 2) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Search term must be at least 2 characters long.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Perform search
        try {
            // Show waiting cursor
            parentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            ArrayList<Event> searchResults = apiWrapper.searchEvents(keyword, 20);
            
            // Update current events and table
            currentEvents.clear();
            currentEvents.addAll(searchResults);
            updateEventTable();
            
            // Reset cursor
            parentFrame.setCursor(Cursor.getDefaultCursor());
            
            // Show results
            JOptionPane.showMessageDialog(parentFrame,
                    "Found " + searchResults.size() + " events matching '" + keyword + "'",
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            parentFrame.setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(parentFrame,
                    "Error searching events: " + ex.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Update the event table with current events
     */
    private void updateEventTable() {
        if (eventTable.getModel() instanceof javax.swing.table.DefaultTableModel) {
            javax.swing.table.DefaultTableModel model = 
                    (javax.swing.table.DefaultTableModel) eventTable.getModel();
            
            // Clear existing rows
            model.setRowCount(0);
            
            // Add events to table - ensure the order in the table matches the order in currentEvents
            for (int i = 0; i < currentEvents.size(); i++) {
                Event event = currentEvents.get(i);
                model.addRow(new Object[]{
                        event.getName(),
                        event.getLocation(),
                        event.getDate(),
                        event.getAvailableTickets(),
                        "$" + String.format("%.2f", event.getPrice())
                });
            }
            
            // If the table is not empty, select the first row
            if (model.getRowCount() > 0) {
                eventTable.setRowSelectionInterval(0, 0);
            }
        }
    }
    
    /**
     * Sanitize user input to prevent injection issues
     * 
     * @param input User input string
     * @return Sanitized string
     */
    private String sanitizeInput(String input) {
        // Remove any potentially harmful characters
        return input.replaceAll("[<>'\";]", "");
    }
}
