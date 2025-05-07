package tickettango;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Admin login screen for TicketTango application
 * Provides administrator access to system functionality
 */
public class AdminLoginScreen extends JFrame {
    private JPanel mainPanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel statusLabel;
    
    // Predefined admin credentials
    private static final String ADMIN_EMAIL = "admin@ticket.tango.ca";
    private static final String ADMIN_PASSWORD = "admin123";
    
    /**
     * Constructor for AdminLoginScreen
     */
    public AdminLoginScreen() {
        super("TicketTango Admin Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        setupUI();
    }
    
    /**
     * Set up the admin login UI
     */
    private void setupUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Admin Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Login panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        loginPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        loginPanel.add(emailField);
        
        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);
        
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        loginPanel.add(statusLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        loginButton = new JButton("Login");
        backButton = new JButton("Back to User Login");
        
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                if (email.isEmpty() || password.isEmpty()) {
                    statusLabel.setText("Please enter email and password");
                    return;
                }
                
                // Check admin credentials
                if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                    // Create admin user
                    Admin admin = new Admin(email, password, "System", "Administrator");
                    
                    // Open admin dashboard
                    openAdminDashboard(admin);
                    
                    // Close login window
                    dispose();
                } else {
                    statusLabel.setText("Invalid admin credentials");
                }
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close admin login window
                new TicketTangoApp().setVisible(true);
            }
        });
        
        setContentPane(mainPanel);
    }
    
    /**
     * Open the admin dashboard with the authenticated admin user
     * 
     * @param admin The authenticated admin user
     */
    private void openAdminDashboard(Admin admin) {
        AdminDashboard dashboard = new AdminDashboard(admin);
        dashboard.setVisible(true);
    }
    
    /**
     * Main method to launch admin login directly (for testing)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminLoginScreen().setVisible(true);
            }
        });
    }
}
