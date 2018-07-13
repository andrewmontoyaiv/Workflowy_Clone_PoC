
package workflowy.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import workflowy.Database;

public class HomeController {
    @FXML
    private TextField emailLoginID;
    
    @FXML
    private PasswordField passwordLoginID;
    
    @FXML
    private Label nftcnLine;
    
    //
    
    @FXML
    private TextField emailSignupID1;
    
    @FXML 
    private TextField emailSignupID2;
    
    @FXML
    private TextField passwordSignupID;
    
    
    @FXML
    public void onLoginClicked (ActionEvent e) throws IOException {
        openPage("/workflowy/dsgn/Login.fxml", e);
    }
    
    @FXML
    public void onSignUpClicked(ActionEvent e) throws IOException {
       openPage("/workflowy/dsgn/SignUp.fxml", e);
    }

    
    @FXML
    public void onTryItClicked (ActionEvent e) throws IOException {
        openPage("/workflowy/dsgn/UserWorkflowTry.fxml", e);
    }
    
    @FXML
    public void loginAuthentication (ActionEvent e) throws IOException{
        String tempEmail = emailLoginID.getText();
        String tempPassword = passwordLoginID.getText();
        System.out.println("login initiated");
        
        Database temp = new Database();
        System.out.println("database created");
        try {
            System.out.println("validation starting");
            temp.validateUser(tempEmail, tempPassword, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void signupAuthentication (ActionEvent e) throws IOException{
        
        
        String tempEmail1 = emailSignupID1.getText();
        String tempEmail2 = emailSignupID2.getText();
        String tempPassword = passwordSignupID.getText();
        
        
        boolean passVal = passwordValidation(tempPassword);
        boolean emailVal = emailValidation(tempEmail1, tempEmail2);
    
        if (passVal && emailVal) {
            Database temp = new Database();
            try {
                if(temp.duplicateUser(tempEmail1)){
                    nftcnLine.setText("user already exists");
                    
                } else {
                    temp.addUser(tempEmail1, tempPassword);
                    System.out.println("user added sucessfully");
                    temp.validateUser(tempEmail1, tempPassword, e);
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            if(!passVal && !emailVal){
                nftcnLine.setText("Too many errors!");
            }
            else if(!emailVal){
                nftcnLine.setText("email does not match");
            }
            else if(!passVal){
                nftcnLine.setText("Password must have at least 6 characters");
            
        }
            else{
                System.out.println("Unknown Error");
            }
        }
        
        
    }
    
    
    
    public void openPage(String i, ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(i));
        Parent root;
        
        try{
            root = (Parent)loader.load();
            Scene scene2 = new Scene(root);
            Stage stage2 = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage2.setScene(scene2);
            stage2.show();

        }
        catch (IOException r){
            r.printStackTrace();
        }
    }
    
    private boolean passwordValidation(String password){

        if (password.length() < 6){
        return false;
    }
        else 
            return true;
    }
    
    private boolean emailValidation(String mail1, String mail2){
        if (mail1.equals(mail2)){
            return true;
        }
        else
            return false;
    }



    
}
