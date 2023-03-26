import javax.swing.*;

public class Database {
    private JPanel panel1;
    private JLabel event_image;
    private JLabel name;
    private JLabel date;
    private JLabel location;
    private JLabel price;
    private JLabel eventInfo;
    private JTextField eventNameText;

    private JTextField DateText;
    private JTextField priceText;
    private JTextField locationText;
    private JButton addToCartButton;
    private JLabel addToCart;
    private JTextField eventInfoText;

    public static void main(String[] args) {
        JFrame eventInfoFrame = new JFrame ("EventInfo");

        eventInfoFrame.setContentPane(new Database().panel1);
        eventInfoFrame.setSize(600, 600);
        eventInfoFrame.pack();
        eventInfoFrame.setVisible(true);
        eventInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        eventNameText.setText("it works!");
    }
}