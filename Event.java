package System;

import java.util.Date;
//Manage tickets
public class Event{
  private String name;
  private String location;
  private String date;
  private int numberofTickets;
  private double price;

  public Event(String name, String location, String date, int numberofTickets, double price) {
    this.name = name;
    this.location = location;
    this.date = date;
    this.numberofTickets = numberofTickets;
    this.price = price;
  }

  public String getEventDetails(){
    return ("Name; " + name + " Location: " + location + " Date: " + date);
  }
  
  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public String getDate() {
    return date;
  }

  public int getNumberofTickets() {
    return numberofTickets;
  }

  public double getPrice() {
    return price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setNumberofTickets(int numberofTickets) {
    this.numberofTickets = numberofTickets;//looked
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean equals(Object other)
	{
		Event otherE = (Event) other;
		return this.name.equals(otherE.name);
	}

	//public int compareTo(Event otherE)
	//{
	//	return this.name.compareTo(otherE.name);
	//}
}
