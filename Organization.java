package System;

import java.util.*;

public class Organization extends User {
  
  private String UID;
  private String phoneNumber;
  private String address;
  private ArrayList<Event> events;
  

  public Organization(String UID, String phoneNumber, String address, String email, String password){
    super(email, password);
    this.UID= UID;
    this.phoneNumber = phoneNumber;
    this.address = address;
    
    
} 






//registerSystem, Login, View events, purchasedTickets, 
  public ArrayList<Event> viewEvents(){

    return events;
  }


}
