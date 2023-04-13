
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import System.*;
import System.Event;
import java.util.ArrayList;

/**
 * Represents the user's account information and displays it in a GUI panel.
 *
 * @author TicketTango
 * @version 1.0
 * @since 2023-04-02
 */
public class AccountInfo {
    private JTextField txtpassword;
    private JTable showTable;
    private JTextField txtusername;
    private JButton logoutButton;

    private JButton backToEventsButton;
    public JPanel Profile;
    private ArrayList<Order> OrderHistory =  new ArrayList<>();;

    /**
     * Initializes the AccountInfo object for a certain customer.
     *
     * @param c1 The Customer object that we will get the account info from.
     */
    public AccountInfo(Customer c1){
        OrderHistory= c1.getOrderHistory();
        txtusername.setText(c1.getEmail());
        txtpassword.setText("*".repeat(c1.getPassword().length()));
        createTable();


        logoutButton.addActionListener(new ActionListener() {
            @Override
            /**
             * This method is triggered when the user clicks in the logout button.
             * It opens a new JFrame window that displays a login screen.
             *
             * @param e An ActionEvent object that represents the user interaction.
             */
            public void actionPerformed(ActionEvent e) {
                JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(logoutButton); // got from ChatGPT
                startFrame2.setVisible(false);
                JFrame startFrame = new JFrame("Welcome!");
                startFrame.setContentPane(new Login().Login);
                startFrame.setPreferredSize(new Dimension(300, 400));
                startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startFrame.pack();
                Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                startFrame.setIconImage(icon);
                startFrame.setLocationRelativeTo(null);
                startFrame.setVisible(true);
            }
        });
        backToEventsButton.addActionListener(new ActionListener() {
            /**
             * This method is triggered when the user clicks in the back button.
             * It opens a new JFrame window that displays an Events Page.
             *
             * @param e An ActionEvent object that represents the user interaction.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(backToEventsButton); // got from ChatGPT
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
     * Creates a GUI table for events.
     *
     */
    private void createTable() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (Order order : OrderHistory) {
            for (Ticket ticket : order.getOrderedTickets()) {
                tickets.add(ticket);
            }
        }

        Object[][] data = new Object[tickets.size()][5];
        for (int i = 0; i < tickets.size(); i++) {
            Event event = tickets.get(i).getEvent();
            String date = tickets.get(i).getDate();
            data[i] = new Object[]{event.getName(), event.getLocation(), event.getDate(), event.getPrice(), date};
        }

        showTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Event Name", "Location", "Event Date", "Price", "Purchase Date"}
        ));
        TableColumnModel columns=showTable.getColumnModel();
        columns.getColumn( 0).setMinWidth (100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment (JLabel.CENTER);
        columns.getColumn( 1).setMinWidth (100);
        columns.getColumn( 2).setMinWidth (100);
        columns.getColumn (3).setCellRenderer(centerRenderer);
    }
}
