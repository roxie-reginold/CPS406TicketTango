
import Database.*;

import System.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
GUI containing CreateAccount.Includes fields gathered from the Customer class such as
First name, last name, email, and password. Class CreateAccount includes a function
isValidEmail to verify proper email format. The class also contains action listeners
to activate the next frame.
*/
public class CreateAccount {

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JButton btnOk;
    private JLabel lblError;
    public JPanel Form;
    private JButton backToLogInButton;
    private JLabel lblEmail;
    private Map<String, String> customers;
    private File file;
    private String fileName="UserDatabase.txt";



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
                startFrame.setVisible(true);
            }
        });
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnOk) {
                    String first = txtFirstName.getText();
                    String last = txtLastName.getText();
                    String email = txtEmail.getText();
                    String password = String.valueOf(txtPassword.getText());
                    lblError.setText("");
                    // Validate email format
                    if (!isValidEmail(email) || password.length() < 8 || first.length() < 1 || last.length() < 1) {
                        lblError.setText("Invalid info");
                    } else if (customers.containsKey(email.trim())) {
                        lblEmail.setText("Account Already Exist");
                    } else {
                        Customer c1 = new Customer( email, password, first, last);
                        DB.addCustomers(c1);

                        JFrame startFrame1 = (JFrame) SwingUtilities.getWindowAncestor(btnOk); // got from ChatGPT
                        startFrame1.setVisible(false);
                        JFrame startFrame = new JFrame("Events Page");
                        startFrame.setContentPane(new EventPage(c1).rootPanel);
                        startFrame.setPreferredSize(new Dimension(600, 400));
                        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        startFrame.pack();
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




}
