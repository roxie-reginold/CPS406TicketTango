package System;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Roxie Reginold
 */


public class CustomerTest {
    Customer cust1 = new Customer("cats@cats.ca", "rizz101");
    Cart cust1Cart = cust1.getCart();
    Event addEvent1 = new Event("BTS", "Toronto", "2023-07-07", 500, 250);
    Event addEvent2 = new Event("BTS", "Toronto", "2023-07-07", 500, 250);


    /**
     * Checks if customer’s cart is successfully updated after adding events to their cart.
     * @result The customer’s cart size should be updated to have both events in their cart.
     */
    @Test
    public void getCart() {

        cust1Cart.addToCart(addEvent1);
        cust1Cart.addToCart(addEvent2);
        cust1.setCart(cust1Cart);
        assertEquals(2,cust1.getCart().getCartSize());
    }

    /**
     * Checks if the order is successfully added to the customer’s order history.
     * @result The customer’s order history should have one order after adding the order to their order history
     */

    @Test
    public void getOrderHistory() {
        ArrayList<Order> oh = new ArrayList<>();
        ArrayList<Ticket> c1Tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket(addEvent1, addEvent1.getPrice(), "2023-04-02");
        Ticket ticket2 = new Ticket(addEvent2, addEvent2.getPrice(), "2023-04-02");
        c1Tickets.add(ticket1);
        c1Tickets.add(ticket2);
        Order orderC1 = new Order(101,c1Tickets);
        oh.add(orderC1);
        cust1.setOrderHistory(oh);
        assertEquals(1, cust1.getOrderHistory().size());

    }

    /**
     * Checks if a customer with an email that exists in the user database successfully creates an account.
     * @result The customer does not successfully create an account as the email already exists in the database
     */

    @Test
    public void createAccount() {
        assertFalse(cust1.createAccount());
    }
}