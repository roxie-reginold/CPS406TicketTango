import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyCart {
    private JButton myCartButton;
    private JButton logoutButton;
    public JPanel Cart;
    private JButton checkoutButton;
    private JButton homeButton;


    public MyCart() {
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == checkoutButton) {
                    JFrame startFrame1 = (JFrame) SwingUtilities.getWindowAncestor(checkoutButton); // got from ChatGPT
                    startFrame1.setVisible(false);
                    JFrame payment = new JFrame("Payment");
                    payment.setContentPane(new Payment().paymentPanel);
                    payment.setTitle("Payment");
                    payment.setSize(800,700);
                    payment.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    payment.setVisible(true);
                    //JFrame startFrame = new JFrame("Login");
                    //startFrame.setContentPane(new Payment().paymentPanel);
                    //startFrame.setPreferredSize(new Dimension(300, 400));
                    //startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //startFrame.pack();
                    //startFrame.setVisible(true);


                }
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(homeButton); // got from ChatGPT
                startFrame2.setVisible(false);
                JFrame startFrame = new JFrame("Home");
                startFrame.setContentPane(new EventPage().rootPanel);
                startFrame.setPreferredSize(new Dimension(800, 700));
                startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startFrame.pack();
                startFrame.setVisible(true);
            }
        });
    }
}
