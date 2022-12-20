package com.bank.Controllers;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import com.bank.Database.ConnectionDB;

public class FinancialController {

    UserController userController = new UserController();
    ConnectionDB connectionDB = new ConnectionDB();

    Connection connection; 
    Statement statement;
    ResultSet resultSet;

    double balance;
    
    public FinancialController(){

        connection = connectionDB.getConnection();
        
        try {

            statement = connection.createStatement();

        } catch(Exception error){
            System.out.println(error);
        }

        balance = 0;
    }

    public double getUserBalance(int userid){

        String query = "SELECT balance FROM user_account WHERE userid = '"+ userid +"' ";

        try {

            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                balance = resultSet.getDouble("balance");
            }

            return balance;

        } catch(Exception error){
            
            System.out.println(error);
            return balance;

        }

    }

    public int registryAccount(int userid){

        int accountid = 0;

        String query = "INSERT INTO user_account (balance, savings, userid) VALUES (0, 0, "+ userid +") ";
        String secondQuery = "SELECT accountid FROM user_account WHERE userid = " + userid;

        try {

            statement.executeUpdate(query);
            resultSet = statement.executeQuery(secondQuery);

            while(resultSet.next()){
                accountid = resultSet.getInt("accountid");
            }

            return accountid;

        } catch(Exception error){

            System.out.println(error);
            return accountid;
        }

    }

    public String deposit(int userid, double value){

        double depositValue = value;
        double valueDatabase = 0;

        String consultBalance = "SELECT balance FROM user_account WHERE userid = " + userid;

        try {

            resultSet = statement.executeQuery(consultBalance);

            while(resultSet.next()){
                valueDatabase = resultSet.getDouble("balance");
            }

            depositValue = valueDatabase + depositValue;

            String queryUpdate = "UPDATE user_account SET balance = " + depositValue + " WHERE userid = " + userid;

            statement.executeUpdate(queryUpdate);

            return "Deposit made sucessfully.";

        } catch(Exception error){

            System.out.println(error);

            return "Something went wrong.";
        }


    }

    public String bankTransfer(String emailReceiver, double value, int userid){

        double transferValue = value; 
        double userEmissaryBalance = 0;
        double userReceiverBalance = 0;

        int receiverId = 0; 

        String userEmissary = "SELECT balance FROM user_account WHERE userid = " + userid;
        String userReceiver = "SELECT userid FROM users WHERE email = '" + emailReceiver + "'";

        try {

            resultSet = statement.executeQuery(userEmissary);

            while(resultSet.next()){
                userEmissaryBalance = resultSet.getDouble("balance");
            }

            resultSet = statement.executeQuery(userReceiver);

            while(resultSet.next()){
                receiverId = resultSet.getInt("userid");
            }

            String queryReceiverBalance = "SELECT balance FROM user_account WHERE userid = " + receiverId;

            resultSet = statement.executeQuery(queryReceiverBalance);

            while(resultSet.next()){
                userReceiverBalance = resultSet.getDouble("balance");
            }

            userEmissaryBalance = userEmissaryBalance - transferValue;
            userReceiverBalance = userReceiverBalance + transferValue;

            String userEmissaryUpdate = "UPDATE user_account SET balance = " + userEmissaryBalance + " WHERE userid = " + userid;
            String userReceiverUpdate = "UPDATE user_account SET balance = " + userReceiverBalance + " WHERE userid = " + receiverId;

            if(userEmissaryBalance < 0){
                return "Sorry, insufficient balance.";
            }

            statement.executeUpdate(userEmissaryUpdate);
            statement.executeUpdate(userReceiverUpdate);

            return "Transference made sucessfully.";


        } catch(Exception error){

            System.out.println(error);
            return "Something went wrong.";
            
        }
    }

    public String savingsDeposit(int userid, double value){

        double depostiValue = value;
        double savingsBalance = 0;
        double userBalance = 0;

        String queryGetSavingsBalance = "SELECT savings, balance FROM user_account WHERE userid = " + userid;

        try {

            resultSet = statement.executeQuery(queryGetSavingsBalance);

            while(resultSet.next()){
                savingsBalance = resultSet.getDouble("savings");
                userBalance = resultSet.getDouble("balance");
            }

            userBalance = userBalance - depostiValue;
            depostiValue = savingsBalance + depostiValue;

            String depositQuery = "UPDATE user_account SET savings = " + depostiValue + " WHERE userid = " + userid;
            String subBalance = "UPDATE user_account SET balance = " + userBalance + " WHERE userid = " + userid;

            statement.executeUpdate(depositQuery);
            statement.executeUpdate(subBalance);

            return "Deposit in your savings made sucessfully"; 

        } catch(Exception error){

            System.out.println(error);

            return "Something went wrong.";
        }

    }

    public String consultSavingsBalance(int userid){
        
        String consultSavings = "SELECT savings FROM user_account WHERE userid = " + userid;

        double userSavings = 0;

        try {

            resultSet = statement.executeQuery(consultSavings);

            while(resultSet.next()){
                userSavings = resultSet.getDouble("savings");
            }

            return "Savings Balance: R$" + userSavings;

        } catch(Exception error){

            System.out.println("");
            System.out.println(error);

            return "Something went wrong.";
        }

    }

    public String plunderSavings(int userid, double value){

        double plunderValue = value;
        double savingsBalance = 0;
        double balance = 0;

        String queryBalance = "SELECT savings, balance FROM user_account WHERE userid = " + userid;

        try {

            resultSet = statement.executeQuery(queryBalance);

            while(resultSet.next()){
                savingsBalance = resultSet.getDouble("savings");
                balance = resultSet.getDouble("balance");
            }

            if(savingsBalance < plunderValue)
                return "Sorry, savings balance insuficient.";

            balance = balance + plunderValue;
            savingsBalance = savingsBalance - plunderValue;

            String plunder = "UPDATE user_account SET savings = " + savingsBalance + " WHERE userid = " + userid;
            String balanceUpdate = "UPDATE user_account SET balance = " + balance + " WHERE userid = " + userid;

            statement.executeUpdate(plunder);
            statement.executeUpdate(balanceUpdate);

            return "Plunder of R$" + plunderValue + " made sucessfully.";


        } catch(Exception error){

            System.out.println("");
            System.out.println(error);

            return "Something went wrong.";
        }
    }

}
