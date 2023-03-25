package System;

import java.util.*;

public class Order
{
	private int orderNumber; 
	private ArrayList<Ticket> tickets; // method to add tickets from cart
	
	public Order(int orderNumber, ArrayList<Ticket> tickets)
	{
		this.orderNumber = orderNumber;
		this.tickets  = tickets;	

	}
  
  // newOrder(---, Tickets)


	public int getOrderNumber() 
	{
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	public ArrayList<Ticket> getOrderedTickets()
	{
		return tickets;
	}
	public void setOrderedTickets(ArrayList<Ticket> tickets)
	{
		this.tickets = tickets;
	}

	
  
	/*
	 * Two Order objects are equal if they have the same order number string.
	 */
	public boolean equals(Object other)
	{
		// Compare two Order objects based on their orderNumber strings
		Order otherO = (Order) other;
		return this.orderNumber == otherO.orderNumber;
	}
  //public void print() //fix
	//{
		//System.out.printf("\nOrder # %3s Customer Id: %3s Product Id: %3s Product Name: %12s Options: %8s", orderNumber, customer.getId(), product.getId(), product.getName(), 
			//	               productOptions);
	//}
}