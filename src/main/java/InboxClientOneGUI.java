import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class InboxClientOneGUI {
    private JPanel jPane;

    public static void main(String[] args){
        try {
            // Build Frame
            JFrame frame = new JFrame("App");
            frame.setContentPane(new InboxClientOneGUI().jPane);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(false);

            // SQL Connection
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.33:3306/emailInbox","root","######");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT emailID, subject, body FROM emails WHERE user='UserOne';");
            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

}
