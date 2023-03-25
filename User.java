package System;

import Database.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String FirstName;
    private String LastName;
    private String email;
    private String password;

    public User(String email, String password,String FirstName, String LastName) {
    
        this.email = email;
        this.password = password;
        this.FirstName =FirstName;
        this.LastName = LastName;
    }

    public User(String email, String password) {

        this.email = email;
        this.password = password;
    }
  
    // Validate email using regular expression
    
    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+-]+(?:\\." +
                         "[a-zA-Z0-9_+-]+)*@" +
                         "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                         "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

     // Getters for email and password
    public String getEmail() {
        return email;
    }

    
    public String getPassword() {
        return password;
    }

    public String getFirstName() { return FirstName;}


    

 
    //public boolean verifyLogin(UserDatabase userDB) {
    //    return userDB.customerExist(this.email, this.password);
    //}
  
  
}
