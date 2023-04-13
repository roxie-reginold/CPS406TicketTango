package System;

import org.junit.Test;

import static org.junit.Assert.*;

public class TicketTest {
    Event addEvent1 = new Event("BTS", "Toronto", "2023-07-07", 1000, 250);
    Ticket t1 = new Ticket(addEvent1, addEvent1.getPrice(), "2023-04-02");


    /**
     * Checks if the event object from ticket equals the event.
     * @result The ticket’s event equals the event object
     */
    @Test
    public void getEvent() {
        assertEquals(addEvent1, t1.getEvent());
    }

    /**
     * Checks if the ticket’s event price is the same as the original ticket object’s price
     * @result The ticket’s event price equals the original ticket object’s price
     */
    @Test
    public void getPrice() {
        assertEquals(addEvent1.getPrice(), t1.getPrice(), 0.00001);
    }

    /**
     * Checks if the ticket’s event data is the same as the original ticket object’s purchase date
     * @result The ticket’s event data does not equals the original ticket object’s purchase date
     */
    @Test
    public void getDate() {
        assertNotEquals(addEvent1.getDate(), t1.getDate());
    }


    /**
     * Checks if the two different ticket object’s are the same despite having the same event
     * @result The two ticket objects are not equal
     */
    @Test
    public void testEquals() {
        Ticket t2 = new Ticket(addEvent1, addEvent1.getPrice(), "2024-04-02");
        assertFalse(t1.equals(t2));
    }
}