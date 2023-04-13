import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import System.*;

/**
 * GUI Containing Login. Includes username and password information as well as
 * buttons indicating a new account(signUpButton) and login (loginButton).
 *
 * @author  TicketTango
 * @version 1.0
 * @since   2023-04-02
 */
public class Login {
    public JPanel Login;
    private JPanel userName;
    private JLabel userNameLabel;
    private JPanel userNameInput;
    private JTextField txtUsername;
    private JPanel Password;
    private JPasswordField txtPassword;
    private JButton signUpButton;
    private JButton loginButton;
    private JLabel lblInvalid;
    private JButton adminLoginButton;

    private Map<String, String> customers;
    private File file;
    private String fileName="UserDatabase.txt";

    /**
     *  Initializing the GUI components
     */
    public Login() {

        lblInvalid.setText("");
        this.customers = new HashMap<String, String>();
        this.file = new File(fileName);
        // Read the user data from the file and store it in a Map
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.out.println("Invalid input line: " + line);
                    continue;
                }
                String email = parts[0];
                String password = parts[1];
                customers.put(email,password);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }



        // Add action listeners to the login and sign up buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginButton) {
                    String email = txtUsername.getText();
                    String pass = String.valueOf(txtPassword.getPassword());

                    if (customers.containsKey(email) && customers.get(email).equals(pass)){
                        // Create a new Customer object with the given email and password
                        Customer c1 = new Customer(email,pass);
                        // Hide the current login window and open the Event Page window
                        JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(loginButton); // got from ChatGPT
                        startFrame2.setVisible(false);
                        JFrame startFrame = new JFrame("Home");
                        startFrame.setContentPane(new EventPage(c1).rootPanel);
                        startFrame.setPreferredSize(new Dimension(800, 700));
                        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        startFrame.pack();
                        Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                        startFrame.setIconImage(icon);
                        startFrame.setLocationRelativeTo(null);
                        startFrame.setVisible(true);
                    }else{
                        lblInvalid.setText("Invalid Email or Password");
                    }


                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            /**
             *
             * this action listener is for when a user signs up
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == signUpButton) {
                    // Hide the current login window and open the Create Account window
                    JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(signUpButton); // got from ChatGPT
                    startFrame2.setVisible(false);
                    JFrame startFrame = new JFrame("Create An Account");
                    startFrame.setContentPane(new CreateAccount().Form);
                    startFrame.setPreferredSize(new Dimension(500,600));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                    startFrame.setIconImage(icon);
                    startFrame.setLocationRelativeTo(null);
                    startFrame.setVisible(true);


                }
            }
        });
        adminLoginButton.addActionListener(new ActionListener() {
            /**
             *this is an action listener for when the admin logs in
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == adminLoginButton) {
                    // Hide the current login window and open the Admin Account window
                    JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(adminLoginButton); // got from ChatGPT
                    startFrame2.setVisible(false);
                    JFrame startFrame = new JFrame("Admin Account");
                    startFrame.setContentPane(new AdminLogin().adminLogin);
                    startFrame.setPreferredSize(new Dimension(300,400));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                    startFrame.setIconImage(icon);
                    startFrame.setLocationRelativeTo(null);
                    startFrame.setVisible(true);


                }
            }
        });
    }


}



