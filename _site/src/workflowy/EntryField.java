
package workflowy;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class EntryField extends TextArea {
    final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN);
    final KeyCombination debug = new KeyCodeCombination(KeyCode.BACK_SLASH, KeyCombination.SHIFT_DOWN);
    private boolean internalIndex = false;
    private boolean isTabbed = false;
    private boolean addTremoveF = false;
    private int tabbedValue = 0;
    private int placeInDoc = 0;

    //This functions as the textArea to the DataField. ActionEvent for this box is located here
    public EntryField() {
        setStyle(""
        + "-fx-font-size: 15px;"
);

        setPrefHeight(30);
        setPadding(new Insets(0, 0, 0, 0));
        setTranslateY(4);
        setTranslateX(3);
        

  
        setPrefWidth(USE_COMPUTED_SIZE);
        setWrapText(true);

        // setOnKeyPressed(new EventHandler<KeyEvent>() does not work. This variant overrides ENTER keyevent
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("I am at index: " + getPlaceInDoc());
                    int i = setNodeLocation();
                    Document.getInstance().getChildren().add(i, new DataField());
                    DataField instance = setDataFieldInstance(i);
                    //acccess value set from prevoius
                    int val = getTabValue();
                    System.out.println("Tab value is " + val);

                    //new entry tab set
                    instance.getEntryField().setTabValue(val);
                    instance.getEntryField().absoluteTabValueSet(val, instance, i);

                    //sets and matches index of last entry to new element
                    instance.getEntryField().setPlaceInDoc(getPlaceInDoc());
                    System.out.println("I am at index: " + getPlaceInDoc());

                    System.out.println("__________________________________________________");
                    instance.getEntryField().requestFocus();
                    event.consume();
                }
            }
        });
        //Tab && Tab + Shift Listener
        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (keyComb1.match(event)) {
                    addTremoveF = false;
                    System.out.println("I am at index: " + getPlaceInDoc());
                    int i = setNodeLocation();
                    DataField currentItem = setDataFieldInstance(--i);

                    if (0 < (i + 2) && currentItem.getEntryField().getPlaceInDoc() > 0) {
                        removePlaceInDoc();
                        System.out.println("my tabbed value is: " + tabbedValue);
                        int newInt = (tabbedValue - 30);
                        tabbedValue = newInt;
                        System.out.println("my new tabbed value is: " + newInt);
                        currentItem.setPadding(new Insets(0, 0, 0, newInt));


                        int limit = count();
                        recursionBackTab(i, limit);
                        
                    }
                    
                    

                } else if (event.getCode().equals(KeyCode.TAB)) {
                    addTremoveF = true;
                    System.out.println("I am at index: " + getPlaceInDoc());
                    int i = setNodeLocation();
                    DataField item = setDataFieldInstance(--i);

                    if (!(i == 0)) {
                        //tabs element by 30 pixels
                        tabValues(30, item);
                    }

                    System.out.println("I am at index: " + getPlaceInDoc());
                    System.out.println("__________________________________________________");

                    event.consume();
                } else if (debug.match(event)) {
                    System.out.println("I am at index: " + getPlaceInDoc());
                    System.out.println("My Tab Value is: " + getTabValue());

                    System.out.println("I am at index: " + getPlaceInDoc());
                    System.out.println("__________________________________________________");
                }
            }
        });
        //Backspace Listener
        setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    int i = setNodeLocation();
                    if (getText().isEmpty() && (i - 1) > 0) {
                        addTremoveF = false;
                        System.out.println("I am at index: " + getPlaceInDoc());

                        if (0 < (i)) {
                            int initPlacement = (getPlaceInDoc());
                            int t = i;
                            if (i < (count())) {
                                System.out.println("Over here");
                                DataField nextItem = (DataField) Document.getInstance().getChildren().get(i);
                                if (nextItem.getEntryField().getPlaceInDoc() <= (initPlacement)) {
                                    Document.getInstance().getChildren().remove(--t);
                                    System.out.println("Here");

                                    DataField item = setDataFieldInstance(--t);
                                    item.getEntryField().requestFocus();
                                }

                            } else {
                                System.out.println("Where am i?");
                                Document.getInstance().getChildren().remove(--t);

                                DataField item = setDataFieldInstance(--t);
                                item.getEntryField().requestFocus();
                            }

                        }

                    }
                }


                
            }
        });

    }

    //creates a flag for the document to catch which node is calling for an action to be done.
    private int setNodeLocation() {
        //Retrieves the index of the child node that is sending the TAB command
        setInternalIndex(true);
        Document.getInstance().setIndex();
        setInternalIndex(false);
        //Retrieves instance of the child node that sent the command and sets it to the variable: item
        return Document.getInstance().getPublishedIndex();
    }
    //returns an instance of the current datafield
    private DataField setDataFieldInstance(int i){
        return (DataField) Document.getInstance().getChildren().get(i);
    }
    //gets the elements current tabbed value
    public int getTabValue(){
        return tabbedValue;
    }
    
    //sets the elements current tabbed value
    private void setTabValue(int i){
        tabbedValue = i;
    }
    
    //called from database for moving new lelements a fixed ammount
    public void moveTab(int i, DataField dfld){
        tabValuesEC(i, dfld);
        setTabValue(i);
        
    }
    
    //sets the tab values (numerical record)
    private void absoluteTabValueSet(int initValue, DataField dfld, int i) {
        int newValue = 0;
        //System.out.println("publishedIndex" + Document.getInstance().getPublishedIndex());
        if (Document.getInstance().getPublishedIndex() < (count()-1)) {
            //System.out.println("First If");
            DataField cItem = setDataFieldInstance(++i);
            if (cItem.getEntryField().getTabValue() > getTabValue()){
                //System.out.println("cItem: " + cItem.getEntryField().getTabValue());
                //System.out.println("getTabValue: " + getTabValue());
                //System.out.println("Second If");
                dfld.getEntryField().addPlaceInDoc();
                newValue = tabbedValue + 30;
            }
            else {
                System.out.println("Second Else");
                newValue = tabbedValue;
            }
        } else {
            System.out.println("First Else");
            newValue = tabbedValue;
        }
        System.out.println("Are you there?");
        tabbedValue = newValue;
        //dfld.getEntryField().setTabValue(newValue);
        System.out.println("newValue is " + newValue);
        dfld.setPadding(new Insets(0, 0, 0, newValue));
        //sets and matches index of last entry to new element
        
        
    }
    
    //outdent nodes following the first node
    private void recursionBackTab(int t, int limit){
        int initPlacement = (getPlaceInDoc());
        System.out.println("Initioal Index: " + initPlacement);
        
        out:
        for (int i = (t); i < limit; i++){
            System.out.println("POSITION OF NEXT: " + i);
            
            
            int j = (i + 1);
            DataField nextItem = (DataField) Document.getInstance().getChildren().get(j);
            if((initPlacement + 1) < nextItem.getEntryField().getPlaceInDoc()){
                System.out.println("get place in doc: " + nextItem.getEntryField().getPlaceInDoc());
                int newValue = 0;
                newValue = nextItem.getEntryField().tabbedValue - 30;
                
                
                nextItem.getEntryField().tabbedValue = newValue;
                System.out.println("TABBED VALUE: " + tabbedValue);
                nextItem.setPadding(new Insets (0, 0, 0, newValue));
                nextItem.getEntryField().removePlaceInDoc();
                
            }
            else
                break out;

        }
    }
    //indent following nodes, called from tabValues
    private void recursionForwardTab(int t, int limit){
        
        int initPlacement = (getPlaceInDoc()-1);
        System.out.println("Initioal Index: " + initPlacement);
        
        
        //
        out:
        for (int i = (t); i < limit; i++){
            System.out.println("POSITION OF NEXT: " + i);
            DataField nextItem = (DataField) Document.getInstance().getChildren().get(i);
            if(initPlacement < nextItem.getEntryField().getPlaceInDoc()){
                System.out.println("get place in doc: " + nextItem.getEntryField().getPlaceInDoc());
                int newValue = 0;
                newValue = nextItem.getEntryField().tabbedValue + 30;
                
                
                nextItem.getEntryField().tabbedValue = newValue;
                System.out.println("TABBED VALUE: " + tabbedValue);
                nextItem.setPadding(new Insets (0, 0, 0, newValue));
                nextItem.getEntryField().addPlaceInDoc();
            }
            else
                break out;

        }
            
        
        
    }
    //a forced tab initiated by the database
    private void tabValuesEC(int i, DataField dfld){
    
        int newValue = i + tabbedValue;
        tabbedValue = newValue;
        dfld.setPadding(new Insets(0, 0, 0, newValue));

    }
//method for tabbing the first called node
    private void tabValues(int i, DataField dfld) {
        
        int t = Document.getInstance().getPublishedIndex();

        t = t - 2;
        DataField item = (DataField) Document.getInstance().getChildren().get(t);
        //tab the single element
        if (isTabbed == false || item.getEntryField().getTabValue() >= getTabValue()) {
            dfld.getEntryField().addPlaceInDoc();
            System.out.println("dflf: " + item.getEntryField().getTabValue());
            System.out.println("non:" + getTabValue());
            
            
            
            //create recursion
            int newValue = i + tabbedValue;
            tabbedValue = newValue;
            dfld.setPadding(new Insets(0, 0, 0, newValue));
       
            t = t + 2;
            int limit = count();
            if (limit > (t - 1)) {
                System.out.println("The height is: " + (limit - 1));
                recursionForwardTab(t, limit);
            }
        }


        isTabbed = true;

    }
    //count the number of nodes the document has. return that number
    private int count(){
        Object[] toArray = Document.getInstance().getChildren().toArray();
        return toArray.length;
    }
    
    //add by increments of 1 its tab value
    private void addPlaceInDoc(){
        placeInDoc++;
    }
    
    //remove by increments of 1 its tab value
    private void removePlaceInDoc(){
        placeInDoc--;
    }
    //get tab value
    public int getPlaceInDoc(){
        return placeInDoc;
    }
    
    //set tab value
    private void setPlaceInDoc(int i){
        placeInDoc = placeInDoc + i;
    }

    //set its location in the document
    private void setInternalIndex(boolean i) {
        internalIndex = i;
    }

    //get its location in the document
    public boolean getInternalIndex() {
        return internalIndex;
    }
    
    //return text
    public String getTheText(){
        return getText();
    }
    
    
    
    
    
    
    
}
