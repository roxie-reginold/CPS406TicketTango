import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;

public class CreateAccount {

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JButton btnOk;
    private JLabel lblError;
    public JPanel Form;



    public CreateAccount() {
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String first = txtFirstName.getText();
                String last = txtLastName.getText();
                String email = txtEmail.getText();
                String password = String.valueOf(txtPassword.getText());
                lblError.setText("");
                // Validate email format
                if (!isValidEmail(email)) {
                    lblError.setText("Invalid info");
                    return;
                }

                // Validate password length
                if (password.length() < 8) {
                    lblError.setText("Invalid info");
                    return;
                }
                if (first.length() < 1) {
                    lblError.setText("Invalid info");
                    return;
                }
                if (last.length() < 1) {
                    lblError.setText("Invalid info");
                    return;
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

    // Helper method for authentication logic (replace with your own logic)

  //  public static void main(String[] args) {
  //      JFrame frame = new JFrame("Create Account");
  //      frame.setPreferredSize(new Dimension(300,400));
  //      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //      frame.setContentPane(new CreateAccount().Form);
  //      frame.pack();
   //     frame.setVisible(true);

    //}


    // A simple User class for demo purposes
//    private class User {
//        private String firstName;
//        private String lastName;
//        private String email;
//
//        public User(String firstName, String lastName, String email) {
//            this.firstName = firstName;
//            this.lastName = lastName;
//            this.email = email;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//    }
}
