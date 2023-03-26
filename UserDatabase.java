package Database;
import System.*;

import java.util.*;



public class UserDatabase {
  private Map<String, String> customers;

  public UserDatabase(){
    this.customers = new HashMap<String, String>();

  }

  public boolean addCustomers(Customer user){
    String email = (user.getEmail()).toLowerCase();
    String password = user.getPassword();
    if (customers.containsKey(email)) {
      return false;
    }

    customers.put(email, password);
    return true;



  }



  public boolean customerExist( String email,String password ){
    String user= email.toLowerCase();

    if (customers.containsKey(user)) {
      if (customers.get(email).equals(password)){
        return true;
      }
    }
    return false;
  }




  public boolean removeCustomer(Customer user){
    String email =(user.getEmail()).toLowerCase();
    if (customers.containsKey(email)) {
      customers.remove(email);
      return true;
    } else {
      return false;
    }
  }

  public Map<String, String> print (){

    return customers;
  }


}

