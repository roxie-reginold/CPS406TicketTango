import Database.UserDatabase.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Payment{
    private JFrame frame;
    private JPanel panel;
    private JRadioButton debitRadioButton;
    private JRadioButton creditRadioButton;
    private JFormattedTextField nameField;
    private JFormattedTextField cardField;
    private JFormattedTextField expiryField;
    private JFormattedTextField ccvField;
    private JFormattedTextField lastNameField;
    private JFormattedTextField firstNameField;
    private JFormattedTextField addressOneField;
    private JFormattedTextField addressTwoField;
    private JFormattedTextField cityField;
    private JComboBox provinceField;
    private JFormattedTextField postalField;
    private JButton cancelPaymentButton;
    private JButton confirmPaymentButton;
    public JPanel paymentPanel;

    public Payment() {


        cancelPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelPaymentButton) {
                    JFrame startFrame3 = (JFrame) SwingUtilities.getWindowAncestor(cancelPaymentButton); // got from ChatGPT
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
        confirmPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //take the objects in cart of the user and turn them into order history
            }
        });
    }

    public static void main(String[] args) {
        Payment payment = new Payment();
    }
    public void setData(Payment data) {
    }

    public void getData(Payment data) {
    }

    public boolean isModified(Payment data) {
        return false;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        postalField = new JFormattedTextField(createFormatter("U#U#U#"));
        cardField = new JFormattedTextField(createFormatter("#### #### #### ####"));
        ccvField = new JFormattedTextField(createFormatter("###"));
        expiryField = new JFormattedTextField(createFormatter("## / ##"));
    }

    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

}

