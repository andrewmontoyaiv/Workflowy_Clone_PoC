
package workflowy;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.HBox.setHgrow;
import javafx.scene.layout.Priority;

//data field is composed of an entry field and a button, which is placed in the document class
public class DataField extends HBox{

    private ControlButton ctrBtn = new ControlButton();
    private EntryField entryFld = new EntryField();
    
    
    public DataField(){
        setAlignment(Pos.CENTER_LEFT);
        setHgrow(entryFld, Priority.ALWAYS);
        setHgrow(ctrBtn, Priority.NEVER);
        setPrefHeight(28);
        setMaxHeight(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinHeight(20);
        getChildren().addAll(ctrBtn, entryFld);
        entryFld.setVisible(true);
        entryFld.setFocusTraversable(true);
        //setMargin(this, new Insets(0, 0, 0, 0));
        setPadding(new Insets(0,0,0,0));
        
        
    }
    
    public EntryField getEntryField(){
        return entryFld;
    }

    
    
    
    

}
