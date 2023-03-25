package System;

import java.util.Calendar;
import java.util.Date;

class Ticket {
  private String TicketNumber;
  private Event EventDetails;
  private double Price;
  private String Date;
  private String Seat;

  public Ticket(){}
  public Ticket(String ticketNumber, Event eventDetails, double price, String date, String seat) {
    this.TicketNumber=ticketNumber;
    this.EventDetails=eventDetails;
    this.Price=Math.round(price * 100.0) / 100.0;
    this.Date=date;
    this.Seat=seat;
  }
  //get
  public String getTicketNumber(){
    return TicketNumber;
  }
  public Event getEventDetails(){
    return EventDetails;
  }
  public double getPrice(){
    return Price;
  }
  public String getDate(){
    return Date;
  }
  public String getSeat(){
    return Seat;
  }

  //set
  public void setPrice(double price) {
    this.Price=price;
  }
  public void setDate(int month, int day, int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MONTH, month-1);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.YEAR, year);
    Date date = calendar.getTime();
    this.Date = date.toString();
  }
  






  
}