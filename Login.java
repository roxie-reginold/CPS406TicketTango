import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Login {
    public JPanel Login;
    private JPanel userName;
    private JLabel userNameLabel;
    private JPanel userNameInput;
    private JTextField userNameIn;
    private JPanel Password;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JButton loginButton;

    //variables from CreateAccount class
    private Map<String, String> customers;
    private File file;
    private String fileName="UserDatabase.txt";
    //

    public Login() {

        String emailEntry = userNameIn.getText();
        char[] passwordEntry = passwordField1.getPassword();

        public void tangoCustomers(File file, HashMap <String, String> customers){
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
        }


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginButton) {

                    JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(loginButton); // got from ChatGPT
                    startFrame2.setVisible(false);
                    JFrame startFrame = new JFrame("Home");
                    startFrame.setContentPane(new EventPage().rootPanel);
                    startFrame.setPreferredSize(new Dimension(800, 700));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    startFrame.setVisible(true);


                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == signUpButton) {
                    JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(signUpButton); // got from ChatGPT
                    startFrame2.setVisible(false);
                    JFrame startFrame = new JFrame("Create An Account");
                    startFrame.setContentPane(new CreateAccount().Form);
                    startFrame2.setPreferredSize(new Dimension(500,600));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    startFrame.setVisible(true);


                }
            }
        });
    }

    public static void main(String[] args) {
//        JFrame startFrame = new JFrame("Login");
//        startFrame.setContentPane(new Login().Login);
//        startFrame.setPreferredSize(new Dimension(300, 400));
//        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        startFrame.pack();
//        startFrame.setVisible(true);
    }


}



