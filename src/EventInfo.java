import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * GUI containing further EventInfo. Event information such as name, date, location, price,
 * and general information are included. Event info displays further information that is
 * stored in the database. The GUI is activated when an event is selected.
 *
 * @author  TicketTango
 * @version 1.0
 * @since   2023-04-02
 */
public class EventInfo {
    public JPanel panel1;
    private JButton backToEventsButton;

    /**
     * Constructor for Event Information.
     *
     * @param name The name of the event.
     * @param location The location where the event is happening.
     * @param date The date the event is taking place.
     * @param ticketLimit The limit of tickets that can be purchased.
     * @param price The price of a single ticket.
     */
    public EventInfo(String name, String location, String date, String ticketLimit, String price){
        // Set up the panel with a better layout
        panel1 = new JPanel(new BorderLayout(10, 10));
        panel1.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel1.setBackground(new Color(245, 245, 250));

        // Create the header panel for event name
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Event name as a title
        JLabel titleLabel = new JLabel(name);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Add header to the main panel
        panel1.add(headerPanel, BorderLayout.NORTH);

        // Create content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        // Format the date for better display
        String formattedDate = formatDate(date);

        // Location section
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(locationLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JLabel locationValueLabel = new JLabel(location);
        locationValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(locationValueLabel, gbc);

        // Date section
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel dateValueLabel = new JLabel(formattedDate);
        dateValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(dateValueLabel, gbc);

        // Price section
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel priceValueLabel = new JLabel("$" + price);
        priceValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(priceValueLabel, gbc);

        // Ticket availability section
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel ticketsLabel = new JLabel("Tickets Available:");
        ticketsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(ticketsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel ticketsValueLabel = new JLabel(ticketLimit);
        ticketsValueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPanel.add(ticketsValueLabel, gbc);

        // Add an alert if tickets are running low
        if (Integer.parseInt(ticketLimit) < 50) {
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            JLabel alertLabel = new JLabel("⚠️ Only " + ticketLimit + " tickets left! Get yours before they're gone!");
            alertLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            alertLabel.setForeground(new Color(255, 69, 0)); // OrangeRed
            contentPanel.add(alertLabel, gbc);
            gbc.gridwidth = 1; // Reset gridwidth
        }

        // Event description section
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Event Description"));

        JTextArea descriptionArea = new JTextArea(
            "Join us for this amazing event at " + location + "! \n\n" +
            "This is going to be an unforgettable experience you won't want to miss. " +
            "Make sure to reserve your tickets early as they are in high demand.\n\n" +
            "The venue offers convenient access to parking and public transportation.\n\n" +
            "We recommend arriving 30 minutes before the event starts."
        );
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        descriptionArea.setBackground(new Color(250, 250, 255));

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(null);

        descriptionPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(descriptionPanel, gbc);

        // Add the content panel to the main panel
        panel1.add(contentPanel, BorderLayout.CENTER);

        // Add a footer panel with the back button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(245, 245, 250));

        backToEventsButton = new JButton("Back to Events");
        backToEventsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backToEventsButton.setBackground(new Color(25, 118, 210));
        backToEventsButton.setForeground(Color.WHITE);
        backToEventsButton.setFocusPainted(false);
        footerPanel.add(backToEventsButton);

        panel1.add(footerPanel, BorderLayout.SOUTH);

        // No need to store these values as we display them directly in the UI

        // Adding action listener to the back button
        backToEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == backToEventsButton){
                    // Getting the eventInfoFrame and disposing it
                    JFrame eventInfoFrame1 = (JFrame) SwingUtilities.getWindowAncestor(backToEventsButton);
                    eventInfoFrame1.dispose();
                }
            }
        });
    }

    /**
     * Format the date string to a more readable format
     * 
     * @param dateStr The date string in format YYYY-MM-DD
     * @return A formatted date string
     */
    private String formatDate(String dateStr) {
        try {
            // Try to parse the date if it's in YYYY-MM-DD format
            LocalDate date = LocalDate.parse(dateStr);
            return date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        } catch (DateTimeParseException e) {
            // If parsing fails, return the original string
            return dateStr;
        }
    }
}