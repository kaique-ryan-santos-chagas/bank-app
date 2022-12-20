package com.bank.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    String host;
    String name;
    String username;
    String url;
    String password;
    int port;
    
    public ConnectionDB(){

        this.host = "localhost";
        this.name = "Bank";
        this.port = 3306;
        this.username = "root";
        this.url = "jdbc:mysql://" + this.host + "/" + this.name;
        this.password = "";
    }

    public Connection getConnection(){

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(this.url, this.username, this.password);
            return connection;

        } catch(Exception error){

            System.out.println(error);
            return connection;
        }
      
    }

}
