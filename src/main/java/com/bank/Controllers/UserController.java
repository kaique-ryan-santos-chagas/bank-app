package com.bank.Controllers;

import com.bank.Database.ConnectionDB;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.HashMap;

public class UserController {

    ConnectionDB connectionDB = new ConnectionDB();

    Connection connection = null;

    Statement statement = null;
    ResultSet resultSet = null;
    
    String username;
    String useremail;
    String phone;
    int userid;

    public UserController(){ 

        connection = connectionDB.getConnection();

        try {

            statement = connection.createStatement();

        } catch(Exception error){

            System.out.println(error);
        }

    }

    public HashMap<String, String> getUserData(String useremail, String password){

        String query = "SELECT * FROM users WHERE email = " + "'" + useremail + "'" + " AND userpass = " + "'" + password + "'";

        HashMap<String, String> userData = new HashMap<String, String>();

        try {

            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                
                this.username = resultSet.getString("username");
                this.useremail = resultSet.getString("email");
                this.phone = resultSet.getString("phone");
                this.userid = resultSet.getInt("userid");
            }

            userData.put("name", this.username);
            userData.put("email", this.useremail);
            userData.put("phone", this.phone);
            userData.put("id", Integer.toString(this.userid));

            return userData;

        } catch(Exception error){

            System.out.println(error);
            return userData;
            
        }
    }

    public void registryUser(String username, String email, String password, String phone){

        String consultEmail = "SELECT email FROM users WHERE email = '" + email + "'";
        
        String query = "INSERT INTO users (username, phone, email, userpass) VALUES ('"+ username +"', '"+ phone +"', '"+ email +"', '"+ password +"')";
        
        String userEmail = "";

        try {

            resultSet = statement.executeQuery(consultEmail);

            while(resultSet.next()){
                userEmail = resultSet.getString("email");
            }

            if(!userEmail.equals(email)){

                statement.executeUpdate(query);

                System.out.println("");
                System.out.println("User sucessfully registred.");
                System.out.println("");
                System.out.println("Welcome, "+ username);

            } 

            else {

                System.out.println("");
                System.out.println("This account already exist.");
            }
           
        } catch(Exception error){

            System.out.println("");
            System.out.println(error);

        }
        
    }

    public String deleteAccount(int userid){

        String deleteUser = "DELETE FROM users WHERE userid = " + userid;
        String deleteUserAccount = "DELETE FROM user_account WHERE userid = " + userid;

        try {
            
            statement.executeUpdate(deleteUserAccount);
            statement.executeUpdate(deleteUser);

            return "User sucessfully deleted.";

        } catch(Exception error){
            
            System.out.println("");
            System.out.println(error);
            return "Something went wrong.";
        }

    }

}
