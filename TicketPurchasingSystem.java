package System;
import Database.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TicketPurchasingSystem implements ActionListener {



    //
    private UserDatabase customerDatabase;
    //private ArrayList<Customer> custs;

    //public TicketPurchasingSystem(){
    //    this.customerDatabase = new UserDatabase();
        //custs = new ArrayList<>();

    //}
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JLabel lblError;
    private JButton btnOk;

    public TicketPurchasingSystem(JTextField txtFirstName, JTextField txtLastName, JTextField txtEmail, JTextField txtPassword, JLabel lblError, JButton btnOk) {
        this.txtFirstName = txtFirstName;
        this.txtLastName = txtLastName;
        this.txtEmail = txtEmail;
        this.txtPassword = txtPassword;
        this.lblError = lblError;
        this.btnOk = btnOk;
        this.customerDatabase = new UserDatabase();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==  btnOk) {
            String first = txtFirstName.getText();
            String last = txtLastName.getText();
            String email = txtEmail.getText();
            String password = String.valueOf(txtPassword.getText());
            lblError.setText("");
            // Validate email format
            if ((!isValidEmail(email)) || (password.length() < 8) || (first.length() < 1)
                    || (last.length() < 1) ) {
                lblError.setText("Invalid info");

            }

            Customer newCustomer = new Customer( email, password, first, last);
            newCustomer.print();
            customerDatabase.addCustomers(newCustomer);

        }
    }

    //public void addtoDatabase(ActionEvent e) {

    //}

    //public static void addUserToSystem(Customer customer){
    //   customer.print();
     //  customerDatabase.addCustomers(customer);

    //}




    private boolean isValidEmail(String email) {
        // A simple regex pattern to check if the email format is valid
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(emailRegex);
    }





  
}