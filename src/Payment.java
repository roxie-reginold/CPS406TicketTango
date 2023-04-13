import System.Event;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import System.*;

/**
 * GUI Containing payment processing. Includes boolean functions to verify certain
 * payment information fields are properly formatted.
 * Contains labels and formatted text fields to be inputted by the user.
 *
 * @author  TicketTango
 * @version 1.0
 * @since   2023-04-02
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
    private JLabel lblerror;
    public ArrayList<Event> events;
    private Customer c1;

    /**
     * Constructor that initializes the Payment GUI with a customer and total cost.
     *
     * @param c1 Customer making the payment.
     * @param total The cost of all the tickets plus tax.
     */
    public Payment(Customer c1 ,double total) {
        // Set the total cost label to the given value
        this.c1 = c1;
        lblTotal.setText(Double.toString(total));
        lblerror.setText("");

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
                    Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                    startFrame.setIconImage(icon);
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
                    String cardNum = cardField.getText().replaceAll("\\s+", "");
                    String expDate = expiryField.getText().replaceAll("\\s+", "");
                    String ccv = ccvField.getText();

                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String add1 = addressOneField.getText();
                    //String add2 = addressTwoField.getText();
                    String city = cityField.getText();
                    String province = provinceField.getSelectedItem().toString();
                    String postalCode = postalField.getText();

                    // Check if all required fields are valid
                    if (isValidName(firstName) && isValidName(lastName) && isValidName(city) && isValidName(paymentName.trim()) && isValidCard(cardNum.trim()) && isValidEXP(expDate.trim())
                            && isValidAddress(add1.trim()) && isValidPostal(postalCode.trim()) && isValidCCV(ccv.trim()) && isRadioButtonSelected() && isValidProvince(province)) {

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
                        Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                        startFrame.setIconImage(icon);
                        startFrame.setLocationRelativeTo(null);
                        startFrame.setVisible(true);
                    }else{
                        lblerror.setText("Invalid Payment information!");
                    }
                }
            }
        });

    }

    /**
     * Checks if the user clicks on an option.
     *
     * @return boolean True if either the debit or credit is selected, false otherwise.
     */
    public  boolean isRadioButtonSelected() {
        return debitRadioButton.isSelected() ||creditRadioButton.isSelected();
    }

    /**
     * This method creates the UI components.
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        postalField = new JFormattedTextField(createFormatter("U#U#U#"));
        cardField = new JFormattedTextField(cardFormatter("**** **** **** ****"));
        ccvField = new JFormattedTextField(createFormatter("###"));
        expiryField = new JFormattedTextField(numberFormatting("## / ##"));
//        expiryField = new JFormattedTextField(expFormat("MM/yy"));
        nameField = new JFormattedTextField(nameFormatter("******************************"));
        firstNameField = new JFormattedTextField(nameFormatter("*************"));
        lastNameField = new JFormattedTextField(nameFormatter("*************"));
    }

    /**
     * Creates MaskFormatter with provided String.
     *
     * @param s Provided String to create MaskFormatter.
     * @return formatter The MaskFormatter object created.
     */
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

    /**
     * Creates MaskFormatter for name inputs.
     *
     * @param s String to be formatted.
     * @return formatter The MaskFormatter used to format name.
     */
    protected MaskFormatter nameFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setValidCharacters("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ");
        } catch (java.text.ParseException exc) {
            System.err.println("Bad formatting: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    /**
     * Creates a MaskFormatter for credit/debit cards.
     *
     * @param s String representing the format.
     * @return formatter A MaskFormatter representing the formatted card.
     */
    protected MaskFormatter cardFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholder(" ");
            formatter.setValidCharacters("0123456789 ");
        } catch (java.text.ParseException exc) {
            System.err.println("Bad formatting: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    /**
     * Creates a MaskFormatter for numbers.
     *
     * @param s String used to create MaskFormatter.
     * @return formatter The MaskFormatter created.
     */
    protected MaskFormatter numberFormatting(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholder("_");
        } catch (java.text.ParseException exc) {
            System.err.println("Bad formatting: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    /**
     * Checks if the date given is in the future.
     *
     * @param s A date.
     * @return date A simple date format "MM/YY".
     */
    protected SimpleDateFormat expFormat(String s) {
        SimpleDateFormat date = new SimpleDateFormat("MM/yy");
        date.setLenient(false);
        try {
            Date expiry = date.parse(s);
            Calendar cal = Calendar.getInstance();
            cal.setTime(expiry);
            int year = cal.get(Calendar.YEAR);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (year >= currentYear) {
                System.out.println("Valid expiry date");
            } else {
                System.out.println("Invalid expiry date");
            }
        } catch (java.text.ParseException exc) {
            System.out.println("Invalid date: " + exc.getMessage());
        }
        return date;
    }


    /**
     * This method check for valid input values for Name.
     *
     * @param name The name to be checked.
     * @return boolean True if format valid, false otherwise.
     */
    private boolean isValidName(String name) {
        String named = "^[A-Za-z][A-Za-z ]*";
        return name.matches(named);
    }

    /**
     * This method checks for valid card input.
     *
     * @param card The card number being checked.
     * @return boolean True if valid, false otherwise.
     */
    private boolean isValidCard(String card) {
        return card.length() == 16;
    }

    /**
     * Checks if card verification is valid.
     *
     * @param card The card code verification to be checked.
     * @return boolean True if valid, false otherwise.
     */
    private boolean isValidCCV(String card) {
        return card.length() == 3;

    }

    /**
     * Checks if card expiration date is in valid format.
     *
     * @param card The expiration date to be checked.
     * @return boolean True if valid, false otherwise.
     */
    private boolean isValidEXP(String card) {

        return card.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    /**
     * Checks if address is valid.
     *
     * @param adr The address to be checked.
     * @return boolean True if form valid, false otherwise.
     */
    private boolean isValidAddress(String adr) {
        String addRegex = "^[A-Za-z0-9.-][A-Za-z0-9 .-]*$";
        return adr.matches(addRegex);
    }

    /**
     * Checks if postal code is valid.
     *
     * @param pc The postal code being checked.
     * @return boolean True if format is valid, false otherwise.
     */
    private boolean isValidPostal(String pc) {
        String postalRegex = "^[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]$";
        return pc.matches(postalRegex);
    }

    /**
     * Checks for valid province.
     *
     * @param pr Province being checked.
     * @return boolean True if it is a province, false otherwise.
     */
    private boolean isValidProvince(String pr) {
        return pr.equals("NL")  || pr.equals("PE") ||
                pr.equals("NS") || pr.equals("NB") || pr.equals("QC") || pr.equals("ON") || pr.equals("MB") ||
                pr.equals("SK") || pr.equals("AB") || pr.equals("BC") || pr.equals("YT") || pr.equals("NT")
                ||pr.equals("NU");
    }

}

