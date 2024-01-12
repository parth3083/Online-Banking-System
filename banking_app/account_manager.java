package banking_app;
import java.sql.*;
import java.util.*;
// import javax.naming.spi.DirStateFactory.Result;
public class account_manager {
    private Connection con;
    private Scanner sc;

    public account_manager(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void credit_money(long account_number) throws SQLException {
        System.out.print("Enter the amount : ");
        double amount = sc.nextDouble();
        System.out.print("Enter the security pin : ");
        String security_pin = sc.nextLine();
        try{
            con.setAutoCommit(false);
            if(account_number!=0){
                PreparedStatement pstmt =con.prepareStatement("select * from account where account_number = ? and security_pin = ?");
                pstmt.setLong(1, account_number);
                pstmt.setString(2, security_pin);
                ResultSet rs=pstmt.executeQuery();
                if(rs.next()){
                        String credit_money="update account set balance = balance + ? where account_number = ?";
                        PreparedStatement pstmt1=con.prepareStatement(credit_money);
                        pstmt1.setDouble(1, amount);
                        pstmt1.setLong(2, account_number);
                        int rows_affected=pstmt1.executeUpdate();
                        if(rows_affected>0){
                            System.out.println("Rs : "+amount+" credited successfully!!!");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        }
                        else{
                            System.out.println("Transaction failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                }
                else{
                    System.out.println("Invalid pin");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.setAutoCommit(true);
    }

    public void debit_money(long account_number) throws SQLException {
        System.out.print("Enter the amount : ");
        double amount = sc.nextDouble();
        System.out.print("Enter the security pin : ");
        String security_pin = sc.nextLine();
        try {
            con.setAutoCommit(false);
            if (account_number != 0) {
                PreparedStatement pstmt = con
                        .prepareStatement("select * from account where account_number = ? and security_pin = ?");
                pstmt.setLong(1, account_number);
                pstmt.setString(2, security_pin);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    double current_balance = rs.getDouble("balance");
                    if (amount <= current_balance) {
                        String debit_money = "update account set balance = balance - ? where account_number = ?";
                        PreparedStatement pstmt1 = con.prepareStatement(debit_money);
                        pstmt1.setDouble(1, amount);
                        pstmt1.setLong(2, account_number);
                        int rows_affected = pstmt1.executeUpdate();
                        if (rows_affected > 0) {
                            System.out.println("Rs : " + amount + " debited successfully!!!");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient balance !!!");
                    }
                } else {
                    System.out.println("Invalid pin");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.setAutoCommit(true);
    }
    public void transfer_money(long sender_account_number) throws SQLException {
        System.out.print("Enter the receiver account number : ");
        Long receiver_account_number = sc.nextLong();
        System.out.print("Enter the amount : ");
        double amount = sc.nextDouble();
        System.out.print("Enter security pin : ");
        String security_pin = sc.nextLine();
        try{
            con.setAutoCommit(false);
            if(sender_account_number!=0 && receiver_account_number!=0){
            PreparedStatement pstmt=con.prepareStatement("select * from account where account_number = ? and security_pin = ?");
            pstmt.setLong(1, sender_account_number);
            pstmt.setString(2, security_pin);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                double current_balance=rs.getLong("balance");
                if(amount<=current_balance){
                    String debit_query="update account set balance = balance - ? and account_number = ?";
                    String credit_query="update account set balance = balance + ? and account_number = ?";
                    PreparedStatement pstmt1=con.prepareStatement(debit_query);
                    PreparedStatement pstmt2 =con.prepareStatement(credit_query);
                    pstmt1.setDouble(1, amount);
                    pstmt1.setLong(2, sender_account_number);
                    pstmt2.setDouble(1, amount);
                    pstmt2.setLong(2, receiver_account_number);
                    int rows_affected1=pstmt1.executeUpdate();
                    int rows_affected2=pstmt2.executeUpdate();
                    if(rows_affected1>0 && rows_affected2>0){
                        System.out.println("Transfer successful");
                        System.out.println("Rs "+amount+" transferred!!!");
                        con.rollback();
                        con.setAutoCommit(true);
                        return;
                    }
                    else{
                        System.out.println("Transfer failed !!!!");
                        con.commit();
                        con.setAutoCommit(true);
                    }
                }
                else{
                    System.out.println("Insufficient balance !!!!");
                }

            }
            else{
                System.out.println("Invalid security pin!!!!");
            }
            }
            else{
                System.out.println("Invalid account number");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.setAutoCommit(true);
        
    }
    public void getBalance(long account_number) {
        System.out.print("Enter the security pin : ");
        String security_pin = sc.nextLine();
        try{
            PreparedStatement pstmt=con.prepareStatement("select * from account where account_number = ? and security_pin = ?");
            pstmt.setLong(1, account_number);
            pstmt.setString(2, security_pin);
           ResultSet rs =pstmt.executeQuery();
           if(rs.next()){
            double balance=rs.getDouble("balance");
            System.out.println(" Balance  : "+balance);
           }
           else{
            System.out.println("Invalid pin");
           }
       } catch (Exception E) {
            System.out.println(E);
        }
    }
}
