package Database;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import System.*;

/**
 * The UserDatabase class represents a database of registered customers. It uses a
 * hash map to store the email and password of each customer, and a text file to
 * persist the data between program runs.
 */

public class UserDatabase {

  // instance variable
  private Map<String, String> users;
  private File file; // to reference file
  private String fileName="UserDatabase.txt";// name of file

  //Creates a new EventDatabase object and initializes the events list by reading data from the file.
  public UserDatabase() {
    this.users= new HashMap<String, String>();
    this.file = new File(fileName);

    // read events from file
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(",");
        if (parts.length != 2) {
          System.out.println("Invalid input line: " + line);
          continue;
        }
        String email = parts[0].trim();
        String password = parts[1].trim();
        users.put(email,password);
      }
      scanner.close();
    } catch (IOException e) {
      System.out.println("An error occurred while reading from the file: " + e.getMessage());
    }


  }

    // add a customer to the database
    public void addUser(String email, String password) {


      if (!userExist(email)) {
        System.out.println("IN IF");
        users.put(email.toLowerCase().trim(), password.trim());
        updateDatabase();
      }
    }


  //Returns true if a customer with the given email is in the database, false otherwise.
    public boolean userExist(String email) {
    String user = email.toLowerCase();
    if (users.containsKey(user)) {
        return true;
    }
    return false;
  }

  //Removes a customer with the given email and password from the database. If the customer
  public void removeUser(String email, String password) {
    String user = email.toLowerCase().trim();
    if (users.containsKey(user)) {
      users.remove(user);
      updateDatabase();

    }
  }
  public void updateDatabase() {
    try {
      FileWriter writer = new FileWriter(fileName);
      for (String email : users.keySet()) {
        String password = users.get(email);
        writer.write(email + "," + password + "\n");
      }
      writer.close();
      System.out.println("User database saved to file.");
    } catch (IOException e) {
      System.out.println("Error writing user database to file.");
      e.printStackTrace();
    }
  }

  //Returns the hash map containing all the customers in the database.
  public Map<String, String> getUserDatabase() {
    return users;
  }




}


