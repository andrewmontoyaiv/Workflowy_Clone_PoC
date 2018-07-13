
package workflowy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQLite_Test_Main {
    public static void main(String[] args){
        SQLite_Test test = new SQLite_Test();
        //display users returns resultset
        ResultSet rs;
        
        //we must do something with all of the exceptions that SQLite_Test is throwing
        try {
            rs = test.displayUsers();
            //if there is no db create, it will create db, a table, and db values
            while(rs.next()){
            System.out.println(rs.getString("fname") + " " + rs.getString("lname"));
        }
        } catch (SQLException ex) {
            //or ex.printStackTrace();
            Logger.getLogger(SQLite_Test_Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            //or ex.printStackTrace();
            Logger.getLogger(SQLite_Test_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
