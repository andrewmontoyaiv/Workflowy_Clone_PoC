/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workflowy;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.E;
import javafx.scene.layout.VBox;

//singleton class. will be accessible throughout the life of the program
public class Document extends VBox{
    private static Document instance = new Document();
    private static String filename = "Document.txt";
    private static int index = 0;
    private static int publishedIndex = 0;
    private static boolean populateDataBool = false;
    
    
    //private constructor. prevents instantiation from another class
    private Document(){
        prefHeight(200.0);
        prefWidth(100.0);
        setPadding(new Insets(5,0,5,0));
        if (populateDataBool == true){
            populateData();
        }
        
    }
    
    //gets location of the called datafield node
    private static int getIndex(){
        return index;
    }
    //sets the location of the called datafield node. used for accessssing the node that is being signaled from a datafields actionevent
    public static void setIndex(){
        Object[] howMany = Document.getInstance().getChildren().toArray();
        int ceiling = howMany.length;
        System.out.println(ceiling);

        outerloop:
        for (int i = 0; i < ceiling; i++) {
            DataField item = (DataField) Document.getInstance().getChildren().get(i);
            boolean tf = item.getEntryField().getInternalIndex();
            if (tf == true) {
                break outerloop;
            }
            index++;
        }
        index++;
        publishedIndex = index;
        resetIndex();


    }
    
    //resets the location for new entries
    private static void resetIndex(){
        index = 0;
    }

    
    //returns the instance of the document
    public static Document getInstance(){
        return instance;
    }
    
    //gets the NEW index
    public static int getPublishedIndex(){
        return publishedIndex;
    }
    
    //not yet implemented
    private static void populateData(){

    }
    //writes data from hashmap to the document. Mat is created in the database class.
    public void writeData(HashMap text) {
        int bounds = text.size();
        int tabPass = 0;
        String textPass = null;
        System.out.println("Bound is: " + bounds);
        
        for (int t = 0; t < bounds; t++){
            TextObject temp = new TextObject();
            temp = (TextObject) text.get(t);
            tabPass = temp.getTab();
            textPass = temp.getText();
            System.out.println("tab "+tabPass);
            System.out.println("text "+ textPass);
            
            DataField dataFieldInstance = new DataField();
            System.out.println(t);
            Document.getInstance().getChildren().add(t, dataFieldInstance);
            
            dataFieldInstance.getEntryField().setText(textPass);
            dataFieldInstance.getEntryField().moveTab(tabPass, dataFieldInstance);
        }
    }
    
    //when called, saves the current state of the document and saves it to the database
    public void saveData() {
        System.out.println("saving data");
        HashMap <Integer,TextObject> tempMap = new HashMap<Integer,TextObject>();
        
        Object[] toArray = Document.getInstance().getChildren().toArray();
        int bounds = toArray.length;
 
        //writing map
        tempMap = writeMap(bounds);
        
        
        //update the data in the database
        try {
            //writing map to database
            DatabaseUser.getInstance().updateData(tempMap, bounds);
        } catch (SQLException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //initiated from saveData()
    private HashMap<Integer,TextObject> writeMap(int bounds){

        HashMap <Integer,TextObject> map = new HashMap<Integer,TextObject>();
        
        for (int i = 0; i < bounds; i++){
            
            DataField item = (DataField) Document.getInstance().getChildren().get(i);
            int tabValue = item.getEntryField().getTabValue();
            String stringValue = item.getEntryField().getText();
            TextObject tempTextObject = new TextObject(tabValue, stringValue);
            map.put(i, tempTextObject);

        }
        
        
        return map;
    }
    
    public void changeColor(String theme){
        //scroll through each EntryField and change color to the selected
        
    }

}
