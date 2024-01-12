package banking_app;
import java.sql.*;
import java.util.*;
public class account {
    private Connection con;
    private Scanner sc;

    public account(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public long open_account(String email) {
        if (!account_exeists(email)) {
            System.out.print("Enter full name  : ");
            String full_name = sc.nextLine();
            System.out.print("Enter the balance : ");
            double balance = sc.nextDouble();
            System.out.print("Enter security pin : ");
            String security_pin = sc.nextLine();
            String open_account_query = "insert into account (account_number, fullname, email, balance, security_pin) values(?,?,?,?)";
            try {
                long account_number = generate_account_number();
                PreparedStatement pstmt = con.prepareStatement(open_account_query);
                pstmt.setLong(1, account_number);
                pstmt.setString(2, full_name);
                pstmt.setString(3, email);
                pstmt.setDouble(4, balance);
                pstmt.setString(5, security_pin);
                int rows_affected = pstmt.executeUpdate();
                if (rows_affected > 0) {
                    return account_number;
                } else {
                    throw new RuntimeException("Account creation failed!!!");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        throw new RuntimeException("Account already exists!!!");
    }
    public long get_account_number(String email) {
        try{
            PreparedStatement pstmt=con.prepareStatement("select account_number from account where email = ?");
            pstmt.setString(1, email);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                return rs.getLong("account_number");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        throw new RuntimeException("Account doesn't exists!!!");
    }
    public long generate_account_number() {
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select account_number from account ORDER BY account_number DESC LIMIT 1");
            if (rs.next()) {
                long latest_account_number = rs.getLong("account_number");
                return latest_account_number + 1;
            }
            else{
                return 10000100;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 10000100;
    }
    public boolean account_exeists(String email){
        try{
            PreparedStatement pstmt= con.prepareStatement("select account_number form account where email= ?");
            pstmt.setString(1, email);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}
