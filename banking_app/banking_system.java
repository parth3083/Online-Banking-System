package banking_app;
// import banking_app.*;
import java.sql.*;
import java.util.*;

public class banking_system {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ce) {
            System.out.println(ce);
        }
        try{
            Connection  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system", "root", "password");
            Scanner sc = new Scanner(System.in);
            users user = new users(con, sc);
            account account = new account(con, sc);
            account_manager account_manager = new account_manager(con, sc);
            String email;
            long account_number;
            while (true) {
                System.out.println("*** Welcome to Online Banking System ***");
                System.out.println();
                System.out.println("1.Register");
                System.out.println("2.Login");
                System.out.println("3.Exit");
                System.out.print("Enter your choice : ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: {
                        user.register();
                        break;
                    }
                    case 2: {
                        email = user.login();
                        if (email != null) {
                            System.out.println();
                            System.out.println("User Logged in");
                            if (account.account_exeists(email)) {
                                System.out.println();
                                System.out.println("1.Open Bank account");
                                System.out.println("2.Exit");
                                if (sc.nextInt() == 1) {
                                    account_number = account.open_account(email);
                                    System.out.println("Account created successfully!!!");
                                    System.out.println("Your account number : " + account_number);
                                } else {
                                    break;
                                }
                            }
                            account_number = account.get_account_number(email);
                            int choice1 = 0;
                            while (choice != 5) {
                                System.out.println();
                                System.out.println("1.Debit Money");
                                System.out.println("2.Credit Money");
                                System.out.println("3.Transfer money");
                                System.out.println("4.Check Balance");
                                System.out.println("5.Exit");
                                switch (choice1) {
                                    case 1: {
                                        account_manager.debit_money(account_number);
                                        break;
                                    }
                                    case 2: {
                                        account_manager.credit_money(account_number);
                                        break;
                                    }    
                                    case 3: {
                                        account_manager.transfer_money(account_number);
                                        break;
                                    }    
                                    case 4: {
                                        account_manager.getBalance(account_number);
                                    }
                                    case 5:{
                                        break;
                                    }
                                    default:
                                        System.out.println("Enter a valid choice");
                                        break;
                                }
                            }
                        }
                    }
                    case 3: {
                        System.out.println("THANK YOU FOR USING THE BANKING SYSTEM");
                        System.out.println("Exiting the system");
                        return;
                    } 
                    default: {
                        System.out.println("Enter a valid choice.");
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}