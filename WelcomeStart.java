import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//GUI that starts system
public class WelcomeStart {
    private JPanel startPanel;
    private JButton startButton;
    private JLabel welcome;
/*
This code is a GUI application that serves as the starting point for a system.
It features a welcoming message and a button that prompts the user to login.
Upon clicking the button, a login form is displayed for the user to enter their username and password.
This simple and user-friendly design makes it easy for users to access the system and get started quickly.
 */
    public WelcomeStart() {
        startButton.addActionListener(new ActionListener() { //action listener for the start button that promps login
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    JFrame startFrame1 = (JFrame) SwingUtilities.getWindowAncestor(startButton); // got from ChatGPT
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

    // main method to start System
    public static void main(String[] args) {
        JFrame startFrame = new JFrame("App");
        startFrame.setContentPane(new WelcomeStart().startPanel);
        startFrame.setPreferredSize(new Dimension(300, 400));
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);

    }



    }

