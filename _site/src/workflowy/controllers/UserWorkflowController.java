
package workflowy.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import workflowy.DataField;
import workflowy.Document;


public class UserWorkflowController {
    @FXML
    private RadioMenuItem radioNight;
            
            
    @FXML
    private ScrollPane scrollPaneID;
    
    @FXML
    private MenuBar menuNode;
    
    @FXML
    public void initialize(){
        scrollPaneID.setContent(Document.getInstance());
        Document.getInstance().getChildren().addAll(new DataField());
    }
    
    @FXML
    public void saveDataActn (ActionEvent e) throws IOException{
        Document.getInstance().saveData();
    }
    
    @FXML
    public void openHome (ActionEvent e) throws IOException{
        Document.getInstance().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/workflowy/dsgn/HomeScreen.fxml"));
        Parent root;
        
        try{
            root = (Parent)loader.load();
            Scene scene3 = new Scene(root);
            Stage stage3 = (Stage) menuNode.getScene().getWindow();       
            stage3.setScene(scene3);
            stage3.show();

        }
        catch (IOException r){
            r.printStackTrace();
        }
        


    }
    
    @FXML
    public void aboutDialog(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Workflowy");
        alert.setHeaderText("Welcome to Workflowy!");
        alert.setContentText("press ENTER on a field to create a new line. Press TAB to indent your text & SHIFT + TAB to perform an outdent");

        alert.showAndWait();
    }
    
    @FXML
    public void nightMode (ActionEvent e) throws IOException {
        if (radioNight.isSelected()){
            System.out.println("Night mode activated");
            Document.getInstance().changeColor("night");
        }
        else{
            System.out.println("reset to original layout");
    }
 
    }
    
    
    
    
}
