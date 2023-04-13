package System;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * @author Kyle Galingan
 * @param tickets: a new arraylist containing tickets used to hold an order.
 * @param testOrder: a new order created with order number 123 and arraylist tickets.
 * @param testOrder2: a new order created with order number 134 and arraylist tickets.
 *  */
public class OrderTest {
    ArrayList<Ticket> tickets = new ArrayList<>();
    Order testOrder = new Order(123, tickets);
    Order testOrder2 = new Order(134, tickets);
    /**
     * Gets the order's order number.
     * @result Same order number as testOrder initialization.
     * **/
    @Test
    public void getOrderNumber() {
        assertEquals(testOrder.getOrderNumber(),123);
        assertNotEquals(testOrder.getOrderNumber(),321);
    }
    /**
     * Sets the order's order number.
     * @result initialized from method.Order number is set to new ordernumber by method.
     * **/
    @Test
    public void setOrderNumber() {
        assertEquals(testOrder.getOrderNumber(),123);
        testOrder.setOrderNumber(321);
        assertEquals(testOrder.getOrderNumber(),321);
    }
    /**
     * Gets the order's Ordered Tickets.
     * @result Size of ordered tickets arraylist is increased by 1 when an event is added.
     * **/
    @Test
    public void getOrderedTickets() {
        Event testEvent = new Event("The Weeknd", "Canada", "2025-02-02", 60, 60.0);
        Ticket testTicket = new Ticket(testEvent, 190.00, "2025-04-29");
        assertEquals(testOrder.getOrderedTickets().size(),0);
        testOrder.getOrderedTickets().add(testTicket);
        assertEquals(testOrder.getOrderedTickets().size(),1);
    }
    /**
     * Sets the order's Ordered Tickets.
     * @result The ordered tickets arraylist of testOrder is the same as the newly initialized testTickets arraylist.
     * **/
    @Test
    public void setOrderedTickets() {
        ArrayList<Ticket> testTickets = new ArrayList<>();
        Event testEvent = new Event("The Weeknd", "Canada", "2025-02-02", 60, 60.0);
        Ticket testTicket = new Ticket(testEvent, 190.00, "2025-04-29");
        testTickets.add(testTicket);
        testOrder.setOrderedTickets(testTickets);
        assertEquals(testOrder.getOrderedTickets(),testTickets);
    }
    /**
     * Compares two Order objects
     * @result Two different events are NOT equal.
     * **/
    @Test
    public void testEquals() {
        assertNotEquals(testOrder, testOrder2);
    }
}