package System;

import Database.UserDatabase;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class UserTest {


    /**
     * Checks if the admin email equals the the expected result from the getEmail() method.
     * @result The expected email and the admin’s email are equal.
     */
    @Test
    public void getEmail() {
        Admin a1 = new Admin("roxie@ticket.tango.ca","admin101");
        assertEquals("roxie@ticket.tango.ca", a1.getEmail());
    }

    /**
     * Checks if the admin password equals the expected result from the getPassword() method.
     * @result The expected password and the admin’s password are equal
     */
    @Test
    public void getPassword() {
        Admin a1 = new Admin("roxie@ticket.tango.ca","admin101");
        assertEquals("admin101", a1.getPassword());
    }

    /**
     * Checks if the customers name equals the the expected result from the gerFirstName() method
     * @result The expected First Name and the customer’s First Name are equal
     */
    @Test
    public void getFirstName() {
        Customer c1 = new Customer("roxie@tmu.ca", "pass1234", "Roxie", "Reginold");
        assertSame("Roxie", c1.getFirstName());
    }

    /**
     * Checks if the user is able to login with their username and password
     * @result The user exists in the database and is able to login
     */
    @Test
    public void verifyLogin() {
        User usr = new User("roxie@tmu.ca", "Rainbow456", "Roxie", "Reginold");
        UserDatabase udb = new UserDatabase();
        assertTrue(usr.verifyLogin(udb));
    }
}