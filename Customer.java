package System;

//import Database.*;
import java.util.ArrayList;

public class Customer extends User{

  
  private Cart Cart;
  private String shippingAddress;
  //not sure if we need to add address
  private ArrayList<Order> OrderHistory; // change to arraylist of orders

  public Customer(String email, String password, String FirstName, String LastName, String address){

    super(email,password, FirstName, LastName);
    this.shippingAddress = address;
    this.Cart = new Cart();
    this.OrderHistory= new ArrayList<Order>();
  }

    public Customer(String email, String password, String FirstName, String LastName){

        super(email,password, FirstName, LastName);
        this.Cart = new Cart();
        this.OrderHistory= new ArrayList<Order>();
    }




  public String getShippingAddress()
	{
		return shippingAddress;
	}
	
	public void setShippingAddress(String shippingAddress)
	{
		this.shippingAddress = shippingAddress;
	}

  public Cart getCart()
	{
		return Cart;
	} //Cart cart = customer.getCart()
    //cart.addToCart();
    //customer.setCart(cart);
  

	public void setCart(Cart c)
	{
		this.Cart = c;
	}

    public void print()
    {
        System.out.printf("\nName: %-20s ", getFirstName());
    }
  

  public  ArrayList<Order> getOrderHistory(){
    return this.OrderHistory;
  }
  public  void setOrderHistory(ArrayList<Order> OrderHistory){
    this.OrderHistory = OrderHistory;
  }
  
  // public Ticket viewTicket(String email) {
  //   //for (Ticket ticket : OrderHistory){}
  //   return ;
  // }
  public String toString() {
      return getEmail() + "," + getPassword();
  }

  
  
}