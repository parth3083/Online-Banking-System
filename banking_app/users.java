package banking_app;
import java.sql.*;
import java.util.*;

public class users {
    private Connection con;
    private Scanner sc;

    public users(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    // registeing the user 
    public void register() {
        System.out.println();
        System.out.print("Enter your full name : ");
        String full_name = sc.nextLine();
        System.out.print("Enter your email : ");
        String email = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        if (user_exists(email)) {
            System.out.println("User already exist for this email ");
            return;
        }
        String registration_query = "insert into users(full_name, email, passsword) values(?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(registration_query);
            pstmt.setString(1, full_name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            int affected_rows = pstmt.executeUpdate();
            if (affected_rows > 0) {
                System.out.println("Registration Successful!!!");
            } else {
                System.out.println("Registration failed!!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String login() {
        System.out.print("Enter your email : ");
        String email = sc.nextLine();
        System.out.print("Enter password : ");
        String password = sc.nextLine();
        String login_query = "select * from users where email=?  and password= ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(login_query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return email;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean user_exists(String email) {
    try{
        PreparedStatement pstmt=con.prepareStatement("select * from user where email = ?");
        pstmt.setString(1, email);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            return true;
        }
        else{
            return false;
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    return false;
 }
}


