import Database.*;
import System.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * GUI containing CreateAccount.Includes fields gathered from the Customer class such as
 * First name, last name, email, and password. Class CreateAccount includes a function
 * isValidEmail to verify proper email format. The class also contains action listeners
 * to activate the next frame.
 *
 * @author  TicketTango
 * @version 1.0
 * @since   2023-04-02
 */
public class CreateAccount {

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnOk;
    private JLabel lblError;
    public JPanel Form;
    private JButton backToLogInButton;
    private JLabel lblEmail;
    private Map<String, String> customers;
    private File file;
    private String fileName="UserDatabase.txt";


    /**
     * This constructor is for the CreateAccount GUI
     */
    public CreateAccount() {

        lblError.setText("");
        lblEmail.setText("");
        UserDatabase DB = new UserDatabase();
        this.customers = new HashMap<String, String>();
        this.file = new File(fileName);
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

        txtFirstName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if(Character.isLetter(c)||Character.isWhitespace(c)|| Character.isISOControl(c)){
                    txtFirstName.setEditable(true);
                }else{
                    txtFirstName.setEditable(false);
                }
            }
        });
        txtLastName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if(Character.isLetter(c)||Character.isWhitespace(c)|| Character.isISOControl(c)){
                    txtLastName.setEditable(true);
                }else{
                    txtLastName.setEditable(false);
                }
            }
        });




        backToLogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame startFrame1 = (JFrame) SwingUtilities.getWindowAncestor(backToLogInButton); // got from ChatGPT
                startFrame1.setVisible(false);
                JFrame startFrame = new JFrame("Login");
                startFrame.setContentPane(new Login().Login);
                startFrame.setPreferredSize(new Dimension(300, 400));
                startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startFrame.pack();
                Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                startFrame.setIconImage(icon);
                startFrame.setLocationRelativeTo(null);
                startFrame.setVisible(true);

            }
        });
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnOk) {
                    String first = txtFirstName.getText();
                    String last = txtLastName.getText();
                    String email = txtEmail.getText().trim();
                    String password = String.valueOf(txtPassword.getPassword());
                    lblError.setText("");
                    // Validate email format
                    if (!isValidEmail(email) || password.length() < 8 || first.length() < 1 || last.length() < 1|| containsTicketTangoCa(email)) {
                        lblError.setText("Invalid info");
                    } else if (customers.containsKey(email.trim())) {
                        lblEmail.setText("Account Already Exist");
                    } else {
                        Customer c1 = new Customer( email, password, first, last);
                        DB.addUser(email,password);

                        JFrame startFrame1 = (JFrame) SwingUtilities.getWindowAncestor(btnOk); // got from ChatGPT
                        startFrame1.setVisible(false);
                        JFrame startFrame = new JFrame("Events Page");
                        startFrame.setContentPane(new EventPage(c1).rootPanel);
                        startFrame.setPreferredSize(new Dimension(800, 700));
                        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        startFrame.pack();
                        Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                        startFrame.setIconImage(icon);
                        startFrame.setLocationRelativeTo(null);
                        startFrame.setVisible(true);


                    }
                }

            }
        });

    }

    // Helper method to check email format
    private boolean isValidEmail(String email) {
        // A simple regex pattern to check if the email format is valid
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(emailRegex);
    }
    public boolean containsTicketTangoCa(String input) {
        if (input == null) {
            return false;
        }
        return input.contains("@ticket.tango.ca");
    }




}
