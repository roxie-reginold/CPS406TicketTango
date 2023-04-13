package System;

import org.junit.Test;

import static org.junit.Assert.*;

public class CartTest {


    /**
     * Checks if the event is successfully added or removed from the customer’s cart.
     * @result The customer’s cart size should be updated after adding or removing events.
     */
    @Test
    public void getCartSize() {
        Customer c1 = new Customer();
        assertTrue(c1.getCart().getCartList().isEmpty());
        Event e1 = new Event("BTS", "Toronto", "2023-07-07", 200, 250);
        Cart cartC1 = c1.getCart();
        cartC1.addToCart(e1);
        assertEquals(1, c1.getCart().getCartSize());
        assertNotEquals(0, c1.getCart().getCartSize());
        cartC1.removeFromCart(e1);
        c1.setCart(cartC1);
        assertEquals(0, c1.getCart().getCartSize());
    }

    /**
     * Checks if the event is added to the customer’s cart with the event being sold out.
     * @result The event is not added to the customer’s cart because there are no tickets left
     */
    @Test
    public void addToCart() {
        Cart cart1 = new Cart();
        Event event1 = new Event("BTS", "Toronto", "2023-07-07", 0, 250);
        assertFalse(cart1.addToCart(event1));
    }
}