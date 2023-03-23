import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    public JPanel HomeSearch;
    private JTextField textField1;
    private JButton Search;
    private JScrollPane displayResults;
    private JButton myCartButton;
    private JButton logoutButton;

    public Home() {
        myCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == myCartButton) {
                    JFrame startFrame3 = (JFrame) SwingUtilities.getWindowAncestor(myCartButton); // got from ChatGPT
                    startFrame3.setVisible(false);
                    JFrame startFrame = new JFrame("My Cart");
                    startFrame.setContentPane(new MyCart().Cart);
                    startFrame.setPreferredSize(new Dimension(800, 700));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    startFrame.setVisible(true);


                }
            }
        });
    }

    {
    }
}
