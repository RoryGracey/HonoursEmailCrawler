import java.sql.*;
public class InboxClientOneGUI {
    public static void main(String[] args){
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.33:3306/emailInbox","root","##########");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM emails WHERE user='UserOne';");
            while (rs.next()){
                System.out.println(rs.getString(3));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
