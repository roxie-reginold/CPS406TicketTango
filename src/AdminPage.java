import System.*;
import System.Event;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * GUI for Admin page.
 *
 * @author  TicketTango
 * @version 1.0
 * @since   2023-04-02
 */
public class AdminPage {
    // Declare variables and objects
    private final String path= "EventDatabase.txt";
    public JPanel rootPanel;
    private JPanel nameTable;
    private JPanel Title;
    private JPanel meInput;
    private JButton btnRemove;
    private JTable showTable;
    private JTextField txtshow;
    private JButton btnViewEvent;
    private JPanel Table;
    private JPanel Input;
    private JTextField txtName;
    private JTextField txtLocation;
    private JTextField txtDate;
    private JTextField txtTicket;
    private JTextField txtPrice;
    private JButton addEventToDatabaseButton;
    private JButton logoutButton;

    public ArrayList<Event> events;

    private double total;
    private Admin a1;
    public boolean flag = false;

    /**
     * Admin page of Ticket Tango.
     *
     * @param a1 An instance of the Admin class.
     */
    public AdminPage(Admin a1) {

        this.a1=a1;
        events= this.a1.getEventDatabase();
        showTable.setDefaultEditor(Object.class, null);// Disables editing of table cells

        createTable();

        //this mouse Listener is for when the admin is viewing event info
        btnViewEvent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (flag) {
                    // Get the selected row from the showTable
                    int index = showTable.getSelectedRow();
                    TableModel model = showTable.getModel();
                    // Get the values of the selected row
                    String value1 = model.getValueAt(index, 0).toString().trim();
                    String value2 = model.getValueAt(index, 1).toString().trim();
                    String value3 = model.getValueAt(index, 2).toString().trim();
                    String value4 = model.getValueAt(index, 3).toString().trim();
                    String value5 = model.getValueAt(index, 4).toString().trim();

                    // Display the event details in a new window
                    JFrame startFrame = new JFrame("Home");
                    startFrame.setContentPane(new EventInfo(value1, value2, value3, value4, value5).panel1);
                    startFrame.setPreferredSize(new Dimension(800, 700));
                    startFrame.pack();
                    Image icon = Toolkit.getDefaultToolkit().getImage("ticketTango.png");
                    startFrame.setIconImage(icon);
                    startFrame.setVisible(true);
                }else{
                    // Display an error message if no event is selected
                    txtshow.setText("Please Select an Event");
                }

            }
        });
        showTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // this mouse listener is to display the table
                super.mouseClicked(e);
                flag = true;
                int index = showTable.getSelectedRow();
                TableModel model = showTable.getModel();
                String name  =model.getValueAt(index,0).toString().trim();

                txtshow.setText(name);
            }
        });
        addEventToDatabaseButton.addMouseListener(new MouseAdapter() {
            /**
             * This mouse Listener is for when the admin clicks on remove events
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = txtName.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String location = txtLocation.getText().toString().trim();
                String price = txtPrice.getText().toString().trim();
                String ticket = txtTicket.getText().toString().trim();
                Event event = new Event(name, location,date,Integer.parseInt(ticket),Double.parseDouble(price));
                a1.addEvent(event);
                createTable();
            }
        });
        btnRemove.addMouseListener(new MouseAdapter() {
            /**
             * This mouse listener is for when the admin clicks remove event.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (flag){
                    int index = showTable.getSelectedRow();
                    TableModel model = showTable.getModel();
                    // Get the values of the selected row
                    String name = model.getValueAt(index, 0).toString().trim();
                    String location = model.getValueAt(index, 1).toString().trim();
                    String date = model.getValueAt(index, 2).toString().trim();
                    String ticket= model.getValueAt(index, 3).toString().trim();
                    String price = model.getValueAt(index, 4).toString().trim();
                    Event event = new Event(name, location,date,Integer.parseInt(ticket),Double.parseDouble(price));
                    a1.removeEvent(event);
                    createTable();
                    flag = false;

                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            /**
             * This is action listener for when the logout button is finished.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(logoutButton); // got from ChatGPT
                startFrame2.setVisible(false);
                JFrame startFrame = new JFrame("Welcome!");
                startFrame.setContentPane(new AdminLogin().adminLogin);
                startFrame.setPreferredSize(new Dimension(300, 400));
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
     * Creates a table that displays events.
     *
     */
    private void createTable() {
        Object[][] data = new Object[events.size()][5];
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            data[i] = new Object[]{event.getName(), event.getLocation(), event.getDate(), event.getNumberofTickets(), event.getPrice()};
        }

        showTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Event Name", "Location", "Date",  "Tickets", "Price"}
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
