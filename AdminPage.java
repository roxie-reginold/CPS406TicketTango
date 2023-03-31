
import Database.EventDatabase;
import System.*;
import System.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
//    private JTable cartTable;
//    private JButton btnPay;
    private JButton btnViewEvent;
    private JPanel Table;
    private JPanel Input;
    private JTextField txtName;
    private JTextField txtLocation;
    private JTextField txtDate;
    private JTextField txtTicket;
    private JTextField txtPrice;
    private JButton addEventToDatabaseButton;


    public ArrayList<Event> events=new ArrayList<>();

    private double total;
    private Admin a1;
    public EventDatabase eventDatabase;
    public boolean flag = false;


    public AdminPage(Admin a1) {
        eventDatabase = new EventDatabase();
        events= eventDatabase.getEventDatabase();
        this.a1=a1;
        showTable.setDefaultEditor(Object.class, null);// Disables editing of table cells

        createTable();


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
                super.mouseClicked(e);
                flag = true;
                int index = showTable.getSelectedRow();
                TableModel model = showTable.getModel();
                String name  =model.getValueAt(index,0).toString().trim();

                txtshow.setText(name);
            }
        });
        addEventToDatabaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = txtName.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String location = txtLocation.getText().toString().trim();
                String price = txtPrice.getText().toString().trim();
                String ticket = txtTicket.getText().toString().trim();
                Event event = new Event(name, location,date,Integer.parseInt(ticket),Double.parseDouble(price));
                eventDatabase.addEvent(event);
                createTable();
            }
        });
        btnRemove.addMouseListener(new MouseAdapter() {
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
                    eventDatabase.removeEvent(event);
                    createTable();
                    flag = false;

                }
            }
        });
    }

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
