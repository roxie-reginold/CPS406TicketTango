import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventInfo {
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
    private JButton backToEventsButton;
    private JTextField eventInfoText;
    private JFrame eventInfoFrame;

    public EventInfo(String name, String location, String date, String price, String eventInfo){

        //display text to the JTextFields
        eventNameText.setText("name");
        DateText.setText("date");
        priceText.setText("price");
        locationText.setText("location");
        eventInfoText.setText("eventInfo");


        backToEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == backToEventsButton){
//                    JFrame eventInfoFrame1 = (JFrame) SwingUtilities.getWindowAncestor(backToEventsButton);
//                    eventInfoFrame1.setVisible(false);


                    eventInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });

    }

    public EventInfo() {
        eventInfoFrame = new JFrame ("EventInfo");
        eventInfoFrame.setContentPane(new EventInfo().panel1);
        eventInfoFrame.setSize(600, 600);
        eventInfoFrame.pack();
        eventInfoFrame.setVisible(true);
    }

    public static void main(String[] args) {

//        JFrame eventInfoFrame = new JFrame ("EventInfo");
//        eventInfoFrame.setContentPane(new EventInfo().panel1);
//        eventInfoFrame.setSize(600, 600);
//        eventInfoFrame.pack();
//        eventInfoFrame.setVisible(true);
//        eventInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}