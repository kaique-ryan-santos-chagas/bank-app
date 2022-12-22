package com.bank.Database;

import java.sql.Statement;
import java.sql.Connection;

public class Migrations {

    ConnectionDB connectionDB = new ConnectionDB();
    Connection connection;

    Statement statement;

    String createTableUsersQuery = "CREATE TABLE IF NOT EXISTS users ( userid INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(60), email VARCHAR(60), phone VARCHAR(15), userpass VARCHAR(30) );";
    String createTableUserAccountQuery = "CREATE TABLE IF NOT EXISTS user_account ( accountid INT AUTO_INCREMENT PRIMARY KEY, balance DOUBLE, savings DOUBLE, userid INT, FOREIGN KEY (userid) REFERENCES users(userid) );";

    public Migrations(){

       connection = connectionDB.getConnection();

       try {

            statement = connection.createStatement();

       } catch(Exception error){

            System.out.println(error);

       }

    }

    public void migrate(){

        createTableUsers();
        createTableUserAccount();

    }

    public String createTableUsers(){

        try {

            statement.executeUpdate(createTableUsersQuery);
            return "Table users migrated sucessfully.";

        } catch(Exception error){

            System.out.println(error);
            return "Something went wrong.";

        }

    }
    
    public String createTableUserAccount(){

        try {

            statement.executeUpdate(createTableUserAccountQuery);
            return "Table user_account migrated sucessfully.";

        } catch(Exception error){

            System.out.println(error);
            return "Something went wrong.";

        }

    }
}
