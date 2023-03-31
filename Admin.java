package System;
import Database.*;

/*
This Java class called "Admin" represents an administrator of the system who has access to various functions
such as checking if a customer, event, or order exists.
The class inherits properties from the User class and includes methods to compare information with the user and event databases.

The Admin class is designed to manage customers, events, and orders.
The class verifies the existence of these entities within the system.
Additionally, the class integrates with existing databases to provide access to necessary information.

The Admin class maintains the integrity and security of the system,
ensuring that only authorized individuals have access to sensitive information.
This can streamline the overall process of managing customers, events,
and orders and simplify the task of verifying their existence.
 */

//another actor of our system  (not implemented in our GUI)
public class Admin extends User {
  UserDatabase DBUser;
  EventDatabase DBEvent;

  public Admin(String email, String password ) {
    // constructor of Admin which inherits properties of a User
    super(email, password);
    DBUser= new UserDatabase();
    DBEvent = new EventDatabase();

  }


  public void removeACustomer(Customer customer){
    String emailCust = customer.getEmail();
    String passCust = customer.getPassword();
    DBUser.removeUser(emailCust,passCust);
  }
  //the admin is able to add an Event to the database
  public void addAEvent(Event event){
    DBEvent.addEvent(event);
  }
  // the admin is able to remove an Event from the database
  public void removeAEvent(Event event){
    DBEvent.removeEvent(event);
  }


  public boolean customerExists(Customer customer, UserDatabase userDB) {
    // compare information from database
    String email = customer.getEmail();
    String password = customer.getPassword();
    return userDB.userExist(email);
  }

  public boolean eventExists(Event event, EventDatabase DBEvent) {
    // compare information from database
    return DBEvent.eventExists(event);
  }

  // check is Customer's order exists
  public boolean orderExists(Order obj, Customer customer) {
    for (Order order : customer.getOrderHistory()) {
      if (order.equals(obj)) {
        return true;
      }
    }
    return false;
  }



}