
package workflowy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import workflowy.controllers.HomeController;


//add or display users
public class Database {
    
    private static Connection con;
    private static boolean hasData = false;
    
    //name change to getUsers
    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if (con == null){
            getConnection();
        }
        
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT email, password FROM user");
        return res;
    }
    
    //gets connection to the datatbase
    private void getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        //will be created if there is no current db
        con = DriverManager.getConnection("jdbc:sqlite:WorkflowyDatabaseSQLite.db");
        inilitalize();
    }

    //initializes the database when called. If database is empty, tables are created
    private void inilitalize() throws SQLException {
        if(!hasData){
            hasData = true;
            Statement state = con.createStatement();
            //is there a table with the name user in the db? if there is, we want to use that one
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users'");
            if (!res.next()){
                //if there is no next
                System.out.println("Building table");
                //building table
                Statement nextState = con.createStatement();
                //limit   execution of SQL
                
                String com1 = "CREATE TABLE users (id integer, " + "email varchar(60)," + "password varchar(60),"  
                + "primary key(id));";
                String com2 = "CREATE TABLE userData (emailID varchar(60), " + "tabValue integer," + "dataEntry varchar(60));";
                System.out.println("second table created");
                String com3 = "SELECT userData.tabValue, userData.dataEntry FROM userData INNER JOIN users ON userData.emailID = users.email";
                System.out.println("initialization join completed");
                
                nextState.addBatch(com1);
                nextState.addBatch(com2);
                nextState.addBatch(com3);
                
                nextState.executeBatch();

      
                
            }
        }
    }
    
    //checks for duplicate users in the database and returns a boolean
    public boolean duplicateUser(String testing) throws SQLException, ClassNotFoundException{
        
        if(con == null || con.isClosed()){
            getConnection();
        }
        
        Statement stmt = null;
        stmt = con.createStatement();

        String query = "SELECT email FROM users";
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()){
            if (rs.getString("email").equals(testing)){
                return true;
            }
                
        }
        return false;
    }
    
    //adds a user to the database
    public void addUser(String email, String password) throws ClassNotFoundException, SQLException{
        if(con == null || con.isClosed()){
            getConnection();
        }
        
        PreparedStatement prep = con.prepareStatement("INSERT INTO users values(?,?,?);");
        System.out.println("insert SUCESSFUL");
        String dataName = email + password;
        prep.setString(2, email);
        prep.setString(3, password);
        prep.execute();
        
        DatabaseUser.getInstance().setUser(dataName, email, password);
        con.close();
        
        
        
        
    }
    
    //has no implementation yet
    public void editUser() throws ClassNotFoundException, SQLException{
        if(con == null || con.isClosed()){
            getConnection();
        }
        
        
    }
    
    //tests whether or not data matches to a record in the database. Called from validateUser
    private boolean validationTest(String email, String password) throws ClassNotFoundException, SQLException{
        String dbEmail = "";
        String dbPassword = "";
        Statement stmt = null;
        boolean validationPass = false;
        int validationPassInt = 0;

        if(con == null || con.isClosed()){
            getConnection();
        }

        stmt = con.createStatement();
            
        String query = "SELECT email, password FROM users WHERE email = '" + email + "'";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            dbEmail = rs.getString("email");
            dbPassword = rs.getString("password");
            
        }
        System.out.println("______________________________");
        


        if (email.equals(dbEmail) && dbPassword.equals(password)){
            validationPass = true;
            System.out.println("validation passed");
        }

        return validationPass;
        
    }
    
    //checks validation for user and password. if validationTest returns true, the new page is opened. if the extracted map has data, 
    //it is written to the document
    public void validateUser(String email, String password, ActionEvent e) throws ClassNotFoundException, SQLException{
        //resets email to its curent state
        DatabaseUser.getInstance().setEmail(email);
        
     
        if (validationTest(email, password)) {
            HashMap text = new HashMap();
            try {
                text = DatabaseUser.getInstance().extractData();

                HomeController contr = new HomeController();
                contr.openPage("/workflowy/dsgn/UserWorkflow.fxml", e);
                System.out.println("Page Opened");
            
                if (!text.isEmpty()) {
                    System.out.println("WRITING TEXT to doc");
                    Document.getInstance().writeData(text);
                }

            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("Validation failed");
        }
        
        con.close();

    }
}
    

    

