package System;

import java.util.ArrayList;

public class Cart {

  private ArrayList<Event> cartItems;
  public Cart(){
     
        this.cartItems = new ArrayList<Event>(); // create arraylist for a customer
    }

  // methods to add or remove items in list
    // public void addToCart(Ticket item){
    //   //
    //     cartItems.add(item);
    // }
   public boolean addToCart(Event item){
      if (item.getNumberofTickets() > 0){
        cartItems.add(item);
        return true;
      }
     return false;
     
    }


  public void removeFromCart(Event item){
        cartItems.remove(item);
    }


  public ArrayList<Event> getCartList (){
        return this.cartItems;
    } // get list of items when needed

  public int getCartSize (){
    return this.cartItems.size();
  }
  

  








  
}