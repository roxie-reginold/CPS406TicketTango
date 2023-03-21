import javax.swing.*;
import java.awt.*;

public class Login{
    private JPanel Login;
    private JPanel userName;
    private JLabel userNameLabel;
    private JPanel userNameInput;
    private JTextField userNameIn;
    private JPanel Password;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JButton loginButton;

    public Login() {


    }

    public static void main(String[] args) {
        JFrame startFrame = new JFrame("Login");
        startFrame.setContentPane(new Login().Login);
        startFrame.setPreferredSize(new Dimension(300, 400));
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setVisible(true);
    }



}



