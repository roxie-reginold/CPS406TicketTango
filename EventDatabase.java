package Database;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import System.*;

public class EventDatabase {
  private ArrayList<Event> events;
  private File file;

  public EventDatabase() {
    this.events = new ArrayList<Event>();
    this.file = new File("EventDatabase.txt");
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
  }

  public void addEvent(Event event) {
    if (!eventExists(event)) {
      events.add(event);
      try {
        FileWriter writer = new FileWriter(file, true);
        writer.write(event.getName() + "," + event.getLocation() + "," + event.getDate() + "," + event.getNumberofTickets() + "," + event.getPrice() + "\n");
        writer.close();
      } catch (IOException e) {
        System.out.println("An error occurred while writing to the file: " + e.getMessage());
      }
    }
  }

  public void removeEvent(Event event) {
    if (eventExists(event)) {
      events.remove(event);
    }
  }

  public boolean eventExists(Event event) {
    for (Event e : events) {
      if (e.equals(event)) {
        return true;
      }
    }
    return false;
  }
}