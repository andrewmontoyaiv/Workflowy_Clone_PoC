
package workflowy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//purpose is to addd or display users
public class SQLite_Test {
    
    private static Connection con;
    private static boolean hasData = false;
    
    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if (con == null){
            getConnection();
        }
        
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT fname, lname FROM user");
        return res;
    }
    
    private void getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        //will be created if there is no current db
        con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
        inilitalize();
    }

    private void inilitalize() throws SQLException {
        if(!hasData){
            hasData = true;
            Statement state = con.createStatement();
            //is there a table with the name user in the db? if there is, we want to use that one
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
            if (!res.next()){
                //if there is no next
                System.out.println("Building the User table with prepopulated values");
                //building table
                Statement nextState = con.createStatement();
                nextState.execute("CREATE TABLE user (id integer, " + "fname varchar(60)," + "lname varchar(60)," 
                + "primary key(id));");
                
                //insert data
                PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?);");
                prep.setString(2, "John");
                prep.setString(3, "McNeil");
                prep.execute();
                
                PreparedStatement prep2 = con.prepareStatement("INSERT INTO user values(?,?,?);");
                prep2.setString(2, "Paul");
                prep2.setString(3, "Smith");
                prep2.execute();
            }
        }
    }
    
    public void addUser(String firstName, String lastName) throws ClassNotFoundException, SQLException{
        if(con == null){
            getConnection();
        }
        
        PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?);");
        prep.setString(2, firstName);
        prep.setString(3, lastName);
        prep.execute();
        
    }
    
}
