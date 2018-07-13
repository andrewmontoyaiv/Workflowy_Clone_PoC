
package workflowy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

//Singleton class, can be accessed anywhere from the system if instance is called. stores user and password. Drops data when program exits for new entries.
public class DatabaseUser{
    private static DatabaseUser instance = new DatabaseUser();
    private static Connection con;
    private static String username = "";
    private static String email = "";
    private static String password = "";
    

    private DatabaseUser() {
        
    }
    
    
    public static DatabaseUser getInstance(){
        return instance;
    }
    
    public void setUser(String username, String email, String password) throws SQLException, ClassNotFoundException{
        this.username = username;
        this.email = email;
        this.password = password;
        if (con == null || con.isClosed()){
            getConnection();
        }
        
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
 
    
    
    private void getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        //will be created if there is no current db
        con = DriverManager.getConnection("jdbc:sqlite:WorkflowyDatabaseSQLite.db");
    }

    private void inilitalize() throws SQLException {
            
    }
    
    public static String getEmail(){
        return email;
    }
    
    public void updateData() throws SQLException{
        
    }
    
    public HashMap extractData() throws SQLException, ClassNotFoundException{
        //user_"+username+"
        HashMap<Integer,TextObject> data = new HashMap<Integer,TextObject>();
        Statement stmt = null;
        if (con == null || con.isClosed()) {
            getConnection();
        }
        System.out.println("attempting usertable data extraction");
        stmt = con.createStatement();

        System.out.println("parsing info for user" + email);
        String query = "SELECT * FROM userData WHERE emailID = '" + email + "'";
        ResultSet rs = stmt.executeQuery(query);
        
        //System.out.println(rs.getString("tabValue") + " " + rs.getString("dataEntry"));

        try {
            int i = 0;
            while (rs.next()) {
                System.out.println(rs.getString("tabValue") + " " + rs.getString("dataEntry"));
   
                System.out.println("parsing database");
                TextObject temp = new TextObject();
                temp.setTab(rs.getInt("tabValue"));
                temp.setText(rs.getString("dataEntry"));
                
                
                data.put(i, temp);
                i++;
            }
        }
        catch (SQLException e){
            System.out.println("ERROR IN DatabaseUser.extractData");
        }
        finally {
            if (stmt != null){
                stmt.close();
            }
        }

        return data;
    }
    
    public void updateData(HashMap temp, int bounds) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        
            String query = "";
            if (con == null || con.isClosed()){
                getConnection();
            }
           
        PreparedStatement delete = con.prepareStatement("DELETE FROM userData WHERE emailID = '" + email + "'");
        delete.execute();

        for (int i = 0; i < bounds; i++) {
            TextObject textTemp = new TextObject();
                
                //textobject
                textTemp = (TextObject) temp.get(i);


                
                System.out.println(textTemp.getText());
                int tempText = textTemp.getTab();
                String tempString = textTemp.getText();
                
                
                
                //insert new data
                PreparedStatement prep = con.prepareStatement("INSERT INTO userData values(?,?,?);");
                //PreparedStatement.setString(0, String.valueOf(myChar))

                
                String tempEmail = this.email;
                prep.setString(1, tempEmail);
                prep.setInt(2, tempText);
                prep.setString(3, tempString);
                prep.execute();
                

            }

        
        
            
        }
    }



