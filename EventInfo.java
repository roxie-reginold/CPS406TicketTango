import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
GUI containing further EventInfo. Event information such as name, date, location, price,
and general information are included. Event info displays further information that is
stored in the database. The GUI is activated when an event is selected.
*/
public class EventInfo {
    public JPanel panel1;
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

    public EventInfo(String name, String location, String date, String ticketLimit, String price){

        //display text to the JTextFields
        eventNameText.setText(name);
        DateText.setText(date);
        priceText.setText(price);
        locationText.setText(location);
        eventInfoText.setText("Only" + ticketLimit + "tickets are left! Get yours before it is sold out!");

        // Adding action listener to the back button
        backToEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource() == backToEventsButton){
                    // Getting the eventInfoFrame and disposing it
                    JFrame eventInfoFrame1 = (JFrame) SwingUtilities.getWindowAncestor(backToEventsButton);
                    eventInfoFrame1.dispose();


                }
            }
        });

    }


}