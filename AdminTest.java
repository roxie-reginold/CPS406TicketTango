package System;

import Database.*;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * AdminTest tests for the Admin class, which handles customers and events
 *
 */
public class AdminTest {
    Admin a1 = new Admin("roxie@ticket.tango.ca", "admin41");
    Customer c1 = new Customer("rr7972@gmail.com", "roller002");
    Event e1 = new Event("Rihanna", "New York", "2024-12-12", 1000, 200);

    /**
     * Checks if customer is added successfully from the database by the admin.
     * @result The Customer's email and password is added to the User Database
     */
    @Test
    public void addCustomer() {
        a1.addCustomer(c1);
        User user= c1;
        assertTrue(a1.userExist(user));

    }

    /**
     * Checks if customer is removed successfully from the database by the admin.
     * @result The Customer's email and password will no longer exist in the User Database
     */
    @Test
    public void removeCustomer() {
        a1.removeCustomer(c1);
        User user= c1;
        assertFalse(a1.userExist(user));
    }

    /**
     * Checks if admin successfully adds an event to the event database
     * @result The event details exist in the event database
     */
    @Test
    public void addEvent() {
        a1.addEvent(e1);
        assertTrue(a1.eventExist(e1));
    }

    /**
     * Checks if admin succesfully removes an event from the event database
     * @result Event details no longer exists in the event database
     */
    @Test
    public void removeEvent() {
        a1.removeEvent(e1);
        assertFalse(a1.eventExist(e1));
    }

    /**
     * Checks if customer's order is successfully canceled and removed from customer's order history
     * @result The customer has the order cancels and order does not exist in Customer's order history
     */
    @Test
    public void cancelOrder() {
        ArrayList<Order> oh = new ArrayList<>();
        ArrayList<Ticket> c1Tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket(e1, e1.getPrice(), "2023-04-02");
        c1Tickets.add(ticket1);
        Order orderC1 = new Order(101,c1Tickets);
        oh.add(orderC1);
        c1.setOrderHistory(oh);
        a1.cancelOrder(orderC1, c1);
        assertEquals(0, c1.getOrderHistory().size());

    }
}