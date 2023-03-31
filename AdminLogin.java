import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import Database.EventDatabase;
import Database.UserDatabase;
import System.*;

/*
GUI Containing Login. Includes username and password information as well as
buttons indicating a new account(signUpButton) and login (loginButton).
*/
public class AdminLogin{
    public JPanel adminLogin;
    private JPanel userName;
    private JLabel userNameLabel;
    private JPanel userNameInput;
    private JTextField txtUsername;
    private JPanel Password;
    private JPasswordField txtPassword;
    private JButton loginButton;
    private JLabel lblInvalid;
    private JButton customerLoginButton;

    private Map<String, String> admins;
    private File file;
    private String fileName="UserDatabase.txt";
    UserDatabase DBUser;
//    EventDatabase evdb;

    public  AdminLogin() {
        // Initializing the GUI components
        lblInvalid.setText("");
        DBUser = new UserDatabase();
        this.admins = DBUser.getUserDatabase();




        // Add action listeners to the login and sign up buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginButton) {

                    String email = txtUsername.getText().toString().toLowerCase();
                    String pass = String.valueOf(txtPassword.getPassword());

                    if (admins.containsKey(email) && admins.get(email).equals(pass) ){
                        // Create a new Customer object with the given email and password
                        Admin a1 = new Admin(email,pass);
                        // Hide the current login window and open the Event Page window
                        JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(loginButton); // got from ChatGPT
                        startFrame2.setVisible(false);
                        JFrame startFrame = new JFrame("Admin Page");
                        startFrame.setContentPane(new AdminPage(a1).rootPanel);
                        startFrame.setPreferredSize(new Dimension(800, 700));
                        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        startFrame.pack();
                        startFrame.setLocationRelativeTo(null);
                        startFrame.setVisible(true);
                    }else{
                        lblInvalid.setText("Invalid Email or Password");
                    }


                }
            }
        });

        customerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == customerLoginButton) {
                    JFrame startFrame1 = (JFrame) SwingUtilities.getWindowAncestor(customerLoginButton); // got from ChatGPT
                    startFrame1.setVisible(false);
                    JFrame startFrame = new JFrame("Login");
                    startFrame.setContentPane(new Login().Login);
                    startFrame.setPreferredSize(new Dimension(300, 400));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    startFrame.setLocationRelativeTo(null);
                    startFrame.setVisible(true);


                }
            }
        });
    }



}



