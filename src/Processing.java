import Database.EventDatabase;
import System.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import System.*;

/**
 * This Processing Class is responsible for processing the customers order and
 * updating the database to reflect customer's order
 *
 * @author  TicketTango
 * @version 1.0
 * @since   2023-04-02
 */
public class Processing {
    public ArrayList<Event> events;
    private JButton btnContinue;
    public JPanel Ppanel;
    private Customer c1;
    public int orderNumber = 500;

    /**
     * Represents the process of creating an order.
     *
     * @param c1 The customer who is making the order.
     */
    public Processing(Customer c1) {
        this.c1=c1;

        EventDatabase eventDatabase = new EventDatabase();
        ArrayList<Ticket> ticketList= new ArrayList<>();
        ArrayList<Event> events= c1.getCart().getCartList(); //get all cart items of the customer
        int val =1;

        ArrayList<Event> exist = new ArrayList<>();
        for (Event event : events){
            for (Event e : exist) {
                if (e.getName().equals(event.getName())) {
                    val+=1;//multiple tickets for the same event

                }
            }
            exist.add(event);
            event.setNumberofTickets(event, event.getNumberofTickets() -val,eventDatabase);
            String date = LocalDate.now().toString();
            Ticket t = new Ticket(event, event.getPrice(), date);
            ticketList.add(t);
            val = 1;

        }

        Order order = new Order(generateOrderNumber() , ticketList);//Makes an order
        ArrayList<Order> orders= c1.getOrderHistory();
        orders.add(order);// add it to customer's order history
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
                Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                startFrame.setIconImage(icon);
                startFrame.setLocationRelativeTo(null);
                startFrame.setVisible(true);

            }
        });
    }

    /**
     * Generates a unique order number for each order
     *
     * @return orderNumber Creates an order number different from others.
     */
    private int generateOrderNumber()
    {
        return orderNumber++;
    }
}
