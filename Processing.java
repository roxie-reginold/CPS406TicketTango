import Database.EventDatabase;
import System.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import System.*;

/**
 * This Processing Class is responsible for processing the customers order and
 * updating the database to reflect customer's order
 */


public class Processing {
    public ArrayList<Event> events;
    private JButton btnContinue;
    public JPanel Ppanel;
    private Customer c1;
    public int orderNumber = 500;

    public Processing(Customer c1) {
        this.c1=c1;

        EventDatabase eventDatabase = new EventDatabase();
        ArrayList<Ticket> ticketList= new ArrayList<>();
        ArrayList<Event> events= c1.getCart().getCartList(); //get all cart items of the customer
        for (Event event : events) {//create a ticket object and updates the databases
            event.setNumberofTickets(event.getName(), event.getNumberofTickets() -1,eventDatabase );
            Ticket t = new Ticket(event, event.getPrice(),event.getDate());
            ticketList.add(t);
        }
        Order order = new Order(generateOrderNumber() , ticketList);//Makes an order
        ArrayList<Order> orders= c1.getOrderHistory();// add it to customer's order history
        c1.setOrderHistory(orders);
        c1.getCart().setCartItems(new ArrayList<>());//set cart to empty


        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(btnContinue); // got from ChatGPT
                startFrame2.setVisible(false);
                JFrame startFrame = new JFrame("Event's Page");
                startFrame.setContentPane(new EventPage(c1).rootPanel);
                startFrame.setPreferredSize(new Dimension(800, 700));
                startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startFrame.pack();
                startFrame.setLocationRelativeTo(null);
                startFrame.setVisible(true);

            }
        });
    }

    //generate a unique order number for each order
    private int generateOrderNumber()
    {
        return orderNumber++;
    }
}
