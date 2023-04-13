import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//GUI that starts system
public class WelcomeStart {
    private JPanel startPanel = new JPanel(new BorderLayout());
    private JButton startButton;
    private JLabel welcome;

    /**
     * This code is a GUI application that serves as the starting point for a system.
     * It features a welcoming message and a button that prompts the user to login.
     * Upon clicking the button, a login form is displayed for the user to enter their username and password.
     * This simple and user-friendly design makes it easy for users to access the system and get started quickly.
     *
     * @author  TicketTango
     * @version 1.0
     * @since   2023-04-02
     */
    public WelcomeStart() {
        startButton = new JButton("Start");
        startButton.setFont(new Font("New York Times", Font.BOLD, 14));
        startButton.setBackground(Color.BLACK);
        startButton.setPreferredSize(new Dimension(100, 40));
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
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
                    Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                    startFrame.setIconImage(icon);
                    startFrame.setLocationRelativeTo(null);
                    startFrame.setVisible(true);
                }
            }
        });
        startPanel.add(startButton, BorderLayout.SOUTH);
    }

    /**
     * Main method to start System.
     *
     */
    public static void main(String[] args) {
        JFrame startFrame = new JFrame("Ticket Tango");
        WelcomeStart welcomeStart = new WelcomeStart();
        ImageIcon imageIcon = new ImageIcon("logo.png");
        Image image = imageIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);
        welcomeStart.startPanel.add(imageLabel, BorderLayout.CENTER);
        startFrame.setContentPane(welcomeStart.startPanel);
        startFrame.setPreferredSize(new Dimension(600, 600));
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.pack();
        Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
        startFrame.setIconImage(icon);
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }
}

