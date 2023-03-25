package System;
import Database.*;
//return userDB.customerExist(customer);
public class Admin extends User {



  public Admin(String email, String password,String First, String Last) {

    super(email, password, First, Last);
  }


  public boolean customerExists(Customer customer, UserDatabase userDB) {
    // compare information from database
    String email = customer.getEmail();
    String password = customer.getPassword();
    return userDB.customerExist(email, password);
  }

  public boolean eventExists(Event event, EventDatabase DBEvent) {
    // compare information from database
    return DBEvent.eventExists(event);
  }

  public boolean orderExists(Order obj, Customer customer) {
    for (Order order : customer.getOrderHistory()) {
      if (order.equals(obj)) {
        return true;
      }
    }
    return false;
  }



}