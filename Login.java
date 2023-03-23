import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Login() {


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginButton) {
                    JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(loginButton); // got from ChatGPT
                    startFrame2.setVisible(false);
                    JFrame startFrame = new JFrame("Home");
                    startFrame.setContentPane(new Home().HomeSearch);
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
                    startFrame2.setPreferredSize(new Dimension(300,400));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    startFrame.setVisible(true);


                }
            }
        });
    }

    /*public static void main(String[] args) {
        JFrame startFrame = new JFrame("Login");
        startFrame.setContentPane(new Login().Login);
        startFrame.setPreferredSize(new Dimension(300, 400));
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setVisible(true);
    }*/


}



