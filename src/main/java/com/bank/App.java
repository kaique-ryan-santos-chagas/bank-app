package com.bank;

import java.util.Scanner;
import java.util.HashMap;

import com.bank.Controllers.FinancialController;
import com.bank.Controllers.UserController;

public class App 
{

    public static void main( String[] args )
    {

        Scanner reader = new Scanner(System.in);
        int option;

        System.out.println("");
        System.out.println("Hello, welcome to Bank App.");
        System.out.println("===========================");
        System.out.println("");

        System.out.println("1 - Login");
        System.out.println("2 - Registry");
        System.out.println("3 - Quit");
        System.out.println("");

        System.out.print("Choose an option: ");
        option = reader.nextInt();

        switch(option){
            
            case 1:
                login();
            break;

            case 2:
                registry();
            break;

            case 3:
                System.out.println("");
                System.out.println("Thank you, goodbye!");
            break;

            default:
                System.out.println("");
                System.out.println("Option invalid.");
            break;
        }

        reader.close();

    }

    public static void login(){

        HashMap<String, String> userData = new HashMap<String, String>();

        UserController userController = new UserController();

        Scanner reader = new Scanner(System.in);

        String usermail;
        String password;
        int userid;

        System.out.println("");
        System.out.println("Login");
        System.out.println("");
        
        System.out.print("E-mail: ");
        usermail = reader.nextLine();
        
        System.out.print("Password: ");
        password = reader.nextLine();

        System.out.println("");
        System.out.println("Wait please...");

        userData = userController.getUserData(usermail, password);

        if(userData.get("name") == null){

            System.out.println("");
            System.out.println("Something is wrong.");

            main(null);
        }
        
        else {

            System.out.println("");
            System.out.println("Welcome back, " + userData.get("name"));

            userid = Integer.parseInt(userData.get("id"));

            bankTasks(userid);
        }

        reader.close();
    }

    public static void registry(){

        UserController userController = new UserController();
        FinancialController financialController = new FinancialController();

        Scanner reader = new Scanner(System.in);

        HashMap<String, String> userData = new HashMap<String, String>();

        String username;
        String phone;
        String usermail;
        String password;
        int userid;
        int userAccount;

        System.out.println("");
        System.out.println("Registry");
        System.out.println("");
        
        System.out.print("E-mail: ");
        usermail = reader.nextLine();
        
        System.out.print("Password: ");
        password = reader.nextLine();

        System.out.print("Name: ");
        username = reader.nextLine();

        System.out.print("Phone: ");
        phone = reader.nextLine();

        System.out.println("");
        System.out.println("Wait please...");

        userController.registryUser(username, usermail, password, phone);
        userData = userController.getUserData(usermail, password);

        userid = Integer.parseInt(userData.get("id"));

        userAccount = financialController.registryAccount(userid);

        System.out.println("");
        System.out.println("Your user account number is: " + userAccount);

        bankTasks(userid);

        reader.close();

    }

    public static void bankTasks(int userid){

        Scanner reader = new Scanner(System.in);

        int option;

        System.out.println("");
        System.out.println("What we can do for you today?");
        System.out.println("");

        System.out.println("1 - Consult Balance.");
        System.out.println("2 - Deposit.");
        System.out.println("3 - Bank transfer.");
        System.out.println("4 - Savings Deposit");
        System.out.println("5 - Consult Savings Balance");
        System.out.println("6 - Plunder Savings");
        System.out.println("7 - Delete Account");
        System.out.println("8 - Quit");

        System.out.println("");
        System.out.print("Choose an option: ");
        option = reader.nextInt();

        switch(option){

            case 1:
                consultBalance(userid);
            break;

            case 2:
                deposit(userid);
            break;

            case 3:
                bankTransfer(userid);
            break;

            case 4:
                savingsDeposit(userid);
            break;

            case 5:
                consultSavingsBalance(userid);
            break;

            case 6:
                plunderSavings(userid);
            break;

            case 7:
                deleteAccount(userid);
            break;

            case 8:
                System.out.println("");
                System.out.println("Thank you, goodbye!");
                main(null);
            break;

            default:
                System.out.println("");
                System.out.println("Option invalid.");
                
                try {

                    Thread.sleep(2000);
                    bankTasks(userid);
                    
                } catch(Exception error){
        
                    System.out.println(error);
                }

            break;

        }

        reader.close();
    }

    public static void consultBalance(int userid){

        FinancialController financialController = new FinancialController();

        double balance;

        System.out.println("");
        System.out.println("Consulting your balance...");

        balance = financialController.getUserBalance(userid);

        System.out.println("");
        System.out.println("Balance: R$" + balance);

        try {

            Thread.sleep(2000);
            bankTasks(userid);
            
        } catch(Exception error){

            System.out.println(error);
        }

    }

    public static void deposit(int userid){

        FinancialController financialController = new FinancialController();

        Scanner reader = new Scanner(System.in);

        double money;
        String depositMessage;

        System.out.println("");
        System.out.println("Bank Deposit");
        System.out.println("============");
        System.out.println("");

        System.out.print("Value: ");
        money = reader.nextDouble();

        System.out.println("");
        System.out.println("Wait please...");

        depositMessage = financialController.deposit(userid, money);

        System.out.println(depositMessage);

        try {

            Thread.sleep(2000);
            bankTasks(userid);
            
        } catch(Exception error){

            System.out.println(error);
        }

        reader.close();

    }

    public static void bankTransfer(int userid){

        FinancialController financialController = new FinancialController();

        Scanner reader = new Scanner(System.in);

        String emailReceiver;
        String transferMessage;
        double transferValue; 

        System.out.println("");
        System.out.println("Bank Transfer");
        System.out.println("=============");
        System.out.println("");

        System.out.print("User receiver e-mail: ");
        emailReceiver = reader.nextLine();

        System.out.print("Value: ");
        transferValue = reader.nextDouble();

        transferMessage = financialController.bankTransfer(emailReceiver, transferValue, userid);

        System.out.println("");
        System.out.println(transferMessage);

        try {

            Thread.sleep(2000);
            bankTasks(userid);

        } catch(Exception error){

            System.out.println(error);
        }

        reader.close();

    }

    public static void savingsDeposit(int userid){

        FinancialController financialController = new FinancialController();

        Scanner reader = new Scanner(System.in);

        double value;
        String depositMessage;

        System.out.println("");
        System.out.println("Savings Deposit");
        System.out.println("===============");
        System.out.println("");

        System.out.print("Value: ");
        value = reader.nextDouble();
       
        depositMessage = financialController.savingsDeposit(userid, value);

        System.out.println("");
        System.out.println(depositMessage);

        try {

            Thread.sleep(2000);
            bankTasks(userid);

        } catch(Exception error){

            System.out.println(error);
        }

        reader.close();
    }

    public static void consultSavingsBalance(int userid){

        FinancialController financialController = new FinancialController();

        String consultMessage;

        System.out.println("");
        System.out.println("Consult Savings Balance");
        System.out.println("=======================");
        System.out.println("");

        consultMessage = financialController.consultSavingsBalance(userid);

        System.out.println(consultMessage);

        try {

            Thread.sleep(2000);
            bankTasks(userid);

        } catch(Exception error){

            System.out.println(error);
        }
        
    }

    public static void deleteAccount(int userid){

        UserController userController = new UserController();

        String deleteMessage;

        deleteMessage = userController.deleteAccount(userid);

        System.out.println("");
        System.out.println(deleteMessage);

        try {

            Thread.sleep(2000);
            main(null);

        } catch(Exception error){

            System.out.println("");
            System.out.println(error);
        }
    }

    public static void plunderSavings(int userid) {

        FinancialController financialController = new FinancialController();

        Scanner reader = new Scanner(System.in);

        String plunderMessage;
        double plunderValue;

        System.out.println("");
        System.out.println("Plunder Savings");
        System.out.println("===============");
        System.out.println("");

        System.out.print("Value: ");
        plunderValue = reader.nextDouble();

        plunderMessage = financialController.plunderSavings(userid, plunderValue);

        System.out.println("");
        System.out.println(plunderMessage);

        try {

            Thread.sleep(2000);
            bankTasks(userid);

        } catch(Exception error){

            System.out.println(error);
        }

        reader.close();

    }

}
