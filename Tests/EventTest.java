package System;

import Database.EventDatabase;
import org.junit.Test;
import static org.junit.Assert.*;

    /**
     * @author Kyle Galingan
     * @param testEvent: A new Event object
     * @param testEvent2 = A new Event Object
     *  */

public class EventTest {
    Event testEvent = new Event("Event Name", "Event Location","2024-01-15", 900, 160.56);
    Event testEvent2 = new Event("Test Event", "Location", "2016-03-16", 500,120.29);



    /**
     * Checks Event details and displays its parameters.
     * @result The event's Name, Event location, date, number of tickets, and price
     *
     */
    @Test
    public void getEventDetails() {
        assertEquals("Name; Event Name Location: Event Location Date: 2024-01-15", "Name; " + testEvent.getName() +
                " Location: " + testEvent.getLocation() + " Date: " + testEvent.getDate());
        assertEquals("Name; Test Event Location: Location Date: 2016-03-16", "Name; " + testEvent2.getName() +
                " Location: " + testEvent2.getLocation() + " Date: " + testEvent2.getDate());
    }

    /**
     * Checks the events name
     * @result The event's name is equal to the name initialized
     */
    @Test
    public void getName() {
        assertEquals(testEvent.getName(), "Event Name");
        assertNotEquals("Taylor Swift", testEvent.getName());
        assertEquals(testEvent2.getName(), "Test Event");
        assertNotEquals("Event Name", testEvent2.getName());
    }
    /**
     * Checks the event's Location
     * @result The event's location is equal to the location intialized
     * **/
    @Test
    public void getLocation() {
        assertEquals(testEvent.getLocation(), "Event Location");
        assertNotEquals("thislocation", testEvent.getLocation());
        assertEquals(testEvent2.getLocation(), "Location");
        assertNotEquals("eaoifkjaszl", testEvent.getLocation());
    }
        /**
         * Checks the dateLocation
         * @result The event's date is equal to the date intialized
         * **/
    @Test
    public void getDate() {
        assertEquals(testEvent.getDate(), "2024-01-15");
        assertNotEquals("2016-05-16", testEvent.getDate());
        assertEquals(testEvent2.getDate(), "2016-03-16");
        assertNotEquals("16-03-2006", testEvent.getDate());
    }

        /**
         * Checks the event's price
         * @result The event's price is equal to the price initialized
         * */
    @Test
    public void getPrice() {
        assertEquals(testEvent.getPrice(), 160.56, 0.001);
        assertNotEquals(16.3, testEvent.getPrice());
        assertEquals(testEvent2.getPrice(), 120.29,0.001);
        assertNotEquals(15948, testEvent.getPrice());
    }
        /**
         * Sets the event's name.
         * @result Event name is updated with name set by method.
         * **/
    @Test
    public void setName() {
        Event nameEvent = new Event(this.testEvent.getName(),this.testEvent.getLocation(),this.testEvent.getDate(),
                this.testEvent.getNumberofTickets(), this.testEvent.getPrice());
        assertEquals(nameEvent.getName(), this.testEvent.getName());
        nameEvent.setName("New Name");
        assertNotEquals(nameEvent.getName(),this.testEvent.getName());
    }
    /**
     * Sets the event's location
     * @result Event location is updated with location set by method.
     * **/
    @Test
    public void setLocation() {
        Event locationEvent = new Event(this.testEvent.getName(),this.testEvent.getLocation(),this.testEvent.getDate(),
                this.testEvent.getNumberofTickets(), this.testEvent.getPrice());
        assertEquals(locationEvent.getLocation(), this.testEvent.getLocation());
        locationEvent.setLocation("New Location");
        assertNotEquals(locationEvent.getLocation(),this.testEvent.getLocation());
    }

    /**
     * Sets the event's date.
     * @result Event date is updated with the date set by method.
     * **/
    @Test
    public void setDate() {
        Event dateEvent = new Event(this.testEvent.getName(),this.testEvent.getLocation(),this.testEvent.getDate(),
                this.testEvent.getNumberofTickets(), this.testEvent.getPrice());
        assertEquals(dateEvent.getDate(), this.testEvent.getDate());
        dateEvent.setDate("2023-04-13");
        assertNotEquals(dateEvent.getDate(),this.testEvent.getDate());
    }
    /**
     * Gets the event's number of tickets.
     * @return Event number of tickets is equal to number of tickets set by initialization.
     * **/
    @Test
    public void getNumberofTickets() {
        assertEquals(testEvent.getNumberofTickets(), 900);
        assertNotEquals(90.0, testEvent.getNumberofTickets());
        assertEquals(testEvent2.getNumberofTickets(), 500);
        assertNotEquals(50000, testEvent.getNumberofTickets());
    }
    /**
     * Sets the event's number of tickets.
     * @return Event number of tickets is updated with number of tickets set by method.
     * **/
    @Test
    public void setNumberofTickets() {
        Event ticketEvent = new Event(this.testEvent.getName(),this.testEvent.getLocation(),this.testEvent.getDate(),
                this.testEvent.getNumberofTickets(), this.testEvent.getPrice());
        assertEquals(ticketEvent.getNumberofTickets(), this.testEvent.getNumberofTickets());
        ticketEvent.setNumberofTickets(ticketEvent, 200,new EventDatabase());
        assertNotEquals(ticketEvent.getNumberofTickets(),this.testEvent.getNumberofTickets());
    }
}