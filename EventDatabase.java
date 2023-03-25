package Database;
import System.Event;

import java.util.ArrayList;

public class EventDatabase {
  private ArrayList<Event> events;

  public EventDatabase() {
    this.events = new ArrayList<Event>();
  }

  // public boolean addEvent(Event event) {
  // if (eventExists(event)) {
  // return false;
  // } else {
  // events.add(event);
  // return true;
  // }
  // }
  public void addEvent(Event event) {
        if (!eventExists(event)) {
            events.add(event);
        } 
    }

  // public boolean removeEvent(Event event) {
  // if (eventExists(event)) {
  // events.remove(event);
  // return true;
  // } else {
  // return false;
  // }
  // }
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
