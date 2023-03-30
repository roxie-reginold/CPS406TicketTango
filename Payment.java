import System.Event;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import System.*;

/*
GUI Containing payment processing. Includes boolean functions to verify certain
payment information fields are properly formatted.
Contains labels and formatted text fields to be inputted by the user.

*/
public class Payment{
    // Declare class variables
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
    private JButton btnConfirm;
    public JPanel paymentPanel;
    private JLabel lblTotal;
    public ArrayList<Event> events;
    private boolean flag = false;
    private Customer c1;

    // Constructor that initializes the Payment GUI with a customer and total cost
    public Payment(Customer c1 ,double total) {
        // Set the total cost label to the given value
        this.c1 = c1;
        lblTotal.setText(Double.toString(total));

        // Add action listener for cancel payment button
        cancelPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide current payment frame and show event page frame
                if (e.getSource() == cancelPaymentButton) {
                    JFrame startFrame3 = (JFrame) SwingUtilities.getWindowAncestor(cancelPaymentButton); // got from ChatGPT
                    startFrame3.setVisible(false);
                    JFrame startFrame = new JFrame("Events");
                    startFrame.setContentPane(new EventPage(c1).rootPanel);
                    startFrame.setPreferredSize(new Dimension(800, 700));
                    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    startFrame.pack();
                    startFrame.setLocationRelativeTo(null);
                    startFrame.setVisible(true);


                }
            }
        });


        // Add action listener for confirm payment button
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() ==  btnConfirm) {
                    String paymentName = nameField.getText();
                    String cardNum = cardField.getText();
                    String expDate = expiryField.getText();
                    String ccv = ccvField.getText();

                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String add1 = addressOneField.getText();
                    String add2 = addressTwoField.getText();
                    String city = cityField.getText();
                    String postalCode = postalField.getText();

                    // Check if all required fields are valid
                    if (isValidName(firstName) && isValidName(lastName) && isValidName(city) && isValidName(paymentName.trim())
                            && isValidAddress(add1.trim()) && isValidPostal(postalCode.trim()) && isValidCCV(ccv.trim()) && isRadioButtonSelected()) {

                        // Display success message
                        JOptionPane.showMessageDialog(frame, "Purchase was successful!");
                        // Hide current payment frame and show processing frame
                        JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(btnConfirm); // got from ChatGPT
                        startFrame2.setVisible(false);
                        JFrame startFrame = new JFrame("Event's Page");
                        startFrame.setContentPane(new Processing(c1).Ppanel);
                        startFrame.setPreferredSize(new Dimension(300, 200));
                        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        startFrame.pack();
                        startFrame.setLocationRelativeTo(null);
                        startFrame.setVisible(true);
                    }
                }
            }
        });

    }
    public  boolean isRadioButtonSelected() {
        return debitRadioButton.isSelected() ||creditRadioButton.isSelected();
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
            System.err.println("Bad formatting: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }
    // Following methods check for valid input values for Name,Card information,Address.
    private boolean isValidName(String name) {
        String named = "^[A-Za-z][A-Za-z ]*";
        return name.matches(named);
    }
    private boolean isValidCard(String card) {
        return card.length() ==16;
    }
    private boolean isValidCCV(String card) {
        return card.length() < 4;
    }
    private boolean isValidAddress(String adr) {
        String addRegex = "^[A-Za-z0-9.-][A-Za-z0-9 .-]*$";
        return adr.matches(addRegex);
    }
    private boolean isValidPostal(String pc) {
        String postalRegex = "^[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]$";
        return pc.matches(postalRegex);
    }

}

