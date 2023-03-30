/*
This code creates a graphical user interface (not implemented) for organizations to create an account by entering their email,
organization name, phone number, address, password and confirming it.
Upon submission, the information is written to a CSV file and
the user is informed of the success of their account creation.
The GUI is created using Swing, a GUI toolkit for Java.
The use of a CSV file allows for easy storage and retrieval of organization account information.
Overall, this code provides a user-friendly and efficient way for organizations to create an account with the system.
 */

// Import necessary libraries

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Define the Organization class that extends JFrame
public class Organization extends JFrame {

  // Constructor method
  public Organization() {
    // Set title, size, and close operation for the JFrame
    setTitle("Create an Organization Account");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Create a new panel with a grid layout
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(6, 2, 10, 10));

    // Create labels and text fields for email, organization name, phone number, address, password, and confirm password
    JLabel emailLabel = new JLabel("Email:");
    JTextField emailField = new JTextField();

    JLabel nameLabel = new JLabel("Organization Name:");
    JTextField nameField = new JTextField();

    JLabel phoneLabel = new JLabel("Phone Number:");
    JTextField phoneField = new JTextField();

    JLabel addressLabel = new JLabel("Address:");
    JTextField addressField = new JTextField();

    JLabel passwordLabel = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField();

    JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
    JPasswordField confirmPasswordField = new JPasswordField();

    // Create a button to submit the information
    JButton submitButton = new JButton("Submit");

    // Add an action listener to the submit button
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Get the information from the text fields and password fields
        String email = emailField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

        // Check if the password and confirm password match
        if (!password.equals(confirmPassword)) {
          JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
          return;
        }

        // Write the information to a CSV file
        try {
          FileWriter csvWriter = new FileWriter("organizations.csv", true);
          csvWriter.append(email);
          csvWriter.append(",");
          csvWriter.append(name);
          csvWriter.append(",");
          csvWriter.append(password);
          csvWriter.append(",");
          csvWriter.append(phone);
          csvWriter.append(",");
          csvWriter.append(address);
          csvWriter.append("\n");
          csvWriter.flush();
          csvWriter.close();

          // Show a success message and close the JFrame
          JOptionPane.showMessageDialog(null, "Account created successfully! We will contact you shortly.");
          dispose();
        } catch (IOException ex) {
          // Show an error message if an error occurs while writing to the CSV file
          ex.printStackTrace();
          JOptionPane.showMessageDialog(null, "An error occurred while creating the account. Please try again later.");
        }
      }
    });

    // Add the labels, text fields, and button to the panel
    panel.add(emailLabel);
    panel.add(emailField);

    panel.add(nameLabel);
    panel.add(nameField);

    panel.add(phoneLabel);
    panel.add(phoneField);

    panel.add(addressLabel);
    panel.add(addressField);

    panel.add(passwordLabel);
    panel.add(passwordField);

    panel.add(confirmPasswordLabel);
    panel.add(confirmPasswordField);

    panel.add(new JLabel());
    panel.add(submitButton);

    // Set the panel as the content pane and make the JFrame visible
    setContentPane(panel);
    setVisible(true);
  }
}
