import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeStart {
    private JPanel startPanel;
    private JButton startButton;
    private JLabel welcome;

    public WelcomeStart() {
        startButton.addActionListener(new ActionListener() {
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
                    startFrame.setVisible(true);


                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame startFrame = new JFrame("App");
        startFrame.setContentPane(new WelcomeStart().startPanel);
        startFrame.setPreferredSize(new Dimension(300, 400));
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        startFrame.setVisible(true);
    }



    }

