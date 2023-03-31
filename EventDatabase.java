package Database;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import System.*;

/**
 * The EventDatabase class represents a database of events, stored in a text file.
 * It allows users to add, remove, and update events in the database.
 */

public class EventDatabase {

  // instance variables
  private ArrayList<Event> events; // to store events
  private File file; // to reference file
  private String fileName= "EventDatabase.txt"; // name of file

  public EventDatabase(){
    // initialize instance variables
    this.events = new ArrayList<Event>();
    this.file = new File(fileName);

    // read events from file
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        if (parts.length < 5) {
          System.out.println("Invalid input line: " + line);
          continue;
        }
        String name = parts[0].trim();
        String location = parts[1].trim();
        String date = parts[2].trim();
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
  }

  // add an event to the database
  public void addEvent(Event event) {

    // check if the event already exists in the database
    if (!eventExists(event)) {
      events.add(event);

    }
      // updates the database
      updateDatabase();
  }

  // remove an event from the database
  public void removeEvent(Event ev) {
    for (Event event :events ) {
      if (event.equals(ev)) {

        events.remove(event);
        updateDatabase();
        return;
      }
    }

  }

  // check if an event exists in the database
  public boolean eventExists(Event event) {
    for (Event e : events) {
      if (e.equals(event)) {
        return true;
      }
    }
    return false;
  }

  public void updateDatabase(){
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
      // loop through events and update the number of tickets for the specified event
      for (Event event : events) {
          writer.write(event.getName() + ", " +
                  event.getLocation() + ", " +
                  event.getDate() + ", " +
                  event.getNumberofTickets() + ", " +
                  event.getPrice());

        writer.newLine();
      }
      writer.close();
      System.out.println("Events written to " + fileName + " successfully.");
    } catch (IOException e) {
      System.out.println("Error writing events to file: " + e.getMessage());
    }
  }

  // update the number of tickets for a specific event in the database
  public void updateDatabase(String name, int num) {

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
      // loop through events and update the number of tickets for the specified event
      for (Event event : events) {
        if(name.equals(event.getName())){
          writer.write(event.getName() + ", " +
                  event.getLocation() + ", " +
                  event.getDate() + ", " +
                  num + ", " + //Change ticket availi
                  event.getPrice());
        } else {
          writer.write(event.getName() + ", " +
                  event.getLocation() + ", " +
                  event.getDate() + ", " +
                  event.getNumberofTickets() + ", " +
                  event.getPrice());
        }
        writer.newLine();
      }
      writer.close();
      System.out.println("Events written to " + fileName + " successfully.");
    } catch (IOException e) {
      System.out.println("Error writing events to file: " + e.getMessage());
    }
  }
  public ArrayList<Event> getEventDatabase() {
    return events;
  }
}
