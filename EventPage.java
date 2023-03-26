
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EventPage {

    private String path="I:\\My Drive\\CPS209\\Java Programs\\406\\src\\Database\\EventDataBase.txt";
    public JPanel rootPanel;
    private JPanel Table;
    private JPanel Title;
    private JPanel Input;
    private JButton btnAdd;
    private JTable showTable;
    private JTextField txtshow;
    private JButton myCartButton;
    private Event event;
    private JFrame frame;

    public EventPage(){
         //this.frame= new JFrame("Search Event");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setContentPane(new EventPage().rootPanel);
        //frame.pack();
        //frame.setSize(600, 400);
        //frame.setLocationRelativeTo(null);//center UI
        //frame.setVisible(true);


        showTable.setDefaultEditor(Object.class, null);
       createTable();

        showTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = showTable.getSelectedRow();
                TableModel model = showTable.getModel();
                String value1 =model.getValueAt(index,0).toString().trim();
                String value2 =model.getValueAt(index,1).toString().trim();
                String value3 =(model.getValueAt(index,2).toString()).trim();
                //Number values
                String value4 =model.getValueAt(index,3).toString().trim();
                int tickets = Integer.parseInt(value4);
                String value5 =model.getValueAt(index,4).toString().trim();
                double price= Double.parseDouble(value5);

                txtshow.setText(value1);

                event = new Event(value1,value2,value3,tickets,price); //Send this object


            }
        });

        TicketPurchasingSystem btnAddListener = new TicketPurchasingSystem(btnAdd, event);
        btnAdd.addActionListener(btnAddListener);

        myCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame startFrame2 = (JFrame) SwingUtilities.getWindowAncestor(myCartButton); // got from ChatGPT
                startFrame2.setVisible(false);
                JFrame startFrame = new JFrame("Home");
                startFrame.setContentPane(new MyCart().Cart);
                startFrame.setPreferredSize(new Dimension(800, 700));
                startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startFrame.pack();
                startFrame.setVisible(true);

            }
        });
    }

    private void createTable() {
        ArrayList<Event> events = getData();
        Object[][] data = new Object[events.size()][5];
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            data[i] = new Object[]{event.getName(), event.getLocation(), event.getDate(), event.getNumberofTickets(), event.getPrice()};
        }

        showTable.setModel(new DefaultTableModel(
                data,
                new String[]{"Event Name", "Location", "Date",  "Tickets", "Price",}
        ));
        TableColumnModel columns=showTable.getColumnModel();
        columns.getColumn( 0).setMinWidth (100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment (JLabel.CENTER);
        columns.getColumn( 1).setMinWidth (100);
//        columns.getColumn (1).setCellRenderer(centerRenderer);
//        columns.getColumn (2).setCellRenderer(centerRenderer);
        columns.getColumn( 2).setMinWidth (100);
        columns.getColumn (3).setCellRenderer(centerRenderer);



    }
    public ArrayList<Event> getData() {
        ArrayList<Event> events = new ArrayList<Event>();
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0];
                String location = parts[1];
                String date = parts[2];
                String numberOfTicketsStr = parts[3].trim();
                int numberOfTickets = Integer.parseInt(numberOfTicketsStr);
                String priceStr = parts[4].trim();
                double price = Double.parseDouble(priceStr);
                Event event = new Event(name, location, date, numberOfTickets, price);
                events.add(event);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }
        return events;
    }










    //public static void main(String[] args) {
   //     JFrame frame = new JFrame("Search Event");
   //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   //     frame.setContentPane(new EventPage().rootPanel);
   //     frame.pack();
   //     frame.setSize(600, 400);
    //    frame.setLocationRelativeTo(null);//center UI
   //     frame.setVisible(true);

    //}


}
