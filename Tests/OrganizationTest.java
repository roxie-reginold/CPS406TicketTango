package System;
import Database.EventDatabase;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * @author Kyle Galingan
 * @param eventTester: an arraylist of events.
 * @param testOrg: A new organization object.
 * @param testEvent: A new event object.
 *  */
public class OrganizationTest{
    ArrayList<Event> eventTester = new ArrayList<>();
    Organization testOrg = new Organization("test@email.com", "Password", "123 Address Rd.",
            "416-555-5555", eventTester);
    Event testEvent = new Event("Justin Bieber", "AIR CANADA CENTRE", "2025-02-02", 60, 60.0);

    /**
     * View the arraylist containing an organization's events.
     * @result When an event is added, the list size is increased. Event is added and gotten from the arraylist. 0->1
     * **/
    @Test
    public void viewEvents() {
        assertEquals(testOrg.viewEvents().size(), 0);
        testOrg.viewEvents().add(testEvent);
        assertEquals(testOrg.viewEvents().size(), 1);
        assertEquals(testOrg.viewEvents().get(0),testEvent);
    }
    /**
     * Events added are removed from the arraylist of events.
     * @result Event size is decreased after an event is removed. 1->0
     * **/
    @Test
    public void removeEvent() {
        testOrg.viewEvents().add(testEvent);
        assertEquals(testOrg.viewEvents().size(),1);
        testOrg.removeEvent(testEvent);
        assertEquals(testOrg.viewEvents().size(),0);
    }
    /**
     * Event is added to the database.
     * @result Database contains the event added, event 'exists'.
     * **/
    @Test
    public void addEvent() {
        EventDatabase testDB = new EventDatabase();
        assertFalse(testOrg.eventExists(testEvent));
        testOrg.addEvent(testEvent, testDB);
        assertTrue(testOrg.eventExists(testEvent));
    }
    /**
     * Gets the organization's address.
     * @result Address of organization set from initialization.
     * */
    @Test
    public void getAddress() {
        assertEquals(testOrg.getAddress(),"123 Address Rd.");
        assertNotEquals(testOrg.getAddress(), "1234 Address Rd.");
    }
    /**
     * Sets the organization's address.
     * @result Address of organization is set to new address set by method.
     * */
    @Test
    public void setAddress() {
        assertEquals(testOrg.getAddress(),"123 Address Rd.");
        testOrg.setAddress("1234 Address Rd.");
        assertEquals(testOrg.getAddress(),"1234 Address Rd.");
        assertNotEquals(testOrg.getAddress(),"123 Address Rd.");

    }
    /**
     * Gets the organization's phone number.
     * @result Address of organization set from initialization.
     * */
    @Test
    public void getPhoneNumber() {
        assertEquals(testOrg.getPhoneNumber(), "416-555-5555");
        assertNotEquals(testOrg.getPhoneNumber(), "123-555-4567");
    }
    /**
     * Sets the organization's phone number.
     * @result The organization's phone number is set to the new phone number set by method.
     * */
    @Test
    public void setPhoneNumber() {
        assertEquals(testOrg.getPhoneNumber(),"416-555-5555");
        testOrg.setPhoneNumber("123-555-456");
        assertEquals(testOrg.getPhoneNumber(),"123-555-456");
        assertNotEquals(testOrg.getPhoneNumber(),"416-555-5555");

    }

    /**
     * Event exists if it is in the arraylist of events.
     * @result Arraylist of event size is increased. 0->1.
     * */
    @Test
    public void eventExists() {
        assertEquals(testOrg.viewEvents().size(),0);
        assertFalse(testOrg.eventExists(testEvent));
        testOrg.viewEvents().add(testEvent);
        assertEquals(testOrg.viewEvents().size(),1);
        assertTrue(testOrg.eventExists(testEvent));

    }
}