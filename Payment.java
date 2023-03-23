import javax.swing.*;
import javax.swing.text.MaskFormatter;


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

